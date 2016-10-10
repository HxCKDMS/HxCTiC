package HxCKDMS.HxCTiC.lib;

import cpw.mods.fml.common.event.FMLInterModComms;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

@SuppressWarnings("WeakerAccess")
public class HxCMaterial {
    public int MaterialID;
    public String MaterialName = "", LocalizationString = "";
    public int Durability, MiningSpeed, HarvestLevel, AttackDamage;
    public float HandleDurabilityModifier;
    public int ReinforcementLevel, StoneboundLevel, JaggyLevel;
    public float ProjectileSpeed;
    public int BowDrawSpeed;
    public float ProjectileMass;
    public float ProjectileFragility;
    public String Style = EnumChatFormatting.WHITE.toString();
    public int Colour = 0xFFFFFFFF;
    public String OreDictionaryItem;
    public int RequiredAmountOfMaterial;
    public boolean StampedMaterial = false;
    public int stringDataA, stringDataB, stringDataC;

    public HxCMaterial () {}

    public HxCMaterial (int id, String name, int durability, int miningSpeed, int miningLevel, int attack, float durabilityMod, float projSpeed, int drawSpeed, float mass, float fragility, String style, int color, String oreDict, int amount, boolean Stamped) {
        MaterialID = id;
        MaterialName = name;
        LocalizationString = "material.hxctic." + name.toLowerCase();
        Durability = durability;
        MiningSpeed = miningSpeed;
        HarvestLevel = miningLevel;
        AttackDamage = attack;
        HandleDurabilityModifier = durabilityMod;
        ProjectileSpeed = projSpeed;
        BowDrawSpeed = drawSpeed;
        ProjectileMass = mass;
        ProjectileFragility = fragility;
        Style = style;
        Colour = color;
        OreDictionaryItem = oreDict;
        RequiredAmountOfMaterial = amount;
        StampedMaterial = Stamped;
    }

    public HxCMaterial (int id, String name, int durability, int miningSpeed, int miningLevel, int attack, float durabilityMod, float projSpeed, int drawSpeed, float mass, float fragility, String style, int color, String oreDict, int amount, boolean Stamped, int reinforcement, int stonebound, int jaggy) {
        MaterialID = id;
        MaterialName = name;
        LocalizationString = "material.hxctic." + name.toLowerCase();
        Durability = durability;
        MiningSpeed = miningSpeed;
        HarvestLevel = miningLevel;
        AttackDamage = attack;
        HandleDurabilityModifier = durabilityMod;
        ReinforcementLevel = reinforcement;
        StoneboundLevel = stonebound;
        JaggyLevel = jaggy;
        ProjectileSpeed = projSpeed;
        BowDrawSpeed = drawSpeed;
        ProjectileMass = mass;
        ProjectileFragility = fragility;
        Style = style;
        Colour = color;
        OreDictionaryItem = oreDict;
        RequiredAmountOfMaterial = amount;
        StampedMaterial = Stamped;
    }

    public HxCMaterial (int id, String name, int durability, int miningSpeed, int miningLevel, int attack, float durabilityMod, float projSpeed, int drawSpeed, float mass, float fragility, String style, int color, String oreDict, int amount, boolean Stamped, int reinforcement, int stonebound, int jaggy, int stringA, int stringB, int stringC) {
        MaterialID = id;
        MaterialName = name;
        LocalizationString = "material.hxctic." + name.toLowerCase();
        Durability = durability;
        MiningSpeed = miningSpeed;
        HarvestLevel = miningLevel;
        AttackDamage = attack;
        HandleDurabilityModifier = durabilityMod;
        ReinforcementLevel = reinforcement;
        StoneboundLevel = stonebound;
        JaggyLevel = jaggy;
        ProjectileSpeed = projSpeed;
        BowDrawSpeed = drawSpeed;
        ProjectileMass = mass;
        ProjectileFragility = fragility;
        Style = style;
        Colour = color;
        OreDictionaryItem = oreDict;
        RequiredAmountOfMaterial = amount;
        StampedMaterial = Stamped;
        stringDataA = stringA;
        stringDataB = stringB;
        stringDataC = stringC;
    }

    public void init() {
        //TODO: Bowstring Material Data
        NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger("Id", MaterialID);
        tag.setString("Name", MaterialName);
        tag.setString("localizationString", LocalizationString);
        tag.setInteger("Durability", Durability);
        tag.setInteger("MiningSpeed", MiningSpeed);
        tag.setInteger("HarvestLevel", HarvestLevel);
        tag.setInteger("Attack", AttackDamage);
        tag.setFloat("HandleModifier", HandleDurabilityModifier);
        tag.setInteger("Reinforced", ReinforcementLevel);

        if (JaggyLevel == 0)
            tag.setInteger("Stonebound", StoneboundLevel);
        else tag.setInteger("Jagged", JaggyLevel);

        if (BowDrawSpeed != 0) {
            tag.setFloat("Bow_ProjectileSpeed", ProjectileSpeed);
            tag.setInteger("Bow_DrawSpeed", BowDrawSpeed);
        }
        if (ProjectileMass != 0) {
            tag.setFloat("Projectile_Mass", ProjectileMass);
            tag.setFloat("Projectile_Fragility", ProjectileFragility);
        }
        tag.setString("Style", Style);
        tag.setInteger("Color", Colour);
        FMLInterModComms.sendMessage("TConstruct", "addMaterial", tag);

        if (OreDictionary.doesOreNameExist(OreDictionaryItem)) {
            tag = new NBTTagCompound();
            tag.setInteger("MaterialId", MaterialID);
            tag.setInteger("Value", 1); // 1 material ever 2 value. See PartMapping IMC
            NBTTagCompound item = new NBTTagCompound();
            (new ItemStack(OreDictionary.getOres(OreDictionaryItem).get(0).getItem())).writeToNBT(item);
            tag.setTag("Item", item);

            FMLInterModComms.sendMessage("TConstruct", "addMaterialItem", tag);

            tag = new NBTTagCompound();
            item = new NBTTagCompound();
            (new ItemStack(OreDictionary.getOres(OreDictionaryItem).get(0).getItem())).writeToNBT(item);
            tag.setTag("Item", item);
            item = new NBTTagCompound();
            (new ItemStack(Blocks.bedrock)).writeToNBT(item);
            tag.setTag("Block", item);

            (new FluidStack(Registry.fluids.get("fluid_" + MaterialName.toLowerCase()), 144)).writeToNBT(tag);

            tag.setInteger("Temperature", 500);
            FMLInterModComms.sendMessage("TConstruct", "addSmelteryMelting", tag);

            tag = new NBTTagCompound();
            // liquid to use
            tag.setString("FluidName", "fluid_" + MaterialName.toLowerCase());
            // or this way, it's equal
            (new FluidStack(Registry.fluids.get("fluid_" + MaterialName.toLowerCase()), 1)).writeToNBT(tag);
            tag.setInteger("MaterialId", MaterialID);
            FMLInterModComms.sendMessage("TConstruct", "addPartCastingMaterial", tag);
        }
    }
}
