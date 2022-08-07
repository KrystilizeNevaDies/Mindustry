package mindustry.io.versions;

import arc.util.io.CounterInputStream;
import mindustry.io.SaveVersion;
import mindustry.world.WorldContext;

import java.io.DataInputStream;
import java.io.IOException;

import static mindustry.Vars.content;

/**
 * This version does not read custom chunk data (<= 6).
 */
public class LegacyRegionSaveVersion extends SaveVersion {

    public LegacyRegionSaveVersion(int version) {
        super(version);
    }

    @Override
    public void read(DataInputStream stream, CounterInputStream counter, WorldContext context)
            throws IOException {
        region("meta", stream, counter, in -> readMeta(in, context));
        region("content", stream, counter, this::readContentHeader);

        try {
            region("map", stream, counter, in -> readMap(in, context));
            region("entities", stream, counter, this::readEntities);
        } finally {
            content.setTemporaryMapper(null);
        }
    }
}
