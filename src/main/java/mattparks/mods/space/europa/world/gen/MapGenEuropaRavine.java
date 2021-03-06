package mattparks.mods.space.europa.world.gen;

import java.util.Random;

import mattparks.mods.space.europa.blocks.EuropaBlocks;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.MapGenBase;

public class MapGenEuropaRavine extends MapGenBase {
	private final float[] field_75046_d = new float[1024];

	protected void generateRavine(long par1, int par3, int par4, Block[] par5ArrayOfByte, double par6, double par8, double par10, float par12, float par13, float par14, int par15, int par16, double par17) {
		final Random random = new Random(par1);
		final double d4 = par3 * 16 + 8;
		final double d5 = par4 * 16 + 8;
		float f3 = 0.0F;
		float f4 = 0.0F;

		if (par16 <= 0) {
			final int j1 = this.range * 16 - 16;
			par16 = j1 - random.nextInt(j1 / 4);
		}

		boolean flag = false;

		if (par15 == -1) {
			par15 = par16 / 2;
			flag = true;
		}

		float f5 = 1.0F;

		for (int k1 = 0; k1 < 128; ++k1) {
			if (k1 == 0 || random.nextInt(3) == 0) {
				f5 = 1.0F + random.nextFloat() * random.nextFloat() * 1.0F;
			}

			this.field_75046_d[k1] = f5 * f5;
		}

		for (; par15 < par16; ++par15) {
			double d6 = 1.5D + MathHelper.sin(par15 * (float) Math.PI / par16) * par12 * 1.0F;
			double d7 = d6 * par17;
			d6 *= random.nextFloat() * 0.25D + 0.75D;
			d7 *= random.nextFloat() * 0.25D + 0.75D;
			final float f6 = MathHelper.cos(par14);
			final float f7 = MathHelper.sin(par14);
			par6 += MathHelper.cos(par13) * f6;
			par8 += f7;
			par10 += MathHelper.sin(par13) * f6;
			par14 *= 0.7F;
			par14 += f4 * 0.05F;
			par13 += f3 * 0.05F;
			f4 *= 0.8F;
			f3 *= 0.5F;
			f4 += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 2.0F;
			f3 += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 4.0F;

			if (flag || random.nextInt(4) != 0) {
				final double d8 = par6 - d4;
				final double d9 = par10 - d5;
				final double d10 = par16 - par15;
				final double d11 = par12 + 2.0F + 16.0F;

				if (d8 * d8 + d9 * d9 - d10 * d10 > d11 * d11) {
					return;
				}

				if (par6 >= d4 - 16.0D - d6 * 2.0D && par10 >= d5 - 16.0D - d6 * 2.0D && par6 <= d4 + 16.0D + d6 * 2.0D && par10 <= d5 + 16.0D + d6 * 2.0D) {
					int l1 = MathHelper.floor_double(par6 - d6) - par3 * 16 - 1;
					int i2 = MathHelper.floor_double(par6 + d6) - par3 * 16 + 1;
					int j2 = MathHelper.floor_double(par8 - d7) - 1;
					int k2 = MathHelper.floor_double(par8 + d7) + 1;
					int l2 = MathHelper.floor_double(par10 - d6) - par4 * 16 - 1;
					int i3 = MathHelper.floor_double(par10 + d6) - par4 * 16 + 1;

					if (l1 < 0) {
						l1 = 0;
					}

					if (i2 > 16) {
						i2 = 16;
					}

					if (j2 < 1) {
						j2 = 1;
					}

					if (k2 > 120) {
						k2 = 120;
					}

					if (l2 < 0) {
						l2 = 0;
					}

					if (i3 > 16) {
						i3 = 16;
					}

					boolean flag1 = false;
					int j3;
					int k3;

					for (j3 = l1; !flag1 && j3 < i2; ++j3) {
						for (int l3 = l2; !flag1 && l3 < i3; ++l3) {
							for (int i4 = k2 + 1; !flag1 && i4 >= j2 - 1; --i4) {
								k3 = (j3 * 16 + l3) * 128 + i4;

								if (i4 >= 0 && i4 < 128) {
									if (this.isOceanBlock(par5ArrayOfByte, k3, j3, i4, l3, par3, par4)) {
										flag1 = true;
									}

									if (i4 != j2 - 1 && j3 != l1 && j3 != i2 - 1 && l3 != l2 && l3 != i3 - 1) {
										i4 = j2;
									}
								}
							}
						}
					}

					if (!flag1) {
						for (j3 = l1; j3 < i2; ++j3) {
							final double d12 = (j3 + par3 * 16 + 0.5D - par6) / d6;

							for (k3 = l2; k3 < i3; ++k3) {
								final double d13 = (k3 + par4 * 16 + 0.5D - par10) / d6;
								int j4 = (j3 * 16 + k3) * 128 + k2;
								boolean flag2 = false;

								if (d12 * d12 + d13 * d13 < 1.0D) {
									for (int k4 = k2 - 1; k4 >= j2; --k4) {
										final double d14 = (k4 + 0.5D - par8) / d7;

										if ((d12 * d12 + d13 * d13) * this.field_75046_d[k4] + d14 * d14 / 6.0D < 1.0D) {
											if (this.isTopBlock(par5ArrayOfByte, j4, j3, k4, k3, par3, par4)) {
												flag2 = true;
											}

											this.digBlock(par5ArrayOfByte, j4, j3, k4, k3, par3, par4, flag2);
										}

										--j4;
									}
								}
							}
						}

						if (flag) {
							break;
						}
					}
				}
			}
		}
	}

	/**
	 * Recursively called by generate() (generate) and optionally by itself.
	 */
	protected void recursiveGenerate(World par1World, int par2, int par3, int par4, int par5, Block[] par6ArrayOfByte) {
		if (this.rand.nextInt(50) == 0) {
			final double d0 = par2 * 16 + this.rand.nextInt(16);
			final double d1 = this.rand.nextInt(this.rand.nextInt(40) + 8) + 20;
			final double d2 = par3 * 16 + this.rand.nextInt(16);
			final byte b0 = 1;

			for (int i1 = 0; i1 < b0; ++i1) {
				final float f = this.rand.nextFloat() * (float) Math.PI * 2.0F;
				final float f1 = (this.rand.nextFloat() - 0.5F) * 2.0F / 8.0F;
				final float f2 = (this.rand.nextFloat() * 2.0F + this.rand.nextFloat()) * 2.0F;
				this.generateRavine(this.rand.nextLong(), par4, par5, par6ArrayOfByte, d0, d1, d2, f2, f, f1, 0, 0, 3.0D);
			}
		}
	}

	protected boolean isOceanBlock(Block[] data, int index, int x, int y, int z, int chunkX, int chunkZ) {
		return data[index] == Blocks.water || data[index] == Blocks.flowing_water;
	}

	// Exception biomes to make sure we generate like vanilla
	private boolean isExceptionBiome(BiomeGenBase biome) {
		if (biome == BiomeGenBase.mushroomIsland) {
			return true;
		}
		if (biome == BiomeGenBase.beach) {
			return true;
		}
		if (biome == BiomeGenBase.desert) {
			return true;
		}
		return false;
	}

	// Determine if the block at the specified location is the top block for the
	// biome, we take into account
	// Vanilla bugs to make sure that we generate the map the same way vanilla
	// does.
	private boolean isTopBlock(Block[] data, int index, int x, int y, int z, int chunkX, int chunkZ) {
		final BiomeGenBase biome = this.worldObj.getBiomeGenForCoords(x + chunkX * 16, z + chunkZ * 16);
		return this.isExceptionBiome(biome) ? data[index] == EuropaBlocks.europaBasicBlock : data[index] == biome.topBlock;
	}

	/**
	 * Digs out the current block, default implementation removes stone, filler,
	 * and top block Sets the block to lava if y is less then 10, and air other
	 * wise. If setting to air, it also checks to see if we've broken the
	 * surface and if so tries to make the floor the biome's top block
	 * 
	 * @param data
	 *            Block data array
	 * @param index
	 *            Pre-calculated index into block data
	 * @param x
	 *            local X position
	 * @param y
	 *            local Y position
	 * @param z
	 *            local Z position
	 * @param chunkX
	 *            Chunk X position
	 * @param chunkZ
	 *            Chunk Y position
	 * @param foundTop
	 *            True if we've encountered the biome's top block. Ideally if
	 *            we've broken the surface.
	 */
	protected void digBlock(Block[] data, int index, int x, int y, int z, int chunkX, int chunkZ, boolean foundTop) {
		final BiomeGenBase biome = this.worldObj.getBiomeGenForCoords(x + chunkX * 16, z + chunkZ * 16);
		final Block top = this.isExceptionBiome(biome) ? EuropaBlocks.europaBasicBlock : biome.topBlock;
		final Block filler = this.isExceptionBiome(biome) ? EuropaBlocks.europaBasicBlock : biome.fillerBlock;
		final Block block = data[index];

		if (block == EuropaBlocks.europaBasicBlock || block == filler || block == top) {
			if (y < 10) {
				data[index] = Blocks.lava;
			} else {
				data[index] = Blocks.air;

				if (foundTop && data[index - 1] == filler) {
					data[index - 1] = top;
				}
			}
		}
	}
}
