package io.github.oppapili.jostrel.service;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;
import io.github.oppapili.jostrel.model.Subscription;

/**
 * Manages subscriptions for WebSocket sessions.
 * 
 * <p>
 * This class provides methods to add and remove subscriptions associated with specific session IDs.
 * It uses a thread-safe map to store subscriptions, allowing concurrent access from multiple
 * threads.
 */
@Component
public class SubscriptionManager {

  /**
   * A map that holds subscriptions for each session ID. The key is the session ID, and the value is
   * a map of subscription IDs to Subscription objects.
   */
  private final Map<String, Map<String, Subscription>> subscriptions = new ConcurrentHashMap<>();

  /**
   * Adds a subscription for a given session ID.
   *
   * @param sessionId the ID of the session
   * @param subscription the Subscription object to add
   */
  public void addSubscriptionToSession(String sessionId, Subscription subscription) {
    subscriptions.computeIfAbsent(sessionId, k -> new ConcurrentHashMap<>())
        .put(subscription.getId(), subscription);
  }

  /**
   * Retrieves all subscriptions associated with a specific session ID.
   *
   * @param sessionId the ID of the session
   * @return a map of subscription IDs to Subscription objects for the specified session
   */
  public Map<String, Subscription> getSubscriptionsOfSession(String sessionId) {
    return subscriptions.getOrDefault(sessionId, Map.of());
  }

  /**
   * Removes a subscription from a session by its subscription ID.
   * 
   * @param sessionId the ID of the session
   * @param subscriptionId the ID of the subscription to remove
   * @return an Optional containing the removed Subscription if it exists, or an empty Optional if
   *         not found
   */
  public Optional<Subscription> removeSubscriptionFromSession(String sessionId,
      String subscriptionId) {
    var subscriptionsOfSession = subscriptions.get(sessionId);

    if (subscriptionsOfSession != null) {
      var removedSubscription = subscriptionsOfSession.remove(subscriptionId);

      // If there are no subscriptions left for this session, remove the session entry
      if (subscriptionsOfSession.isEmpty()) {
        subscriptions.remove(sessionId);
      }

      return Optional.ofNullable(removedSubscription);
    }

    return Optional.empty();
  }

  /**
   * Removes all subscriptions associated with a specific session ID.
   *
   * @param sessionId the ID of the session to remove
   */
  public void removeAllSubscriptionsOfSession(String sessionId) {
    subscriptions.remove(sessionId);
  }
}
