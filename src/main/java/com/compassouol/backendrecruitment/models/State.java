package com.compassouol.backendrecruitment.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "states")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class State implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "state_id")
    private long stateId;

    @Column(name = "state_name")
    private String stateName;

    @Column(name = "state_name_normalized")
    private String stateNameNormalized;

    @Column(name = "state_short_name")
    private String stateShortName;
}
