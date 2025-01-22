tp @a[scores={ninja=1..}] -72 83 -71 0 0
execute at @a[scores={ninja=1..}] run playsound entity.enderman.teleport ambient @p ~ ~ ~ 1000000 1 1
scoreboard players reset @a[scores={ninja=1..}] ninja

particle minecraft:dust 1000 1000 1000 100 -72 83 -71 1 1 1 0 200 force @a