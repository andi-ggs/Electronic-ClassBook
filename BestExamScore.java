import java.util.TreeSet;

/**
 * Clasa care implementeaza metoda din interfata
 * Strategy si returneaza studentul cu
 * nota maxima la examenul final
 */
public class BestExamScore implements Strategy{


        // Metoda compute, care returneaza studentul cu cea mai mare
        // nota la examen
        public Student compute(TreeSet<Grade> grades){
            Student ans = null;
            Double value = 0.0;

            for (Grade grade : grades) {
                if (grade.getExamScore() > value) {
                    value = grade.getExamScore();
                    ans = grade.getStudent();
                }
            }

            return ans;
        }
}
