import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

/**
 * Clasa pentru student, care mosteneste clasa User
 */

public class Student extends User implements Comparable {

    private Parent mother;
    public Parent father;

    public Student(){
        mother = new Parent();
        father = new Parent();
    }

    //Constructor
    public Student(String firstName, String lastName) {
        super(firstName, lastName);
    }

    //Getters and setters
    public void setMother(Parent mother) {
        this.mother = mother;
    }

    public Parent getMother() {
        return mother;
    }

    public void setFather(Parent father) {
        this.father = father;
    }

    public Parent getFather() {
        return father;
    }

    public String getFirstName() {
        return super.getFirstName();
    }

    public String getLastName() {
        return super.getLastName();
    }

    //Metoda compareTo pentru a putea sorta studentii
    @Override
    public int compareTo(Object o) {
        Student student = (Student) o;
        return toString().hashCode() - o.toString().hashCode();
    }

    //Returneaza asistentul studentului
    public Assistant getAssistant() {
        Catalog catalog = Catalog.getInstance();
        for(Course course : catalog.getCourses()) {
            Collection<Group> groups = course.getGroups().values();
            for(Group group : groups) {
                if(group.getStudent().equals(this)) {
                    return group.getAssistant();
                }
            }
        }
        return null;
    }

    //Returneaza obiectul de tip Student in functie de numele acestuia
    public Student getStudent(String firstName, String lastName) {
        Catalog catalog = Catalog.getInstance();
        for(Course course : catalog.getCourses()) {
            Collection<Group> groups = course.getGroups().values();
            for(Group group : groups) {
                if(group.getStudent().equals(this)) {
                    return group.getStudent();
                }
            }
        }
        return null;
    }

    //Returneaza ID ul grupei la care este studentul
    public String getGroupID() {
        Catalog catalog = Catalog.getInstance();
        for(Course course : catalog.getCourses()) {
            Collection<Group> groups = course.getGroups().values();
            for(Group group : groups) {
                for(Student s : group) {
                    if (s.equals(this)) {
                        return group.getID();
                    }
                }
            }
        }
        return null;
    }

    //Returneaza obiectul de tip Parent, care reprezinta mama acestuia
    public Parent getMother(Student student){
        Catalog catalog = Catalog.getInstance();
        for(Course course : catalog.getCourses()) {
            Collection<Group> groups = course.getGroups().values();
            for(Group group : groups) {
                for(Student s : group) {
                    if (s.getFirstName().equals(this.getFirstName()) && s.getLastName().equals(this.getLastName())) {
                        return s.getMother();
                    }
                    }
                }
            }
        return null;
    }

    //Returneaza obiectul de tip Parent, care reprezinta tatal acestuia
    public Parent getFather(Student student){
        Catalog catalog = Catalog.getInstance();
        for(Course course : catalog.getCourses()) {
            Collection<Group> groups = course.getGroups().values();
            for(Group group : groups) {
                for(Student s : group) {
                    if (s.getFirstName().equals(this.getFirstName()) && s.getLastName().equals(this.getLastName())) {
                        return s.getFather();
                    }
                    }
                }
            }
        return null;
    }

}