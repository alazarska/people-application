package com.alazarska.peopleapplication.controller;

import com.alazarska.peopleapplication.dto.PersonForm;
import com.alazarska.peopleapplication.dto.PersonView;
import com.alazarska.peopleapplication.dto.ValidationError;
import com.alazarska.peopleapplication.dto.ValidationErrors;
import com.alazarska.peopleapplication.service.PeopleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/people")
public class PeopleRestController {

    private final PeopleService peopleService;

    public PeopleRestController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @GetMapping
    public List<PersonView> getAllPeople() {
        return peopleService.findAllPeople();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonView> showPerson(@PathVariable Long id) {
        Optional<PersonView> optionalPerson = peopleService.findPersonById(id);

        return optionalPerson
                .map(personView -> ResponseEntity.status(HttpStatus.OK).body(personView))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<Object> createPerson(@Valid @RequestBody PersonForm personForm, Errors errors) {
        if (!errors.hasErrors()) {
            PersonView personView = peopleService.savePerson(personForm, new EmptyMultipartFile());
            return ResponseEntity.status(HttpStatus.OK).body(personView);
        } else {
            return errorsToResponseEntity(errors);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updatePerson(@PathVariable Long id, @Valid @RequestBody PersonForm personForm, Errors errors) {
        if (!errors.hasErrors()) {
            PersonView personView = peopleService.updatePerson(id, personForm, new EmptyMultipartFile());
            return ResponseEntity.status(HttpStatus.OK).body(personView);
        } else {
            return errorsToResponseEntity(errors);
        }
    }

    private static ResponseEntity<Object> errorsToResponseEntity(Errors errors) {
        List<ValidationError> validationErrorsList = errors.getFieldErrors()
                .stream()
                .map(it -> new ValidationError(it.getField(), it.getRejectedValue(), it.getDefaultMessage()))
                .toList();
        ValidationErrors validationErrors = new ValidationErrors(validationErrorsList);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationErrors);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deletePerson(@PathVariable Long id) {
        Optional<PersonView> optionalPerson = peopleService.findPersonById(id);

        if (optionalPerson.isPresent()) {
            peopleService.deletePerson(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
