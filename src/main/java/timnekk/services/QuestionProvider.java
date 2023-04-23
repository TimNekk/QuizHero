package timnekk.services;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

import timnekk.exceptions.CreatingQuestionProviderException;
import timnekk.exceptions.GettingQuestionException;
import timnekk.models.Question;

public final class QuestionProvider implements AutoCloseable {
    private static final Logger logger = LoggerFactory.getLogger(QuestionProvider.class);
    private static final CloseableHttpClient client = HttpClients.createDefault();
    private static final ObjectMapper mapper = new ObjectMapper();

    private static final String SCHEME = "http";
    private static final String HOST = "jservice.io";
    private static final String API_PATH = "api";
    private static final String RANDOM_PATH = "random";
    private static final String COUNT_PARAM = "count";

    private static final int MAX_QUESTIONS_PER_REQUEST = 100;
    private URI randomQuestionWithCountUrl;
    private final Queue<Question> questionPool = new LinkedList<>();

    public QuestionProvider(int questionsPerRequest)
            throws CreatingQuestionProviderException, IllegalArgumentException {
        validateQuestionsPerRequest(questionsPerRequest);
        buildSingleRandomQuestionUrl(questionsPerRequest);
    }

    private void validateQuestionsPerRequest(int count) throws IllegalArgumentException {
        if (count <= 0 || count > MAX_QUESTIONS_PER_REQUEST) {
            throw new IllegalArgumentException(
                    "Questions per request value can be from 0 to " + MAX_QUESTIONS_PER_REQUEST);
        }
    }

    private void buildSingleRandomQuestionUrl(Integer questionsPerRequest) throws CreatingQuestionProviderException {
        URIBuilder builder = new URIBuilder()
                .setScheme(SCHEME)
                .setHost(HOST)
                .setPathSegments(API_PATH, RANDOM_PATH)
                .addParameter(COUNT_PARAM, questionsPerRequest.toString());

        try {
            randomQuestionWithCountUrl = builder.build();
        } catch (URISyntaxException e) {
            throw new CreatingQuestionProviderException("Error while building single random question url", e);
        }
    }

    public Question getQuestion() throws GettingQuestionException {
        if (!questionPool.isEmpty()) {
            logger.debug("Question pool is not empty, returning question from pool");
            return questionPool.poll();
        }

        HttpGet request = new HttpGet(randomQuestionWithCountUrl);
        Question[] questions;

        try (CloseableHttpResponse response = client.execute(request)) {
            String responseContent = EntityUtils.toString(response.getEntity());
            questions = mapper.readValue(responseContent, Question[].class);
        } catch (IOException e) {
            throw new GettingQuestionException("Error while getting question", e);
        }

        if (questions.length == 0) {
            throw new GettingQuestionException("No questions found");
        }

        questionPool.addAll(Arrays.asList(questions));
        logger.debug("Question pool filled with {} questions", questions.length);
        return questionPool.poll();
    }

    @Override
    public void close() {
        try {
            client.close();
        } catch (IOException e) {
            logger.error("Error while closing http client", e);
        }
    }
}