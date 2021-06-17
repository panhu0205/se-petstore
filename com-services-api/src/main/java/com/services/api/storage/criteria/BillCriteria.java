package com.services.api.storage.criteria;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.services.api.storage.model.Bill;

import org.springframework.data.jpa.domain.Specification;

import lombok.Data;

@Data
public class BillCriteria {

    private Long id;
    private Date exportedDate;
    private Long customerId;
    private Long staffId;
    private Integer subTotal;
    private Integer shippingFee;

    public Specification<Bill> getSpecification() {
        return new Specification<Bill>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<Bill> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();

                if (getId() != null) {
                    predicates.add(cb.equal(root.get("id"), getId()));
                }
                if (getExportedDate() != null) {
                    predicates.add(cb.equal(root.get("exportedDate"), getExportedDate()));
                }
                if (getCustomerId() != null) {
                    predicates.add(cb.equal(root.get("customerId"), getCustomerId()));
                }
                if (getStaffId() != null) {
                    predicates.add(cb.equal(root.get("staffId"), getStaffId()));
                }
                if (getSubTotal() != null) {
                    predicates.add(cb.equal(root.get("subTotal"), getSubTotal()));
                }
                if (getShippingFee() != null) {
                    predicates.add(cb.equal(root.get("shippingFee"), getShippingFee()));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
