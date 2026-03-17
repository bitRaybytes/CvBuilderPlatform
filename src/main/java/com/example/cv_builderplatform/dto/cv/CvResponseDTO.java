package com.example.cv_builderplatform.dto.cv;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CvResponseDTO {
    private String title;

    // einzelne Objekte, weil Relation 1:1
    private PersonalInfoDTO personals;
    private SignatureDTO signature;

    // Liste, weil Relation 1:N
    private List<ExperienceDTO> experiences;
    private List<CertificateDTO> certificates;
    private List<EducationDTO> educations;
    private List<HobbyDTO> hobbies;
    private List<InternshipDTO> internships;
    private List<SkillDTO> skills;
    private List<VolunteerDTO> volunteers;
}
