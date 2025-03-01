package mindustry.entities.abilities;

import arc.graphics.Color;
import arc.util.Time;
import arc.util.Tmp;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.gen.*;

public class MoveEffectAbility extends Ability {
    public float minVelocity = 0.08f;
    public float interval = 3f;
    public float x, y, rotation;
    public boolean rotateEffect = false;
    public float effectParam = 3f;
    public boolean teamColor = false;
    public boolean parentizeEffects;
    public Color color = Color.white;
    public Effect effect = Fx.missileTrail;

    protected float counter;

    public MoveEffectAbility(float x, float y, Color color, Effect effect, float interval) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.effect = effect;
        this.interval = interval;
        display = false;
    }

    public MoveEffectAbility() {
    }

    @Override
    public void update(Unit unit) {
        if (Vars.headless) return;

        counter += Time.delta;
        if (unit.vel.len2() >= minVelocity * minVelocity
                && (counter >= interval)
                && !unit.inFogTo(Vars.player.team())) {
            Tmp.v1.trns(unit.rotation - 90f, x, y);
            counter %= interval;
            effect.at(
                    Tmp.v1.x + unit.x,
                    Tmp.v1.y + unit.y,
                    (rotateEffect ? unit.rotation : effectParam) + rotation,
                    teamColor ? unit.team.color : color,
                    parentizeEffects ? unit : null);
        }
    }
}
