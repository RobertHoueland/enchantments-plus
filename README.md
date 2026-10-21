# Enchantments-Plus for Fabric

<img src="https://i.imgur.com/HFDSgKD.png" alt="Icon" width="250"/>

Minecraft mod that adds over 15 new enchantments to the game!

Supports [Enchantment Descriptions](https://github.com/Darkhax-Minecraft/Enchantment-Descriptions "Enchantment Descriptions Github")
and [Mod Menu](https://modrinth.com/mod/modmenu "Mod Menu Modrinth")

# **[Download Link](https://github.com/therobdog7/enchantments-plus/releases/ "Download")**

---

**Requires: [Fabric Loader](https://fabricmc.net/use/ "Fabric Download")
AND [Fabric API](https://www.curseforge.com/minecraft/mc-mods/fabric-api)**

Installation: Drop the mod .jar and Fabric API into your mods folder.

Optional: Install [Cloth Config API Fabric](https://www.curseforge.com/minecraft/mc-mods/cloth-config)
for configuration file support, and [Mod Menu](https://modrinth.com/mod/modmenu "Mod Menu Modrinth") for the in-game
settings screen.<br>Without Cloth Config,
all enchantments are enabled by default; multiplayer clients follow the server's enchantment settings.

### Configuration

With Cloth Config and Mod Menu installed, open **Mods → Enchantments Plus → Configure**.
The **Combat**, **Movement**, and **Utility & Defense** tabs contain collapsible sections for each enchantment.
Each section has an enable toggle, a maximum-level input (1–99), individual reset controls,
and a label indicating whether the effect scales with level.
Disabled enchantments retain their maximum-level setting. Cancel discards unsaved edits.

Maximum levels default to the values in the table below. Reopen the world or restart the server after changing them.
Server administrators can edit `config/enchantmentsplus.json5`; existing enable settings remain compatible,
and new fields such as `maxLevelFrostbite` control maximum levels. Out-of-range file values are clamped to 1–99.
Multiplayer uses the server's registry definitions, regardless of a client's local settings or whether it has Cloth
Config.
Without Cloth Config, the mod leaves datapack maximum levels unchanged. With Cloth Config, configured levels take
precedence over datapack maximum levels for these 19 enchantments.

These limits apply to enchanting, new enchanted-book trades and loot, anvil combinations, and `/enchant`.
Existing items and already-generated trades/loot are not rewritten; combining existing items follows vanilla anvil
rules.
Enchanting costs and effect formulas are unchanged, so high levels may require anvil combinations and can be very
powerful.
All 19 enchantments allow maximum-level changes; this does not necessarily make their effects stronger:
- **Level-dependent effects (16):** Blaze Walker, Cubical, Dual Leap, End Slayer, Excavator, Frostbite, Hiker,
  Levitation, Life Steal, Moon Walker, Payback, Raider, Sniper, Storm Strike, Thunder Lord, and Toxic Strike.
  Dual Leap gains more midair jumps, and Hiker gains greater step height, despite both defaulting to level I.
  Some effects have caps: Blaze Walker's radius caps at 16 blocks (level XIV), Life Steal's activation chance
  reaches 100% at level V, Sniper's extra damage caps at 40, and lightning chances reach 100% at level XX
  for Storm Strike and level X for Thunder Lord (when the sky is visible).
- **Effects unchanged at higher levels (3):** Flash Forge, Lunar Sight, and Mystic Mind.
  Their maximum levels can still be configured, but higher levels do not improve their effects.

New UI text uses English placeholders in the Russian and Chinese locales until translations are provided.

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
|  Dual Leap   |       Boots       |        1         | Allows you to double jump (does not work if you have fallen a certain number of blocks)     |   Rare    | Cannot be equipped with Moon Walk                                           |
|    Sniper    |        Bow        |        3         | Deals more damage the further away your target is from you                                  |  Common   | Cannot be equipped with Storm Strike                                        |
| Mystic Mind  |      Helmet       |        1         | Teleports the user away (like chorus fruit) when they're damaged below 3 hearts             | Uncommon  |                                                                             |
| Storm Strike |        Bow        |        3         | Chance of summoning a lightning bolt to strike target                                       | Very Rare | Cannot be equipped with Sniper                                              |
|  Excavator   |  Pickaxe/Shovel   |        3         | Extends your reach distance to mine from further away                                       |   Rare    |                                                                             |

[![Curse Forge Banner](https://i.imgur.com/1gmBDfe.png?1)](https://www.curseforge.com/minecraft/mc-mods/enchantments-plus-fabric "Curse Forge")

![Fabric Only Banner](https://i.ibb.co/yphNcXz/fabric-only-banner.png)
