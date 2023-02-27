package com.alazarska.peopleapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidationError {
    private String fieldName;
    private Object value;
    private String message;
}
