package toys;

/**
 * Doll is a concrete class that inherits from Toy.
 *
 * @author Nam Huynh
 */
public class Doll extends Toy{
    /** The unique product code for every doll created. Starting at
     *  200 and increment by 1 each time the constructor is called*/
    private static int proCode = 200;
    /** The hair color of the doll created*/
    private Color haircolor;
    /** The age of the doll created*/
    private int age;
    /** The spoken catchphrase of the doll created*/
    public String speak;

    /**
     * A constructor that takes the name, hair color, age, and spoken catchphrase.
     * The unique product code starts at 200 and increases by 1 each time a new one is created.
     *
     * @param name the name of the doll
     * @param haircolor the hair color of the doll
     * @param age the age of the doll
     * @param speak the spoken catchphrase of the doll
     */
    protected Doll (String name, Color haircolor, int age, String speak){
        super(proCode,name);
        proCode++;
        this.speak = speak;
        this.haircolor = haircolor;
        this.age = age;
    }

    /**
     * A constructor that takes the product code, name, hair color, age, and spoken catchphrase.
     * The unique product code starts at 500 and increases by 1 each time a new one is created.
     *
     * @param name the name of the doll
     * @param haircolor the hair color of the doll
     * @param age the age of the doll
     * @param speak the spoken catchphrase of the doll
     */
    protected Doll(int productCode, String name, Color haircolor, int age, String speak) {
        super(productCode,name);
        this.speak = speak;
        this.haircolor = haircolor;
        this.age = age;
    }

    /**
     * @return the hair color of the doll
     */
    public Color getHairColor(){
        return haircolor;
    }

    /**
     * @return the age of the doll
     */
    public int getAge(){
        return age;
    }

    /**
     * @return the spoken catchphrase of the doll
     */
    public String getSpeak(){
        return speak;
    }

    /**
     * An abstract method that is special to the doll class
     * Displays a standard output:
     *      "{name} brushes their {hair color} hair and says, "{speak}""
     * Increase the wear by the age of the doll
     *
     * @param time the time that the toy is played with
     */
    @Override
    protected void specialPlay(int time) {
        System.out.println("\t" + super.getName() + " brushes their " + haircolor + " hair and says, " + '"'+ speak + '"');
        increaseWear(age);
    }

    /**
     * @return a string in the format:
     *    "Toy{PC:?, N:?, H:?, R:?, W:?}, Doll{HC:?, A:?, S:?}
     * The Toy part of the string comes from Toy's toString()
     * HC for the hair color
     * A for the age
     * S for the spoken catchphrase
     */
    @Override
    public String toString() {
        return super.toString() + ", Doll{HC:" + haircolor +", A:" + age + ", S:" + speak +"}";
    }
}
