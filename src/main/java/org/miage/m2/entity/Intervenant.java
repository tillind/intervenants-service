package org.miage.m2.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor   // obligatoire pour JPA
@Data
public class Intervenant implements Serializable {

    @Id
    private String id;
    private String nom;
    private String prenom;
    private String commune;
    private String codepostal;

}
