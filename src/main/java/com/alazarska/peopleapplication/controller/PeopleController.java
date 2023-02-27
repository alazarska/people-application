package com.alazarska.peopleapplication.controller;

import com.alazarska.peopleapplication.converter.PersonConverter;
import com.alazarska.peopleapplication.dto.PersonForm;
import com.alazarska.peopleapplication.dto.PersonView;
import com.alazarska.peopleapplication.service.PeopleService;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/people")
@Log4j2
public class PeopleController {

    public static final String DISPO = """
             attachment; filename="%s"
            """;
    private final PersonConverter personConverter;
    private final PeopleService peopleService;

    public PeopleController(PersonConverter personConverter, PeopleService peopleService) {
        this.personConverter = personConverter;
        this.peopleService = peopleService;
    }

    @SneakyThrows
    @GetMapping
    public String showPeoplePage(Model model) {
        addRequiredPeoplePageAttributes(model);
        return "people-page";
    }

    @GetMapping("/{id}")
    public String showPersonPage(@PathVariable Long id, Model model) {
        Optional<PersonView> foundPerson = peopleService.findPersonById(id);
        if (foundPerson.isPresent()) {
            model.addAttribute("person", foundPerson.get());
            return "person-page";
        } else {
            return "not-found-page";
        }
    }

    @GetMapping("/save")
    public String showSavePersonPage(Model model) {
        addRequiredPeoplePageAttributes(model);
        return "save-person-page";
    }

    @PostMapping("/save")
    public String savePerson(@Valid PersonForm personForm, Errors errors, @RequestParam("photoFileName") MultipartFile photoFile) {
        if (!errors.hasErrors()) {
            PersonView savedPerson = peopleService.savePerson(personForm, photoFile);
            return "redirect:/people/" + savedPerson.getId();
        }
        return "save-person-page";
    }

    @GetMapping("/images/{fileName}")
    public ResponseEntity<Resource> getImage(@PathVariable String fileName) {
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, String.format(DISPO, fileName))
                .body(peopleService.findPersonImageByFileName(fileName));
    }

    @GetMapping("/{id}/update")
    public String showUpdatePersonPage(@PathVariable Long id, Model model) {
        Optional<PersonView> optionalPerson = peopleService.findPersonById(id);

        if (optionalPerson.isPresent()) {
            PersonView person = optionalPerson.get();
            PersonForm personForm = new PersonForm();
            personConverter.setPersonFormProperties(person, personForm);
            model.addAttribute("person", person);
            model.addAttribute("personForm", personForm);
            return "save-person-page";
        } else {
            return "not-found-page";
        }
    }

    @PostMapping("/{id}/update")
    public String updatePerson(@PathVariable Long id, @Valid PersonForm personForm, Errors errors, @RequestParam("photoFileName") MultipartFile photoFile) {
        Optional<PersonView> optionalPerson = peopleService.findPersonById(id);

        if (optionalPerson.isPresent()) {
            if (!errors.hasErrors()) {
                PersonView savedPerson = peopleService.updatePerson(id, personForm, photoFile);
                return "redirect:/people/" + savedPerson.getId();
            } else {
                return "save-person-page";
            }
        } else {
            return "not-found-page";
        }
    }

    @PostMapping("/{id}/delete")
    public String deletePerson(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Optional<PersonView> optionalPerson = peopleService.findPersonById(id);

        if (optionalPerson.isPresent()) {
            peopleService.deletePerson(id);
            redirectAttributes.addFlashAttribute("deleteAlert", "Selected person has been removed from database.");
            return "redirect:/people";
        } else {
            return "not-found-page";
        }
    }

    private void addRequiredPeoplePageAttributes(Model model) {
        if (!model.containsAttribute("people")) {
            model.addAttribute("people", peopleService.findAllPeople());
        }
        if (!model.containsAttribute("person")) {
            model.addAttribute("person", new PersonView());
        }
        if (!model.containsAttribute("personForm")) {
            model.addAttribute("personForm", new PersonForm());
        }
    }
}
