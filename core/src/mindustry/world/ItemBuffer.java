package mindustry.world;

import arc.util.Time;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.annotations.Annotations.Struct;
import mindustry.gen.*;
import mindustry.type.Item;

import static mindustry.Vars.content;

public class ItemBuffer {
    private long[] buffer;
    private int index;

    public ItemBuffer(int capacity) {
        this.buffer = new long[capacity];
    }

    public boolean accepts() {
        return index < buffer.length;
    }

    public void accept(Item item, short data) {
        // if(!accepts()) return;
        buffer[index++] = TimeItem.get(data, item.id, Time.time);
    }

    public void accept(Item item) {
        accept(item, (short) -1);
    }

    public Item poll(float speed) {
        if (index > 0) {
            long l = buffer[0];
            float time = TimeItem.time(l);

            if (Time.time >= time + speed || Time.time < time) {
                return content.item(TimeItem.item(l));
            }
        }
        return null;
    }

    public void remove() {
        System.arraycopy(buffer, 1, buffer, 0, index - 1);
        index--;
    }

    public void write(Writes write) {
        write.b((byte) index);
        write.b((byte) buffer.length);
        for (long l : buffer) {
            write.l(l);
        }
    }

    public void read(Reads read) {
        index = read.b();
        byte length = read.b();
        for (int i = 0; i < length; i++) {
            long l = read.l();
            if (i < buffer.length) {
                buffer[i] = l;
            }
        }
        index = Math.min(index, length - 1);
    }

    @Struct
    class TimeItemStruct {
        short data;
        short item;
        float time;
    }
}
