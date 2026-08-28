# Jostrel

Java (Spring Boot) で実装された [Nostr](https://github.com/nostr-protocol/nostr) リレーです。

Nostr は分散型のソーシャルネットワーク/通信プロトコルです。リレーはクライアント間でイベントを保管・転送するサーバーであり、Jostrel はその [NIP-01](https://github.com/nostr-protocol/nips/blob/master/01.md) ベースのリレー実装です。

## 特徴

- WebSocket ベースの Nostr プロトコル通信
- NIP-01 に基づくクライアント/リレー間メッセージの処理
- 購読管理とイベントのブロードキャスト

## 対応状況

現時点で実装済み/未実装の機能は次のとおりです。

| 機能 | 状態 | 備考 |
|---|---|---|
| WebSocket 接続 (`/`) | 実装済み | 全オリジン許可 (`*`) |
| `EVENT` メッセージ受信 | 実装済み | 受信イベントを保存 |
| `REQ` メッセージ (購読) | 実装済み | 一致イベント返却後に `EOSE` を送信 |
| `CLOSE` メッセージ (購読解除) | 実装済み | 解除後に `CLOSED` を送信 |
| イベントの永続化 | 未実装 | インメモリ保持のみ。再起動で消失する |
| フィルタによるイベント絞り込み | 未実装 | 現状はすべてのイベントに一致する |
| 署名検証 | 未実装 | イベントの `sig` は検証しない |

## 必要環境

- Java 21
- Maven (同梱の `mvnw` / `mvnw.cmd` ラッパーを利用可能)

## セットアップ

```bash
# リポジトリを取得
git clone https://github.com/oppapili/jostrel.git
cd jostrel

# ビルド
./mvnw clean package

# 起動
./mvnw spring-boot:run
```

起動すると WebSocket エンドポイントが `ws://localhost:8080/` で待ち受けます。

### 開発

```bash
# コンパイル
./mvnw clean compile

# テスト
./mvnw test

# テストをスキップしてパッケージング
./mvnw package -DskipTests
```

DevContainer (`.devcontainer/`) を利用すると、Java 21 + Maven を含む開発環境を再現できます。

## 使い方

Nostr クライアントから `ws://localhost:8080/` へ接続し、NIP-01 形式のメッセージを送信します。

```jsonc
// イベントを送信 (保存)
["EVENT", { "id": "...", "pubkey": "...", "created_at": 1700000000, "kind": 1, "tags": [], "content": "hello", "sig": "..." }]

// 購読を開始 (一致イベント + EOSE が返る)
["REQ", "sub-id", { "kinds": [1] }]

// 購読を終了 (CLOSED が返る)
["CLOSE", "sub-id"]
```

## ディレクトリ構成

```text
jostrel/
├── src/
│   ├── main/
│   │   ├── java/io/github/oppapili/jostrel/
│   │   │   ├── JostrelApplication.java   # アプリケーションエントリポイント
│   │   │   ├── config/                   # 設定クラス
│   │   │   │   ├── WebSocketConfig.java   # WebSocket エンドポイント設定
│   │   │   │   └── JacksonConfig.java     # JSON シリアライズ設定
│   │   │   ├── handler/                   # WebSocket ハンドラ
│   │   │   │   └── WebSocketHandler.java   # Nostr メッセージ処理
│   │   │   ├── model/                     # ドメインモデル / DTO
│   │   │   │   ├── Event.java              # Nostr イベント
│   │   │   │   ├── Filter.java             # イベントフィルタ
│   │   │   │   ├── Message.java            # メッセージ基底
│   │   │   │   ├── MessageType.java        # メッセージ種別
│   │   │   │   ├── MessageDeserializer.java # メッセージのデシリアライズ
│   │   │   │   ├── Subscription.java       # 購読
│   │   │   │   ├── EventMessage.java        # EVENT 応答メッセージ
│   │   │   │   ├── EoseMessage.java         # EOSE メッセージ
│   │   │   │   └── ClosedMessage.java       # CLOSED メッセージ
│   │   │   ├── repository/                 # 永続化層
│   │   │   │   └── EventRepository.java     # イベント保管 (インメモリ)
│   │   │   └── service/                    # ビジネスロジック
│   │   │       ├── EventService.java        # イベント保存 / 検索
│   │   │       └── SubscriptionManager.java # 購読管理
│   │   └── resources/
│   │       └── application.properties      # アプリケーション設定
│   └── test/                               # テストコード (main 構成をミラー)
├── pom.xml                                 # Maven プロジェクト設定
├── mvnw / mvnw.cmd                         # Maven ラッパー
└── README.md
```

## 技術スタック

- Spring Boot 3.5.0 (Spring WebSocket)
- Java 21
- Maven
- Lombok
- Jackson (JSON 処理)

## ライセンス

[MIT License](LICENSE.md) — © oppapili
