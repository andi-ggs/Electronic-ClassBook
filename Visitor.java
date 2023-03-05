/**
 * Interfata pentru sablonul de proiectare Visitor
 */
public interface Visitor {
    void visit(Assistant assistant);
    void visit(Teacher teacher);
}
