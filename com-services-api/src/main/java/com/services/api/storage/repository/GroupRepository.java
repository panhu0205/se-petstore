package com.services.api.storage.repository;

import com.services.api.storage.model.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Long>, JpaSpecificationExecutor<Group> {
   public Group findFirstByName(String name);
   public Page<Group> findAllByKind(int kind, Pageable pageable);

   @Query("SELECT DISTINCT g  FROM Group g INNER JOIN Account  a ON g.id = a.group.id JOIN g.permissions p WHERE a.id=?1 AND a.status=1 AND p.status=1 AND INSTR(?2, p.action)> 0")
   public Group checkPermission(long accountId, String action);

   public Group findFirstByKind(int kind);
}
