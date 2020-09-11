package com.trade.rates.server.websocket;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.trade.JUnitTag.UNIT_TEST;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag(UNIT_TEST)
class WebSocketHeartbeatManagerTest {

	@Test
	public void testConstructor() {
		WebSocketHeartbeatManager webSocketHeartbeatManager = new WebSocketHeartbeatManager(1L, 2L);
		assertEquals(1L, webSocketHeartbeatManager.getHeartbeatCheck());
		assertEquals(2L, webSocketHeartbeatManager.getHeartbeatTimeout());
	}

	@Test
	public void testHeartbeat() {
		WebSocketHeartbeatManager webSocketHeartbeatManager = new WebSocketHeartbeatManager();
		webSocketHeartbeatManager.heartbeat("sessionId");
		assertTrue(webSocketHeartbeatManager.getLastHeartbeat("sessionId") > System.currentTimeMillis() - 10L);
	}
}