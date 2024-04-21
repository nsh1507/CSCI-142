package war;

/**
 * Represents a player in the game.
 *
 * @author Nam Huynh
 */

public class Player {
    /**
     * Create a player with their id and set the
     * pile's name to be "P1" or "P2" based on that id
     *
     * @param id the player's id
     */

    private final Pile pile;
    private boolean winner;

    public Player(int id){
        String name = "P" + id;
        this.pile = new Pile(name);
    }

    public void addCard(Card card){
        /**
         * Add a single card to the bottom of the player's pile using
         * method from the Pile class.
         *
         * @param card the card to be added
         */
        this.pile.addCard(card);
    }

    public void addCards(Pile cards){
        /** vvv
         * Add the collection of cards from the incoming pile
         * to the bottom of player's pile, in order.
         *
         * @param cards the pile of cards to be added
         */
        for(Card card: cards.getCards()){
            this.pile.addCard(card);
        }
    }

    public Card drawCard(){
        /**
         * Remove a card from the top of the pile with the
         * intention that the card should switch to be face up.
         *
         * @return the newly removed card from the top of the pile
         */
        return this.pile.drawCard(true);
    }

    public boolean hasCard(){
        /**
         * Check if player still has cards in the
         * pile using method from Pile class.
         *
         * @return boolean value if they still have cards or not
         */
        return this.pile.hasCard();
    }

    public void setWinner(){
        /**
         * Set the winner for the match.
         */
        this.winner = true;
    }

    public boolean isWinner(){
        /**
         * Check if the player is the winner.
         *
         * @return boolean value whether this player was the winner or not
         */
        return this.winner;
    }

    @Override
    public String toString(){
        /**
         * Returns a string representation of this
         * player's pile using method from the Pile class
         *
         * @return a string representing the pile
         */
        return this.pile.toString();
    }
}
