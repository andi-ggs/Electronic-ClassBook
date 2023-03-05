/**
 * Clasa pentru asistentul unui curs, care mosteneste clasa User
 */
public class Assistant extends User implements Element{
    public Assistant(String firstName, String lastName) {
        super(firstName, lastName);
    }

    public Assistant() {
        super(null, null);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
