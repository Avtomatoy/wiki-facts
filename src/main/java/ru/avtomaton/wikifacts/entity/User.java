package ru.avtomaton.wikifacts.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

/**
 * @author Anton Akkuzin
 */
@Entity
@Table(name = "t_user")
@Getter
@Setter
@NoArgsConstructor
public class User implements UserDetails, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    @Transient
    private String passwordConfirm;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;
    @Enumerated(EnumType.STRING)
    private Fact.Status preferredStatus;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Category> preferredCategories;

    public Role.RoleName getMainRole() {
        boolean admin = false;
        boolean moderator = false;

        for (Role role : roles) {
            if (role.getName().equals(Role.RoleName.ROLE_ADMIN.name())) {
                admin = true;
            } else if (role.getName().equals(Role.RoleName.ROLE_MODERATOR.name())) {
                moderator = true;
            }
        }

        if (admin) {
            return Role.RoleName.ROLE_ADMIN;
        } else if (moderator) {
            return Role.RoleName.ROLE_MODERATOR;
        }

        return Role.RoleName.ROLE_MEMBER;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
