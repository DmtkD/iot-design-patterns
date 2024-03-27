package io.mtk.doc_and_design_pattern.lab2.model;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@Entity
@Table(name = "user")
public class User extends BaseUser {
    @Column(name = "username", nullable = false, length = 30)
    private String username;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinTable(
            name = "install_apps",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "app_id", referencedColumnName = "id", nullable = false)
    )
    private Set<App> installApps;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinTable(
            name = "wished_apps",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "app_id", referencedColumnName = "id", nullable = false)
    )
    private Set<App> wishedApps;

    @OneToMany(mappedBy = "user")
    private Set<Review> reviews;

    @Override
    public void updateApp(App app) {

    }

    @Override
    public void deleteApp(App app) {

    }
}