package langsford.dto;

import java.util.Arrays;

/**
 * Created by Trevyn Langsford on 10/28/2014.
 */
public class Person {

    private int id;
    private String name;
    private int spouseId;
    private int[] childrenIds;

    public Person() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSpouseId() {
        return spouseId;
    }

    public void setSpouseId(int spouseId) {
        this.spouseId = spouseId;
    }

    public int[] getChildrenIds() {
        return childrenIds;
    }

    public void setChildrenIds(int[] childrenIds) {
        this.childrenIds = childrenIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;

        Person person = (Person) o;

        if (id != person.id) return false;
        if (spouseId != person.spouseId) return false;
        if (!Arrays.equals(childrenIds, person.childrenIds)) return false;
        if (name != null ? !name.equals(person.name) : person.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + spouseId;
        result = 31 * result + (childrenIds != null ? Arrays.hashCode(childrenIds) : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", spouseId=" + spouseId +
                ", childrenIds=" + Arrays.toString(childrenIds) +
                '}';
    }
}
