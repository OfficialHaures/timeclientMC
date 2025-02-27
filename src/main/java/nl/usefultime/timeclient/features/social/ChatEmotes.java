package nl.usefultime.timeclient.features.social;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.HashMap;
import java.util.Map;

public class ChatEmotes {
    private boolean enabled = true;
    private Map<String, String> emotes = new HashMap<>();

    public ChatEmotes() {
        emotes.put(":smile:", "☺");
        emotes.put(":heart:", "❤");
        emotes.put(":star:", "⭐");
        emotes.put(":check:", "✔");
        emotes.put(":x:", "✖");
    }

    @SubscribeEvent
    public void onChat(ClientChatEvent event) {
        if (!enabled) return;

        String message = event.getMessage();
        for (Map.Entry<String, String> emote : emotes.entrySet()) {
            message = message.replace(emote.getKey(), emote.getValue());
        }
        event.setMessage(message);
    }

    public void addEmote(String trigger, String emote) {
        emotes.put(trigger, emote);
    }

    public void toggle() {
        enabled = !enabled;
        Minecraft.getInstance().gui.getChat().addMessage(
            Component.literal("Chat Emotes " + (enabled ? "§aenabled" : "§cdisabled"))
        );
    }
}
