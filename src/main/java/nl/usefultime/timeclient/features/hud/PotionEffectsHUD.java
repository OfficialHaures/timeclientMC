package nl.usefultime.timeclient.features.hud;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraftforge.client.event.CustomizeGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class PotionEffectsHUD {
    private boolean enabled = true;
    private int xPos = 5;
    private int yPos = 100;

    @SubscribeEvent
    public void onRender(CustomizeGuiOverlayEvent event) {
        if (!enabled) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;

        PoseStack poseStack = new PoseStack();
        int y = yPos;

        for (MobEffectInstance effect : mc.player.getActiveEffects()) {
            String effectName = effect.getEffect().getDisplayName().getString();
            int duration = effect.getDuration() / 20; // Convert to seconds
            String text = String.format("%s %d:%02d", effectName, duration / 60, duration % 60);

            mc.font.draw(
                    poseStack,
                    text,
                    xPos,
                    y,
                    effect.getEffect().getColor()
            );
            y += 10;
        }
    }

    public void setPosition(int x, int y) {
        this.xPos = x;
        this.yPos = y;
    }

    public void toggle() {
        enabled = !enabled;
        Minecraft.getInstance().gui.getChat().addMessage(
                Component.literal("Potion Effects " + (enabled ? "§aenabled" : "§cdisabled"))
        );
    }
}
