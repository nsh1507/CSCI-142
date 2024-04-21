package war;

import java.util.*;

/**
 * Represents a pile in the game.
 *
 * @author Nam Huynh
 */

public class Pile {
    /**
     * Create an empty pile of cards for a given seed and give it a name
     *
     * @param name the name of the pile
     */

    private final ArrayList<Card> cards;
    private final String name;
    private static final Random rng= new Random();

    public Pile(String name) {
        this.name = name;
        this.cards = new ArrayList<>();
    }

    public void addCard(Card card) {
        /**
         * Add a card to the bottom of the pile
         *
         * @param card the card to be added
         */
        cards.add(card);
    }

    public void clear() {
        /**
         * Remove all cards from the pile by clearing it out
         */
        cards.removeAll(cards);
    }

    public Card drawCard(boolean faceUp) {
        /**
         *Get the next top card from the pile.
         *
         * @param faceUp should the card be set to face up when drawn
         * @return list of Card objects
         */
        if (cards.get(0).isFaceUp()){
            shuffle();
            System.out.println(this);
        }
        Card top = cards.remove(0);
        if (faceUp){
            top.setFaceUp();
        }
        return top;
    }

    public ArrayList<Card> getCards() {
        /**
         * Returns the collection of cards in the pile's current state.
         *
         * @return list of Card objects
         */
        return cards;
    }

    public boolean hasCard() {
        /**
         * return true or false if the pile has at least one card.
         *
         * @return whether there is a card in the pile or none
         */
        return (cards.size() > 0);
    }

    public static void setSeed(long seed) {
        /**
         * Create and set the seed for the random number generator.
         *
         * @param seed the seed value
         */
        rng.setSeed(seed);
    }

    public void shuffle() {
        /**
         * Shuffle the cards, set them all to face down,
         * and displays "Shuffling {name} pile".
         */
        System.out.println("Shuffling " + this.name + " pile");
        Collections.shuffle(cards, this.rng);
        for (Card card: cards){
            card.setFaceDown();
        }
    }

    @Override
    public String toString() {
        /**
         * Returns a string representation of the pile:
         * "{name} pile: first-card second-card ..."
         */
        String c= "";
        for (Card card : cards) {
            c = c + card.toString() +" ";
        }
        return this.name + " pile: " + c;
    }
}
