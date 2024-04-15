package io.mtk.doc_and_design_pattern.lab3.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@MappedSuperclass
public abstract class BaseUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false, length = 25)
    private String name;

    @Column(name = "surname", nullable = false, length = 25)
    private String surname;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    public void register(String name, String surname, String email, String password) {
        // TODO:
    }

    public void login(String email, String password) {
        ///
    }

    public void changeLogin(String login) {
        ///
    }

    public void changePassword(String password) {
        ///
    }

    abstract public void updateApp(App app);

    abstract public void deleteApp(App app);
}