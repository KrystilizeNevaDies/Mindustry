package mindustry.entities.abilities;

import arc.Core;
import arc.Events;
import arc.graphics.g2d.Draw;
import arc.math.Angles;
import arc.util.Time;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.entities.Units;
import mindustry.game.EventType.UnitCreateEvent;
import mindustry.gen.*;
import mindustry.graphics.Drawf;
import mindustry.type.UnitType;

import static mindustry.Vars.state;

public class UnitSpawnAbility extends Ability {
    public UnitType unit;
    public float spawnTime = 60f, spawnX, spawnY;
    public Effect spawnEffect = Fx.spawn;
    public boolean parentizeEffects;

    protected float timer;

    public UnitSpawnAbility(UnitType unit, float spawnTime, float spawnX, float spawnY) {
        this.unit = unit;
        this.spawnTime = spawnTime;
        this.spawnX = spawnX;
        this.spawnY = spawnY;
    }

    public UnitSpawnAbility() {
    }

    @Override
    public void update(Unit unit) {
        timer += Time.delta * state.rules.unitBuildSpeed(unit.team);

        if (timer >= spawnTime && Units.canCreate(unit.team, this.unit)) {
            float x = unit.x + Angles.trnsx(unit.rotation, spawnY, spawnX),
                    y = unit.y + Angles.trnsy(unit.rotation, spawnY, spawnX);
            spawnEffect.at(x, y, 0f, parentizeEffects ? unit : null);
            Unit u = this.unit.create(unit.team);
            u.set(x, y);
            u.rotation = unit.rotation;
            Events.fire(new UnitCreateEvent(u, null, unit));
            if (!Vars.net.client()) {
                u.add();
            }

            timer = 0f;
        }
    }

    @Override
    public void draw(Unit unit) {
        if (Units.canCreate(unit.team, this.unit)) {
            Draw.draw(
                    Draw.z(),
                    () -> {
                        float x = unit.x + Angles.trnsx(unit.rotation, spawnY, spawnX),
                                y = unit.y + Angles.trnsy(unit.rotation, spawnY, spawnX);
                        Drawf.construct(
                                x,
                                y,
                                this.unit.fullIcon,
                                unit.rotation - 90,
                                timer / spawnTime,
                                1f,
                                timer);
                    });
        }
    }

    @Override
    public String localized() {
        return Core.bundle.format("ability.unitspawn", unit.localizedName);
    }
}
