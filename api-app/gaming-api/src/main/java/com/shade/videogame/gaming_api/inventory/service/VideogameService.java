package com.shade.videogame.gaming_api.inventory.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.shade.videogame.gaming_api.inventory.model.Platform;
import com.shade.videogame.gaming_api.inventory.model.PlayStatus;
import com.shade.videogame.gaming_api.inventory.model.Videogame;

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

	public Page<Videogame> findByCriteria(Platform platform, PlayStatus playStatus, String title,
            Pageable pageable) {
		return findByCriteria(
				platform != null ? platform.getDescription() : null, 
				playStatus != null ? playStatus.getDescription() : null,
				title,
				pageable);
	}

	public Page<Videogame> findByCriteria(String platform, String playStatus, String title,
            Pageable pageable) {
		
		platform = platform == null || Platform.UNKNOWN.getDescription().equalsIgnoreCase(platform) ? null : platform;
		
		String platformSearch = platform != null ? Platform.fromDescription(platform).name() : platform;
		String playStatusSearch = playStatus != null ? PlayStatus.fromDescription(playStatus).name() : playStatus;

		Specification<Videogame> spec = Specification
				.where(VideogameSpecifications.hasPlatform(platformSearch))
				.and(VideogameSpecifications.hasPlayStatus(playStatusSearch))
				.and(VideogameSpecifications.hasTitle(title));
		
		return videogameRepository.findAll(spec, pageable);
	}
}
