# API Documentation — Jostrel

## External API — WebSocket (Nostr NIP-01)

- **エンドポイント**: `ws://<host>:8080/`（パス `/`、Spring Boot デフォルトポート 8080 と推定。`application.properties` にポート設定なし）
- **オリジン**: 全許可（`setAllowedOrigins("*")`、`WebSocketConfig`）
- **プロトコル**: テキストフレーム。JSON 配列 `[<TYPE>, <payload>...]`（NIP-01）

### 受信メッセージ（クライアント → リレー）

| Type | ペイロード | 動作 |
|---|---|---|
| `EVENT` | `[<event>]` | イベントを保存する（`EventService.saveEvent`）。署名検証・`OK` 応答は未実装 |
| `REQ` | `[<sub_id>, <filter1>, <filter2>, ...]` | 購読を登録し、一致イベントを返して `EOSE` を送る。フィルタ絞り込みは未実装（全件返却） |
| `CLOSE` | `[<sub_id>]` | 購読を解除し、削除できた場合 `CLOSED` を送る |

（`MessageType` には `OK` / `NOTICE` も定義されているが、ハンドラでの送信処理は未実装。）

### 送信メッセージ（リレー → クライアント）

| Type | ペイロード | 契機 |
|---|---|---|
| `EVENT` | `[<sub_id>, <event>]` | `REQ` に対する一致イベントの返却 |
| `EOSE` | `[<sub_id>]` | 保存済みイベントの送出完了 |
| `CLOSED` | `[<sub_id>, <message>]` | 購読解除時（デフォルトメッセージ `"subscription closed"`） |

## Internal API（主なメソッド契約）

### EventService

- `void saveEvent(Event event)` — イベントを保存。
- `List<Event> findEventsByFilters(List<Filter> filters)` — フィルタに一致するイベントを返す。現状 `matches()` が常に `true`（全件返却）。

### SubscriptionManager

- `void addSubscriptionToSession(String sessionId, Subscription subscription)`
- `Map<String, Subscription> getSubscriptionsOfSession(String sessionId)`
- `Optional<Subscription> removeSubscriptionFromSession(String sessionId, String subscriptionId)`
- `void removeAllSubscriptionsOfSession(String sessionId)`

### EventRepository

- `void save(Event event)` — インメモリリストへ追加。
- `List<Event> findAll()` — 保管イベントの不変コピーを返す。

## HTTP REST API

なし（WebSocket のみ）。
