import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

import java.awt.*;

import java.util.*;

/**
 * Pagina destinata studentilor pentru a vedea notele primite de la profesori
 * pe parcursul semestrului
 */


public class StudentPage extends JFrame implements ListSelectionListener {
    private JPanel panel1;

    private JList<String> courseJList;

    private Vector<Vector<Object>> data;

    private final Student student;

    private JTextField teachernameField, assistantlistField, examField, partialField, totalfield, asistantfield;

    private JLabel teachernameLabel, assistantlistLabel, examLabel, partialLabel, totalLabel, asistantLabel;

    JScrollBar scrollBar;


    public StudentPage(Student student) {
        super("Student Page");
        this.student = student;
        setGUI();
        addStudentCourses(student);
        update();
    }

    public void setGUI() {
        setSize(1100, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        data = new Vector<>();

        JPanel panel = new JPanel();
        scrollBar = new JScrollBar(JScrollBar.HORIZONTAL);


        //Label & Text Field pentru numele profesorului
        teachernameLabel = new JLabel("Teacher name: ");
        teachernameField = new JTextField(12);

        //Label & Text Field pentru lista de asistenti
        assistantlistLabel = new JLabel("Assistant list: ");
        assistantlistField = new JTextField(20);

        BoundedRangeModel brm = assistantlistField.getHorizontalVisibility();
        scrollBar.setModel(brm);

        //Label & Text Field pentru nota de examen
        examLabel = new JLabel("Exam: ");
        examField = new JTextField(3);

        //Label & Text Field pentru nota partiala
        partialLabel = new JLabel("Partial: ");
        partialField = new JTextField(3);

        //Label & Text Field pentru nota totala
        totalLabel = new JLabel("Total: ");
        totalfield = new JTextField(3);

        //Label & Text Field pentru nota asistentului
        asistantLabel = new JLabel("Asistant: ");
        asistantfield = new JTextField(12);

        panel1 = new JPanel();


        panel.add(teachernameLabel);
        panel.add(teachernameField);
        panel.add(assistantlistLabel);
        panel.add(assistantlistField);
        panel.add(scrollBar);
        panel.add(examLabel);
        panel.add(examField);
        panel.add(partialLabel);
        panel.add(partialField);
        panel.add(totalLabel);
        panel.add(totalfield);
        panel.add(asistantLabel);
        panel.add(asistantfield);

        add(panel, BorderLayout.NORTH);
        add(panel1);
    }

    public void update() {
        revalidate();
        repaint();

    }

    //Adauga cursurile la care este inrolat studentul in JList
    public void addStudentCourses(Student student) {
        DefaultListModel<String> courseDefaultListModel = new DefaultListModel<>();

        //Parcurge lista de cursuri din catalog si adauga cursurile la care este inrolat studentul
        for (Course course : Catalog.getInstance().getCourses()) {
            for (Student studentFromCourse : course.getStudents()) {
                if (studentFromCourse.equals(student)) {
                    courseDefaultListModel.addElement(course.getName());
                }
            }
        }

        courseJList = new JList<>(courseDefaultListModel);
        courseJList.setPreferredSize(new Dimension(150, 20));
        courseJList.setLayoutOrientation(JList.VERTICAL);
        courseJList.addListSelectionListener(this);

        JScrollPane scrollPaneCourses = new JScrollPane(courseJList);
        scrollPaneCourses.setViewportView(courseJList);

        panel1.add(scrollPaneCourses);
    }


    //Returneaza cursul in functie de numele aceluia
    public Course getCourseByName(Object courseName) {
        for (Course course : Catalog.getInstance().getCourses()) {
            if (course.getName().equals(courseName))
                return course;
        }

        return null;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (courseJList.isSelectionEmpty())
            return;

        Course course = getCourseByName(courseJList.getSelectedValue());

        assert course != null;

        //Afiseaza numele profesorului
        teachernameField.setText(course.getTeacher().toString());
        //Afiseaza lista de asistenti
        assistantlistField.setText(course.getAssistants().toString());
        TreeSet<Grade> grades = course.getGrades();
        //Afiseaza notele de examen, partiala si totala
        for (Grade grade : grades)
            if (grade.getStudent().equals(student)) {
                examField.setText(String.valueOf(grade.getExamScore()));
                partialField.setText(String.valueOf(grade.getPartialScore()));
                totalfield.setText(String.valueOf(grade.getTotal()).substring(0,4));
            }

        //Afiseaza asistentul specific grupei studentului
        for (Map.Entry<String, Group> entry : course.getGroups().entrySet()) {
            if (entry.getKey().equals(student.getGroupID())) {
                asistantfield.setText(entry.getValue().getAssistant().toString());
            }
        }

    }
}