package com.nrgserver.ergovision.iam.domain.services;


import com.nrgserver.ergovision.iam.domain.model.aggregates.User;
import com.nrgserver.ergovision.iam.domain.model.commands.DeleteUserCommand;
import com.nrgserver.ergovision.iam.domain.model.commands.SignInCommand;
import com.nrgserver.ergovision.iam.domain.model.commands.SignUpCommand;
import com.nrgserver.ergovision.iam.domain.model.commands.UpdateUserCommand;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.Optional;

public interface UserCommandService {

    void handle(DeleteUserCommand deleteUserCommand);

    Optional<User> handle(UpdateUserCommand updateUserCommand , Long userId);

    Optional<ImmutablePair<User,String>> handle(SignInCommand signInCommand);

    Optional<User> handle(SignUpCommand signUpCommand);

}
