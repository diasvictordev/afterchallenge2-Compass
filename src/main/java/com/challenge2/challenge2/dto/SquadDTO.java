package com.challenge2.challenge2.dto;

import jakarta.persistence.Column;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SquadDTO {
    private String squadName;
    private List<Long> students;

}
