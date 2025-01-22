tp @a[scores={ifern=1..}] 44.93 11 56.02 -90 0

execute at @a[scores={ifern=1..}] run playsound entity.enderman.teleport ambient @p ~ ~ ~ 1000000 1 1
scoreboard players reset @a[scores={ifern=1..}] ifern

particle minecraft:dust 1000 1000 1000 100 44.93 11 56.02 1 1 1 0 200 force @a