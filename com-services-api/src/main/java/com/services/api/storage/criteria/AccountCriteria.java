package com.services.api.storage.criteria;

import com.services.api.storage.model.Account;
import com.services.api.storage.model.Group;

import lombok.Data;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class AccountCriteria {
    private Long id;
    private Integer kind;
    private String username;
    private String email;
    private String password;
    private String fullname;
    private Long groupId;
    private Date lastLogin;
    private String avatarPath;
    private String resetPwdCode;
    private Date resetPwdTime;
    private Integer attemptCode;
    private Integer attemptLogin;
    private String phone;
    private String lang;

    public Specification<Account> getSpecification() {
        return new Specification<Account>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<Account> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();

                if (getId() != null) {
                    predicates.add(cb.equal(root.get("id"), getId()));
                }
                if (getKind() != null) {
                    predicates.add(cb.equal(root.get("kind"), getKind()));
                }
                if (getGroupId() != null) {
                    Join<Account, Group> joinGroup = root.join("group", JoinType.INNER);
                    predicates.add(cb.equal(joinGroup.get("id"), getGroupId()));
                }
                if (getAttemptCode() != null) {
                    predicates.add(cb.equal(root.get("attemptCode"), getAttemptCode()));
                }
                if (getAttemptLogin() != null) {
                    predicates.add(cb.equal(root.get("attemptLogin"), getAttemptLogin()));
                }
                if (!StringUtils.isEmpty(getUsername())) {
                    predicates.add(cb.like(cb.lower(root.get("username")), "%" + getUsername().toLowerCase() + "%"));
                }
                if (!StringUtils.isEmpty(getEmail())) {
                    predicates.add(cb.like(cb.lower(root.get("email")), "%" + getEmail().toLowerCase() + "%"));
                }
                if (!StringUtils.isEmpty(getPassword())) {
                    predicates.add(cb.like(cb.lower(root.get("password")), "%" + getPassword().toLowerCase() + "%"));
                }
                if (!StringUtils.isEmpty(getFullname())) {
                    predicates.add(cb.like(cb.lower(root.get("fullname")), "%" + getFullname().toLowerCase() + "%"));
                }
                if (!StringUtils.isEmpty(getAvatarPath())) {
                    predicates
                            .add(cb.like(cb.lower(root.get("avatarPath")), "%" + getAvatarPath().toLowerCase() + "%"));
                }
                if (!StringUtils.isEmpty(getResetPwdCode())) {
                    predicates.add(
                            cb.like(cb.lower(root.get("resetPwdCode")), "%" + getResetPwdCode().toLowerCase() + "%"));
                }
                if (!StringUtils.isEmpty(getPhone())) {
                    predicates.add(cb.like(cb.lower(root.get("phone")), "%" + getPhone().toLowerCase() + "%"));
                }
                if (!StringUtils.isEmpty(getLang())) {
                    predicates.add(cb.like(cb.lower(root.get("lang")), "%" + getLang().toLowerCase() + "%"));
                }
                if (getLastLogin() != null) {
                    predicates.add(cb.equal(root.get("lastLogin"), getLastLogin()));
                }
                if (getResetPwdTime() != null) {
                    predicates.add(cb.equal(root.get("resetPwdTime"), getResetPwdTime()));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
