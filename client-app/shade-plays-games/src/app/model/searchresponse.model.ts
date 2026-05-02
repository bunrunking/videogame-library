import { Game } from "./game.model";

export interface SearchResponse {
    results: Game[];
    totalCount: number;
}
