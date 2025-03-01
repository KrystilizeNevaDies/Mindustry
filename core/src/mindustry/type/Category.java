package mindustry.type;

public enum Category {
    /**
     * Offensive turrets.
     */
    turret,
    /**
     * Blocks that produce raw resources, such as drills.
     */
    production,
    /**
     * Blocks that move items around.
     */
    distribution,
    /**
     * Blocks that move liquids around.
     */
    liquid,
    /**
     * Blocks that generate or transport power.
     */
    power,
    /**
     * Walls and other defensive structures.
     */
    defense,
    /**
     * Blocks that craft things.
     */
    crafting,
    /**
     * Blocks that create units.
     */
    units,
    /**
     * Things for storage or passive effects.
     */
    effect,
    /**
     * Blocks related to logic.
     */
    logic;

    public static final Category[] all = values();

    public Category prev() {
        return all[(ordinal() - 1 + all.length) % all.length];
    }

    public Category next() {
        return all[(ordinal() + 1) % all.length];
    }
}
