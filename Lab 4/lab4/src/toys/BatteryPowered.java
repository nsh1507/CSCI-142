package toys;

/**
 * BatteryPowered is an abstract subclass of Toy. RCBoat and Robot
 * inherit from this class to obtain its state and behaviors
 *
 * @author Nam Huynh
 */
public abstract class BatteryPowered extends Toy{
    /** The number of batteries for the battery powered toy created*/
    private int numBatteries;
    /** The battery level for the battery powered toy created*/
    private int batteryLevel;
    /** The max battery level, capped at 100, for every battery powered toy created*/
    public final static int FULLY_CHARGED = 100;
    /** The minimum battery level, capped at 0, for every battery powered toy created*/
    public final static int DEPLETED = 0;

    /**
     * A constructor that takes the product code, name and number of batteries.
     *
     * @param productCode the code of the battery powered toy
     * @param name the name of the battery powered toy
     * @param numBatteries the  number of batteries for the battery powered toy
     */
    public BatteryPowered(int productCode, String name, int numBatteries){
        super(productCode, name);
        this.numBatteries = numBatteries;
        this.batteryLevel = FULLY_CHARGED;
    }

    /**
     * @return the current battery level of the battery powered toy
     */
    public int getBatteryLevel(){
        return batteryLevel;
    }

    /**
     * @return the number of batteries the battery powered toy
     */
    public int getNumBatteries(){
        return numBatteries;
    }

    /**
     * Reduced the battery level by: time + number-of-batteries
     * If the battery level is depleted:
     *      Battery level is capped at DEPLETED
     *      Message in format "DEPLETED:toy-toString:" is displayed
     *      The battery level is restored to FULLY_CHARGED.
     *      Message in format "RECHARGED:toy-toString:" is displayed
     *
     * @param time the time that the toy is played with
     */
    public void useBatteries(int time) {
        int reduced = time + numBatteries;
        batteryLevel -= reduced;
        if (batteryLevel <= DEPLETED) {
            batteryLevel = DEPLETED;
            System.out.println("\tDEPLETED:" + this);
            batteryLevel = FULLY_CHARGED;
            System.out.println("\tRECHARGED:" + this);
        }
    }

    /**
     * @return a string in the format:
     *    "Toy{PC:?, N:?, H:?, R:?, W:?}, BatteryPowered{BL:?, NB:?}
     * The Toy part of the string comes from Toy's toString()
     * BL for the battery level
     * NB for the number of batteries
     */
    @Override
    public String toString(){
        return super.toString() + ", BatteryPowered{BL:" + batteryLevel +", NB:" + numBatteries + "}";
    }
}

