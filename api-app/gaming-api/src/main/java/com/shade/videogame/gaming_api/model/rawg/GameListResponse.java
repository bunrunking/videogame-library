package com.shade.videogame.gaming_api.model.rawg;
import java.util.List;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class GameListResponse {
    private int count;
    private List<Game> results;
}
