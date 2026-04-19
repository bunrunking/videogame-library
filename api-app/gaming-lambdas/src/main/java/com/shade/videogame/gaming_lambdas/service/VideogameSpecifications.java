package com.shade.videogame.gaming_lambdas.service;

import org.springframework.data.jpa.domain.Specification;

import com.shade.videogame.gaming_lambdas.model.Videogame;

public class VideogameSpecifications {
    public static Specification<Videogame> hasPlatform(String platform) {
        return (root, query, cb) ->
                platform == null ? null : cb.equal(root.get("platform"), platform);
    }

    public static Specification<Videogame> hasPlayStatus(String playStatus) {
        return (root, query, cb) ->
        		playStatus == null ? null : cb.equal(root.get("playStatus"), playStatus);
    }
}
