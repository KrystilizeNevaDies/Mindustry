import arc.util.Http;
import arc.util.io.Streams;
import mindustry.Vars;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class GenericModTest {

    /**
     * grabs a mod and puts it in the mod folder
     */
    static void grabMod(String url) {
        // clear older mods
        ApplicationTests.testDataFolder.deleteDirectory();
        Http.get(url)
                .error(Assertions::fail)
                .timeout(20000)
                .block(
                        httpResponse -> {
                            try {
                                ApplicationTests.testDataFolder
                                        .child("mods")
                                        .child("test_mod." + (url.endsWith("jar") ? "jar" : "zip"))
                                        .writeBytes(
                                                Streams.copyBytes(
                                                        httpResponse.getResultAsStream()));
                            } catch (IOException e) {
                                Assertions.fail(e);
                            }
                        });

        ApplicationTests.launchApplication(false);
    }

    static void checkExistence(String modName) {
        assertNotEquals(Vars.mods, null);
        assertNotEquals(Vars.mods.list().size, 0, "At least one mod must be loaded.");
        assertEquals(modName, Vars.mods.list().first().name, modName + " must be loaded.");
    }
}
