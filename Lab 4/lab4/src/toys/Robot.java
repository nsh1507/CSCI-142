package toys;

/**
 * Robot is a concrete class that inherits from BatteryPowered.
 *
 * @author Nam Huynh
 */
public class Robot extends BatteryPowered {
    /** Whether the robot flies or not*/
    private boolean flying;
    /** The distance that the robot travelled*/
    private int distance;
    /** The unique product code for every robot created. Starting at
     *  400 and increment by 1 each time the constructor is called*/
    private static int prodCode = 400;
    /** The fly speed that every robot has*/
    public final static int FLY_SPEED = 25;
    /** The run speed that every robot has*/
    public final static int RUN_SPEED = 10;
    /** The initial speed that every robot will be given once created*/
    public final static int INITIAL_SPEED = 0;

    /**
     * A constructor that takes the name, number of batteries, and
     * whether it flies or not. The unique product code starts at
     * 400 and increases by 1 each time a new one is created
     *
     * @param name the name of the RCBoat
     * @param numBatteries the number of batteries for the robot
     * @param flying whether the robot flies or not
     */
    protected Robot(String name, int numBatteries, boolean flying){
        super(prodCode, name, numBatteries);
        this.flying = flying;
        this.distance = INITIAL_SPEED;
        prodCode++;
    }

    /**
     * @return the whether the robot flies or not
     */
    public boolean isFlying() {
        return flying;
    }

    /**
     * @return the distance the robot travelled
     */
    public int getDistance(){
        return distance;
    }

    /**
     * An abstract method that is special to the robot class.
     * If the robot flies:
     *     The distance is increased by time * FLY_SPEED
     *     Message in format "{name} is flying around with total distance: {distance}" is displayed
     *     The wear is increased by FLY_SPEED
     * If the robot does not fly:
     *     The distance is increased by time * RUN_SPEED
     *     Message in format "{name} is running around with total distance: {distance}" is displayed
     *     The wear is increased by RUN_SPEED
     * The batteries are used for the amount of time
     *
     * @param time the time that the toy is played with
     */
    @Override
    protected void specialPlay(int time) {
        if(flying){
            distance += time * FLY_SPEED;
            System.out.println("\t" + getName() + " is flying around with total distance: " + distance);
            increaseWear(FLY_SPEED);
        }
        else{
            distance += time * RUN_SPEED;
            System.out.println("\t" + getName() + " is running around with total distance: " + distance);
            increaseWear(RUN_SPEED);
        }
        useBatteries(time);
    }

    /**
     * @return a string in the format:
     *    "Toy{PC:?, N:?, H:?, R:?, W:?}, BatteryPowered{BL:?, NB:?},  Robot{F:?, D:?}
     * The Toy part of the string comes from Toy's toString()
     * The BatteryPowered part of the string comes from BatteryPowered's toString()
     * F for flying or not (true or false)
     * D for distance
     */
    @Override
    public String toString(){
        return super.toString() + ", Robot{F:" + flying + ", D:" + distance + "}";
    }
}
