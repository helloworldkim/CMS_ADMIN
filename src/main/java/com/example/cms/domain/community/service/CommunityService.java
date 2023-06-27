package com.example.cms.domain.community.service;

import com.example.cms.domain.community.dto.CommunityDTO;
import com.example.cms.domain.community.entity.Community;
import com.example.cms.domain.community.repository.CommunityRepository;
import com.example.cms.system.util.MessageUtil;
import com.example.cms.web.controller.community.CommunityForm;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommunityService {
    private final CommunityRepository communityRepository;
    private final MessageUtil messageUtil;

    @Transactional
    public Long save(Community community) {
        return communityRepository.save(community).getId();
    }

    @Transactional
    public void update(CommunityForm communityForm) {
        Community community = communityRepository.findById(communityForm.getId()).orElseThrow(() -> new IllegalStateException(messageUtil.getMessage("message.board.common.unknown.target")));
        community.updateCommunity(communityForm.getTitle(), communityForm.getContent());
    }


    public Optional<Community> findById(Long id) {
        return communityRepository.findById(id);
    }

    @Transactional
    public void deleteById(Long id) {
        communityRepository.findById(id).orElseThrow(() -> new IllegalStateException(messageUtil.getMessage("message.board.common.unknown.target")));
        communityRepository.deleteById(id);
    }

    public Page<CommunityDTO> findAll(Pageable pageable) {
        return communityRepository.findCommunityList(pageable);
    }

}
