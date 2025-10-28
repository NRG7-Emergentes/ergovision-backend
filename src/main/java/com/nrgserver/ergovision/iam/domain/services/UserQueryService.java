package com.nrgserver.ergovision.iam.domain.services;


import com.nrgserver.ergovision.iam.domain.model.aggregates.User;
import com.nrgserver.ergovision.iam.domain.model.queries.GetAllUsersQuery;
import com.nrgserver.ergovision.iam.domain.model.queries.GetUserByIdQuery;
import com.nrgserver.ergovision.iam.domain.model.queries.GetUserByUsernameQuery;

import java.util.List;
import java.util.Optional;

public interface UserQueryService {
    List<User> handle(GetAllUsersQuery getAllUsersQuery);

    Optional<User> handle(GetUserByIdQuery getUserByIdQuery);

    Optional<User> handle(GetUserByUsernameQuery getUserByUserNameQuery);

}
