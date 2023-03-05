import java.util.*;

public class ScoreVisitor implements Visitor{

    private final HashMap<Teacher, ArrayList<TupleInterface>> examScores;
    private final HashMap<Assistant, ArrayList<TupleInterface>> partialScores;
    private final Catalog catalog = Catalog.getInstance();

    public ScoreVisitor(){
        examScores = new HashMap<>();
        partialScores = new HashMap<>();
    }

    public ScoreVisitor(HashMap<Teacher, ArrayList<TupleInterface>> examScores, HashMap<Assistant, ArrayList<TupleInterface>> partialScores) {
        this.examScores = examScores;
        this.partialScores = partialScores;
    }

    //Introduce in HashMap-ul examScores notele de la examene
    public void setExamScores(Teacher teacher, Student student, String courseName, Double score){
        Course course = catalog.getCourse(courseName);
        //verificam daca nota este in catalog
        if(examScores.containsKey(teacher)){
            examScores.get(teacher).add(new Tuple<>(student, courseName, score));
        //daca nu este, o cream si o adaugam
        }else{
            ArrayList<TupleInterface> list = new ArrayList<>();
            list.add(new Tuple<>(student, courseName, score));
            examScores.put(teacher, list);
        }
    }

    //Introduce in HashMap-ul partialScores notele de la partial
    public void setPartialScores(Assistant assistant, Student student, String courseName, Double score){
        Course course = catalog.getCourse(courseName);
        //verificam daca nota este in catalog
                if(partialScores.containsKey(assistant)){
                    partialScores.get(assistant).add(new Tuple<>(student, courseName, score));
                //daca nu este, o cream si o adaugam
                }else{
                    ArrayList<TupleInterface> list = new ArrayList<>();
                    list.add(new Tuple<>(student, courseName, score));
                    partialScores.put(assistant, list);
                }
    }

    //Returneaza HashMap-ul examScores
    public HashMap<Teacher, ArrayList<TupleInterface>> getExamScores() {
        return examScores;
    }

    //Returneaza HashMap-ul partialScores
    public HashMap<Assistant, ArrayList<TupleInterface>> getPartialScores() {
        return partialScores;
    }

    @Override
    public void visit(Assistant assistant) {
        ArrayList<TupleInterface> pair = null;
        Set<Assistant> assistants = partialScores.keySet();
        //Accesam keySetul partialScores pentru a accesa lista de Tuple-uri
        for(Assistant a: assistants){
            if(a.getFirstName().equals(assistant.getFirstName()) && a.getLastName().equals(assistant.getLastName())){
                pair = partialScores.get(a);
            }
        }
        //Parcurgem Tuple-urile si modificam notele de la partial
        for (TupleInterface<Student, String, Double> pereche: pair) {
            Course curs = Catalog.getInstance().getCourse(pereche.getCourseName());
            int gasit = 0;
            for (Grade grade: curs.getGrades()) {
                if(grade.getStudent() == pereche.getStudent()) {
                    gasit = 1;
                    //actualizam nota de la partial
                    grade.setPartialScore(pereche.getScore());
                    //notificam observatorii
                    catalog.notifyObservers(grade);
                }
            }
            if(gasit == 0) {
                //se creeaza si se adauga in catalog nota de la partial
                Grade nota = new Grade(curs.getName(), pereche.getStudent());
                nota.setPartialScore(pereche.getScore());
                curs.getGrades().add(nota);
                //notificam observatorii
                catalog.notifyObservers(nota);
            }
        }
    }
    @Override
    public void visit(Teacher teacher) {
        ArrayList<TupleInterface> pair = examScores.get(teacher);
        //Parcurgem Tuple-urile si modificam notele de la examen
        for (TupleInterface<Student, String, Double> pereche: pair) {
            Course curs = Catalog.getInstance().getCourse(pereche.getCourseName());
            int gasit = 0;
            for (Grade grade : curs.getGrades()) {
                if (grade.getStudent() == pereche.getStudent()) {
                    gasit = 1;
                    //actualizam nota de la examen
                    grade.setExamScore(pereche.getScore());
                    //notificam observatorii
                    catalog.notifyObservers(grade);
                }
            }
            if (gasit == 0) {
                //se creeaza si se adauga in catalog nota de la examen
                Grade nota = new Grade(curs.getName(), pereche.getStudent());
                nota.setExamScore(pereche.getScore());
                curs.getGrades().add(nota);
                //notificam observatorii
                catalog.notifyObservers(nota);
            }
        }
    }

//    public TupleInterface get(){
//        return null;
//    }

    public String toString(){
        return examScores + " -> " + partialScores;
    }

    //Clasa interna Tuple
    private static class Tuple<V, T, U> implements TupleInterface{
        private final T courseName;
        private final U score;
        private final V student;

        public Tuple(){
            this.courseName = null;
            this.score = null;
            this.student = null;
        }
        public Tuple(V student, T courseName, U score){
            this.student = student;
            this.courseName = courseName;
            this.score = score;
        }

        public T getCourseName() {
            return this.courseName;
        }

        public U getScore() {
            return this.score;
        }

        public V getStudent() {
            return this.student;
        }

        public String toString(){
            return student + " " + courseName + " " + score + "\n";
        }
    }
}
