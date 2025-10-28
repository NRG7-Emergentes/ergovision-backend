package com.nrgserver.ergovision.iam.application.internal.commandservices;


import com.nrgserver.ergovision.iam.domain.model.commands.SeedRolesCommand;
import com.nrgserver.ergovision.iam.domain.model.entities.Role;
import com.nrgserver.ergovision.iam.domain.model.valueobjects.Roles;
import com.nrgserver.ergovision.iam.domain.services.RoleCommandService;
import com.nrgserver.ergovision.iam.infrastructure.persistence.jpa.repositories.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class RoleCommandServiceImpl implements RoleCommandService {
    private final RoleRepository roleRepository;

    public RoleCommandServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    @Override
    public void handle(SeedRolesCommand seedRolesCommand) {
        Arrays.stream(Roles.values()).forEach(role -> {
            if (!roleRepository.existsByName(role)) {
                roleRepository.save(new Role(Roles.valueOf(role.name())));
            }
        });
    }
}
