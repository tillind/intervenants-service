package org.miage.m2.boundary;

import org.miage.m2.entity.Intervenant;
import org.springframework.data.repository.CrudRepository;

public interface IntervenantRessource extends CrudRepository<Intervenant,String> {
    
}

