package com.services.api.controller;

import com.services.api.constant.ServiceConstant;
import com.services.api.dto.ApiMessageDto;
import com.services.api.dto.ResponseListObj;
import com.services.api.dto.account.*;
import com.services.api.exception.UnauthorizationException;
import com.services.api.form.account.*;
import com.services.api.intercepter.MyAuthentication;
import com.services.api.jwt.JWTUtils;
import com.services.api.jwt.UserJwt;
import com.services.api.mapper.AccountMapper;
import com.services.api.service.QRCodeApiService;
import com.services.api.storage.criteria.AccountCriteria;
import com.services.api.storage.model.*;
import com.services.api.storage.repository.*;
import com.services.api.utils.AESUtils;
import com.services.api.utils.ConvertUtils;
import com.services.api.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.Objects;

@RestController
@RequestMapping("/v1/account")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class AccountController extends ABasicController {
    PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    QRCodeApiService qrCodeApiService;

    @Autowired
    AccountMapper accountMapper;

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ResponseListObj<AccountDto>> getList(AccountCriteria accountCriteria, Pageable pageable) {
        // if (!isAdmin()) {
        //     throw new UnauthorizationException("Not allowed to get");
        // }
        ApiMessageDto<ResponseListObj<AccountDto>> apiMessageDto = new ApiMessageDto<>();

        Page<Account> accountPage = accountRepository.findAll(accountCriteria.getSpecification(), pageable);

        ResponseListObj<AccountDto> responseListObj = new ResponseListObj<>();
        responseListObj.setData(accountMapper.fromEntityListToDtoList(accountPage.getContent()));
        responseListObj.setPage(pageable.getPageNumber());
        responseListObj.setTotalPage(accountPage.getTotalPages());
        responseListObj.setTotalElements(accountPage.getTotalElements());

        apiMessageDto.setData(responseListObj);
        apiMessageDto.setMessage("List account success");
        return apiMessageDto;
    }

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<LoginDto> login(@Valid @RequestBody LoginForm loginForm, BindingResult bindingResult) {

        ApiMessageDto<LoginDto> apiMessageDto = new ApiMessageDto<>();
        Account account = accountRepository.findAccountByUsername(loginForm.getUsername());
        if (account == null || !passwordEncoder.matches(loginForm.getPassword(), account.getPassword())
                || !Objects.equals(account.getStatus(), ServiceConstant.STATUS_ACTIVE)) {
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("Login fail, check your username or password");
            return apiMessageDto;
        }

        // Tao xong tra ve cai gi?
        LocalDate parsedDate = LocalDate.now();
        parsedDate = parsedDate.plusDays(7);

        UserJwt qrJwt = new UserJwt();
        qrJwt.setAccountId(account.getId());
        qrJwt.setKind(null);
        String appendStringRole = "";
        if (Objects.equals(account.getKind(), ServiceConstant.USER_KIND_ADMIN)) {
            appendStringRole = "/account/profile,/account/update_profile_admin";
        } else if (Objects.equals(account.getKind(), ServiceConstant.USER_KIND_AGENCY)) {
            appendStringRole = "/agency/profile,/agency/update_profile,/agency/update-position";
        } else if (Objects.equals(account.getKind(), ServiceConstant.USER_KIND_USER)) {
            appendStringRole = "/customer/profile,/customer/update_profile";
        }
        qrJwt.setUsername(account.getUsername());
        qrJwt.setPemission(qrCodeApiService.convertGroupToUri(account.getGroup().getPermissions()) + appendStringRole);
        qrJwt.setUserKind(account.getKind());

        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(new MyAuthentication(qrJwt));

        log.info("jwt user ne: {}", qrJwt);
        String token = JWTUtils.createJWT(JWTUtils.ALGORITHMS_HMAC, "authenticationToken.getId().toString()", qrJwt,
                DateUtils.convertToDateViaInstant(parsedDate));
        LoginDto loginDto = new LoginDto();
        loginDto.setFullName(account.getFullName());
        loginDto.setId(account.getId());
        loginDto.setToken(token);
        loginDto.setUsername(account.getUsername());
        loginDto.setKind(account.getKind());

        apiMessageDto.setData(loginDto);
        apiMessageDto.setMessage("Login account success");
        account.setLastLogin(new Date());
        // update lastLogin
        accountRepository.save(account);
        return apiMessageDto;

    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> create(@Valid @RequestBody CreateAccountForm createAccountForm,
            BindingResult bindingResult) {
        // if (!isAdmin()) {
        //     throw new UnauthorizationException("Not allowed create admin.");
        // }
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();

        //
        Long accountCheck = accountRepository.countAccountByUsernameOrEmailOrPhone(createAccountForm.getUsername(),
                createAccountForm.getEmail(), createAccountForm.getPhone());
        if (accountCheck > 0) {
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("Username is exist ! or the Email or the Phone number is used");
            return apiMessageDto;
        }
        Group group = groupRepository.findById(createAccountForm.getGroupId()).orElse(null);
        if (group == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("Group doesnt exist!");
            return apiMessageDto;
        }

        Account account = accountMapper.fromCreateAccountFormToEntity(createAccountForm);
        account.setPassword(passwordEncoder.encode(createAccountForm.getPassword()));
        account.setKind(ServiceConstant.USER_KIND_USER);
        account.setStatus(ServiceConstant.STATUS_ACTIVE);
        account.setGroup(group);
        accountRepository.save(account);
        apiMessageDto.setMessage("Create account success");
        return apiMessageDto;

    }

    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> updateAdmin(@Valid @RequestBody UpdateAccountForm updateAccountForm,
            BindingResult bindingResult) {
        if (!isAdmin()) {
            throw new UnauthorizationException("Not allowed update.");
        }

        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Account account = accountRepository.findById(updateAccountForm.getId()).orElse(null);
        if (account == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("Account not found");
            return apiMessageDto;
        }
        Group group = groupRepository.findById(updateAccountForm.getGroupId()).orElse(null);
        if (group == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("Group doesnt exist!");
            return apiMessageDto;
        }

        accountMapper.mappingFormUpdateToEntity(updateAccountForm, account);

        if (StringUtils.isNoneBlank(updateAccountForm.getPassword())) {
            account.setPassword(passwordEncoder.encode(updateAccountForm.getPassword()));
        }
        account.setFullName(updateAccountForm.getFullName());
        if (StringUtils.isNoneBlank(updateAccountForm.getAvatarPath())) {
            if (!updateAccountForm.getAvatarPath().equals(account.getAvatarPath())) {
                // delete old image
                qrCodeApiService.deleteFile(account.getAvatarPath());
            }
            account.setAvatarPath(updateAccountForm.getAvatarPath());
        }

        accountRepository.save(account);

        apiMessageDto.setMessage("Update account success");
        return apiMessageDto;

    }

    @GetMapping(value = "/profile", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<AccountDto> profile() {
        long id = getCurrentUserId();
        Account account = accountRepository.findById(id).orElse(null);
        ApiMessageDto<AccountDto> apiMessageDto = new ApiMessageDto<>();
        apiMessageDto.setData(accountMapper.fromEntityToAccountDto(account));
        apiMessageDto.setMessage("Get Account success");
        return apiMessageDto;

    }

    @PutMapping(value = "/update_profile_admin", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> updateProfileAdmin(@Valid @RequestBody UpdateProfileAdminForm updateProfileAdminForm,
            BindingResult bindingResult) {

        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        long id = getCurrentUserId();
        Account account = accountRepository.findById(id).orElse(null);
        if (account == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("Account not found");
            return apiMessageDto;
        }
        if (!passwordEncoder.matches(updateProfileAdminForm.getOldPassword(), account.getPassword())) {
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("oldPassword not match in db");
            return apiMessageDto;
        }

        if (StringUtils.isNoneBlank(updateProfileAdminForm.getPassword())) {
            account.setPassword(passwordEncoder.encode(updateProfileAdminForm.getPassword()));
        }
        if (StringUtils.isNoneBlank(updateProfileAdminForm.getAvatarPath())) {
            if (!updateProfileAdminForm.getAvatarPath().equals(account.getAvatarPath())) {
                // delete old image
                qrCodeApiService.deleteFile(account.getAvatarPath());
            }
            account.setAvatarPath(updateProfileAdminForm.getAvatarPath());
        }
        accountMapper.mappingFormUpdateProfileToEntity(updateProfileAdminForm, account);
        accountRepository.save(account);

        apiMessageDto.setMessage("Update admin account success");
        return apiMessageDto;

    }

    @Transactional
    @GetMapping(value = "/logout", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> logout() {
        // Long id = getCurrentUserId();
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        apiMessageDto.setMessage("Logout success");
        return apiMessageDto;
    }

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<AccountDto> get(@PathVariable("id") Long id) {
        if (!isAdmin()) {
            throw new UnauthorizationException("Not allowed get.");
        }
        Account account = accountRepository.findById(id).orElse(null);
        ApiMessageDto<AccountDto> apiMessageDto = new ApiMessageDto<>();
        if (account == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("Can not get account");
            return apiMessageDto;
        }

        apiMessageDto.setData(accountMapper.fromEntityToAccountDto(account));
        apiMessageDto.setMessage("Get shop profile success");
        return apiMessageDto;

    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> delete(@PathVariable("id") Long id) {
        if (!isAdmin()) {
            throw new UnauthorizationException("Not allowed delete.");
        }
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Account account = accountRepository.findById(id).orElse(null);
        if (account == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("Account not found to delete");
            return apiMessageDto;

        }

        qrCodeApiService.deleteFile(account.getAvatarPath());
        accountRepository.deleteById(id);
        apiMessageDto.setMessage("Delete Account success");
        return apiMessageDto;
    }

    @PostMapping(value = "/request_forget_password", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<ForgetPasswordDto> requestForgetPassword(
            @Valid @RequestBody RequestForgetPasswordForm forgetForm, BindingResult bindingResult) {
        ApiMessageDto<ForgetPasswordDto> apiMessageDto = new ApiMessageDto<>();
        Account account = accountRepository.findAccountByEmail(forgetForm.getEmail());
        if (account == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("account not found.");
            return apiMessageDto;
        }

        String otp = qrCodeApiService.getOTPForgetPassword();
        account.setAttemptCode(0);
        account.setResetPwdCode(otp);
        account.setResetPwdTime(new Date());
        accountRepository.save(account);

        // send email
        qrCodeApiService.sendEmail(account.getEmail(), "OTP: " + otp, "Reset password", false);

        ForgetPasswordDto forgetPasswordDto = new ForgetPasswordDto();
        String hash = AESUtils.encrypt(account.getId() + ";" + otp, true);
        forgetPasswordDto.setIdHash(hash);

        apiMessageDto.setResult(true);
        apiMessageDto.setData(forgetPasswordDto);
        apiMessageDto.setMessage("Request forget password successfull, please check email.");
        return apiMessageDto;
    }

    @PostMapping(value = "/forget_password", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<Long> forgetPassword(@Valid @RequestBody ForgetPasswordForm forgetForm,
            BindingResult bindingResult) {
        ApiMessageDto<Long> apiMessageDto = new ApiMessageDto<>();

        String[] hash = AESUtils.decrypt(forgetForm.getIdHash(), true).split(";", 2);
        Long id = ConvertUtils.convertStringToLong(hash[0]);
        if (Objects.equals(id, 0)) {
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("Wrong hash.");
            return apiMessageDto;
        }

        Account account = accountRepository.findById(id).orElse(null);
        if (account == null) {
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("Account not found.");
            return apiMessageDto;
        }

        if (account.getAttemptCode() >= ServiceConstant.MAX_ATTEMPT_FORGET_PWD) {
            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("Account locked");
            return apiMessageDto;
        }

        if (!account.getResetPwdCode().equals(forgetForm.getOtp()) || (new Date().getTime()
                - account.getResetPwdTime().getTime() >= ServiceConstant.MAX_TIME_FORGET_PWD)) {
            // tang so lan
            account.setAttemptCode(account.getAttemptCode() + 1);
            accountRepository.save(account);

            apiMessageDto.setResult(false);
            apiMessageDto.setMessage("Code invalid");
            return apiMessageDto;
        }

        account.setResetPwdTime(null);
        account.setResetPwdCode(null);
        account.setAttemptCode(null);
        account.setPassword(passwordEncoder.encode(forgetForm.getNewPassword()));
        accountRepository.save(account);

        apiMessageDto.setResult(true);
        apiMessageDto.setMessage("Change password success.");
        return apiMessageDto;
    }

    @PostMapping(value = "/verify", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiMessageDto<String> verify(@RequestBody @Valid VerifyForm verifyForm, BindingResult bindingResult) {
        ApiMessageDto<String> apiMessageDto = new ApiMessageDto<>();
        Account account = accountRepository.findById(verifyForm.getId()).orElse(null);
        Period period = Period.between(account.getCreatedDate().toLocalDate(), LocalDate.now());
        if (period.getDays() >= Account.EXPIRE_TIME) {
            // if(Objects.equals(account.getKind(), ServiceConstant.USER_KIND_AGENCY)){
            // agencyRepository.deleteById(verifyForm.getId());
            // }
            // else if(Objects.equals(account.getKind(),
            // ServiceConstant.USER_KIND_CUSTOMER)){
            // customerRepository.deleteById(verifyForm.getId());
            // }
            accountRepository.deleteById(verifyForm.getId());
            apiMessageDto.setMessage("Times to verify is expired");
            return apiMessageDto;
        }
        // if(account.getOtp().equals(verifyForm.getOtp())){
        // account.setStatus(1);
        // accountRepository.save(account);
        // if(Objects.equals(account.getKind(), ServiceConstant.USER_KIND_AGENCY)){
        // Agency agency = agencyRepository.findById(verifyForm.getId()).orElse(null);
        // agency.setStatus(1);
        // agencyRepository.save(agency);
        // }
        // else if(Objects.equals(account.getKind(),
        // ServiceConstant.USER_KIND_CUSTOMER)) {

        // Customer customer =
        // customerRepository.findById(verifyForm.getId()).orElse(null);
        // customer.setStatus(ServiceConstant.STATUS_ACTIVE);
        // customerRepository.save(customer);

        // // assume that there is a invitation record for this customer
        // Invitation invitation = invitationRepository.findByUserInvite(customer);
        // // if customer is created with referral code
        // if (invitation != null) {
        // invitation.setStatus(ServiceConstant.STATUS_ACTIVE);
        // invitationRepository.save(invitation);
        // }
        // }
        // apiMessageDto.setMessage("Verify account success");
        // return apiMessageDto;
        // }
        account.setVerifyTime(account.getVerifyTime() + 1);
        if (account.getVerifyTime().compareTo(Account.MAX_VERIFY_TIMES) >= 0) {
            // if(Objects.equals(account.getKind(), ServiceConstant.USER_KIND_AGENCY)){
            // agencyRepository.deleteById(verifyForm.getId());
            // }
            // else if(Objects.equals(account.getKind(),
            // ServiceConstant.USER_KIND_CUSTOMER)){
            // customerRepository.deleteById(verifyForm.getId());
            // }
            accountRepository.deleteById(verifyForm.getId());
            apiMessageDto.setMessage("Number of verify times is over");
            return apiMessageDto;
        }
        apiMessageDto.setMessage("Otp is not match");
        return apiMessageDto;
    }
}
