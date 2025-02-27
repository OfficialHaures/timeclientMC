package nl.usefultime.timeclient.features.hud;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.event.CustomizeGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class CoordinatesHUD {
    private boolean enabled = true;
    private int xPos = 5;
    private int yPos = 5;

    @SubscribeEvent
    public void onRender(CustomizeGuiOverlayEvent event) {
        if (!enabled) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;

        String coords = String.format(
                "XYZ: %.1f / %.1f / %.1f",
                mc.player.getX(),
                mc.player.getY(),
                mc.player.getZ()
        );

        PoseStack poseStack = new PoseStack();
        mc.font.draw(
                poseStack,
                coords,
                xPos,
                yPos,
                0xFFFFFF
        );
    }

    public void toggle() {
        enabled = !enabled;
        Minecraft.getInstance().gui.getChat().addMessage(
                Component.literal("Coordinates " + (enabled ? "§aenabled" : "§cdisabled"))
        );
    }
}
