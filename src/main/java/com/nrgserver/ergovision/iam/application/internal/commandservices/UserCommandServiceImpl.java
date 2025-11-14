package com.nrgserver.ergovision.iam.application.internal.commandservices;

import com.nrgserver.ergovision.iam.application.internal.outboundservices.hashing.HashingService;
import com.nrgserver.ergovision.iam.application.internal.outboundservices.tokens.TokenService;
import com.nrgserver.ergovision.iam.domain.model.aggregates.User;
import com.nrgserver.ergovision.iam.domain.model.commands.DeleteUserCommand;
import com.nrgserver.ergovision.iam.domain.model.commands.SignInCommand;
import com.nrgserver.ergovision.iam.domain.model.commands.SignUpCommand;
import com.nrgserver.ergovision.iam.domain.model.commands.UpdateUserCommand;
import com.nrgserver.ergovision.iam.domain.services.UserCommandService;
import com.nrgserver.ergovision.iam.infrastructure.persistence.jpa.repositories.RoleRepository;
import com.nrgserver.ergovision.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserCommandServiceImpl implements UserCommandService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final HashingService hashingService;
    private final TokenService tokenService;

    public UserCommandServiceImpl(UserRepository userRepository, RoleRepository roleRepository, HashingService hashingService, TokenService tokenService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.hashingService = hashingService;
        this.tokenService = tokenService;
    }

    @Override
    public Optional<User> handle(UpdateUserCommand updateUserCommand, Long userId) {
        var userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("User with ID " + userId + " not found");
        }

        //Check if a user with the same email already exists
        var existingUserWithEmail = userRepository.findByUsername(updateUserCommand.username());
        if (existingUserWithEmail.isPresent() && !existingUserWithEmail.get().getId().equals(userId)) {
            throw new IllegalArgumentException("User with username " + updateUserCommand.username() + " already exists");
        }

        var userToUpdate = userOptional.get();

        String encodedPassword = updateUserCommand.password();
        if (encodedPassword != null && !encodedPassword.isBlank()) {
            encodedPassword = hashingService.encode(encodedPassword);
        } else {
            // Si viene vacía, mantén la actual
            encodedPassword = userToUpdate.getPassword();
        }

        var commandWithEncodedPassword = new UpdateUserCommand(
                updateUserCommand.username(),
                updateUserCommand.email(),
                updateUserCommand.imageUrl(),
                updateUserCommand.age(),
                updateUserCommand.height(),
                updateUserCommand.weight(),
                encodedPassword
        );

        try{
            var updatedUser= userRepository.save(userToUpdate.updateUserDetails(commandWithEncodedPassword));
            return Optional.of(updatedUser);
        }catch (Exception e) {
            // Handle exception, e.g., log it or rethrow as a custom exception
            return Optional.empty();
        }
    }

    @Override
    public void handle(DeleteUserCommand deleteUserCommand) {
        if (!userRepository.existsById(deleteUserCommand.userId())) {
            throw new IllegalArgumentException("User with ID " + deleteUserCommand.userId() + " not found");
        }

        try{
            userRepository.deleteById(deleteUserCommand.userId());
        } catch (Exception e) {
            // Handle exception, e.g., log it or rethrow as a custom exception
            throw new RuntimeException("Error deleting user: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<ImmutablePair<User, String>> handle(SignInCommand signInCommand) {
        var user = userRepository.findByUsername(signInCommand.username());

        if (user.isEmpty()) {
            throw new IllegalArgumentException("User with user name " + signInCommand.username() + " not found");
        }
        if (!hashingService.matches(signInCommand.password(), user.get().getPassword())) {
            throw new IllegalArgumentException("Invalid password");
        }
        var token = tokenService.generateToken(user.get().getUsername());
        return Optional.of(ImmutablePair.of(user.get(), token));
    }

    @Override
    public Optional<User> handle(SignUpCommand signUpCommand) {
        if (userRepository.existsByUsername(signUpCommand.username())) {
            throw new IllegalArgumentException("User with user name " + signUpCommand.username() + " already exists");
        }
        var roles= signUpCommand.roles().stream().map(
                role->roleRepository.findByName(role)
                        .orElseThrow(() -> new IllegalArgumentException("Role " + role + " not found"))
                ).toList();
        var user = new User(
                signUpCommand.username(),
                signUpCommand.email(),
                signUpCommand.imageUrl(),
                signUpCommand.age(),
                signUpCommand.height(),
                signUpCommand.weight(),
                hashingService.encode(signUpCommand.password()),
                roles);
        userRepository.save(user);
        return userRepository.findByUsername(signUpCommand.username());
    }
}
