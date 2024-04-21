package test;

import org.junit.jupiter.api.*;
import toys.BatteryPowered;
import toys.RCBoat;
import toys.ToyFactory;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit 5 unit test for RCBoat
 *
 * @author RIT CS
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestRCBoat {

    /** standard output capturer */
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    /** Test all aspects of the first RCBoat */
    @Test
    @Order(1)
    public void testFirstRCBoat() {
        RCBoat boat1 = (RCBoat) ToyFactory.makeToy("RC_BOAT BlackPearl 4");
        assertEquals(300, boat1.getProductCode());
        assertEquals("BlackPearl", boat1.getName());
        assertEquals(0, boat1.getHappiness());
        assertFalse(boat1.isRetired());
        assertEquals(0, boat1.getWear());
        assertEquals(100, boat1.getBatteryLevel());
        assertEquals(4, boat1.getNumBatteries());
        assertEquals(10, boat1.getSpeed());
        assertEquals("Toy{PC:300, N:BlackPearl, H:0, R:false, W:0.0}, BatteryPowered{BL:100, NB:4}, RCBoat{S:10}", boat1.toString());

        boat1.play(60);
        String expected = "PLAYING(60): Toy{PC:300, N:BlackPearl, H:0, R:false, W:0.0}, BatteryPowered{BL:100, NB:4}, RCBoat{S:10}" + System.lineSeparator();
        expected += "\tBlackPearl races around at 10knots!";
        assertEquals(expected, outputStreamCaptor.toString()
                .trim());
        assertEquals(60, boat1.getHappiness());
        assertFalse(boat1.isRetired());
        assertEquals(10, boat1.getWear());
        assertEquals(36, boat1.getBatteryLevel());
        assertEquals(4, boat1.getNumBatteries());
        assertEquals(15, boat1.getSpeed());

        outputStreamCaptor.reset();
        boat1.play(12);
        expected = "PLAYING(12): Toy{PC:300, N:BlackPearl, H:60, R:false, W:10.0}, BatteryPowered{BL:36, NB:4}, RCBoat{S:15}" + System.lineSeparator();
        expected += "\tBlackPearl races around at 15knots!";
        assertEquals(expected, outputStreamCaptor.toString()
                .trim());
        assertEquals(72, boat1.getHappiness());
        assertFalse(boat1.isRetired());
        assertEquals(25, boat1.getWear());
        assertEquals(20, boat1.getBatteryLevel());
        assertEquals(4, boat1.getNumBatteries());
        assertEquals(20, boat1.getSpeed());

        outputStreamCaptor.reset();
        boat1.play(37);
        expected = "PLAYING(37): Toy{PC:300, N:BlackPearl, H:72, R:false, W:25.0}, BatteryPowered{BL:20, NB:4}, RCBoat{S:20}" + System.lineSeparator();
        expected += "\tBlackPearl races around at 20knots!" +  System.lineSeparator();
        expected += "\tDEPLETED:Toy{PC:300, N:BlackPearl, H:72, R:false, W:25.0}, BatteryPowered{BL:0, NB:4}, RCBoat{S:20}"  + System.lineSeparator();
        expected += "\tRECHARGED:Toy{PC:300, N:BlackPearl, H:72, R:false, W:25.0}, BatteryPowered{BL:100, NB:4}, RCBoat{S:20}"  + System.lineSeparator();
        expected += "RETIRED: Toy{PC:300, N:BlackPearl, H:109, R:true, W:45.0}, BatteryPowered{BL:100, NB:4}, RCBoat{S:25}";
        assertEquals(expected, outputStreamCaptor.toString()
                .trim());
        assertEquals(109, boat1.getHappiness());
        assertTrue(boat1.isRetired());
        assertEquals(45, boat1.getWear());
        assertEquals(100, boat1.getBatteryLevel());
        assertEquals(4, boat1.getNumBatteries());
        assertEquals(25, boat1.getSpeed());
    }

    /** More testing with second RCBoat */
    @Test
    @Order(2)
    public void testSecondRCBoat() {
        RCBoat boat2 = (RCBoat) ToyFactory.makeToy("RC_BOAT TheORCA 10");
        assertEquals(301, boat2.getProductCode());
        assertEquals("TheORCA", boat2.getName());
        assertEquals(0, boat2.getHappiness());
        assertFalse(boat2.isRetired());
        assertEquals(0, boat2.getWear());
        assertEquals(100, boat2.getBatteryLevel());
        assertEquals(10, boat2.getNumBatteries());
        assertEquals(10, boat2.getSpeed());
        assertEquals("Toy{PC:301, N:TheORCA, H:0, R:false, W:0.0}, BatteryPowered{BL:100, NB:10}, RCBoat{S:10}", boat2.toString());

        boat2.play(98);
        String expected = "PLAYING(98): Toy{PC:301, N:TheORCA, H:0, R:false, W:0.0}, BatteryPowered{BL:100, NB:10}, RCBoat{S:10}" + System.lineSeparator();
        expected += "\tTheORCA races around at 10knots!" + System.lineSeparator();
        expected += "\tDEPLETED:Toy{PC:301, N:TheORCA, H:0, R:false, W:0.0}, BatteryPowered{BL:0, NB:10}, RCBoat{S:10}" + System.lineSeparator();
        expected += "\tRECHARGED:Toy{PC:301, N:TheORCA, H:0, R:false, W:0.0}, BatteryPowered{BL:100, NB:10}, RCBoat{S:10}";
        assertEquals(expected, outputStreamCaptor.toString()
                .trim());
        assertEquals(98, boat2.getHappiness());
        assertFalse(boat2.isRetired());
        assertEquals(10, boat2.getWear());
        assertEquals(100, boat2.getBatteryLevel());
        assertEquals(10, boat2.getNumBatteries());
        assertEquals(15, boat2.getSpeed());

        outputStreamCaptor.reset();
        boat2.play(1);
        expected = "PLAYING(1): Toy{PC:301, N:TheORCA, H:98, R:false, W:10.0}, BatteryPowered{BL:100, NB:10}, RCBoat{S:15}" + System.lineSeparator();
        expected += "\tTheORCA races around at 15knots!";
        assertEquals(expected, outputStreamCaptor.toString()
                .trim());
        assertEquals(99, boat2.getHappiness());
        assertFalse(boat2.isRetired());
        assertEquals(25, boat2.getWear());
        assertEquals(89, boat2.getBatteryLevel());
        assertEquals(10, boat2.getNumBatteries());
        assertEquals(20, boat2.getSpeed());

        // make sure RCBoat extends BatteryPowered
        BatteryPowered bp = boat2;
        assertEquals(89, bp.getBatteryLevel());
        assertEquals(10, bp.getNumBatteries());
    }
}