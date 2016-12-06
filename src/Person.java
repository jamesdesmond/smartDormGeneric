/**
 * @author James Desmond
 * SmartDormGeneric Final Assignment
 * CS1000-Fall 2016
 * Due: 12/6/16
 *
 * **********************
 * Description
 * **********************
 *
 * Creates Person objects that have a name and an email address
 *
 */
public class Person {
    /**
     * Person's name
     */
    private String name;
    /**
     * Person's email
     */
    private String email;

    /**
     * Creates the specified Person
     * @param name name
     * @param email email
     */
    public Person(String name, String email) {
        this.name = name;
        this.email = email;
    }

    /**
     * Returns the Person's name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the Person's email
     * @return email
     */
    public String getEmail() {
        return email;
    }
}