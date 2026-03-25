package com.example.cv_builderplatform.services;

import com.example.cv_builderplatform.dto.cv.CvResponseDTO;
import com.example.cv_builderplatform.entities.CvEntity;
import com.example.cv_builderplatform.mapper.*;
import com.example.cv_builderplatform.repositories.*;
import org.springframework.stereotype.Service;


@Service
public class CvLoadService {
    private final CvService cvService;
    private final CertificateRepository certRepo;
    private final EducationRepository educRepo;
    private final ExperienceRepository expeRepo;
    private final HobbyRepository hobbRepo;
    private final InternshipRepository inteRepo;
    private final PersonalInfoRepository persRepo;
    private final SignatureRepository signRepo;
    private final SkillRepository skilRepo;
    private final VolunteerRepository voluRepo;


    private final CertificateMapper certMapper;
    private final EducationMapper educMapper;
    private final ExperienceMapper expeMapper;
    private final HobbyMapper hobbMapper;
    private final InternshipMapper inteMapper;
    private final PersonalInfoMapper persMapper;
    private final SignatureMapper signMapper;
    private final SkillMapper skilMapper;
    private final VolunteerMapper voluMapper;

    public CvLoadService(
        CvService cvService,             CertificateRepository certRepo, 
        EducationRepository educRepo,    ExperienceRepository expeRepo, 
        HobbyRepository hobbRepo,        InternshipRepository inteRepo, 
        PersonalInfoRepository persRepo, SignatureRepository signRepo, 
        SkillRepository skilRepo,        VolunteerRepository voluRepo, 
        CertificateMapper certMapper,    EducationMapper educMapper, 
        ExperienceMapper expeMapper,     HobbyMapper hobbMapper, 
        InternshipMapper inteMapper,     PersonalInfoMapper persMapper, 
        SignatureMapper signMapper,      SkillMapper skilMapper, 
        VolunteerMapper voluMapper) {

                this.cvService = cvService;
                this.certRepo = certRepo;
                this.educRepo = educRepo;
                this.expeRepo = expeRepo;
                this.hobbRepo = hobbRepo;
                this.inteRepo = inteRepo;
                this.persRepo = persRepo;
                this.signRepo = signRepo;
                this.skilRepo = skilRepo;
                this.voluRepo = voluRepo;
                this.certMapper = certMapper;
                this.educMapper = educMapper;
                this.expeMapper = expeMapper;
                this.hobbMapper = hobbMapper;
                this.inteMapper = inteMapper;
                this.persMapper = persMapper;
                this.signMapper = signMapper;
                this.skilMapper = skilMapper;
                this.voluMapper = voluMapper;
    }

    public CvResponseDTO loadFullCv(String username){
        CvEntity cv = cvService.getOrCreateCvByUsername(username);
        CvResponseDTO dto = new CvResponseDTO();


        persRepo.findByCv(cv).map(persMapper::mapToDTO)
                .ifPresent(dto::setPersonals);

        signRepo.findByCv(cv).map(signMapper::mapToDTO)
                .ifPresent(dto::setSignature);

        dto.setCertificates(certRepo.findByCv(cv).stream()
                .map(certMapper::mapToDTO).toList());

        dto.setEducations(educRepo.findByCv(cv).stream()
                .map(educMapper::mapToDTO).toList());

        dto.setExperiences(expeRepo.findByCv(cv).stream()
                .map(expeMapper::mapToDTO).toList());

        dto.setHobbies(hobbRepo.findByCv(cv).stream()
                .map(hobbMapper::mapToDTO).toList());

        dto.setInternships(inteRepo.findByCv(cv).stream()
                .map(inteMapper::maptoDTO).toList());

        dto.setSkills(skilRepo.findByCv(cv).stream()
                .map(skilMapper::mapToDTO).toList());

        dto.setVolunteers(voluRepo.findByCv(cv).stream()
                .map(voluMapper::mapToDTO).toList());

        return dto;
    }
}
