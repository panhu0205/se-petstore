package com.services.api.storage.criteria;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.services.api.storage.model.Product;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import lombok.Data;

@Data
public class ProductCriteria {

    private Long id;
    private Long postId;
    private Long productCategoryId;
    private Integer price;
    private Integer quantity;
    private Integer status;
    private Integer weight;
    private String origin;

    public Specification<Product> getSpecification() {
        return new Specification<Product>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();

                if (getId() != null) {
                    predicates.add(cb.equal(root.get("id"), getId()));
                }
                if (getPostId() != null) {
                    predicates.add(cb.equal(root.get("postId"), getPostId()));
                }
                if (getProductCategoryId() != null) {
                    predicates.add(cb.equal(root.get("productCategoryId"), getProductCategoryId()));
                }
                if (getPrice() != null) {
                    predicates.add(cb.equal(root.get("price"), getPrice()));
                }
                if (getQuantity() != null) {
                    predicates.add(cb.equal(root.get("quantity"), getQuantity()));
                }
                if (getStatus() != null) {
                    predicates.add(cb.equal(root.get("status"), getStatus()));
                }
                if (getWeight() != null) {
                    predicates.add(cb.equal(root.get("weight"), getWeight()));
                }
                if (!ObjectUtils.isEmpty(getOrigin())) {
                    predicates.add(cb.like(cb.lower(root.get("origin")), "%" + getOrigin().toLowerCase() + "%"));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
