package com.example.cv_builderplatform.services;

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
 * Appliying for a job with this resumee is not recommended yet. :)
 */


@Service
public class PdfExportService {

    private static final String TABPIPE = "\t|\t";

    public byte[] exportCv(CvResponseDTO cvDto) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4, 40, 40, 50, 50);

        try {
        
            PdfWriter.getInstance(document, out);
            document.open();

            // Persönliche Daten hinzufügen
            PersonalInfoDTO p = cvDto.getPersonals();
            if (p != null) {    
            
                document.add(new Paragraph("Persönliche Informationen"));
                Paragraph name =    new Paragraph("Vor- und Zuname "+p.getFirstname() + " " + p.getLastname());
                Paragraph birth =   new Paragraph("Geburtstag: " + p.getBirthDate()+ TABPIPE + "Geburtsort: "+p.getBirthplace());
                Paragraph addres =  new Paragraph("Adresse: " + p.getStreet() + ", " + p.getCity() + " - "+p.getZip() + " "+p.getCountry());
                Paragraph kontakt = new Paragraph("E-Mail: " + p.getEmail() + TABPIPE + "Mobil: " + p.getPhone());
                Paragraph summary = new Paragraph("Zusammenfassung: " + p.getSummary());
                
                document.add(name); 
                document.add(birth);
                document.add(addres);
                document.add(kontakt);
                document.add(summary);
            }
            
            addNewLine(document);

            // Berufserfahrung hinzufügen

            List<ExperienceDTO> exp = cvDto.getExperiences();

            if (exp!= null && !exp.isEmpty()){
                document.add(new Paragraph("Berufserfahrung"));

                for(ExperienceDTO e : exp){
                    Paragraph company =     new Paragraph("Unternehmen: "+ e.getCompany() + TABPIPE +"Position: "+ e.getRole());
                    Paragraph dateFromTo =  new Paragraph("von " + e.getDateFrom() + " bis " +e.getDateTo());
                    Paragraph expSummary =  new Paragraph("Beschreibung: "+e.getDescription());

                    document.add(company);
                    document.add(dateFromTo);
                    document.add(expSummary);
                }
            }
            
            addNewLine(document);

            // Ausbildung hinzufügen
            List<EducationDTO> educations = cvDto.getEducations();
            if (educations !=null&&!educations.isEmpty()) {
                document.add(new Paragraph("Ausbildung"));
                for(EducationDTO edus : educations){
                    Paragraph eduInstitution =  new Paragraph("Insitution/Schule: " + edus.getInstitution() + TABPIPE + "Abschluss: "+ edus.getDegree());
                    Paragraph eduDates =        new Paragraph("Fallstudie: "+edus.getFieldOfStudy() + " vom: " + edus.getDateFrom() + " bis "+ edus.getDateTo());

                    document.add(eduInstitution);
                    document.add(eduDates);
                }
            }
            addNewLine(document);
            

            // Zertifikate hinzufügen
            List<CertificateDTO> certs = cvDto.getCertificates();
            if (certs !=null && !certs.isEmpty()) {
                document.add(new Paragraph("Zertifikate"));
                for (CertificateDTO cert : certs){
                    Paragraph info =        new Paragraph("Zertifikat: "+cert.getTitle());
                    Paragraph certInfo =    new Paragraph("ausgestellt von: " + cert.getIssuer()+" am " + cert.getDateIssued());

                    document.add(info);
                    document.add(certInfo);
                }
            }
            
            addNewLine(document);

            // Internships hinzufügen
            List<InternshipDTO> internships = cvDto.getInternships();
            if (internships !=null && !internships.isEmpty()) {
                document.add(new Paragraph("Praktika"));
                for(InternshipDTO i : internships){
                    Paragraph compRole = new Paragraph("Unternehmen: "+ i.getCompany() + TABPIPE+"Position: "+ i.getRole());
                    Paragraph iDates =   new Paragraph("vom: " + i.getDateFrom() + " bis "+ i.getDateTo());

                    document.add(compRole);
                    document.add(iDates);
                }
            }
            addNewLine(document);
            
            // Volunteers hinzufügen
            List<VolunteerDTO> volunteers = cvDto.getVolunteers();
            if (volunteers != null && !volunteers.isEmpty()) {
                document.add(new Paragraph("Ehrenamt"));
                for (VolunteerDTO v : volunteers){
                    Paragraph orga = new Paragraph("Organisation: " + v.getOrganization());
                    Paragraph date = new Paragraph("Rolle: "+v.getRole() +" vom " + v.getDateFrom() + " bis " + v.getDateTo());
                    Paragraph desc = new Paragraph("Beschreibung: " + v.getDescription());
    
                    document.add(orga);
                    document.add(date);
                    document.add(desc);
                }
            }

            addNewLine(document);

            // Skills hinzufügen
            List<SkillDTO> skills = cvDto.getSkills();
            if(skills !=null && !skills.isEmpty()){
                document.add(new Paragraph("Fähigkeiten"));
                for (SkillDTO s : skills){
                    Paragraph skill = new Paragraph("- " +s.getName() +", "+s.getCategory() + ", "+s.getLevel());
                    document.add(skill);
                }
            }
            

            // Hobbies hinzufügen
            List<HobbyDTO> hobbies = cvDto.getHobbies();
            if(hobbies !=null&&!hobbies.isEmpty()){
                document.add(new Paragraph("Hobbys"));
                for(HobbyDTO h : hobbies){
                    Paragraph hobby = new Paragraph("- " + h.getName());
                    document.add(hobby);
                }
            }
            

            // Signatur hinzufügen
            SignatureDTO sig = cvDto.getSignature();
            if (sig != null) {
                Paragraph sigStats = new Paragraph(sig.getCity()+", den "+sig.getSignatureDate());
                document.add(sigStats);
            }
            
            document.close();
            // Puffer als byte[] zurückgeben
            return out.toByteArray();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new byte[0];  // leeres Array statt null - sicherer
        }
    }

    private void addNewLine(Document doc) throws Exception{
        doc.add(new LineSeparator());
        doc.add(new Paragraph(" "));
    }
   
}
