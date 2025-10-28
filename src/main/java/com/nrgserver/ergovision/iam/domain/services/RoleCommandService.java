package com.nrgserver.ergovision.iam.domain.services;


import com.nrgserver.ergovision.iam.domain.model.commands.SeedRolesCommand;

public interface RoleCommandService {

    void handle(SeedRolesCommand seedRolesCommand);

}
