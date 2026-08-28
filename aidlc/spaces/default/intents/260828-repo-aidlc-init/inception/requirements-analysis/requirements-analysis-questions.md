# Requirements Analysis Questions

方針は「基盤整備に絞る」で確定済み（中核負債は要件化のみ、実装は別イニシアチブで issue 化）。残りの具体を確認します。

## Q1. CI（GitHub Actions）で実行する内容はどこまでにしますか

- A. ビルド（`mvn package`）+ テスト（`mvn test`）+ フォーマットチェック（Spotless）+ カバレッジ計測（JaCoCo レポート生成）
- B. A に加えて、カバレッジがしきい値を下回ったら fail させる（ゲート化）
- C. ビルド + テストのみ（フォーマット/カバレッジは後回し）
- X. Other (please specify)

[Answer]: A

## Q2. Java フォーマッタの適用範囲をどうしますか

- A. Spotless + google-java-format を導入し、既存 Java コード全体を一括整形（インデント混在を解消）。以後 CI でチェック
- B. フォーマッタ設定と CI チェックのみ導入し、既存コードの一括整形は行わない（差分を小さく保つ）
- X. Other (please specify)

[Answer]: A

## Q3. 中核負債（フィルタ/永続化/署名検証）の扱いを確認します

- A. 今回は requirements.md に「Out of scope（別イニシアチブで対応）」として記録し、GitHub issue 化を提案する
- B. requirements.md に記録するのみ（issue 化はしない）
- X. Other (please specify)

[Answer]: A

## Q4. カバレッジのしきい値（将来の目標値）をどう記載しますか

- A. ビジネスロジック80%を「将来目標」として記載（今回は計測の足場のみ、ゲート化はしない）
- B. 具体的な数値目標は今回設けず、計測できる状態にするだけ
- X. Other (please specify)

[Answer]: A

## Consolidated Summary Confirmation

生成する requirements.md の内容は以下の方針でまとめます。requirements 生成前に確認してください。

- 目的: 既存 Jostrel を AI-DLC で継続開発するための開発基盤整備（機能追加ではない）。
- 機能要件（基盤）: JaCoCo によるカバレッジ計測、Spotless + google-java-format による Java コード整形（既存コード一括整形含む）、GitHub Actions CI（build + test + フォーマットチェック + カバレッジレポート）。
- 非機能要件: CI は PR/push で実行。カバレッジ80%は将来目標として記載（今回ゲート化しない）。
- Out of scope: 中核負債（フィルタ実装 / イベント永続化 / 署名検証）は今回実装せず記録のみ。GitHub issue 化を提案する。
- 制約: Java 21 / Spring Boot 3.5.0 / Maven を維持。trunk-based、Conventional Commits。

Does this all look correct before I generate the requirements artifact?

- Looks correct
- Request changes

[Answer]: Looks correct
