package timnekk.services;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import timnekk.exceptions.GameFlowException;
import timnekk.exceptions.GettingQuestionException;
import timnekk.handlers.InputHandler;
import timnekk.handlers.OutputHandler;
import timnekk.models.Question;

public class App implements AutoCloseable {
    private static final Logger logger = LoggerFactory.getLogger(App.class);
    private final QuestionProvider questionProvider = new QuestionProvider();
    private final GameScore gameScore = new GameScore();
    private final InputHandler inputHandler;
    private final OutputHandler outputHandler;

    public App(InputHandler inputHandler, OutputHandler outputHandler) {
        this.inputHandler = inputHandler;
        this.outputHandler = outputHandler;
        logger.debug("App created");
    }

    public void run() throws GameFlowException {
        outputHandler.printLine("Welcome to the Quiz Hero!");
        outputHandler.printLine("Type /q to quit.");
        logger.info("App started");

        while (true) {
            Question question = getQuestion();

            outputHandler.printLine(System.lineSeparator() + "Question: " + question.getValue());
            outputHandler.printLine("Type your answer below:");

            String answer = getUserAnswer();

            outputHandler.printLine("");

            if (isExitCommand(answer)) {
                logger.debug("Exit command received");
                outputHandler.printLine("Your score: " + gameScore.getScore());
                logger.debug("Final score: {}", gameScore.getScore());
                break;
            }

            checkAnswer(question, answer);
        }
    }

    private void checkAnswer(Question question, String answer) {
        if (answer.equals(question.getAnswer())) {
            logger.debug("Correct answer");

            outputHandler.printLine("Correct!");
            outputHandler.printLine("Got " + question.getDifficulty() + " points!");

            gameScore.addScore(question.getDifficulty());
        } else {
            logger.debug("Wrong answer");

            outputHandler.printLine("Wrong!");
            outputHandler.printLine("Correct answer: " + question.getAnswer());
        }
    }

    private String getUserAnswer() throws GameFlowException {
        try {
            String answer = inputHandler.readLine();
            logger.info("Got answer: {}", answer);
            return answer;
        } catch (IOException e) {
            throw new GameFlowException("Error while reading answer", e);
        }
    }

    private Question getQuestion() throws GameFlowException {
        try {
            Question question = questionProvider.getQuestion();
            logger.info("Got question: {}", question);
            return question;
        } catch (GettingQuestionException e) {
            throw new GameFlowException("Error while getting question", e);
        }
    }

    private boolean isExitCommand(String command) {
        return command.equals("/q");
    }

    @Override
    public void close() {
        questionProvider.close();

        try {
            inputHandler.close();
        } catch (IOException e) {
            logger.error("Error while closing input handler", e);
        }
    }
}
