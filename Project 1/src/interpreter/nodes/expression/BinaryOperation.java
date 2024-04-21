/**
 * @author Nam Huynh (nsh1507)
 */

package interpreter.nodes.expression;

import common.Errors;
import common.SymbolTable;

import java.io.PrintWriter;
import java.util.List;

public class BinaryOperation implements ExpressionNode{
     /** ARB addition operator */
    public final static String ADD = "+";

    /** ARB division operator */
    public final static String DIV = "/";

    /** ARB modulus operator */
    public final static String MOD = "%";

    /** ARB multiply operator */
    public final static String MUL = "*";

    /** ARB subtraction operator */
    public final static String SUB = "-";

    /** The operator */
    private String operator;

    /** The left child expression */
    private ExpressionNode leftChild;

    /** The right child expression */
    private ExpressionNode right;

    /** The legal binary operators, for use when parsing*/
    public static final List< String > OPERATORS = List.of(
            ADD,
            DIV,
            MOD,
            MUL,
            SUB
    );

    /**
     * Create a new BinaryOperation node.
     * @param operator the operator
     * @param leftChild the left child expression
     * @param right the right child expression
     */
    public BinaryOperation(String operator, ExpressionNode leftChild, ExpressionNode right){
        this.leftChild = leftChild;
        this.right = right;
        this.operator = operator;
    }

    /**
     * Print to standard output the infix display of
     * the two child nodes separated by the operator
     * and surrounded by parentheses.
     */
    @Override
    public void emit() {
        System.out.print("( ");
        leftChild.emit();
        switch (operator) {
            case "+" -> System.out.print(" + ");
            case "-" -> System.out.print(" - ");
            case "*" -> System.out.print(" * ");
            case "/" -> System.out.print(" / ");
            case "%" -> System.out.print(" % ");
        }
        right.emit();
        System.out.print(" )");
    }

    /**
     * Generates the MAQ instructions for this operation.
     * @param out the stream to write output to using out.println()
     */
    @Override
    public void compile(PrintWriter out) {
        leftChild.compile(out);
        right.compile(out);
        switch (operator) {
            case "+" -> out.println("ADD");
            case "-" -> out.println("SUB");
            case "*" -> out.println("MUL");
            case "/" -> out.println("DIV");
            case "%" -> out.println("MOD");
        }
    }

    /**
     * Compute the result of evaluating the child expression and applying the operator to it.
     * @param symTbl the symbol table, if needed, to fetch the variable values.
     * @return the result of the computation
     */
    @Override
    public int evaluate(SymbolTable symTbl) {
        switch (operator) {
            case "+" -> {
                int add1 = this.leftChild.evaluate(symTbl);
                int add2 = this.right.evaluate(symTbl);
                return add1 + add2;
            }
            case "-" -> {
                int add1 = this.leftChild.evaluate(symTbl);
                int add2 = this.right.evaluate(symTbl);
                return add1 - add2;
            }
            case "*" -> {
                int add1 = this.leftChild.evaluate(symTbl);
                int add2 = this.right.evaluate(symTbl);
                return add1 * add2;
            }
            case "/" -> {
                int add1 = this.leftChild.evaluate(symTbl);
                int add2 = this.right.evaluate(symTbl);
                if (add2 == 0){
                    Errors.report(Errors.Type.DIVIDE_BY_ZERO);
                }
                return add1 / add2;
            }
            case "%" -> {
                int add1 = this.leftChild.evaluate(symTbl);
                int add2 = this.right.evaluate(symTbl);
                return add1 % add2;
            }
        }
        return 0;
    }
}
