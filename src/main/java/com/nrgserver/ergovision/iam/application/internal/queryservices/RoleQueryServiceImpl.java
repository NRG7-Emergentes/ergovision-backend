package com.nrgserver.ergovision.iam.application.internal.queryservices;


import com.nrgserver.ergovision.iam.domain.model.entities.Role;
import com.nrgserver.ergovision.iam.domain.model.queries.GetAllRolesQuery;
import com.nrgserver.ergovision.iam.domain.services.RoleQueryService;
import com.nrgserver.ergovision.iam.infrastructure.persistence.jpa.repositories.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleQueryServiceImpl implements RoleQueryService {

    private final RoleRepository roleRepository;

    public RoleQueryServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> handle(GetAllRolesQuery getAllRolesQuery) {
        return roleRepository.findAll();
    }

}
