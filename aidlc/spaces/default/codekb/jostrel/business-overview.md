# Business Overview — Jostrel

## Business Domain

Jostrel は [Nostr](https://github.com/nostr-protocol/nostr) プロトコルの **リレー** 実装である。Nostr（Notes and Other Stuff Transmitted by Relays）は分散型のソーシャルネットワーク/通信プロトコルで、リレーはクライアント間でイベント（投稿等）を保管・転送するサーバーの役割を担う。

## Purpose

Java / Spring Boot でリレーサーバーを提供し、Nostr クライアントからの WebSocket 接続を受け付けて、[NIP-01](https://github.com/nostr-protocol/nips/blob/master/01.md) に基づくイベントの保管・購読・転送を行う。内部ツールとして単独開発者が運用/開発する。

## Key Functionality

- WebSocket 接続の受け付け（エンドポイント `/`）。
- `EVENT` メッセージの受信とイベントの保存。
- `REQ` メッセージによる購読と、一致イベントの返却および `EOSE`（保存済みイベント終端）の送信。
- `CLOSE` メッセージによる購読解除と `CLOSED` の送信。
- セッション単位の購読管理。

## Current Maturity（現状の成熟度）

NIP-01 のメッセージ往復は動作するが、リレーとして本質的な以下の機能は未実装または簡易実装である（`code-quality-assessment.md` 参照）。

- フィルタによるイベント絞り込み（未実装。`REQ` に対し全イベントを返す）。
- イベントの永続化（インメモリのみ。再起動で消失）。
- イベント署名の検証（未実装）。

## License

MIT License（`oppapili`）。
