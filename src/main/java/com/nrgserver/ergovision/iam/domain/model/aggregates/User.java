package com.nrgserver.ergovision.iam.domain.model.aggregates;

import com.nrgserver.ergovision.iam.domain.model.commands.UpdateUserCommand;
import com.nrgserver.ergovision.iam.domain.model.entities.Role;
import com.nrgserver.ergovision.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Entity
public class User extends AuditableAbstractAggregateRoot<User> {

    private String username;

    @Email
    private String email;

    private String imageUrl;

    private Integer age;

    private Integer height;

    private Integer weight;

    private String password;

    @ManyToMany(fetch = FetchType.EAGER,cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="role_id")
    )
    private Set<Role> userRoles;

    protected User(){
        super();
        this.userRoles = new HashSet<>();
    }

    public User(
            String username,
            String email,
            String imageUrl,
            Integer age,
            Integer height,
            Integer weight,
            String password
    ){
        this.username = username;
        this.email = email;
        this.imageUrl = imageUrl;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.password = password;
        this.userRoles = new HashSet<>();
    }

    public User(String username,
                String email,
                String imageUrl,
                Integer age,
                Integer height,
                Integer weight,
                String password,
                List<Role> roles) {
        this(username, email, imageUrl, age, height, weight, password);
        addRoles(roles);
    }

    //update
    public User updateUserDetails(UpdateUserCommand updateUserCommand){
        this.email = updateUserCommand.email();
        this.imageUrl = updateUserCommand.imageUrl();
        this.age = updateUserCommand.age();
        this.height = updateUserCommand.height();
        this.weight = updateUserCommand.weight();

        return this;
    }


    public void addRoles(List<Role> roles) {
        var validatedRoleSet = Role.validateRoleSet(roles);
        this.userRoles.addAll(validatedRoleSet);
    }
}
