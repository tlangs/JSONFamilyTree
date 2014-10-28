package langsford.service;

import langsford.dto.Person;

import java.util.*;

/**
 * Created by Trevyn Langsford on 10/28/2014.
 */
public final class PersonService {

    public PersonService() {

    }

    /**
     * Make a Map of People with the key being the Person's ID.
     * @param people Array of People to make the map from.
     * @return A Map of People with IDs as keys.
     */
    private static Map<Integer, Person> toMap(Person[] people) {
        Map<Integer, Person> peopleMap = new HashMap<Integer, Person>();
        Iterator<Person> personIter = Arrays.asList(people).iterator();
        while (personIter.hasNext()) {
            Person p = personIter.next();
            peopleMap.put(p.getId(), p);
        }
        return peopleMap;
    }

    /**
     * Finds all the grandchildren of the provided person in the provided
     * Array of People
     * @param person Person to find grandchildren of.
     * @param people Array of People to find grandchildren in.
     * @return Array of all grandchildren of the provided Person.
     */
    public static Person[] findGrandChildren(Person person, Person[] people) {
        Map<Integer, Person> personMap = toMap(people);
        List<Person> children = new ArrayList<>();
        List<Person> grandchildren = new ArrayList<>();
        List<Person> spouses = new ArrayList<>();

        // Getting the children of the person
        for (int x : person.getChildrenIds()) {
            children.add(personMap.get(x));
        }

        // Adding grandchildren to map
        for (Person child : children) {
            for (int x : child.getChildrenIds()) {
                Person grandchild = personMap.get(x);
                if (!grandchildren.contains(grandchild.getId()))
                    grandchildren.add(personMap.get(x));
            }
        }

        // adding spouses of grandchildren to map
        for (Person grandchild : grandchildren) {
            Person spouse = personMap.get(grandchild.getSpouseId());
                spouses.add(spouse);
        }

        for (Person spouse : spouses) {
            if (!grandchildren.contains(spouse)) {
                grandchildren.add(spouse);
            }
        }

        return grandchildren.toArray(new Person[grandchildren.size()]);
    }
}
