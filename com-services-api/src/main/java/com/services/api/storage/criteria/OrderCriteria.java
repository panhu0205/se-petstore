package com.services.api.storage.criteria;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.services.api.storage.model.Order;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import lombok.Data;

@Data
public class OrderCriteria {

    private Long id;
    private Integer state;
    private Long customerId;
    private String address;
    private Long total;

    public Specification<Order> getSpecification() {
        return new Specification<Order>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();

                if (getId() != null) {
                    predicates.add(cb.equal(root.get("id"), getId()));
                }
                if (getState() != null) {
                    predicates.add(cb.equal(root.get("state"), getState()));
                }
                if (getCustomerId() != null) {
                    predicates.add(cb.equal(root.get("customerId"), getCustomerId()));
                }
                if (!ObjectUtils.isEmpty(getAddress())) {
                    predicates.add(cb.like(cb.lower(root.get("address")), "%" + getAddress().toLowerCase() + "%"));
                }
                if (getTotal() != null) {
                    predicates.add(cb.equal(root.get("total"), getTotal()));
                }
                
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
