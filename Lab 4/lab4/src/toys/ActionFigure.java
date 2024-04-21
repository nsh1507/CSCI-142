package toys;

/** ActionFigure is a concrete class that inherits from Doll.
 *
 * @author Nam Huynh
 */

public class ActionFigure extends Doll{
    /** The minimum energy level of every action figure created*/
    public final static int MIN_ENERGY_LEVEL = 1;
    /** The same hair color of every action figure created*/
    public final static Color HAIR_COLOR = Color.ORANGE;
    /** The energy level of the action figure*/
    private int energyLevel;
    /** The product code for every action figure created. Starting at
     *  500 and increment by 1 each time the constructor is called*/
    private static int proCode = 500;

    /**
     * A constructor that takes the name, age and spoken catchphrase
     * All action figures have HAIR_COLOR (orange) hair. The unique product
     * code starts at 500 and increases by 1 each time a new one is created
     *
     * @param name the name of the action figure
     * @param age the age of the action figure
     * @param speak the spoken catchphrase of the action figure
     */
    protected ActionFigure(String name, int age, String speak){
        super(proCode, name, HAIR_COLOR, age, speak);
        energyLevel = MIN_ENERGY_LEVEL;
        proCode ++;
    }

    /**
     * @return the current energy level of the action figure
     */
    public int getEnergyLevel(){
        return energyLevel;
    }

    /**
     * An abstract method that is special to the action figure class.
     * Displays a standard output:
     *    "{name} kung foo chops with {energy-formula} energy!"
     * Increase the energy level by 1
     * Invoke the special play method of Doll
     *
     * @param time the time that the toy is played with
     */
    @Override
    protected void specialPlay(int time) {
        int energyFormula = energyLevel * time;
        System.out.println("\t" + super.getName() + " kung foo chops with " + energyFormula + " energy!");
        energyLevel++;
        super.specialPlay(time);
    }

    /**
     * @return a string in the format:
     *    "Toy{PC:?, N:?, H:?, R:?, W:?}, Doll{HC:?, A:?, S:?}, ActionFigure{E:?}"
     * The Toy part of the string comes from Toy's toString()
     * The Doll part of the string comes from Doll's toString()
     * E for the energy
     */
    @Override
    public String toString(){
        return super.toString() + ", ActionFigure{E:" + energyLevel + "}";
    }
}
