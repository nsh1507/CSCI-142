package machine.instructions;

import common.SymbolTable;
import machine.InstructionStack;
import machine.Maquina;

/**
 * @author Nam Huynh
 */

public class Store implements Instruction{
    /**
     * The name of variable
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
     * Constructor that takes in the variable's name and the Maquina
     * @param name name of variable
     * @param machine the Maquina
     */

    public Store(String name, Maquina machine){
        this.stack = machine.getInstructionStack();
        this.symbolTable = machine.getSymbolTable();
        this.name = name;
    }

    /**
     * Pops the value off the top of stack and sets the
     * variable's value in the symbol table to the value.
     */
    @Override
    public void execute() {
        int el = stack.pop();
        symbolTable.set(name,el);
    }

    /**
     * Show the instruction using text so that it can be understood by a person.
     * @return a short string describing what this instruction will do
     */
    @Override
    public String toString(){
        return Maquina.STORE + " " + name;
    }
}
