package mindustry.ios;

import arc.Core;
import arc.Events;
import arc.Input.TextInput;
import arc.backend.robovm.IOSApplication;
import arc.backend.robovm.IOSApplicationConfiguration;
import arc.files.Fi;
import arc.func.Cons;
import arc.scene.ui.layout.Scl;
import arc.util.Log;
import arc.util.io.Streams;
import mindustry.ClientLauncher;
import mindustry.game.EventType.ClientLoadEvent;
import mindustry.game.Saves.SaveSlot;
import mindustry.io.SaveIO;
import mindustry.io.SaveMeta;
import mindustry.net.CrashSender;
import mindustry.ui.Styles;
import org.robovm.apple.coregraphics.CGRect;
import org.robovm.apple.foundation.*;
import org.robovm.apple.uikit.*;
import org.robovm.objc.block.VoidBlock2;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.InflaterInputStream;

import static mindustry.Vars.*;
import static org.robovm.apple.foundation.NSPathUtilities.getDocumentsDirectory;

// warnings for deprecated functions related to multi-window applications are not applicable here
@SuppressWarnings("deprecation")
public class IOSLauncher extends IOSApplication.Delegate {
    private boolean forced;

    @Override
    protected IOSApplication createApplication() {

        if (UIDevice.getCurrentDevice().getUserInterfaceIdiom() == UIUserInterfaceIdiom.Pad) {
            Scl.setAddition(0.5f);
        } else {
            Scl.setAddition(-0.5f);
        }

        return new IOSApplication(
                new ClientLauncher() {

                    @Override
                    public void showFileChooser(
                            boolean open, String titleIgn, String extension, Cons<Fi> cons) {
                        if (!open) { // when exporting, just share it.
                            // ask for export name
                            Core.input.getTextInput(
                                    new TextInput() {
                                        {
                                            title = Core.bundle.get("filename");
                                            accepted =
                                                    name -> {
                                                        try {
                                                            // write result
                                                            Fi result =
                                                                    tmpDirectory.child(
                                                                            name + "." + extension);
                                                            cons.get(result);

                                                            // import the document
                                                            shareFile(result);
                                                        } catch (Throwable t) {
                                                            ui.showException(t);
                                                        }
                                                    };
                                        }
                                    });
                            return;
                        }

                        UIDocumentBrowserViewController cont =
                                new UIDocumentBrowserViewController((NSArray<NSString>) null);

                        NSArray<UIBarButtonItem> arr =
                                new NSArray<>(
                                        new UIBarButtonItem(
                                                Core.bundle.get("cancel"),
                                                UIBarButtonItemStyle.Plain,
                                                uiBarButtonItem ->
                                                        cont.dismissViewController(
                                                                true, () -> {
                                                                })));

                        cont.setAllowsDocumentCreation(false);
                        cont.setAdditionalLeadingNavigationBarButtonItems(arr);

                        class ChooserDelegate extends NSObject
                                implements UIDocumentBrowserViewControllerDelegate {
                            @Override
                            public void didPickDocumentURLs(
                                    UIDocumentBrowserViewController controller,
                                    NSArray<NSURL> documentURLs) {
                            }

                            @Override
                            public void didPickDocumentsAtURLs(
                                    UIDocumentBrowserViewController controller,
                                    NSArray<NSURL> documentURLs) {
                                if (documentURLs.size() < 1) return;

                                NSURL url = documentURLs.first();
                                NSFileCoordinator coord = new NSFileCoordinator(null);
                                url.startAccessingSecurityScopedResource();
                                try {
                                    coord.coordinateReadingItem(
                                            url,
                                            NSFileCoordinatorReadingOptions.ForUploading,
                                            result -> {
                                                Fi src =
                                                        Core.files.absolute(
                                                                result.getAbsoluteURL().getPath());
                                                Fi dst =
                                                        Core.files
                                                                .absolute(getDocumentsDirectory())
                                                                .child(src.name());
                                                src.copyTo(dst);

                                                Core.app.post(
                                                        () -> {
                                                            try {
                                                                cons.get(dst);
                                                            } catch (Throwable t) {
                                                                ui.showException(t);
                                                            }
                                                        });
                                            });
                                } catch (Throwable e) {
                                    ui.showException(e);
                                }

                                url.stopAccessingSecurityScopedResource();

                                cont.dismissViewController(true, () -> {
                                });
                            }

                            @Override
                            public void didRequestDocumentCreationWithHandler(
                                    UIDocumentBrowserViewController controller,
                                    VoidBlock2<NSURL, UIDocumentBrowserImportMode> importHandler) {
                            }

                            @Override
                            public void didImportDocument(
                                    UIDocumentBrowserViewController controller,
                                    NSURL sourceURL,
                                    NSURL destinationURL) {
                            }

                            @Override
                            public void failedToImportDocument(
                                    UIDocumentBrowserViewController controller,
                                    NSURL documentURL,
                                    NSError error) {
                            }

                            @Override
                            public NSArray<UIActivity> applicationActivities(
                                    UIDocumentBrowserViewController controller,
                                    NSArray<NSURL> documentURLs) {
                                return null;
                            }

                            @Override
                            public void willPresentActivityViewController(
                                    UIDocumentBrowserViewController controller,
                                    UIActivityViewController activityViewController) {
                            }
                        }

                        cont.setDelegate(new ChooserDelegate());

                        UIApplication.getSharedApplication()
                                .getKeyWindow()
                                .getRootViewController()
                                .presentViewController(cont, true, () -> {
                                });
                    }

                    @Override
                    public void shareFile(Fi file) {
                        try {
                            Log.info("Attempting to share file " + file);
                            List<Object> list = new ArrayList<>();

                            // better choice?
                            list.add(new NSURL(file.file()));

                            UIActivityViewController p = new UIActivityViewController(list, null);
                            UIViewController rootVc =
                                    UIApplication.getSharedApplication()
                                            .getKeyWindow()
                                            .getRootViewController();
                            if (UIDevice.getCurrentDevice().getUserInterfaceIdiom()
                                    == UIUserInterfaceIdiom.Pad) {
                                // Set up the pop-over for iPad
                                UIPopoverPresentationController pop =
                                        p.getPopoverPresentationController();
                                UIView mainView = rootVc.getView();
                                pop.setSourceView(mainView);
                                CGRect targetRect =
                                        new CGRect(
                                                mainView.getBounds().getMidX(),
                                                mainView.getBounds().getMidY(),
                                                0,
                                                0);
                                pop.setSourceRect(targetRect);
                                pop.setPermittedArrowDirections(UIPopoverArrowDirection.None);
                            }
                            rootVc.presentViewController(
                                    p, true, () -> Log.info("Success! Presented @", file));
                        } catch (Throwable t) {
                            ui.showException(t);
                        }
                    }

                    @Override
                    public void beginForceLandscape() {
                        forced = true;
                        UINavigationController.attemptRotationToDeviceOrientation();
                    }

                    @Override
                    public void endForceLandscape() {
                        forced = false;
                        UINavigationController.attemptRotationToDeviceOrientation();
                    }
                },
                new IOSApplicationConfiguration());
    }

    @Override
    public UIInterfaceOrientationMask getSupportedInterfaceOrientations(
            UIApplication application, UIWindow window) {
        return forced ? UIInterfaceOrientationMask.Landscape : UIInterfaceOrientationMask.All;
    }

    @Override
    public boolean openURL(UIApplication app, NSURL url, UIApplicationOpenURLOptions options) {
        openURL(url);
        return false;
    }

    @Override
    public boolean didFinishLaunching(
            UIApplication application, UIApplicationLaunchOptions options) {
        boolean b = super.didFinishLaunching(application, options);

        if (options != null && options.has(UIApplicationLaunchOptions.Keys.URL())) {
            openURL(((NSURL) options.get(UIApplicationLaunchOptions.Keys.URL())));
        }

        Events.on(
                ClientLoadEvent.class,
                e ->
                        Core.app.post(
                                () ->
                                        Core.app.post(
                                                () ->
                                                        Core.scene.table(
                                                                Styles.black9,
                                                                t -> {
                                                                    t.visible(
                                                                            () -> {
                                                                                if (!forced)
                                                                                    return false;
                                                                                t.toFront();
                                                                                UIInterfaceOrientation
                                                                                        o =
                                                                                        UIApplication
                                                                                                .getSharedApplication()
                                                                                                .getStatusBarOrientation();
                                                                                return forced
                                                                                        && (o
                                                                                        == UIInterfaceOrientation
                                                                                        .Portrait
                                                                                        || o
                                                                                        == UIInterfaceOrientation
                                                                                        .PortraitUpsideDown);
                                                                            });
                                                                    t.add(
                                                                                    "Rotate the device to landscape orientation to use the editor.")
                                                                            .wrap()
                                                                            .grow();
                                                                }))));

        return b;
    }

    void openURL(NSURL url) {

        Core.app.post(
                () ->
                        Core.app.post(
                                () -> {
                                    Fi file =
                                            Core.files
                                                    .absolute(getDocumentsDirectory())
                                                    .child(url.getLastPathComponent());
                                    Core.files.absolute(url.getPath()).copyTo(file);

                                    if (file.extension()
                                            .equalsIgnoreCase(saveExtension)) { // open save

                                        if (SaveIO.isSaveValid(file)) {
                                            try {
                                                SaveMeta meta =
                                                        SaveIO.getMeta(
                                                                new DataInputStream(
                                                                        new InflaterInputStream(
                                                                                file.read(
                                                                                        Streams
                                                                                                .defaultBufferSize))));
                                                if (meta.tags.containsKey("name")) {
                                                    // is map
                                                    if (!ui.editor.isShown()) {
                                                        ui.editor.show();
                                                    }

                                                    ui.editor.beginEditMap(file);
                                                } else {
                                                    SaveSlot slot = control.saves.importSave(file);
                                                    ui.load.runLoadSave(slot);
                                                }
                                            } catch (IOException e) {
                                                ui.showException("@save.import.fail", e);
                                            }
                                        } else {
                                            ui.showErrorMessage("@save.import.invalid");
                                        }
                                    }
                                }));
    }

    public static void main(String[] argv) {
        NSAutoreleasePool pool = new NSAutoreleasePool();
        try {
            UIApplication.main(argv, null, IOSLauncher.class);
        } catch (Throwable t) {
            // attempt to log the exception
            CrashSender.log(t);
            Log.err(t);
            // rethrow the exception so it actually crashes
            throw t;
        }
        pool.close();
    }
}
