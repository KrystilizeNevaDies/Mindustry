package mindustry.world.blocks.power;

import arc.Core;
import arc.Events;
import arc.math.Mathf;
import arc.struct.EnumSet;
import arc.util.Strings;
import arc.util.Time;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.content.Fx;
import mindustry.game.EventType.Trigger;
import mindustry.gen.*;
import mindustry.graphics.Pal;
import mindustry.logic.LAccess;
import mindustry.ui.Bar;
import mindustry.world.draw.DrawDefault;
import mindustry.world.draw.DrawMulti;
import mindustry.world.draw.DrawPlasma;
import mindustry.world.draw.DrawRegion;
import mindustry.world.meta.BlockFlag;
import mindustry.world.meta.Env;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;

public class ImpactReactor extends PowerGenerator {
    public final int timerUse = timers++;
    public float warmupSpeed = 0.001f;
    public float itemDuration = 60f;

    public ImpactReactor(String name) {
        super(name);
        hasPower = true;
        hasLiquids = true;
        liquidCapacity = 30f;
        hasItems = true;
        outputsPower = consumesPower = true;
        flags = EnumSet.of(BlockFlag.reactor, BlockFlag.generator);
        lightRadius = 115f;
        emitLight = true;
        envEnabled = Env.any;

        drawer = new DrawMulti(new DrawRegion("-bottom"), new DrawPlasma(), new DrawDefault());

        explosionShake = 6f;
        explosionShakeDuration = 16f;
        explosionDamage = 1900 * 4;
        explodeEffect = Fx.impactReactorExplosion;
        explodeSound = Sounds.explosionbig;
    }

    @Override
    public void setBars() {
        super.setBars();

        addBar(
                "power",
                (GeneratorBuild entity) ->
                        new Bar(
                                () ->
                                        Core.bundle.format(
                                                "bar.poweroutput",
                                                Strings.fixed(
                                                        Math.max(
                                                                entity.getPowerProduction()
                                                                        - consPower.usage,
                                                                0)
                                                                * 60
                                                                * entity.timeScale(),
                                                        1)),
                                () -> Pal.powerBar,
                                () -> entity.productionEfficiency));
    }

    @Override
    public void setStats() {
        super.setStats();

        if (hasItems) {
            stats.add(Stat.productionTime, itemDuration / 60f, StatUnit.seconds);
        }
    }

    public class ImpactReactorBuild extends GeneratorBuild {
        public float warmup, totalProgress;

        @Override
        public void updateTile() {
            if (efficiency >= 0.9999f && power.status >= 0.99f) {
                boolean prevOut = getPowerProduction() <= consPower.requestedPower(this);

                warmup = Mathf.lerpDelta(warmup, 1f, warmupSpeed * timeScale);
                if (Mathf.equal(warmup, 1f, 0.001f)) {
                    warmup = 1f;
                }

                if (!prevOut && (getPowerProduction() > consPower.requestedPower(this))) {
                    Events.fire(Trigger.impactPower);
                }

                if (timer(timerUse, itemDuration / timeScale)) {
                    consume();
                }
            } else {
                warmup = Mathf.lerpDelta(warmup, 0f, 0.01f);
            }

            totalProgress += warmup * Time.delta;

            productionEfficiency = Mathf.pow(warmup, 5f);
        }

        @Override
        public float warmup() {
            return warmup;
        }

        @Override
        public float totalProgress() {
            return totalProgress;
        }

        @Override
        public float ambientVolume() {
            return warmup;
        }

        @Override
        public double sense(LAccess sensor) {
            if (sensor == LAccess.heat) return warmup;
            return super.sense(sensor);
        }

        @Override
        public void createExplosion() {
            if (warmup >= 0.3f) {
                super.createExplosion();
            }
        }

        @Override
        public void write(Writes write) {
            super.write(write);
            write.f(warmup);
        }

        @Override
        public void read(Reads read, byte revision) {
            super.read(read, revision);
            warmup = read.f();
        }
    }
}
