package nl.usefultime.timeclient.features.visual;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class TimeChanger {
    private boolean enabled = false;
    private long customTime = 1000;

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (!enabled || Minecraft.getInstance().level == null) return;

        Minecraft.getInstance().level.setDayTime(customTime);
    }

    public void setTime(String timeString) {
        switch (timeString.toLowerCase()) {
            case "day":
                customTime = 1000;
                break;
            case "noon":
                customTime = 6000;
                break;
            case "night":
                customTime = 13000;
                break;
            case "midnight":
                customTime = 18000;
                break;
        }
    }

    public void toggle() {
        enabled = !enabled;
        Minecraft.getInstance().gui.getChat().addMessage(
            Component.literal("TimeChanger " + (enabled ? "§aenabled" : "§cdisabled"))
        );
    }
}
