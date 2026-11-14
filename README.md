# Enchantments-Plus

## Updated to 1.20.1 for Fabric

<img src="https://i.imgur.com/HFDSgKD.png" alt="Icon" width="250"/>

Minecraft mod that adds 19 custom enchantments to the game.

This branch backports the features and fixes from the 26.3 release to Minecraft 1.20.1 (Java 17).

Supports [Enchantment Descriptions](https://github.com/Darkhax-Minecraft/Enchantment-Descriptions "Enchantment Descriptions Github")
and [Mod Menu](https://modrinth.com/mod/modmenu "Mod Menu Modrinth")

# **[Download Link](https://github.com/therobdog7/enchantments-plus/releases/ "Download")**

---

**UPDATED TO MC VERSION: 1.20.1**

**Requires: [Fabric Loader (0.14.21+)](https://fabricmc.net/use/ "Fabric Download")
AND [Fabric API (0.86.1+)](https://www.curseforge.com/minecraft/mc-mods/fabric-api)**

Optional: [Cloth Config API Fabric (11.0.99+)](https://www.curseforge.com/minecraft/mc-mods/cloth-config)
for configuration and [Mod Menu](https://modrinth.com/mod/modmenu) for the settings screen.
Without Cloth Config, all enchantments use their enabled defaults.

Installation: Place the mod JAR and Fabric API in the `mods` folder on both the client and server.

### Configuration and backported fixes

- Three settings tabs (combat, movement, utility), per-enchantment enable toggles, and maximum levels from **1 to 99**.
- Server settings are authoritative and synchronize to joining clients and when changed. Local client settings do not override a multiplayer server.
- Disabled enchantments are excluded from random enchanting, enchanted-book trades, and random loot, and their effects are disabled. Existing items are not rewritten.
- Cubical, End Slayer, Payback, Raider, and Sniper add damage to the original hit rather than causing a second, unattributed hit.
- Sniper uses the bow that fired the arrow, even after switching weapons, and adds at most **40 bonus damage** after arrow speed/critical scaling and before armor mitigation.
- Dual Leap has success/failure sounds, prevents leaps after falling four blocks, and clears unused leaps when boots are removed or the enchantment is disabled.
- Hiker preserves other step-height adjustments. Excavator adds block reach without modifying attack reach or overwriting other attribute modifiers.
- Flash Forge preserves Fortune drop counts without modifying shared smelting recipe outputs.

The table below lists default levels. Raising a maximum level does not add scaling to effects that are intrinsically on/off.
Minecraft 1.20.1 uses Java-registered enchantments rather than the newer data-driven enchantment registry.

---

### Current Enchantments List:

|     Name     | Tool/Weapon/Armor | Number of Levels | Effect                                                                                      |  Rarity   | Notes                                                                       |
|:------------:|:-----------------:|:----------------:|---------------------------------------------------------------------------------------------|:---------:|-----------------------------------------------------------------------------|
|  Levitation  |       Sword       |        2         | Target floats for a few seconds when hit                                                    |   Rare    | Cannot be equipped with fire aspect, toxic strike, frostbite                |
|  Life Steal  |       Sword       |        3         | Some of damage dealt is returned to user as health                                          |   Rare    | Cannot be equipped with payback                                             |
| Thunder Lord |       Sword       |        3         | Chance of summoning a lightning bolt to strike target                                       | Very Rare |                                                                             |
|   Payback    |       Sword       |        3         | If user is low on health, target will be dealt more damage                                  |   Rare    | Cannot be equipped with life steal                                          |
|  Frostbite   |       Sword       |        2         | Target is given slowness when hit                                                           |   Rare    | Cannot be equipped with fire aspect, toxic strike, levitation               |
|  End Slayer  |       Sword       |        5         | Deals extra damage to Endermen, Endermites, Shulkers and Ender Dragon                       | Uncommon  | Cannot be equipped with other damage enchantments                           |
|   Cubical    |       Sword       |        5         | Deals extra damage to Creepers, Slimes, and Magma Cubes                                     | Uncommon  | Cannot be equipped with other damage enchantments                           |
| Lunar Sight  |      Helmet       |        1         | Gives night vision effect                                                                   |  Common   |                                                                             |
| Blaze Walker |       Boots       |        2         | Walk on lava to turn it to obsidian (works like frost walker)                               |   Rare    | Treasure enchantment. Cannot be equipped with depth strider or frost walker |
| Flash Forge  |  Pickaxe/Shovel   |        1         | Blocks automatically smelt when mined, works with fortune for ores                          |   Rare    | Cannot be equipped with silk touch                                          |
|  Moon Walk   |       Boots       |        2         | Constant low level speed and jump boost, and a 20 second cooldown of high level jump boost. |   Rare    |                                                                             |
|    Raider    |       Sword       |        5         | Deals extra damage to all raid/illager/pillager mobs                                        | Uncommon  | Cannot be equipped with other damage enchantments                           |
| Toxic Strike |       Sword       |        2         | Target is poisoned when hit                                                                 |   Rare    | Cannot be equipped with fire aspect, frostbite, levitation                  |
|    Hiker     |       Boots       |        1         | Allows you to step up one high blocks without jumping, like a horse                         | Uncommon  |                                                                             |
|  Dual Leap   |       Boots       |        1         | Allows you to double jump                                                                   |   Rare    | Cannot be equipped with Moon Walk                                           |
|    Sniper    |        Bow        |        3         | Deals more damage the further away your target is from you                                  |  Common   | Cannot be equipped with Storm Strike                                        |
| Mystic Mind  |      Helmet       |        1         | Teleports the user away (like chorus fruit) when they're damaged below 3 hearts             | Uncommon  |                                                                             |
| Storm Strike |        Bow        |        3         | Chance of summoning a lightning bolt to strike target                                       | Very Rare | Cannot be equipped with Sniper                                              |
|  Excavator   |  Pickaxe/Shovel   |        3         | Extends your reach distance to mine from further away                                       |   Rare    |                                                                             |

[![Curse Forge Banner](https://i.imgur.com/1gmBDfe.png?1)](https://www.curseforge.com/minecraft/mc-mods/enchantments-plus-fabric "Curse Forge")

![Fabric Only Banner](https://i.ibb.co/yphNcXz/fabric-only-banner.png)
