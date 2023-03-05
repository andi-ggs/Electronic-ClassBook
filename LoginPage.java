import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

/**
 * Pagina de login, destinata tuturor entitatilor din
 * catalog pentru a se autentifica si pentru a urmari
 * activitatile lor
 */

public class LoginPage extends JFrame implements ActionListener {

    private JLabel title;
    private JTextField email;
    private JPasswordField password;
    private JLabel emailLabel;
    private JLabel passwordLabel;
    private JButton loginButton;

    private final TreeSet<Student> students;

    private final ArrayList<Assistant> assistants;

    private final ArrayList<Teacher> teachers;

    private final ScoreVisitor scoreVisitor;

    private final HashSet<Parent> parents;


    public LoginPage(String title, TreeSet<Student> students, ArrayList<Teacher> teachers, ArrayList<Assistant> assistants,HashSet<Parent> parents, ScoreVisitor scoreVisitor) {
        super(title);
        this.students = students;
        this.teachers = teachers;
        this.assistants = assistants;
        this.parents = parents;
        this.scoreVisitor = scoreVisitor;
        setGUI();
        update();
    }

    public void setGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(200, 200));
        getContentPane().setBackground(new Color(100, 100, 80));
        setLayout(new BorderLayout());

        this.title = new JLabel("Please enter your credentials! :)");
        email = new JTextField();
        password = new JPasswordField();
        emailLabel = new JLabel("Username");
        passwordLabel = new JLabel("Password ");
        loginButton = new JButton("Login");

        this.title.setFont(new Font("Georgia", Font.BOLD, 20));
        email.setColumns(30);
        password.setColumns(30);

        JPanel emailPanel = new JPanel();
        emailPanel.add(emailLabel);
        emailPanel.add(email);

        JPanel passwordPanel = new JPanel();
        passwordPanel.add(passwordLabel);
        passwordPanel.add(password);

        JPanel loginPanel = new JPanel(new GridLayout(2, 1));
        loginPanel.add(emailPanel);
        loginPanel.add(passwordPanel);

        loginButton.addActionListener(this);

        add(this.title, BorderLayout.NORTH);
        add(loginPanel, BorderLayout.CENTER);
        add(loginButton, BorderLayout.SOUTH);

        this.email.addActionListener(this);
        this.password.addActionListener(this);

        pack();
        setVisible(true);
    }

    public void update() {
        revalidate();
        repaint();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String username = "";
        String password = "";
        if (e.getSource() instanceof JButton) {
            username = email.getText();
            password = new String(this.password.getPassword());
        }

        /** Daca autentificarea se face de un student, se preiau datele acestuia
        * (Numele si Prenumele) si se deschide pagina de student
         * Analog pentru toate celelalte entitati
         */
        if(password.equals("student")){
            StringTokenizer st = new StringTokenizer(username, " ");
            String firstName = st.nextToken();
            String lastName = st.nextToken();
            for(Student student : students){
                if(student.getFirstName().equals(firstName) && student.getLastName().equals(lastName)){
                    new StudentPage(student);
                    dispose();
                }
            }

        }

        if(password.equals("teacher")){
            StringTokenizer st = new StringTokenizer(username, " ");
            String firstName = st.nextToken();
            String lastName = st.nextToken();
            for(Teacher teacher : teachers){
                if(teacher.getFirstName().equals(firstName) && teacher.getLastName().equals(lastName)){
                    new TeacherPage("Teacher Page Bonus", teacher, scoreVisitor);
                    dispose();
                }
            }

        }

        if(password.equals("assistant")){
            StringTokenizer st = new StringTokenizer(username, " ");
            String firstName = st.nextToken();
            String lastName = st.nextToken();
            for(Assistant assistant : assistants){
                if(assistant.getFirstName().equals(firstName) && assistant.getLastName().equals(lastName)){
                    new TeacherPage("Assistant Page Bonus", assistant, scoreVisitor);
                    dispose();
                }
            }
        }

        if(password.equals("parent")){
            StringTokenizer st = new StringTokenizer(username, " ");
            String firstName = st.nextToken();
            String lastName = st.nextToken();
            for(Parent parent : parents){
                if(parent.getFirstName().equals(firstName) && parent.getLastName().equals(lastName)){
                    new ParentPage("Parent Page Bonus", parent);
                    dispose();
                }
            }
        }

    }
}

