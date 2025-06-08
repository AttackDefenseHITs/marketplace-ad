package com.example.springauth.entity;

import com.example.springauth.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(unique = true)
    @Size(min = 1, max = 100, message = "Name must be at least 1 character and no more than 100")
    @NotBlank(message = "User firstname cannot be empty")
    private String nickName;

    @Column(unique = true, nullable = false, length = 30)
    @Size(min = 5, max = 30, message = "Email must be at least 5 character and no more than 30")
    @NotBlank(message = "User email cannot be empty")
    @Email(message = "Invalid email")
    private String email;

    @Column(nullable = false)
    private Date registrationDate = new Date();

    @Column(nullable = false)
    private Date lastUpdateDate = new Date();;

    @Column(unique = true, nullable = false)
    private String phone;

    @Size(min = 8, message = "Password must be at least 8 characters")
    @NotBlank(message = "User password cannot be empty")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return this.nickName;
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
