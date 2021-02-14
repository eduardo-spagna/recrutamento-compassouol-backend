package com.compassouol.backendrecruitment.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "customers")
@Data
public class Customer implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private long customerId;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "customer_name_normalized")
    private String customerNameNormalized;

    @Column(name = "customer_birthdate")
    private LocalDate customerBirthdate;

    @Column(name = "customer_created_at", updatable = false)
    private LocalDateTime customerCreatedAt = LocalDateTime.now();

    @Column(name = "customer_updated_at")
    private LocalDateTime customerUpdatedAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "gender_id")
    private Gender gender;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;
}
