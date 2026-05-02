import { PlatformWrapper } from "./platformwrapper.model";

export interface Game {
    id: number
    name: string;
    imageUrl: string;
    platforms: PlatformWrapper[];
}