package com.example.cv_builderplatform.dto.cv;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SkillDTO {
    private UUID id;
    private String name;
    private String category;  // könnte im SkillEntity als Enum or record sein??
    private String level;
}
