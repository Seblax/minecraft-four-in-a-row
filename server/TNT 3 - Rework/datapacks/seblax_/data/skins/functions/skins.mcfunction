#Darle vueltas al armor stand del loby
execute at @e[name="Quitar Particula"] run tp @e[name="Quitar Particula"] ~ ~ ~ ~2 ~

function skins:coal
function skins:villager
function skins:frost_soul
function skins:bruja
function skins:heart
function skins:nube
function skins:fire
function skins:none
function skins:rainbow

#Skins secretas
function skins:samurai
function skins:piglin
function skins:picolo
function skins:portal

#Rainbow
scoreboard players add @e[tag=l] Rain 1
scoreboard players set @e[tag=l,scores={Rain=140..}] Rain 0

#Skin Lobby

execute at @e[type=minecraft:armor_stand,name="Happy"] if entity @p[distance=..4] run particle minecraft:happy_villager ~ ~ ~ 0.25 1 0.25 0 3 force @p
execute at @a[scores={Particulas=4}] run particle minecraft:happy_villager ~ ~ ~ 0 0 0 0.015 10 normal @a

execute at @e[type=minecraft:armor_stand,name="Corazón"] if entity @p[distance=..4] run particle minecraft:heart ~ ~ ~ 0.25 1 0.25 0 3 force @p
execute at @a[scores={Particulas=5}] run particle minecraft:soul ~ ~ ~ 0 0 0 0.015 10 normal @a

execute at @e[type=minecraft:armor_stand,name="Humo"] if entity @p[distance=..4] run particle minecraft:campfire_cosy_smoke ~ ~ ~ 0.25 1 0.25 0 3 force @p
execute at @a[scores={Particulas=3}] run particle minecraft:campfire_signal_smoke ~ ~ ~ 0 0 0 0.015 1 normal @a

execute at @e[type=minecraft:armor_stand,name="Witch"] if entity @p[distance=..4] run particle minecraft:witch ~ ~ ~ 0.25 1 0.25 0 3 force @p
execute at @a[scores={Particulas=6}] run particle minecraft:witch ~ ~ ~ 0 0 0 0.015 10 normal @a

execute at @e[type=minecraft:armor_stand,name="Rainbow"] if entity @p[distance=..4] run particle minecraft:dust 1000 1000 1000 10 ~ ~ ~ 0.25 1 0.25 0 1 force @p
execute at @a[scores={Particulas=2}] run particle minecraft:dust 10000 10000 10000 2.5 ~ ~ ~ 0 0 0 0.015 10 normal @a

execute at @e[type=minecraft:armor_stand,name="Nubes"] if entity @p[distance=..4] run particle minecraft:cloud ~ ~ ~ 0.25 1 0.25 0 3 force @p
execute at @a[scores={Particulas=7}] run particle minecraft:heart ~ ~ ~ 0.25 0.25 0.25 0.015 1 normal @a

execute at @e[type=minecraft:armor_stand,name="Soulfire"] if entity @p[distance=..4] run particle minecraft:soul ~ ~ ~ 0.25 1 0.25 0 3 force @p
execute at @a[scores={Particulas=8}] run particle minecraft:cloud ~ ~ ~ 0.25 0.25 0.25 0.015 10 normal @a

execute at @e[type=minecraft:armor_stand,name="Fuego"] if entity @p[distance=..4] run particle minecraft:flame ~ ~ ~ 0.25 1 0.25 0 3 force @p
execute at @a[scores={Particulas=1}] run particle minecraft:flame ~ ~ ~ 0 0 0 0.015 10 normal @a

#Skins secretas
execute at @e[type=minecraft:armor_stand,tag=gold] if entity @p[distance=..6] run particle minecraft:wax_on ~ ~1 ~ 0.25 1 0.25 1 3 normal @p
execute at @a[scores={Particulas=9}] run particle minecraft:falling_lava ~ ~1 ~ 0.1 0.1 0.1 1 1 normal @a
execute at @a[scores={Particulas=9}] run particle minecraft:lava ~ ~ ~ 0.1 0.1 0.1 1 1 normal @a

execute at @e[type=minecraft:armor_stand,tag=samurai] if entity @p[distance=..10] run particle minecraft:cherry_leaves -72 75 -73 0.25 1 0.25 1 3 normal @p
execute at @a[scores={Particulas=10}] run particle minecraft:cherry_leaves ~ ~1 ~ 0.1 0.1 0.1 1 1 normal @a
execute at @a[scores={Particulas=10}] run particle minecraft:effect ~ ~ ~ 0.1 0.1 0.1 1 1 normal @a

execute at @e[type=minecraft:armor_stand,tag=picolo] if entity @p[distance=..10] run particle minecraft:crit 13 29 -91 0.25 0.25 0.25 0.05 3 normal @p
execute at @a[scores={Particulas=11}] run particle minecraft:electric_spark ~ ~1 ~ 0.25 0.25 0.25 1 2 normal @a
execute at @a[scores={Particulas=11}] run particle minecraft:scrape ~ ~ ~ 0.2 0.2 0.2 1 5 normal @a

execute at @a[scores={Particulas=12}] run particle minecraft:end_rod ~ ~1 ~ 0.25 0.25 0.25 0 2 normal @a
execute at @a[scores={Particulas=12}] run particle minecraft:dripping_obsidian_tear ~ ~ ~ 0.25 0.25 0.25 0.1 5 normal @a