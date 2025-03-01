package mindustry.world.blocks.defense;

import arc.Core;
import arc.audio.Sound;
import arc.graphics.Blending;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.util.Time;
import mindustry.entities.Lightning;
import mindustry.entities.TargetPriority;
import mindustry.gen.*;
import mindustry.graphics.Pal;
import mindustry.world.Block;
import mindustry.world.meta.BlockGroup;
import mindustry.world.meta.Env;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;

import static mindustry.Vars.state;
import static mindustry.Vars.tilesize;

public class Wall extends Block {
    /**
     * Lighting chance. -1 to disable
     */
    public float lightningChance = -1f;

    public float lightningDamage = 20f;
    public int lightningLength = 17;
    public Color lightningColor = Pal.surge;
    public Sound lightningSound = Sounds.spark;

    /**
     * Bullet deflection chance. -1 to disable
     */
    public float chanceDeflect = -1f;

    public boolean flashHit;
    public Color flashColor = Color.white;
    public Sound deflectSound = Sounds.none;

    public Wall(String name) {
        super(name);
        solid = true;
        destructible = true;
        group = BlockGroup.walls;
        buildCostMultiplier = 6f;
        canOverdrive = false;
        drawDisabled = false;
        crushDamageMultiplier = 5f;
        priority = TargetPriority.wall;

        // it's a wall of course it's supported everywhere
        envEnabled = Env.any;
    }

    @Override
    public void setStats() {
        super.setStats();

        if (chanceDeflect > 0f) stats.add(Stat.baseDeflectChance, chanceDeflect, StatUnit.none);
        if (lightningChance > 0f) {
            stats.add(Stat.lightningChance, lightningChance * 100f, StatUnit.percent);
            stats.add(Stat.lightningDamage, lightningDamage, StatUnit.none);
        }
    }

    @Override
    public TextureRegion[] icons() {
        return new TextureRegion[]{Core.atlas.find(Core.atlas.has(name) ? name : name + "1")};
    }

    public class WallBuild extends Building {
        public float hit;

        @Override
        public void draw() {
            super.draw();

            // draw flashing white overlay if enabled
            if (flashHit) {
                if (hit < 0.0001f) return;

                Draw.color(flashColor);
                Draw.alpha(hit * 0.5f);
                Draw.blend(Blending.additive);
                Fill.rect(x, y, tilesize * size, tilesize * size);
                Draw.blend();
                Draw.reset();

                if (!state.isPaused()) {
                    hit = Mathf.clamp(hit - Time.delta / 10f);
                }
            }
        }

        @Override
        public boolean collision(Bullet bullet) {
            super.collision(bullet);

            hit = 1f;

            // create lightning if necessary
            if (lightningChance > 0f) {
                if (Mathf.chance(lightningChance)) {
                    Lightning.create(
                            team,
                            lightningColor,
                            lightningDamage,
                            x,
                            y,
                            bullet.rotation() + 180f,
                            lightningLength);
                    lightningSound.at(tile, Mathf.random(0.9f, 1.1f));
                }
            }

            // deflect bullets if necessary
            if (chanceDeflect > 0f) {
                // slow bullets are not deflected
                if (bullet.vel.len() <= 0.1f || !bullet.type.reflectable) return true;

                // bullet reflection chance depends on bullet damage
                if (!Mathf.chance(chanceDeflect / bullet.damage())) return true;

                // make sound
                deflectSound.at(tile, Mathf.random(0.9f, 1.1f));

                // translate bullet back to where it was upon collision
                bullet.trns(-bullet.vel.x, -bullet.vel.y);

                float penX = Math.abs(x - bullet.x), penY = Math.abs(y - bullet.y);

                if (penX > penY) {
                    bullet.vel.x *= -1;
                } else {
                    bullet.vel.y *= -1;
                }

                bullet.owner = this;
                bullet.team = team;
                bullet.time += 1f;

                // disable bullet collision by returning false
                return false;
            }

            return true;
        }
    }
}
