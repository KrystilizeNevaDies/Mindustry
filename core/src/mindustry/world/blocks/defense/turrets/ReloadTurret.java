package mindustry.world.blocks.defense.turrets;

import arc.math.Mathf;
import mindustry.world.consumers.ConsumeLiquidFilter;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatValues;

import static mindustry.Vars.tilesize;

public class ReloadTurret extends BaseTurret {
    public float reload = 10f;

    public ReloadTurret(String name) {
        super(name);
    }

    @Override
    public void setStats() {
        super.setStats();

        if (coolant != null) {
            stats.add(Stat.booster, StatValues.boosters(reload, coolant.amount, coolantMultiplier, true, l -> l.coolant && consumesLiquid(l)));
        }
    }

    public class ReloadTurretBuild extends BaseTurretBuild {
        public float reloadCounter;

        protected void updateCooling() {
            if (reloadCounter < reload && coolant != null && coolant.efficiency(this) > 0 && efficiency > 0) {
                float capacity = coolant instanceof ConsumeLiquidFilter filter ? filter.getConsumed(this).heatCapacity : 1f;
                coolant.update(this);
                reloadCounter += coolant.amount * edelta() * capacity * coolantMultiplier;

                if (Mathf.chance(0.06 * coolant.amount)) {
                    coolEffect.at(x + Mathf.range(size * tilesize / 2f), y + Mathf.range(size * tilesize / 2f));
                }
            }
        }

        protected float baseReloadSpeed() {
            return efficiency;
        }
    }
}
