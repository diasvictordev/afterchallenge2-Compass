package com.challenge2.challenge2.entities;

import com.challenge2.challenge2.enums.OrganizerEnums;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.BatchSize;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@SuperBuilder
public class Organizer extends User{


    @Column(name = "role")
    @Enumerated(value= EnumType.STRING)
    private OrganizerEnums role;
}
