package com.services.api.storage.repository;

import java.util.Optional;

import com.services.api.storage.model.Account;
import com.services.api.storage.model.Cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface CartRepository extends JpaRepository<Cart, Long>, JpaSpecificationExecutor<Cart> {
    Optional<Cart> findByCustomer(Account customer);
    
    @Query("SELECT c FROM Cart c WHERE c.customer.id = ?1")
    Optional<Cart> findByCustomerId(Long customerId);

}
