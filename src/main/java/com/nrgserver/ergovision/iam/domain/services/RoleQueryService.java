package com.nrgserver.ergovision.iam.domain.services;


import com.nrgserver.ergovision.iam.domain.model.entities.Role;
import com.nrgserver.ergovision.iam.domain.model.queries.GetAllRolesQuery;

import java.util.List;

public interface RoleQueryService {

    List<Role> handle(GetAllRolesQuery getAllRolesQuery);
}
