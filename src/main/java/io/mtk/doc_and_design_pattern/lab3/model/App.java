package io.mtk.doc_and_design_pattern.lab3.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "app")
public class App {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "version", nullable = false)
    private String version;

    @Column(name = "rating", nullable = false)
    private float rating;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "type", nullable = false)
    private Type type;

    @ManyToMany(mappedBy = "publishedApps")
    private Set<Developer> developers;

    @OneToOne
    @JoinColumn(name = "latest_update_log_id", referencedColumnName = "id", nullable = true)
    private UpdateStatus latestUpdateLog;

    @ManyToMany(mappedBy = "installApps")
    private Set<User> usersWhoInstallApp;

    @ManyToMany(mappedBy = "wishedApps")
    private Set<User> usersWhoWishedApp;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinTable(
            name = "update_status_history",
            joinColumns = @JoinColumn(name = "app_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "update_status_id", referencedColumnName = "id", nullable = false)
    )
    private Set<UpdateStatus> updateStatusHistory;
}