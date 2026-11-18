
package interpreter;

import interpreter.model.PrgState;
import interpreter.model.stmt.IStmt;
import interpreter.repository.Repository;
import interpreter.controller.Controller;
import interpreter.view.*;

public class Main {
    public static void main(String[] args) {
        TextMenu menu = new TextMenu();

        menu.addCommand(new ExitCommand("0", "exit"));

        IStmt[] programs = Examples.getExamples();

        for (int i = 0; i < programs.length; i++) {
            IStmt program = programs[i];

            Repository repo = new Repository(
                    PrgState.of(program),
                    "log" + (i + 1) + ".txt"
            );

            Controller controller = new Controller(repo);
            controller.setDisplayFlag(true);

            menu.addCommand(new RunExampleCommand(
                    String.valueOf(i + 1),
                    program.toString(),
                    controller
            ));
        }

        menu.show();
    }
}
