import static org.junit.jupiter.api.Assertions.*;

import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MorraTest {
	MorraClient client;

	@BeforeEach
	void init() {
		client = new MorraClient(data->{}, 5555, "127.0.0.1");
		client.start();
	}

	@Test
	void testMorraClientConstructor() {
		assertEquals(5555, client.portNum);
		assertEquals("127.0.0.1", client.ip);
	}

	@Test
	void testWhoWon1() {
		client.clientInfo.setP1Points(2);
		client.clientInfo.setP2Points(0);

		assertEquals(1, client.whoWon());
	}

	@Test
	void testWhoWon2() {
		client.clientInfo.setP1Points(0);
		client.clientInfo.setP2Points(2);

		assertEquals(2, client.whoWon());
	}

	@Test
	void testWhoWon3() {
		client.clientInfo.setP1Points(0);
		client.clientInfo.setP2Points(0);

		assertEquals(0, client.whoWon());
	}

	@Test
	void testWhoWon4() {
		client.clientInfo.setP1Points(1);
		client.clientInfo.setP2Points(0);

		assertEquals(0, client.whoWon());
	}

	@Test
	void testWhoWon5() {
		client.clientInfo.setP1Points(1);
		client.clientInfo.setP2Points(1);

		assertEquals(0, client.whoWon());
	}

	@Test
	void testWhoWon6() {
		client.clientInfo.setP1Points(0);
		client.clientInfo.setP2Points(0);

		assertEquals(0, client.whoWon());

		client.clientInfo.setP2Points(1);
		assertEquals(0, client.whoWon());
	}

	@Test
	void testWhoWon7() {
		client.clientInfo.setP1Points(0);
		client.clientInfo.setP2Points(0);

		assertEquals(0, client.whoWon());

		client.clientInfo.setP1Points(1);
		client.clientInfo.setP2Points(2);
		assertEquals(2, client.whoWon());
	}

	@Test
	void testWhoWon8() {
		client.clientInfo.setP1Points(0);
		client.clientInfo.setP2Points(0);

		assertEquals(0, client.whoWon());

		client.clientInfo.setP1Points(2);
		client.clientInfo.setP2Points(1);
		assertEquals(1, client.whoWon());
	}
}
