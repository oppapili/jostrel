# Unit Test Instructions — AI-DLC 開発基盤の整備（Jostrel）

## Testing Posture（team.md より）

- Methodology: test-after
- Ordering: 各テスト可能なレイヤを実装後、そのレイヤのテストを書いて実行する。
- 既存テスト（2件）は緑を維持する。カバレッジ80%は将来目標（今回ゲート化しない）。

## 本ステージのテスト方針

本ステージの変更はビルド設定（JaCoCo / Spotless）と CI（GitHub Actions）、および既存 Java コードの整形であり、新規のプロダクションロジックは追加しない。したがって新規のユニットテストは追加しない。検証は以下で行う（build-and-test ステージ）。

### 検証項目

1. **既存テストの緑維持**: `./mvnw test` が整形・プラグイン追加後も成功する（`JostrelApplicationTests`, `SubscriptionManagerTest`）。回帰がないこと。
2. **JaCoCo レポート生成**: `./mvnw test`（または `verify`）実行後、`target/site/jacoco/` にカバレッジレポート（HTML/XML）が生成される。
3. **Spotless チェック**: 一括整形後、`./mvnw spotless:check` が成功する（違反ゼロ）。
4. **ビルド成功**: `./mvnw -B verify` が成功する。

### テスト対象外

- カバレッジのしきい値による fail（NFR2 によりゲート化しない）。
- 新規機能テスト（中核負債は Out of scope）。

## Arrange-Act-Assert

既存テストの様式（Spring Boot Test / JUnit 5）を踏襲。本ステージでは新規テストなし。
