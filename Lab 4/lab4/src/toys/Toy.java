package toys;

/**
 * Toy is the abstract superclass of the toy inheritance hierarchy.
 *
 * @author Nam Huynh
 */
public abstract class Toy implements IToy {
    /** The initial happiness that every toy will be given once created*/
    public final static int INITIAL_HAPPINESS = 0;
    /** The cap of happiness that every toy will be given once created*/
    public final static int MAX_HAPPINESS = 100;
    /** The initial wear that every toy will be given once created*/
    public final static double INITIAL_WEAR = 0.0;
    /** The product code of the toy created*/
    private int productCode;
    /** The name of the toy created*/
    private String name;
    /** The happiness of the toy*/
    private int happiness;
    /** The wear of the toy*/
    private double wear;
    /** Whether the toy is retired or not*/
    private boolean retire;

    /**
     * A constructor that takes the product code and name.
     *
     * @param productCode the product code of the toy
     * @param name the name of the toy
     */
    protected Toy(int productCode, String name) {
        this.productCode = productCode;
        this.name = name;
        this.happiness = INITIAL_HAPPINESS;
        this.wear = INITIAL_WEAR;
        this.retire = false;
    }

    /**
     * @return the product code of the toy
     */
    @Override
    public int getProductCode() {
        return productCode;
    }

    /**
     * @return the name of the toy
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * @return the current happiness of the toy
     */
    @Override
    public int getHappiness() {
        return happiness;
    }

    /**
     * @return whether the toy is retired or not
     */
    @Override
    public boolean isRetired() {
        return retire;
    }

    /**
     * @return the current wear of the toy
     */
    @Override
    public double getWear() {
        return wear;
    }

    /**
     * Increase the wear of the toy by a certain amount
     * @param amount the amount that the wear will be increased by
     */
    @Override
    public void increaseWear(double amount) {
        this.wear = this.wear + amount;
    }

    /**
     * Message in format "PLAYING(##): toy-toString" is displayed
     * The special play is invoked for the same number of minutes.
     * The happiness of the toy increases by the number of minutes.
     * If the toy retires, displays a message to standard output in the format: "RETIRED: toy-toString"
     * @param time the time that the toy is played with
     */
    @Override
    public void play(int time) {
        System.out.println("PLAYING(" + time +"): " + this);
        specialPlay(time);
        happiness += time;
        if (happiness >= MAX_HAPPINESS){
            retire = true;
            System.out.println("RETIRED: " + this);
        }
    }

    /**
     * Each toy has its own defined behavior when being
     * played with and is therefore abstract here.
     * @param time the time that the toy is played with
     */
    protected abstract void specialPlay(int time);

    /**
     * @return a string in the format:
     *     "Toy{PC:?, N:?, H:?, R:?, W:?}
     * PC for product code
     * N for name
     * H for current happiness
     * R for retired (true or false)
     * W for current wear
     */
    @Override
    public String toString(){
        return "Toy{PC:" + productCode +", N:" + name + ", H:" + happiness + ", R:" + isRetired() + ", W:" + wear + "}";
    }
}
