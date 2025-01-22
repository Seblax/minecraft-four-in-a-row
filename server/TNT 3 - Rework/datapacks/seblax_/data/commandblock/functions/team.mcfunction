#jugadores activos
team join jugadores @a[scores={rol=1},team=!AFK]

#jugadores espectadores
team join Espectadores @a[scores={rol=0},team=!AFK]

#jugadores AFK
team join AFK @a[tag=afk]
team leave @a[tag=!afk,team=AFK]