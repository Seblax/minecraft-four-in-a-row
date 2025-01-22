particle minecraft:dust 1000 1000 1000 100 51.99 8.00 -48.99 1 1 1 0 200 force @a
playsound minecraft:entity.guardian.death ambient @a 51.99 8.00 -48.99 10 1 1
tp @a[team=!AFK] 51.99 8.00 -48.99 ~ ~
particle minecraft:dust 1000 1000 1000 100 51.99 8.00 -48.99 1 1 1 0 200 force @a
fill 14 4 15 24 4 15 minecraft:redstone_block destroy
stopsound @a weather
bossbar set minecraft:tnt visible false
scoreboard objectives setdisplay sidebar
clear @a