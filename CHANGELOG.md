## IronRust v1.0.3F

Final version, no more features will be added unless Mojang adds some new metal materials or new behaviour to the oxidizing mechanic

+ Released NeoForge beta-experimental version (pretty untested)
+ Dispensers can now apply active honeycomb on blocks
+ Lightning strikes decrease oxidized state
+ Added Spanish (all variants) translations
+ Added Chinese (traditional) localization (by CurseForge:[junshengxie](https://legacy.curseforge.com/members/junshengxie))
+ Added **auto-generated** localizations: Catalan, Euskera, Portuguese, Italian, French, Russian, German, Japanese

## IronRust-Wipe v1.0.1 (bumped)
+ Automatic IronRust changes reverter via on-read NBT injection, actively converts IDs during chunk-reading (notified in log)
+ block/items converted are:
    - [coated] exposed|weathered|oxidized items/blocks -> vanilla clean equivalent
    - [coated] unaffected|exposed|weathered|oxidized Iron Grate block -> vanilla Iron block
    - Active Honeycomb item/block -> vanilla standard Honeycomb item/block
+ locations covered are:
    - world blocks
    - block entities slots
    - main player inventory
    - entities inventories
    - dropped items
    - item frames
    - bundles

<hr><br>

## IronRust v1.0.2B
+ Fixed a (stupid) bug causing no block were dropping its item on break
+ Lowered oxidation rate

## IronRust v1.0.1B
+ Block: Iron Grate Block
+ Block: exposed|weathered|oxidized variants of all major iron blocks _(Block|Grate|Door|Trapdoor|Bars|Chain)_
+ Block: Coated variants of all unaffected|exposed|weathered|oxidized blocks _(Block|Grate|Door|Trapdoor|Bars|Chain)_
+ Item: Active Honeycomb
+ Block: Active Honeycomb Block
+ Water affects oxidation probability
+ Axes de-oxidizing on use
+ Coat On|Off advancements
+ Active Honeycomb advancements
+ Recipe book entries for all new recipes
+ Server/Client basic checks