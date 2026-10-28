package interpreter.repository;

import interpreter.model.PrgState;

import java.util.List;

public class Repository implements IRepository {
    private final List<PrgState> prgList;

    public Repository(PrgState program) {
        this.prgList = List.of(program);
    }

    @Override
    public PrgState getCrtPrg() {
        return prgList.get(0);
    }
}