package com.alazarska.peopleapplication.service;

import com.alazarska.peopleapplication.converter.PersonConverter;
import com.alazarska.peopleapplication.dto.PersonForm;
import com.alazarska.peopleapplication.dto.PersonView;
import com.alazarska.peopleapplication.persistene.database.Person;
import com.alazarska.peopleapplication.persistene.database.PersonRepository;
import com.alazarska.peopleapplication.persistene.disk.FileStorageRepository;
import lombok.SneakyThrows;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class PeopleService {

    private final PersonRepository personRepository;
    private final PersonConverter personConverter;
    private final FileStorageRepository fileStorageRepository;

    public PeopleService(PersonRepository personRepository, PersonConverter personConverter, FileStorageRepository fileStorageRepository) {
        this.personRepository = personRepository;
        this.personConverter = personConverter;
        this.fileStorageRepository = fileStorageRepository;
    }

    public List<PersonView> findAllPeople() {
        Iterable<Person> people = personRepository.findAll();
        return StreamSupport.stream(people.spliterator(), true)
                .map(personConverter::personToView)
                .collect(Collectors.toList());
    }

    public Optional<PersonView> findPersonById(Long id) {
        return personRepository.findById(id).map(personConverter::personToView);
    }

    public Resource findPersonImageByFileName(String fileName) {
        return fileStorageRepository.findByName(fileName);
    }

    @SneakyThrows
    public PersonView savePerson(PersonForm personForm, MultipartFile photoFile) {
        Person person = new Person();
        personConverter.setPersonProperties(personForm, person);
        Person savedPerson = personRepository.save(person);

        if (!photoFile.isEmpty()) {
            String fileName = createFileName(photoFile.getOriginalFilename(), savedPerson.getId());
            fileStorageRepository.save(fileName, photoFile.getInputStream());
            savedPerson.setPhotoFileName(fileName);
        } else {
            savedPerson.setPhotoFileName("default-avatar.jpg");
        }
        Person personWithUpdatedPhoto = personRepository.save(savedPerson);
        return personConverter.personToView(personWithUpdatedPhoto);
    }

    @SneakyThrows
    public PersonView updatePerson(Long id, PersonForm personForm, MultipartFile photoFile){
        Person person = personRepository.findById(id).get();
        personConverter.setPersonProperties(personForm, person);

        if (!photoFile.isEmpty()) {
            String fileName = createFileName(photoFile.getOriginalFilename(), person.getId());
            fileStorageRepository.deleteFileIfExist(fileName);
            fileStorageRepository.save(fileName, photoFile.getInputStream());
            person.setPhotoFileName(fileName);
        }

        Person savedPerson = personRepository.save(person);
        return personConverter.personToView(savedPerson);
    }

    public void deletePerson(Long id) {
        PersonView personView = findPersonById(id).get();
        fileStorageRepository.deleteFileIfExist(personView.getPhotoFileName());
        personRepository.deleteById(id);
    }

    private String createFileName(String photoFileName, Long personId) {
        String[] splitOriginalFileName = photoFileName.split("\\.");
        String extension = splitOriginalFileName[splitOriginalFileName.length - 1];
        return personId + "." + extension;
    }
}
