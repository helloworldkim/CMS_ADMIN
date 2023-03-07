package com.example.cms.domain.admingroup.repository;

import com.example.cms.domain.admingroup.entity.AdminGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminGroupRepository extends JpaRepository<AdminGroup, Long> {
}
