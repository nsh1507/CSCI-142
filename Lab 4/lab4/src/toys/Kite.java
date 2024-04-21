package toys;

/**
 * Kite is a concrete class that inherits from Toy
 *
 * @author Nam Huynh
 */
public class Kite extends Toy {
    /** The color of the kite*/
    private Color color;
    /** The unique product code for every kite created. Starting at
     *  100 and increment by 1 each time the constructor is called*/
    private static int serialCode = 100;
    /** The type of the kite*/
    private Type type;
    /** The value that the wear is multiplied by*/
    public final static double WEAR_MULTIPLE = 0.05;

    /**
     * An enumerated type for some common type of kites
     */
    public enum Type{
        DELTA, DIAMOND, PARAFOIL, CELLULAR, SLED, STUNT, ROKKAKUS
    }

    /**
     * A constructor that takes the name, color, and type. The unique product
     * code starts at 100 and increases by 1 each time a new one is created.
     *
     * @param name the name of the kite
     * @param color the color of the kite
     * @param type the type of the kite
     */
    protected Kite(String name, Color color, Type type){
        super(serialCode, name);
        serialCode ++;
        this.color = color;
        this.type = type;
    }

    /**
     * @return the color of the kite
     */
    public Color getColor(){
        return color;
    }

    /**
     * @return the type of the kite
     */
    public Type getType() {
        return type;
    }

    /**
     * An abstract method that is special to the kite class.
     * Displays a standard output:
     *     Flying the {color}, {type} kite {name}
     * Increase the wear by a multiple of WEAR_MULTIPLIER * time.
     *
     * @param time the time that the toy is played with
     */
    @Override
    protected void specialPlay(int time) {
        System.out.println("\tFlying the " + color + ", " + type + " kite " + super.getName());
        double minutes = time * WEAR_MULTIPLE;
        increaseWear(minutes);
    }

    /**
     * @return a string in the format:
     *     "Toy{PC:?, N:?, H:?, R:?, W:?}, Kite{C:?, T:?}
     * The Toy part of the string comes from Toy's toString()
     * C for the color
     * T for the type
     */
    @Override
    public String toString(){
        return super.toString() + ", Kite{C:" + color +", T:" + type +"}";
    }
}
