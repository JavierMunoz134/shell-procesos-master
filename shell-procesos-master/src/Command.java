import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class Command {
    private String[] arguments;
    private String outputFile;

    public Command(String[] arguments, String outputFile) {
        this.arguments = arguments;
        this.outputFile = outputFile;
    }

    public Command(String commandString) {
        // Analizar la cadena de comandos y determinar los argumentos y el archivo de salida (si hay uno)
        // Esto es solo un ejemplo básico y puede necesitar una implementación más robusta
        String[] parts = commandString.split(">");
        String commandPart = parts[0].trim();
        String outputPart = (parts.length > 1) ? parts[1].trim() : "";

        this.arguments = commandPart.split("\\s+");
        this.outputFile = outputPart;
    }

    public String execute() {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(arguments);
            Process process = processBuilder.start();

            // Leer la salida del proceso
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            // Esperar a que el proceso termine y obtener el código de salida
            int exitValue = process.waitFor();

            // Mostrar información del proceso
            String result = "Command: " + String.join(" ", arguments) + "\n";
            result += "Number of Parameters: " + arguments.length + "\n";
            result += "Executed with PID: " + process.pid() + "\n";
            result += "Output:\n" + output.toString();

            if (!outputFile.isEmpty()) {
                // Redirigir la salida estándar al archivo
                // Esto es solo un ejemplo y puede necesitar una implementación más robusta
                // No se manejan excepciones aquí por simplicidad
                processBuilder.redirectOutput(ProcessBuilder.Redirect.to(new File(outputFile)));
                result += "Output redirected to file: " + outputFile + "\n";
            }

            result += "Process Exit Value: " + exitValue + "\n";
            return result;

        } catch (IOException | InterruptedException e) {
            return "Error executing command: " + e.getMessage();
        }
    }

    @Override
    public String toString() {
        return "Command{" +
                "arguments=" + String.join(" ", arguments) +
                ", outputFile='" + outputFile + '\'' +
                '}';
    }
}
