package io.mtk.doc_and_design_pattern.lab2.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "update_status")
public class UpdateStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "update_log", nullable = false, length = 1000)
    private String updateLog;

    @Column(name = "fix_version", nullable = false, length = 15)
    private String fixVersion;

    @OneToOne(mappedBy = "latestUpdateLog")
    private App appWithThisUpdatedLog;

    @ManyToMany(mappedBy = "updateStatusHistory")
    private Set<App> app;

    public void edit(String newUpdateLog) {
        ///
    }
}