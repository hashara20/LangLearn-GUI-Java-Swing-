package Project;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

public class Set extends JFrame {

    private JPanel contentPane;
    private JPanel userPanel;
    private JPanel learningProgressPanel;
    private JPanel topicsPanel;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Set frame = new Set();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public Set() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 635); // set frame size
        setResizable(false); // make the window non-resizable
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        // Create User panel
        userPanel = new JPanel();
        userPanel.setBounds(104, 0, 696, 596);
        userPanel.setLayout(null);
        userPanel.setVisible(false);  // initially hidden
        contentPane.add(userPanel);
        
        JLabel lblUserId = new JLabel("User ID:");
        lblUserId.setBounds(20, 20, 60, 15);
        userPanel.add(lblUserId);

        // Create Learning Progress panel
        learningProgressPanel = new JPanel();
        learningProgressPanel.setBounds(104, 0, 696, 596);
        learningProgressPanel.setLayout(null);
        learningProgressPanel.setVisible(false);  // initially hidden
        contentPane.add(learningProgressPanel);

        JLabel lblLearningProgress = new JLabel("Learning Progress:");
        lblLearningProgress.setBounds(20, 20, 150, 15);
        learningProgressPanel.add(lblLearningProgress);

        // Create Topics panel
        topicsPanel = new JPanel();
        topicsPanel.setBounds(104, 0, 696, 596);
        topicsPanel.setLayout(null);
        topicsPanel.setVisible(false);  // initially hidden
        contentPane.add(topicsPanel);

        JLabel lblTopics = new JLabel("Topics:");
        lblTopics.setBounds(20, 20, 60, 15);
        topicsPanel.add(lblTopics);

        JButton btnUser = new JButton("User");
        btnUser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Show User panel and hide others when User button is clicked
                userPanel.setVisible(true);
                learningProgressPanel.setVisible(false);
                topicsPanel.setVisible(false);
            }
        });
        btnUser.setBounds(0, 0, 104, 200);
        contentPane.add(btnUser);
        
        JButton btnLearningProgress = new JButton("<html>Learning<br />Progress</html>");
        btnLearningProgress.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Show Learning Progress panel and hide others when Learning Progress button is clicked
                userPanel.setVisible(false);
                learningProgressPanel.setVisible(true);
                topicsPanel.setVisible(false);
            }
        });
        btnLearningProgress.setBounds(0, 200, 104, 200);
        contentPane.add(btnLearningProgress);

        JButton btnTopics = new JButton("Topics");
        btnTopics.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Show Topics panel and hide others when Topics button is clicked
                userPanel.setVisible(false);
                learningProgressPanel.setVisible(false);
                topicsPanel.setVisible(true);
            }
        });
        btnTopics.setBounds(0, 400, 104, 200);
        contentPane.add(btnTopics);
    }
}
