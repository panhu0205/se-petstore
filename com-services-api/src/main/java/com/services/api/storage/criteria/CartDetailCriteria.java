package com.services.api.storage.criteria;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.services.api.storage.model.CartDetail;

import org.springframework.data.jpa.domain.Specification;

import lombok.Data;

@Data
public class CartDetailCriteria {

    private Long id;
    private Long cartId;
    private Long productId;
    private Integer quantity;   

    public Specification<CartDetail> getSpecification() {
        return new Specification<CartDetail>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<CartDetail> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();

                if (getId() != null) {
                    predicates.add(cb.equal(root.get("id"), getId()));
                }
                if (getCartId() != null) {
                    predicates.add(cb.equal(root.get("cartId"), getCartId()));
                }
                if (getProductId() != null) {
                    predicates.add(cb.equal(root.get("productId"), getProductId()));
                }
                if (getQuantity() != null) {
                    predicates.add(cb.equal(root.get("quantity"), getQuantity()));
                }
                
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
