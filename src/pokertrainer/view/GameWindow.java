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
package pokertrainer.view;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.swing.ImageIcon;
import pokertrainer.model.Card;
import pokertrainer.model.Player;
import pokertrainer.controller.GameController;
import pokertrainer.model.Action;
import pokertrainer.model.Role;

/**
 *
 * @author Javi
 */
public class GameWindow extends javax.swing.JDialog {
     
    /**
     * Creates new form MainWindow
     * @param controller
     * @param players
     * @param seats
     * @param pot
     * @param playerTurn
     * @param bigBlind
     */
    
    public GameWindow(GameController controller, LinkedList<Player> players, 
            int[] seats, int pot, Player playerTurn, int bigBlind) {
        this(null, true);
        this.menuPanel1.setController(controller);
        this.jTextArea1.setLineWrap(true);
        setTextToTextArea("******** HAND 1 ******** \n");
        System.out.println("******** HAND 1 ******** \n");
        //cargamos toda la informacion de los jugadores
        loadPlayersInfo(seats, players, pot, playerTurn, bigBlind);
    }
    
    private void loadPlayersInfo(int[] seats, LinkedList<Player> players, 
            int pot, Player playerTurn, int bigBlind) {
        showPlayers(seats, false);
        setPlayersInfo(players);
        updatePot(pot);
        updateViewForPlayerTurn(playerTurn, bigBlind, bigBlind);
    }
    
    public GameWindow(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        setTitle("PokerTrainer");
        initComponents();
        this.getContentPane().setBackground(Color.BLACK);
    }
    

    
    //Llama a putPlayerOnSeat para pintar la info de todos los jugadores
    private void setPlayersInfo(List<Player> players) {
        int numPlayers = players.size();
        for(int i=0; i<numPlayers; i++){
            Player player = players.get(i);
            putPlayerOnSeat(player);
        }
    }
    
    //Pinta toda la informacion del jugador en el asiento correspondiente
    private void putPlayerOnSeat(Player player){
        Card card1 = player.getCards().get(0);
        Card card2 = player.getCards().get(1);
        ImageIcon IconCard1 = getCardImageIcon(card1);
        ImageIcon IconCard2 =  getCardImageIcon(card2);
        
        int seat = player.getSeat();
        
        getBetPanel(seat).setBet(player.getBet());
        getCardsPanel(seat).setCardsImages(IconCard1, IconCard2);
        getInfoPanel(seat).setInfo(player.getMoney(), player.getName(), player.getRole());
    }
    
    
    /* Colorea el marco para que sepa que jugador tiene el turno
        y edita los botones conforme al dinero del que disponga y el estado de 
        la partida
    */
    public void updateViewForPlayerTurn(Player playerTurn, int highBet, int bigBlind) {
        //Llama a playerInfo par apintar de amarillo el panel del jugador al que le toca
        getInfoPanel(playerTurn.getSeat()).showPanelTurnPlayer();
        
        //Llama a una funcion de menuPanel encargada de editar los botones
        this.menuPanel1.update(playerTurn.getMoney(), playerTurn.getBet(), 
                bigBlind, highBet, !playerTurn.isBot());
    }
    
    
    
    public void disableViewForPlayerTurn(Player playerTurn){
        getInfoPanel(playerTurn.getSeat()).disableShowPanelTurnPlayer();
    }
    
    //Se usa para el call, raise y all-in
    //Pinta la apuesta y actualiza el dinero del jugador
    public void setPlayerBetAndMoney(int seat, int money, int bet) {
        getBetPanel(seat).setBet(bet);
        getInfoPanel(seat).setPlayerMoney(money);        
    }
    
    public void setFoldState(Player playerTurn) {
        getInfoPanel(playerTurn.getSeat()).setFoldInfo();
        getBetPanel(playerTurn.getSeat()).setFoldBet();
        getCardsPanel(playerTurn.getSeat()).setVisible(false);
    }
    
    public void updatePot(int moneyPot){
        this.potPanel1.setPot(moneyPot);
    }
    

    
    
    //Dada una carta obtiene la ruta con el ImageIcon y lo devuelve
    private ImageIcon getCardImageIcon(Card c) {
        String cardRoute = "img/cards/" + c.getVal().getVal() + c.getSuit().getName() + ".png";
        return new ImageIcon(CardPanel.class.getResource(cardRoute));
    }
    
    /*Devuelve el panel de apuesta correspondiente al jugador en el asiento
      recibido por parámetro
    */
    private PlayerBet getBetPanel(int seat) {
        PlayerBet bet;
        switch(seat) {
            case 0: bet = this.playerBet0; break;
            case 1: bet = this.playerBet1; break;
            case 2: bet = this.playerBet2; break;
            case 3: bet = this.playerBet3; break;
            case 4: bet = this.playerBet4; break;
            case 5: bet = this.playerBet5; break;
            case 6: bet = this.playerBet6; break;
            case 7: bet = this.playerBet7; break;
            case 8: bet = this.playerBet8; break;
            default: bet = null; 
        }
        return bet;
    }
    
    /*Devuelve el panel de cartas correspondiente al jugador en el asiento
      recibido por parámetro
    */
    private PlayerCards getCardsPanel(int seat) {
        PlayerCards cards;
        switch(seat) {
            case 0: cards = this.playerCards0; break;
            case 1: cards = this.playerCards1; break;
            case 2: cards = this.playerCards2; break;
            case 3: cards = this.playerCards3; break;
            case 4: cards = this.playerCards4; break;
            case 5: cards = this.playerCards5; break;
            case 6: cards = this.playerCards6; break;
            case 7: cards = this.playerCards7; break;
            case 8: cards = this.playerCards8; break;
            default: cards = null; 
        }
        return cards;
    }

    /*Devuelve el panel de info correspondiente al jugador en el asiento
      recibido por parámetro
    */
    private PlayerInfo getInfoPanel(int seat) {
        PlayerInfo info;
        switch(seat) {
            case 0: info = this.playerInfo0; break;
            case 1: info = this.playerInfo1; break;
            case 2: info = this.playerInfo2; break;
            case 3: info = this.playerInfo3; break;
            case 4: info = this.playerInfo4; break;
            case 5: info = this.playerInfo5; break;
            case 6: info = this.playerInfo6; break;
            case 7: info = this.playerInfo7; break;
            case 8: info = this.playerInfo8; break;
            default: info = null; 
        }
        return info;
    }
    
    
    //Pone la visibilidad de los paneles del jugador al valor de flag
    private void showSeat(int num, boolean flag){
        getBetPanel(num).setVisible(flag);
        getCardsPanel(num).setVisible(flag);
        getInfoPanel(num).setVisible(flag);    
    }
    
    /*Pone todos la propiedad visible de todos los asientos al valor del flag
      excepto los recibidos por parametro
    */
    private void showPlayers(int[] seats, boolean flag) {
        int cont = 0;
        
        //8 el numero maximo de jugadores
        for (int i = 0; i < 9; i++) {
            if (cont < seats.length && i == seats[cont]) {
                showSeat(seats[cont], !flag);
                cont++;
            }
            else {
                showSeat(i, flag);
            }
        }
    }
    
    
    
    //Pinta la carta recibida en la posicion (de 1 a 5) en la mesa
    private void setCardOnBoard(Card card, int pos) {
        this.boardCards.setCardImage(getCardImageIcon(card), pos);
    }
    
    public void paintFlop(LinkedList<Card> flopCards){
        for (int i=0; i<flopCards.size(); i++){
            setCardOnBoard(flopCards.get(i), i+1);
        }
    }
    
    public void paintTurn(Card turnCard){
        setCardOnBoard(turnCard, 4);
    }
    
    public void paintRiver(Card riverCard){
        setCardOnBoard(riverCard, 5);
    }
    
    public void paintWinners(LinkedList<LinkedList<Player>> roundWinners) {
       
        setTextToTextArea("The winners are: \n");
        for(int i=0; i<roundWinners.size(); i++){
            disableBordersPlayers(roundWinners.get(i));
            setTextToTextArea("Pot " + (i + 1) + " : \n");
            for(int j =0; j< roundWinners.get(i).size(); j++){
                setTextToTextArea(" - " + roundWinners.get(i).get(j).getName() + "\n");
                //getInfoPanel(roundWinners.get(i).get(j).getSeat()).setMoneyZero();
                getInfoPanel(roundWinners.get(i).get(j).getSeat()).setWinnerInfo();
            }
            setTextToTextArea("\n");
            
            try {
                TimeUnit.SECONDS.sleep(2);
                
            } catch (InterruptedException ex) {

            }
            disableBordersPlayers(roundWinners.get(i));
        }
    }
     
     public void addActionToPlayerList(String playerName, Action action) {
         String texto = playerName + " - " + action+ "\n";
         jTextArea1.append(texto);
     }
     
     public void setTextToTextArea(String text) {
         jTextArea1.append(text);
     }
     
     public void paintAllInitialBets(int[] seats){
         for(int i=0; i< seats.length; i++){
             getBetPanel(seats[i]).setBet(0);
         }
     }
     
     public void paintAllBets(LinkedList<Player> players){
         for(int i=0; i< players.size(); i++){
             getInfoPanel(players.get(i).getSeat()).setPlayerMoney(players.get(i).getMoney());
         }
     }
     
    public void prepareViewForNextHand(LinkedList<Player> players,
            int[] seats, int pot, Player playerTurn, int bigBlind, int hand) {
        
        setTextToTextArea("******** HAND " + (hand + 1) + " ******** \n");
        System.out.println("******** HAND " + (hand + 1) + " ******** \n");
        
        for (Player p: players) {
             getInfoPanel(p.getSeat()).disableShowPanelTurnPlayer();
             //////CAMBIAAAAAAAAAAAAAAAAR
             int add=0;
             if(p.getRole() == Role.SMALL_BLIND){
                 add = 5;
             }else{
                if(p.getRole() == Role.BIG_BLIND){
                   add = 10;
                }
             }
             ////////
             System.out.println(p.getName() + " money: "+ (p.getMoney() + add));
             //System.out.println(p.getName() + " money: "+ p.getMoney());
             loadPlayersInfo(seats, players, pot, playerTurn, bigBlind);
        }
        
        this.boardCards.hideBoardCards();
    }
    
    public void disablePlayer(int seat) {
        getBetPanel(seat).disablePanel();
        getInfoPanel(seat).disablePanel();
        getCardsPanel(seat).disablePanel();
    }
    
    private void disableBordersPlayers(LinkedList<Player> players){
        for(Player p : players){
          getInfoPanel(p.getSeat()).setMoneyZero();
          getInfoPanel(p.getSeat()).disableShowPanelTurnPlayer();
        }
    }
    
     public void paintLastWinners(LinkedList<Player> last) {
         this.potPanel1.setPot(0);
        for(Player p: last){
            int seat = p.getSeat();
            getBetPanel(seat).setBet(p.getBet());
            getInfoPanel(seat).setPlayerMoney(p.getMoney());
            getInfoPanel(seat).setPlayerRole(p.getRole());
        }
    }
     
     public void disableMenuPanel() {
        this.menuPanel1.disableButtons();
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        gamePanel1 = new pokertrainer.view.GamePanel();
        playerInfo0 = new pokertrainer.view.PlayerInfo();
        playerInfo1 = new pokertrainer.view.PlayerInfo();
        playerInfo2 = new pokertrainer.view.PlayerInfo();
        playerInfo3 = new pokertrainer.view.PlayerInfo();
        playerInfo4 = new pokertrainer.view.PlayerInfo();
        playerInfo5 = new pokertrainer.view.PlayerInfo();
        playerInfo6 = new pokertrainer.view.PlayerInfo();
        playerInfo7 = new pokertrainer.view.PlayerInfo();
        playerInfo8 = new pokertrainer.view.PlayerInfo();
        playerBet0 = new pokertrainer.view.PlayerBet();
        playerBet1 = new pokertrainer.view.PlayerBet();
        playerBet2 = new pokertrainer.view.PlayerBet();
        playerBet3 = new pokertrainer.view.PlayerBet();
        playerBet4 = new pokertrainer.view.PlayerBet();
        playerBet5 = new pokertrainer.view.PlayerBet();
        playerBet6 = new pokertrainer.view.PlayerBet();
        playerBet7 = new pokertrainer.view.PlayerBet();
        playerBet8 = new pokertrainer.view.PlayerBet();
        playerCards0 = new pokertrainer.view.PlayerCards();
        playerCards1 = new pokertrainer.view.PlayerCards();
        playerCards2 = new pokertrainer.view.PlayerCards();
        playerCards3 = new pokertrainer.view.PlayerCards();
        playerCards4 = new pokertrainer.view.PlayerCards();
        playerCards5 = new pokertrainer.view.PlayerCards();
        playerCards6 = new pokertrainer.view.PlayerCards();
        playerCards7 = new pokertrainer.view.PlayerCards();
        playerCards8 = new pokertrainer.view.PlayerCards();
        boardCards = new pokertrainer.view.BoardCards();
        potPanel1 = new pokertrainer.view.PotPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        menuPanel1 = new pokertrainer.view.MenuPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(0, 0, 0));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        gamePanel1.setBackground(new java.awt.Color(0, 0, 0));
        gamePanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        gamePanel1.add(playerInfo0, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 500, -1, -1));
        gamePanel1.add(playerInfo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 340, -1, -1));
        gamePanel1.add(playerInfo2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, -1, -1));
        gamePanel1.add(playerInfo3, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 10, -1, -1));
        gamePanel1.add(playerInfo4, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 0, -1, -1));
        gamePanel1.add(playerInfo5, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 10, -1, -1));
        gamePanel1.add(playerInfo6, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 210, -1, -1));
        gamePanel1.add(playerInfo7, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 330, -1, -1));
        gamePanel1.add(playerInfo8, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 500, -1, -1));
        gamePanel1.add(playerBet0, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 510, -1, -1));
        gamePanel1.add(playerBet1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 410, -1, -1));
        gamePanel1.add(playerBet2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, -1, -1));
        gamePanel1.add(playerBet3, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 10, -1, -1));
        gamePanel1.add(playerBet4, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 10, -1, -1));
        gamePanel1.add(playerBet5, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 10, -1, -1));
        gamePanel1.add(playerBet6, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 180, -1, -1));
        gamePanel1.add(playerBet7, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 400, -1, -1));
        gamePanel1.add(playerBet8, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 510, -1, -1));
        gamePanel1.add(playerCards0, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 400, -1, -1));
        gamePanel1.add(playerCards1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 330, -1, -1));
        gamePanel1.add(playerCards2, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 210, -1, -1));
        gamePanel1.add(playerCards3, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 100, -1, -1));
        gamePanel1.add(playerCards4, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 90, -1, -1));
        gamePanel1.add(playerCards5, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 100, -1, -1));
        gamePanel1.add(playerCards6, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 210, -1, -1));
        gamePanel1.add(playerCards7, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 330, -1, -1));
        gamePanel1.add(playerCards8, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 400, -1, -1));
        gamePanel1.add(boardCards, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 240, -1, -1));
        gamePanel1.add(potPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 170, 120, 50));

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jScrollPane1.setViewportView(jTextArea1);

        menuPanel1.setPreferredSize(new java.awt.Dimension(161, 53));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addComponent(menuPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(gamePanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1013, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(gamePanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(menuPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GameWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GameWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GameWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GameWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                GameWindow dialog = new GameWindow(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private pokertrainer.view.BoardCards boardCards;
    private pokertrainer.view.GamePanel gamePanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private pokertrainer.view.MenuPanel menuPanel1;
    private pokertrainer.view.PlayerBet playerBet0;
    private pokertrainer.view.PlayerBet playerBet1;
    private pokertrainer.view.PlayerBet playerBet2;
    private pokertrainer.view.PlayerBet playerBet3;
    private pokertrainer.view.PlayerBet playerBet4;
    private pokertrainer.view.PlayerBet playerBet5;
    private pokertrainer.view.PlayerBet playerBet6;
    private pokertrainer.view.PlayerBet playerBet7;
    private pokertrainer.view.PlayerBet playerBet8;
    private pokertrainer.view.PlayerCards playerCards0;
    private pokertrainer.view.PlayerCards playerCards1;
    private pokertrainer.view.PlayerCards playerCards2;
    private pokertrainer.view.PlayerCards playerCards3;
    private pokertrainer.view.PlayerCards playerCards4;
    private pokertrainer.view.PlayerCards playerCards5;
    private pokertrainer.view.PlayerCards playerCards6;
    private pokertrainer.view.PlayerCards playerCards7;
    private pokertrainer.view.PlayerCards playerCards8;
    private pokertrainer.view.PlayerInfo playerInfo0;
    private pokertrainer.view.PlayerInfo playerInfo1;
    private pokertrainer.view.PlayerInfo playerInfo2;
    private pokertrainer.view.PlayerInfo playerInfo3;
    private pokertrainer.view.PlayerInfo playerInfo4;
    private pokertrainer.view.PlayerInfo playerInfo5;
    private pokertrainer.view.PlayerInfo playerInfo6;
    private pokertrainer.view.PlayerInfo playerInfo7;
    private pokertrainer.view.PlayerInfo playerInfo8;
    private pokertrainer.view.PotPanel potPanel1;
    // End of variables declaration//GEN-END:variables

    

   

    

    
}
