package mattparks.mods.exoplanets.elipse.items;

import mattparks.mods.exoplanets.elipse.ElipseCore;
import mattparks.mods.space.core.SpaceCore;
import mattparks.mods.space.core.proxy.ClientProxy;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemAxeElipse extends ItemAxe {
	public ItemAxeElipse(String name, ToolMaterial par2EnumToolMaterial) {
		super(par2EnumToolMaterial);
		this.setUnlocalizedName(name);
	}

	@Override
	public CreativeTabs getCreativeTab() {
		return SpaceCore.spaceItemsTab;
	}

	@Override
	public EnumRarity getRarity(ItemStack par1ItemStack) {
		return ClientProxy.spaceItem;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister par1IconRegister) {
		this.itemIcon = par1IconRegister.registerIcon(this.getUnlocalizedName().replace("item.", ElipseCore.TEXTURE_PREFIX));
	}
}
