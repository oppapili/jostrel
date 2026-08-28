# Requirements — AI-DLC 開発基盤の整備（Jostrel）

## Intent Analysis

既存の Jostrel（Java 21 / Spring Boot 3.5.0 / Maven の Nostr リレー）を、今後 AI-DLC のもとで継続開発するための **開発基盤** を整える。本イニシアチブはリレーの機能追加ではなく、検証・整形・自動化の足場づくりに絞る。中核機能の負債（フィルタ・永続化・署名検証）は要件として記録するが、実装は別イニシアチブに委ねる。

## Functional Requirements

### FR1. カバレッジ計測基盤

- FR1.1 Maven ビルドに JaCoCo を組み込み、テスト実行時にカバレッジレポート（HTML / XML）を生成する。
- FR1.2 カバレッジレポートは CI 実行時にも生成される。

### FR2. コードフォーマット基盤

- FR2.1 Spotless + google-java-format を Maven に導入し、Java コードの整形を自動化する。
- FR2.2 既存の Java コード全体を一括整形し、現状のインデント混在（2/4 スペース）を解消する。
- FR2.3 フォーマット違反を検出するチェック（`spotless:check` 相当）を提供する。

### FR3. CI 基盤（GitHub Actions）

- FR3.1 push および pull request を契機に CI が起動する。
- FR3.2 CI は Maven build（`mvn package`）を実行する。
- FR3.3 CI はテスト（`mvn test`）を実行し、既存テストの緑を維持する。
- FR3.4 CI は Spotless によるフォーマットチェックを実行する。
- FR3.5 CI は JaCoCo によるカバレッジレポートを生成する（しきい値による fail は行わない）。

## Non-Functional Requirements

- NFR1. CI は Java 21 上で動作し、Maven ラッパー（`mvnw`）を用いる。
- NFR2. カバレッジのビジネスロジック80%は「将来目標」として記載し、今回はゲート化しない（段階的に引き上げる）。
- NFR3. フォーマット・ビルド・テストの各ステップは、失敗時に原因が分かるログを出力する。
- NFR4. 導入するツールはユーザー空間で完結し、特別な特権を要しない（既存の Maven ラッパー経由で実行可能）。

## Constraints

- Java 21 / Spring Boot 3.5.0 / Maven を維持する（技術スタックの変更はしない）。
- trunk-based development、Conventional Commits に従う。
- commit & push はユーザー確認を得てから行う。
- 秘密情報はコミットせず環境変数で管理する。

## Assumptions

- CI は GitHub Actions を用いる（リポジトリが GitHub にホストされているため）。
- google-java-format のインデント規約（2スペース）を採用する（team.md の Code Style と整合）。
- 既存テスト（2件）は一括整形後も緑を維持できる。

## Out of Scope

以下の中核負債は本イニシアチブでは実装せず、記録のみとする。承認後に GitHub issue 化を提案する（intent-statement の Working Practices と整合）。

- OOS1. フィルタによるイベント絞り込みの実装（`EventService.matches` の TODO 解消） — code-quality-assessment.md TD1。
- OOS2. イベントの永続化（インメモリからの脱却） — TD2。
- OOS3. イベント署名の検証 — TD3。
- OOS4. 軽微な負債（テストパッケージのパスずれ、`DEFALUT_MESSAGE` typo、DI の冗長、例外方針） — TD5〜TD8。

## Open Questions

- google-java-format と Google スタイル（2スペース）以外のフォーマッタ流派（4スペース系）を将来採用する可能性 — 現時点では google-java-format で確定。
- カバレッジしきい値をいつゲート化するか — 基盤整備後、別途判断。

## Traceability（上流）

- 上流: intent-statement.md（目的・スコープ）、team-practices.md（Testing Posture / Code Style / Deployment）、codekb（business-overview / architecture / code-structure / code-quality-assessment）。
- FR1〜FR3・NFR1〜NFR4 は intent-statement の Success Metrics（構造把握・ビルド/テスト/CI 足場）に対応。
- OOS1〜OOS4 は code-quality-assessment.md の TD1〜TD8 に対応。

## Review

**Verdict:** READY

**Reviewer:** aidlc-product-lead-agent

**Iteration:** 1

### 評価

- FR1〜FR3・NFR1〜NFR4 に安定 ID が付与され、いずれも検証可能な形（レポート生成・CI 起動・チェック実行）で記述されている。
- Out of scope（OOS1〜OOS4）が明示され、code-quality-assessment.md の TD1〜TD8 へトレースできる。intent-statement の Working Practices（issue 化提案）とも整合。
- Constraints / Assumptions / Open Questions が揃っており、スコープ境界が明確。基盤整備という意図に忠実で、機能実装を混ぜていない点が良い。

### 助言事項（人間の判断に委ねる、ブロッカーではない）

- **S1 (Low)**: FR2.2（既存コード一括整形）は、フォーマッタ導入 PR の差分を大きくする。レビュー容易性のため、code-generation 段階で「フォーマッタ設定の追加」と「一括整形の適用」を別コミットに分けることを推奨（要件自体の変更は不要）。
- **S2 (Low)**: NFR2 のカバレッジ将来目標（80%）は、ゲート化の判断時期（Open Questions に記載済み）が別途必要。今回スコープ外として妥当。
