package nl.usefultime.timeclient.features.hud;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.HashMap;
import java.util.Map;

public class AutoTextHotkeys {
    private boolean enabled = true;
    private Map<Integer, String> hotkeyMessages = new HashMap<>();

    public AutoTextHotkeys() {
        // hotkeys
        hotkeyMessages.put(296, "gg"); // F7
        hotkeyMessages.put(297, "Good game!"); // F8
        hotkeyMessages.put(298, "Thanks!"); // F9
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.Key event) {
        if (!enabled) return;

        String message = hotkeyMessages.get(event.getKey());
        if (message != null) {
            Minecraft.getInstance().player.connection.sendChat(message);
        }
    }

    public void addHotkey(int key, String message) {
        hotkeyMessages.put(key, message);
    }

    public void toggle() {
        enabled = !enabled;
        Minecraft.getInstance().gui.getChat().addMessage(
            Component.literal("Auto Text Hotkeys " + (enabled ? "§aenabled" : "§cdisabled"))
        );
    }
}
