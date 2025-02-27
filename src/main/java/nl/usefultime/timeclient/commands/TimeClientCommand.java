package nl.usefultime.timeclient.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import nl.usefultime.timeclient.Timeclient;

public class TimeClientCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        LiteralArgumentBuilder<CommandSourceStack> command = Commands.literal("timeclient")
                .then(Commands.literal("crosshair").executes(ctx -> {
                    Timeclient.getInstance().getCustomCrosshair().toggle();
                    return 1;
                }))
                .then(Commands.literal("blockoverlay").executes(ctx -> {
                    Timeclient.getInstance().getBlockOverlay().toggle();
                    return 1;
                }))
                .then(Commands.literal("ram").executes(ctx -> {
                    Timeclient.getInstance().getRamDisplay().toggle();
                    return 1;
                }))
                .then(Commands.literal("fps").executes(ctx -> {
                    Timeclient.getInstance().getFpsDisplay().toggle();
                    return 1;
                }))
                .then(Commands.literal("ping").executes(ctx -> {
                    Timeclient.getInstance().getPingDisplay().toggle();
                    return 1;
                }))
                .then(Commands.literal("potions").executes(ctx -> {
                    Timeclient.getInstance().getPotionEffectsHUD().toggle();
                    return 1;
                }))
                .then(Commands.literal("particles").executes(ctx -> {
                    Timeclient.getInstance().getParticleMultiplier().toggle();
                    return 1;
                }));

        dispatcher.register(command);
    }
}
