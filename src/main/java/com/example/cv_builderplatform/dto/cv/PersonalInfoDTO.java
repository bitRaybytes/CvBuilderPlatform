package com.example.cv_builderplatform.dto.cv;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PersonalInfoDTO {
    private UUID id;
    private String firstname;
    private String lastname;
    private String street;
    private String city;
    private String zip;
    private String country;
    private String phone;
    private String email;
    private LocalDate birthDate;
    private String birthplace;
    private String summary;
    private String profilePicturePath;


}
