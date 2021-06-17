package com.services.api.storage.repository;

import com.services.api.storage.model.Breed;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BreedRepository extends JpaRepository<Breed, Long>, JpaSpecificationExecutor<Breed> {

}
