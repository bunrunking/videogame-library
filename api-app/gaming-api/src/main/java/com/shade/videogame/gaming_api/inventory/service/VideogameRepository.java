package com.shade.videogame.gaming_api.inventory.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.shade.videogame.gaming_api.inventory.model.Videogame;

public interface VideogameRepository extends JpaRepository<Videogame, Integer>, JpaSpecificationExecutor<Videogame> {

}
