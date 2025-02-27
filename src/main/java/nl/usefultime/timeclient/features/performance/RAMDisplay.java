package nl.usefultime.timeclient.features.performance;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.event.CustomizeGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class RAMDisplay {
    private boolean enabled = true;
    private int xPos = 5;
    private int yPos = 25;
    private int color = 0xFFFFFF;

    @SubscribeEvent
    public void onRender(CustomizeGuiOverlayEvent event) {
        if (!enabled) return;

        Runtime runtime = Runtime.getRuntime();
        long usedMemory = (runtime.totalMemory() - runtime.freeMemory()) / 1024 / 1024;
        long totalMemory = runtime.totalMemory() / 1024 / 1024;
        long maxMemory = runtime.maxMemory() / 1024 / 1024;

        String memoryInfo = String.format("RAM: %dMB/%dMB (Max: %dMB)", usedMemory, totalMemory, maxMemory);

        PoseStack poseStack = new PoseStack();
        Minecraft.getInstance().font.draw(
                poseStack,
                memoryInfo,
                xPos,
                yPos,
                color
        );
    }

    public void setPosition(int x, int y) {
        this.xPos = x;
        this.yPos = y;
    }

    public void setColor(int newColor) {
        this.color = newColor;
    }

    public void toggle() {
        enabled = !enabled;
        Minecraft.getInstance().gui.getChat().addMessage(
                Component.literal("RAM Display " + (enabled ? "§aenabled" : "§cdisabled"))
        );
    }
}
