package timnekk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import timnekk.exceptions.GameFlowException;
import timnekk.handlers.InputHandler;
import timnekk.handlers.OutputHandler;
import timnekk.services.App;

public final class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        try (App app = new App(new InputHandler(System.in), new OutputHandler(System.out))) {
            app.run();
        } catch (GameFlowException e) {
            logger.error("Error while running app", e);
        }

        logger.info("App finished");
    }
}