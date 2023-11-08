package Project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Properties;

public class user extends JFrame {

    private JButton goBackButton;
    private JPanel UserPanel;
    private JLabel profile_image;
    private JPanel rightPanel;
    private JPanel leftPanel;
    private JPanel leftInnerPanel;
    private JTextField username_entry;
    private JLabel username_label;
    private JTextField userid_entry;
    private JTextField email_entry;
    private JTextField age_entry;
    private JTextField level_entry;
    private JLabel userid_label;
    private JLabel email_label;
    private JLabel age_label;
    private JLabel level_label;
    private JButton saveUserButton;


    private void saveUserInfo(String userName, String userId, String userEmail, String userAge, String userLevel) {
        Properties properties = new Properties();
        properties.setProperty("userName", userName);
        properties.setProperty("userId", userId);
        properties.setProperty("userEmail", userEmail);
        properties.setProperty("userAge", userAge);
        properties.setProperty("userLevel", userLevel);

        try (FileOutputStream out = new FileOutputStream("userInfo.properties")) {
            properties.store(out, "User Information");
        } catch (IOException e) {
            // Handle the exception appropriately
        }
    }


    private void loadUserInfo() {
        Properties properties = new Properties();
        try (FileInputStream in = new FileInputStream("userInfo.properties")) {
            properties.load(in);
            username_entry.setText(properties.getProperty("userName"));
            userid_entry.setText(properties.getProperty("userId"));
            email_entry.setText(properties.getProperty("userEmail"));
            age_entry.setText(properties.getProperty("userAge"));
            level_entry.setText(properties.getProperty("userLevel"));
        } catch (IOException e) {
            // Handle the exception appropriately


        }
    }

    public user() {
        setContentPane(UserPanel);
        setTitle("LangLearn! - User");

        // Sizeeee
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		setBounds(100, 100, 1600, 900);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        ///////////////////////////////////////////

        goBackButton.setOpaque(false);
        goBackButton.setContentAreaFilled(false);
        goBackButton.setBorderPainted(false);


        goBackButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Set the cursor to a hand cursor when the mouse enters the button
                goBackButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Set the cursor back to the default cursor when the mouse exits the button
                goBackButton.setCursor(Cursor.getDefaultCursor());
            }
        });

        ///////////////////////////////////////////
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                if (getExtendedState() != JFrame.MAXIMIZED_BOTH) {
                    // If window is not maximized, give it fixed padding
                    int padding = 50; // Adjust this value as per your requirement
                    setBounds(padding, padding,
                            Toolkit.getDefaultToolkit().getScreenSize().width - 2 * padding,
                            Toolkit.getDefaultToolkit().getScreenSize().height - 2 * padding);
                }
            }
        });
        //////////////////////////////////////////////
        goBackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        try {
                            LanguageLearningMain frame = new LanguageLearningMain();
                            frame.setVisible(true);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                // Close the current window
                dispose();
            }
        });

        profile_image.setBounds(592, 60, 400, 400);
        try {
            URL imgURL = getClass().getResource("/Project/profile.png");
            if (imgURL != null) {
                ImageIcon originalIcon = new ImageIcon(imgURL);
                Image scaledImage = originalIcon.getImage().getScaledInstance(profile_image.getWidth(), profile_image.getHeight(), Image.SCALE_SMOOTH);
                ImageIcon scaledIcon = new ImageIcon(scaledImage);
                profile_image.setIcon(scaledIcon);
            } else {
                System.err.println("Couldn't find file: " + "/Project/pfphold.jpg");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        saveUserButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String userName = username_entry.getText();
                String userId = "100001";
                String userEmail = email_entry.getText();
                String userAge = age_entry.getText();
                String userLevel = level_entry.getText();
                if (Objects.equals(userLevel, "")) {
                    userLevel = "Beginner";
                }


                // Save the Project.user information
                saveUserInfo(userName, userId, userEmail, userAge, userLevel);
                saveProgress();

                // Optional: Provide feedback to the Project.user
                JOptionPane.showMessageDialog(null, "User information saved!");
                loadUserInfo();
            }
        });

        loadUserInfo();


    }

    public void saveProgress() {
        int current_progress;
        Properties properties = new Properties();

        try (FileInputStream in = new FileInputStream("gram_progress.properties")) {
            properties.load(in);
            current_progress = Integer.parseInt(properties.getProperty("currentQuestionIndex"));

        } catch (IOException e) {
            current_progress = 0;

        }

        try (FileInputStream in = new FileInputStream("phra_progress.properties")) {
            properties.load(in);
            current_progress += Integer.parseInt(properties.getProperty("currentIndex"));

        } catch (IOException ignored) {

        }

        Path path = Paths.get("progress.txt");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String dateTimeString = LocalDateTime.now().format(formatter);
        String line = dateTimeString + "," + current_progress;
        try {
            // If file doesn't exist, create it. If it exists, append the line.
            if (Files.notExists(path)) {
                Files.createFile(path);
            }
            Files.write(path, (line + System.lineSeparator()).getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    user frame = new user();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
