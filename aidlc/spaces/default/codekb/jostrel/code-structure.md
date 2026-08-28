# Code Structure — Jostrel

## Package Organization

ベースパッケージ: `io.github.oppapili.jostrel`

```text
src/main/java/io/github/oppapili/jostrel/
├── JostrelApplication.java        # Spring Boot エントリポイント
├── config/
│   ├── WebSocketConfig.java       # WebSocket エンドポイント登録（/、全オリジン許可）
│   └── JacksonConfig.java         # ObjectMapper（Message カスタムデシリアライザ登録）
├── handler/
│   └── WebSocketHandler.java      # Nostr メッセージ処理（EVENT/REQ/CLOSE）
├── model/
│   ├── Event.java                 # Nostr イベント（NIP-01）
│   ├── Filter.java                # イベントフィルタ
│   ├── Message.java               # メッセージ基底（type + payload）
│   ├── MessageType.java           # メッセージ種別 enum
│   ├── MessageDeserializer.java   # 配列形式メッセージのカスタムデシリアライズ
│   ├── Subscription.java          # 購読（id + filters）
│   ├── EventMessage.java          # EVENT 送信メッセージ
│   ├── EoseMessage.java           # EOSE 送信メッセージ
│   └── ClosedMessage.java         # CLOSED 送信メッセージ
├── repository/
│   └── EventRepository.java       # イベント保管（CopyOnWriteArrayList）
└── service/
    ├── EventService.java          # イベント保存/フィルタ検索
    └── SubscriptionManager.java   # セッション別購読管理
```

```text
src/test/java/
├── io/github/oppapili/jostrel/
│   └── JostrelApplicationTests.java   # コンテキストロードテスト
└── io/github/service/
    └── SubscriptionManagerTest.java   # 購読管理のテスト（パッケージパスが本体とずれ）
```

## File Classification

| 種別 | ファイル |
|---|---|
| エントリポイント | `JostrelApplication.java` |
| 設定 | `config/WebSocketConfig.java`, `config/JacksonConfig.java` |
| 入口/ハンドラ | `handler/WebSocketHandler.java` |
| ドメインモデル/DTO | `model/*.java`（9ファイル） |
| サービス | `service/EventService.java`, `service/SubscriptionManager.java` |
| 永続化 | `repository/EventRepository.java` |
| テスト | `test/.../JostrelApplicationTests.java`, `test/.../SubscriptionManagerTest.java` |
| ビルド/設定 | `pom.xml`, `application.properties`, `mvnw`, `.mvn/` |

## Code Patterns

- **Lombok** による DTO 生成（`@Data` `@Builder` `@NoArgsConstructor` `@AllArgsConstructor`）。
- **メッセージサブクラス**（`EventMessage` / `EoseMessage` / `ClosedMessage`）が `Message` を継承し、送信用ペイロードを構築。
- **カスタム Jackson デシリアライザ**（`MessageDeserializer`）で JSON 配列 → `Message` 変換。
- **列挙 + switch** による受信メッセージのディスパッチ（`WebSocketHandler.handleTextMessage`）。

## Naming / Convention Notes

- パッケージは小文字ドット区切り、クラスは PascalCase（Spring Boot 慣習準拠）。
- テストパッケージ `io.github.service` が本体名前空間とずれている（`code-quality-assessment.md` に負債として記録）。
