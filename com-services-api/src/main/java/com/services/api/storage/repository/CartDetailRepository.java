package com.services.api.storage.repository;

import java.util.Optional;

import com.services.api.storage.model.CartDetail;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface CartDetailRepository extends JpaRepository<CartDetail, Long>, JpaSpecificationExecutor<CartDetail> {

    @Query("SELECT c FROM CartDetail c WHERE c.product.id = ?1 AND c.cart.id = ?2 ")
    Optional<CartDetail> findByProductIdAndCartId (Long productId, Long cartId);
}
