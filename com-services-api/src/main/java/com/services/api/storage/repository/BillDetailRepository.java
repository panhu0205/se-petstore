package com.services.api.storage.repository;

import com.services.api.storage.model.BillDetail;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BillDetailRepository extends JpaRepository<BillDetail, Long>, JpaSpecificationExecutor<BillDetail> {

}
