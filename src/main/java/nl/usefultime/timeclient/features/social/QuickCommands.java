package nl.usefultime.timeclient.features.social;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

import java.util.HashMap;
import java.util.Map;

public class QuickCommands {
    private boolean enabled = true;
    private Map<String, String> commandAliases = new HashMap<>();

    public QuickCommands() {
        commandAliases.put("hub", "/lobby");
        commandAliases.put("spawn", "/spawn");
        commandAliases.put("p", "/party");
        commandAliases.put("sw", "/play skywars");
        commandAliases.put("bw", "/play bedwars");
    }

    public void executeCommand(String alias) {
        if (!enabled) return;

        String command = commandAliases.get(alias.toLowerCase());
        if (command != null) {
            Minecraft.getInstance().player.connection.sendChat(command);
        }
    }

    public void addCommand(String alias, String command) {
        commandAliases.put(alias.toLowerCase(), command);
    }

    public void toggle() {
        enabled = !enabled;
        Minecraft.getInstance().gui.getChat().addMessage(
            Component.literal("Quick Commands " + (enabled ? "§aenabled" : "§cdisabled"))
        );
    }
}
