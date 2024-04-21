package machine.instructions;

import machine.InstructionStack;
import machine.Maquina;

public class Multiply implements Instruction{
    private InstructionStack stack;
    public Multiply(Maquina machine){
        this.stack = machine.getInstructionStack();
    }
    @Override
    public void execute() {
        int popped1 = stack.pop();
        int popped2 = stack.pop();
        int result = popped1 * popped2;
        stack.push(result);
    }
    @Override
    public String toString(){
        return Maquina.MULTIPLY;
    }
}
