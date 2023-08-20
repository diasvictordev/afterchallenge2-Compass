package com.challenge2.challenge2.entities;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResponse {
    public String message;
    public Timestamp timestamp;
    public String httpStatus;
}
