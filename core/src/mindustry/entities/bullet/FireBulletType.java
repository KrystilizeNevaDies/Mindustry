package mindustry.entities.bullet;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.math.Mathf;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.entities.Fires;
import mindustry.gen.*;
import mindustry.graphics.Pal;

public class FireBulletType extends BulletType {
    public Color colorFrom = Pal.lightFlame, colorMid = Pal.darkFlame, colorTo = Color.gray;
    public float radius = 3f;
    public float velMin = 0.6f, velMax = 2.6f;
    public float fireTrailChance = 0.04f;
    public Effect trailEffect2 = Fx.ballfire;
    public float fireEffectChance = 0.1f, fireEffectChance2 = 0.1f;

    {
        pierce = true;
        collidesTiles = false;
        collides = false;
        drag = 0.03f;
        hitEffect = despawnEffect = Fx.none;
        trailEffect = Fx.fireballsmoke;
    }

    public FireBulletType(float speed, float damage) {
        super(speed, damage);
    }

    public FireBulletType() {
    }

    @Override
    public void init(Bullet b) {
        super.init(b);

        b.vel.setLength(Mathf.random(velMin, velMax));
    }

    @Override
    public void draw(Bullet b) {
        Draw.color(colorFrom, colorMid, colorTo, b.fin());
        Fill.circle(b.x, b.y, radius * b.fout());
        Draw.reset();
    }

    @Override
    public void update(Bullet b) {
        super.update(b);

        if (Mathf.chanceDelta(fireTrailChance)) {
            Fires.create(b.tileOn());
        }

        if (Mathf.chanceDelta(fireEffectChance)) {
            trailEffect.at(b.x, b.y);
        }

        if (Mathf.chanceDelta(fireEffectChance2)) {
            trailEffect2.at(b.x, b.y);
        }
    }
}
