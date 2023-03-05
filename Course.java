import java.util.*;

public abstract class Course{

    private String name;
    private Teacher teacher;
    private ArrayList<Assistant> assistants = new ArrayList<>();
    private TreeSet<Grade> grades = new TreeSet<Grade>();
    private HashMap<String, Group> groups = new HashMap<>();
    private int creditPoints;
    private Strategy strategy;

    private Snapshot snapshot;


    //Constructor specific design patternului Builder
    public Course(CourseBilder courseBilder) {
        this.name = courseBilder.name;
        this.teacher = courseBilder.teacher;
        this.creditPoints = courseBilder.creditPoints;
        this.assistants = courseBilder.assistants;
        this.grades = courseBilder.grades;
        this.groups = courseBilder.groups;
        this.strategy = courseBilder.strategy;
    }

    //Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public ArrayList<Assistant> getAssistants() {
        ArrayList<Assistant> copy = new ArrayList<>();
        for (Assistant assistant : assistants) {
                copy.add(assistant);
            }
        for(int i = 0; i < copy.size(); i++){
            for(int j = 0; j < copy.size(); j++){
                if(copy.get(i).getFirstName().equals(copy.get(j).getFirstName()) && copy.get(i).getLastName().equals(copy.get(j).getLastName()) && i != j){
                    copy.remove(j);
                }
            }
        }
        return copy;
    }

    public void setAssistants(ArrayList<Assistant> assistants) {
        this.assistants = assistants;
    }

    public TreeSet<Grade> getGrades() {
        return grades;
    }

    public void setGrades(TreeSet<Grade> grades) {
        this.grades = grades;
    }

    public HashMap<String, Group> getGroups() {
        return groups;
    }

    public void setGroups(HashMap<String, Group> groups) {
        this.groups = groups;
    }

    public int getCreditPoints() {
        return creditPoints;
    }

    public void setCreditPoints(int creditPoints) {
        this.creditPoints = creditPoints;
    }

    public void addAssistant(String ID, Assistant assistant) {

        if(groups.get(ID) == null){
            Group group = new Group(ID, assistant);
            groups.put(ID, group);
        }
        else{
            groups.get(ID).setAssistant(assistant);
        }

        if (!assistants.contains(assistant))
            assistants.add(assistant);
    }


    public void addStudent(String ID, Student student) {
        if(groups.get(ID) == null){
            Group group = new Group(ID, null);
            group.add(student);
            groups.put(ID, group);
        }
        else{
            groups.get(ID).add(student);
            groups.put(ID, groups.get(ID));
        }
        groups.get(ID).add(student);

        groups.put(ID, groups.get(ID));
    }

    public void addGroup(Group group) {
        groups.put(group.getID(), group);
    }

    public void addGroup(String ID, Assistant assistant) {

        if(groups.get(ID) == null){
            Group group = new Group(ID, assistant);
            groups.put(ID, group);
        }
        else{
            groups.get(ID).setAssistant(assistant);
        }
    }

    public void addGroup(String ID, Assistant assistant, Comparator<Student> comp) {
        groups.put(ID, new Group(ID, assistant, comp));
    }

    public Grade getGrade(Student student) {
        for (Grade g : grades) {
            if (g.getStudent().equals(student)) return g;
        }
        return null;
    }

    public void addGrade(Grade grade) {
        grades.add(grade);
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }


    public ArrayList<Student> getStudents() {
        ArrayList<Student> students = new ArrayList<>();
        for (Group g : groups.values()) {
            for (Student s : g) {
                students.add(s);
            }
        }
        return students;
    }

    public HashMap<Student, Grade> getAllStudentGrades() {
        HashMap<Student, Grade> grades = new HashMap<>();
        for (Group g : groups.values()) {
            for (Student s : g) {
                grades.put(s, getGrade(s));
            }
        }
        return grades;
    }

    public String toString(){
        return name + " - " + creditPoints + " " + assistants + " " + grades + " " + teacher;
    }

    public Student getBestStudent() {
        return this.strategy.compute(this.grades);
        }

    public void makeBackup(){
        this.snapshot = new Snapshot((TreeSet<Grade>) grades);
    }

    public void undo(){
        if(snapshot == null) {
            System.out.println("No backup available");
        }
        else
            this.grades = snapshot.getGrades_copy();
    }
    public abstract ArrayList<Student> getGraduatedStudents();

    //Clasa interna Builder
    static class CourseBilder {
        private final String name;
        private Teacher teacher;
        private final int creditPoints;
        private ArrayList<Assistant> assistants = new ArrayList<>();
        private TreeSet<Grade> grades = new TreeSet<>();
        private HashMap<String, Group> groups = new HashMap<>();
        private Strategy strategy;

        private Snapshot snapshot;

        public CourseBilder(String name, int creditPoints) {
            this.name = name;
            this.creditPoints = creditPoints;
        }

        public CourseBilder setTeacher(Teacher teacher) {
            this.teacher = teacher;
            return this;
        }

        public CourseBilder setAssistants(ArrayList<Assistant> assistants) {
            this.assistants = assistants;
            return this;
        }

        public CourseBilder setGrades(TreeSet<Grade> grades) {
            this.grades = grades;
            return this;
        }

        public CourseBilder setGroups(HashMap<String, Group> groups) {
            this.groups = groups;
            return this;
        }

        public CourseBilder setStrategy(Strategy strategy) {
            this.strategy = strategy;
            return this;
        }

        public CourseBilder setSnapshot(Snapshot snapshot) {
            this.snapshot = snapshot;
            return this;
        }

        public String toString() {
            return name + " " + creditPoints;
        }

        public Course build() {
            return new Course(this) {
                @Override
                public ArrayList<Student> getGraduatedStudents() {
                    ArrayList<Student> graduatedStudents = new ArrayList<>();
                    for (Group g : groups.values()) {
                        for (Student s : g) {
                            if (getGrade(s).getPartialScore() >= 18) {
                                graduatedStudents.add(s);
                            }
                        }
                    }
                    return graduatedStudents;
                }
            };
        }
    }

    private class Snapshot {

        private TreeSet<Grade> grades_copy;

        public Snapshot(TreeSet<Grade> gradesc_copy){
            this.grades_copy = grades_copy;
        }

        public TreeSet<Grade> getGrades_copy() {
            return grades_copy;
        }

        public void setGrades_copy(TreeSet<Grade> grades_copy) {
            this.grades_copy = grades_copy;
        }
    }
}
