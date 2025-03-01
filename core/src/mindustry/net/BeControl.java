package mindustry.net;

import arc.Core;
import arc.files.Fi;
import arc.func.*;
import arc.util.*;
import arc.util.serialization.Jval;
import mindustry.Vars;
import mindustry.core.Version;
import mindustry.gen.*;
import mindustry.graphics.Pal;
import mindustry.io.SaveIO;
import mindustry.net.Administration.Config;
import mindustry.net.Packets.KickReason;
import mindustry.ui.Bar;
import mindustry.ui.dialogs.BaseDialog;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static mindustry.Vars.*;

/**
 * Handles control of bleeding edge builds.
 */
public class BeControl {
    private static final int updateInterval = 60;

    private boolean checkUpdates = true;
    private boolean updateAvailable;
    private String updateUrl;
    private int updateBuild;

    /**
     * @return whether this is a bleeding edge build.
     */
    public boolean active() {
        return Version.type.equals("bleeding-edge");
    }

    public BeControl() {
        if (active()) {
            Timer.schedule(
                    () -> {
                        if ((Vars.clientLoaded || headless) && checkUpdates && !mobile) {
                            checkUpdate(t -> {
                            });
                        }
                    },
                    updateInterval,
                    updateInterval);
        }

        if (OS.hasProp("becopy")) {
            try {
                Fi dest = Fi.get(OS.prop("becopy"));
                Fi self =
                        Fi.get(
                                BeControl.class
                                        .getProtectionDomain()
                                        .getCodeSource()
                                        .getLocation()
                                        .toURI()
                                        .getPath());

                for (Fi file : self.parent().findAll(f -> !f.equals(self))) file.delete();

                self.copyTo(dest);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * asynchronously checks for updates.
     */
    public void checkUpdate(Boolc done) {
        Http.get("https://api.github.com/repos/Anuken/MindustryBuilds/releases/latest")
                .error(
                        e -> {
                            // don't log the error, as it would clog output if there is no internet.
                            // make sure
                            // it's handled to prevent infinite loading.
                            done.get(false);
                        })
                .submit(
                        res -> {
                            Jval val = Jval.read(res.getResultAsString());
                            int newBuild = Strings.parseInt(val.getString("tag_name", "0"));
                            if (newBuild > Version.build) {
                                Jval asset =
                                        val.get("assets")
                                                .asArray()
                                                .find(
                                                        v ->
                                                                v.getString("name", "")
                                                                        .startsWith(
                                                                                headless
                                                                                        ? "Mindustry-BE-Server"
                                                                                        : "Mindustry-BE-Desktop"));
                                String url = asset.getString("browser_download_url", "");
                                updateAvailable = true;
                                updateBuild = newBuild;
                                updateUrl = url;
                                Core.app.post(
                                        () -> {
                                            showUpdateDialog();
                                            done.get(true);
                                        });
                            } else {
                                Core.app.post(() -> done.get(false));
                            }
                        });
    }

    /**
     * @return whether a new update is available
     */
    public boolean isUpdateAvailable() {
        return updateAvailable;
    }

    /**
     * shows the dialog for updating the game on desktop, or a prompt for doing so on the server
     */
    public void showUpdateDialog() {
        if (!updateAvailable) return;

        if (!headless) {
            checkUpdates = false;
            ui.showCustomConfirm(
                    Core.bundle.format("be.update", "") + " " + updateBuild,
                    "@be.update.confirm",
                    "@ok",
                    "@be.ignore",
                    () -> {
                        try {
                            boolean[] cancel = {false};
                            float[] progress = {0};
                            int[] length = {0};
                            Fi file = bebuildDirectory.child("client-be-" + updateBuild + ".jar");
                            Fi fileDest =
                                    OS.hasProp("becopy")
                                            ? Fi.get(OS.prop("becopy"))
                                            : Fi.get(
                                            BeControl.class
                                                    .getProtectionDomain()
                                                    .getCodeSource()
                                                    .getLocation()
                                                    .toURI()
                                                    .getPath());

                            BaseDialog dialog = new BaseDialog("@be.updating");
                            download(
                                    updateUrl,
                                    file,
                                    i -> length[0] = i,
                                    v -> progress[0] = v,
                                    () -> cancel[0],
                                    () -> {
                                        try {
                                            Runtime.getRuntime()
                                                    .exec(
                                                            OS.isMac
                                                                    ? new String[]{
                                                                    javaPath,
                                                                    "-XstartOnFirstThread",
                                                                    "-DlastBuild="
                                                                            + Version.build,
                                                                    "-Dberestart",
                                                                    "-Dbecopy="
                                                                            + fileDest
                                                                            .absolutePath(),
                                                                    "-jar",
                                                                    file.absolutePath()
                                                            }
                                                                    : new String[]{
                                                                    javaPath,
                                                                    "-DlastBuild="
                                                                            + Version.build,
                                                                    "-Dberestart",
                                                                    "-Dbecopy="
                                                                            + fileDest
                                                                            .absolutePath(),
                                                                    "-jar",
                                                                    file.absolutePath()
                                                            });
                                            System.exit(0);
                                        } catch (IOException e) {
                                            ui.showException(e);
                                        }
                                    },
                                    e -> {
                                        dialog.hide();
                                        ui.showException(e);
                                    });

                            dialog.cont
                                    .add(
                                            new Bar(
                                                    () ->
                                                            length[0] == 0
                                                                    ? Core.bundle.get("be.updating")
                                                                    : (int)
                                                                    (progress[
                                                                            0]
                                                                            * length[
                                                                            0])
                                                                    / 1024
                                                                    / 1024
                                                                    + "/"
                                                                    + length[0] / 1024
                                                                    / 1024
                                                                    + " MB",
                                                    () -> Pal.accent,
                                                    () -> progress[0]))
                                    .width(400f)
                                    .height(70f);
                            dialog.buttons
                                    .button(
                                            "@cancel",
                                            Icon.cancel,
                                            () -> {
                                                cancel[0] = true;
                                                dialog.hide();
                                            })
                                    .size(210f, 64f);
                            dialog.setFillParent(false);
                            dialog.show();
                        } catch (Exception e) {
                            ui.showException(e);
                        }
                    },
                    () -> checkUpdates = false);
        } else {
            Log.info("&lcA new update is available: &lyBleeding Edge build @", updateBuild);
            if (Config.autoUpdate.bool()) {
                Log.info("&lcAuto-downloading next version...");

                try {
                    // download new file from github
                    Fi source =
                            Fi.get(
                                    BeControl.class
                                            .getProtectionDomain()
                                            .getCodeSource()
                                            .getLocation()
                                            .toURI()
                                            .getPath());
                    Fi dest = source.sibling("server-be-" + updateBuild + ".jar");

                    download(
                            updateUrl,
                            dest,
                            len ->
                                    Core.app.post(
                                            () ->
                                                    Log.info(
                                                            "&ly| Size: @ MB.",
                                                            Strings.fixed(
                                                                    (float) len / 1024 / 1024, 2))),
                            progress -> {
                            },
                            () -> false,
                            () ->
                                    Core.app.post(
                                            () -> {
                                                Log.info("&lcSaving...");
                                                SaveIO.save(
                                                        saveDirectory.child(
                                                                "autosavebe." + saveExtension));
                                                Log.info("&lcAutosaved.");

                                                netServer.kickAll(KickReason.serverRestarting);
                                                Threads.sleep(32);

                                                Log.info(
                                                        "&lcVersion downloaded, exiting. Note that if you are not using a auto-restart script, the server will not restart automatically.");
                                                // replace old file with new
                                                dest.copyTo(source);
                                                dest.delete();
                                                System.exit(
                                                        2); // this will cause a restart if using
                                                // the script
                                            }),
                            Throwable::printStackTrace);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            checkUpdates = false;
        }
    }

    private void download(
            String furl,
            Fi dest,
            Intc length,
            Floatc progressor,
            Boolp canceled,
            Runnable done,
            Cons<Throwable> error) {
        mainExecutor.submit(
                () -> {
                    try {
                        HttpURLConnection con = (HttpURLConnection) new URL(furl).openConnection();
                        BufferedInputStream in = new BufferedInputStream(con.getInputStream());
                        OutputStream out = dest.write(false, 4096);

                        byte[] data = new byte[4096];
                        long size = con.getContentLength();
                        long counter = 0;
                        length.get((int) size);
                        int x;
                        while ((x = in.read(data, 0, data.length)) >= 0 && !canceled.get()) {
                            counter += x;
                            progressor.get((float) counter / (float) size);
                            out.write(data, 0, x);
                        }
                        out.close();
                        in.close();
                        if (!canceled.get()) done.run();
                    } catch (Throwable e) {
                        error.get(e);
                    }
                });
    }
}
