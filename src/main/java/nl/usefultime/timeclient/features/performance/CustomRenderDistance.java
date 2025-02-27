package nl.usefultime.timeclient.features.performance;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public class CustomRenderDistance {
    private boolean enabled = false;
    private int renderDistance = 8;
    private int defaultRenderDistance;

    public CustomRenderDistance() {
        defaultRenderDistance = Minecraft.getInstance().options.renderDistance().get();
    }

    public void setRenderDistance(int chunks) {
        if (!enabled) return;

        this.renderDistance = Math.max(2, Math.min(32, chunks));
        Minecraft.getInstance().options.renderDistance().set(renderDistance);
    }

    public void toggle() {
        enabled = !enabled;
        if (enabled) {
            setRenderDistance(renderDistance);
        } else {
            Minecraft.getInstance().options.renderDistance().set(defaultRenderDistance);
        }
        Minecraft.getInstance().gui.getChat().addMessage(
            Component.literal("Custom Render Distance " + (enabled ? "§aenabled" : "§cdisabled"))
        );
    }
}
