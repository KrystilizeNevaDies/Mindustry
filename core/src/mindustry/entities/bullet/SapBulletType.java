package mindustry.entities.bullet;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.math.geom.Position;
import arc.math.geom.Vec2;
import arc.util.Tmp;
import mindustry.content.Fx;
import mindustry.content.StatusEffects;
import mindustry.entities.Damage;
import mindustry.gen.*;
import mindustry.graphics.Drawf;
import mindustry.graphics.Pal;

public class SapBulletType extends BulletType {
    public float length = 100f;
    public float sapStrength = 0.5f;
    public Color color = Color.white.cpy();
    public float width = 0.4f;

    public SapBulletType() {
        speed = 0f;
        despawnEffect = Fx.none;
        pierce = true;
        collides = false;
        hitSize = 0f;
        hittable = false;
        hitEffect = Fx.hitLiquid;
        status = StatusEffects.sapped;
        lightColor = Pal.sap;
        lightOpacity = 0.6f;
        statusDuration = 60f * 3f;
        impact = true;
    }

    @Override
    public void draw(Bullet b) {
        if (b.data instanceof Position data) {
            Tmp.v1.set(data).lerp(b, b.fin());

            Draw.color(color);
            Drawf.laser(Core.atlas.find("laser"), Core.atlas.find("laser-end"),
                    b.x, b.y, Tmp.v1.x, Tmp.v1.y, width * b.fout());

            Draw.reset();

            Drawf.light(b.x, b.y, Tmp.v1.x, Tmp.v1.y, 15f * b.fout(), lightColor, lightOpacity);
        }
    }

    @Override
    public void drawLight(Bullet b) {

    }

    @Override
    protected float calculateRange() {
        return Math.max(length, maxRange);
    }

    @Override
    public void init(Bullet b) {
        super.init(b);

        Healthc target = Damage.linecast(b, b.x, b.y, b.rotation(), length);
        b.data = target;

        if (target != null) {
            float result = Math.max(Math.min(target.health(), damage), 0);

            if (b.owner instanceof Healthc h) {
                h.heal(result * sapStrength);
            }
        }

        if (target instanceof Hitboxc hit) {
            hit.collision(b, hit.x(), hit.y());
            b.collision(hit, hit.x(), hit.y());
        } else if (target instanceof Building tile) {
            if (tile.collide(b)) {
                tile.collision(b);
                hit(b, tile.x, tile.y);
            }
        } else {
            b.data = new Vec2().trns(b.rotation(), length).add(b.x, b.y);
        }
    }
}
