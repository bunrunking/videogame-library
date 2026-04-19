import { Game } from "../model/game.model";

export interface SearchResponse {
    results: Game[];
    totalCount: number;
}
