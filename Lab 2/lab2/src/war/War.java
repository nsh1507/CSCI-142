package war;


/**
 * The main program for the card game, War.  It is run on the command line as:<br>
 * <br>
 * java War cards-per-player seed<br>
 * <br>
 *
 * @author RIT CS
 * @author Nam Huynh
 */

public class War {
    /** The maximum number of cards a single player can have */
    public final static int MAX_CARDS_PER_PLAYER = 26;
    private final Player p1;
    private final Player p2;
    private int round;

    /**
     * Initialize the game.
     *
     * @param cardsPerPlayer the number of cards for a single player
     */
    public War(int cardsPerPlayer) {
        this.round = 1;
        Pile Initial = new Pile("Initial");
        for (Rank rank : Rank.values()) {
            for (Suit suit : Suit.values()) {
                Initial.addCard(new Card(rank,suit));
            }
        }

        Initial.shuffle();
        System.out.println(Initial);

        this.p1 = new Player(1);
        this.p2 = new Player(2);

        int i = 1;
        while (i <= cardsPerPlayer*2){
            if (i % 2 == 1){
                p1.addCard(Initial.getCards().get(0));
                Initial.getCards().remove(0);
            }
            else{
                p2.addCard(Initial.getCards().get(0));
                Initial.getCards().remove(0);
            }
            i++;
        }
    }

    /** Play the full game. */
    public void playGame() {
        Pile toBeAdded = new Pile("toBeAdded");
        while(p1.hasCard() && p2.hasCard()){
            System.out.println("ROUND " + round);
            System.out.println(p1);
            System.out.println(p2);
            Card p1draw = p1.drawCard();
            Card p2draw = p2.drawCard();
            toBeAdded.addCard(p1draw);
            toBeAdded.addCard(p2draw);
            System.out.println("P1 card: " + p1draw);
            System.out.println("P2 card: " + p2draw);
            if (p1draw.beats(p2draw)){
                System.out.println("P1 wins round gets Trick pile: " + p1draw + " " + p2draw);
                p1.addCards(toBeAdded);
                toBeAdded.clear();
                this.round++;
            }
            else if (p2draw.beats(p1draw)){
                System.out.println("P2 wins round gets Trick pile: " + p1draw + " " + p2draw);
                p2.addCards(toBeAdded);
                toBeAdded.clear();
                this.round++;
            }
            else if (p2draw.equals(p1draw)){
                boolean war = true;
                while (war){
                    System.out.println("WAR!");
                    System.out.println(p1);
                    System.out.println(p2);
                    if (!p1.hasCard()){
                        System.out.println("P2 wins round gets Trick pile: " + p1draw + " " + p2draw);
                        p2.addCard(p1draw);
                        p2.addCard(p2draw);
                        break;
                    }
                    else if (!p2.hasCard()){
                        System.out.println("P1 wins round gets Trick pile: " + p1draw + " " + p2draw);
                        p1.addCard(p1draw);
                        p1.addCard(p2draw);
                        break;
                    }
                    Card p1war = p1.drawCard();
                    Card p2war = p2.drawCard();
                    System.out.println("P1 card: " + p1war);
                    System.out.println("P2 card: " + p2war);
                    toBeAdded.addCard(p1war);
                    toBeAdded.addCard(p2war);
                    if (p1war.beats(p2war)) {
                        p1.addCards(toBeAdded);
                        String str= "";
                        for (Card card: toBeAdded.getCards()) {
                            str = str + card.toString() + " ";
                        }
                        System.out.println("P1 wins round gets Trick pile: " + str);
                        toBeAdded.clear();
                        this.round++;
                        war = false;
                    }
                    else if (p2war.beats(p1war)) {
                        String str= "";
                        p2.addCards(toBeAdded);
                        for (Card card : toBeAdded.getCards()) {
                            str = str + card.toString() + " ";
                        }
                        System.out.println("P2 wins round gets Trick pile: " + str);
                        toBeAdded.clear();
                        this.round++;
                        war = false;
                    }
                }
            }
        }
        System.out.println(p1);
        System.out.println(p2);
        if (p1.hasCard()){
            p1.setWinner();
            System.out.println("P1 WINS!");
        }
        else if(p2.hasCard()){
            p2.setWinner();
            System.out.println("P2 WINS!");
        }
    }

    /**
     * The main method get the command line arguments, then constructs
     * and plays the game.  The Pile's random number generator and seed
     * need should get set here using Pile.setSeed(seed).
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java War cards-per-player seed");
        } else {
            int cardsPerPlayer = Integer.parseInt(args[0]);
            // must be between 1 and 26 cards per player
            if (cardsPerPlayer <= 0 || cardsPerPlayer > MAX_CARDS_PER_PLAYER) {
                System.out.println("cards-per-player must be between 1 and " + MAX_CARDS_PER_PLAYER);
            } else {
                // set the rng seed
                Pile.setSeed(Integer.parseInt(args[1]));
                War war = new War(cardsPerPlayer);
                war.playGame();
            }
        }
    }
}
