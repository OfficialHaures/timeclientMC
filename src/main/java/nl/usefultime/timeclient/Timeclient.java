package nl.usefultime.timeclient;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import nl.usefultime.timeclient.features.AutoGG;
import nl.usefultime.timeclient.features.visual.*;
import nl.usefultime.timeclient.features.performance.*;
import nl.usefultime.timeclient.features.social.*;
import nl.usefultime.timeclient.features.hud.*;
import nl.usefultime.timeclient.gui.TimeClientGui;

@Mod("timeclient")
public class Timeclient {
    private static Timeclient instance;
    private String name = "TimeClient";
    private String version = "1.0";

    // Feature instances
    private CustomCrosshair customCrosshair = new CustomCrosshair();
    private BlockOverlay blockOverlay = new BlockOverlay();
    private RAMDisplay ramDisplay = new RAMDisplay();
    private FPSDisplay fpsDisplay = new FPSDisplay();
    private PingDisplay pingDisplay = new PingDisplay();
    private PotionEffectsHUD potionEffectsHUD = new PotionEffectsHUD();
    private ParticleMultiplier particleMultiplier = new ParticleMultiplier();

    public Timeclient() {
        instance = this;
        MinecraftForge.EVENT_BUS.register(this);
        init();
    }

    public void init() {
        // Register all features to Forge Event Bus
        registerFeatures();
    }

    private void registerFeatures() {
        MinecraftForge.EVENT_BUS.register(customCrosshair);
        MinecraftForge.EVENT_BUS.register(blockOverlay);
        MinecraftForge.EVENT_BUS.register(ramDisplay);
        MinecraftForge.EVENT_BUS.register(fpsDisplay);
        MinecraftForge.EVENT_BUS.register(pingDisplay);
        MinecraftForge.EVENT_BUS.register(potionEffectsHUD);
        MinecraftForge.EVENT_BUS.register(particleMultiplier);
    }

    public void openGui() {
        Minecraft.getInstance().setScreen(new TimeClientGui().getScreen());
    }

    // Getters
    public CustomCrosshair getCustomCrosshair() { return customCrosshair; }
    public BlockOverlay getBlockOverlay() { return blockOverlay; }
    public RAMDisplay getRamDisplay() { return ramDisplay; }
    public FPSDisplay getFpsDisplay() { return fpsDisplay; }
    public PingDisplay getPingDisplay() { return pingDisplay; }
    public PotionEffectsHUD getPotionEffectsHUD() { return potionEffectsHUD; }
    public ParticleMultiplier getParticleMultiplier() { return particleMultiplier; }

    public static Timeclient getInstance() {
        return instance;
    }
    public String getVersion() {
        return version;
    }

}
