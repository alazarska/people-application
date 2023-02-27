package com.alazarska.peopleapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonView {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate dob;
    private BigDecimal salary;
    private String photoFileName;
}
