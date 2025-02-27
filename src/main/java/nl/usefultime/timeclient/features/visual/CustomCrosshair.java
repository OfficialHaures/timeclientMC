package nl.usefultime.timeclient.features.visual;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.event.CustomizeGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class CustomCrosshair {
    private boolean enabled = false;
    private int color = 0xFFFFFF;
    private int size = 5;
    private boolean dot = true;
    private boolean outline = true;

    @SubscribeEvent
    public void onRender(CustomizeGuiOverlayEvent event) {
        if (!enabled) return;

        Minecraft mc = Minecraft.getInstance();
        int screenWidth = mc.getWindow().getGuiScaledWidth();
        int screenHeight = mc.getWindow().getGuiScaledHeight();
        int centerX = screenWidth / 2;
        int centerY = screenHeight / 2;

        PoseStack poseStack = new PoseStack();

        if (outline) {
            drawRect(poseStack, centerX - size - 1, centerY - 1, centerX + size + 1, centerY + 1, 0xFF000000);
            drawRect(poseStack, centerX - 1, centerY - size - 1, centerX + 1, centerY + size + 1, 0xFF000000);
        }

        drawRect(poseStack, centerX - size, centerY, centerX + size, centerY + 1, color);
        drawRect(poseStack, centerX, centerY - size, centerX + 1, centerY + size, color);

        if (dot) {
            drawRect(poseStack, centerX, centerY, centerX + 1, centerY + 1, color);
        }
    }

    private void drawRect(PoseStack poseStack, int left, int top, int right, int bottom, int color) {
        float alpha = (color >> 24 & 255) / 255.0F;
        float red = (color >> 16 & 255) / 255.0F;
        float green = (color >> 8 & 255) / 255.0F;
        float blue = (color & 255) / 255.0F;

        poseStack.pushPose();
        poseStack.translate(0, 0, 0);
        poseStack.scale(1, 1, 1);
        fill(poseStack, left, top, right, bottom, red, green, blue, alpha);
        poseStack.popPose();
    }

    private void fill(PoseStack poseStack, int left, int top, int right, int bottom, float red, float green, float blue, float alpha) {
        if (left < right) {
            int i = left;
            left = right;
            right = i;
        }
        if (top < bottom) {
            int j = top;
            top = bottom;
            bottom = j;
        }
    }

    public void setColor(int newColor) {
        this.color = newColor;
    }

    public void toggle() {
        enabled = !enabled;
        Minecraft.getInstance().gui.getChat().addMessage(
                Component.literal("CustomCrosshair " + (enabled ? "§aenabled" : "§cdisabled"))
        );
    }
}
