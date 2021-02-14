package com.compassouol.backendrecruitment.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "genders")
@Data
public class Gender implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "gender_id")
    private long genderId;

    @Column(name = "gender_description")
    private String genderDescription;
}
