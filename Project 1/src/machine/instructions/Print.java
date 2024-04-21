package machine.instructions;

import machine.InstructionStack;
import machine.Maquina;

/**
 * @author Nam Huynh
 */
public class Print implements Instruction{
    /**
     * The instruction stack from maquina
     */
    private final InstructionStack stack;

    /**
     * A constructor that takes in the Maquina to get the instruction stack
     * @param machine the Maquina
     */
    public Print(Maquina machine){
        this.stack = machine.getInstructionStack();
    }

    /**
     * Pops the top operand off the stack and prints the resulting value.
     */
    @Override
    public void execute() {
        System.out.println(this.stack.pop());
    }

    /**
     * Show the instruction using text so that it can be understood by a person.
     * @return a short string describing what this instruction will do
     */
    @Override
    public String toString(){
        return Maquina.PRINT;
    }
}
