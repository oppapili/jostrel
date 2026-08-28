# Discovered Rules — Jostrel

## Mandated

- ALWAYS commit と push はユーザーの確認を得てから実行する。
- ALWAYS コミットメッセージは Conventional Commits（`type(scope): description`）に従う。
- ALWAYS 公開関数にドキュメンテーション（JavaDoc）を付与する。
- ALWAYS 秘密情報は環境変数で管理する。

## Forbidden

- NEVER 秘密情報をコミットする。
- NEVER SQL クエリを文字列連結で組み立てる（パラメータ化する）。
