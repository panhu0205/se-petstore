package com.services.api.storage.repository;

import com.services.api.storage.model.CartDetail;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CartDetailRepository extends JpaRepository<CartDetail, Long>, JpaSpecificationExecutor<CartDetail> {

}
