//CV laden, Sektionen befüllen

// ── Zustandsvariable ──────────────────────────────────────
// Hier wird das geladene CV gespeichert.
// Alle render-Funktionen lesen aus diesem Objekt.
let cvData = null;

// ── Auth-Check ────────────────────────────────────────────
// Kein Token → zurück zum Login
const token = localStorage.getItem("token");
if (!token) window.location.href = "/index.html";

// Username in der Navbar anzeigen
document.getElementById("usernameDisplay").textContent =
    localStorage.getItem("username") || "";

// ── Navigation ────────────────────────────────────────────
// Blendet die gewählte Sektion ein, alle anderen aus.
// Wird von den nav-items in der Sidebar aufgerufen.
function showSection(name) {
    document.querySelectorAll(".section").forEach(s => s.classList.remove("active"));
    document.querySelectorAll(".nav-item").forEach(n => n.classList.remove("active"));

    document.getElementById("section-" + name).classList.add("active");
    // Passendes Nav-Item markieren
    document.querySelectorAll(".nav-item").forEach(n => {
        if (n.getAttribute("onclick").includes(name)) n.classList.add("active");
    });
}

// ── Logout ───────────────────────────────────────────────
// Token löschen, zur Login-Seite weiterleiten
function logout() {
    localStorage.removeItem("token");
    localStorage.removeItem("username");
    window.location.href = "/index.html";
}


// ── CV laden ─────────────────────────────────────────────
// GET /api/cv
// Wird einmalig beim Laden der Seite aufgerufen.
// Befüllt cvData und ruft alle render-Funktionen auf.
async function loadCv() {
    try {
        const response = await fetch("/api/cv", {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
                "Authorization": "Bearer " + token
            }
        });

        if (response.ok) {
            cvData = await response.json();
            renderPersonalInfo(cvData.personals);
            renderList("experiences", cvData.experiences, formatExperience);
            renderList("educations", cvData.educations, formatEducation);
            renderList("skills", cvData.skills, formatSkill);
            renderList("certificates", cvData.certificates, formatCertificate);
            renderList("internships", cvData.internships, formatInternship);
            renderList("volunteers", cvData.volunteers, formatVolunteer);
            renderList("hobbies", cvData.hobbies, formatHobby);
            renderSignature(cvData.signature);
            
            const deleteCvBtn = document.getElementById("deleteCvBtn");
            if (cvData.personals !== null) {
                deleteCvBtn.disabled = false;
            }
        }

    } catch (err) {
        console.error("CV konnte nicht geladen werden:", err);
    } finally {
        // Loading-Overlay verstecken egal ob Erfolg oder Fehler
        document.getElementById("loadingOverlay").classList.add("hidden");
    }
}

// ── Render: Persönliche Daten ────────────────────────────
// Befüllt die Input-Felder mit den Werten aus dem DTO.
// Wird nach loadCv() aufgerufen.
function renderPersonalInfo(data) {
    if (!data) return;
    document.getElementById("firstname").value  = data.firstname  || "";
    document.getElementById("lastname").value   = data.lastname   || "";
    document.getElementById("email").value      = data.email      || "";
    document.getElementById("phone").value      = data.phone      || "";
    document.getElementById("street").value     = data.street     || "";
    document.getElementById("city").value       = data.city       || "";
    document.getElementById("zip").value        = data.zip        || "";
    document.getElementById("country").value    = data.country    || "";
    document.getElementById("birthDate").value  = data.birthDate  || "";
    document.getElementById("birthplace").value = data.birthplace || "";
    document.getElementById("summary").value    = data.summary    || "";
}

// ── Render: Listen (1:N) ─────────────────────────────────
// Generische Funktion für alle Listen-Sektionen.
// formatFn bestimmt wie ein einzelner Eintrag dargestellt wird.
function renderList(name, items, formatFn) {
    const list = document.getElementById("list-" + name);
    list.innerHTML = "";
    if (!items || items.length === 0) return;

    items.forEach(item => {
        const div = document.createElement("div");
        div.className = "entry-item";
        div.innerHTML = `
            <span>${formatFn(item)}</span>
            <div class="entry-actions">
                <button class="btn-icon" onclick="edit${capitalize(name)}('${item.id}')">Bearbeiten</button>
                <button class="btn-icon delete" onclick="deleteListing('${name}','${item.id}')">Löschen</button>
            </div>
        `;
        list.appendChild(div);
    });
}




// ── Format-Funktionen für Listen ─────────────────────────
// Jede gibt einen lesbaren String für die Listenansicht zurück.
// Werden von renderList() aufgerufen.
function formatExperience(e)  { return `${e.role || "-"} @ ${e.company || "-"} von: ${e.dateFrom || "-"} bis ${e.dateTo || "-"}`; }
function formatEducation(e)   { return `${e.degree || "-"} - ${e.institution || "-"} von: ${e.dateFrom || "-"} bis ${e.dateTo || "-"}`; }
function formatSkill(e)       { return `${e.name || "-"} (${e.category || "-"}), ${e.level}`; }
function formatCertificate(e) { return `${e.title || "-"} - ${e.issuer || "-"} am ${e.dateIssued}`; }
function formatInternship(e)  { return `${e.role || "-"} @ ${e.company || "-"} von: ${e.dateFrom || "-"} bis ${e.dateTo || "-"}`;}
function formatVolunteer(e)   { return `${e.role || "-"} @ ${e.organization || "-"} von: ${e.dateFrom || "-"} bis ${e.dateTo || "-"}`; }
function formatHobby(e)       { return e.name || "-"; }

// ── Render: Signatur ─────────────────────────────────────
function renderSignature(data) {
    if (!data) return;
    document.getElementById("sigCity").value = data.city || "";
    document.getElementById("sigDate").value = data.signatureDate || "";
}

// ── Speichern: Persönliche Daten ─────────────────────────
// POST /api/cv/personals
// Liest die Input-Felder aus und sendet sie als JSON.
async function savePersonalInfo() {
    const body = {
        firstname:  document.getElementById("firstname").value.trim(),
        lastname:   document.getElementById("lastname").value.trim(),
        email:      document.getElementById("email").value.trim(),
        phone:      document.getElementById("phone").value.trim(),
        street:     document.getElementById("street").value.trim(),
        city:       document.getElementById("city").value.trim(),
        zip:        document.getElementById("zip").value.trim(),
        country:    document.getElementById("country").value.trim(),
        birthDate:  document.getElementById("birthDate").value.trim(),
        birthplace: document.getElementById("birthplace").value.trim(),
        summary:    document.getElementById("summary").value.trim()
    };

    const response = await fetch("/api/cv/personals", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization": "Bearer " + token
        },
        body: JSON.stringify(body)
    });


    if (response.ok){
        document.getElementById("firstname").value = "";
        document.getElementById("lastname").value = "";
        document.getElementById("email").value = "";
        document.getElementById("phone").value = "";
        document.getElementById("street").value = "";
        document.getElementById("city").value = "";
        document.getElementById("zip").value = "";
        document.getElementById("country").value = "";
        document.getElementById("birthDate").value = "";
        document.getElementById("birthplace").value = "";
        document.getElementById("summary").value = "";
        await loadCv();
    };
}

// ── Experience hinzufügen ───────────────────────────────────
// POST /api/cv/experiences
async function showAddExperience() {
    const body = {
        company:        document.getElementById("exp-company").value.trim(),
        role:           document.getElementById("exp-role").value.trim(),
        dateFrom:       document.getElementById("exp-dateFrom").value.trim(),
        dateTo:         document.getElementById("exp-dateTo").value.trim(),
        description:    document.getElementById("exp-description").value.trim()

    };

    if (!body || body.length == 0) return;

    const response = await fetch("/api/cv/experiences",{
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization": "Bearer " + token
        },
        body: JSON.stringify(body)
    });

    if (response.ok){
        document.getElementById("exp-company").value="";
        document.getElementById("exp-role").value="";
        document.getElementById("exp-dateFrom").value="";
        document.getElementById("exp-dateTo").value="";
        document.getElementById("exp-description").value="";
        await loadCv();
    };
}

// ── Experiences : Listeneinträge editieren ─────────────────────────────────
function editExperiences(id) {
    const exp = cvData.experiences.find(e => e.id === id);
    if (!exp) return;

    // Felder befüllen damit der User sie sieht
    document.getElementById("exp-company").value     = exp.company     || "";
    document.getElementById("exp-role").value        = exp.role        || "";
    document.getElementById("exp-dateFrom").value    = exp.dateFrom    || "";
    document.getElementById("exp-dateTo").value      = exp.dateTo      || "";
    document.getElementById("exp-description").value = exp.description || "";

    // ID merken für den PUT Request
    const btn = document.querySelector("#section-experiences .form-actions .btn" );
    btn.dataset.editId = id;

    btn.textContent = "Aktualisieren";
    btn.onclick = () => updateExperiences(id);

    showSection("experiences");
}

// ── Experiences : Listeneinträge aktualisieren ─────────────────────────────
async function updateExperiences(id) {
    const btn = document.querySelector("#section-experiences .form-actions .btn");

    // Body erst jetzt lesen – der User hat die Felder eventuell geändert
    const body = {
        company:     document.getElementById("exp-company").value.trim(),
        role:        document.getElementById("exp-role").value.trim(),
        dateFrom:    document.getElementById("exp-dateFrom").value.trim(),
        dateTo:      document.getElementById("exp-dateTo").value.trim(),
        description: document.getElementById("exp-description").value.trim()
    };

    const response = await fetch(`/api/cv/experiences/${id}`, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json",
            "Authorization": "Bearer " + token
        },
        body: JSON.stringify(body)
    });

    if (response.ok) {
        document.getElementById("exp-company").value     = "";
        document.getElementById("exp-role").value        = "";
        document.getElementById("exp-dateFrom").value    = "";
        document.getElementById("exp-dateTo").value      = "";
        document.getElementById("exp-description").value = "";

        btn.textContent = "+ Eintrag hinzufügen";
        btn.onclick = () => showAddExperience();

        await loadCv();
    }
}

// ── Education hinzufügen ───────────────────────────────────
// POST /api/cv/educations
async function showAddEducation() {
    const body = {
        institution: document.getElementById("edu-institution").value.trim(),
        degree: document.getElementById("edu-degree").value.trim(),
        fieldOfStudy: document.getElementById("edu-fieldOfStudy").value.trim(),
        dateFrom: document.getElementById("edu-dateFrom").value.trim(),
        dateTo: document.getElementById("edu-dateTo").value.trim()
    };

    const response = await fetch("/api/cv/educations",{
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization": "Bearer " + token
        },
        body: JSON.stringify(body)
    });

    if (response.ok){
        document.getElementById("edu-institution").value="";
        document.getElementById("edu-degree").value="";
        document.getElementById("edu-fieldOfStudy").value="";
        document.getElementById("edu-dateFrom").value="";
        document.getElementById("edu-dateTo").value="";
        await loadCv();
    };   
}

// ── Zertifikate hinzufügen ───────────────────────────────────
// POST /api/cv/certificates
async function showAddCertificate() {
    const body = {
        title: document.getElementById("cert-title").value.trim(),
        issuer: document.getElementById("cert-issuer").value.trim(),
        dateIssued: document.getElementById("cert-dateIssued").value.trim()
    }

    const response = await fetch("/api/cv/certificates", {
        method: "POST",
        headers:{
            "Content-Type": "application/json",
            "Authorization": "Bearer " + token
        },
        body: JSON.stringify(body)
    });
    if (response.ok){
        document.getElementById("cert-title").value ="";
        document.getElementById("cert-issuer").value ="";
        document.getElementById("cert-dateIssued").value ="";
        await loadCv();    
    }
}

// ── Internships hinzufügen ───────────────────────────────────
// POST /api/cv/internships
async function showAddInternship() {
    const body = {
        company: document.getElementById("inter-company").value.trim(),
        role: document.getElementById("inter-role").value.trim(),
        dateFrom: document.getElementById("inter-dateFrom").value.trim(),
        dateTo: document.getElementById("inter-dateTo").value.trim(),
        description: document.getElementById("inter-description").value.trim(),
    }

    const response = await fetch("/api/cv/internships", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization": "Bearer " + token
        },
        body: JSON.stringify(body)
    })
    if(response.ok){
        document.getElementById("inter-company").value="";
        document.getElementById("inter-role").value="";
        document.getElementById("inter-dateFrom").value="";
        document.getElementById("inter-dateTo").value="";
        document.getElementById("inter-description").value="";
        await loadCv();
    }
}

// ── Volunteers hinzufügen ───────────────────────────────────
// POST /api/cv/volunteers
async function showAddVolunteer() {
    const body = {
        organization: document.getElementById("volu-organization").value.trim(),
        role: document.getElementById("volu-role").value.trim(),
        dateFrom: document.getElementById("volu-dateFrom").value.trim(),
        dateTo: document.getElementById("volu-dateTo").value.trim(),
        description: document.getElementById("volu-description").value.trim()
    }
    
    const response = await fetch("/api/cv/volunteers", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization": "Bearer " + token
        },
        body: JSON.stringify(body)
    })

    if (response.ok){
        document.getElementById("volu-organization").value ="";
        document.getElementById("volu-role").value ="";
        document.getElementById("volu-dateFrom").value ="";
        document.getElementById("volu-dateTo").value ="";
        document.getElementById("volu-description").value ="";
        await loadCv();
    }
}

// ── Skill hinzufügen ───────────────────────────────────
// POST /api/cv/skills
async function showAddSkill() {
    const body = {
        name: document.getElementById("skillName").value.trim(),
        category: document.getElementById("skillCat").value.trim(),
        level: document.getElementById("skillLevel").value.trim()
    }

    const response = await fetch("/api/cv/skills",{
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization": "Bearer "+token
        },
        body: JSON.stringify(body)
    });
    if (response.ok){
        document.getElementById("skillName").value = "";
        document.getElementById("skillCat").value="";
        document.getElementById("skillLevel").value="";
        await loadCv();
    };
}


// ── Hobby hinzufügen ─────────────────────────────────────
// POST /api/cv/hobbies
// Als Beispiel-Implementierung - die anderen folgen demselben Muster
async function addHobby() {
    const name = document.getElementById("hobbyName").value.trim();
    if (!name) return;

    const response = await fetch("/api/cv/hobbies", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization": "Bearer " + token
        },
        body: JSON.stringify({ name })
    });

    if (response.ok) {
        document.getElementById("hobbyName").value = "";
        await loadCv(); // CV neu laden damit die Liste aktuell ist
    };
}



// ── Speichern: Signatur ───────────────────────────────────
// POST /api/cv/signature
// async function saveSignature() {
//     const fileInput = document.getElementById("sigPath");
//     const file = fileInput.files[0]; // die tatsächliche Datei

//     const formData = new FormData();
//     formData.append("signaturePath", file);
//     formData.append("city", document.getElementById("sigCity").value);
//     formData.append("signatureDate", document.getElementById("sigDate").value);

//     const response = await fetch("/api/cv/signature", {
//         method: "POST",
//         headers: {
//             // KEIN Content-Type hier – Browser setzt es automatisch mit Boundary
//             "Authorization": "Bearer " + token
//         },
//         body: formData  // kein JSON.stringify()
//     });
// }

async function saveSignature() {
    // Nur Stadt und Datum – POST /api/cv/signature mit JSON
    const body = {
        city:          document.getElementById("sigCity").value,
        signatureDate: document.getElementById("sigDate").value
    };

    const response = await fetch("/api/cv/signature", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization": "Bearer " + token
        },
        body: JSON.stringify(body)
    });

    if (response.ok) await loadCv();
}

async function uploadSignatureFile() {
    // Nur Datei – POST /api/cv/signature/upload mit FormData
    const fileInput = document.getElementById("sig-file");
    if (!fileInput || !fileInput.files[0]) return;

    const formData = new FormData();
    formData.append("file", fileInput.files[0]);

    const response = await fetch("/api/cv/signature/upload", {
        method: "POST",
        headers: {
            "Authorization": "Bearer " + token
        },
        body: formData
    });

    if (response.ok) await loadCv();
}


// ── Export PDF Funktion ───────────────────────────────────
// GET /api/cv/pdfExport
async function exportPdf() {
    
    const response = await fetch("/api/cv/pdfexport", {
        method: "GET",
        headers:{
            "Authorization": "Bearer " + token
        }
    })
    
    if (response.ok){
        // response.blob() wandelt die Antwort in eine Binärdatei um
        const blob = await response.blob();
        // URL für den Blob erstellen – damit kann der Browser die Datei öffnen
        const url = URL.createObjectURL(blob);
        // unsichtbaren Link erzeugen und klicken – löst den Download aus
        const a = document.createElement("a");
        a.href = url;
        a.download = "lebenslauf.pdf";
        a.click();
        // Speicher freigeben
        URL.revokeObjectURL(url);
    }
}

// Delete element in list  
async function deleteListing(name,id){
    const response = await fetch(`/api/cv/${name}/${id}`,{
        method: "DELETE",
        headers:{
            "Authorization": "Bearer " + token
        }
    })

    if(response.ok){
        await loadCv();
    }
}

async function deleteCv(id){
    const response = await fetch (`/api/cv/${id}`,{
        method: "DELETE",
        headers:{
            "Authorization": "Bearer " + token
        }
    })

    if (response.ok){
        await loadCv();
    }
}

// ── Platzhalter für noch nicht implementierte Methoden ───
// Diese Schritt für Schritt in cv.js ausbauen
function editEducations(id)   { console.log("editEducation - TODO", id); }
function editSkills(id)       { console.log("editSkill - TODO", id); }
function editCertificates(id) { console.log("editCertificate - TODO", id); }
function editInternships(id)  { console.log("editInternship - TODO", id); }
function editVolunteers(id)   { console.log("editVolunteer - TODO", id); }
function editHobbies(id)      { console.log("editHobby - TODO", id); }


// ── Hilfsfunktion ─────────────────────────────────────────
function capitalize(str) { return str.charAt(0).toUpperCase() + str.slice(1); }

// ── Start ─────────────────────────────────────────────────
// Einmalig beim Laden der Seite aufrufen
loadCv();