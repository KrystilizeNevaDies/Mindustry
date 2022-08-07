package mindustry.maps.generators;

import arc.graphics.Color;
import arc.math.geom.Vec3;
import mindustry.game.Rules;
import mindustry.type.Sector;
import mindustry.world.Tiles;

/**
 * A planet generator that provides no weather, height, color or bases. Override generate().
 */
public class BlankPlanetGenerator extends PlanetGenerator {

    @Override
    public float getHeight(Vec3 position) {
        return 0;
    }

    @Override
    public Color getColor(Vec3 position) {
        return Color.white;
    }

    @Override
    public void generateSector(Sector sector) {
    }

    @Override
    public void addWeather(Sector sector, Rules rules) {
    }

    @Override
    public void generate(Tiles tiles, Sector sec, int seed) {
        this.tiles = tiles;
        this.sector = sec;
        this.rand.setSeed(sec.id + seed + baseSeed);

        tiles.fill();

        generate(tiles);
    }
}
