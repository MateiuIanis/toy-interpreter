package interpreter.view;

import interpreter.controller.Controller;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.exp.*;
import interpreter.model.stmt.*;
import interpreter.model.type.*;
import interpreter.model.value.*;
import interpreter.repository.Repository;

import java.util.Scanner;

public class View {

    private final Scanner scanner = new Scanner(System.in);

    private IStmt[] getExamples() {
        IStmt ex1 = new CompStmt(
                new VarDeclStmt("v", new IntType()),
                new CompStmt(
                        new AssignStmt("v", new ValueExp(new IntValue(2))),
                        new PrintStmt(new VarExp("v"))
                )
        );

        IStmt ex2 = new CompStmt(
                new VarDeclStmt("a", new IntType()),
                new CompStmt(
                        new VarDeclStmt("b", new IntType()),
                        new CompStmt(
                                new AssignStmt(
                                        "a",
                                        new ArithExp(
                                                ArithOp.ADD,
                                                new ValueExp(new IntValue(2)),
                                                new ArithExp(
                                                        ArithOp.MUL,
                                                        new ValueExp(new IntValue(3)),
                                                        new ValueExp(new IntValue(5))
                                                )
                                        )
                                ),
                                new CompStmt(
                                        new AssignStmt(
                                                "b",
                                                new ArithExp(
                                                        ArithOp.ADD,
                                                        new VarExp("a"),
                                                        new ValueExp(new IntValue(1))
                                                )
                                        ),
                                        new PrintStmt(new VarExp("b"))
                                )
                        )
                )
        );

        IStmt ex3 = new CompStmt(
                new VarDeclStmt("a", new BoolType()),
                new CompStmt(
                        new VarDeclStmt("v", new IntType()),
                        new CompStmt(
                                new AssignStmt(
                                        "a",
                                        new ValueExp(new BoolValue(true))
                                ),
                                new CompStmt(
                                        new IfStmt(
                                                new VarExp("a"),
                                                new AssignStmt(
                                                        "v",
                                                        new ValueExp(new IntValue(2))
                                                ),
                                                new AssignStmt(
                                                        "v",
                                                        new ValueExp(new IntValue(3))
                                                )
                                        ),
                                        new PrintStmt(new VarExp("v"))
                                )
                        )
                )
        );

        return new IStmt[]{ex1, ex2, ex3};
    }

    public void run() {
        IStmt[] examples = getExamples();

        while (true) {
            System.out.println("\n=== Toy Interpreter ===");
            System.out.println("1. int v; v = 2; print(v)");
            System.out.println("2. int a; int b; a = 2 + 3 * 5; b = a + 1; print(b)");
            System.out.println("3. bool a; int v; a = true; if a then v = 2 else v = 3; print(v)");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");

            String option = scanner.nextLine();

            if (option.equals("0")) {
                break;
            }

            try {
                int index = Integer.parseInt(option);

                if (index < 1 || index > examples.length) {
                    System.out.println("Invalid option.");
                    continue;
                }

                PrgState state = new PrgState(
                        new MyStack<>(),
                        new MyDictionary<>(),
                        new MyList<>(),
                        examples[index - 1]
                );

                Controller controller = new Controller(
                        new Repository(state)
                );

                controller.setDisplayFlag(true);
                controller.allStep();

            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            } catch (Exception e) {
                System.out.println("Execution error: " + e.getMessage());
            }
        }
    }
}