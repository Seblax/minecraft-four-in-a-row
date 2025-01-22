#Dar emotes
item replace entity @a[scores={rol=1}] container.8 with minecraft:green_dye{display:{Name:'[{"text":"BUENÍSIMA","italic":false,"color":"dark_green"}]',Lore:['[{"text":"Lanzalo con Q","italic":false}]','[{"text":"para burlarte","italic":false}]']},Enchantments:[{}]} 9
item replace entity @a[scores={rol=1}] container.7 with minecraft:red_dye{display:{Name:'[{"text":"PRINGAO","italic":false,"color":"dark_red"}]',Lore:['[{"text":"Lanzalo con Q","italic":false}]','[{"text":"para burlarte","italic":false}]']},Enchantments:[{}]} 8
item replace entity @a[scores={rol=1}] container.6 with minecraft:brown_dye{display:{Name:'[{"text":"GG!","italic":false,"color":"aqua"}]',Lore:['[{"text":"Lanzalo con Q","italic":false}]','[{"text":"para burlarte","italic":false}]']},Enchantments:[{}]} 7
item replace entity @a[scores={rol=1}] container.5 with minecraft:yellow_dye{display:{Name:'[{"text":";D","italic":false,"color":"gold"}]',Lore:['[{"text":"Lanzalo con Q","italic":false}]','[{"text":"para burlarte","italic":false}]']},Enchantments:[{}]} 6

#Ejecutar emotes
execute as @a[scores={Emote1=1..}] run function emotes:pringao
execute as @a[scores={Emote2=1..}] run function emotes:buenisima
execute as @a[scores={Emote3=1..}] run function emotes:gg 
execute as @a[scores={Emote4=1..}] run function emotes:happy