package mindustry.entities.abilities;

import arc.Core;
import arc.audio.Sound;
import arc.graphics.Blending;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Angles;
import arc.math.Mathf;
import arc.util.Nullable;
import arc.util.Time;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.entities.Lightning;
import mindustry.entities.bullet.BulletType;
import mindustry.gen.*;

public class MoveLightningAbility extends Ability {
    /**
     * Lightning damage
     */
    public float damage = 35f;
    /**
     * Chance of firing every tick. Set >= 1 to always fire lightning every tick at max speed
     */
    public float chance = 0.15f;
    /**
     * Length of the lightning. <= 0 to disable
     */
    public int length = 12;
    /**
     * Speeds for when to start lightninging and when to stop getting faster
     */
    public float minSpeed = 0.8f, maxSpeed = 1.2f;
    /**
     * Lightning color
     */
    public Color color = Color.valueOf("a9d8ff");
    /**
     * Shifts where the lightning spawns along the Y axis
     */
    public float y = 0f;
    /**
     * Offset along the X axis
     */
    public float x = 0f;
    /**
     * Whether the spawn side alternates
     */
    public boolean alternate = true;
    /**
     * Jittering heat sprite like the shield on v5 Javelin
     */
    public String heatRegion = "error";
    /**
     * Bullet type that is fired. Can be null
     */
    public @Nullable BulletType bullet;
    /**
     * Bullet angle parameters
     */
    public float bulletAngle = 0f, bulletSpread = 0f;

    public Effect shootEffect = Fx.sparkShoot;
    public boolean parentizeEffects;
    public Sound shootSound = Sounds.spark;

    protected float side = 1f;

    MoveLightningAbility() {
    }

    public MoveLightningAbility(
            float damage,
            int length,
            float chance,
            float y,
            float minSpeed,
            float maxSpeed,
            Color color,
            String heatRegion) {
        this.damage = damage;
        this.length = length;
        this.chance = chance;
        this.y = y;
        this.minSpeed = minSpeed;
        this.maxSpeed = maxSpeed;
        this.color = color;
        this.heatRegion = heatRegion;
    }

    public MoveLightningAbility(
            float damage,
            int length,
            float chance,
            float y,
            float minSpeed,
            float maxSpeed,
            Color color) {
        this.damage = damage;
        this.length = length;
        this.chance = chance;
        this.y = y;
        this.minSpeed = minSpeed;
        this.maxSpeed = maxSpeed;
        this.color = color;
    }

    @Override
    public void update(Unit unit) {
        float scl = Mathf.clamp((unit.vel().len() - minSpeed) / (maxSpeed - minSpeed));
        if (Mathf.chance(Time.delta * chance * scl)) {
            float x = unit.x + Angles.trnsx(unit.rotation, this.y, this.x * side),
                    y = unit.y + Angles.trnsy(unit.rotation, this.y, this.x * side);

            shootEffect.at(x, y, unit.rotation, color, parentizeEffects ? unit : null);
            shootSound.at(x, y);

            if (length > 0) {
                Lightning.create(
                        unit.team,
                        color,
                        damage,
                        x + unit.vel.x,
                        y + unit.vel.y,
                        unit.rotation,
                        length);
            }

            if (bullet != null) {
                bullet.create(
                        unit,
                        unit.team,
                        x,
                        y,
                        unit.rotation + bulletAngle + Mathf.range(bulletSpread));
            }

            if (alternate) side *= -1f;
        }
    }

    @Override
    public void draw(Unit unit) {
        float scl = Mathf.clamp((unit.vel().len() - minSpeed) / (maxSpeed - minSpeed));
        TextureRegion region = Core.atlas.find(heatRegion);
        if (Core.atlas.isFound(region) && scl > 0.00001f) {
            Draw.color(color);
            Draw.alpha(scl / 2f);
            Draw.blend(Blending.additive);
            Draw.rect(
                    region,
                    unit.x + Mathf.range(scl / 2f),
                    unit.y + Mathf.range(scl / 2f),
                    unit.rotation - 90);
            Draw.blend();
        }
    }
}
