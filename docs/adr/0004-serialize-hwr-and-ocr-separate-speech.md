---
status: accepted
---

# Serialize HWR and OCR while isolating speech

Run HWR and OCR through one strict process-wide FIFO with one active call, eight
pending calls, and one reserved activation command. Queued cancellation removes
work; after native entry, cancellation or timeout returns immediately to the caller
while the lane remains occupied until the result can be discarded safely. Speech
uses an independent single-session lane and fails fast when busy. This favors
predictable lifecycle behavior for non-reentrant or non-interruptible engines over
parallel throughput or unsafe native preemption.
