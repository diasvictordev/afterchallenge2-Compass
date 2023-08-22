package com.challenge2.challenge2.dto;

import lombok.Builder;
import java.util.List;

@Builder
public record SquadDTO (String squadName, List<Long> students){}
