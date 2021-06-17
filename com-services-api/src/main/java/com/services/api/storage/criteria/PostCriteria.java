package com.services.api.storage.criteria;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.services.api.storage.model.Post;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import lombok.Data;

@Data
public class PostCriteria {

    private Long id;
    private String title;
    private String image;
    private String shortDescription;
    private String longDescription;

    public Specification<Post> getSpecification() {
        return new Specification<Post>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<Post> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();

                if (getId() != null) {
                    predicates.add(cb.equal(root.get("id"), getId()));
                }
                if (!ObjectUtils.isEmpty(getTitle())) {
                    predicates.add(cb.like(cb.lower(root.get("title")), "%" + getTitle().toLowerCase() + "%"));
                }
                if (!ObjectUtils.isEmpty(getImage())) {
                    predicates.add(cb.like(cb.lower(root.get("image")), "%" + getImage().toLowerCase() + "%"));
                }
                if (!ObjectUtils.isEmpty(getShortDescription())) {
                    predicates.add(cb.like(cb.lower(root.get("shortDescription")), "%" + getShortDescription().toLowerCase() + "%"));
                }
                if (!ObjectUtils.isEmpty(getLongDescription())) {
                    predicates.add(cb.like(cb.lower(root.get("longDescription")), "%" + getLongDescription().toLowerCase() + "%"));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
