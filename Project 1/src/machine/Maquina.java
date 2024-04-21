package machine;

import common.Errors;
import common.SymbolTable;
import machine.instructions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/**
 * The machine can process/execute a series of low level MAQ instructions using
 * an instruction stack and symbol table.
 *
 * @author RIT CS
 * @author Nam Huynh
 */
public class Maquina {
    /** the push instruction */
    public final static String PUSH = "PUSH";
    /** the print instruction */
    public final static String PRINT = "PRINT";
    /** the store instruction */
    public final static String STORE = "STORE";
    /** the load instruction */
    public final static String LOAD = "LOAD";
    /** the negate instruction */
    public final static String NEGATE = "NEG";
    /** the square root instruction */
    public final static String SQUARE_ROOT = "SQRT";
    /** the add instruction */
    public final static String ADD = "ADD";
    /** the subtract instruction */
    public final static String SUBTRACT = "SUB";
    /** the multiply instruction */
    public final static String MULTIPLY = "MUL";
    /** the divide instruction */
    public final static String DIVIDE = "DIV";
    /** the modulus instruction */
    public final static String MODULUS = "MOD";
    private SymbolTable symbolTable;
    private InstructionStack instructionStack;
    private ArrayList<Instruction> instructionList;
    private List<Instruction> printInstruction;

    /** the list of valid machine instructions */
    public static final List< String > OPERATIONS =
            List.of(
                    ADD,
                    DIVIDE,
                    LOAD,
                    MODULUS,
                    MULTIPLY,
                    NEGATE,
                    PUSH,
                    PRINT,
                    SQUARE_ROOT,
                    STORE,
                    SUBTRACT
            );

    /** the terminating character when reading machine instructions from user (not file) */
    private final static String EOF = ".";

    /**
     * Create a new machine, with an empty symbol table, instruction stack, and
     * list of instructions.
     */
    public Maquina() {
        this.instructionStack = new InstructionStack();
        this.symbolTable = new SymbolTable();
        this.instructionList = new ArrayList<>();
        this.printInstruction = new ArrayList<>();
    }

    /**
     * Return the instruction stack.
     *
     * @return the stack
     */
    public InstructionStack getInstructionStack() {
        return instructionStack;
    }

    /**
     * Return the symbol table.
     *
     * @return the symbol table
     */
    public SymbolTable getSymbolTable() {
        return symbolTable;
    }


    /**
     * Assemble the machine instructions.
     *
     * @param maqIn the input source
     * @param stdin true if input is coming from standard input (for prompting)
     */
    public void assemble(Scanner maqIn, boolean stdin) {
        if (stdin) {System.out.print("🤖 ");}
        while (maqIn.hasNextLine()) {
            if (stdin) {System.out.print("🤖 ");}
            String[] fields = maqIn.nextLine().strip().split("\\s+");
            if (OPERATIONS.contains(fields[0])) {
                switch (fields[0]) {
                    case "PUSH" -> {
                        int value = Integer.parseInt(fields[1]);
                        Push push = new Push(value, this);
                        instructionList.add(push);
                        printInstruction.add(push);
                    }
                    case "ADD" -> {
                        Add add = new Add(this);
                        instructionList.add(add);
                        printInstruction.add(add);
                    }
                    case "DIV" -> {
                        Divide divide = new Divide(this);
                        instructionList.add(divide);
                        printInstruction.add(divide);
                    }
                    case "MOD" -> {
                        Modulus modulus = new Modulus(this);
                        instructionList.add(modulus);
                        printInstruction.add(modulus);
                    }
                    case "NEG" -> {
                        Negate negate = new Negate(this);
                        instructionList.add(negate);
                        printInstruction.add(negate);
                    }
                    case "SUB" -> {
                        Subtract subtract = new Subtract(this);
                        instructionList.add(subtract);
                        printInstruction.add(subtract);
                    }
                    case "MUL" -> {
                        Multiply multiply = new Multiply(this);
                        instructionList.add(multiply);
                        printInstruction.add(multiply);
                    }
                    case "SQRT" -> {
                        SquareRoot squareRoot = new SquareRoot(this);
                        instructionList.add(squareRoot);
                        printInstruction.add(squareRoot);
                    }
                    case "PRINT" -> {
                        Print print = new Print(this);
                        instructionList.add(print);
                        printInstruction.add(print);
                    }
                    case "LOAD" -> {
                        Load load = new Load(fields[1], this);
                        instructionList.add(load);
                        printInstruction.add(load);
                    }
                    case "STORE" -> {
                        Store store = new Store(fields[1], this);
                        instructionList.add(store);
                        printInstruction.add(store);
                    }
                    default -> {
                    }
                }
            }
            else if (fields[0].equals(EOF)) {break;}
            else {
                Errors.report(Errors.Type.ILLEGAL_INSTRUCTION, fields[0]);
            }
        }
        System.out.println("(MAQ) Machine instructions:");
    }

    /**
     * Executes each assembled machine instruction in order.  When completed it
     * displays the symbol table and the instruction stack.
     */
    public void execute() {
        printInstruction.forEach(System.out::println);
        System.out.println("(MAQ) Executing...");
        for (Instruction instruction: instructionList){
            instruction.execute();
        }
        System.out.println("(MAQ) Completed execution!");
        System.out.println("(MAQ) Symbol table:");
        System.out.print(symbolTable);
        System.out.println(instructionStack);
        }

    /**
     * The main method.  Machine instructions can either be specified from standard input
     * (no command line), or from a file (only argument on command line).  From
     * here the machine assembles the instructions and then executes them.
     *
     * @param args command line argument (optional)
     * @throws FileNotFoundException if the machine file is not found
     */
    public static void main(String[] args) throws FileNotFoundException {
        // determine input source
        Scanner maqIn = null;
        boolean stdin = false;
        if (args.length == 0) {
            maqIn = new Scanner(System.in);
            stdin = true;
        } else if (args.length == 1){
            maqIn = new Scanner(new File(args[0]));
        } else {
            System.out.println("Usage: java Maquina [filename.maq]");
            System.exit(1);
        }

        Maquina machine = new Maquina();
        machine.assemble(maqIn, stdin);     // assemble the machine instructions
        machine.execute();                  // execute the program
        maqIn.close();
    }
}
