package nl.usefultime.timeclient.features.visual;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class FullBright {
    private boolean enabled = false;
    private float defaultGamma;
    private float targetGamma = 100.0F;
    private float transitionSpeed = 2.0F;

    public FullBright() {
        defaultGamma = Minecraft.getInstance().options.gamma().get().floatValue();
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        float currentGamma = Minecraft.getInstance().options.gamma().get().floatValue();

        if (enabled && currentGamma < targetGamma) {
            Minecraft.getInstance().options.gamma().set(Double.valueOf(Math.min(currentGamma + transitionSpeed, targetGamma)));
        } else if (!enabled && currentGamma > defaultGamma) {
            Minecraft.getInstance().options.gamma().set(Double.valueOf(Math.max(currentGamma - transitionSpeed, defaultGamma)));
        }
    }

    public void setTargetGamma(float gamma) {
        this.targetGamma = gamma;
    }

    public void setTransitionSpeed(float speed) {
        this.transitionSpeed = speed;
    }

    public void toggle() {
        enabled = !enabled;
        Minecraft.getInstance().gui.getChat().addMessage(
                Component.literal("FullBright " + (enabled ? "§aenabled" : "§cdisabled"))
        );
    }
}
