package com.terminatr.phycology.client.inventory;

import com.mojang.blaze3d.platform.GlStateManager;
import com.terminatr.phycology.Phycology;
import com.terminatr.phycology.common.inventory.FilterMachineContainer;
import net.minecraft.client.gui.IHasContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;


@OnlyIn(Dist.CLIENT)
public class FilterMachineScreen extends ContainerScreen<FilterMachineContainer> implements IHasContainer<FilterMachineContainer> {

    private int textureXSize;

    private int textureYSize;

    private ResourceLocation gui;

    public FilterMachineScreen(FilterMachineContainer container, PlayerInventory playerInventory, ITextComponent title) {

        super(container, playerInventory, title);

        this.xSize = 184;
        this.ySize = 222;
        this.textureXSize = 256;
        this.textureYSize = 256;

        this.gui = new ResourceLocation(Phycology.MOD_ID, "textures/gui/screen_machine_filter.png");

        this.passEvents = true;
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {

        this.renderBackground();
        super.render(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {

        this.font.drawString(this.title.getFormattedText(), 8.0f, 6.0f, 4210752);
        this.font.drawString(this.playerInventory.getDisplayName().getFormattedText(), 8.0f, (float) (this.ySize - 96 +2), 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {

        GlStateManager.color4f(1.0f, 1.0f, 1.0f, 1.0f);

        this.minecraft.getTextureManager().bindTexture(this.gui);

        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;

        this.blit(x, y, 0, 0, this.xSize, this.ySize, textureXSize, textureYSize);

        int progressBar = this.container.getProcessingProgressScaled();
        int waterLevel = this.container.getWaterLevelScaled();

        this.blit(x + 83, y + 65, 184, 0, progressBar + 1, 6);

        this.blit(x + 12, y + 98 - waterLevel, 240, 128 - waterLevel, 16, waterLevel + 1);

        this.blit(x + 12, y + 37, 208, 0, 16, 62);
        this.blit(x + 156, y + 37, 208, 0, 16, 62);
    }
}
