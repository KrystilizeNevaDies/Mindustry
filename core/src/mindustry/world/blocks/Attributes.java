package mindustry.world.blocks;

import arc.util.serialization.Json;
import arc.util.serialization.Json.JsonSerializable;
import arc.util.serialization.JsonValue;
import mindustry.world.meta.Attribute;

import java.util.Arrays;

public class Attributes implements JsonSerializable {
    private float[] arr = new float[Attribute.all.length];

    public void clear() {
        Arrays.fill(arr, 0);
    }

    public float get(Attribute attr) {
        check();
        return arr[attr.id];
    }

    public void set(Attribute attr, float value) {
        check();
        arr[attr.id] = value;
    }

    public void add(Attributes other) {
        check();
        other.check();
        for (int i = 0; i < arr.length; i++) {
            arr[i] += other.arr[i];
        }
    }

    public void add(Attributes other, float scl) {
        check();
        other.check();
        for (int i = 0; i < arr.length; i++) {
            arr[i] += other.arr[i] * scl;
        }
    }

    @Override
    public void write(Json json) {
        check();
        for (Attribute at : Attribute.all) {
            if (arr[at.id] != 0) {
                json.writeValue(at.name, arr[at.id]);
            }
        }
    }

    @Override
    public void read(Json json, JsonValue data) {
        check();
        for (Attribute at : Attribute.all) {
            arr[at.id] = data.getFloat(at.name, 0);
        }
    }

    private void check() {
        if (arr.length != Attribute.all.length) {
            var last = arr;
            arr = new float[Attribute.all.length];
            System.arraycopy(last, 0, arr, 0, Math.min(last.length, arr.length));
        }
    }
}
