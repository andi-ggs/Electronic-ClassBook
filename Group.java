import java.lang.reflect.Constructor;
import java.util.Comparator;
import java.util.TreeSet;

/**
 * Clasa pentru
 */

public class Group extends TreeSet<Student> {
    private Assistant assistant;
    private String ID;
    private Student student;

    public Comparator<Student> comp = null;

    public Group(){
        assistant = new Assistant();
        ID = "";
    }

    //Getters and setters
    public void setAssistant(Assistant assistant) {
        this.assistant = assistant;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public Student getStudent(){
        return this.student;
    }

    public Assistant getAssistant() {
        return this.assistant;
    }
    public String getID() {
        return ID;
    }

    //To string
    @Override
    public String toString() {
        return " " + assistant;
    }

    public Group(String ID, Assistant assistant) {
        this.ID = ID;
        this.assistant = assistant;
    }

    public Group(String ID, Assistant assistant, Comparator<Student> comp) {
        this.ID = ID;
        this.assistant = assistant;
        this.comp = new Comparator<>() {
            @Override
            public int compare(Student o1, Student o2) {
                return o1.compareTo(o2);
            }
        } ;
    }
}
