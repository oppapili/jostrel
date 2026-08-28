# Technology Stack — Jostrel

## Language / Runtime

- **Java** — 21（`pom.xml` の `java.version`）

## Framework

- **Spring Boot** — 3.5.0（`spring-boot-starter-parent`）
- **Spring WebSocket** — Boot 管理バージョン（`spring-boot-starter-websocket`）

## Libraries

| ライブラリ | バージョン | スコープ | 用途 |
|---|---|---|---|
| Lombok | 1.18.38 | provided | ボイラープレート削減（`@Data` `@Builder` 等） |
| Jackson | Boot 管理 | runtime | JSON シリアライズ/デシリアライズ |
| Spring Boot Test | Boot 管理 | test | テスト基盤（JUnit 5） |

## Build / Tooling

- **Maven**（`pom.xml` + `mvnw` ラッパー）
- **spring-boot-maven-plugin** — パッケージング
- **Dependabot**（`.github/dependabot.yml`）— 依存更新
- **DevContainer**（`.devcontainer/`）— Java 21 + Maven の開発環境

## Not Present（未整備）

- カバレッジツール（JaCoCo 等）: なし
- リンタ/フォーマッタ設定（Checkstyle/Spotless/Prettier 等）: なし
- CI パイプライン（`.github/workflows/`）: なし
