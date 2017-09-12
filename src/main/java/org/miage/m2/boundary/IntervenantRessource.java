package org.miage.m2.boundary;

import org.miage.m2.entity.Intervenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel="intervenants")
public interface IntervenantRessource extends JpaRepository<Intervenant,String> {
    // GET, POST, PUT, DELETE sont traitees automatiquement
}