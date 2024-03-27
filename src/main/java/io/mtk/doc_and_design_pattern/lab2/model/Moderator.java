package io.mtk.doc_and_design_pattern.lab2.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "moderator")
public class Moderator extends BaseUser {
    @Column(name = "moderator_name", nullable = false)
    private String moderatorName;

    @Column(name = "position", nullable = false)
    private String position;

    public void approveApp(App app) {
        ///
    }

    public void blockApp(App app) {
        ///
    }

    @Override
    public void updateApp(App app) {

    }

    @Override
    public void deleteApp(App app) {

    }
}