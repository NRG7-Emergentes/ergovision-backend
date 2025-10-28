package com.nrgserver.ergovision.iam.domain.model.aggregates;

import com.nrgserver.ergovision.iam.domain.model.commands.UpdateUserCommand;
import com.nrgserver.ergovision.iam.domain.model.entities.Role;
import com.nrgserver.ergovision.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
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

    //update
    public User updateUserDetails(UpdateUserCommand updateUserCommand){
        this.username = updateUserCommand.username();
        this.password = updateUserCommand.password();

        return this;
    }


    public void addRoles(List<Role> roles) {
        var validatedRoleSet = Role.validateRoleSet(roles);
        this.userRoles.addAll(validatedRoleSet);
    }

}
