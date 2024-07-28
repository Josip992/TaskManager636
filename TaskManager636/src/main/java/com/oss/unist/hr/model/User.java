package com.oss.unist.hr.model;

import com.oss.unist.hr.model.enums.Role;
import lombok.*;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Getter
@Table(name = "MyUsers")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class User {

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_tasks",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "task_id")
    )
    private List<Task> tasks;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column
    private String username = "";

    @Column
    private String email = "";

    @Column
    private String password = "";

    @Column
    private String country = "";

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    public User(UUID id) {
        this.id = id;
    }

    public User(UUID id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }
}
