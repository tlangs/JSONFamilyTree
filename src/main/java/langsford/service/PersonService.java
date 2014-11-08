package langsford.service;

import langsford.dto.Person;

import java.util.*;

/**
 * Created by Trevyn Langsford on 10/28/2014.
 */
public class PersonService {

    private Map<Integer, Person> personMap;
    private Person[] people;

    public PersonService(Person[] people) {
        this.personMap = toMap(people);
        this.people = people;
    }

    /**
     * Make a Map of People with the key being the Person's ID.
     * @param people Array of People to make the map from.
     * @return A Map of People with IDs as keys.
     */
    private static Map<Integer, Person> toMap(Person[] people) {
        Map<Integer, Person> peopleMap = new HashMap<Integer, Person>();
        for (Person p : Arrays.asList(people)) {
            peopleMap.put(p.getId(), p);
        }
        return peopleMap;
    }

    /**
     * Get rid of all spouses in the list
     */
    public void removeSpouses() {
        Map<Integer, Person> noSpouses = toMap(this.people);
        for (Person p : this.people) {
            if (noSpouses.containsKey(p.getId()) && noSpouses.containsKey(p.getSpouseId())) {
                noSpouses.remove(p.getSpouseId());
            }
        }
        this.people = noSpouses.values().toArray(new Person[noSpouses.size()]);
    }

    /**
     * Finds all the grandchildren of the provided person in the provided
     * Array of People
     * @param person Person to find grandchildren of.
     * @return Array of all grandchildren of the provided Person.
     */
    private Person[] findGrandChildren(Person person) {
        List<Person> children = new ArrayList<>();
        List<Person> grandchildren = new ArrayList<>();
        List<Person> spouses = new ArrayList<>();

        // Getting the children of the person
        for (int x : person.getChildrenIds()) {
            children.add(this.personMap.get(x));
            int spouseId = this.personMap.get(x).getSpouseId();
            if (spouseId >= 0 ) {
                  children.add(this.personMap.get(spouseId));
            }
        }

        // Adding grandchildren to map
        for (Person child : children) {
            for (int x : child.getChildrenIds()) {
                Person grandchild = this.personMap.get(x);
                if (!grandchildren.contains(grandchild.getId()))
                    grandchildren.add(this.personMap.get(x));
            }
        }

        // adding spouses of grandchildren to map
        for (Person grandchild : grandchildren) {
            int spouseId = grandchild.getSpouseId();
            if (grandchild.getSpouseId() >= 0) {
                Person spouse = this.personMap.get(spouseId);
                spouses.add(spouse);
            }
        }

        for (Person spouse : spouses) {
            if (!grandchildren.contains(spouse)) {
                grandchildren.add(spouse);
            }
        }

        return grandchildren.toArray(new Person[grandchildren.size()]);
    }

    public Set<Person> findAllGrandchildren() {
        Set<Person> totalGrandchildren = new HashSet<>();
        for (Person person : this.people) {
            Person[] grandchildren = this.findGrandChildren(person);
            totalGrandchildren.addAll(Arrays.asList(grandchildren));
        }
        return totalGrandchildren;
    }


}
