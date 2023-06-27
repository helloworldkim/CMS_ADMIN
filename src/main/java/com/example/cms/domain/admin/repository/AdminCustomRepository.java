package com.example.cms.domain.admin.repository;

import com.example.cms.domain.admin.dto.AdminDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminCustomRepository {

    Page<AdminDTO> findAdminList(Pageable pageable);
}
