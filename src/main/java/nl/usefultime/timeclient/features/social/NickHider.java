package nl.usefultime.timeclient.features.social;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.HashMap;
import java.util.Map;

public class NickHider {
    private boolean enabled = true;
    private String customName = "You";
    private Map<String, String> hiddenNames = new HashMap<>();

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        if (!enabled) return;

        String message = event.getMessage().getString();
        String playerName = Minecraft.getInstance().player.getName().getString();

        if (message.contains(playerName)) {
            message = message.replace(playerName, customName);
        }

        for (Map.Entry<String, String> name : hiddenNames.entrySet()) {
            message = message.replace(name.getKey(), name.getValue());
        }

        event.setMessage(Component.literal(message));
    }

    public void setCustomName(String name) {
        this.customName = name;
    }

    public void addHiddenName(String original, String hidden) {
        hiddenNames.put(original, hidden);
    }

    public void toggle() {
        enabled = !enabled;
        Minecraft.getInstance().gui.getChat().addMessage(
            Component.literal("Nick Hider " + (enabled ? "§aenabled" : "§cdisabled"))
        );
    }
}
