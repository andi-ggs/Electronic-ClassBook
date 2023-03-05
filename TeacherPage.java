import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

/**
 * Pagina destinata profesorilor pentru a valida notele primite de
 * studenti pe parcursul semestrului
 */

public class TeacherPage extends JFrame implements ActionListener, ListSelectionListener {

    private JPanel panel1;

    private JList<String> courseJList;

    private final User user;

    private ScoreVisitor scoreVisitor;

    private JTextArea textArea;

    private JButton button;

    JScrollPane scrollPane;


    public TeacherPage(String title, User user, ScoreVisitor scoreVisitor) {
        super(title);
        this.user = user;
        this.scoreVisitor = scoreVisitor;
        setGUI();
        addTeacherCourses(user);
        update();
    }

    //Adauga cursurile profesorului/asistentului in lista de cursuri
    public void addTeacherCourses(User user) {
        DefaultListModel<String> courseDefaultListModel = new DefaultListModel<>();
        //Adauga cursurile profesorului
        if (user instanceof Teacher) {
            for (Course course : Catalog.getInstance().getCourses()) {
                if (course.getTeacher().equals(user)) {
                    courseDefaultListModel.addElement(course.getName());
                }
            }
        }

        //Adauga cursurile asistentului
        if (user instanceof Assistant) {
            for (Course course : Catalog.getInstance().getCourses()) {
                ArrayList<Assistant> asistenti = course.getAssistants();
                for (Assistant assistant : asistenti) {
                    if (assistant.getFirstName().equals(((Assistant) user).getFirstName())&&assistant.getLastName().equals(((Assistant) user).getLastName())) {
                        courseDefaultListModel.addElement(course.getName());
                    }
                }
            }
        }

        courseJList = new JList<>(courseDefaultListModel);
        //JScrollPane scrollPane = new JScrollPane(courseJList);
        scrollPane = new JScrollPane(courseJList);
        courseJList.setLayoutOrientation(JList.VERTICAL);
        courseJList.setModel(courseDefaultListModel);
        courseJList.addListSelectionListener(this);
        panel1.add(scrollPane);
    }

    public void setGUI() {
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        //data = new Vector<>();

        button = new JButton("Add Grade");
        button.addActionListener(this);
        button.setEnabled(true);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        scrollPane = new JScrollPane(courseJList);

        panel1 = new JPanel();

        textArea = new JTextArea(1, 10);
        panel1.add(textArea);
        panel1.add(button);
        add(panel1);

    }

    public void update() {
        revalidate();
        repaint();

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() instanceof JButton) {
            //Adauga nota profesorului/asistentului
            //prin apelarea metodei accept din clasa ScoreVisitor
            if (user instanceof Teacher) {
                ((Teacher) user).accept(scoreVisitor);
            } else if (user instanceof Assistant) {
                ((Assistant) user).accept(scoreVisitor);
            }
        }
        //Dupa ce a fost accesat butonul si s-au adaugat
        //notele profesorului/asistentului, se seteaza butonul
        //inapoi pe disabled
        button.setEnabled(false);
        button.setText("Grade Added");

    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (courseJList.isSelectionEmpty()) {
            return;
        }

        //Se afiseaza studentii care au primit note la examen
        if (user instanceof Teacher) {
            textArea.setText(scoreVisitor.getExamScores().get((Teacher) user).toString());
        }

        //Se afiseaza studentii care au primit note la laborator
        if (user instanceof Assistant) {
            Set<Assistant> ass = scoreVisitor.getPartialScores().keySet();
            for(Assistant a : ass){
                if(a.getFirstName().equals(((Assistant) user).getFirstName())&&a.getLastName().equals(((Assistant) user).getLastName())){
                    textArea.setText(scoreVisitor.getPartialScores().get(a).toString());
                }
            }
        }
    }
}
