package com.services.api.storage.criteria;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.services.api.storage.model.OrderDetail;

import org.springframework.data.jpa.domain.Specification;

import lombok.Data;

@Data
public class OrderDetailCriteria {

    private Long id; 
    private Integer quantity;
    private Long orderId;
    private Long productId;
    private Long price;

    public Specification<OrderDetail> getSpecification() {
        return new Specification<OrderDetail>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<OrderDetail> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();

                if (getId() != null) {
                    predicates.add(cb.equal(root.get("id"), getId()));
                }
                if (getQuantity()!= null) {
                    predicates.add(cb.equal(root.get("quantity"), getQuantity()));
                }
                if (getOrderId()!= null) {
                    predicates.add(cb.equal(root.get("orderId"), getOrderId()));
                }
                if (getProductId() != null) {
                    predicates.add(cb.equal(root.get("productId"), getProductId()));
                }
                if (getPrice() != null) {
                    predicates.add(cb.equal(root.get("price"), getPrice()));
                }
                
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
