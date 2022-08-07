package mindustry.io.versions;

import java.io.DataInput;
import java.io.IOException;

public class Save1 extends LegacySaveVersion {

    public Save1() {
        super(1);
    }

    @Override
    public void readEntities(DataInput stream) throws IOException {
        readLegacyEntities(stream);
    }
}
