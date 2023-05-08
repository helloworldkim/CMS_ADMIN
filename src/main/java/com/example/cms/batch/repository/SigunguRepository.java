package com.example.cms.batch.repository;

import com.example.cms.batch.entity.Sigungu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SigunguRepository extends JpaRepository<Sigungu, Long> {
}
