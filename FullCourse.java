import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

/**
 * Clasa care implementeaza metoda abstracta din
 * clasa parinte, si returneaza un ArrayList cu
 * toti studentii care au trecut pragul de promovare
 * la examenul final
 */

public class FullCourse extends Course{
    private FullCourse(FullCourseBuilder fullCourseBuilder) {
        super(fullCourseBuilder);
    }

    //Implementeaza metoda abstracta din clasa parinte
    @Override
    public ArrayList<Student> getGraduatedStudents() {
        ArrayList<Student> graduatedStudents = new ArrayList<>();
        for (Grade g : getGrades()) {
            if (g.getPartialScore() >= 3 && g.getExamScore() >= 2) {
                graduatedStudents.add(g.getStudent());
            }
        }
        return graduatedStudents;
    }

    //Clasa interna Builder
    static class FullCourseBuilder extends CourseBilder {
        private String name;
        private Teacher teacher;

        private int creditPoints;
        private TreeSet<Assistant> assistants;
        private TreeSet<Grade> grades;
        private HashMap<String, Group> groups;

        public FullCourseBuilder(String name, int creditPoints) {
            super(name, creditPoints);
        }

        public FullCourseBuilder setTeacher(Teacher teacher) {
            this.teacher = teacher;
            return this;
        }

        public FullCourseBuilder setAssistants(TreeSet<Assistant> assistants) {
            this.assistants = assistants;
            return this;
        }

        public FullCourseBuilder setGrades(TreeSet<Grade> grades) {
            this.grades = grades;
            return this;
        }

        public FullCourseBuilder setGroups(HashMap<String, Group> groups) {
            this.groups = groups;
            return this;
        }


        @Override
        public FullCourse build() {
            return new FullCourse(this);
        }
    }
}
