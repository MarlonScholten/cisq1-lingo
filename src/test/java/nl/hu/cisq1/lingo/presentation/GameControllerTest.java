package nl.hu.cisq1.lingo.presentation;

import com.jayway.jsonpath.JsonPath;
import nl.hu.cisq1.lingo.CiTestConfiguration;
import nl.hu.cisq1.lingo.Utils;
import nl.hu.cisq1.lingo.application.GameService;
import nl.hu.cisq1.lingo.data.LingoGameDM;
import nl.hu.cisq1.lingo.words.application.WordService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@Import(CiTestConfiguration.class)
@AutoConfigureMockMvc
class GameControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private GameService gameService;

	@Autowired
	private WordService wordService;

	@Test
	@DisplayName("start a new game")
	void startNewGame() throws Exception {
		RequestBuilder newGameRequest = MockMvcRequestBuilders
				.post("/game/new")
				.contentType(MediaType.APPLICATION_JSON);

		mockMvc.perform(newGameRequest)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.roundNumber", is(1)))
				.andExpect(jsonPath("$.status", is("PLAYING")))
				.andExpect(jsonPath("$.score", is(0)))
				.andExpect(jsonPath("$.feedbackHistory", hasSize(0)))
				.andExpect(jsonPath("$.hint", hasLength(5)));
	}

	@Test
	@DisplayName("do a guess after starting a game")
	void doGuess() throws Exception {
		RequestBuilder newGameRequest = MockMvcRequestBuilders
				.post("/game/new")
				.contentType(MediaType.APPLICATION_JSON);

		MockHttpServletResponse response = mockMvc.perform(newGameRequest).andReturn().getResponse();
		Integer gameId = JsonPath.read(response.getContentAsString(), "$.id");

		LingoGameDM game = gameService.getGameById(Long.valueOf(gameId));
		String attempt = Utils.generateTestGuess(wordService,game.getLingoGame().getCurrentRound().getWordToGuess());

		RequestBuilder attemptRequest = MockMvcRequestBuilders
				.post("/game/"+ gameId + "/guess/"+ attempt)
				.contentType(MediaType.APPLICATION_JSON);

		mockMvc.perform(attemptRequest)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", greaterThanOrEqualTo(0)))
				.andExpect(jsonPath("$.status", is("PLAYING")))
				.andExpect(jsonPath("$.feedbackHistory", hasSize(1)));
	}

	@Test
	@DisplayName("do an attempt where the guess is not a valid word")
	void doGuessWithInvalidWord() throws Exception {
		RequestBuilder newGameRequest = MockMvcRequestBuilders
				.post("/game/new")
				.contentType(MediaType.APPLICATION_JSON);

		MockHttpServletResponse response = mockMvc.perform(newGameRequest).andReturn().getResponse();
		Integer gameId = JsonPath.read(response.getContentAsString(), "$.id");

		String attempt = "aaaaa";

		RequestBuilder attemptRequest = MockMvcRequestBuilders
				.post("/game/"+ gameId + "/guess/"+ attempt)
				.contentType(MediaType.APPLICATION_JSON);

		mockMvc.perform(attemptRequest)
				.andExpect(status().isBadRequest());
	}

	@Test
	@DisplayName("win a round")
	void winRound() throws Exception {
		RequestBuilder newGameRequest = MockMvcRequestBuilders
				.post("/game/new")
				.contentType(MediaType.APPLICATION_JSON);

		MockHttpServletResponse response = mockMvc.perform(newGameRequest).andReturn().getResponse();
		Integer gameId = JsonPath.read(response.getContentAsString(), "$.id");

		LingoGameDM game = gameService.getGameById(Long.valueOf(gameId));
		String attempt = game.getLingoGame().getCurrentRound().getWordToGuess();

		RequestBuilder attemptRequest = MockMvcRequestBuilders
				.post("/game/"+ gameId + "/guess/"+ attempt)// win the round
				.contentType(MediaType.APPLICATION_JSON);

		mockMvc.perform(attemptRequest)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", greaterThanOrEqualTo(0)))
				.andExpect(jsonPath("$.status", is("WON")))
				.andExpect(jsonPath("$.score", is(25)))
				.andExpect(jsonPath("$.feedbackHistory", hasSize(1)));
	}

	@Test
	@DisplayName("win a round and start a new one")
	void winRoundAndStartNextRound() throws Exception {
		RequestBuilder newGameRequest = MockMvcRequestBuilders
				.post("/game/new")
				.contentType(MediaType.APPLICATION_JSON);

		MockHttpServletResponse response = mockMvc.perform(newGameRequest).andReturn().getResponse();
		Integer gameId = JsonPath.read(response.getContentAsString(), "$.id");

		LingoGameDM game = gameService.getGameById(Long.valueOf(gameId));
		String attempt = game.getLingoGame().getCurrentRound().getWordToGuess();

		RequestBuilder attemptRequest = MockMvcRequestBuilders
				.post("/game/"+ gameId + "/guess/"+ attempt)// win the round
				.contentType(MediaType.APPLICATION_JSON);

		RequestBuilder nextRoundRequest = MockMvcRequestBuilders
				.post("/game/"+ gameId + "/round/next")
				.contentType(MediaType.APPLICATION_JSON);

		mockMvc.perform(attemptRequest);
		mockMvc.perform(nextRoundRequest)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", greaterThanOrEqualTo(0)))
				.andExpect(jsonPath("$.roundNumber", is(2)))
				.andExpect(jsonPath("$.status", is("PLAYING")))
				.andExpect(jsonPath("$.score", is(25)))
				.andExpect(jsonPath("$.feedbackHistory", hasSize(0)))
				.andExpect(jsonPath("$.hint", hasLength(6)));
	}

	@Test
	@DisplayName("lose the first round")
	void loseRound() throws Exception {
		RequestBuilder newGameRequest = MockMvcRequestBuilders
				.post("/game/new")
				.contentType(MediaType.APPLICATION_JSON);

		MockHttpServletResponse response = mockMvc.perform(newGameRequest).andReturn().getResponse();
		Integer gameId = JsonPath.read(response.getContentAsString(), "$.id");

		LingoGameDM game = gameService.getGameById(Long.valueOf(gameId));
		String attempt = Utils.generateTestGuess(wordService,game.getLingoGame().getCurrentRound().getWordToGuess());

		RequestBuilder attemptRequest = MockMvcRequestBuilders
				.post("/game/"+ gameId + "/guess/"+ attempt)
				.contentType(MediaType.APPLICATION_JSON);

		String actualWordToGuess = game.getLingoGame().getCurrentRound().getWordToGuess();

		mockMvc.perform(attemptRequest);// 1
		mockMvc.perform(attemptRequest);// 2
		mockMvc.perform(attemptRequest);// 3
		mockMvc.perform(attemptRequest);// 4
		mockMvc.perform(attemptRequest) // 5
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.status", is("LOST")))
				.andExpect(jsonPath("$.score", is(0)))
				.andExpect(jsonPath("$.message", is("The word to guess was " + actualWordToGuess)))
				.andExpect(jsonPath("$.feedbackHistory", hasSize(5)));
	}

	@Test
	@DisplayName("lose the first round and try to guess anyway")
	void loseRoundAndGuess() throws Exception {
		RequestBuilder newGameRequest = MockMvcRequestBuilders
				.post("/game/new")
				.contentType(MediaType.APPLICATION_JSON);

		MockHttpServletResponse response = mockMvc.perform(newGameRequest).andReturn().getResponse();
		Integer gameId = JsonPath.read(response.getContentAsString(), "$.id");

		LingoGameDM game = gameService.getGameById(Long.valueOf(gameId));
		String attempt = Utils.generateTestGuess(wordService,game.getLingoGame().getCurrentRound().getWordToGuess());

		RequestBuilder attemptRequest = MockMvcRequestBuilders
				.post("/game/"+ gameId + "/guess/"+ attempt)
				.contentType(MediaType.APPLICATION_JSON);


		mockMvc.perform(attemptRequest);// 1
		mockMvc.perform(attemptRequest);// 2
		mockMvc.perform(attemptRequest);// 3
		mockMvc.perform(attemptRequest);// 4
		mockMvc.perform(attemptRequest);// 5
		mockMvc.perform(attemptRequest)
				.andExpect(status().isBadRequest());
	}

	@Test
	@DisplayName("cannot start a new round when playing")
	void cannotStartNewRoundWhenPlaying() throws Exception {
		RequestBuilder newGameRequest = MockMvcRequestBuilders
				.post("/game/new")
				.contentType(MediaType.APPLICATION_JSON);

		MockHttpServletResponse response = mockMvc.perform(newGameRequest).andReturn().getResponse();
		Integer gameId = JsonPath.read(response.getContentAsString(), "$.id");

		RequestBuilder nextRoundRequest = MockMvcRequestBuilders
				.post("/game/"+ gameId + "/round/next")
				.contentType(MediaType.APPLICATION_JSON);

		mockMvc.perform(nextRoundRequest)
				.andExpect(status().isBadRequest());
	}

	@Test
	@DisplayName("get a game by it's id")
	void getGameById() throws Exception {
		RequestBuilder newGameRequest = MockMvcRequestBuilders
				.post("/game/new")
				.contentType(MediaType.APPLICATION_JSON);

		MockHttpServletResponse response = mockMvc.perform(newGameRequest).andReturn().getResponse();
		Integer gameId = JsonPath.read(response.getContentAsString(), "$.id");

		RequestBuilder getGameRequest = MockMvcRequestBuilders
				.get("/game/"+ gameId)
				.contentType(MediaType.APPLICATION_JSON);

		mockMvc.perform(getGameRequest)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.roundNumber", is(1)))
				.andExpect(jsonPath("$.status", is("PLAYING")))
				.andExpect(jsonPath("$.score", is(0)))
				.andExpect(jsonPath("$.feedbackHistory", hasSize(0)))
				.andExpect(jsonPath("$.hint", hasLength(5)));
	}

	@Test
	@DisplayName("get a game that doesn't exist")
	void getNonexistentGame() throws Exception {
		int fakeGameId = 999999;
		RequestBuilder getGameRequest = MockMvcRequestBuilders
				.get("/game/"+ fakeGameId)
				.contentType(MediaType.APPLICATION_JSON);

		mockMvc.perform(getGameRequest)
				.andExpect(status().isNotFound());
	}
}