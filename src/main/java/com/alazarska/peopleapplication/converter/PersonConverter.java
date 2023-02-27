package com.alazarska.peopleapplication.converter;

import com.alazarska.peopleapplication.dto.PersonForm;
import com.alazarska.peopleapplication.dto.PersonView;
import com.alazarska.peopleapplication.persistene.database.Person;
import org.springframework.stereotype.Component;

@Component
public class PersonConverter {

    public PersonView personToView(Person person) {
        PersonView personView = new PersonView();
        personView.setId(person.getId());
        personView.setFirstName(person.getFirstName());
        personView.setLastName(person.getLastName());
        personView.setEmail(person.getEmail());
        personView.setDob(person.getDob());
        personView.setSalary(person.getSalary());
        personView.setPhotoFileName(person.getPhotoFileName());
        return personView;
    }

    public void setPersonProperties(PersonForm personForm, Person person) {
        person.setFirstName(personForm.getFirstName());
        person.setLastName(personForm.getLastName());
        person.setEmail(personForm.getEmail());
        person.setDob(personForm.getDob());
        person.setSalary(personForm.getSalary());
    }

    public void setPersonFormProperties(PersonView personView, PersonForm personForm) {
        personForm.setFirstName(personView.getFirstName());
        personForm.setLastName(personView.getLastName());
        personForm.setEmail(personView.getEmail());
        personForm.setDob(personView.getDob());
        personForm.setSalary(personView.getSalary());
    }
}
