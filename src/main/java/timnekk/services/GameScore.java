package timnekk.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameScore {
    private static final Logger logger = LoggerFactory.getLogger(GameScore.class);
    private int score;

    public GameScore() {
        score = 0;
    }

    public void addScore(int score) {
        logger.debug("Adding score: {}", score);
        this.score += score;
    }

    public int getScore() {
        return score;
    }
}