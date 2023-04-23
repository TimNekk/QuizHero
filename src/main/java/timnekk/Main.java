package timnekk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.ParameterException;
import picocli.CommandLine.Help;

import timnekk.exceptions.GameFlowException;
import timnekk.handlers.InputHandler;
import timnekk.handlers.OutputHandler;
import timnekk.services.App;

@Command(name = "quizhero", mixinStandardHelpOptions = true, description = "Quiz Hero", requiredOptionMarker = '*', abbreviateSynopsis = true)
public final class Main implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    @Option(names = { "-Q",
            "--questionsPerRequest" }, description = "Amount of questions that will be fetched with single request", defaultValue = "10", showDefaultValue = Help.Visibility.ALWAYS)
    private int questionsPerRequest;

    @Override
    public void run() {
        try (App app = new App(new InputHandler(System.in), new OutputHandler(System.out), questionsPerRequest)) {
            app.run();
        } catch (GameFlowException e) {
            logger.error("Error while running app", e);
        } catch (IllegalArgumentException e) {
            logger.error("Wrong arguments: {}", e.getMessage());
        }
    }

    public static void main(String[] args) {
        final CommandLine cmd = new CommandLine(new Main());

        try {
            cmd.parseArgs(args);
            cmd.execute(args);
        } catch (ParameterException e) {
            logger.error("Parsing command line arguments failed: {}", e.getMessage());
        }
    }
}