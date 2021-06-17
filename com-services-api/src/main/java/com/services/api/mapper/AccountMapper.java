package com.services.api.mapper;

import java.util.List;

import com.services.api.dto.account.AccountDto;
import com.services.api.dto.account.AccountDto;
import com.services.api.form.account.CreateAccountForm;
import com.services.api.form.account.UpdateAccountForm;
import com.services.api.form.account.UpdateProfileAdminForm;
import com.services.api.storage.model.Account;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AccountMapper {

    @Mapping(source = "username", target = "username")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "fullName", target = "fullName")
    @Mapping(source = "avatarPath", target = "avatarPath")
    @Mapping(source = "groupId", target = "group.id")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "lang", target = "lang")
    Account fromCreateAccountFormToEntity(CreateAccountForm createAccountForm);

    @Mapping(source = "fullName", target = "fullName")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "groupId", target = "group.id")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "lang", target = "lang")
    void mappingFormUpdateToEntity(UpdateAccountForm updateAccountForm, @MappingTarget Account account);

    @Mapping(source = "fullName", target = "fullName")
    void mappingFormUpdateProfileToEntity(UpdateProfileAdminForm updateProfileAdminForm,
            @MappingTarget Account account);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "kind", target = "kind")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "fullName", target = "fullName")
    @Mapping(source = "group", target = "group")
    @Mapping(source = "lastLogin", target = "lastLogin")
    @Mapping(source = "avatarPath", target = "avatar")
    @Mapping(source = "phone", target = "phone")
    @Mapping(source = "lang", target = "lang")
    AccountDto fromEntityToAccountDto(Account account);

    // @Mapping(source = "id", target = "id")
    // @Mapping(source = "kind", target = "kind")
    // @Mapping(source = "username", target = "username")
    // @Mapping(source = "email", target = "email")
    // @Mapping(source = "fullName", target = "fullName")
    // @Mapping(source = "group", target = "group")
    // @Mapping(source = "lastLogin", target = "lastLogin")
    // @Mapping(source = "avatarPath", target = "avatar")
    // @Mapping(source = "createdBy", target = "createdBy")
    // @Mapping(source = "createdDate", target = "createdDate")
    // @Mapping(source = "modifiedBy", target = "modifiedBy")
    // @Mapping(source = "status", target = "status")
    // @Mapping(source = "phone", target = "phone")
    // @Mapping(source = "lang", target = "lang")
    // AccountDto fromEntityToAccountDto(Account account);

    @IterableMapping(elementTargetType = AccountDto.class)
    List<AccountDto> fromEntityListToDtoList(List<Account> content);
}
