# Component Inventory — Jostrel

各コンポーネントの見出しは `reverse-engineering-timestamp.md` の Scope of Analysis ブロックの `analyzed.components` と一致する。

## WebSocketConfig

- **責務**: WebSocket エンドポイント（`/`）の登録と `WebSocketHandler` の紐付け。全オリジン許可。
- **依存**: `WebSocketHandler`
- **健全性**: healthy

## JacksonConfig

- **責務**: `ObjectMapper` の Bean 提供。`Message` 用のカスタムデシリアライザ（`MessageDeserializer`）を登録。
- **依存**: `MessageDeserializer`, `Message`
- **健全性**: healthy

## WebSocketHandler

- **責務**: Nostr メッセージ（`EVENT`/`REQ`/`CLOSE`）の受信・ディスパッチ・応答。セッションライフサイクル処理。
- **依存**: `ObjectMapper`, `SubscriptionManager`, `EventService`, `model/*`
- **健全性**: at-risk（署名検証なし、`@Autowired` フィールドとコンストラクタ注入の併用、例外を `RuntimeException` で握り直し）

## EventService

- **責務**: イベントの保存と、フィルタによる検索。
- **依存**: `EventRepository`, `Filter`, `Event`
- **健全性**: degraded（`matches()` が常に `true` の TODO 実装＝フィルタ未実装）

## SubscriptionManager

- **責務**: セッション別の購読管理（追加/取得/削除）。スレッドセーフ。
- **依存**: `Subscription`
- **健全性**: healthy

## EventRepository

- **責務**: イベントの保管と全件取得。
- **依存**: `Event`
- **健全性**: at-risk（`CopyOnWriteArrayList` によるインメモリのみ。永続化なし・全件走査）

## model（ドメインモデル群）

- **責務**: Nostr プロトコル（NIP-01）のデータ表現。`Event`, `Filter`, `Message`, `MessageType`, `MessageDeserializer`, `Subscription`, `EventMessage`, `EoseMessage`, `ClosedMessage`。
- **依存**: Jackson, Lombok
- **健全性**: healthy（`ClosedMessage` の定数 typo `DEFALUT_MESSAGE` のみ軽微）

## JostrelApplication

- **責務**: Spring Boot アプリケーションのエントリポイント。
- **依存**: Spring Boot
- **健全性**: healthy
