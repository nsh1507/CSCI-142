/**
 * @author Nam Huynh (nsh1507)
 */

package interpreter;

import common.Errors;
import common.SymbolTable;
import interpreter.nodes.action.ActionNode;
import interpreter.nodes.action.Assignment;
import interpreter.nodes.action.Print;
import interpreter.nodes.expression.*;
import machine.Maquina;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * The main program for the high level ARB language.  It takes a program in ARB,
 * converts it into a token list, builds the parse trees for each statement,
 * displays the program in infix, interprets/evaluates the program, then
 * compiles into MAQ instructions so that the machine, Maquina, can execute it.
 *
 * @author RIT CS
 */
public class Arboles {
    /** the terminating character when reading machine instructions from user (not file) */
    private final static String EOF = ".";

    /** the ARB print token */
    private final static String PRINT = "@";
    /** the ARB assignment token */
    private final static String ASSIGN = "=";

    /** the location to generate the compiled ARB program of MAQ instructions */
    private final static String TMP_MAQ_FILE = "tmp/TEMP.maq";

    /** the token list use to read the file */
    private final ArrayList<String> tokenList;

    /** the action node list use to execute the program */
    private final List<ActionNode> actionNodeList;

    /**
     * Create a new Arboles instance.  The result of this method is the tokenization
     * of the entire ARB input into a list of strings.
     *
     * @param in where to read the ARB input from
     * @param stdin if true, the user should be prompted to enter ARB statements until
     *              a terminating ".".
     */
    public Arboles(Scanner in, boolean stdin) {
        this.tokenList = new ArrayList<>();
        this.actionNodeList = new ArrayList<>();
        List<String> printList = new ArrayList<>();
        if (stdin) {System.out.print("🌳 ");}
        while (in.hasNextLine()) {
            if (stdin) {System.out.print("🌳 ");}
            String[] fields = in.nextLine().strip().split("\\s+");
            if (fields[0].equals(EOF)) {break;}
            StringBuilder print = new StringBuilder();
            for (String el: fields){
                print.append(" ").append(el);}
            printList.add(print.toString());
            tokenList.addAll(List.of(fields));
        }
        System.out.println("(ARB) prefix...");
        printList.forEach(System.out::println);
    }

    /**
     * Build the parse trees into the program which is a list of ActionNode's -
     * one per line of ARB input.
     */
    public void buildProgram() {
        while (!tokenList.isEmpty()){
            String token = tokenList.remove(0);
            if (token.equals(ASSIGN)){
                String varName = tokenList.remove(0);
                Assignment assignment = new Assignment(varName, recurse(null));
                actionNodeList.add(assignment);
            } else if (token.equals(PRINT)) {
                Print print = new Print(recurse(null));
                actionNodeList.add(print);
            }
            else{
                Errors.report(Errors.Type.ILLEGAL_ACTION, token);
            }
        }
    }

    /**
     * Using the recurse method to implement parse
     * @param expressionNode the expression node for Assignment or Print
     * @return an ExpressionNode
     */
    public ExpressionNode recurse (ExpressionNode expressionNode){
        try{
            if (UnaryOperation.OPERATORS.contains(tokenList.get(0))) {
                return new UnaryOperation(tokenList.remove(0), recurse(expressionNode));}
            else if (BinaryOperation.OPERATORS.contains(tokenList.get(0))){
                return new BinaryOperation(tokenList.remove(0), recurse(expressionNode), recurse(expressionNode));}
            else if (tokenList.get(0).equals(ASSIGN) || tokenList.get(0).equals(PRINT)){return expressionNode;}
            else if (tokenList.get(0).matches("^[a-zA-Z].*")) {return new Variable(tokenList.remove(0));}
            else if (isDigit(tokenList.get(0))) {return new Constant(Integer.parseInt(tokenList.remove(0)));}
            else {Errors.report(Errors.Type.ILLEGAL_OPERATOR, tokenList.get(0));}}
        catch (IndexOutOfBoundsException e){Errors.report(Errors.Type.PREMATURE_END);}
        return expressionNode;
    }

    /**
     * Helper function to check if string is an integer or not
     * @param el string to check if it's a digit or not
     * @return whether that string is a digit
     */
    public boolean isDigit(String el){
        try {Integer.parseInt(el); return true;}
        catch (NumberFormatException e){return false;}}

    /**
     * Displays the entire ARB program of ActionNode's to standard
     * output using emit().
     */
    public void displayProgram() {
        System.out.println("(ARB) infix...");
        for (ActionNode actionNode: actionNodeList){
            actionNode.emit();
            System.out.println();}
    }

    /**
     * Execute the ARB program of ActionNode's to standard output using execute().
     * In order to execute the ActionNodes, a local SymbolTable must be created here
     * for use.
     */
    public void interpretProgram() {
        System.out.println("(ARB) interpreting program...");
        SymbolTable symbolTable = new SymbolTable();
        for (ActionNode actionNode: actionNodeList){
            actionNode.execute(symbolTable);}
        System.out.println("(ARB) Symbol table:");
        System.out.print(symbolTable);
    }

    /**
     * Compile the ARB program using ActionNode's compile() into the
     * temporary MAQ instruction file.
     *
     * @throws IOException if there are issues working with the temp file
     */
    public void compileProgram() throws IOException {
        System.out.println("(ARB) compiling program to " + TMP_MAQ_FILE + "...");
        PrintWriter out = new PrintWriter(TMP_MAQ_FILE);
        for (ActionNode actionNode: actionNodeList){
            actionNode.compile(out);}
        out.close();
    }

    /**
     * Takes the generated MAQ instruction file and assembles/executes
     * it using the Maquina machine.
     *
     * @throws FileNotFoundException if the MAQ file cannot be found.
     */
    public void executeProgram() throws FileNotFoundException {
        Maquina maquina = new Maquina();
        maquina.assemble(new Scanner(new File(TMP_MAQ_FILE)), false);
        maquina.execute();
    }

    /**
     * The main program runs either with no input (ARB program entered through standard
     * input), or with a file name that represents the ARB program.
     *
     * @param args command line arguments
     * @throws IOException if there are issues working with the ARB/MAQ files.
     */
    public static void main(String[] args) throws IOException {
        // determine ARB input source
        Scanner arbIn = null;
        boolean stdin = false;
        if (args.length == 0) {
            arbIn = new Scanner(System.in);
            stdin = true;
        } else if (args.length == 1) {
            arbIn = new Scanner(new File(args[0]));
        } else {
            System.out.println("Usage: java Arboles filename.arb");
            System.exit(1);
        }

        // step 1: read ARB program into token list
        Arboles interpreter = new Arboles(arbIn, stdin);

        // step 2: parse and build the program from the token list
        interpreter.buildProgram();

        // step 3: display the program in infix
        interpreter.displayProgram();

        // step 4: interpret program
        interpreter.interpretProgram();

        // step 5: compile the program
        interpreter.compileProgram();

        // step 6: have machine execute compiled program
        interpreter.executeProgram();
    }
}
