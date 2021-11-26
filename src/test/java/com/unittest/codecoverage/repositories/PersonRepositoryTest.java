package com.unittest.codecoverage.repositories;

import com.unittest.codecoverage.models.Person;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PersonRepositoryTest {
    PersonRepository repository = new PersonRepository();

    @Test
    public void testInsert_shouldNotPermitNullValue() {
        Person person = null;

        assertThatThrownBy(() -> repository.insert(person))
                .hasMessage("person can't be null");
    }

    @Test
    public void testDelete_shouldNotPermitNullName() {
        String name = null;

        assertThatThrownBy(() -> repository.delete(name))
                .hasMessage("name can't be null");
    }
}
