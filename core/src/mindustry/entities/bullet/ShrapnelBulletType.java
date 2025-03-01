package mindustry.entities.bullet;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.math.Angles;
import arc.math.Mathf;
import arc.util.Tmp;
import mindustry.content.Fx;
import mindustry.entities.Damage;
import mindustry.gen.*;
import mindustry.graphics.Drawf;
import mindustry.graphics.Pal;

public class ShrapnelBulletType extends BulletType {
    public float length = 100f;
    public float width = 20f;
    public Color fromColor = Color.white, toColor = Pal.lancerLaser;
    public boolean hitLarge = false;

    public int serrations = 7;
    public float serrationLenScl = 10f,
            serrationWidth = 4f,
            serrationSpacing = 8f,
            serrationSpaceOffset = 80f,
            serrationFadeOffset = 0.5f;

    public ShrapnelBulletType() {
        speed = 0f;
        hitEffect = Fx.hitLancer;
        shootEffect = smokeEffect = Fx.lightningShoot;
        lifetime = 10f;
        despawnEffect = Fx.none;
        keepVelocity = false;
        collides = false;
        pierce = true;
        hittable = false;
        absorbable = false;
        lightOpacity = 0.6f;
    }

    @Override
    public void init(Bullet b) {
        super.init(b);

        Damage.collideLaser(b, length, hitLarge, laserAbsorb, pierceCap);
    }

    @Override
    public void init() {
        super.init();

        drawSize = Math.max(drawSize, length * 2f);
    }

    @Override
    protected float calculateRange() {
        return Math.max(length, maxRange);
    }

    @Override
    public void draw(Bullet b) {
        float realLength = b.fdata, rot = b.rotation();

        Draw.color(fromColor, toColor, b.fin());
        for (int i = 0; i < (int) (serrations * realLength / length); i++) {
            Tmp.v1.trns(rot, i * serrationSpacing);
            float sl =
                    Mathf.clamp(b.fout() - serrationFadeOffset)
                            * (serrationSpaceOffset - i * serrationLenScl);
            Drawf.tri(b.x + Tmp.v1.x, b.y + Tmp.v1.y, serrationWidth, sl, b.rotation() + 90);
            Drawf.tri(b.x + Tmp.v1.x, b.y + Tmp.v1.y, serrationWidth, sl, b.rotation() - 90);
        }
        Drawf.tri(b.x, b.y, width * b.fout(), (realLength + 50), b.rotation());
        Drawf.tri(b.x, b.y, width * b.fout(), 10f, b.rotation() + 180f);
        Draw.reset();

        Drawf.light(
                b.x,
                b.y,
                b.x + Angles.trnsx(rot, realLength),
                b.y + Angles.trnsy(rot, realLength),
                width * 2.5f * b.fout(),
                toColor,
                lightOpacity);
    }
}
