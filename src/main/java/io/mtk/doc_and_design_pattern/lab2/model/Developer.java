package io.mtk.doc_and_design_pattern.lab2.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
@Table(name = "developer")
public class Developer extends BaseUser {
    @Column(name = "name_of_company", nullable = false, length = 50)
    private String nameOfCompany;

    @Column(name = "bank_card", nullable = false, length = 19)
    private String bankCard;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinTable(
            name = "published_apps",
            joinColumns = @JoinColumn(name = "developer_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "app_id", referencedColumnName = "id", nullable = false)
    )
    private Set<App> publishedApps;

    public void publishAppToStore(App app) {
        ///
    }

    public float getStatistics(App app) {
        return 0;
    }

    private UpdateStatus create_update_log(String textLog) {
        return null;
    }

    @Override
    public void updateApp(App app) {
        ///
    }

    @Override
    public void deleteApp(App app) {
        ///
    }
}