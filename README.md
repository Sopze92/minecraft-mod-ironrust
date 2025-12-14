## Description
Feels weird that copper oxidizes but iron doesn't? Well... this small mod solves that by implementing oxide into iron in a seamless way for a nice vanilla integration


<p align="center">
<br>
<img src="https://img.shields.io/badge/Minecraft-1.21.11-60c400?style=plastic">
<img src="https://img.shields.io/badge/Minecraft-1.21.9+10-60c400?style=plastic">
</p>
<p align="center">
<img src="https://img.shields.io/badge/ModLoader-Fabric-60c400?style=plastic">
<img src="https://img.shields.io/badge/ModLoader-Forge-60c400?style=plastic">
<img src="https://img.shields.io/badge/ModLoader-NeoForge-60c400?style=plastic">
</p>
<p align="center">
<img src="https://img.shields.io/badge/Single--Player-Passing-60c400?style=plastic">
<img src="https://img.shields.io/badge/Local--Multiplayer-Passing-60c400?style=plastic">
<img src="https://img.shields.io/badge/Dedicated--Server-Passing-60c400?style=plastic">
</p>

<p align="center">
  <a href="https://www.paypal.com/donate/?hosted_button_id=WPVF2NFY2XHE6" target="_blank"><img width=256px src="https://github.com/Sopze92/minecraft-mod-ironrust/blob/main/.resources/donate_button_paypaldonate.webp?raw=true" alt="donate with paypal"></a>
  <a href="https://www.buymeacoffee.com/sopze" target="_blank"><img width=256px src="https://github.com/Sopze92/minecraft-mod-ironrust/blob/main/.resources/donate_button_buymeacoffee.webp?raw=true" alt="buy me a coffee"></a>
</p>
<hr>

### <p align="center">Screenshots</p>
<p align="center">
  <img height=256px src="https://github.com/Sopze92/minecraft-mod-ironrust/blob/main/.resources/preview1.webp?raw=true" alt="preview image">
  <img height=256px src="https://github.com/Sopze92/minecraft-mod-ironrust/blob/main/.resources/screenshot0.webp?raw=true" alt="preview screenshot">
</p>
<hr>

### <p align="center">Dependencies (Fabric)</p>
- [Fabric-API](https://modrinth.com/mod/fabric-api)
<hr>

### <p align="center">What this Mod includes</p>
- **Exposed**|**Weathered**|**Oxidized** variants of all major iron blocks
  - _(Block, Door, Trapdoor, Bars, Chain)_
- **Iron Grate Block** and its 3 oxidized variants
- **Coated** variants for all iron blocks and its oxidized variants
  - _('Coated' is the 'Waxed' state of iron)_
- **Active Honeycomb** (item)
  - _(to apply Coating on iron, by using or crafting)_
- **Active Honeycomb Block** (decorative)
- **Oxidation probability** is affected by nearby water
  - _(Everything counts, distance and level)_
- **Axes de-oxidize iron** as they do with copper
- **Coat On**|**Off** and **Active Honeycomb** advancements
- **Recipe Book entries** for all new recipes
- **Lightning strikes** decrease Iron oxide level
- **Dispensers** can apply Active Honeycomb on blocks
- Oxidized blocks work as their Vanilla counterparts
  - _(Blocks can make Beacons, Doors/Trapdoors require redstone)_
- **Coated Iron Blocks** can be de-crafted into 9 Iron Ingots

<p align="center"><img width=512px src="https://github.com/Sopze92/minecraft-mod-ironrust/blob/main/.resources/preview0.webp?raw=true" alt="content"></p>
<hr>

### <p align="center">Singleplayer / Online</p>
Multiplayer implementation is done so it also works in Multiplayer games as long as both Client+Server have the mod installed. On the other hand, the Mod will be automatically self-disabled if you connect to a server that doesn't have the mod installed
<hr>

## Recipes
<p align="center">
  <img width=189px src="https://github.com/Sopze92/minecraft-mod-ironrust/blob/main/.resources/recipe_active_honeycomb.webp?raw=true" alt="honeycomb recipes">
  <img width=284px src="https://github.com/Sopze92/minecraft-mod-ironrust/blob/main/.resources/recipe_grate_anim.webp?raw=true" alt="iron grate recipes">
  <img width=582px src="https://github.com/Sopze92/minecraft-mod-ironrust/blob/main/.resources/recipe_coated_anim.webp?raw=true" alt="coated iron recipes">
</p>
<hr>

## Installation
Download and drop the correct .jar file based on the ModLoader you're using into the **mods** folder located at Minecraft's root folder

## Uninstallation
I've also made an Uninstaller-Mod-Tool (Local worlds only) (only available for Fabric) that automatically reverts all oxidized iron to Vanilla one during normal gameplay. The IronRust-Uninstaller can only be found in the [Releases Page](https://github.com/Sopze92/minecraft-mod-ironrust/releases).

<details>
<summary><b>Uninstall guide</b></summary>
<br>

To use the uninstaller, remove **IronRust** mod from your mods folder and placethe **IronRust-Wipe** file there, then start Minecraft and load your world. 

You can either play normally and let **IronRust-Wipe** do its magic in thebackground, or perform a cleanup-proccess (basically visit every place with**IronRust** content) then remove the **IronRust-Wipe** mod file

The procedure is simple: it replaces **IronRust** IDs with vanilla ones duringchunk-reading, works on world blocks, dropped items, entities, block entities,nested entities, inventories, nested inventories... and basically everywhere,just make sure you visit every place that contains **IronRust** content. You cansafelly remove the uninstaller mod file once you're sure enough that everythinghas been converted back to vanilla.
</details>