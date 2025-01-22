#Eliminar items al lanzarlos
kill @e[type=minecraft:item]
#gamemode adventure @a
clear @a[x=51,y=8.5,z=-50,dx=1,dy=5,dz=1]

#set el world spawn en el lobby cuando no se está jugando
execute if entity @e[tag=j,scores={d=0}] unless entity @e[tag=l,scores={level=1..4}] run setworldspawn 21 25 -45 1

#efectos
effect give @a minecraft:resistance infinite 255 true
effect give @a minecraft:saturation infinite 255 true

effect give @a[x=21,y=25,z=-45,dy=5] minecraft:night_vision infinite 255 true

#Randomtick Speed
gamerule randomTickSpeed 3
gamerule spawnRadius 0

#Settters d elos scoreboardsa
scoreboard players add @a rol 0
scoreboard players add @e[tag=l] level 0
scoreboard objectives setdisplay list

#Change worldSpawn
execute if entity @e[tag=j,scores={d=1}] if entity @e[tag=l,scores={level=1}] run spawnpoint @a -72 83 -71 0
execute if entity @e[tag=j,scores={d=1}] if entity @e[tag=l,scores={level=2}] run spawnpoint @a -88 54 31 -90
execute if entity @e[tag=j,scores={d=1}] if entity @e[tag=l,scores={level=3}] run spawnpoint @a 6 56 181 180
execute if entity @e[tag=j,scores={d=1}] if entity @e[tag=l,scores={level=4}] run spawnpoint @a 44 11 56 -90

#Particulas en armor stand de bajada y subida de los mapas
execute at @e[name="subida",type=minecraft:armor_stand] if entity @a[distance=..5] run particle minecraft:dust 0 10 0 100 ~ ~ ~ 0.25 1 0.25 10 1 normal @a
execute at @e[name="bajada",type=minecraft:armor_stand] if entity @a[distance=..5] run particle minecraft:dust 10 0 0 100 ~ ~ ~ 0.25 1 0.25 10 1 normal @a

#botas en basalto
execute at @a if block ~ ~-1 ~ minecraft:magma_block run item replace entity @p armor.feet with golden_boots{display:{Name:'[{"text":"Botas para no quemarte los feets","italic":false,"color":"dark_red"}]'},Enchantments:[{id:"frost_walker",lvl:2}],HideFlags:27,AttributeModifiers:[{AttributeName:"generic.knockback_resistance",Amount:1,Operation:0,UUID:[I;-121424,23760,124614,-47520],Name:"generic.knockback_resistance"}]} 1
execute at @a[nbt={Inventory:[{id:"minecraft:golden_boots"}]}] unless block ~ ~-1 ~ minecraft:magma_block run clear @p[nbt={Inventory:[{id:"minecraft:golden_boots"}]}] minecraft:golden_boots

#kills
execute at @a[gamemode=adventure,scores={rol=1}] if entity @e[tag=j,scores={d=1}] if block ~ ~ ~ lava run kill @p
execute at @a[gamemode=adventure,scores={rol=1}] if entity @e[tag=j,scores={d=1}] if block ~ ~-1 ~ minecraft:ochre_froglight run kill @p

#Partículas mapas
execute if entity @a[x=-99,y=16,z=-61,dx=54,dy=100,dz=54] run particle minecraft:cherry_leaves -72 49 -34 25 30 25 1 10 force @a

#Velocidad siempre
#effect give @a speed infinite 0 true

#Set experience
xp set @a 0 levels