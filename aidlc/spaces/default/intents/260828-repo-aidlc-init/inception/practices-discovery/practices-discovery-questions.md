# Practices Discovery Questions

証拠（git履歴・既存コード・steering）から大半の実践は確立できました。確定しきれない点だけ確認します。

## Q1. テストのカバレッジ目標はいつから適用しますか

ビジネスロジック80%というカバレッジ目標は steering にありますが、現状テストは2件でカバレッジ計測ツール（JaCoCo）も未導入です。

- A. まずカバレッジ計測（JaCoCo）と CI を足場として整え、その後に段階的に80%へ引き上げる（既存2件は緑維持）
- B. 今回のスコープで即座に全域80%を達成する
- C. カバレッジ目標は当面設けない（既存テストの緑維持のみ）
- X. Other (please specify)

[Answer]: A

## Q2. コードスタイルの「Prettier / 2スペース」の適用範囲をどう扱いますか

Prettier は Java コードには直接適用されません。既存の Java コードは Java 言語慣習に従っています。

- A. Prettier/2スペースは設定・ドキュメント系ファイル向けとし、Java コードは Java 慣習（＋将来必要なら Spotless 等を検討）とする
- B. Java にもフォーマッタ（Spotless / google-java-format 等）を今回のスコープで導入する
- C. 当面フォーマッタ強制はせず、既存の書き方を踏襲する
- X. Other (please specify)

[Answer]: B

## Q3. Walking Skeleton（薄いエンドツーエンドの骨組みを先に作る進め方）は不要という理解でよいですか

既存の Nostr リレーが動作しているため、骨組みを新規に作る必要はないと判断しています。

- A. 不要（既存実装があるため、通常の作業として進める）
- B. 必要（別途骨組みを作りたい理由がある）
- X. Other (please specify)

[Answer]: A

## Consolidated Summary Confirmation

発見・整理した実践の要約は以下のとおりです。内容が正しいか確認してください。

- Way of Working: trunk-based（main 単一）、Conventional Commits、commit/push はユーザー確認後。
- Walking Skeleton: 不要（既存実装あり、通常作業として進める）。
- Testing Posture: test-after。JaCoCo + CI を足場に段階的カバレッジ（目標80%）。既存テストは緑維持。
- Deployment: 現状デプロイ未整備（本スコープ外）。将来は Docker 優先。CI 整備を先行。
- Code Style: Java は Spotless + google-java-format 等で整形し CI 強制（インデント混在を統一）。Prettier は設定/ドキュメント系に限定。JavaDoc 付与。
- Mandated/Forbidden: commit/push はユーザー確認、Conventional Commits、公開関数に JavaDoc、秘密情報は環境変数、SQL パラメータ化（秘密コミット禁止・文字列連結禁止）。

- Looks correct
- Request changes
- Other (please specify)

[Answer]: Looks correct
