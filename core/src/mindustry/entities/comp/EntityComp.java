package mindustry.entities.comp;

import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.annotations.Annotations.BaseComponent;
import mindustry.annotations.Annotations.Component;
import mindustry.annotations.Annotations.InternalImpl;
import mindustry.annotations.Annotations.MethodPriority;
import mindustry.entities.EntityGroup;
import mindustry.gen.*;

import static mindustry.Vars.player;

@Component
@BaseComponent
abstract class EntityComp {
    private transient boolean added;
    transient int id = EntityGroup.nextId();

    boolean isAdded() {
        return added;
    }

    void update() {
    }

    void remove() {
        added = false;
    }

    void add() {
        added = true;
    }

    boolean isLocal() {
        return ((Object) this) == player || ((Object) this) instanceof Unitc u && u.controller() == player;
    }

    boolean isRemote() {
        return ((Object) this) instanceof Unitc u && u.isPlayer() && !isLocal();
    }

    boolean isNull() {
        return false;
    }

    /**
     * Replaced with `this` after code generation.
     */
    <T extends Entityc> T self() {
        return (T) this;
    }

    <T> T as() {
        return (T) this;
    }

    @InternalImpl
    abstract int classId();

    @InternalImpl
    abstract boolean serialize();

    @MethodPriority(1)
    void read(Reads read) {
        afterRead();
    }

    void write(Writes write) {

    }

    void afterRead() {

    }
}
