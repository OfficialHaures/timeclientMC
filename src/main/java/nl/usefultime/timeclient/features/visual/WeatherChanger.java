package nl.usefultime.timeclient.features.visual;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class WeatherChanger {
    private boolean enabled = false;
    private String currentWeather = "CLEAR";

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (!enabled || Minecraft.getInstance().level == null) return;

        switch(currentWeather) {
            case "CLEAR":
                Minecraft.getInstance().level.setRainLevel(0);
                Minecraft.getInstance().level.setThunderLevel(0);
                break;
            case "RAIN":
                Minecraft.getInstance().level.setRainLevel(1);
                Minecraft.getInstance().level.setThunderLevel(0);
                break;
            case "STORM":
                Minecraft.getInstance().level.setRainLevel(1);
                Minecraft.getInstance().level.setThunderLevel(1);
                break;
        }
    }

    public void setWeather(String weather) {
        this.currentWeather = weather;
    }

    public void toggle() {
        enabled = !enabled;
        Minecraft.getInstance().gui.getChat().addMessage(
            Component.literal("WeatherChanger " + (enabled ? "§aenabled" : "§cdisabled"))
        );
    }
}
