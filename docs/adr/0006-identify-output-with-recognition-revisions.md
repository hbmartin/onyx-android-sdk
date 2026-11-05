---
status: accepted
---

# Identify output with recognition revisions

Attach typed provenance and a canonical SHA-256 recognition revision to every result
and completed no-match attempt. The revision covers provider/runtime generation,
models, dictionaries, pre/postprocessing, transforms, routing, and actual compute
backend, but excludes input content, timing, and diagnostic IDs. Common result models
have no SDK persistence wire format; hosts may use the revision as a cache
invalidation key while mapping results into their own schemas. This prevents silent
model or pipeline changes from masquerading as equivalent output.
