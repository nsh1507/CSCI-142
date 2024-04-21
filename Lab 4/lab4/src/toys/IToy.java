package toys;

public interface IToy {
    /**
     * IToy is an interface for the public behaviors of Toy.
     */
    int getProductCode();
    String getName();
    int getHappiness();
    boolean isRetired();
    double getWear();
    void increaseWear(double amount);
    void play(int time);
}
