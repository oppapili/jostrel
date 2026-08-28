# Reverse Engineering Timestamp — Jostrel

- **Date**: 2026-08-28T15:03:25Z
- **Commit**: ad162d8efb02bed3ca7a98073f9afaa26f71a478
- **Scan kind**: Full rescan（初回スキャン、リポジトリ全体を対象）
- **Repo**: jostrel

## Scope of Analysis

```yaml
scope_version: 1
kind: full
intent: 260828-repo-aidlc-init
fingerprint: 07d8b854cf18f2ad30c5c593f5a14ffd09b1e051
analyzed:
  paths:
    - ./
  components:
    - WebSocketConfig
    - JacksonConfig
    - WebSocketHandler
    - EventService
    - SubscriptionManager
    - EventRepository
    - model
    - JostrelApplication
shallow:
  paths:
    - .mvn/
    - .devcontainer/
    - .github/
```
