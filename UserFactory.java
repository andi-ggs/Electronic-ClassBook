public class UserFactory {

    //Implement the factory method
    public static User getUser(String firstName, String lastName, String type) {
        return switch (type) {
            case "Assistant" -> new Assistant(firstName, lastName);
            case "Teacher" -> new Teacher(firstName, lastName);
            case "Parent" -> new Parent(firstName, lastName);
            case "Student" -> new Student(firstName, lastName);
            default -> null;
        };
    }
}
