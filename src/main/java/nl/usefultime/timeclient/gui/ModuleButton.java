package nl.usefultime.timeclient.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;

public class ModuleButton extends Button {
    public ModuleButton(int x, int y, int width, int height, Component message, OnPress onPress) {
        super(x, y, width, height, message, onPress, DEFAULT_NARRATION);
    }

    @Override
    public void renderWidget(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        int color = isHovered ? 0xFF666666 : 0xFF333333;

        // Draw button background
        fill(poseStack, getX(), getY(), getX() + width, getY() + height, color);

        // Draw button text
        drawCenteredString(
                poseStack,
                Minecraft.getInstance().font,
                getMessage(),
                getX() + width / 2,
                getY() + (height - 8) / 2,
                0xFFFFFF
        );
    }

    public static void drawCenteredString(PoseStack poseStack, net.minecraft.client.gui.Font font, Component text, int x, int y, int color) {
        font.draw(poseStack, text, (float)(x - font.width(text) / 2), (float)y, color);
    }
}
