package mindustry.world.blocks.environment;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.math.geom.Point2;
import mindustry.annotations.Annotations.Load;
import mindustry.graphics.CacheLayer;
import mindustry.world.Tile;

import static mindustry.Vars.world;

public class StaticWall extends Prop {
    public @Load("@-large") TextureRegion large;
    public TextureRegion[][] split;

    public StaticWall(String name) {
        super(name);
        breakable = alwaysReplace = false;
        solid = true;
        variants = 2;
        cacheLayer = CacheLayer.walls;
    }

    @Override
    public void drawBase(Tile tile) {
        int rx = tile.x / 2 * 2;
        int ry = tile.y / 2 * 2;

        if (Core.atlas.isFound(large)
                && eq(rx, ry)
                && Mathf.randomSeed(Point2.pack(rx, ry)) < 0.5) {
            Draw.rect(split[tile.x % 2][1 - tile.y % 2], tile.worldx(), tile.worldy());
        } else if (variants > 0) {
            Draw.rect(
                    variantRegions[
                            Mathf.randomSeed(
                                    tile.pos(), 0, Math.max(0, variantRegions.length - 1))],
                    tile.worldx(),
                    tile.worldy());
        } else {
            Draw.rect(region, tile.worldx(), tile.worldy());
        }

        // draw ore on top
        if (tile.overlay().wallOre) {
            tile.overlay().drawBase(tile);
        }
    }

    @Override
    public void load() {
        super.load();
        split = large.split(32, 32);
    }

    boolean eq(int rx, int ry) {
        return rx < world.width() - 1
                && ry < world.height() - 1
                && world.tile(rx + 1, ry).block() == this
                && world.tile(rx, ry + 1).block() == this
                && world.tile(rx, ry).block() == this
                && world.tile(rx + 1, ry + 1).block() == this;
    }
}
