tag @a[x=79,y=11,z=-56,dx=16,dy=16,dz=16] add afk
title @a[x=79,y=11,z=-56,dx=16,dy=16,dz=16] actionbar ["",{"text":"Estás en la ","color":"red"},{"text":"[AFK]","bold":true,"color":"dark_red"},{"text":" zone","color":"red"}]

execute at @a[tag=afk] unless entity @p[x=79,y=11,z=-56,dx=16,dy=16,dz=16] run tag @p remove afk
execute at @a[tag=afk] if entity @p[x=50,y=4,z=-51,dx=3,dy=4,dz=3,tag=afk] run tag @p remove afk
