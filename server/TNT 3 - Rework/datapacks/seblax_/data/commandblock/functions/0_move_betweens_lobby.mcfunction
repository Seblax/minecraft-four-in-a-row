#Dar los items para teletransoprtarse

#Lobby
item replace entity @a[scores={rol=0}] container.0 with tnt{display:{Name:'[{"text":"L","italic":false,"color":"dark_red","bold": true},{"text":"o","color":"white","bold": true},{"text":"b","color":"black","bold": true},{"text":"b","color":"white","bold": true},{"text":"y","bold": true}]',Lore:['[{"text":"Lanzalo usando la Q","italic":false}]','[{"text":"para Tepearte al","italic":false}]','[{"text":"Lobby","italic":false,"color":"gold"}]']}} 1

#Ninja
item replace entity @a[scores={rol=0}] container.1 with music_disc_stal{display:{Name:'[{"text":"Ninja Temple","italic":false,"color":"gold","bold": true}]',Lore:['[{"text":"Lanzalo usando la Q","italic":false}]','[{"text":"para Tepearte al","italic":false}]','[{"text":"Templo Ninja","italic":false,"color":"gold"}]']}} 1

#Lab
item replace entity @a[scores={rol=0}] container.2 with music_disc_mellohi{display:{Name:'[{"text":"Laboratorio","italic":false,"color":"green","bold": true}]',Lore:['[{"text":"Lanzalo usando la Q","italic":false}]','[{"text":"para Tepearte al","italic":false}]','[{"text":"Laboratorio","italic":false,"color":"gold"}]']}} 1

#Inferno
item replace entity @a[scores={rol=0}] container.3 with wither_skeleton_skull{display:{Name:'[{"text":"Infierno","italic":false,"color":"dark_red","bold": true}]',Lore:['[{"text":"Lanzalo usando la Q","italic":false}]','[{"text":"para Tepearte al","italic":false}]','[{"text":"Infierno","italic":false,"color":"gold"}]']}} 1

#Castillo Pinglin
item replace entity @a[scores={rol=0}] container.4 with shield{display:{Name:'[{"text":"Castillo Piglin","italic":false,"color":"dark_aqua","bold": true}]',Lore:['[{"text":"Lanzalo usando la Q","italic":false}]','[{"text":"para Tepearte al","italic":false}]','[{"text":"Castillo Piglin","italic":false,"color":"gold"}]']}}

#Perla de velocidad
item replace entity @a[scores={rol=0}] container.8 with carrot{display:{Name:'[{"text":"Perla de Velocidad","italic":false,"color":"dark_red","bold": true}]',Lore:['[{"text":"Mantenla seleccionada","italic":false,"color":"white"}]','[{"text":"para aumentar la","italic":false,"color":"white"}]','[{"text":"velocidad","italic":false,"color":"white"},{"text":" ","color":"dark_purple"},{"text":"x4","color":"dark_aqua"}]']}} 1



#Dar velocidad cuando se equipe la perla de la velocidad
effect give @a[nbt={SelectedItem:{id:"minecraft:carrot"}}] minecraft:speed 1 4 true

#Tepeo a ninja
execute if entity @a[scores={ninja=1..}] run function lobby:ninja

#Tepeo a lab
execute if entity @a[scores={lab=1..}] run function lobby:lab

#Tepeo a lab
execute if entity @a[scores={ifern=1..}] run function lobby:inferno

#Tepeo a Castillo
execute if entity @a[scores={castle=1..}] run function lobby:castle

#Tepeo a Lobby
execute if entity @a[scores={tnt=1..}] run function lobby:lobby