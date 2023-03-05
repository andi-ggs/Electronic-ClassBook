import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Test {
    public static void main(String[] args) {
        try {
            Catalog catalog = Catalog.getInstance();

            FileReader fileReader = new FileReader("test.json");
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(fileReader);
            TreeSet<Student> studentlist = new TreeSet<>(new Comparator<Student>() {
                @Override
                public int compare(Student o1, Student o2) {
                    return o1.toString().compareTo(o2.toString());
                }
            });
            ArrayList<Teacher> teacherlist = new ArrayList<>();

            HashMap<Teacher, ArrayList<TupleInterface>> examScores = new HashMap<>();
            HashMap<Assistant, ArrayList<TupleInterface>> partialScores = new HashMap<>();
            ScoreVisitor scoreVisitor = new ScoreVisitor(examScores,partialScores);
            ArrayList<Assistant> assistantslist = new ArrayList<>();
            HashSet<Parent> parents = new HashSet<>();
            Course fullcourse = null;
            for (Object o : (JSONArray) jsonObject.get("courses")) {
                boolean check = false;
                JSONObject curs = (JSONObject) o;
                String name = (String) curs.get("name");
                Long cred = (Long) curs.get("credits");
                int credits = cred.intValue();
                String strategy = (String) curs.get("strategy");
                JSONObject prof = (JSONObject) curs.get("teacher");
                String numeprof = (String) prof.get("firstName");
                String prenumeprof = (String) prof.get("lastName");
                User teacher = UserFactory.getUser(numeprof, prenumeprof, "Teacher");
                teacherlist.add((Teacher) teacher);
                String type = (String) curs.get("type");
                switch (type){
                    case "FullCourse":
                        fullcourse = new FullCourse.FullCourseBuilder(name, credits).build();
                        break;
                    case "PartialCourse":
                        fullcourse = new PartialCourse.PartialCourseBuilder(name, credits).build();
                        break;
                }
                fullcourse.setCreditPoints(credits);
                ArrayList<Assistant> assistants1 = new ArrayList<>();
                for (Object assis : (JSONArray) curs.get("assistants")) {
                    JSONObject assistant = (JSONObject) assis;
                    String firstName = (String) assistant.get("firstName");
                    String lastName = (String) assistant.get("lastName");
                    if (!check) {
                        User assistant1 = UserFactory.getUser(firstName, lastName, "Assistant");
                        assistants1.add((Assistant) assistant1);
                        assistantslist.add((Assistant) assistant1);
                        check = true;
                    } else {
                        ArrayList<Assistant> copyassistants = new ArrayList<>(assistantslist);
                        for (Assistant a : assistantslist) {
                            if (a.getFirstName().equals(firstName) && a.getLastName().equals(lastName)) {
                                assistants1.add(a);
                            } else {
                                User assistant1 = UserFactory.getUser(firstName, lastName, "Assistant");
                                assistants1.add((Assistant) assistant1);
                                copyassistants.add((Assistant) assistant1);
                                assistantslist = copyassistants;
                                break;
                            }
                        }
                        assistantslist = copyassistants;
                    }
                }
                fullcourse.setAssistants(assistants1);
                fullcourse.setTeacher((Teacher) teacher);
                switch (strategy) {
                    case "BestExamScore":
                        fullcourse.setStrategy(new BestExamScore());
                        break;
                    case "BestPartialScore":
                        fullcourse.setStrategy(new BestPartialScore());
                        break;
                    case "BestTotalScore":
                        fullcourse.setStrategy(new BestTotalScore());
                        break;
                }
                for (Object groups : (JSONArray) curs.get("groups")) {
                    JSONObject group = (JSONObject) groups;
                    String ID = (String) group.get("ID");
                    JSONObject assistant = (JSONObject) group.get("assistant");
                    String firstName = (String) assistant.get("firstName");
                    String lastName = (String) assistant.get("lastName");
                    User assist = UserFactory.getUser(firstName, lastName, "Assistant");
                    Group gr = new Group(ID, (Assistant) assist);
                    for (Object students : (JSONArray) group.get("students")) {
                        gr.setID(ID);
                        gr.setAssistant((Assistant) assist);
                        JSONObject student = (JSONObject) students;
                        String firstName1 = (String) student.get("firstName");
                        String lastName1 = (String) student.get("lastName");
                        int ok = 0;
                        for (Student s : studentlist) {
                            if (s.getFirstName().equals(firstName1) && s.getLastName().equals(lastName1)) {
                                ok = 1;
                                gr.add(s);
                            }
                        }
                        if (ok == 0) {
                            User stud = UserFactory.getUser(firstName1, lastName1, "Student");
                            JSONObject mami = (JSONObject) student.get("mother");
                            if (mami != null) {
                                String firstName2 = (String) mami.get("firstName");
                                String lastName2 = (String) mami.get("lastName");
                                User mama = UserFactory.getUser(firstName2, lastName2, "Parent");
                                ((Student) stud).setMother((Parent) mama);
                                if((Parent) mama != null)
                                    parents.add((Parent) mama);
                            }
                            JSONObject tati = (JSONObject) student.get("father");
                            if (tati != null) {
                                String firstName3 = (String) tati.get("firstName");
                                String lastName3 = (String) tati.get("lastName");
                                User tata = UserFactory.getUser(firstName3, lastName3, "Parent");
                                ((Student) stud).setFather((Parent) tata);
                                if((Parent) tata != null)
                                    parents.add((Parent) tata);
                            }

                            gr.add((Student) stud);
                            studentlist.add((Student) stud);
                        }
                    }
                    fullcourse.addAssistant(ID, (Assistant) assist);
                    fullcourse.addGroup(ID, (Assistant) assist);
                    fullcourse.addGroup(gr);
                }
                catalog.addCourse(fullcourse);
            }
                for(Object obj : (JSONArray)jsonObject.get("examScores")) {
                    JSONObject examScore = (JSONObject) obj;
                    JSONObject tch = (JSONObject) examScore.get("teacher");
                    String firstName = (String) tch.get("firstName");
                    String lastName = (String) tch.get("lastName");
                    int index = 0;
                    for (Teacher t : teacherlist) {
                        if (t.getFirstName().equals(firstName) && t.getLastName().equals(lastName)) {
                            index = teacherlist.indexOf(t);
                        }
                    }
                    String courseName = (String) examScore.get("course");
                    JSONObject stud = (JSONObject) examScore.get("student");
                    String firstName1 = (String) stud.get("firstName");
                    String lastName1 = (String) stud.get("lastName");
                    Double score1 = (((Number) examScore.get("grade")).doubleValue());
                    for (Student st : studentlist) {
                        if (st.getFirstName().equals(firstName1) && st.getLastName().equals(lastName1)) {
                            scoreVisitor.setExamScores(teacherlist.get(index), st, courseName, score1);
                        }
                    }
                }
                for(Object obj : (JSONArray)jsonObject.get("partialScores")) {
                    JSONObject partialScore = (JSONObject) obj;
                    JSONObject assist = (JSONObject) partialScore.get("assistant");
                    String firstName = (String) assist.get("firstName");
                    String lastName = (String) assist.get("lastName");
                    int index = 0;
                    for (Assistant a : assistantslist) {
                        if (a.getFirstName().equals(firstName) && a.getLastName().equals(lastName)) {
                            index = assistantslist.indexOf(a);
                        }
                    }
                    String courseName = (String) partialScore.get("course");
                    JSONObject stud = (JSONObject) partialScore.get("student");
                    String firstName1 = (String) stud.get("firstName");
                    String lastName1 = (String) stud.get("lastName");
                    Double score1 = (((Number) partialScore.get("grade")).doubleValue());
                    for (Student st : studentlist) {
                        if (st.getFirstName().equals(firstName1) && st.getLastName().equals(lastName1)) {
                            scoreVisitor.setPartialScores(assistantslist.get(index), st, courseName, score1);
                        }
                    }
                }
            Student s = catalog.getCourses().get(3).getStudents().get(2);

            StudentPage studentPage = new StudentPage(s);

            /**
             * Alegem random cursul "Sisteme de operare" si afisam
             * atat TeacherPage-ul cat si AssistantPage-ul,
             * pentru a valida notele primite in examen si partial de
             * studentul ales tot random ceva mai sus.
             */

            Teacher c = catalog.getCourses().get(3).getTeacher();
            TeacherPage teacher_page = new TeacherPage("Teacher Page", c, scoreVisitor);
            Assistant a = null;

            Course c2 = catalog.getCourses().get(3);
            for(Group g : c2.getGroups().values()) {
                for(Student stu : g){
                    if(stu.equals(s))
                        a = g.getAssistant();
                }
            }
            TeacherPage assistant_page = new TeacherPage("Assistant Page", a, scoreVisitor);


            /**
             * Afisam pagina de notificari pentru fiecare parinte al studentului ales mai sus.
             */

            for(Parent parent : parents){
                catalog.addObserver(parent);
            }

            Parent p2 = s.getMother();
            ParentPage parentPage = new ParentPage("Parent Page",p2);

            /**
             * Testarea implementarii design pattern-ului Memento.
             * Pentru a verifica, am apelat functia makeBackup() pentru primul curs din catalog
             * (ales random), iar apoi am adaugat un nou student caruia i-am setat nota.
             * Dupa care am afisat din nou (linia 244) si am apelat undo().
             * Pentru verificare am afisat din nou (linia 246).
             */
            Course poo = catalog.getCourses().get(0);

            System.out.println("Testare Memento");
            //Course poo = catalog.getCourses().get(0);
            poo.makeBackup();
            User andi = UserFactory.getUser("Andi", "Georgescu", "Student");
            Grade test = new Grade();
            test.setPartialScore(1.7);
            test.setExamScore(4.5);
            test.setCourse(catalog.getCourses().get(0).toString());
            test.setStudent((Student) andi);
            poo.addStudent("323CC",(Student) andi);
            poo.addGrade(test);
            System.out.println(poo);
            poo.undo();
            System.out.println(poo);

            /**
             * Test Login Page.
             */

            LoginPage loginPage = new LoginPage("Login Page", studentlist, teacherlist, assistantslist, parents, scoreVisitor);


            /**
             * Demaram procedura de aflare a celui mai bun student,
             * si alegem random cursul "Paradigme de programare".
             * Extragem atat profesorul titular, cat si lista de asistenti.
             * Apoi, pentru profesor si pentru fiecare asistent in parte
             * se apeleaza functia visit() si se afiseaza output-ul.
             * Pentru o mai buna intelegere, se pot decomenta, pe rand,
             * liniile : 269 -> se afiseaza notele studentilor pe care
             * le-am primit in examen; in continuare, linia 273 pentru
             * a putea vizualiza notele finale ale studentilor.
             */
                Teacher profpp = catalog.getCourses().get(1).getTeacher();
                ArrayList<Assistant> assistatspp = catalog.getCourses().get(1).getAssistants();
                scoreVisitor.visit(profpp);
                //System.out.println("Actualizare teacher:" + catalog.getCourses().get(1).getGrades());

                scoreVisitor.visit(assistatspp.get(0));
                scoreVisitor.visit(assistatspp.get(1));
                //System.out.println("Actualizare assistant:" + catalog.getCourses().get(1).getGrades());
                System.out.println("---CONCLUZII---");
                System.out.println("I) Cel mai bun student pentru cursul <" + catalog.getCourses().get(1).getName() + "> este " + catalog.getCourses().get(1).getBestStudent());
            /**
             * La cursul de Paradigme de Programare, studenta Georgiana Calin
             * este considerata ca fiind best student, deoarece are cea mai mare
             * nota din examen, fiind strategia de ordonare aleasa de profesor.
             */

            FullCourse PP = (FullCourse) catalog.getCourses().get(1);
            System.out.println("II) Studentii care au promovat cursul <" + PP.getName() + "> sunt: " + PP.getGraduatedStudents());
            /**
             * Se afiseaza lista cu studentii care au promovat
             * cursul de Paradigme de Programare
             */


        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }
}