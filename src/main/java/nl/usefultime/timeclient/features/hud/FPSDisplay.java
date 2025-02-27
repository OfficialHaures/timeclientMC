package nl.usefultime.timeclient.features.hud;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.event.CustomizeGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class FPSDisplay {
    private boolean enabled = true;
    private int xPos = 5;
    private int yPos = 5;
    private int color = 0xFFFFFF;

    @SubscribeEvent
    public void onRender(CustomizeGuiOverlayEvent event) {
        if (!enabled) return;

        String fps = String.format("FPS: %d", Minecraft.getInstance().getFps());
        PoseStack poseStack = new PoseStack();

        Minecraft.getInstance().font.draw(
                poseStack,
                fps,
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
                Component.literal("FPS Display " + (enabled ? "§aenabled" : "§cdisabled"))
        );
    }
}
