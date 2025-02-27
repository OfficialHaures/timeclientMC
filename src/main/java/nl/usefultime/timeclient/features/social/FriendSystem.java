package nl.usefultime.timeclient.features.social;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FriendSystem {
    private boolean enabled = true;
    private Map<String, FriendData> friends = new HashMap<>();

    public static class FriendData {
        String name;
        String tag;
        boolean favorite;
        UUID uuid;

        public FriendData(String name, String tag) {
            this.name = name;
            this.tag = tag;
            this.favorite = false;
        }
    }

    public void addFriend(String name, String tag) {
        friends.put(name.toLowerCase(), new FriendData(name, tag));
        Minecraft.getInstance().gui.getChat().addMessage(
            Component.literal("§aAdded §f" + name + " §aas friend with tag: §f" + tag)
        );
    }

    public void removeFriend(String name) {
        if (friends.remove(name.toLowerCase()) != null) {
            Minecraft.getInstance().gui.getChat().addMessage(
                Component.literal("§cRemoved §f" + name + " §cfrom friends")
            );
        }
    }

    public void setTag(String name, String newTag) {
        FriendData friend = friends.get(name.toLowerCase());
        if (friend != null) {
            friend.tag = newTag;
            Minecraft.getInstance().gui.getChat().addMessage(
                Component.literal("§aUpdated tag for §f" + name + " §ato: §f" + newTag)
            );
        }
    }

    public void toggleFavorite(String name) {
        FriendData friend = friends.get(name.toLowerCase());
        if (friend != null) {
            friend.favorite = !friend.favorite;
            Minecraft.getInstance().gui.getChat().addMessage(
                Component.literal("§f" + name + (friend.favorite ? " §ais now favorite" : " §cis no longer favorite"))
            );
        }
    }

    public boolean isFriend(String name) {
        return friends.containsKey(name.toLowerCase());
    }

    public String getTag(String name) {
        FriendData friend = friends.get(name.toLowerCase());
        return friend != null ? friend.tag : null;
    }

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        if (!enabled) return;

        String message = event.getMessage().getString();
        for (FriendData friend : friends.values()) {
            if (message.contains(friend.name)) {
                message = message.replace(friend.name, "§b" + friend.name + "§r");
                if (friend.tag != null && !friend.tag.isEmpty()) {
                    message += " §7[" + friend.tag + "]§r";
                }
                event.setMessage(Component.literal(message));
                break;
            }
        }
    }

    public void toggle() {
        enabled = !enabled;
        Minecraft.getInstance().gui.getChat().addMessage(
            Component.literal("Friend System " + (enabled ? "§aenabled" : "§cdisabled"))
        );
    }
}
