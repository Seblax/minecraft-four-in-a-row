tp @a[scores={lab=1..}] 28 69 103 0 90

execute at @a[scores={lab=1..}] run playsound entity.enderman.teleport ambient @p ~ ~ ~ 1000000 1 1
scoreboard players reset @a[scores={lab=1..}] lab

particle minecraft:dust 1000 1000 1000 100 28 69 103 1 1 1 0 200 force @a