package machine.instructions;

import common.Errors;
import common.SymbolTable;
import machine.InstructionStack;
import machine.Maquina;

/**
* @author Nam Huynh
*/
public class Load implements Instruction{
    /**
     * The name of the variable
     */
    private String name;
    /**
     * The instruction stack from maquina
     */
    private InstructionStack stack;
    /**
     * The symbol table from maquina
     */
    private SymbolTable symbolTable;

    /**
     * A constructor that takes in the Maquina, variable name
     * @param name the variable name
     * @param machine the Maquina
     */
    public Load(String name, Maquina machine){
        this.name = name;
        this.stack = machine.getInstructionStack();
        this.symbolTable = machine.getSymbolTable();
    }

    /**
     * Load the variables value from the symbol
     * table and push it onto the stack.
     */
    @Override
    public void execute() {
        if (!symbolTable.has(name)){
            Errors.report(Errors.Type.UNINITIALIZED);
        }
        stack.push(symbolTable.get(name));
    }

    /**
     * Show the instruction using text so that it can be understood by a person.
     * @return a short string describing what this instruction will do
     */
    @Override
    public String toString(){
        return Maquina.LOAD + " " + name ;
    }
}
