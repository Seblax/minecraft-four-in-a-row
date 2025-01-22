tp @a[scores={castle=1..}] -88 54 31 -90 ~
execute at @a[scores={castle=1..}] run playsound entity.enderman.teleport ambient @p ~ ~ ~ 1000000 1 1
scoreboard players reset @a[scores={castle=1..}] castle

particle minecraft:dust 1000 1000 1000 100 -88 54 31 1 1 1 0 200 force @a