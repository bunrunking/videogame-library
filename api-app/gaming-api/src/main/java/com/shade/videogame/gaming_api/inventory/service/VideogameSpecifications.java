package com.shade.videogame.gaming_api.inventory.service;

import org.springframework.data.jpa.domain.Specification;

import com.shade.videogame.gaming_api.inventory.model.Videogame;

public class VideogameSpecifications {
    public static Specification<Videogame> hasPlatform(String platform) {
        return (root, query, cb) ->
                platform == null ? null : cb.equal(root.get("platform"), platform);
    }

    public static Specification<Videogame> hasPlayStatus(String playStatus) {
        return (root, query, cb) ->
        		playStatus == null ? null : cb.equal(root.get("playStatus"), playStatus);
    }

    public static Specification<Videogame> hasTitle(String title) {
        return (root, query, cb) ->
            (title == null || title.isBlank())
                ? null
                : cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%");
    }
}
