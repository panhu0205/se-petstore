package com.services.api.storage.criteria;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.services.api.storage.model.BillDetail;

import org.springframework.data.jpa.domain.Specification;

import lombok.Data;

@Data
public class BillDetailCriteria {

    private Long id;
    private Long billId;
    private Long productId;
    private Integer quantity;

    public Specification<BillDetail> getSpecification() {
        return new Specification<BillDetail>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<BillDetail> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();

                if (getId() != null) {
                    predicates.add(cb.equal(root.get("id"), getId()));
                }
                if (getBillId() != null) {
                    predicates.add(cb.equal(root.get("billId"), getBillId()));
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
