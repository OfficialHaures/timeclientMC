package nl.usefultime.timeclient.features.hud;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.event.CustomizeGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class PingDisplay {
    private boolean enabled = true;
    private int xPos = 5;
    private int yPos = 15;
    private int color = 0xFFFFFF;

    @SubscribeEvent
    public void onRender(CustomizeGuiOverlayEvent event) {
        if (!enabled) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.getConnection() == null) return;

        int ping = mc.getConnection().getPlayerInfo(mc.player.getUUID()).getLatency();
        String pingText = String.format("Ping: %dms", ping);

        PoseStack poseStack = event.getPoseStack();
        mc.font.draw(
                poseStack,
                pingText,
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
        this.color = color;
    }

    public void toggle() {
        enabled = !enabled;
        Minecraft.getInstance().gui.getChat().addMessage(
                Component.literal("Ping Display " + (enabled ? "§aenabled" : "§cdisabled"))
        );
    }
}
