package com.example.cms.domain.community.repository;

import com.example.cms.domain.community.dto.CommunityDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommunityCustomRepository {

    Page<CommunityDTO> findCommunityList(Pageable pageable);
}
