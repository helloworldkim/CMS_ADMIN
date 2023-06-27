package com.example.cms.domain.admingroup.repository;

import com.example.cms.domain.admingroup.dto.AdminGroupDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminGroupCustomRepository {

    Page<AdminGroupDTO> findAdminGroupList(Pageable pageable);
}
