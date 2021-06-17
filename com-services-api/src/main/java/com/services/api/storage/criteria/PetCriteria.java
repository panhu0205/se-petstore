package com.services.api.storage.criteria;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.services.api.storage.model.Pet;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import lombok.Data;

@Data
public class PetCriteria {

    private Long id;
    private Integer gender;
    private Integer age;
    private String origin;
    private Long breedId;
    private Long postId;

    public Specification<Pet> getSpecification() {
        return new Specification<Pet>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<Pet> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();

                if (getId() != null) {
                    predicates.add(cb.equal(root.get("id"), getId()));
                }
                if (getGender() != null) {
                    predicates.add(cb.equal(root.get("gender"), getGender()));
                }
                if (getAge() != null) {
                    predicates.add(cb.equal(root.get("age"), getAge()));
                }
                if (getOrigin()!= null) {
                    predicates.add(cb.equal(root.get("origin"), getOrigin()));
                }
                if (getBreedId() != null) {
                    predicates.add(cb.equal(root.get("breedId"), getBreedId()));
                }
                if (getPostId() != null) {
                    predicates.add(cb.equal(root.get("postId"), getPostId()));
                }
                if (!ObjectUtils.isEmpty(getOrigin())) {
                    predicates.add(cb.like(cb.lower(root.get("origin")), "%" + getOrigin().toLowerCase() + "%"));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
