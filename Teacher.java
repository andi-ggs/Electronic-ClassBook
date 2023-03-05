/**
 * Clasa pentru profesorul unui curs, care mosteneste clasa User
 */

public class Teacher extends User implements Element{
    public Teacher(String firstName, String lastName) {
        super(firstName, lastName);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
