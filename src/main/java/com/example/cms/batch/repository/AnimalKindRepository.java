package com.example.cms.batch.repository;

import com.example.cms.batch.entity.AnimalKind;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimalKindRepository extends JpaRepository<AnimalKind, Long> {
}
