import java.util.Collection;

/**
 * Clasa parinte pentru celelalte entitati din catalog
 */
public abstract class User {
    private String firstName, lastName;

    public User(){
        firstName = "";
        lastName = "";
    }

    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // Getters and setters
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    //To string
    public String toString() {
        return firstName + " " + lastName;
    }
}
