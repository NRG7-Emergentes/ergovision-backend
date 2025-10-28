package com.nrgserver.ergovision.iam.application.internal.queryservices;

import com.nrgserver.ergovision.iam.domain.model.aggregates.User;
import com.nrgserver.ergovision.iam.domain.model.queries.GetAllUsersQuery;
import com.nrgserver.ergovision.iam.domain.model.queries.GetUserByIdQuery;
import com.nrgserver.ergovision.iam.domain.model.queries.GetUserByUsernameQuery;
import com.nrgserver.ergovision.iam.domain.services.UserQueryService;
import com.nrgserver.ergovision.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserQueryServiceImpl implements UserQueryService {

    private final UserRepository userRepository;

    public UserQueryServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> handle(GetUserByIdQuery getUserByIdQuery) {
        return userRepository.findById(getUserByIdQuery.userId());
    }

    @Override
    public Optional<User> handle(GetUserByUsernameQuery getUserByUsernameQuery) {
        return userRepository.findByUsername(getUserByUsernameQuery.username());
    }

    @Override
    public List<User> handle(GetAllUsersQuery getAllUsersQuery) {
        return userRepository.findAll();
    }



}
