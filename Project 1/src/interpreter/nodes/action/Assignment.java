/**
 * @author Nam Huynh (nsh1507)
 */

package interpreter.nodes.action;

import common.SymbolTable;
import interpreter.nodes.expression.ExpressionNode;

import java.io.PrintWriter;

public class Assignment implements ActionNode{
    /** Name of the variable */
    private String name;
    /** The child expression */
    private ExpressionNode child;

    /**
     * Create a new Assignment node with an identifier name and child expression.
     * @param name the name of the variable that is getting a new value
     * @param child the expression on the right-hand-side (RHS) of the assignment statement.
     */
    public Assignment(String name, ExpressionNode child){
        this.name = name;
        this.child = child;
    }

    /**
     * Print to standard output the assignment with the
     * variable name, followed by the assignment token,
     * and followed by the infix form of the child expression.
     */
    @Override
    public void emit() {
        System.out.print(name + " = ");
        child.emit();
    }

    /**
     * Generates the MAQ instructions that when instructed will perform the assignment.
     * @param out the stream to write output to using out.println()
     */
    @Override
    public void compile(PrintWriter out) {
        child.compile(out);
        out.println("STORE " + name);
    }

    /**
     * Evaluate the child expression and assign the result to the variable.
     * @param symTbl the table where variable values are stored
     */
    @Override
    public void execute(SymbolTable symTbl) {
        symTbl.set(name, child.evaluate(symTbl));
    }
}
