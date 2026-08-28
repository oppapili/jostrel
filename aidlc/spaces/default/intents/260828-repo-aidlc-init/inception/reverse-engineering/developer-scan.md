# Developer Code Scan — jostrel

## Developer Code Scan Results

### Scan Coverage

- **Analyzed deeply**:
  - `pom.xml`
  - `src/main/resources/application.properties`
  - `src/main/java/io/github/oppapili/jostrel/JostrelApplication.java`
  - `src/main/java/io/github/oppapili/jostrel/config/WebSocketConfig.java`
  - `src/main/java/io/github/oppapili/jostrel/config/JacksonConfig.java`
  - `src/main/java/io/github/oppapili/jostrel/handler/WebSocketHandler.java`
  - `src/main/java/io/github/oppapili/jostrel/model/` (Event, Filter, Message, MessageType, MessageDeserializer, Subscription, EventMessage, EoseMessage, ClosedMessage)
  - `src/main/java/io/github/oppapili/jostrel/service/` (EventService, SubscriptionManager)
  - `src/main/java/io/github/oppapili/jostrel/repository/EventRepository.java`
  - `src/test/java/io/github/oppapili/jostrel/JostrelApplicationTests.java`
  - `src/test/java/io/github/service/SubscriptionManagerTest.java`
  - `README.md`
- **Skimmed only**:
  - `.devcontainer/` (Dockerfile, devcontainer.json)
  - `.github/` (ISSUE_TEMPLATE, dependabot.yml)
  - `.mvn/`, `mvnw`, `mvnw.cmd` (Maven wrapper)

### Packages Found

- `io.github.oppapili.jostrel` — root — Java — Spring Boot アプリケーションエントリポイント（`JostrelApplication`）
- `io.github.oppapili.jostrel.config` — configuration — Java — WebSocket / Jackson の設定
- `io.github.oppapili.jostrel.handler` — handler — Java — Nostr WebSocket メッセージ処理
- `io.github.oppapili.jostrel.model` — model/DTO — Java — Nostr プロトコルのデータモデル（NIP-01）
- `io.github.oppapili.jostrel.service` — service — Java — ビジネスロジック（イベント保存/検索、購読管理）
- `io.github.oppapili.jostrel.repository` — repository — Java — イベント永続化層（インメモリ）

### Build System

- **Type**: Maven（Spring Boot parent 3.5.0）
- **Config Files**: `pom.xml`, `mvnw`, `mvnw.cmd`, `.mvn/`
- **Build Dependencies**:
  - `spring-boot-starter-parent:3.5.0` → 依存バージョン管理
  - `spring-boot-starter-websocket` → WebSocket ランタイム
  - `lombok:1.18.38`（provided）→ ボイラープレート削減
  - `spring-boot-starter-test`（test）→ テスト基盤
  - `spring-boot-maven-plugin` → パッケージング

### APIs Discovered

- WebSocket エンドポイント — `WebSocketConfig` — 1 エンドポイント（パス `/`、全オリジン許可 `*`）
- Nostr メッセージプロトコル（NIP-01） — `WebSocketHandler` — 受信 3 種（`EVENT` / `REQ` / `CLOSE`）、送信 3 種（`EVENT` / `EOSE` / `CLOSED`）
- HTTP REST エンドポイント — なし（WebSocket のみ）

### Frameworks & Libraries

- Spring Boot — 3.5.0 — アプリケーションフレームワーク
- Spring WebSocket — （Boot 管理）— WebSocket 通信
- Java — 21 — 言語/ランタイム
- Lombok — 1.18.38 — `@Data` `@Builder` 等でボイラープレート削減
- Jackson — （Boot 管理）— JSON シリアライズ/デシリアライズ（`MessageDeserializer` によるカスタムデシリアライズ）

### Test Coverage

- **Test Directories**: `src/test/java/io/github/oppapili/jostrel/`, `src/test/java/io/github/service/`
- **Test Frameworks**: Spring Boot Test（JUnit 5）
- **Coverage Config**: 不在（JaCoCo 等のカバレッジ設定なし）
- 備考: テストは2件のみ。`JostrelApplicationTests`（コンテキストロード）と `SubscriptionManagerTest`。テストパッケージ `io.github.service` が本体パッケージ（`io.github.oppapili.jostrel.service`）とずれている。

### Code Quality Indicators

- **Linting**: 専用リンタ設定なし（Prettier/Checkstyle/Spotless 等の設定ファイルは検出されず）
- **CI/CD**: パイプライン定義なし（`.github/workflows/` は不在。`.github/` には ISSUE_TEMPLATE と dependabot.yml のみ）
- **Documentation**: README.md あり（概要・セットアップ・構成・対応状況を記載）。主要クラスに JavaDoc あり（Event, Filter, Message, MessageType 等に NIP-01 仕様への参照付き）

### Technical Debt Signals

- `EventService.matches(Filter, Event)` が常に `true` を返す TODO 実装 — `service/EventService.java`。フィルタによるイベント絞り込みが未実装で、`REQ` に対し全イベントを返す。
- イベント永続化が `EventRepository` の `CopyOnWriteArrayList` によるインメモリ保持のみ — `repository/EventRepository.java`。再起動でデータ消失。
- 署名検証なし — `WebSocketHandler` の `EVENT` 処理は `Event.sig` を検証せず保存する。
- テストパッケージのパスずれ — `src/test/java/io/github/service/SubscriptionManagerTest.java` が本体の名前空間と不一致。
- `WebSocketHandler` が `@Autowired` フィールドとコンストラクタ注入を併用（冗長）。
- `ClosedMessage` の定数名に typo（`DEFALUT_MESSAGE`）。
- カバレッジ設定・CI が未整備で、回帰検知の自動化がない。

## Handoff Summary

- **Intent-relevant finding**: 本リポジトリは単一 Maven モジュールの Spring Boot 製 Nostr リレー。NIP-01 のメッセージ往復（EVENT/REQ/CLOSE と EVENT/EOSE/CLOSED）は WebSocket 経由で実装済みだが、(1) フィルタマッチング未実装、(2) 永続化がインメモリのみ、(3) 署名検証なし、の3点が中核的な技術的負債。AI-DLC 開発基盤の整備という意図に対し、これらは requirements-analysis で要件化する主要候補。
- **Risks / follow-up**: フィルタ未実装・永続化なし・署名検証なしは「動作するが仕様未達」の状態。CI/カバレッジ設定が皆無のため、今後の変更の回帰検知手段が現状ない（ci-pipeline ステージで整備予定）。
