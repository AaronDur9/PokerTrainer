# PokerTrainer
PokerTrainer es un framework que permite tanto a jugadores humanos como bots enfrentarse en partidas de Poker siguiendo las reglas del Texas Holdem Poker.
Simula una mesa de un torneo de Poker, donde el número de jugadores se debe establecer antes del inicio de cada partida.

Una vez asignado el número de jugadores, se podrán customizar los siguientes parámetros de cada uno de ellos:

  ● Nombre del jugador
  
  ● Stack inicial de cada jugador (por defecto 1000 y como mínimo 1)
  
  ● Si será bot o humano
  
    ○ En caso de ser bot se debe seleccionar la clase con su código fuente dentro del directorio de bots (botsDirectory).
En esta misma ventana se podrá seleccionar si las cartas de los bots serán visibles o no durante la partida, y ofrece la posibilidad de establecer un límite a las apuestas de los jugadores durante una mano (CAP: por defecto y como mínimo 100).

Tras customizar los jugadores aparece la interfaz principal de juego, en la cual se muestra la mesa de juego con los jugadores y sus respectivas informaciones como su nombre, el stack, la cantidad que van apostando y sus cartas. Además es visible el timer para cada jugador (por defecto 30 segundos) y el menú con los botones que representan las distintas acciones que puede realizar el jugador que tiene turno (Call, Check, Fold, Raise to, Bet, All-in).
Al arrancar esta última ventana, se muestra de forma paralela una ventana de depuración que irá recogiendo las distintas acciones realizadas por los jugadores, los ganadores de cada mano y además, si varios jugadores llegan al showDown sus cartas quedarán recogidas en esta ventana.
