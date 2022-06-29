package com.perscholas.astrodia.models;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String firstName;
    String lastName;
    String email;
    @Column(length=60)
    String password;
    String role;
    boolean enabled = false;


    public User(@NonNull String firstName, @NonNull String lastName, @NonNull String email, @NonNull String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return email.equals(user.email) && password.equals(user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password);
    }

}
