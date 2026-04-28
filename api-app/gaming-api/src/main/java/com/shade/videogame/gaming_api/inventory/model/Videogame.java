package com.shade.videogame.gaming_api.inventory.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "\"Videogame\"")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Videogame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;
    
    private String title;
    
    @Enumerated(EnumType.STRING)
    private Platform platform;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "play_status")
    private PlayStatus playStatus; 
    
    @Enumerated(EnumType.STRING)
    @Column(name = "list_type")
    private ListType listType; 
    
    private Integer rating;
}
