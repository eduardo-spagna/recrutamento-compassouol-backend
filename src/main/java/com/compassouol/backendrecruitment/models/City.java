package com.compassouol.backendrecruitment.models;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cities")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class City implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "city_id")
    private long cityId;

    @Column(name = "city_name")
    private String cityName;

    @Column(name = "city_name_normalized")
    private String cityNameNormalized;

    @Column(name = "city_created_at", updatable = false)
    private LocalDateTime cityCreatedAt = LocalDateTime.now();

    @Column(name = "city_updated_at")
    private LocalDateTime cityUpdatedAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "state_id")
    private State state;
}
