package mindustry.entities.bullet;

import arc.graphics.Color;
import arc.math.Mathf;
import mindustry.content.Fx;
import mindustry.content.StatusEffects;
import mindustry.entities.Lightning;
import mindustry.gen.*;
import mindustry.graphics.Pal;

public class LightningBulletType extends BulletType {
    public Color lightningColor = Pal.lancerLaser;
    public int lightningLength = 25, lightningLengthRand = 0;

    public LightningBulletType() {
        damage = 1f;
        speed = 0f;
        lifetime = 1;
        despawnEffect = Fx.none;
        hitEffect = Fx.hitLancer;
        keepVelocity = false;
        hittable = false;
        // for stats
        status = StatusEffects.shocked;
    }

    @Override
    protected float calculateRange() {
        return (lightningLength + lightningLengthRand / 2f) * 6f;
    }

    @Override
    public float estimateDPS() {
        return super.estimateDPS() * Math.max(lightningLength / 10f, 1);
    }

    @Override
    public void draw(Bullet b) {
    }

    @Override
    public void init(Bullet b) {
        Lightning.create(
                b,
                lightningColor,
                damage,
                b.x,
                b.y,
                b.rotation(),
                lightningLength + Mathf.random(lightningLengthRand));
    }
}
