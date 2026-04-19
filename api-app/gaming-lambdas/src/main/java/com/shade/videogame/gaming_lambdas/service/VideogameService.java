package com.shade.videogame.gaming_lambdas.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.shade.videogame.gaming_lambdas.model.Videogame;

@Service
public class VideogameService {
	private final VideogameRepository videogameRepository;
	
	public VideogameService(VideogameRepository videogameRepository) {
		this.videogameRepository = videogameRepository;
	}
	
	public List<Videogame> findAll() {
		return videogameRepository.findAll();
	}

	public Page<Videogame> findAll(Pageable pageable) {
		return videogameRepository.findAll(pageable);
	}

	public Page<Videogame> findByCriteria(String platform, String playStatus,
            Pageable pageable) {

		Specification<Videogame> spec = Specification
				.where(VideogameSpecifications.hasPlatform(platform))
				.and(VideogameSpecifications.hasPlayStatus(playStatus));
		
		return videogameRepository.findAll(spec, pageable);
	}
}
