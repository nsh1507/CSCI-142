package machine.instructions;

import machine.InstructionStack;
import machine.Maquina;

/**
 * @author Nam Huynh
 */

public class Negate implements Instruction{
    /**
     * The instruction stack from maquina
     */
    private InstructionStack stack;

    /**
     * A constructor that takes in the Maquina to get the instruction stack
     * @param machine the Maquina
     */
    public Negate(Maquina machine){
        this.stack = machine.getInstructionStack();
    }

    /**
     * Pops the operand off the stack, and pushes the result of negating it.
     */
    @Override
    public void execute() {
        int popped = stack.pop();
        popped *= -1;
        stack.push(popped);
    }

    /**
     * Show the instruction using text so that it can be understood by a person.
     * @return a short string describing what this instruction will do
     */
    @Override
    public String toString(){
        return Maquina.NEGATE;
    }
}
