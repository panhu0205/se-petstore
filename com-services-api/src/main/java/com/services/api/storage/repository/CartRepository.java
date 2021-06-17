package com.services.api.storage.repository;

import java.util.Optional;

import com.services.api.storage.model.Account;
import com.services.api.storage.model.Cart;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CartRepository extends JpaRepository<Cart, Long>, JpaSpecificationExecutor<Cart> {
    Optional<Cart> findByCustomer(Account customer);
}
