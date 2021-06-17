package com.services.api.storage.criteria;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.services.api.storage.model.Cart;

import org.springframework.data.jpa.domain.Specification;

import lombok.Data;

@Data
public class CartCriteria {

    private Long id;
    private Long customerId;   

    public Specification<Cart> getSpecification() {
        return new Specification<Cart>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<Cart> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();

                if (getId() != null) {
                    predicates.add(cb.equal(root.get("id"), getId()));
                }
                if (getCustomerId() != null) {
                    predicates.add(cb.equal(root.get("customerId"), getCustomerId()));
                }
                
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
