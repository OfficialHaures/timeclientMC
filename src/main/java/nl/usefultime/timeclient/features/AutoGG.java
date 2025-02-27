package nl.usefultime.timeclient.features;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class AutoGG {
    private boolean enabled = true;
    private String[] triggers = {"Victory!", "Winner!", "1st Place", "Game Over!"};
    private String ggMessage = "gg";
    private long delay = 1000;

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        if (!enabled) return;

        String message = event.getMessage().getString().toLowerCase();
        for (String trigger : triggers) {
            if (message.contains(trigger.toLowerCase())) {
                sendGG();
                break;
            }
        }
    }

    private void sendGG() {
        new Thread(() -> {
            try {
                Thread.sleep(delay);
                Minecraft.getInstance().player.connection.sendChat(ggMessage);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void toggle() {
        enabled = !enabled;
        Minecraft.getInstance().gui.getChat().addMessage(
                Component.literal("AutoGG " + (enabled ? "§aenabled" : "§cdisabled"))
        );
    }


}
