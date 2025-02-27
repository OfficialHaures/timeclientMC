package nl.usefultime.timeclient.features.visual;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.core.BlockPos;
import net.minecraftforge.client.event.RenderHighlightEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class BlockOverlay {
    private boolean enabled = false;
    private int color = 0x4FFF0000;
    private float lineWidth = 2.0f;
    private boolean fillMode = true;
    private float alpha = 0.4f;

    @SubscribeEvent
    public void onRender(RenderHighlightEvent event) {
        if (!enabled) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.hitResult instanceof BlockHitResult) {
            BlockHitResult hit = (BlockHitResult) mc.hitResult;
            PoseStack poseStack = event.getPoseStack();
            BlockPos pos = hit.getBlockPos();

            event.setCanceled(true);

            VoxelShape shape = Shapes.block();
            VertexConsumer builder = mc.renderBuffers().bufferSource()
                    .getBuffer(RenderType.lines());

            if (fillMode) {
                renderFilledBox(poseStack, pos, shape);
            } else {
                renderOutlineBox(poseStack, pos, shape);
            }
        }
    }

        private void renderFilledBox(PoseStack poseStack, BlockPos pos, VoxelShape shape) {
            float red = (color >> 16 & 255) / 255.0F;
            float green = (color >> 8 & 255) / 255.0F;
            float blue = (color & 255) / 255.0F;

            VertexConsumer vertexConsumer = Minecraft.getInstance().renderBuffers().bufferSource().getBuffer(RenderType.translucent());
            shape.forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> {
                LevelRenderer.addChainedFilledBoxVertices(
                    poseStack,
                    vertexConsumer,
                    minX + pos.getX(), minY + pos.getY(), minZ + pos.getZ(),
                    maxX + pos.getX(), maxY + pos.getY(), maxZ + pos.getZ(),
                    red, green, blue, alpha
                );
            });
        }

        private void renderOutlineBox(PoseStack poseStack, BlockPos pos, VoxelShape shape) {
            float red = (color >> 16 & 255) / 255.0F;
            float green = (color >> 8 & 255) / 255.0F;
            float blue = (color & 255) / 255.0F;

            VertexConsumer vertexConsumer = Minecraft.getInstance().renderBuffers().bufferSource().getBuffer(RenderType.lines());
            shape.forAllEdges((x1, y1, z1, x2, y2, z2) -> {
                float x1f = (float)(x1 + pos.getX());
                float y1f = (float)(y1 + pos.getY());
                float z1f = (float)(z1 + pos.getZ());
                float x2f = (float)(x2 + pos.getX());
                float y2f = (float)(y2 + pos.getY());
                float z2f = (float)(z2 + pos.getZ());

                vertexConsumer.vertex(poseStack.last().pose(), x1f, y1f, z1f).color(red, green, blue, 1.0F).normal(poseStack.last().normal(), 0.0F, 1.0F, 0.0F).endVertex();
                vertexConsumer.vertex(poseStack.last().pose(), x2f, y2f, z2f).color(red, green, blue, 1.0F).normal(poseStack.last().normal(), 0.0F, 1.0F, 0.0F).endVertex();
            });
        }
    public void setColor(int newColor) {
        this.color = newColor;
    }

    public void setAlpha(float newAlpha) {
        this.alpha = Math.max(0.0F, Math.min(1.0F, newAlpha));
    }

    public void toggleFillMode() {
        fillMode = !fillMode;
        Minecraft.getInstance().gui.getChat().addMessage(
                Component.literal("BlockOverlay Mode: " + (fillMode ? "§aFilled" : "§aOutline"))
        );
    }

    public void toggle() {
        enabled = !enabled;
        Minecraft.getInstance().gui.getChat().addMessage(
                Component.literal("BlockOverlay " + (enabled ? "§aenabled" : "§cdisabled"))
        );
    }
}
