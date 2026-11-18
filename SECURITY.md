# Security

## Reporting a vulnerability

Please **do not** file public issues for security vulnerabilities.

Report them privately to **Abscissa24** (the maintainer) via GitHub's private vulnerability reporting on this repository, or by email if you have it.

Include:

- The app version (Settings → About in the app, or the installer sheet) and Android version.
- Steps to reproduce, and whether the issue involves the app, the release build itself, or the distribution channel.
- Any logs or screenshots that help.

We aim to acknowledge reports within 7 days.

## Scope notes

- **Firebase configuration inside the APK** (project ID, web API key, OAuth client IDs, SHA-1/SHA-256 certificate fingerprints) is public by design and is **not** a credential. It is listed here to set expectations: their presence in the binary is not a vulnerability.
- The signing keystore/credentials are held privately and are never committed to any repository.
- Play Protect "unrecognised app" notices for sideloaded builds are distribution-channel artifacts, not findings of compromise.
- This repository contains the compiled build and its checksum only; it contains no secrets (see README).
