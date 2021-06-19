package com.services.api.storage.repository;

import java.util.Optional;

import com.services.api.storage.model.Order;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {

    @Query("SELECT o FROM Order o INNER JOIN OrderDetail p WHERE p.product.id = ?1 AND o.state = 1")
    Optional<Page<Order>> findByProductIdValidated(Long productId, Pageable pageable);

    @Query("SELECT o FROM Order o WHERE o.state = 1")
    Optional<Page<Order>> findValidate (Pageable pageable);
}
