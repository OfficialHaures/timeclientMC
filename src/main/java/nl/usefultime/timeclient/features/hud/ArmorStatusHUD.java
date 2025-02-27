package nl.usefultime.timeclient.features.hud;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.CustomizeGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ArmorStatusHUD {
    private boolean enabled = true;
    private int xPos = 5;
    private int yPos = 25;

    @SubscribeEvent
    public void onRender(CustomizeGuiOverlayEvent event) {
        if (!enabled) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;

        PoseStack poseStack = new PoseStack();
        int y = yPos;

        for (ItemStack armor : mc.player.getArmorSlots()) {
            if (!armor.isEmpty()) {
                mc.getItemRenderer().renderGuiItem(poseStack, armor, xPos, y);
                String durability = String.format("%d%%",
                        (int)((armor.getMaxDamage() - armor.getDamageValue()) * 100f / armor.getMaxDamage()));
                mc.font.draw(poseStack, durability, xPos + 20, y + 4, 0xFFFFFF);
                y += 16;
            }
        }
    }

    public void toggle() {
        enabled = !enabled;
        Minecraft.getInstance().gui.getChat().addMessage(
                Component.literal("Armor Status " + (enabled ? "§aenabled" : "§cdisabled"))
        );
    }
}
