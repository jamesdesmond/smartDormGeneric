/**
 * **********************
 * Description
 * **********************
 *
 * Creates Person objects that have a name and an email address
 *
 * **********************
 * Analysis
 * **********************
 * Inputs: none
 * Outputs: none
 *
 * **********************
 * Pseudocode
 * **********************
 * Default field assignment
 */
public class Person {
    private String name;
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
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
}