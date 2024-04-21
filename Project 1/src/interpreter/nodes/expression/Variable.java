/**
 * @author Nam Huynh (nsh1507)
 */

package interpreter.nodes.expression;

import common.Errors;
import common.SymbolTable;
import machine.Maquina;

import java.io.PrintWriter;

public class Variable implements ExpressionNode{
    /** Name of the variable */
    private String name;

    /**
     * Create a new Variable for the identifier.
     * @param name the name of this variable
     */
    public Variable(String name){
        this.name = name;
    }

    /**
     * Print to standard output the name of the Variable.
     */
    @Override
    public void emit() {
        System.out.print(name);
    }

    /**
     * Generates the MAQ instruction for loading the variable name
     * @param out the stream to write output to using out.println()
     */
    @Override
    public void compile(PrintWriter out) {
        out.println("LOAD " + name);
    }

    /**
     * Get the value of the variable name from the symbol table.
     * @param symTbl the symbol table, if needed, to fetch the variable values.
     * @return this variable's current value in the symbol table
     */
    @Override
    public int evaluate(SymbolTable symTbl) {
        if (!symTbl.has(name)){
            Errors.report(Errors.Type.UNINITIALIZED, name);
        }
        return symTbl.get(name);
    }
}
