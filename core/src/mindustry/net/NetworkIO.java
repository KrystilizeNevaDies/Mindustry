package mindustry.net;

import arc.Core;
import arc.util.Time;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.core.Version;
import mindustry.ctype.Content;
import mindustry.ctype.ContentType;
import mindustry.ctype.UnlockableContent;
import mindustry.game.Gamemode;
import mindustry.game.Rules;
import mindustry.gen.*;
import mindustry.io.JsonIO;
import mindustry.io.SaveIO;
import mindustry.logic.GlobalVars;
import mindustry.maps.Map;
import mindustry.net.Administration.Config;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.Arrays;

import static mindustry.Vars.*;

public class NetworkIO {

    public static void writeWorld(Player player, OutputStream os) {

        try (DataOutputStream stream = new DataOutputStream(os)) {
            //write all researched content to rules if hosting
            if (state.isCampaign()) {
                state.rules.researched.clear();
                for (ContentType type : ContentType.all) {
                    for (Content c : content.getBy(type)) {
                        if (c instanceof UnlockableContent u && u.unlocked() && u.techNode != null) {
                            state.rules.researched.add(u.name);
                        }
                    }
                }
            }

            stream.writeUTF(JsonIO.write(state.rules));
            SaveIO.getSaveWriter().writeStringMap(stream, state.map.tags);

            stream.writeInt(state.wave);
            stream.writeFloat(state.wavetime);
            stream.writeDouble(state.tick);
            stream.writeLong(GlobalVars.rand.seed0);
            stream.writeLong(GlobalVars.rand.seed1);

            stream.writeInt(player.id);
            player.write(new Writes(stream));

            SaveIO.getSaveWriter().writeContentHeader(stream);
            SaveIO.getSaveWriter().writeMap(stream);
            SaveIO.getSaveWriter().writeTeamBlocks(stream);
            SaveIO.getSaveWriter().writeCustomChunks(stream, true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void loadWorld(InputStream is) {

        try (DataInputStream stream = new DataInputStream(is)) {
            Time.clear();
            state.rules = JsonIO.read(Rules.class, stream.readUTF());
            state.map = new Map(SaveIO.getSaveWriter().readStringMap(stream));

            state.wave = stream.readInt();
            state.wavetime = stream.readFloat();
            state.tick = stream.readDouble();
            GlobalVars.rand.seed0 = stream.readLong();
            GlobalVars.rand.seed1 = stream.readLong();

            Reads read = new Reads(stream);

            Groups.clear();
            int id = stream.readInt();
            player.reset();
            player.read(read);
            player.id = id;
            player.add();

            SaveIO.getSaveWriter().readContentHeader(stream);
            SaveIO.getSaveWriter().readMap(stream, world.context);
            SaveIO.getSaveWriter().readTeamBlocks(stream);
            SaveIO.getSaveWriter().readCustomChunks(stream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            content.setTemporaryMapper(null);
        }
    }

    public static ByteBuffer writeServerData() {
        String name = (headless ? Config.serverName.string() : player.name);
        String description = headless && !Config.desc.string().equals("off") ? Config.desc.string() : "";
        String map = state.map.name();

        ByteBuffer buffer = ByteBuffer.allocate(500);

        writeString(buffer, name, 100);
        writeString(buffer, map, 64);

        buffer.putInt(Core.settings.getInt("totalPlayers", Groups.player.size()));
        buffer.putInt(state.wave);
        buffer.putInt(Version.build);
        writeString(buffer, Version.type);

        buffer.put((byte) state.rules.mode().ordinal());
        buffer.putInt(netServer.admins.getPlayerLimit());

        writeString(buffer, description, 100);
        if (state.rules.modeName != null) {
            writeString(buffer, state.rules.modeName, 50);
        }
        return buffer;
    }

    public static Host readServerData(int ping, String hostAddress, ByteBuffer buffer) {
        String host = readString(buffer);
        String map = readString(buffer);
        int players = buffer.getInt();
        int wave = buffer.getInt();
        int version = buffer.getInt();
        String vertype = readString(buffer);
        Gamemode gamemode = Gamemode.all[buffer.get()];
        int limit = buffer.getInt();
        String description = readString(buffer);
        String modeName = readString(buffer);

        return new Host(ping, host, hostAddress, map, wave, players, version, vertype, gamemode, limit, description, modeName.isEmpty() ? null : modeName);
    }

    private static void writeString(ByteBuffer buffer, String string, int maxlen) {
        byte[] bytes = string.getBytes(charset);
        //todo truncating this way may lead to wierd encoding errors at the ends of strings...
        if (bytes.length > maxlen) {
            bytes = Arrays.copyOfRange(bytes, 0, maxlen);
        }

        buffer.put((byte) bytes.length);
        buffer.put(bytes);
    }

    private static void writeString(ByteBuffer buffer, String string) {
        writeString(buffer, string, 32);
    }

    private static String readString(ByteBuffer buffer) {
        short length = (short) (buffer.get() & 0xff);
        byte[] bytes = new byte[length];
        buffer.get(bytes);
        return new String(bytes, charset);
    }
}
