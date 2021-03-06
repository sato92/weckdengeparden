package com.gepardec.wdg.challenge;

import com.gepardec.wdg.challenge.model.Answer;
import com.gepardec.wdg.challenge.model.Challenge;
import com.gepardec.wdg.challenge.model.Challenges;
import com.gepardec.wdg.client.personio.Source;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@QuarkusTest
class ChallengeResourceTest {

    @Test
    void list_withPOST_then404Returned() {
        given().post("/challenge")
                .then()
                .statusCode(HttpStatus.SC_METHOD_NOT_ALLOWED);
    }

    @Test
    void list_withGET_thenChallengesReturned() {
        final List<Challenge> expectedChallenges = Stream.of(Challenges.values())
                .sorted(Comparator.comparing(Challenges::getId))
                .map((challenges) -> Challenge.of(challenges.getId(), challenges.getQuestion()))
                .collect(Collectors.toList());

        given().get("/challenge")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .assertThat().body(equalTo(toJson(expectedChallenges)));
    }

    @Test
    void byId_withGETAndInvalidId_then400Returned() {
        given().get("/challenge/0")
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    void byId_withGETAndValidId_thenChallengeReturned() {
        final Challenges challenges = Challenges.forId(1).get();
        final Challenge expectedChallenge = Challenge.of(challenges.getId(), challenges.getQuestion());
        given().get("/challenge/1")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .assertThat().body(equalTo(toJson(expectedChallenge)));
    }

    @Test
    void answer_withInvalidContentTypeTEXT_then400Returned() {
        given().contentType(ContentType.TEXT)
                .post("/challenge/-1/answer")
                .then().statusCode(HttpStatus.SC_UNSUPPORTED_MEDIA_TYPE);
    }

    @Test
    void answer_withInvalidId_then400Returned() {
        given().contentType(ContentType.JSON)
                .post("/challenge/-1/answer")
                .then().statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("success", equalTo(false))
                .body("message", equalTo("The request was invalid due to constraint violations"));
    }

    @Test
    void answer_withEmptyBody_then400Returned() {
        given().contentType(ContentType.JSON)
                .post("/challenge/1/answer")
                .then().statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("success", equalTo(false))
                .body("message", equalTo("The request was invalid due to constraint violations"));
    }

    @Test
    void answer_withInvalidBody_then400Returned() {
        final Answer answer = buildValidAnswer(Challenges.CHALLENGE1.getId());
        answer.setEmail(null);
        given().contentType(ContentType.JSON)
                .body(answer)
                .post("/challenge/1/answer")
                .then().statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("success", equalTo(false))
                .body("message", equalTo("The request was invalid due to constraint violations"));
    }

    @Test
    void answer_withInvalidAnswer_then400Returned() {
        final Answer answer = buildValidAnswer(Challenges.CHALLENGE1.getId());
        answer.setAnswer("Invalid answer");
        given().contentType(ContentType.JSON)
                .body(answer)
                .post("/challenge/1/answer")
                .then().statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("success", equalTo(false))
                .body("message", equalTo("Sorry, die Antwort ist falsch. Denk' nochmal in Ruhe darüber nach und versuch es noch einmal."));
    }

    @Test
    void answer_withValidAnswer_then200Returned() {
        final Answer answer = buildValidAnswer(Challenges.CHALLENGE1.getId());
        answer.setSource(Source.SONSTIGES);
        answer.setOtherSource("My friend");
        given().contentType(ContentType.JSON)
                .body(answer)
                .post("/challenge/1/answer")
                .then().statusCode(HttpStatus.SC_OK)
                .body("success", equalTo(true))
                .body("message", equalTo("Danke! Du hast den Geparden in dir erweckt und wir melden uns in den nächsten Tagen bei dir! Lg, Michael Sollberger"));
    }

    private Answer buildValidAnswer(final int challengeId) {
        final Challenges challenges = Challenges.forId(challengeId)
                .orElseThrow(() -> new IllegalArgumentException(String.format("No challenge with id '%d' found", challengeId)));
        final Answer answer = new Answer();
        answer.setJobId("1");
        answer.setFirstName("Thomas");
        answer.setLastName("Herzog");
        answer.setEmail("thomas.herzog@gepardec.om");
        answer.setAnswer(challenges.getAnswer());

        return answer;
    }

    private String toJson(final Object value) {
        try (final Jsonb jsonb = JsonbBuilder.create()) {
            return jsonb.toJson(value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
