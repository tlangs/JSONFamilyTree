package langsford.service;

import com.google.gson.Gson;
import langsford.dto.Person;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Set;

/**
 * Created by Trevyn Langsford on 10/29/2014.
 */
public class PersonServiceTests {
    @Before
    public void setup() {
        // Nothing to do
    }

    @Test
    public void testGetGrandchildren() throws Exception {
        try {
            BufferedReader br = new BufferedReader(new FileReader("resources/test2.txt"));
            Gson gson = new Gson();
            Person[] people = gson.fromJson(br, Person[].class);
            PersonService personService = new PersonService(people);
            Set<Person> totalGrandchildren = personService.findAllGrandchildren();

            assertEquals(3, totalGrandchildren.size());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.err.println("Error: File not found!");
        }
    }

    @Test
    public void testIdenticalSpouseLists() throws Exception {
        try {
            BufferedReader br = new BufferedReader(new FileReader("resources/test.txt"));
            Gson gson = new Gson();
            Person[] people = gson.fromJson(br, Person[].class);
            PersonService personService = new PersonService(people);
            personService.removeSpouses();
            Set<Person> totalGrandchildren = personService.findAllGrandchildren();

            assertEquals(8, totalGrandchildren.size());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.err.println("Error: File not found!");
        }
    }

    @After
    public void tearDown() {
        // Nothing to do
    }
}
