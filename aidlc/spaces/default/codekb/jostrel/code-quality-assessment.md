# Code Quality Assessment — Jostrel

## Test Coverage

- **テストファイル**: 2件
  - `JostrelApplicationTests`（コンテキストロード）
  - `SubscriptionManagerTest`（購読管理）
- **カバレッジ設定**: なし（JaCoCo 等未導入）
- **評価**: ハンドラ・サービス・リポジトリ・デシリアライザの大部分が未テスト。ビジネスロジックのカバレッジは低い。

## Linting / Formatting

- 専用のリンタ/フォーマッタ設定なし（Checkstyle/Spotless/Prettier 等の設定ファイル未検出）。

## CI/CD

- CI パイプライン定義なし（`.github/workflows/` が不在）。回帰検知の自動化がない。
- `.github/` には ISSUE_TEMPLATE と `dependabot.yml` のみ。

## Documentation

- README.md あり（概要・セットアップ・ディレクトリ構成・対応状況表を記載）。
- 主要モデル/設定クラスに JavaDoc あり（NIP-01 仕様への参照付きで質は良い）。

## Technical Debt（優先度付き）

| ID | 深刻度 | 内容 | 場所 |
|---|---|---|---|
| TD1 | High | フィルタマッチング未実装（`matches()` が常に `true`、`REQ` に全件返却） | `service/EventService.java` |
| TD2 | High | イベント永続化がインメモリのみ（再起動で消失） | `repository/EventRepository.java` |
| TD3 | High | イベント署名検証なし（`EVENT` を無検証で保存、`OK` 応答なし） | `handler/WebSocketHandler.java` |
| TD4 | Medium | CI/カバレッジ設定が皆無で回帰検知手段がない | リポジトリ全体 |
| TD5 | Low | テストパッケージ `io.github.service` が本体名前空間とずれ | `src/test/java/io/github/service/` |
| TD6 | Low | `@Autowired` フィールドとコンストラクタ注入の併用（冗長） | `handler/WebSocketHandler.java` |
| TD7 | Low | 定数名の typo（`DEFALUT_MESSAGE`） | `model/ClosedMessage.java` |
| TD8 | Low | 例外を握って `RuntimeException` で再送出（メッセージ単位のエラー方針が粗い） | `handler/WebSocketHandler.java` |

## Overall Health

**at-risk**。プロトコルの往復は動くが、リレーの中核機能（フィルタ・永続化・署名検証）が未達で、それを守る自動テスト/CI もない。AI-DLC 基盤整備（本イニシアチブ）で、まず検証の足場（CI・カバレッジ）を整え、TD1〜TD3 を要件化していくのが妥当。
