import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

/**
 * Clasa care implementeaza metoda abstracta din
 * clasa parinte, si returneaza un ArrayList cu
 * toti studentii care au trecut pragul de promovare
 * la examenul partial
 */

public class PartialCourse extends Course {
    private PartialCourse(PartialCourseBuilder partialCourseBuilder) {
        super(partialCourseBuilder);
    }

    //Implementeaza metoda abstracta din clasa parinte
    @Override
    public ArrayList<Student> getGraduatedStudents() {
        ArrayList<Student> graduatedStudents = new ArrayList<>();
        for (Grade g : getGrades()) {
            if (g.getTotal() >= 5) {
                graduatedStudents.add(g.getStudent());
            }
        }
        return graduatedStudents;
    }

    //Clasa interna Builder
    static class PartialCourseBuilder extends CourseBilder {

        private String name;
        private Teacher teacher;

        private int creditPoints;
        private TreeSet<Assistant> assistants;
        private TreeSet<Grade> grades;
        private HashMap<String, Group> groups;

        public PartialCourseBuilder(String name, int creditPoints) {
            super(name, creditPoints);
        }

        public PartialCourseBuilder setTeacher(Teacher teacher) {
            this.teacher = teacher;
            return this;
        }

        public PartialCourseBuilder setAssistants(TreeSet<Assistant> assistants) {
            this.assistants = assistants;
            return this;
        }

        public PartialCourseBuilder setGrades(TreeSet<Grade> grades) {
            this.grades = grades;
            return this;
        }

        public PartialCourseBuilder setGroups(HashMap<String, Group> groups) {
            this.groups = groups;
            return this;
        }

        @Override
        public PartialCourse build() {
            return new PartialCourse(this);
        }
    }
}
