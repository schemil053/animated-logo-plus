package com.cyao.animatedLogo;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.SplashOverlay;
import net.minecraft.client.resource.metadata.TextureResourceMetadata;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.ResourceTexture;
import net.minecraft.resource.DefaultResourcePack;
import net.minecraft.resource.InputSupplier;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class AnimationFrameTexture extends ResourceTexture {
    Identifier IDENTIFIER;

    public AnimationFrameTexture(Identifier identifier) {
        super(identifier);

        this.IDENTIFIER = identifier;
    }

    protected ResourceTexture.TextureData loadTextureData(ResourceManager resourceManager) {
        DefaultResourcePack defaultResourcePack = MinecraftClient.getInstance().getDefaultResourcePack();
        InputSupplier<InputStream> inputSupplier = defaultResourcePack.open(ResourceType.CLIENT_RESOURCES, this.IDENTIFIER);
        if (inputSupplier == null) {
            return new ResourceTexture.TextureData(new FileNotFoundException(this.IDENTIFIER.toString()));
        } else {
            try {
                InputStream inputStream = inputSupplier.get();

                ResourceTexture.TextureData var5;
                try {
                    var5 = new ResourceTexture.TextureData(new TextureResourceMetadata(true, true), NativeImage.read(inputStream));
                } catch (Throwable var8) {
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (Throwable var7) {
                            var8.addSuppressed(var7);
                        }
                    }

                    throw var8;
                }

                inputStream.close();

                return var5;
            } catch (IOException var9) {
                return new ResourceTexture.TextureData(var9);
            }
        }
    }
}
