# Practices Discovery Evidence — Jostrel

## Inspected / Inferred

### pipeline-deploy（lead）

- **Git 履歴**: 14コミット。Conventional Commits を一貫使用（feat/fix/docs/refactor/test/chore + scope）。ブランチは `main` のみで trunk-based の運用実態。→ Way of Working。
- **CI/デプロイ設定**: `.github/workflows/` 不在。CI パイプラインなし。→ Deployment/CI は未整備、本スコープの ci-pipeline で整備。
- **DevContainer**: Java 21 + Maven。Prettier 拡張を含む。→ フォーマット意図の証拠。

### quality（support）

- テスト2件のみ（`JostrelApplicationTests`, `SubscriptionManagerTest`）、カバレッジ設定なし。→ Testing Posture は test-after、段階的なカバレッジ足場整備が必要。
- ビジネスロジック（handler/service/repository）の大半が未テスト。

### developer（support）

- パッケージはレイヤ分割（config/handler/service/repository/model）、命名は Java 慣習準拠。
- 公開クラスに JavaDoc あり（NIP-01 仕様参照付き）。→ Code Style のドキュメント方針の裏付け。
- テストパッケージ `io.github.service` が本体名前空間とずれ（軽微な負債）。

### devsecops（support）

- リンタ/フォーマッタ設定ファイルは未検出（Prettier は IDE 拡張のみ）。SAST/DAST・シークレットスキャン・依存スキャンは Dependabot のみ。
- 秘密情報の取り扱い規約（環境変数管理）は steering に明示。

## Interview Decisions

- **Q1（カバレッジ適用時期）= A**: JaCoCo と CI を足場として整え、段階的に80%へ引き上げる。既存2件は緑維持。→ quality の OBJECT（JaCoCo 未明記）を解消。
- **Q2（Prettier 適用範囲）= B**: Java 用フォーマッタ（Spotless + google-java-format 等）を今回のスコープで導入し、CI で整形を強制する。Prettier は設定・ドキュメント系に限定。→ developer の OBJECT（Prettier が Java 非適用）を「フォーマッタ導入」で解消。現状のインデント混在（2/4スペース）も統一する。
- **Q3（Walking Skeleton）= A**: 不要。既存実装があるため通常作業として進める（scope `skeleton: off` と整合）。

## Unresolved Uncertainty

None.（Q2=B の決定により、Java フォーマッタの扱いは確定。具体的なツール選定と既存コードの一括整形は code-generation / ci-pipeline ステージで実施する。）
