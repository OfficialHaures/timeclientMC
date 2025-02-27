package nl.usefultime.timeclient.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import nl.usefultime.timeclient.Timeclient;

public class ModuleScreen extends Screen {
    private final String category;
    private final int BUTTON_WIDTH = 120;
    private final int BUTTON_HEIGHT = 20;

    public ModuleScreen(String category) {
        super(Component.literal("TimeClient - " + category));
        this.category = category;
    }

    @Override
    protected void init() {
        int y = 30;

        switch(category) {
            case "Visual":
                addModuleButton("CustomCrosshair", 10, y);
                addModuleButton("CustomSkybox", 10, y += 25);
                addModuleButton("BlockOverlay", 10, y += 25);
                addModuleButton("MotionBlur", 10, y += 25);
                break;

            case "Performance":
                addModuleButton("RAM Display", 10, y);
                addModuleButton("FPS Boost", 10, y += 25);
                addModuleButton("Particles", 10, y += 25);
                break;

            case "HUD":
                addModuleButton("Coordinates", 10, y);
                addModuleButton("FPS Display", 10, y += 25);
                addModuleButton("Ping Display", 10, y += 25);
                addModuleButton("Potion Effects", 10, y += 25);
                break;
        }

        // Back button
        addRenderableWidget(new ModuleButton(width - 70, height - 30, 60, 20,
                Component.literal("Back"), button -> minecraft.setScreen(new TimeClientGui().getScreen())));
    }

    private void addModuleButton(String name, int x, int y) {
        addRenderableWidget(new ModuleButton(x, y, BUTTON_WIDTH, BUTTON_HEIGHT,
                Component.literal(name), button -> toggleModule(name)));
    }

    private void toggleModule(String name) {
        switch(name) {
            case "CustomCrosshair":
                Timeclient.getInstance().getCustomCrosshair().toggle();
                break;
            case "BlockOverlay":
                Timeclient.getInstance().getBlockOverlay().toggle();
                break;
            case "RAM Display":
                Timeclient.getInstance().getRamDisplay().toggle();
                break;
            case "FPS Display":
                Timeclient.getInstance().getFpsDisplay().toggle();
                break;
            case "Ping Display":
                Timeclient.getInstance().getPingDisplay().toggle();
                break;
            case "Potion Effects":
                Timeclient.getInstance().getPotionEffectsHUD().toggle();
                break;
        }
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        renderBackground(poseStack);
        drawCenteredString(poseStack, font, category + " Modules", width / 2, 10, 0xFFFFFF);
        super.render(poseStack, mouseX, mouseY, partialTicks);
    }
}
