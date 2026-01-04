# Deterministic Replay Engine

A deterministic replay framework for event-driven systems, designed to record concurrent event streams and replay them in a reproducible, verifiable manner.  
The project focuses on correctness, concurrency control, and clean system architecture.

## Overview

The engine ingests events from multiple concurrent producers, records them in a canonical order, and allows deterministic replay to reconstruct system state exactly as it occurred during recording.

This enables:
- Reliable debugging
- State verification
- Deterministic testing of event-driven systems

## Architecture

````
EventSource (N threads)
↓
ProducerQueue (lock-free)
↓
Recorder (single writer)
↓
EventLog (immutable)
↓
Replayer (strategy-driven)
↓
StateSnapshot
````

Key design choice: **all state mutation and event ordering are handled by a single recorder thread**, ensuring determinism while allowing concurrent event production.

## Technologies & Concepts

- Java
- Concurrency (multi-producer / single-consumer)
- Lock-free data structures (`ConcurrentLinkedQueue`)
- Event sourcing
- Deterministic replay
- Strategy pattern
- Immutable state snapshots

## How to Run

```
mvn clean package
````
Run Main.main() to start the application:

- Starts multiple event producers

- Records events deterministically

- Replays the event log

- Verifies that the final replayed state matches the original recorded state