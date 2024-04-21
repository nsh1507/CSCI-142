/**
 * @author Nam Huynh (nsh1507)
 */

package interpreter.nodes.expression;

import common.SymbolTable;

import java.io.PrintWriter;

public class Constant implements ExpressionNode{
    /** The constant for which the value will be stored to */
    private int CONSTANT;

    /**
     * Create a new constant.
     * @param value the value
     */
    public Constant(int value){
        this.CONSTANT = value;
    }

    /**
     * Print the stored value to standard output.
     */
    @Override
    public void emit() {
        System.out.print(this.CONSTANT);
    }

    /**
     * Generates the MAQ instruction for pushing the value.
     * @param out the stream to write output to using out.println()
     */
    @Override
    public void compile(PrintWriter out) {
        out.println("PUSH " + this.CONSTANT);
    }

    /**
     * Return the stored value when evaluated.
     * @param symTbl the symbol table, if needed, to fetch the variable values.
     * @return the value
     */
    @Override
    public int evaluate(SymbolTable symTbl) {
        return this.CONSTANT;
    }
}
