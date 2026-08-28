# Team Practices — Jostrel

## Way of Working

trunk-based development。単一 `main` ブランチへ短命のフィーチャーブランチ経由でマージする。コミットは Conventional Commits（`type(scope): description`、type は feat/fix/docs/refactor/test/chore）に従う。commit & push はユーザー確認を得てから実行する。

## Walking Skeleton

適用しない。既存の Nostr リレー実装が動作しており、薄いエンドツーエンドの骨組みを新たに作る必要はない。今後の変更は通常の作業として進める（active scope の `skeleton: off` と整合）。

## Testing Posture

- **Methodology**: test-after
- **Ordering**: 各テスト可能なレイヤを実装した後、そのレイヤのテストを書いて実行する。
- ビジネスロジックは最低80%のカバレッジを目標とする（共通ロジックは100%を目指す）。ただし即時全域適用ではなく、まずカバレッジ計測（JaCoCo）と CI を足場として整え、段階的に引き上げる。既存テスト（2件）は緑を維持する。
- カバレッジ計測ツールとして JaCoCo を導入し、CI で実行する。
- 外部依存はモック化する。クリティカルパスには統合テストを書く。
- Arrange-Act-Assert で構成し、テスト名は説明的に書く。

## Deployment

現時点でデプロイパイプラインは未整備（本イニシアチブのスコープ外）。将来的にサービスとして運用する場合は Docker 優先（アプリ/ミドルウェアは Docker、サーバ全体管理ツールのみホスト直接）。CI 整備（本スコープの ci-pipeline）を先行させる。

## Code Style

- **Java コード**: Java 用フォーマッタ（Spotless + google-java-format 等）を導入し、CI で整形を強制する。現状インデントが 2/4 スペースで混在しているため、フォーマッタ導入時に統一する。フォーマッタが定める規約（google-java-format の場合インデント2スペース）に従う。
- **設定・ドキュメント系ファイル**（JSON / YAML / Markdown 等）: Prettier を使用（セミコロンあり、インデント2スペース、複数行の末尾カンマ）。
- 命名は Java 言語慣習に準拠: クラスは PascalCase、メソッド/変数は camelCase、定数は SCREAMING_SNAKE_CASE。
- すべての公開クラス/メソッドに JavaDoc を付与する（既存コードで実践済み）。
- 秘密情報はコミットせず環境変数で管理。クエリはパラメータ化。
