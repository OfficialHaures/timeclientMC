package nl.usefultime.timeclient.features;

import net.minecraft.client.Minecraft;
import net.minecraft.client.Screenshot;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.event.ScreenshotEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ScreenshotManager {
    private boolean enabled = true;
    private File screenshotDir;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");
    private List<String> recentScreenshots = new ArrayList<>();

    public ScreenshotManager() {
        screenshotDir = new File(Minecraft.getInstance().gameDirectory, "screenshots");
        if (!screenshotDir.exists()) {
            screenshotDir.mkdir();
        }
    }

    @SubscribeEvent
    public void onScreenshot(ScreenshotEvent event) {
        if (!enabled) return;

        String fileName = dateFormat.format(new Date()) + ".png";
        File screenshot = new File(screenshotDir, fileName);

        event.setScreenshotFile(screenshot);
        recentScreenshots.add(screenshot.getAbsolutePath());

        if (recentScreenshots.size() > 50) {
            recentScreenshots.remove(0);
        }
    }

        public void takeScreenshot() {
            String fileName = dateFormat.format(new Date()) + ".png";
            File screenshotFile = new File(screenshotDir, fileName);

            Screenshot.grab(
                    Minecraft.getInstance().gameDirectory,
                    fileName,
                    Minecraft.getInstance().getMainRenderTarget(),
                    (message) -> {
                        Minecraft.getInstance().execute(() -> {
                            Minecraft.getInstance().gui.getChat().addMessage(message);
                        });
                    }
            );
        }



    public List<String> getRecentScreenshots() {
        return recentScreenshots;
    }

    public void openScreenshotFolder() {
        try {
            Runtime.getRuntime().exec("explorer.exe /select," + screenshotDir.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void toggle() {
        enabled = !enabled;
        Minecraft.getInstance().gui.getChat().addMessage(
                Component.literal("ScreenshotManager " + (enabled ? "§aenabled" : "§cdisabled"))
        );
    }
}
