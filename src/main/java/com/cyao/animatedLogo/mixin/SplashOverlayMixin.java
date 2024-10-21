package com.cyao.animatedLogo.mixin;

import com.cyao.animatedLogo.AnimationFrameTexture;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.SplashOverlay;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SplashOverlay.class)
public class SplashOverlayMixin {
    @Shadow @Final private MinecraftClient client;
    @Unique
    private int count = 0;
    private Identifier frames[];
    private boolean inited = false;

    /**
     * Draws the logo shadows
     * Original names for future reference:
     * @param scaledWidth i
     * @param scaledHeight j
     * @param now l
     * @param fadeOutProgress f
     * @param fadeInProgress g
     * @param alpha s
     * @param x t
     * @param y u
     * @param height d
     * @param halfHeight v
     * @param width e
     * @param halfWidth w
     */
    @Inject(method = "render",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lnet/minecraft/util/Identifier;IIIIFFIIII)V", ordinal = 1, shift = At.Shift.AFTER)
    )
    private void onAfterRenderLogo(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci, @Local(ordinal = 2) int scaledWidth, @Local(ordinal = 3) int scaledHeight, @Local long now, @Local(ordinal = 1) float fadeOutProgress, @Local(ordinal = 2) float fadeInProgress, @Local(ordinal = 3) float alpha, @Local(ordinal = 4) int x, @Local(ordinal = 5) int y, @Local(ordinal = 0) double height, @Local(ordinal = 6) int halfHeight, @Local(ordinal = 1) double width, @Local(ordinal = 7) int halfWidth) {
        if (!inited) {
            this.frames = new Identifier[12];

            for (int i = 0; i < 12; i++) {
                this.frames[i] = Identifier.of("animated-logo", "textures/gui/frame_" + i + ".png");
            }

            inited = true;
        }

        context.drawTexture(this.frames[count], 0, y - halfHeight, halfWidth, (int) height, -0.0625F, 0, 120, 60, 120, 120);
        context.drawTexture(this.frames[count], x, y - halfHeight, halfWidth, (int) height, 0.0625F, 60, 120, 60, 120, 120);

        count++;
        if (count >= 12) {
            count = 0;
        }
    }
}
