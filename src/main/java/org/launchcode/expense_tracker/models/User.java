package org.launchcode.expense_tracker.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "app_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    @Size(max = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @Size(max = 100)
    private String lastName;

    @Column(name = "email", nullable = false)
    @Email
    private String email;

    @Column(nullable = false)
    @Size(max = 100)
    private String username;

    @Column(nullable = false)
    @Size(min = 8, max = 100, message = "Password must be at least 8 characters long")
    private String password;

    @OneToMany(mappedBy = "user")
    private List<Expense> expenses;

}

