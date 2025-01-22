execute at @a[gamemode=adventure] if block ~ ~ ~-1 minecraft:lime_stained_glass run particle minecraft:dust 0 1 0 100 ~ ~ ~ 1 1 1 0 200 normal @a
execute at @a[gamemode=adventure] if block ~ ~ ~-1 minecraft:lime_stained_glass run playsound minecraft:block.note_block.bit ambient @a ~ ~ ~ 100 1 1
execute at @a[gamemode=adventure] if block ~ ~ ~-1 minecraft:lime_stained_glass run tp @p ~ ~ ~60

execute at @a[gamemode=adventure] if block ~ ~ ~1 minecraft:lime_stained_glass run particle minecraft:dust 0 1 0 100 ~ ~ ~ 1 1 1 0 200 normal @a
execute at @a[gamemode=adventure] if block ~ ~ ~1 minecraft:lime_stained_glass run playsound minecraft:block.note_block.bit ambient @a ~ ~ ~ 100 1 1
execute at @a[gamemode=adventure] if block ~ ~ ~1 minecraft:lime_stained_glass run tp @p ~ ~ ~-60

execute at @a[gamemode=adventure] if block ~1 ~ ~ minecraft:lime_stained_glass run particle minecraft:dust 0 1 0 100 ~ ~ ~ 1 1 1 0 200 normal @a
execute at @a[gamemode=adventure] if block ~1 ~ ~ minecraft:lime_stained_glass run playsound minecraft:block.note_block.bit ambient @a ~ ~ ~ 100 1 1
execute at @a[gamemode=adventure] if block ~1 ~ ~ minecraft:lime_stained_glass run tp @p ~-60 ~ ~

execute at @a[gamemode=adventure] if block ~-1 ~ ~ minecraft:lime_stained_glass run particle minecraft:dust 0 1 0 100 ~ ~ ~ 1 1 1 0 200 normal @a
execute at @a[gamemode=adventure] if block ~-1 ~ ~ minecraft:lime_stained_glass run playsound minecraft:block.note_block.bit ambient @a ~ ~ ~ 100 1 1
execute at @a[gamemode=adventure] if block ~-1 ~ ~ minecraft:lime_stained_glass run tp @p ~60 ~ ~
