package com.services.api.storage.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "petmart_account")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Account extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Integer kind;
    private String username;
    private String email;
    @JsonIgnore
    private String password;
    @Column(name = "full_name")
    private String fullName;
    @ManyToOne // (fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;
    @Column(name = "last_login")
    private Date lastLogin;
    @Column(name = "avatar_path")
    private String avatarPath;
    @Column(name = "reset_pwd_code")
    private String resetPwdCode;
    @Column(name = "reset_pwd_time")
    private Date resetPwdTime;

    @Column(name = "attempt_forget_pwd")
    private Integer attemptCode;

    @Column(name = "attempt_login")
    private Integer attemptLogin;

    private String phone;
    private String lang;
    private String otp;
    private Integer verifyTime;
    public final static Integer MAX_VERIFY_TIMES = 5;
    public final static int EXPIRE_TIME = 1; // 1 day
}
