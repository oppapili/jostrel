package io.github.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import io.github.oppapili.jostrel.model.Subscription;
import io.github.oppapili.jostrel.service.SubscriptionManager;

public class SubscriptionManagerTest {
  @Test
  public void manageSubscriptionsPerSession() {
    // Arrange
    var sessionId1 = "websocket-session-1";
    var sessionId2 = "websocket-session-2";

    var sub1 = new Subscription("subscription-1", new ArrayList<>());
    var sub2 = new Subscription("subscription-2", new ArrayList<>());
    var sub3 = new Subscription("subscription-3", new ArrayList<>());
    var sub1Dash = new Subscription("subscription-1", new ArrayList<>());

    var manager = new SubscriptionManager();

    // 🔍 Add subscriptions per session.
    // Act
    manager.addSubscriptionToSession(sessionId1, sub1);
    manager.addSubscriptionToSession(sessionId1, sub2);

    manager.addSubscriptionToSession(sessionId2, sub3);

    // Assert
    assertEquals(2, manager.getSubscriptionsOfSession(sessionId1).size());
    assertTrue(manager.getSubscriptionsOfSession(sessionId1).containsKey("subscription-1"));
    assertTrue(manager.getSubscriptionsOfSession(sessionId1).containsKey("subscription-2"));

    assertEquals(1, manager.getSubscriptionsOfSession(sessionId2).size());
    assertTrue(manager.getSubscriptionsOfSession(sessionId2).containsKey("subscription-3"));

    // 🔍 Remove one subscription from session
    // Act
    manager.removeSubscriptionFromSession(sessionId1, "subscription-1");

    // Assert
    assertEquals(1, manager.getSubscriptionsOfSession(sessionId1).size());
    assertFalse(manager.getSubscriptionsOfSession(sessionId1).containsKey("subscription-1"));

    // 🔍 Remove all subscriptions from session
    // Act
    manager.removeAllSubscriptionsOfSession(sessionId1);

    // Assert
    assertEquals(0, manager.getSubscriptionsOfSession(sessionId1).size());

    // 🔍 Overwrite subscription of session
    // Act
    manager.addSubscriptionToSession(sessionId1, sub1Dash);

    // Assert
    assertEquals(1, manager.getSubscriptionsOfSession(sessionId1).size());
    assertNotSame(sub1, manager.getSubscriptionsOfSession(sessionId1).get("subscription-1"));
  }
}
