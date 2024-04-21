package machine.instructions;

import machine.InstructionStack;
import machine.Maquina;

/**
 * @author Nam Huynh
 */

public class Subtract implements Instruction{
    /**
     * The instruction stack from maquina
     */
    private InstructionStack stack;

    /**
     * A constructor that takes in the Maquina to get the instruction stack
     * @param machine the Maquina
     */
    public Subtract(Maquina machine){
        this.stack = machine.getInstructionStack();
    }

    /**
     * Pops the second and then first operands off the stack,
     * and pushes the result of the first subtracted by the second.
     */
    @Override
    public void execute() {
        int popped1 = stack.pop();
        int popped2 = stack.pop();
        int result = popped2 -  popped1;
        stack.push(result);
    }

    /**
     * Show the instruction using text so that it can be understood by a person.
     * @return a short string describing what this instruction will do
     */
    @Override
    public String toString() {
        return Maquina.SUBTRACT;
    }
}
