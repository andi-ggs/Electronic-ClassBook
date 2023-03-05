import java.util.TreeSet;

/**
 * Clasa care implementeaza metoda din interfata
 * Strategy si returneaza studentul cu
 * nota finala maxima
 */
public class BestTotalScore implements Strategy{

    // Metoda compute, care returneaza studentul cu cea mai mare
    // nota finala
    @Override
    public Student compute(TreeSet<Grade> grades) {
        Student ans = null;
        Double value = 0.0;

        for (Grade grade : grades) {
            if (grade.getTotal() > value) {
                value = grade.getTotal();
                ans = grade.getStudent();
            }
        }

        return ans;
    }
}
