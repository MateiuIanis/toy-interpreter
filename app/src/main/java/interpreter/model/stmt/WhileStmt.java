
package interpreter.model.stmt;

import interpreter.exceptions.MyException;
import interpreter.exceptions.StatementException;
import interpreter.model.PrgState;
import interpreter.model.exp.IExp;
import interpreter.model.value.BoolValue;
import interpreter.model.value.IValue;

public record WhileStmt(IExp cond, IStmt body) implements IStmt {

    @Override
    public PrgState execute(PrgState state) throws MyException {
        IValue value = cond.eval(state.getSymTable(), state.getHeap());

        if (!(value instanceof BoolValue boolValue))
            throw new StatementException("while: condition is not a boolean");

        if (boolValue.val()) {
            state.getExeStack().push(this);
            state.getExeStack().push(body);
        }

        return state;
    }

    @Override
    public String toString() {
        return "(while (" + cond + ") " + body + ")";
    }
}
