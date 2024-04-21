package toys;
/**
 * RCBoat is a concrete class that inherits from BatteryPowered.
 *
 * @author Nam Huynh
 */
public class RCBoat extends BatteryPowered {
    /** The initial speed for every RCBoat created*/
    public static final int STARTING_SPEED = 10;
    /** The amount the speed will be increased for every RCBoat created*/
    public static final int SPEED_INCREASE = 5;
    /** The unique product code for every RCBoat created. Starting at
     *  300 and increment by 1 each time the constructor is called*/
    private static int proCode = 300;
    /** The speed of the RCBoat*/
    private int speed;

    /**
     * A constructor that takes the name and number of batteries. The unique
     * product code starts at 300 and increases by 1 each time a new one is created
     * Set the speed to be equal to STARTING_SPEED
     *
     * @param name the name of the RCBoat
     * @param numBatteries the number of batteries for the RCBoat
     */
    protected RCBoat(String name, int numBatteries){
        super(proCode, name, numBatteries);
        proCode++;
        this.speed = STARTING_SPEED;
    }

    /**
     * @return the current speed of the RCBoat
     */
    public int getSpeed(){
        return speed;
    }

    /**
     * An abstract method that is special to the RCBoat class.
     * Displays a standard output:
     *    "{name} races around at {speed} knots!"
     * The batteries are used for the amount of time
     * The wear increases by the current speed
     * The speed increases by SPEED_INCREASE
     *
     * @param time the time that the toy is played with
     */
    @Override
    protected void specialPlay(int time) {
        System.out.println("\t" + super.getName() + " races around at " + speed + "knots!");
        useBatteries(time);
        increaseWear(speed);
        speed += SPEED_INCREASE;
    }

    /**
     * @return a string in the format:
     *    "Toy{PC:?, N:?, H:?, R:?, W:?}, BatteryPowered{BL:?, NB:?}, RCBoat{S:?}
     * The Toy part of the string comes from Toy's toString()
     * The BatteryPowered part of the string comes from BatteryPowered's toString()
     * S for the speed
     */
    @Override
    public String toString(){
        return super.toString() + ", RCBoat{S:" + speed + "}";
    }
}
