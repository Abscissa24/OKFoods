# Changelog

All notable changes to the OK Foods Android app are documented here.

## [1.0.0] — 2026-11-18

### First official release of the standalone OK Foods app

**Ordering & real-time order tracking**
- Pizza menu with cart, checkout and loyalty card.
- Live order status: the app subscribes to your own `orders` in the OK Foods backend with a realtime listener and updates the order view and timeline immediately — no manual refresh. Status changes made by staff in the orders dashboard (Received → Preparing → Ready → Completed) appear within seconds.
- Automatic reconnection and backoff with an in-app notice and tap-to-retry if the realtime stream is unavailable.
- Push notifications (FCM) on order status changes; identical behaviour in foreground, background and killed states.

**Catalog / specials**
- Weekly leaflet specials rendered in a full-screen carousel straight from the official OK Foods leaflet PDF.
- Image specials remain supported via the manifest fallback.

**Reliability & footprint**
- Arm64-only build, hardened release profile.
- Firebase sync, auth and Firestore rules re-verified; composite index `orders(userId, createdAt)` deployed.
- Minimum permission set — no storage, camera, contacts, or location permissions.
- Internal installer pipeline (dropper-pattern) removed; Play Protect "unrecognised app" notice is expected for sideloaded installs and can be disputed/cleared via the Play Protect review flow if shown.

### Security
- This repository contains **no source code, credentials or signing materials**. It distributes the compiled, signed release build only.
- Signing certificate SHA-1 `62:FA:D7:17:AC:8F:3F:05:E9:7C:B4:E0:6E:0E:3A:82:61:08:43:CE` (unchanged; it is the certificate registered in the OK Foods Firebase project).

### Notes
- Play Protect may still flag the sideloaded APK on first install until Google's reputation system re-evaluates it. This is not an indication of malware (see README § Verifying integrity).
- If you installed an earlier 1.0.0 build, uninstall it and install `OKFoods-v1.0.0-arm64-release.apk` from this repository: it is the only signed, verified distribution.
