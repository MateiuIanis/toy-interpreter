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

import static interpreter.view.Examples.getExamples;

public class View {

    private final Scanner scanner = new Scanner(System.in);


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

                PrgState state = PrgState.of(examples[index - 1]);

                Controller controller = new Controller(new Repository(state, "log.txt"));

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