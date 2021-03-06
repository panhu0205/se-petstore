package com.services.api;

import com.services.api.storage.model.Account;
import com.services.api.storage.model.Group;
import com.services.api.storage.model.Permission;
import com.services.api.storage.repository.AccountRepository;
import com.services.api.storage.repository.GroupRepository;
import com.services.api.storage.repository.PermissionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

@SpringBootApplication
@EnableScheduling
@EnableAspectJAutoProxy
@Slf4j
@ComponentScan(value = {"com.services.api"})
public class Application {

    @Autowired
    AccountRepository qrCodeStorageService;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    PermissionRepository permissionRepository;

    PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    private void createAdminUserIfNotExist(){
        Account account = qrCodeStorageService.findAccountByUsername("admin");
        if(account == null){
            List<Permission> defaultPermission = addPermission();
            Group group = initGroupDefault(defaultPermission);
            account = new Account();
            account.setUsername("admin");
            account.setPassword(passwordEncoder.encode("admin123654"));
            account.setStatus(1);
            account.setKind(1);
            account.setFullName("Root account");
            account.setGroup(group);
            qrCodeStorageService.save(account);
        }

    }

    private List<Permission> addPermission(){
        List<Permission> results = new ArrayList<>();
        Permission permissionCreateGroup = new Permission();
        permissionCreateGroup.setAction("/v1/group/create");
        permissionCreateGroup.setDescription("Create group");
        permissionCreateGroup.setName("Create Group");
        permissionCreateGroup.setNameGroup("Group");
        permissionCreateGroup.setShowMenu(false);
        results.add(permissionRepository.save(permissionCreateGroup));

        Permission permissionViewGroup = new Permission();
        permissionViewGroup.setAction("/v1/group/get");
        permissionViewGroup.setDescription("View group");
        permissionViewGroup.setName("View Group");
        permissionViewGroup.setNameGroup("Group");
        permissionViewGroup.setShowMenu(false);
        results.add(permissionRepository.save(permissionViewGroup));

        Permission permissionUpdateGroup = new Permission();
        permissionUpdateGroup.setAction("/v1/group/update");
        permissionUpdateGroup.setDescription("Update group");
        permissionUpdateGroup.setName("Update Group");
        permissionUpdateGroup.setNameGroup("Group");
        permissionUpdateGroup.setShowMenu(false);
        results.add(permissionRepository.save(permissionUpdateGroup));


        Permission permissionCreatePermission = new Permission();
        permissionCreatePermission.setAction("/v1/permission/create");
        permissionCreatePermission.setDescription("Create permission");
        permissionCreatePermission.setName("Create permission");
        permissionCreatePermission.setNameGroup("Permission");
        permissionCreatePermission.setShowMenu(false);
        results.add(permissionRepository.save(permissionCreatePermission));

        return results;
    }

    private Group initGroupDefault(List<Permission> defaultPermission){
        Group customerGroup = new Group();
        customerGroup.setKind(1);
        customerGroup.setName("ROLE CUSTOMER");
        customerGroup.setPermissions(defaultPermission);
        groupRepository.save(customerGroup);

        Group staffGroup = new Group();
        staffGroup.setKind(1);
        staffGroup.setName("ROLE STAFF");
        staffGroup.setPermissions(defaultPermission);
        groupRepository.save(staffGroup); 
        
        Group managerGroup = new Group();
        managerGroup.setKind(1);
        managerGroup.setName("ROLE MANAGER");
        managerGroup.setPermissions(defaultPermission);
        groupRepository.save(managerGroup); 

        Group superAdminGroup = new Group();
        superAdminGroup.setKind(1);
        superAdminGroup.setName("ROLE SUPPER ADMIN");
        superAdminGroup.setId(1L);
        superAdminGroup.setPermissions(defaultPermission);
        return groupRepository.save(superAdminGroup);

    }

    @PostConstruct
    public void initialize() {
        createAdminUserIfNotExist();
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }
}
