package timnekk.services;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

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

    private URI singleRandomQuestionUrl;

    public QuestionProvider() throws CreatingQuestionProviderException {
        try {
            buildSingleRandomQuestionUrl();
        } catch (URISyntaxException e) {
            throw new CreatingQuestionProviderException("Error while building single random question url", e);
        }
    }

    private void buildSingleRandomQuestionUrl() throws URISyntaxException {
        URIBuilder builder = new URIBuilder()
                .setScheme(SCHEME)
                .setHost(HOST)
                .setPathSegments(API_PATH, RANDOM_PATH)
                .addParameter(COUNT_PARAM, "1");
        singleRandomQuestionUrl = builder.build();
    }

    public Question getQuestion() throws GettingQuestionException {
        HttpGet request = new HttpGet(singleRandomQuestionUrl);

        try (CloseableHttpResponse response = client.execute(request)) {
            String responseContent = EntityUtils.toString(response.getEntity());
            Question[] questions = mapper.readValue(responseContent, Question[].class);

            if (questions.length == 0) {
                throw new GettingQuestionException("No questions found");
            }

            return questions[0];
        } catch (IOException e) {
            throw new GettingQuestionException("Error while getting question", e);
        }
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