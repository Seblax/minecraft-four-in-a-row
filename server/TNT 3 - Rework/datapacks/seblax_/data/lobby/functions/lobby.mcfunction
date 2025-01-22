tp @a[scores={tnt=1..}] 51.99 8.00 -48.99 ~ ~

execute at @a[scores={tnt=1..}] run playsound entity.enderman.teleport ambient @p ~ ~ ~ 1000000 1 1
scoreboard players reset @a[scores={tnt=1..}] tnt

particle minecraft:dust 1000 1000 1000 100 51.99 8.00 -48.99 1 1 1 0 200 force @a