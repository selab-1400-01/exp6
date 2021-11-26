package com.unittest.codecoverage.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import com.unittest.codecoverage.models.validators.PersonValidator;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.unittest.codecoverage.exceptions.PersonException;
import com.unittest.codecoverage.models.Gender;
import com.unittest.codecoverage.models.Person;
import com.unittest.codecoverage.repositories.PersonRepository;
import com.unittest.codecoverage.services.PersonService;
import com.unittest.codecoverage.services.impl.PersonServiceImpl;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {
	
	@InjectMocks
	PersonService service = new PersonServiceImpl();
	@Mock
	PersonRepository repository;
	@Mock
	PersonValidator validator;

	@Test
	public void testInsert_shouldValidatePerson() {
		Person person = new Person();

		service.insert(person);

		verify(validator).validate(person);
	}

	@Test
	public void testInsert_shouldInsertPersonWithSuccessWhenAllPersonsInfoIsFilled() {
		Person person = new Person();
		person.setName("Name");
		person.setAge(21);
		person.setGender(Gender.M);
		when(repository.insert(any(Person.class))).thenReturn(person);

		service.insert(person);

		verify(repository).insert(person);
	}

	@Test
	public void testUpdate_shouldValidatePerson() {
		Person person = new Person();

		service.update(person);

		verify(validator).validate(person);
	}

	@Test
	public void testUpdate_shouldUpdatePersonWithSuccessWhenAllPersonsInfoIsFilled() {
		Person person = new Person();
		person.setName("Name");
		person.setAge(21);
		person.setGender(Gender.M);

		service.update(person);

		verify(repository).update(person);
	}

	@Test
	public void testGet_shouldThrowPersonExceptionIfNotValid() {
		String name = "SampleName";

		when(validator.requiredName(name)).thenReturn(true);

		assertThatThrownBy(() -> service.get(name))
				.isInstanceOf(PersonException.class)
				.hasMessage("Name is required");
	}

	@Test
	public void testGet_shouldGetPersonWithName() {
		String name = "SampleName";

		service.get(name);

		verify(repository).get(name);
	}

	@Test
	public void testDelete_shouldThrowPersonExceptionIfNotValid() {
		String name = "SampleName";

		when(validator.requiredName(name)).thenReturn(true);

		assertThatThrownBy(() -> service.delete(name))
				.isInstanceOf(PersonException.class)
				.hasMessage("Name is required");
	}

	@Test
	public void testDelete_shouldDeleteThePersonWithName() {
		String name = "SampleName";

		service.delete(name);

		verify(repository).delete(name);
	}
}
