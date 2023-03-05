import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Clasa Catalog.
 */
public class Catalog implements Subject {

    private final List<Course> courses;
    private final ArrayList<Observer> observers;

    private static Catalog obj = null;

    private Catalog() {
        courses = new ArrayList<>();
        observers = new ArrayList<>();

    }

    public static Catalog getInstance() {
        if (obj == null) {
            synchronized (Catalog.class) {
                if (obj == null) {
                    obj = new Catalog();
                }
            }
        }
        return obj;
    }

    // Adauga un curs în catalog
    public void addCourse(Course course) {
        if (!Catalog.getInstance().courses.contains(course))
            Catalog.getInstance().courses.add(course);
        return;
    }

    // Sterge un curs din catalog
    public void removeCourse(Course course) {
        if (Catalog.getInstance().courses.contains(course))
            Catalog.getInstance().courses.remove(course);
        return;
    }

    // Returneaza un obiect de tip Course din catalog, in functie de numele acestuia
    public Course getCourse(String name1) {
        if (name1 != null) {
            for (Course course : Catalog.getInstance().courses) {
                if (course.getName().equals(name1))
                    return course;
            }
        }
        return null;
    }

    // Returneaza lista de cursuri din catalog
    public List<Course> getCourses() {
        return courses;
    }

    public Student getStudent(String firstName, String lastName) {
        Catalog catalog = Catalog.getInstance();
        for (Course course : catalog.getCourses()) {
            Collection<Group> groups = course.getGroups().values();
            for (Group group : groups) {
                if (group.getStudent().getFirstName().equals(firstName) && group.getStudent().getLastName().equals(lastName)) {
                    return group.getStudent();
                }
            }
        }
        return null;
    }

    public String toString() {
        return "Catalog: courses=" + courses + '}' + "\n";
    }

    // Metodele de Subject, adauga un observer in lista de observers
    public void addObserver(Observer observer) {
        this.observers.add(observer);
    }

    // Metodele de Subject, sterge un observer din lista de observers
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    // Metodele de Subject, notifica toti observatorii in momentul in care se adauga o nota noua
    public void notifyObservers(Grade grade) {
        for (Observer o : observers) {
            Parent parent = (Parent) o;
            if(grade.getStudent().getMother() != null) {
                if (grade.getStudent().getMother().equals(o)) {
                    parent.update(new Notification(grade));
                }
            }
            if (grade.getStudent().getFather() != null) {
                if (grade.getStudent().getFather().equals(o)) {
                    parent.update(new Notification(grade));
                }
            }
        }
    }

    public ArrayList <Observer> getObservers() {
        return this.observers;
    }
}