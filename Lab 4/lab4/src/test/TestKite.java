package test;

import org.junit.jupiter.api.*;
import toys.Color;
import toys.Kite;
import toys.ToyFactory;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit 5 unit test for Kite
 *
 * @author RIT CS
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestKite {
    /** standard output capturer */
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    /** Test all aspects of the first Kite */
    @Test
    @Order(1)
    public void testFirstKite() {
        Kite kite1 = (Kite) ToyFactory.makeToy("KITE Cometa GREEN DELTA");
        assertEquals(100, kite1.getProductCode());
        assertEquals("Cometa", kite1.getName());
        assertEquals(0, kite1.getHappiness());
        assertFalse(kite1.isRetired());
        assertEquals(0, kite1.getWear());
        assertEquals(Color.GREEN, kite1.getColor());
        assertEquals(Kite.Type.DELTA, kite1.getType());
        assertEquals( "Toy{PC:100, N:Cometa, H:0, R:false, W:0.0}, Kite{C:GREEN, T:DELTA}", kite1.toString());

        kite1.play(10);
        String expected = "PLAYING(10): Toy{PC:100, N:Cometa, H:0, R:false, W:0.0}, Kite{C:GREEN, T:DELTA}" + System.lineSeparator();
        expected += "\tFlying the GREEN, DELTA kite Cometa";
        assertEquals(expected, outputStreamCaptor.toString().trim());
        assertEquals(10, kite1.getHappiness());
        assertFalse(kite1.isRetired());
        assertEquals(0.5, kite1.getWear());

        outputStreamCaptor.reset();
        kite1.play(5);
        expected = "PLAYING(5): Toy{PC:100, N:Cometa, H:10, R:false, W:0.5}, Kite{C:GREEN, T:DELTA}" + System.lineSeparator();
        expected += "\tFlying the GREEN, DELTA kite Cometa";
        assertEquals(expected, outputStreamCaptor.toString()
                .trim());
        assertEquals(15, kite1.getHappiness());
        assertFalse(kite1.isRetired());
        assertEquals(0.75, kite1.getWear());

        outputStreamCaptor.reset();
        kite1.play(100);
        expected = "PLAYING(100): Toy{PC:100, N:Cometa, H:15, R:false, W:0.75}, Kite{C:GREEN, T:DELTA}" + System.lineSeparator();
        expected += "\tFlying the GREEN, DELTA kite Cometa" + System.lineSeparator();
        expected += "RETIRED: Toy{PC:100, N:Cometa, H:115, R:true, W:5.75}, Kite{C:GREEN, T:DELTA}";
        assertEquals(expected, outputStreamCaptor.toString()
                .trim());
        assertEquals(115, kite1.getHappiness());
        assertTrue(kite1.isRetired());
        assertEquals(5.75, kite1.getWear());
    }

    /** More testing with second Kite */
    @Test
    @Order(2)
    public void testSecondKite() {
        Kite kite2 = (Kite) ToyFactory.makeToy("KITE Pipa RED DIAMOND");
        assertEquals(101, kite2.getProductCode());
        assertEquals("Pipa", kite2.getName());
        assertEquals(0, kite2.getHappiness());
        assertFalse(kite2.isRetired());
        assertEquals(0, kite2.getWear());
        assertEquals(Color.RED, kite2.getColor());
        assertEquals(Kite.Type.DIAMOND, kite2.getType());
        assertEquals("Toy{PC:101, N:Pipa, H:0, R:false, W:0.0}, Kite{C:RED, T:DIAMOND}", kite2.toString());

        kite2.play(42);
        String expected = "PLAYING(42): Toy{PC:101, N:Pipa, H:0, R:false, W:0.0}, Kite{C:RED, T:DIAMOND}" + System.lineSeparator();
        expected += "\tFlying the RED, DIAMOND kite Pipa";
        assertEquals(expected, outputStreamCaptor.toString()
                .trim());
        assertEquals(42, kite2.getHappiness());
        assertFalse(kite2.isRetired());
        assertEquals(2.1, kite2.getWear());

        // this test makes sure the product code is not static in Toy
        Kite pd3 = (Kite) ToyFactory.makeToy("KITE Cometa GREEN DELTA");
        assertEquals(102, pd3.getProductCode());
        assertEquals(101, kite2.getProductCode());
    }
}