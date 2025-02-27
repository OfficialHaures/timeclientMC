package nl.usefultime.timeclient.features;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ChatFilter {
    private boolean enabled = true;
    private List<Pattern> filters = new ArrayList<>();

    public ChatFilter() {
        addFilter("\\[AD\\].*");
        addFilter(".*[0-9]+\\$.*");
        addFilter(".*discord\\.gg.*");
    }

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        if (!enabled) return;

        String message = event.getMessage().getString();
        for (Pattern filter : filters) {
            if (filter.matcher(message).matches()) {
                event.setCanceled(true);
                return;
            }
        }
    }

    public void addFilter(String regex) {
        filters.add(Pattern.compile(regex, Pattern.CASE_INSENSITIVE));
    }

    public void toggle() {
        enabled = !enabled;
        Minecraft.getInstance().gui.getChat().addMessage(
                Component.literal("ChatFilter " + (enabled ? "§aenabled" : "§cdisabled"))
        );
    }
}
