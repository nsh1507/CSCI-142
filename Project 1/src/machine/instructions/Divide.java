package machine.instructions;

import common.Errors;
import machine.InstructionStack;
import machine.Maquina;

/**
 * @author Nam Huynh
 */
public class Divide implements Instruction{
    /**
     * The instruction stack from maquina
     */
    private InstructionStack stack;

    /**
     * A constructor that takes in the Maquina to get the instruction stack
     * @param machine the Maquina
     */
    public Divide(Maquina machine){
        this.stack = machine.getInstructionStack();
    }

    /**
     * Pops the second and then first operands off the stack,
     * and pushes the result of the first divided by the second.
     */
    @Override
    public void execute() {
        int popped1 = stack.pop();
        int popped2 = stack.pop();
        if (popped1 == 0){
            Errors.report(Errors.Type.DIVIDE_BY_ZERO);}
        stack.push((popped2/popped1));
    }

    /**
     * Show the instruction using text so that
     * it can be understood by a person.
     * @return a string representation
     */
    @Override
    public String toString() {
        return Maquina.DIVIDE;
    }
}
