// Login, Register, Token speichern
// -------------------------------------------------------
// Basispfad - alle fetch() Calls werden relativ aufgelöst
// da Frontend und Backend auf demselben Server laufen
// -------------------------------------------------------
const API_BASE = '/api';

// -------------------------------------------------------
// Wenn der User bereits eingeloggt ist (Token vorhanden),
// direkt zum Dashboard weiterleiten
// -------------------------------------------------------
if (localStorage.getItem('token')) {
    window.location.href = '/dashboard.html';
}



// -------------------------------------------------------
// Tab-Wechsel zwischen Login und Registrierung
// -------------------------------------------------------
function switchTab(tab) {
    // Alle Tabs und Forms zurücksetzen
    document.querySelectorAll('.tab').forEach(t => t.classList.remove('active'));
    document.querySelectorAll('.form').forEach(f => f.classList.remove('active'));
//        console.log("Token: " + token);
    // Gewählten Tab und Form aktivieren
    if (tab === 'login') {
        document.querySelectorAll('.tab')[0].classList.add('active');
        document.getElementById('loginForm').classList.add('active');
    } else {
        document.querySelectorAll('.tab')[1].classList.add('active');
        document.getElementById('registerForm').classList.add('active');
    }

    // Fehlermeldungen leeren beim Tab-Wechsel
    clearMessage('loginMessage');
    clearMessage('registerMessage');
}

// -------------------------------------------------------
// Hilfsfunktionen für Feedback-Meldungen
// -------------------------------------------------------
function showMessage(id, text, type) {
    const el = document.getElementById(id);
    el.textContent = text;
    el.className = 'message ' + type;
}

function clearMessage(id) {
    const el = document.getElementById(id);
    el.textContent = '';
    el.className = 'message';
}

// Button während des Requests deaktivieren und Spinner zeigen
function setLoading(btnId, loading) {
    const btn = document.getElementById(btnId);
    btn.disabled = loading;
    btn.innerHTML = loading
        ? '<span class="spinner"></span> Bitte warten...'
        : btn.dataset.label;
}

// Label merken bevor erster Klick
document.getElementById('loginBtn').dataset.label = 'Anmelden';
document.getElementById('registerBtn').dataset.label = 'Konto erstellen';

// -------------------------------------------------------
// LOGIN
// POST /api/auth/login
// Erwartet: { username, password }
// Antwort:  { accessToken, tokenType, username }
// -------------------------------------------------------
async function handleLogin(event) {
    event.preventDefault(); // Verhindert den Standard-Submit (Seitenneuladen)
    clearMessage('loginMessage');
    setLoading('loginBtn', true);

    const username = document.getElementById('loginUsername').value.trim();
    const password = document.getElementById('loginPassword').value;

    try {
        const response = await fetch(API_BASE + '/auth/login', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            // JSON.stringify wandelt das JS-Objekt in einen String um
            // weil HTTP nur Text überträgt
            body: JSON.stringify({ username, password })
        });

        if (response.ok) {
            const data = await response.json();

            // Token im Browser-Speicher sichern.
            // Alle nachfolgenden Requests lesen ihn von hier.
            localStorage.setItem('token', data.accessToken);
            localStorage.setItem('username', data.username);

            // Weiterleitung zum Dashboard
            window.location.href = '/dashboard.html';

        } else if (response.status === 401) {
            showMessage('loginMessage', 'Benutzername oder Passwort falsch.', 'error');
        } else {
            showMessage('loginMessage', 'Ein Fehler ist aufgetreten. Bitte versuche es erneut.', 'error');
        }

    } catch (err) {
        // Netzwerkfehler - Backend nicht erreichbar
        showMessage('loginMessage', 'Server nicht erreichbar. Läuft das Backend?', 'error');
    } finally {
        // Button immer wieder freischalten, auch bei Fehler
        setLoading('loginBtn', false);
    }
}

// -------------------------------------------------------
// REGISTRIERUNG
// POST /api/auth/register
// Erwartet: { username, email, password }
// Antwort:  201 Created
// -------------------------------------------------------
async function handleRegister(event) {
    event.preventDefault();
    clearMessage('registerMessage');
    setLoading('registerBtn', true);

    const username = document.getElementById('regUsername').value.trim();
    const email    = document.getElementById('regEmail').value.trim();
    const password = document.getElementById('regPassword').value;

    try {
        const response = await fetch(API_BASE + '/auth/register', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ username, email, password })
        });

        if (response.status === 201 || response.ok) {
            // Erfolg - Tab zu Login wechseln und Hinweis zeigen
            switchTab('login');
            showMessage('loginMessage', 'Konto erstellt! Du kannst dich jetzt anmelden.', 'success');
            // Benutzername direkt ins Login-Feld übernehmen
            document.getElementById('loginUsername').value = username;

        } else if (response.status === 409) {
            showMessage('registerMessage', 'Benutzername oder E-Mail bereits vergeben.', 'error');
        } else {
            showMessage('registerMessage', 'Registrierung fehlgeschlagen. Bitte versuche es erneut.', 'error');
        }

    } catch (err) {
        showMessage('registerMessage', 'Server nicht erreichbar. Läuft das Backend?', 'error');
    } finally {
        setLoading('registerBtn', false);
    }
}