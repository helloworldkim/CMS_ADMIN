package com.example.cms.domain.admin.repository;

import com.example.cms.domain.admin.entity.Admin;
import com.example.cms.domain.admingroup.entity.AdminGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long>, AdminCustomRepository {

    @Query("SELECT a FROM Admin a join fetch a.adminGroup where a.adminId = :adminId")
    Optional<Admin> findByAdminId(@Param("adminId")String adminId);

    @Query("SELECT a FROM Admin a join fetch a.adminGroup where a.adminId = :adminId and a.deleted = false")
    Optional<Admin> findByAdminIdActive(@Param("adminId")String adminId);

    Optional<Admin> findByAdminGroup(AdminGroup adminGroup);
}
