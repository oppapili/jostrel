# Code Generation Plan — AI-DLC 開発基盤の整備（Jostrel）

## 対象要件

- FR1 カバレッジ計測（JaCoCo）
- FR2 コードフォーマット（Spotless + google-java-format、既存コード一括整形）
- FR3 CI 基盤（GitHub Actions: build / test / format check / coverage report）
- NFR1〜4（Java 21 CI、将来目標80%はゲート化なし、失敗ログ、ユーザー空間完結）

中核負債（OOS1〜4）は本ステージでは実装しない。

## Blast Radius Analysis（影響範囲）

| 変更対象 | 種別 | 影響 | 分類 |
|---|---|---|---|
| `pom.xml` | 変更 | JaCoCo プラグイン、Spotless プラグインを追加。既存の build/plugins に追記 | Medium（ビルド構成に影響、既存依存は変更しない） |
| `.github/workflows/ci.yml` | 新規 | CI ワークフロー追加。既存挙動への影響なし | Low |
| `src/main/**`, `src/test/**` の Java ファイル | 変更 | Spotless（google-java-format）による一括整形。挙動は不変、体裁のみ | Medium（全 Java ファイルの差分。ロジック不変） |

- 消費者・テストへの影響: 整形はロジックを変えないため、既存テスト2件の結果に影響しないはず（Test Baseline で確認）。
- 設定参照: `pom.xml` の変更は Maven ビルド全体に効くが、既存プラグイン（spring-boot-maven-plugin）とは独立。

## Test Baseline Protocol

実装前に `./mvnw test` を実行し、ベースライン（total/passing/failing）を記録する。整形・プラグイン追加後に再実行し、回帰がないことを確認する（build-and-test ステージで検証）。

## 実装ステップ（コミット分割案）

レビュー容易性のため、以下の順で分けて実装する（reviewer 助言 S1 反映）。

1. **JaCoCo 導入**（FR1）: `pom.xml` に jacoco-maven-plugin を追加（prepare-agent + report）。`./mvnw test` でレポート生成を確認。
2. **Spotless 設定追加**（FR2.1, FR2.3）: `pom.xml` に spotless-maven-plugin を追加し、googleJavaFormat を設定。`spotless:check` が動くことを確認。この時点では既存コードは未整形（check は fail しうる）。
3. **既存コード一括整形**（FR2.2）: `./mvnw spotless:apply` を実行し、全 Java ファイルを整形。差分はこのコミットに閉じる。以後 `spotless:check` が緑になる。
4. **CI ワークフロー追加**（FR3）: `.github/workflows/ci.yml` を新規作成。push / PR で `mvnw -B verify`（build + test + jacoco report）と `mvnw spotless:check` を実行。Java 21 / Temurin をセットアップ。カバレッジのしきい値による fail はしない（NFR2）。

## ツール選定

- **JaCoCo**: `org.jacoco:jacoco-maven-plugin`（Java の事実上標準のカバレッジツール、Maven 統合が容易）。
- **Spotless**: `com.diffplug.spotless:spotless-maven-plugin` + `googleJavaFormat`（team.md の Code Style と整合、インデント2スペースに統一）。
- **CI**: GitHub Actions（`actions/checkout`, `actions/setup-java` with Temurin 21）。

## Out of Scope（本プランでは変更しない）

- フィルタ実装 / 永続化 / 署名検証（OOS1〜3）。
- 軽微な負債（テストパッケージ移動、typo 修正等 OOS4）。

## Assumptions

- google-java-format の AOSP でなく標準スタイル（インデント2スペース）を採用。
- CI は GitHub でホストされている前提。

## Plan status

計画は承認待ち。承認後に Step 4（実装）へ進む。

## Review

**Verdict:** READY

**Reviewer:** aidlc-architecture-reviewer-agent

**Iteration:** 1

### 評価

- ツール選定は健全: JaCoCo 0.8.12 は Java 21 をサポート、Spotless 2.44.3 + google-java-format 1.24.0 も Java 21 で動作する。バージョンの組み合わせに既知の非互換はない。
- スコープが requirements.md の FR1〜FR3・NFR1〜4 に対応し、中核負債（OOS1〜4）を実装に混ぜていない。Blast Radius 分析（pom.xml=Medium、CI=Low、整形=Medium）が影響範囲を正しく捉えている。
- コミット分割案（JaCoCo → Spotless設定 → 一括整形 → CI）は reviewer 助言 S1 を反映し、整形差分を独立コミットに閉じている。実装可能で順序も妥当。

### 助言事項（ブロッカーではない）

- **S1 (Low)**: 一括整形（Step 3）はロジック不変だが全 Java ファイルの差分になるため、`./mvnw test` によるベースライン確認を整形の前後で行い、回帰ゼロを明示的に確認すること（build-and-test で検証）。
- **S2 (Low)**: google-java-format は Lombok アノテーション付きクラスも整形するが、アノテーションの意味は変えない。念のため整形後に既存テストの緑を確認する。

