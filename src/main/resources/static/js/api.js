// zentrale fetch() Funktion mit JWT Header


// Basispfad für alle API-Aufrufe.
// So musst du nicht bei jedem fetch() die volle URL tippen.
// Aus '/cv' wird automatisch '/api/cv'
const API_BASE = '/api';

// async bedeutet: diese Funktion arbeitet asynchron.
// Das brauchen wir weil HTTP-Requests Zeit brauchen -
// der Browser soll nicht einfrieren während er auf die Antwort wartet.
//
// Parameter:
//   endpoint  → z.B. '/cv' oder '/cv/hobbies'
//   method    → GET, POST, PUT, DELETE - Standard ist GET
//   body      → die Daten die wir mitsenden wollen, Standard ist null
async function apiFetch(endpoint, method = 'GET', body = null) {

    // Token aus dem Browser-Speicher holen.
    // Nach dem Login speichern wir den JWT dort - beim nächsten
    // Aufruf holen wir ihn hier wieder heraus.
    const token = localStorage.getItem('token');
//    console.log(token);

    // Jeder HTTP-Request braucht Header - Metainformationen über den Request.
    // Content-Type: application/json sagt dem Backend:
    // "Ich schicke dir JSON, nicht HTML oder Formulardaten"
    const headers = { 'Content-Type': 'application/json' };

    // Wenn ein Token vorhanden ist, hängen wir ihn an den Header.
    // Das ist die Authentifizierung - ohne diesen Header gibt Spring Security
    // einen 403 zurück weil es nicht weiß wer der User ist.
    // Format ist immer: "Bearer <token>" - das erwartet dein JwtAuthenticationFilter
    if (token) headers['Authorization'] = `Bearer ${token}`;

    // Der eigentliche HTTP-Request.
    // await bedeutet: warte hier bis die Antwort kommt, dann mach weiter.
    // Ohne await würde die Funktion sofort weiterlaufen bevor die Antwort da ist.
    const response = await fetch(API_BASE + endpoint, {
        method,       // GET, POST, PUT oder DELETE
        headers,      // Content-Type + Authorization
        // body nur mitsenden wenn Daten vorhanden sind (POST/PUT).
        // JSON.stringify wandelt ein JavaScript-Objekt in einen JSON-String um
        // weil HTTP nur Text übertragen kann, kein JS-Objekt.
        // Bei GET ist body null - GET-Requests haben keinen Body.
        body: body ? JSON.stringify(body) : null
    });

    // 401 bedeutet "Unauthorized" - der Token ist abgelaufen oder ungültig.
    // In dem Fall räumen wir den alten Token weg und schicken den User
    // zurück zur Login-Seite. Sonst würde er im Dashboard feststecken
    // und bei jedem Request 401 bekommen ohne zu wissen warum.
    if (response.status === 401) {
        localStorage.removeItem('token');
        window.location.href = '/index.html';
    }

    // Die Response zurückgeben damit der Aufrufer die Daten verarbeiten kann.
    // Beispiel: const data = await apiFetch('/cv').then(r => r.json())
    return response;
}