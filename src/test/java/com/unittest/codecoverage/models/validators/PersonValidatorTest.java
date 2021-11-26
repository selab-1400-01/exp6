package com.unittest.codecoverage.models.validators;

import com.unittest.codecoverage.exceptions.PersonException;
import com.unittest.codecoverage.models.Gender;
import com.unittest.codecoverage.models.Person;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class PersonValidatorTest {

    private PersonValidator validator = new PersonValidator();

    @Test
    public void testValidate_shouldThrowPersonExceptionWhenPersonIsNull() {

        List<String> expectedErrors = Lists.newArrayList(
                "Name is required", "Gender is required", "Age cannot be negative.");
        String expectedMessage = String.join(";", expectedErrors);
        Person person = null;

        assertThatThrownBy(() -> validator.validate(person))
                .isInstanceOf(PersonException.class)
                .hasFieldOrPropertyWithValue("errors", expectedErrors)
                .hasMessage(expectedMessage);
    }

    @Test
    public void testValidate_shouldThrowPersonExceptionWhenPersonNameIsNull() {

        List<String> expectedErrors = Lists.newArrayList("Name is required");
        String expectedMessage = String.join(";", expectedErrors);
        Person person = new Person();
        person.setGender(Gender.M);

        assertThatThrownBy(() -> validator.validate(person))
                .isInstanceOf(PersonException.class)
                .hasFieldOrPropertyWithValue("errors", expectedErrors)
                .hasMessage(expectedMessage);
    }

    @Test
    public void testValidate_shouldThrowPersonExceptionWhenPersonNameIsBlank() {

        List<String> expectedErrors = Lists.newArrayList("Name is required");
        String expectedMessage = String.join(";", expectedErrors);
        Person person = new Person();
        person.setGender(Gender.M);
        person.setName(" ");

        assertThatThrownBy(() -> validator.validate(person))
                .isInstanceOf(PersonException.class)
                .hasFieldOrPropertyWithValue("errors", expectedErrors)
                .hasMessage(expectedMessage);
    }

    @Test
    public void testValidate_shouldThrowPersonExceptionWhenPersonGenderIsNull() {

        List<String> expectedErrors = Lists.newArrayList("Gender is required");
        String expectedMessage = String.join(";", expectedErrors);
        Person person = new Person();
        person.setName("Name");
        person.setGender(null);

        assertThatThrownBy(() -> validator.validate(person))
                .isInstanceOf(PersonException.class)
                .hasFieldOrPropertyWithValue("errors", expectedErrors)
                .hasMessage(expectedMessage);
    }

    @Test
    public void testValidate_shouldThrowPersonExceptionWhenPersonAgeIsNegative() {

        List<String> expectedErrors = Lists.newArrayList("Age cannot be negative.");
        String expectedMessage = String.join(";", expectedErrors);
        Person person = new Person();
        person.setName("Name");
        person.setGender(Gender.M);
        person.setAge(-2);

        assertThatThrownBy(() -> validator.validate(person))
                .isInstanceOf(PersonException.class)
                .hasFieldOrPropertyWithValue("errors", expectedErrors)
                .hasMessage(expectedMessage);
    }

    @Test
    public void testRequireName_shouldReturnFalseWhenNameIsNull() {
        String name = null;

        assertThat(validator.requiredName(name))
                .isFalse();
    }

    @Test
    public void testRequireName_shouldReturnFalseWhenNameIsEmpty() {
        String name = "";

        assertThat(validator.requiredName(name))
                .isFalse();
    }
}
