# CV Builder API – Postman Collection

Diese Collection enthält alle Endpoints der CV Builder Platform API und dient als schneller Einstieg zum Testen der Anwendung ohne ein Frontend zu benötigen.

## Voraussetzungen

- Postman installiert
- Anwendung läuft lokal auf `http://localhost:8080`
- Docker Container gestartet mit `docker compose up --build`

## Reihenfolge

Die Requests sind in der richtigen Reihenfolge angelegt. Starte immer mit der Registrierung und dem Login – alle anderen Endpoints benötigen einen gültigen Token.

1. **Register** – Lege einen neuen User an. Username und E-Mail müssen einzigartig sein.
2. **Login** – Melde dich mit deinen Zugangsdaten an. Kopiere den accessToken aus der Antwort.
3. **API Requests** – Setze den Token im Authorization-Header aller weiteren Requests:

```text
Authorization: Bearer <dein_token>
```

## Hinweis

Der Token läuft nach 15 Minuten ab. Bei einem `401 Unauthorized` einfach erneut einloggen und den neuen Token setzen.
Ein vollständiges Beispiel-Payload für alle CV-Sektionen findest du in [docs/cv-example.json](/docs/cv-example.json).