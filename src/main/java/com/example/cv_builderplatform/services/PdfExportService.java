package com.example.cv_builderplatform.services;

import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;
import org.springframework.stereotype.Service;

import com.example.cv_builderplatform.dto.cv.CertificateDTO;
import com.example.cv_builderplatform.dto.cv.CvResponseDTO;
import com.example.cv_builderplatform.dto.cv.EducationDTO;
import com.example.cv_builderplatform.dto.cv.ExperienceDTO;
import com.example.cv_builderplatform.dto.cv.HobbyDTO;
import com.example.cv_builderplatform.dto.cv.InternshipDTO;
import com.example.cv_builderplatform.dto.cv.PersonalInfoDTO;
import com.example.cv_builderplatform.dto.cv.SignatureDTO;
import com.example.cv_builderplatform.dto.cv.SkillDTO;
import com.example.cv_builderplatform.dto.cv.VolunteerDTO;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * Applying for a job with this resumee is not recommended yet. :)
 */


@Service
public class PdfExportService {

    // Fonts – BaseFont für Umlaut-Unterstützung
    private static Font fontName;
    private static Font fontSection;
    private static Font fontBody;
    private static Font fontMuted;

    static {
        try {
            BaseFont base = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.EMBEDDED);
            fontName    = new Font(base, 20, Font.BOLD);
            fontSection = new Font(base, 12, Font.BOLD);
            fontBody    = new Font(base, 10, Font.NORMAL);
            fontMuted   = new Font(base, 9,  Font.ITALIC);
        } catch (Exception e) {
            // Fallback auf Standard-Font
            fontName    = new Font(Font.FontFamily.HELVETICA, 20, Font.BOLD);
            fontSection = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
            fontBody    = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);
            fontMuted   = new Font(Font.FontFamily.HELVETICA,  9, Font.ITALIC);
        }
    }

    public byte[] exportCv(CvResponseDTO cvDto) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4, 56, 56, 56, 56);

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            // Persönliche Daten
            PersonalInfoDTO p = cvDto.getPersonals();
            if (p != null) {
                // Name groß und fett – ATS liest das als Hauptidentifikator
                document.add(new Paragraph(p.getFirstname() + " " + p.getLastname(), fontName));

                // Kontaktdaten in separaten Zeilen – keine Tabs
                // ATS parst zeilenweise – Tabs verwirren den Parser
                if (p.getEmail() != null)
                    document.add(new Paragraph("E-Mail: "+p.getEmail(), fontBody));
                if (p.getPhone() != null)
                    document.add(new Paragraph("Telefon: "+ p.getPhone(), fontBody));
                if (p.getStreet() != null)
                    document.add(new Paragraph("Adresse: "+
                            p.getStreet() + ", " + p.getZip() + " " + p.getCity(), fontBody));
                if (p.getBirthDate() != null)
                    document.add(new Paragraph(
                            "Geburtsdatum /-ort: " + p.getBirthDate() + ", " + p.getBirthplace(), fontBody));
                if (p.getSummary() != null && !p.getSummary().isBlank()) {
                    document.add(new Paragraph(" "));
                    document.add(new Paragraph(p.getSummary(), fontBody));
                }
            }

            // Berufserfahrung
            List<ExperienceDTO> exp = cvDto.getExperiences();
            if (exp != null && !exp.isEmpty()) {
                addSectionTitle(document, "Berufserfahrung");
                for (ExperienceDTO e : exp) {
                    // Rolle und Unternehmen in einer Zeile – ATS-Standard
                    document.add(new Paragraph("Position: "+e.getRole() + " – " + e.getCompany(), fontBody));
                    document.add(new Paragraph(
                            formatDateRange(e.getDateFrom(), e.getDateTo()), fontMuted));
                    if (e.getDescription() != null && !e.getDescription().isBlank())
                        document.add(new Paragraph(e.getDescription(), fontBody));
                    document.add(new Paragraph(" "));
                }
            }

            // Ausbildung
            List<EducationDTO> educations = cvDto.getEducations();
            if (educations != null && !educations.isEmpty()) {
                addSectionTitle(document, "Ausbildung");
                for (EducationDTO e : educations) {
                    document.add(new Paragraph("Abschluss: "+ e.getDegree() + " – " + e.getInstitution(), fontBody));
                    if (e.getFieldOfStudy() != null)
                        document.add(new Paragraph("Studienfach: "+ e.getFieldOfStudy(), fontBody));
                    document.add(new Paragraph(
                            formatDateRange("Vom "+e.getDateFrom()," bis "+ e.getDateTo()), fontMuted));
                    document.add(new Paragraph(" "));
                }
            }

            // Fähigkeiten – ATS sucht hier nach Keywords
            List<SkillDTO> skills = cvDto.getSkills();
            if (skills != null && !skills.isEmpty()) {
                addSectionTitle(document, "Faehigkeiten");
                for (SkillDTO s : skills) {
                    // Kein Sonderzeichen vor dem Skill – ATS parst Bindestriche manchmal als Trennzeichen
                    document.add(new Paragraph(
                            s.getName() + " | " + s.getCategory() + " | " + s.getLevel(), fontBody));
                }
                document.add(new Paragraph(" "));
            }

            // Zertifikate
            List<CertificateDTO> certs = cvDto.getCertificates();
            if (certs != null && !certs.isEmpty()) {
                addSectionTitle(document, "Zertifikate");
                for (CertificateDTO c : certs) {
                    document.add(new Paragraph(c.getTitle(), fontBody));
                    document.add(new Paragraph(
                            c.getIssuer() + ", " + c.getDateIssued(), fontMuted));
                    document.add(new Paragraph(" "));
                }
            }

            // Praktika
            List<InternshipDTO> internships = cvDto.getInternships();
            if (internships != null && !internships.isEmpty()) {
                addSectionTitle(document, "Praktika");
                for (InternshipDTO i : internships) {
                    document.add(new Paragraph(i.getRole() + " – " + i.getCompany(), fontBody));
                    document.add(new Paragraph(
                            formatDateRange(i.getDateFrom(), i.getDateTo()), fontMuted));
                    if (i.getDescription() != null && !i.getDescription().isBlank())
                        document.add(new Paragraph(i.getDescription(), fontBody));
                    document.add(new Paragraph(" "));
                }
            }

            // Ehrenamt
            List<VolunteerDTO> volunteers = cvDto.getVolunteers();
            if (volunteers != null && !volunteers.isEmpty()) {
                addSectionTitle(document, "Ehrenamtliches Engagement");
                for (VolunteerDTO v : volunteers) {
                    document.add(new Paragraph(v.getRole() + " – " + v.getOrganization(), fontBody));
                    document.add(new Paragraph(
                            formatDateRange(v.getDateFrom(), v.getDateTo()), fontMuted));
                    if (v.getDescription() != null && !v.getDescription().isBlank())
                        document.add(new Paragraph(v.getDescription(), fontBody));
                    document.add(new Paragraph(" "));
                }
            }

            // Hobbys – ATS liest das selten aber es schadet nicht
            List<HobbyDTO> hobbies = cvDto.getHobbies();
            if (hobbies != null && !hobbies.isEmpty()) {
                addSectionTitle(document, "Interessen");
                StringBuilder hobbyLine = new StringBuilder();
                for (int i = 0; i < hobbies.size(); i++) {
                    hobbyLine.append(hobbies.get(i).getName());
                    if (i < hobbies.size() - 1) hobbyLine.append(", ");
                }
                document.add(new Paragraph(hobbyLine.toString(), fontBody));
                document.add(new Paragraph(" "));
            }

            // Signatur
            SignatureDTO sig = cvDto.getSignature();
            if (sig != null && sig.getCity() != null) {
                document.add(new Paragraph(" "));
                document.add(new Paragraph(
                        sig.getCity() + ", " + sig.getSignatureDate(), fontBody));
            }

            document.close();
            return out.toByteArray();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new byte[0];
        }
    }

    // Sektionsüberschrift – fett, mit Linie darunter
    // ATS erkennt Sektionen über konsistente Formatierung
    private void addSectionTitle(Document doc, String title) throws Exception {
        doc.add(new Paragraph(" "));
        Paragraph p = new Paragraph(title.toUpperCase(), fontSection);
        p.setSpacingAfter(4);
        doc.add(p);
        doc.add(new LineSeparator());
        doc.add(new Paragraph(" "));
    }

    // Datumsbereich – null-sicher
    private String formatDateRange(Object from, Object to) {
        String f = from != null ? from.toString() : "–";
        String t = to   != null ? to.toString()   : "heute";
        return f + " – " + t;
    }
}
