package com.nrgserver.ergovision.iam.domain.model.aggregates;

import com.nrgserver.ergovision.iam.domain.model.commands.UpdateUserCommand;
import com.nrgserver.ergovision.iam.domain.model.entities.Role;
import com.nrgserver.ergovision.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Entity
public class User extends AuditableAbstractAggregateRoot<User> {

    private String username;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER,cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="role_id")
    )
    private Set<Role> userRoles;

    private String name;
    private String lastName;

    @Min(value = 0, message = "Age must be positive")
    private Integer age;

    @Positive(message = "Height must be positive")
    private Double height;

    @Positive(message = "Weight must be positive")
    private Double weight;

    private String imageUrl;


    protected User(){
        super();
        this.userRoles = new HashSet<>();
    }

    public User(String username, String password){
        this.username = username;
        this.password = password;
        this.userRoles = new HashSet<>();
    }

    public User(String username, String password, List<Role> roles) {
        this(username, password);
        addRoles(roles);
    }
    public User(String username, String password, String name, String lastName,
                Integer age, Double height, Double weight, String imageUrl) {
        this(username, password);
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.imageUrl = imageUrl;
    }

    public User(String username, String password, List<Role> roles,
                String name, String lastName, Integer age, Double height,
                Double weight, String imageUrl) {
        this(username, password, name, lastName, age, height, weight, imageUrl);
        addRoles(roles);
    }


    //update
    public User updateUserDetails(UpdateUserCommand updateUserCommand) {
        /*this.username = updateUserCommand.username();
        this.password = updateUserCommand.password();*/
        this.name = updateUserCommand.name();
        this.lastName = updateUserCommand.lastName();
        this.age = updateUserCommand.age();
        this.height = updateUserCommand.height();
        this.weight = updateUserCommand.weight();
        this.imageUrl = updateUserCommand.imageUrl();
        return this;
    }


    public void addRoles(List<Role> roles) {
        var validatedRoleSet = Role.validateRoleSet(roles);
        this.userRoles.addAll(validatedRoleSet);
    }

}
