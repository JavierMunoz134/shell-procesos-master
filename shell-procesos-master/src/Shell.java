import java.util.Scanner;

public class Shell {
    private static Command lastCommand = null;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine();

            if (input.equals("exit")) {
                break;
            } else if (input.equals("last-command")) {
                if (lastCommand != null) {
                    System.out.println(lastCommand);
                } else {
                    System.out.println("No commands have been executed yet.");
                }
            } else {
                Command command = new Command(input);
                String output = command.execute();
                System.out.println(output);

                // Guardar el último comando ejecutado
                lastCommand = command;
            }
        }

        scanner.close();
    }
}
