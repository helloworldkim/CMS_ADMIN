package com.example.cms.domain.community.repository;

import com.example.cms.domain.community.entity.Community;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityRepository extends JpaRepository<Community, Long>, CommunityCustomRepository {

}
