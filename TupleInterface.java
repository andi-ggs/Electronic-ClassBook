/**
 * Am folosit aceasta interfata pentru a putea sa
 * accesez elementele din clasa Tuple,
 * deoarece aeasta era o clasa interna privata
 */
public interface TupleInterface<V,T,U> {
    public U getScore();
    public T getCourseName();
    public V getStudent();
}
