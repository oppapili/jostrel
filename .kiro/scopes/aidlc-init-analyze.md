---
name: aidlc-init-analyze
depth: Minimal
keywords: []
description: Analyze an existing repository and stand up its AI-DLC development baseline
skeleton: off
---

# aidlc-init-analyze scope

Composed for: "本リポジトリを解析して、AI-DLCでの開発環境を初期化して。"

Minimal depth for a brownfield initialization task — analyze an existing,
readable codebase and establish the baseline needed to develop it under
AI-DLC. It is not a new-product build: the ideation framing stages and the
heavy design phase are folded away, leaving analysis, practice capture,
requirements, and the implement/verify spine plus a CI foundation.

## Why these stages, why skip those

The work starts from code that already exists (a Nostr relay), so the
load-bearing stages are reverse-engineering (map the existing structure —
required because no external CodeKB index is available) and
practices-discovery (surface the conventions already embodied in the code
and tests). intent-capture pins the meaning of "initialize" and
requirements-analysis states the baseline as engineering requirements.
code-generation and build-and-test are the spine, and ci-pipeline is added
because the repository has no CI yet and standing one up is part of the
initialization intent.

Skipped: the ideation framing stages (market-research, feasibility,
scope-definition, team-formation, rough-mockups, approval-handoff) — an
internal tool with a clear, feasible task; the decomposition/design stages
(domain-design, units-generation, contract-design, delivery-planning,
functional-design) — a single, low-coupling package; the NFR and
infrastructure stages — out of scope for initialization; and the deployment
and operation stages (deployment-*, environment-provisioning,
observability-setup, incident-response, performance-validation,
feedback-optimization) — no deployment or operational surface in this task.

## Membership

Composed scope — resolves by `--scope aidlc-init-analyze` only, never by
keyword inference. Initialization, intent-capture, reverse-engineering,
practices-discovery, requirements-analysis, code-generation, build-and-test,
and ci-pipeline execute; the rest is SKIP.
