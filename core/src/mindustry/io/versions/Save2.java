package mindustry.io.versions;

import java.io.DataInput;
import java.io.IOException;

public class Save2 extends LegacySaveVersion {

    public Save2() {
        super(2);
    }

    @Override
    public void readEntities(DataInput stream) throws IOException {
        readLegacyEntities(stream);
    }
}
