package com.services.api.storage.criteria;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.services.api.storage.model.ProductCategory;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import lombok.Data;

@Data
public class ProductCategoryCriteria {

    private Long id;
    private String name;
    private String description;

    public Specification<ProductCategory> getSpecification() {
        return new Specification<ProductCategory>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<ProductCategory> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();

                if (getId() != null) {
                    predicates.add(cb.equal(root.get("id"), getId()));
                }
                if (!ObjectUtils.isEmpty(getName())) {
                    predicates.add(cb.like(cb.lower(root.get("name")), "%" + getName().toLowerCase() + "%"));
                }
                if (!ObjectUtils.isEmpty(getDescription())) {
                    predicates.add(cb.like(cb.lower(root.get("description")), "%" + getDescription().toLowerCase() + "%"));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
