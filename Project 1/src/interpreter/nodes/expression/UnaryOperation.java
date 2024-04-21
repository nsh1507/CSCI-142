/**
 * @author Nam Huynh (nsh1507)
 */

package interpreter.nodes.expression;

import common.Errors;
import common.SymbolTable;

import java.io.PrintWriter;
import java.util.List;
import java.lang.Math.*;

public class UnaryOperation implements ExpressionNode {
    /** ARB negation operator */
    public final static String NEG = "!";

    /** ARB square root operator */
    public final static String SQRT = "$";

    /** The operator */
    private String operator;

    /** The ExpressionNode child */
    private ExpressionNode child;

    /**
     * the legal unary operators, for use when parsing
     */
    public static final List< String > OPERATORS = List.of(NEG, SQRT);

    /**
     * Create a new UnaryOperation node.
     * @param operator the operator
     * @param child the child expression
     */
    public UnaryOperation(String operator, ExpressionNode child){
        this.operator = operator;
        this.child = child;
    }

    /**
     * Print to standard output the infix display of
     * the child nodes preceded by the operator and
     * without an intervening blank.
     */
    @Override
    public void emit() {
        System.out.print(operator);
        child.emit();
    }

    /**
     * Generates the MAQ instructions for this operation.
     * @param out the stream to write output to using out.println()
     */
    @Override
    public void compile(PrintWriter out) {
        child.compile(out);
        switch (operator) {
            case "!" -> out.println("NEG");
            case "$" -> out.println("SQRT");
        }
    }

    /**
     * Compute the result of evaluating the expression and applying the operator to it.
     * @param symTbl the symbol table, if needed, to fetch the variable values.
     * @return the result of the computation
     */
    @Override
    public int evaluate(SymbolTable symTbl) {
        switch (operator) {
            case "!" -> {
                int neg = this.child.evaluate(symTbl);
                neg *= -1;
                return neg;
            }
            case "$" -> {
                int sqrt = this.child.evaluate(symTbl);
                if (sqrt < 0){
                    Errors.report(Errors.Type.NEGATIVE_SQUARE_ROOT);
                }
                sqrt = (int) Math.sqrt(sqrt);
                return sqrt;
            }
        }
        return 0;
    }
}
