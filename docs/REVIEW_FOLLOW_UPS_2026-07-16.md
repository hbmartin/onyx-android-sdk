# Review follow-ups (2026-07-16)

These items were intentionally not changed while addressing
`findings_20260716_100110.md`.

## Ink-renderer command bounds (PLAUSIBLE4)

`NativeDrawCommand.bounds` currently describes the generated geometry and does not outset path
bounds for half of a `FILL_AND_STROKE` paint's stroke width. There is no in-repository consumer
that uses these bounds as a partial-refresh or invalidation rectangle, so changing the public
geometry now could introduce an observable compatibility difference without fixing a demonstrated
bug.

Before using these bounds for refresh/invalidation, decide whether the contract is geometric or
painted extent. If it is painted extent, outset path bounds by `ceil(strokeWidth / 2)` and add
coverage for paths, point circles, bitmaps, joins, and caps.

## TouchHelper construction race (rejected)

No change was made for the reported `TouchHelper.setRawInputListenerV2` lazy-reader race. Review
showed that `TouchHelper` eagerly constructs and binds its renderers during construction, before a
caller can register the v2 listener, so the claimed unsynchronized lazy creation path does not
exist.

Revisit this conclusion if renderer creation or `bindHostView` becomes lazy, replaceable, or
callable concurrently with listener registration. Such a redesign should publish renderer state
under a single lock and include a registration-versus-rebind stress test.
