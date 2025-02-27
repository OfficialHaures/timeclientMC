package nl.usefultime.timeclient.features;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class AutoTip {
    private boolean enabled = true;
    private int tipDelay = 900;
    private int tickCounter = 0;

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (!enabled) return;

        if (event.phase == TickEvent.Phase.END) {
            tickCounter++;
            if (tickCounter >= tipDelay * 20) {
                sendTip();
                tickCounter = 0;
            }
        }
    }

    private void sendTip() {
        Minecraft.getInstance().player.connection.sendChat("/tip all");
    }

    public void toggle() {
        enabled = !enabled;
        Minecraft.getInstance().gui.getChat().addMessage(
                Component.literal("AutoTip " + (enabled ? "§aenabled" : "§cdisabled"))
        );
    }
}
