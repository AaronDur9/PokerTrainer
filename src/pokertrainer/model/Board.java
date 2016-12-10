/*
Autores:
-Aarón Durán Sánchez
-Javier López de Lerma
-Mateo García Fuentes
-Carlos López Martínez


 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pokertrainer.model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Random;
import java.util.TreeMap;
import pokertrainer.controller.GameController;


/**
 *
 * @author usuario_local
 */
public class Board {

    private LinkedList<Card> deck;
    static final int CARDS_HAND = 5;
    static final int NUMBER_CARDS = 52;
    static final int BIG_BLIND = 10;
    static final int SMALL_BLIND = 5;
	
    private int numCardsDeck;
    private int numPlayers;
    private LinkedList<Player> players;
    private LinkedList<Player> handPlayers;
    private LinkedList<Card> boardCards;
    ///
    private LinkedList<Player> playerLoses;
    ///
    private int numPot;
    private int[] seats;
    private int turn;
    private int bigBlind;
    private int smallBlind;
    private int hand;
    private State state;
    private LinkedList<Pot> potList;
    
    private LinkedList<Card> burntCards;

    private int posBigBlind;
    private int posSmallBlind;
    
    private int maxBet;
    //Indica el asiento del ultimo en hacer raise
    private int lastToSpeak;
    
    
    //Disposición de los asientos segun el numero de jugadores
    public static final int[] TWO = {0,4}; 
    public static final int[] THREE = {1,4,7}; 
    public static final int[] FOUR = {1,3,5,7}; 
    public static final int[] FIVE = {1,3,4,5,7}; 
    public static final int[] SIX = {0,1,3,4,5,7}; 
    public static final int[] SEVEN = {0,1,2,3,4,5,6}; 
    public static final int[] EIGHT = {0,1,2,3,4,5,6,7}; 
    public static final int[] NINE = {0,1,2,3,4,5,6,7,8}; 
	
	
    public static final int MONEY = 1000;
    private boolean finished = false;
    private GameController c;
	
    public Board(int numPlayers){
        this.numCardsDeck = NUMBER_CARDS;
        this.bigBlind = BIG_BLIND;
        this.smallBlind = SMALL_BLIND;
        this.burntCards = new LinkedList<>();
        this.numPot = 0;
        this.state = State.PREFLOP;
        this.turn = 0;
        this.hand = 0;
        this.posSmallBlind = 0;
        this.posBigBlind = 1;
        this.boardCards = new LinkedList<>();
        this.playerLoses = new LinkedList<>();
        generateDeck();
        initializePlayers(numPlayers);
        

        
    }
    /*
    public Board() {
        this.numCardsDeck = NUMBER_CARDS;
        this.bigBlind = BIG_BLIND;
        this.smallBlind = SMALL_BLIND;
        this.burntCards = new LinkedList<>();
        this.state = State.PREFLOP;
        this.turn = 0;
        this.hand = 0;
        this.posSmallBlind = 0;
        this.posBigBlind = 1;
        
        LinkedList<Card> cards1 = new LinkedList<>();
        Card c1 = new Card(Suit.DIAMONDS, Value.KING);
        cards1.add(c1);
        Card c2 = new Card(Suit.SPADES, Value.KING);
        cards1.add(c2);
        Card c3 = new Card(Suit.CLUBS, Value.QUEEN);
        cards1.add(c3);
        Card c4 = new Card(Suit.HEARTS, Value.QUEEN);
        cards1.add(c4);
        Card c5 = new Card(Suit.DIAMONDS, Value.JACK);
        cards1.add(c5);
        Card c6 = new Card(Suit.HEARTS, Value.JACK);
        cards1.add(c6);
        Card c7 = new Card(Suit.SPADES, Value.TWO);
        cards1.add(c7);
        
       
        
        LinkedList<Card> cards2 = new LinkedList<>();
        Card c8 = new Card(Suit.DIAMONDS, Value.JACK);
        cards2.add(c8);
        Card c9 = new Card(Suit.CLUBS, Value.JACK);
        cards2.add(c9);
        Card c10 = new Card(Suit.HEARTS, Value.TEN);
        cards2.add(c10);
        Card c11 = new Card(Suit.CLUBS, Value.TEN);
        cards2.add(c11);
        Card c12 = new Card(Suit.DIAMONDS, Value.TEN);
        cards2.add(c12);
        Card c13 = new Card(Suit.SPADES, Value.TEN);
        cards2.add(c13);
        Card c14 = new Card(Suit.CLUBS, Value.TWO);
        cards2.add(c14);
                

        Player player1 = new Player("Player1", MONEY, 0, Role.NONE, cards1,0);
        Player player2 = new Player("Player2", MONEY, 0, Role.NONE, cards2,1);
      
        this.numPlayers = 2;
        players = new LinkedList<>();
        this.players.add(player1);
        this.players.add(player2);
        

        this.players.get(0).setHand(getHand(sortHand(cards1)));
        this.players.get(1).setHand(getHand(sortHand(cards2)));
        
          //Una vez que se ha establecido la mano de cada jugador se llama al método que dice quien ha ganado
        //Se le debe pasar el linkedList de jugadores que juegan la mano
        
        System.out.println("Jugador1" + " " + this.players.get(0).getHand());
        System.out.println("Jugador2" + " " + this.players.get(1).getHand());
        
        LinkedList<Player> winners = whoWin(this.players);
        System.out.print("The winners are: ");
        for(Player p: winners)
            System.out.print(p.getName() + " ");
 
    }
      
    */
    
    private void generateDeck() {
        this.deck = new LinkedList<>();
       
        for (Value val: Value.values()) 
            deck.push(new Card(Suit.HEARTS, val));
        
        for (Value val: Value.values()) 
            deck.push(new Card(Suit.DIAMONDS, val));
        
        for (Value val: Value.values()) 
            deck.push(new Card(Suit.CLUBS, val));
        
        for (Value val: Value.values()) 
            deck.push(new Card(Suit.SPADES, val)); 
        
    }

    
    private void initializePlayers(int numPlayers) {
      this.players = new LinkedList<>();
      this.numPlayers = numPlayers;
      this.seats = getSeatsForPlayers(numPlayers);
      for (int i = 0; i < this.numPlayers; i++) {
          Player p;
          if(i==3){
              p = new PlayerBot("Player" + (i+1), MONEY, seats[i],i);
          }else{
              p =  new PlayerHuman("Player" + (i+1), MONEY, seats[i],i);
          }
          this.players.add(p);
      }

      preflop();      
    }   	
    
    
    
      //metodo que realiza el preflop
    public void preflop(){
        //Inicializa el array con el orden en el que hablan
        initializeHandPlayers();
        
        this.boardCards = new LinkedList<>();
        
        this.potList = new LinkedList<>();
       
        //Apuestan las ciegas
        this.potList.add(new Pot(0, 0, new LinkedList<>(), new LinkedList<>()));
        
        
        //Se reparten 2 cartas a cada jugador
        for (Player p: players) {
            LinkedList<Card> cards = deal(2);
            this.burntCards.addAll(cards);
            p.setCards(cards);
        }
        this.players.get(posSmallBlind).setRole(Role.SMALL_BLIND);
        this.players.get(posBigBlind).setRole(Role.BIG_BLIND);
        this.lastToSpeak = -1;
        this.maxBet = BIG_BLIND;
        
        blindsBet();
        
            
       
    }

     //quitamos dinero de las ciegas a cada jugador
    private void blindsBet() {
        
        Player smPlayer = this.players.get(this.posSmallBlind);
        Player bbPlayer = this.players.get(this.posBigBlind);
        refreshBet(smPlayer, this.smallBlind);
        refreshBet(bbPlayer, this.bigBlind);
    }
    

    
    //Le paso el dinero que hay que poner y calcula el que debe poner este jugador en concreto
    public void refreshBet(Player player, int bet){
        int b = player.bet(bet);
        
  
        int bAux = b;
        //Lo que un jugador ya ha apostado aunque le hagan Re-Raise no debe volver a meterlo en
        //el bote.
        int discount = player.getTotalBet();
        //ACTUALIZAR BOTESSSSSSSSSS
        int i = 0;
        
        while(i < this.potList.size() && b > 0) {
            Pot pot = this.potList.get(i);
            
            int auxBet = pot.getBet();
            
            
            
            if(pot.getPlayers().contains(player)) {
                //Estas haciendo un raise en un bote que ya habías jugado
                if((b + discount) > auxBet && (this.potList.size() - 1 == i)) {
                    pot.removePlayersForRaise(player);
                    pot.addPlayers(player);
                    pot.setBet(b+discount);
                    pot.addTotalPot(b);
                    //this.maxBet = player.getBet() + b;
                    player.setBetLastPot(b + discount);
                }
                discount -= pot.getBet();
            }
            else {
                //Estas haciendo un raise en un bote que aún no había jugado
                if((b + discount) > auxBet && (this.potList.size() - 1 == i)) {
                    
                    //Comprobamos si había algún jugador en allin
                    boolean plaAllIn = false;
                    //Si el jugador hace un raise y en ese bote había algún jugador/es que estaba allin hay que dividir el bote para este jugador/es
                    int j = 0; 
                    while(j < pot.getPlayers().size() && !plaAllIn){
                        if(pot.getPlayers().get(j).getMoney() == 0)
                            plaAllIn = true;
                        j++;
                    }
                    //Si había jugadores en allin dividimos el bote para estos
                    if(plaAllIn) {
                        
                        
                        int newP = (b + discount) - auxBet;
                        //Y ahora creamos un nuevo bote para el jugador del raise
                        //Este nuevo bote será de la apuesta del jugador menos lo que ha aportado al anterior bote
                        
                        LinkedList<Player> play = new LinkedList<>();
                        LinkedList<Player> outPla = new LinkedList<>();
                        play.add(player);
                        Pot p = new Pot(newP, newP, play,outPla);
                        this.potList.add(i+1, p);
                        
                        
                        //Lo que hay que hacer es añadir el dinero que aporta el jugador que ha hecho el raise
                        //al bote antiguo
                        //¿Y SI YA HABÍA APORTADO DINERO A ESE BOTE?
                        int betL = b;
                        
                        if(auxBet >= discount) {
                            pot.addTotalPot(auxBet - discount);
                            betL -= (auxBet - discount);
                            discount = 0;
                        }
                        else {
                            discount -= auxBet;
                            
                        }
                        
                        //Añadimos al jugador del raise en el anterior bote
                        pot.addPlayers(player);
                        player.setBetLastPot(betL);
                        
                        
                        b = 0;
                        
                    }
                    else {
                        pot.setBet(b+discount);
                        pot.addTotalPot(b); 
                        //this.maxBet =  b;
                        //Si hace un raise esta será su apuesta en el último bote que jugó
                        player.setBetLastPot(b + discount);


                        pot.removePlayersForRaise(player);
                        pot.addPlayers(player);
                    }
                    
                    
                    
                }
                //Si el jugador tiene suficiente dinero para jugar este bote
                //Sumamos lo que el jugador ya había puesto(por si re-raise)
                else if((b + discount) >= auxBet) {
                    //Si la apuesta es igual a la bet de este bote será el últim bote que juegue
                    if(b + discount == auxBet)
                        player.setBetLastPot(b + discount);
                    if(discount == 0){    
                        pot.addTotalPot(auxBet);
                        pot.addPlayers(player);
                        b -= auxBet;
                    }else{
                        int currentD = auxBet - discount;
                       
                            pot.addTotalPot(currentD);
                            b = b - currentD;
                            if(auxBet >= discount)
                                discount = 0;
                        
                        pot.addPlayers(player);
                    }
                    
                }
                //En el caso de que no tengas dinero suficiente para el último de los botes no es necesario
                //calcular cuanto pusiste en el último bote ya que esto solo sirve para el caso en el que te hacen un raise
                //y los raise solo se pueden realizar al último bote.
                else {
                    
                    //Lo que hay que tratar con cuidado es como repartir el dinero de los botes teniendo en cuenta que puede haber dinero
                    //de un jugador en un bote el cual(EL JUGADOR) no esté ya en el bote.
                    
                    //DINERO PARA EL NUEVO BOTE
                    //Los jugadores que aparecían como participantes pondrán en el nuevo bote el dinero que tenga 
                    //este jugador que está haciendo que se parta el bote 
                    int newPot = (b + discount) * pot.getPlayers().size();
                    //Para calcular el dinero de otros jugadores debo comprobar cuanto pusieron en su último bote
                    //los jugadores que fueron eliminados de este bote por un raise
                    LinkedList<Player> playerForNewpot = new LinkedList<>();
                    //Lista de jugadores que aparecerán como participantes en el nuevo bote.
                    LinkedList<Player> pla = new LinkedList<>();
                    
                    //No puedo eliminar directamente del array outForRaise
                    LinkedList<Player> playerForDelete = new LinkedList<>();
                    for(Player py: pot.getPlayersOutForRaise()) {
                        //Si el dinero que puso el jugador es menor o igual que el que hay que poner en este nuevo bote
                        if(py.getBetLastPot() <= (b + discount)){
                            //Su dinero se meterá íntegramente en este nuevo bote y 
                            
                            newPot += py.getBetLastPot();
                            //ahora aparecerá en la lista de jugadores que han aportado dinero a este bote aunque no aparecen como participantes.
                            if(player != py)
                                playerForNewpot.add(py);
                            //Y desaparecerá de la lista del anterior bote(ya que ya no aporta dinero en ese bote).
                            //pot.delPlayerOut(py);
                            playerForDelete.add(py);
                        }
                        //El caso en el que el dinero del jugador se deba repartir entre el nuevo bote y el antiguo
                        else{
                            //Lo equivalente a la apuesta de este nuevo bote se meterá en este.
                            newPot += (b + discount);
                            //Reducimos el dinero que aporta al anterior bote.
                            py.setBetLastPot(py.getBetLastPot() - (b + discount));
                            //Podemos introducirlo como participante del bote (el algoritmo "arreglará" ya que el jugador tendrá que volver a hablar).
                            //Este jugador no puede estar allin porque si te hacen un raise a un allin se parte en dos botes.
                            pla.add(py);

                            
                        }
                    }
                    
                    if(!playerForDelete.isEmpty())
                        for(Player p: playerForDelete)
                            pot.delPlayerOut(p);

                    pla.addAll(pot.getPlayers());
                    pla.add(player);
                    //Número de jugadores que jugaban el anterior bote.
                    int numP = pot.getPlayers().size();
                    //Suma dos veces nuestro jugador?
                    Pot p = new Pot(newPot + b,(b + discount),pla,playerForNewpot);
                    this.potList.add(i, p);
                    //Ahora modifico el bote que antes ocupaba la posicion i.
                    this.potList.get(i+1).subTotalPot((b + discount) * numP);
                    this.potList.get(i+1).subBet(b + discount);
                    b = 0;
                    
                }
            }
            i++;
        }
        
        player.updatePlayer(bAux);
    }
    
    
    public State changeState() {
        int n = (this.state.ordinal() + 1 )%6; //% 6
        State t = State.values()[n];
        this.state = t;
       
        this.turn = 0;
        this.lastToSpeak = -1;
        this.maxBet = 0;
        
        //Se debe actualizar cada vez que un jugador hace una apuesta.
        for (Player p: this.handPlayers) {
            p.setBet(0);
        }
        
        return this.state;
    }
    
    public int getHandPlayersSize() {
        return this.handPlayers.size();
    }
    
    public void removePlayerFromPots(Player p){
        for (Pot pot: this.potList) {
            pot.getPlayers().remove(p);
        }
    }
    
    public int getNextPlayerTotalBet(){
        return handPlayers.get(((this.turn+1)%getHandPlayersSize())).getTotalBet();
    }
    
    public void setHighBet(int bet) {
        this.maxBet = bet;
    }
    
    //Establece como ultimo a hablar el jugador anterior al del turno actual
    public void updateLastToSpeak() {
        int t = (this.turn - 1)%handPlayers.size();
        if(t == -1)
           t = this.handPlayers.size() - 1;
        this.lastToSpeak = this.handPlayers.get(t).getNumPlayer();
    }
            
    
    //******************* ACCIONES DE LOS JUGADORES ************************
    
    //Accion llamada desde el controller cuando en la vista se hace call o check
    /*public GameState playerTurnCalls() {
        Player player = this.handPlayers.get(this.turn);
       
        //Calculamos la apuesta que tiene que realizar para hacer el call
        int actualBet = player.getBet();
        int betToCall = this.maxBet - actualBet;
        
        refreshBet(player, betToCall);
         
        if (handPlayers.size() == 1) return GameState.OVER_ALLIN;
        else return GameState.CONTINUE;
    }*/
    
    //Cuando un jugador hace fold lo eliminamos de todos los botes
    /*public GameState playerTurnFolds() {
        GameState state = GameState.CONTINUE;

        //Si tenemos 2 jugadores y uno se ha retirado
        Player player = getPlayerTurn();
        for (Pot pot: this.potList) {
            pot.getPlayers().remove(player);
        }
        
        if(handPlayers.size() == 2 && isLastToSpeak())
            state = updateCurrentState();
        else if(handPlayers.size() == 2 && handPlayers.get(((this.turn+1)%handPlayers.size())).getTotalBet() >= this.maxBet)
            state = updateCurrentState();
        return state;
    }*/
    
    //Elimina un jugador de handPlayers, es decir, ese jugador no va a hablar mas
    //eso ocurre cuando hace all-in o hace fold
    public void removePlayer(Player p) {
        //Primero eliminas
        handPlayers.remove(p);
        //Y luego divides
        if(this.handPlayers.size() >0 )////AÑADIDO POSTERIOR
             this.turn %= this.handPlayers.size();
    }
    
    
    
    /*public GameState playerTurnRaises(int raiseBet){
        GameState allin = GameState.CONTINUE;
        Player player = getPlayerTurn();
        
        if(raiseBet > this.maxBet) {
            this.maxBet = raiseBet;
            int t = (this.turn - 1)%handPlayers.size();
            if(t == -1)
                t = this.handPlayers.size() - 1;
            this.lastToSpeak = this.handPlayers.get(t).getNumPlayer();
        }
        
        //Restamos la apuesta total que habia realizado 
        raiseBet -= player.getBet();
        
        refreshBet(player, raiseBet);
        
        //Si solo quedaba yo por hablar o si yo voy allin y yo era el último jugador en hablar
        if((handPlayers.size() == 1) || (handPlayers.size() == 2 && player.getMoney() == 0 && isLastToSpeak()))
            //Compruebo si se debe dar el dinero a un jugador o si se debe mostrar el showdown
            allin = updateCurrentState();
        
        return allin;
    }*/
    
    
    public GameState updateCurrentState() {
        GameState state = GameState.CONTINUE;
        //Si solo quedaba yo por hablar y el único que juega algún bote soy yo
        if(this.potList.get(0).getPlayers().size() == 1)
           state = GameState.OVER;
        //Si solo quedaba yo por hablar y en el primer bote hay varios jugadores
        else if(this.potList.get(0).getPlayers().size() > 1)
            state = GameState.OVER_ALLIN;
        
        return state;
    }
    
    //Inicializa el array de handPlayers en orden desde BB + 1 hasta BB
    //Array ordenado en el orden en que hablan los jugadores (Solo valido en preflop)
     private void initializeHandPlayers() {
        this.handPlayers = new LinkedList<>();
        int i = this.posBigBlind + 1;
        i %= this.numPlayers;
        int j = 0;
        while (j < this.numPlayers) {
            handPlayers.add(this.players.get(i));
            
            //El array de jugadores es circular
            i++;
            i %= this.numPlayers;
            
            j++;
        }
        
        //TotalBet a 0, betLastPot a 0, bet a 0
        for (Player p: handPlayers)
            p.initializePlayer();
    }

    //Mete todas las cartas en el deck
    private void reinsertCardsToDeck(){
        this.deck.addAll(this.burntCards);
        this.deck.addAll(this.boardCards);
    }
    
    /*Inicializa los jugadores asignadoles nombre, dinero, asiento, rol y sus cartas
        y llama a preflop
    */

   
    //Devuelve los asientos donde se sentaran los jugadores
    private int[] getSeatsForPlayers(int numPlayers) {
        int[] seatsForPlayers = null;
        switch (numPlayers) {
            case 2: seatsForPlayers = TWO; break;
            case 3: seatsForPlayers = THREE; break;
            case 4: seatsForPlayers = FOUR; break;
            case 5: seatsForPlayers = FIVE; break;
            case 6: seatsForPlayers = SIX; break;
            case 7: seatsForPlayers = SEVEN; break;
            case 8: seatsForPlayers = EIGHT; break;
            case 9: seatsForPlayers = NINE; break;
            default: break;
        }
        return seatsForPlayers;
    }

    public LinkedList<Player> getPlayers() {
        return this.players;
    }

    
    //Devuelve el jugador al que le toca jugar
    public Player getPlayerTurn() {
        return this.handPlayers.get(turn);
    }
    
    //Devuelve la apuesta máxima
    public int getHighBet() {
        return this.maxBet;
    }
    
    public int getBigBlind() {
        return bigBlind;
    }

    public int getSmallBlind() {
        return smallBlind;
    }

    public int getSumOfPots(){
        int sumPots = 0;
        for(Pot p : this.potList){
            sumPots += p.getTotalPot();
        }
        return sumPots;
    }
    
    public int[] getSeats() {
        return this.seats;
    }
    
    //generamos semillas de random
    private LinkedList<Card> deal(int numCards) {
        LinkedList<Card> cards = new LinkedList<>();
        Random rndGenerated = new Random();
        for(int i = 0; i < numCards; i++){
            int rnd = rndGenerated.nextInt(numCardsDeck-1);
            cards.push(deck.get(rnd));
            numCardsDeck--;
            deck.remove(rnd);
        }
        return cards;
    }
   
  
    
   

    //metodo que realiza el flop
    public LinkedList<Card> flop() {
        updateHandPlayers();
        //quemamos
        burntCards.addAll(deal(1));
        //realizamos el flop
        this.boardCards = deal(3);
        return this.boardCards;
    }
    
    private void updateHandPlayers() {
        LinkedList<Player> oldHandPlayers = this.handPlayers;
        LinkedList<Player> newHandPlayers = new LinkedList<>();
        
        int i = this.posSmallBlind;
        
        do {
            if (oldHandPlayers.contains(this.players.get(i))) {
                newHandPlayers.addLast(this.players.get(i));
            }
            i++;
            i %= this.players.size();
            
        } while (i != this.posSmallBlind);
        
        this.handPlayers = newHandPlayers;
        
    }

    
    //metodo que genera el turn(4 Carta) habiendo quemado previamente una carta
    public Card turn() {
        //quemamos
        burntCards.addAll(deal(1));
        //realizamos el turn
        LinkedList<Card> turnCard = deal(1);
        Card c = turnCard.getFirst();
        boardCards.add(3,c);
        return turnCard.peekFirst();
    }
    
    //metodo que genera el river(5 Carta) habiendo quemado previamente una carta
    public Card river() {
        //quemamos
        burntCards.addAll(deal(1));
        //realizamos el river
        LinkedList<Card> riverCard = deal(1);
        Card c = riverCard.getFirst();
        boardCards.add(4,c);
        return riverCard.peekFirst();
    }
    
    private LinkedList<LinkedList<Player>> sharingPots() {
        LinkedList<LinkedList<Player>> winnersPerPot = new LinkedList<>();
        //winnersPerPot.add(new LinkedList<>());
        //BIEN
        int i=0;
        for(Pot pot: this.potList){
            LinkedList<Player> potWinners = new LinkedList<Player>();
            //Si en un bote solo hay un jugador él será el ganador de ese bote.
            if(pot.getPlayers().size() == 1)
                potWinners.add(pot.getPlayers().getFirst());
            else
                potWinners = getWinners(pot.getPlayers(), i);
            
            int cashPerPlayer = pot.getTotalPot()/potWinners.size();
            for(Player p: potWinners){
                p.incrMoney(cashPerPlayer);
                /////////DEPURACIÓN/////////////
                if(p.getHand() != null) {
                    System.out.println("Winner del bote " + i + " " + p.getName() + " Jugada: " + p.getHand().toString());
                    System.out.println(cashPerPlayer);
                    for(Card c : p.getCards()) {
                        System.out.print(" " + c.toString() + " ");                  
                    }
                    System.out.println();
                }
                
                /////////FIN DEPURACIÓN////////////
            }
            winnersPerPot.addLast(potWinners);
            i++;
        }
        
        return winnersPerPot;
    }
    
      
    //CAMBIAD CARLOS JAVI SOLO SE PUEDE HACER UNA  EZ MONGOLES DE ALBA
    private LinkedList<Player> getWinners(LinkedList<Player> playersPot, int numPot){
       //Se llama una vez por bote
       if(numPot == 0)
        for (Player p: playersPot) {
             LinkedList<Card> hand = p.getCards();   
             hand.addAll(this.boardCards);  
             p.setHand(getHand(sortHand(hand))); 
         }
        
        //Una vez que se ha establecido la mano de cada jugador se llama al método que dice quien ha ganado
        //Se le debe pasar el linkedList de jugadores que juegan la mano
        return whoWin(playersPot);
    }
    
    private void incrPlayerPot(int incrMoney, Player p) {
        p.incrMoney(incrMoney);
    }
    
    
    public LinkedList<LinkedList<Player>> showdown() {
        this.finished = true;
        
        //LinkedList<Player> winners = getWinners();
        //Se mira quienes son los ganadores y se reparte el bote
        return sharingPots();
    }
    
    //Devuelve si la partida ha acabado
    public State tryNewHand() {
        
        reinsertCardsToDeck();
        this.state = State.GAME_FINISHED;
        this.finished = false;
        this.burntCards = new LinkedList<>();
        this.numCardsDeck = NUMBER_CARDS;
        
        //Se mira cuantos jugadores tienen mas de 0 de dinero
       /* for (Player p: this.players) {
            if (p.getMoney() == 0) {
                //Se elimina a los jugadores del array
                this.players.remove(p);
                this.numPlayers--;
            }
        }*/
       
       ListIterator<Player> it = this.players.listIterator();
        while(it.hasNext()){
            Player p = it.next();
            if (p.getMoney() == 0) {
                this.playerLoses.add(p);
                it.remove();
                this.numPlayers--;
            }
        }

        
        //Si hay mas de 2 jugadores en el array
        if (this.numPlayers >= 2) {
            
            
            
            //Se incrementa la mano y se cambia el estado a preflop
            this.hand++;
            
            this.players.get(posSmallBlind).setRole(Role.NONE);
            
            posSmallBlind = (posSmallBlind + 1 )%this.numPlayers;
            posBigBlind = (posBigBlind + 1)%this.numPlayers;
            this.state = State.PREFLOP;
            
            for (Player p: players) {
                p.setBet(0);
            }
            //preflop();
        }
        
        return this.state;
        
    }
    
    
    //Cambiar el turno
    public void changeTurn() {
        this.turn++;
        this.turn %= this.handPlayers.size();
    }
    
    
    
    
    public boolean isLastToSpeak() {
       
        return ((lastToSpeak == -1 && handPlayers.getLast() == getPlayerTurn()) 
                || (lastToSpeak != -1 && handPlayers.get((this.turn)).getNumPlayer() == lastToSpeak));
    }
    
    public boolean isFinished() {
        return this.finished;
    }

    public LinkedList<Player> getPlayerLoses() {
        return playerLoses;
    }
    
    
    
    
    
    
    
     
    
    /*
    //Imprime por consola la mejor jugada de cada jugador
    public void GetBestHandOfEachPlayer() {
        for (int i = 0; i < this.numPlayers; i++) {
            LinkedList<Card> hand = this.players.get(i).getCards();   
            hand.addAll(this.boardCards);  
            this.players.get(i).setHand(getHand(sortHand(hand)));
            System.out.println("Jugador " + (i+1) + " " + this.players.get(i).getHand());
            }
        
        //Una vez que se ha establecido la mano de cada jugador se llama al método que dice quien ha ganado
        //Se le debe pasar el linkedList de jugadores que juegan la mano
        LinkedList<Player> winners = whoWin(this.players);
        System.out.print("The winners are: ");
        for(Player p: winners)
            System.out.print(p.getName() + " ");
    }
    */
    
    //Devuelve un LinkedList de Players para el caso que haya empate
    private LinkedList<Player> whoWin(LinkedList<Player> boardPlayers) {
        LinkedList<Player> winners = new LinkedList<>();
        winners.push(boardPlayers.getFirst());
        
        for(int i = 1; i < boardPlayers.size(); i++) {
            HandComparator comp = compareTwoHands(winners.getFirst().getHand(),boardPlayers.get(i).getHand());
            if(comp == HandComparator.SECOND) {
                winners.clear();
                winners.push(boardPlayers.get(i));
                
            }
            else if(comp == HandComparator.DRAW) {
                winners.push(boardPlayers.get(i));
            }
            
            
        }
        
        
        return winners;
    }
    
    //Devuelve el ganador de estas dos manos (o empate).
    private HandComparator compareTwoHands(HandPlayer h1, HandPlayer h2) {
        HandComparator winner = HandComparator.DRAW;
        
        
        if(h1.getHand().ordinal() > h2.getHand().ordinal())
            winner = HandComparator.FIRST;
        else if(h1.getHand().ordinal() < h2.getHand().ordinal())
            winner = HandComparator.SECOND;
        
        /////////////EMPATE EN LAS JUGADAS/////////////////////////////
        else {
            //Si la jugada tiene main Value
            if(h1.getMainV() != null && h2.getMainV() != null) {
                if(h1.getMainV().ordinal() > h2.getMainV().ordinal())
                    winner = HandComparator.FIRST;
                //Si el main Value del segundo es mejor se acaba
                else if(h1.getMainV().ordinal() < h2.getMainV().ordinal())
                    winner = HandComparator.SECOND;
                /////////////EMPATE EN EL MAIN VALUE/////////////////////////////
                
                else  { //2 casos: Si tiene secondValue y si no tiene secondValue
                    
                    //1 - SI tiene SecondValue
                    if(h1.getSecondV() != null && h2.getSecondV() != null) {
                        if(h1.getSecondV().ordinal() > h2.getSecondV().ordinal())
                            winner = HandComparator.FIRST;
                        else if(h1.getSecondV().ordinal() < h2.getSecondV().ordinal())
                            winner = HandComparator.SECOND;
                        //////////////EMPATE EN EL SECOND VALUE////////////////////////
                        //En el caso de que tenga single cards las comparamos
                        else if(h1.getCards() != null && h2.getCards() != null){
                            //Comparamos las singleCards y estas decidirán al ganador (o el empate).
                            winner = compareSingleCards(h1.getCards(), h2.getCards());    
                        }
                    }
                    //2 - NO tiene SecondValue
                    else {
                        //En el caso de que tenga single cards las comparamos
                        if(h1.getCards() != null && h2.getCards() != null){
                            //Comparamos las singleCards y estas decidirán al ganador (o el empate).
                            winner = compareSingleCards(h1.getCards(), h2.getCards()); 
                    
                        }
                    
                    }
                }
            }
            else {
                //Si la jugada no tiene mainValue
                //En el caso de que tenga single cards las comparamos
                if(h1.getCards() != null && h2.getCards() != null){
                    //Comparamos las singleCards y estas decidirán al ganador (o el empate).
                    winner = compareSingleCards(h1.getCards(), h2.getCards()); 
                    } 
                }
        }
        
        
        return winner;
    }
    
    
    private HandComparator compareSingleCards(LinkedList<Value> p1, LinkedList<Value> p2) {
        HandComparator winner = HandComparator.DRAW;
        int i = 0;
        
        
        while(winner == HandComparator.DRAW && i < p1.size()) {
            if(p1.get(i).ordinal() > p2.get(i).ordinal())
                winner = HandComparator.FIRST;
            else if(p1.get(i).ordinal() < p2.get(i).ordinal())
                winner = HandComparator.SECOND;
            i++;
        }
        
        return winner;
    }
    
    
    private LinkedList<Card> sortHand(LinkedList<Card> cards) {
      Collections.sort(cards, Collections.reverseOrder());
      return cards;
    }

   //Suponemos que siempre nos dan el array de cartas ordenado
    //por el valor de la carta (considerando Ace la más alta)
    private HandPlayer getHand(LinkedList<Card> cards) {
        //System.out.println(cards.toString());
        int length = cards.size();
        HandPlayer bestHand = null;
        if(length > 0){            
            //Obtenemos la posición en el enumerado de la primera carta
            Card current = cards.get(0);
            LinkedList<Value> singleCards = new LinkedList<>();
            //value
            Value highStr = null;
            Value highStrFlushH = null, highStrFlushC = null, highStrFlushD = null, highStrFlushS = null;
            int straightCont = 1; 
            int strFlushContH = 0, strFlushContC = 0, strFlushContD = 0, strFlushContS = 0;
            int flushContH = 0, flushContC = 0, flushContD = 0, flushContS = 0;
            int repCont = 1;
            //Current strFlush
            Value currentH = null, currentC = null, currentD = null, currentS = null;
            Value highFlushH = null, highFlushC = null, highFlushD = null, highFlushS = null;
            bestHand = new HandPlayer(HandCategories.HIGH_CARD);
            //bestHand.setMainV(cards.get(0).getVal());
            
            switch (cards.get(0).getSuit()) {
                case CLUBS:
                    flushContC = 1;
                    highFlushC = cards.get(0).getVal();
                    //strFlushContC = 1;
                    break;
                case DIAMONDS:
                    flushContD = 1;
                    highFlushD= cards.get(0).getVal();
                    //strFlushContD = 1;
                    break;
                case HEARTS:
                    flushContH = 1;
                    highFlushH = cards.get(0).getVal();
                    //strFlushContH = 1;
                    break;
                case SPADES:
                    flushContS = 1;
                    highFlushS = cards.get(0).getVal();
                    //strFlushContS = 1;
                    break;
                default:
                    break;
            }
            
            for(int i = 1; i < length; i++) {
                
                /////////////BUSCAR PAREJAS//////////////////////////////
                //Si me encuentro varias cartas iguales incremento el contador
                //hasta que me encuentre alguna diferente.
                if(cards.get(i).getVal() == current.getVal()) {
                    repCont++;
                }
                else {
                    //Como máximo necesitaremos 3 cartas adicionales en el caso que
                    //tengamos pareja
                    if(repCont == 1 && singleCards.size() < 5) {
                        singleCards.addLast(current.getVal());
                        //Las cartas que aparezcan una sola vez las insertamos 
                        //al final del array finalHand y serán eliminadas
                        //si hay mejores jugadas
                    }
                    else if(repCont == 2 || repCont == 3 || repCont == 4) {              
                        bestHand = giveMeBestHand(current.getVal(), repCont, bestHand);
                        repCont = 1;
                    }
                }
                
                if(i == length-1 &&(repCont == 2 || repCont == 3 || repCont == 4)){
                    bestHand = giveMeBestHand(current.getVal(), repCont, bestHand);
                }      
                /////////////FIN BUSCAR PAREJAS////////////////////////////
                /////////////BUSCAR ESCALERAS//////////////////////////////
                
                if(cards.get(i).getVal().ordinal() == current.getVal().ordinal()-1 && straightCont<5){
                    if(straightCont == 1)
                        highStr = current.getVal();
                    straightCont++;
                    if(straightCont == 5){
                        HandCategories currentHand = HandCategories.STRAIGHT;
                        if(bestHand.getHand().ordinal() < currentHand.ordinal()){
                            bestHand.setHand(currentHand);
                            bestHand.setMainV(highStr);
                        }
                    }else{
                        //Caso de A a 5
                        if(straightCont == 4 && highStr == Value.FIVE && 
                                cards.get(0).getVal() == Value.ACE){
                            HandCategories currentHand = HandCategories.STRAIGHT;
                            if(bestHand.getHand().ordinal() < currentHand.ordinal()){
                                bestHand.setHand(currentHand);
                                bestHand.setMainV(highStr);
                            }
                        }
                    }
                }else{
                     if(cards.get(i).getVal().ordinal() < current.getVal().ordinal()-1){
                         straightCont = 1;
                     }
                }
                /////////////FIN BUSCAR ESCALERAS//////////////////////////////
                /////////////BUSCAR ESCALERA DE COLOR//////////////////////////
                //falta A a 5
                
                //////HEARTS/////////////////
                if(strFlushContH == 0 && current.getSuit() == Suit.HEARTS) {
                    strFlushContH++;
                    currentH = current.getVal();
                    highStrFlushH = current.getVal();
                } 
                if(currentH != null){
                    if(cards.get(i).getVal().ordinal() == currentH.ordinal()-1 && 
                            cards.get(i).getSuit() == Suit.HEARTS && strFlushContH < 5){
                        
                        currentH = cards.get(i).getVal();
                        strFlushContH++;
                        if(strFlushContH == 5){                            
                            bestHand.setHand(HandCategories.STRAIGHT_FLUSH);
                            bestHand.setMainV(highStrFlushH);
                            bestHand.setSuit(Suit.HEARTS);                       
        
                        }else{
                        //Caso A a 5 corazones
                            if(strFlushContH == 4 && highStrFlushH == Value.FIVE){
                                int j = 0; 
                                boolean noAce = false;
                                while(j < 3 && !noAce){
                                    Card c = cards.get(j);
                                    if(c.getVal() == Value.ACE && c.getSuit() == Suit.HEARTS){
                                        noAce = true;
                                        bestHand.setHand(HandCategories.STRAIGHT_FLUSH);
                                        bestHand.setMainV(highStrFlushH);
                                        bestHand.setSuit(Suit.HEARTS); 
                                    }
                                    j++;
                                }
                            }
                        }
                    }else{
                        if(cards.get(i).getVal().ordinal() < currentH.ordinal()-1 && 
                            cards.get(i).getSuit() == Suit.HEARTS){
                            strFlushContH = 1;
                            currentH = cards.get(i).getVal();
                            highStrFlushH = cards.get(i).getVal();
                        }
                    }
                }
                //////////////////DIAMONDS//////////////////
                if(strFlushContD == 0 && current.getSuit() == Suit.DIAMONDS) {
                    strFlushContD++;
                    currentD = current.getVal();
                    highStrFlushD = current.getVal();
                } if(currentD != null)  {
                    if(cards.get(i).getVal().ordinal() == currentD.ordinal()-1 && 
                        cards.get(i).getSuit() == Suit.DIAMONDS && strFlushContD < 5){
                        currentD = cards.get(i).getVal();
                        strFlushContD++;
                        if(strFlushContD == 5){
                            bestHand.setHand(HandCategories.STRAIGHT_FLUSH);
                            bestHand.setMainV(highStrFlushD);
                            bestHand.setSuit(Suit.DIAMONDS); 
                        }else{
                        //Caso A a 5 diamantes
                            if(strFlushContD == 4 && highStrFlushD == Value.FIVE){
                                int j = 0; 
                                boolean noAce = false;
                                while(j < 3 && !noAce){
                                    Card c = cards.get(j);
                                    if(c.getVal() == Value.ACE && c.getSuit() == Suit.DIAMONDS){
                                        noAce = true;
                                        bestHand.setHand(HandCategories.STRAIGHT_FLUSH);
                                        bestHand.setMainV(highStrFlushD);
                                        bestHand.setSuit(Suit.DIAMONDS); 
                                    }
                                    j++;
                                }
                            }
                        }
                    }else{
                        if(cards.get(i).getVal().ordinal() < currentD.ordinal()-1 && 
                            cards.get(i).getSuit() == Suit.DIAMONDS){
                            strFlushContD = 1;
                            currentD = cards.get(i).getVal();
                            highStrFlushD = cards.get(i).getVal();
                        }
                    }
                }
                
                
                /////////////////////////CLUBS////////////////////////
                
                if(strFlushContC == 0 && current.getSuit() == Suit.CLUBS) {
                    strFlushContC++;
                    currentC = current.getVal();
                    highStrFlushC = current.getVal();
                } if(currentC != null) {
                    if(cards.get(i).getVal().ordinal() == currentC.ordinal()-1 && 
                            cards.get(i).getSuit() == Suit.CLUBS && strFlushContC < 5){
                        currentC = cards.get(i).getVal();
                        strFlushContC++;
                        if(strFlushContC == 5){
                            bestHand.setHand(HandCategories.STRAIGHT_FLUSH);
                            bestHand.setMainV(highStrFlushC);
                            bestHand.setSuit(Suit.CLUBS); 
                        }else{
                        //Caso A a 5 clubs
                            if(strFlushContC == 4 && highStrFlushC == Value.FIVE){
                                int j = 0; 
                                boolean noAce = false;
                                while(j < 3 && !noAce){
                                    Card c = cards.get(j);
                                    if(c.getVal() == Value.ACE && c.getSuit() == Suit.CLUBS){
                                        noAce = true;
                                        bestHand.setHand(HandCategories.STRAIGHT_FLUSH);
                                        bestHand.setMainV(highStrFlushC);
                                        bestHand.setSuit(Suit.CLUBS); 
                                    }
                                    j++;
                                }
                            }
                        }
                    }else{
                        if(cards.get(i).getVal().ordinal() < currentC.ordinal()-1 && 
                            cards.get(i).getSuit() == Suit.CLUBS){
                            strFlushContC = 1;
                            currentC = cards.get(i).getVal();
                            highStrFlushC = cards.get(i).getVal();
                        }
                    }
                }
                ////////////////////////////////SPADES////////////////////////////
                if(strFlushContS == 0 && current.getSuit() == Suit.SPADES) {
                    strFlushContS++;
                    currentS = current.getVal();
                    highStrFlushS = current.getVal();
                } if(currentS != null) {
                    if(cards.get(i).getVal().ordinal() == currentS.ordinal()-1 && 
                    cards.get(i).getSuit() == Suit.SPADES && strFlushContS < 5){
                        currentS = cards.get(i).getVal();
                        strFlushContS++;
                        if(strFlushContS == 5){
                            bestHand.setHand(HandCategories.STRAIGHT_FLUSH);
                            bestHand.setMainV(highStrFlushS);
                            bestHand.setSuit(Suit.SPADES); 
                        }else{
                        //Caso A a 5 clubs
                            if(strFlushContS == 4 && highStrFlushS == Value.FIVE){
                                int j = 0; 
                                boolean noAce = false;
                                while(j < 3 && !noAce){
                                    Card c = cards.get(j);
                                    if(c.getVal() == Value.ACE && c.getSuit() == Suit.SPADES){
                                        noAce = true;
                                        bestHand.setHand(HandCategories.STRAIGHT_FLUSH);
                                        bestHand.setMainV(highStrFlushS);
                                        bestHand.setSuit(Suit.SPADES); 
                                    }
                                    j++;
                                }
                            }
                        }
                    }else{
                        if(cards.get(i).getVal().ordinal() < currentS.ordinal()-1 && 
                            cards.get(i).getSuit() == Suit.SPADES){
                            strFlushContS = 1;
                            currentS = cards.get(i).getVal();
                            highStrFlushS = cards.get(i).getVal();
                        }
                    }
                  }
                /////////////FIN BUSCAR ESCALERAS DE COLOR/////////////////
                
                
                //La primera vez que me encuentro una carta de un palo lo inicializo
                
                
                /////////////BUSCAR COLOR/////////////////////////////////
                switch (cards.get(i).getSuit()) {
                    case CLUBS:
                        if(flushContC == 0) 
                            highFlushC = cards.get(i).getVal();
                        flushContC++;
                        if(flushContC == 5) { 
                            HandCategories currentHand = HandCategories.FLUSH;
                            if(bestHand.getHand().ordinal() < currentHand.ordinal()) {
                                bestHand.setHand(currentHand);
                                bestHand.setSuit(Suit.CLUBS);
                            }
                        }
                        break;                
                    case DIAMONDS:
                        if(flushContD == 0) 
                            highFlushD = cards.get(i).getVal();
                        flushContD++;
                        if(flushContD == 5) {
                            HandCategories currentHand = HandCategories.FLUSH;
                            if(bestHand.getHand().ordinal() < currentHand.ordinal()) {
                                bestHand.setHand(currentHand);
                                bestHand.setSuit(Suit.DIAMONDS);
                            }
                        }
                        break;
                    case HEARTS:
                        if(flushContH == 0) 
                            highFlushH = cards.get(i).getVal();
                        flushContH++;
                        if(flushContH == 5) {
                            HandCategories currentHand = HandCategories.FLUSH;
                            if(bestHand.getHand().ordinal() < currentHand.ordinal()) {
                                bestHand.setHand(currentHand);
                                bestHand.setSuit(Suit.HEARTS);
                            }
                        }
                        break;
                    case SPADES:
                        if(flushContS == 0) 
                            highFlushS = cards.get(i).getVal();
                        flushContS++;
                        if(flushContS == 5) {HandCategories currentHand = HandCategories.FLUSH;
                            if(bestHand.getHand().ordinal() < currentHand.ordinal()) {
                                bestHand.setHand(currentHand);
                                bestHand.setSuit(Suit.SPADES);
                            }
                        }
                        break;
                    default:
                        break;
                }
                /////////////FIN BUSCAR COLOR/////////////////////////////////
                current = cards.get(i);
            }
            
        
            if(null != bestHand.getHand()) 
                switch (bestHand.getHand()) {
                case FLUSH:
                    bestHand.setCards(getFlushCards(cards, bestHand.getSuit()));
                    bestHand.setMainV(null);
                    break;
                case HIGH_CARD:
                    bestHand.setMainV(null);
                    bestHand.setCards(singleCards);
                    break;
                case POKER:
                    if(!singleCards.isEmpty())
                        bestHand.addLastCards(singleCards.getFirst());
                    else  //La carta que debería ser el kicker para el poker
                          //podría no estar en singleCards si formara una pareja o trío.
                        bestHand.addLastCards(findTheKicker(bestHand, cards));
                    break;
                case TWO_PAIR:
                    if(!singleCards.isEmpty())
                        bestHand.addLastCards(singleCards.getFirst());
                    else  //Porque puede haber 3 parejas y no estar en singleCards
                        bestHand.addLastCards(cards.get(4).getVal());
                    break;
                case THREE_KIND:
                    bestHand.addLastCards(singleCards.getFirst());
                    singleCards.removeFirst();
                    bestHand.addLastCards(singleCards.getFirst());
                    break;
                case ONE_PAIR:
                    bestHand.addLastCards(singleCards.getFirst());
                    singleCards.removeFirst();
                    bestHand.addLastCards(singleCards.getFirst());
                    singleCards.removeFirst();
                    bestHand.addLastCards(singleCards.getFirst());
                    break;
                default:
                    break;
            }
            
       
        }
        
        
        return bestHand;
    }
    
    private HandPlayer giveMeBestHand(Value currentVal, int repCont, HandPlayer bestHand){
        HandCategories currentHand = getCurrentHand(repCont);
        bestHand = decideBetterHand(bestHand,currentHand, currentVal);
        return bestHand;
    }
    
    //Encuentra parejas,dobles, trios, full y poker.
    private HandPlayer decideBetterHand(HandPlayer bestHand, HandCategories current
                                            ,Value currentVal) {
       
        //Si había encontrado una pareja y ahora encuentro un trio tengo full.
        if(bestHand.getHand() == HandCategories.ONE_PAIR && current == HandCategories.THREE_KIND) {
            bestHand.setHand(HandCategories.FULL_HOUSE);            
            bestHand.setSecondV(bestHand.getMainV());
            bestHand.setMainV(currentVal);
        } 
        //Si había encontrado un trio y ahora encuentro una pareja tengo full.
        else if(bestHand.getHand() == HandCategories.THREE_KIND && current == HandCategories.ONE_PAIR) {            
            bestHand.setHand(HandCategories.FULL_HOUSE);
            bestHand.setSecondV(currentVal);
            
        }
        //Si había encontrado una pareja y ahora encuentro una pareja tengo doble pareja.
        else if(bestHand.getHand() == HandCategories.ONE_PAIR && current == HandCategories.ONE_PAIR){
            bestHand.setHand(HandCategories.TWO_PAIR);
            bestHand.setSecondV(currentVal);
        }
        //Si encuentro dos trios tengo full con trio del mayor y pareja del menor
        else if(bestHand.getHand() == HandCategories.THREE_KIND && current == HandCategories.THREE_KIND){
            bestHand.setHand(HandCategories.FULL_HOUSE);
            bestHand.setSecondV(currentVal);
        }
        else if(bestHand.getHand().ordinal() < current.ordinal()) {
            bestHand.setHand(current);
            bestHand.setMainV(currentVal);
        }
      
        return bestHand;
    }   
    
   private HandCategories getCurrentHand(int cont){
        HandCategories hand = null;
        switch (cont) {
            case 2:
                hand = HandCategories.ONE_PAIR;
                /*
                else{
                    Value v = hand.getMainV();
                    hand = HandCategories.TWO_PAIR;
                    hand.setMainV(v);
                    hand.setSecondV(val);
                    HandCategories.ONE_PAIR.setMainV(null);
                }
                        */
                break;
            case 3:
                hand = HandCategories.THREE_KIND;
               // hand.setMainV(val);
                break;
            case 4:
                hand = HandCategories.POKER;
                //hand.setMainV(val);
                break;
            default:
                break;
        }
        return hand;
    }
   
   private LinkedList<Value> getFlushCards(LinkedList<Card> cards, Suit s) {
       
       int i = cards.size() - 1;
       //int cont = 0;
       LinkedList<Value> flush = new LinkedList<>();
       while (i >= 0 && flush.size() < 5) {           
           if(cards.get(i).getSuit() == s) 
               flush.push(cards.get(i).getVal());
           
           i--;
       }
       return flush;
   }  
   
   private Value findTheKicker(HandPlayer hp, LinkedList<Card> cards){
      int i = 0;
      Value v;
      while(cards.get(i).getVal() == hp.getMainV() && i < cards.size()) {
          i++;
      }
      v = cards.get(i).getVal();
      return v;
   }

    public int getNumberOfHand() {
        return hand;
    }

    public void setControl(GameController gameController) {
       this.c = gameController;
    }
   
}
