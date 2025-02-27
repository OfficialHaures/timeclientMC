package nl.usefultime.timeclient.features.performance;

import net.minecraft.client.Minecraft;
import net.minecraft.client.GraphicsStatus;
import net.minecraft.client.ParticleStatus;
import net.minecraft.client.CloudStatus;
import net.minecraft.network.chat.Component;

public class FPSBoost {
    private boolean enabled = false;
    private boolean fastRender = true;
    private boolean optimizeTextures = true;
    private boolean reducedParticles = true;
    private boolean lowGraphics = true;

    public void applyOptimizations() {
        if (!enabled) return;

        Minecraft mc = Minecraft.getInstance();

        mc.options.graphicsMode().set(GraphicsStatus.FAST);
        mc.options.particles().set(ParticleStatus.MINIMAL);
        mc.options.mipmapLevels().set(0);
        mc.options.entityShadows().set(false);
        mc.options.biomeBlendRadius().set(0);
        mc.options.cloudStatus().set(CloudStatus.OFF);
        mc.options.ambientOcclusion().set(false);
        mc.options.renderDistance().set(8);
        mc.options.entityDistanceScaling().set(Double.valueOf(0.5));
    }

    public void setFastRender(boolean value) {
        this.fastRender = value;
        if (enabled) applyOptimizations();
    }

    public void setOptimizeTextures(boolean value) {
        this.optimizeTextures = value;
        if (enabled) applyOptimizations();
    }

    public void setReducedParticles(boolean value) {
        this.reducedParticles = value;
        if (enabled) applyOptimizations();
    }

    public void toggle() {
        enabled = !enabled;
        if (enabled) {
            applyOptimizations();
        }
        Minecraft.getInstance().gui.getChat().addMessage(
                Component.literal("FPS Boost " + (enabled ? "§aenabled" : "§cdisabled"))
        );
    }
}
