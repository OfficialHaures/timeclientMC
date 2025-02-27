package nl.usefultime.timeclient.features.performance;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.network.chat.Component;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ParticleMultiplier {
    private boolean enabled = false;
    private float multiplier = 1.0f;
    private ParticleEngine particleEngine;

    public ParticleMultiplier() {
        this.particleEngine = Minecraft.getInstance().particleEngine;
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (!enabled) return;

        if (event.phase == TickEvent.Phase.START) {
            updateParticles();
        }
    }

    private void updateParticles() {
        if (particleEngine != null) {
            particleEngine.setLevel(Minecraft.getInstance().level);
            particleEngine.tick();
        }
    }

    public void setMultiplier(float value) {
        this.multiplier = Math.max(0.0f, Math.min(5.0f, value));
        Minecraft.getInstance().gui.getChat().addMessage(
                Component.literal("Particle Multiplier set to: " + multiplier)
        );
    }

    public float getMultiplier() {
        return multiplier;
    }

    public void toggle() {
        enabled = !enabled;
        Minecraft.getInstance().gui.getChat().addMessage(
                Component.literal("Particle Multiplier " + (enabled ? "§aenabled" : "§cdisabled"))
        );
    }
}
