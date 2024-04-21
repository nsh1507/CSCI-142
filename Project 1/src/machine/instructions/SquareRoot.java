package machine.instructions;

import common.Errors;
import machine.InstructionStack;
import machine.Maquina;
import java.lang.Math;

public class SquareRoot implements Instruction{
    /**
     * The instruction stack from maquina
     */
    private InstructionStack stack;

    /**
     * A constructor that takes in the Maquina to get the instruction stack
     * @param machine the Maquina
     */
    public SquareRoot(Maquina machine){
        this.stack = machine.getInstructionStack();
    }

    /**
     * Pops the operand off the stack, and pushes the
     * integer result of taking the square root of it.
     */
    @Override
    public void execute() {
        int popped = stack.pop();
        if (popped < 0){
            Errors.report(Errors.Type.NEGATIVE_SQUARE_ROOT);
        }
        stack.push((int) Math.sqrt(popped));
    }

    /**
     * Show the instruction using text so that it can be understood by a person.
     * @return a short string describing what this instruction will do
     */
    @Override
    public String toString() {
        return Maquina.SQUARE_ROOT;
    }
}
