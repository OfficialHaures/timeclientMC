package nl.usefultime.timeclient.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import nl.usefultime.timeclient.Timeclient;

public class TimeClientGui extends Screen {
    private final int BUTTON_WIDTH = 120;
    private final int BUTTON_HEIGHT = 20;

    public TimeClientGui() {
        super(Component.literal("TimeClient"));
    }

    @Override
    protected void init() {
        // Categories
        addCategory("Visual", 10, 30);
        addCategory("Performance", 140, 30);
        addCategory("HUD", 270, 30);
    }

    private void addCategory(String name, int x, int y) {
        addRenderableWidget(new ModuleButton(x, y, BUTTON_WIDTH, BUTTON_HEIGHT,
                Component.literal(name),
                button -> minecraft.setScreen(new ModuleScreen(name)))
        );
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        renderBackground(poseStack);
        drawCenteredString(
                poseStack,
                font,
                "TimeClient v" + Timeclient.getInstance().getVersion(),
                width / 2,
                10,
                0xFFFFFF
        );
        super.render(poseStack, mouseX, mouseY, partialTicks);
    }

    public Screen getScreen() {
        return new TimeClientGui();
    }
}
