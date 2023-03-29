package com.alazarska.peopleapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonForm {
    @NotEmpty(message = "First Name can not be empty")
    private String firstName;

    @NotEmpty(message = "Last Name can not be empty")
    private String lastName;

    @Email(message = "Email must be valid")
    @NotEmpty(message = "The email address is required")
    private String email;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past(message = "Date of birth must be in past")
    @NotNull(message = "Date of birth can not be empty")
    private LocalDate dob;

    @DecimalMin(value = "0.00", message = "Salary must be at least 0.00")
    @NotNull(message = "Salary can not be empty")
    private BigDecimal salary;
}
