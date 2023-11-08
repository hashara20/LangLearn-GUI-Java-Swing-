package Project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class phra extends JFrame {
    private JPanel phraPanel;
    private JButton goBackButton;
    private JButton previousButton;
    private JButton nextButton;
    private JPanel topPanel;
    private JPanel bottomPanel;
    private JPanel previousPanel;
    private JPanel contentPanel;
    private JPanel nextPanel;
    private JLabel titleLabel;
    private JLabel contentLabel;


    private String[] phrases = {
            "Break the ice",
            "Bite the bullet",
            "Cut corners",
            "Hit the nail on the head",
            "Let the cat out of the bag",
            "The ball is in your court",
            "Get your act together",
            "Pull yourself together",
            "Jump on the bandwagon",
            "Wrap your head around it",
            "Get out of hand",
            "Break a leg",
            "Call it a day",
            "Go back to the drawing board",
            "Get something out of your system",
            "Burning the midnight oil",
            "Cutting it close",
            "Read between the lines",
            "Add fuel to the fire",
            "Sit tight",
            "Bite off more than you can chew",
            "Break the bank",
            "Don't cry over spilled milk",
            "Kill two birds with one stone",
            "Give the benefit of the doubt",
            "Take it with a grain of salt",
            "Go down in flames",
            "Hit the sack",
            "Out of the frying pan into the fire",
            "You can't make an omelet without breaking a few eggs"
    };

    private String[] meanings = {
            "To initiate social interaction",
            "To face a difficult situation with courage",
            "To do something in the easiest or cheapest way",
            "To describe exactly what is causing a situation or problem",
            "To accidentally reveal a secret",
            "It is your decision now",
            "Work seriously and responsibly",
            "Calm down",
            "Join a popular activity",
            "Understand something complicated",
            "Get out of control",
            "Good luck",
            "Stop working for the day",
            "Start over",
            "Do the thing you've been wanting to do",
            "Work late into the night",
            "Finish just barely on time",
            "Look for deeper or hidden meaning",
            "Worsen a bad situation",
            "Wait patiently",
            "Take on a task that is too big",
            "Expend a lot of effort or money",
            "Don't dwell on past mistakes",
            "Achieve two objectives with one action",
            "Trust someone's honesty",
            "Be skeptical",
            "Fail spectacularly",
            "Go to sleep",
            "Go from one problem to another",
            "It's impossible to achieve something significant without making sacrifices"
    };


    private int currentIndex = 0;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    phra frame = new phra();
                    frame.setVisible(true);
                    frame.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosing(WindowEvent e) {
                            frame.loadProgress();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public phra() {
        setTitle("LangLearn! - Phrasebook");
        setContentPane(phraPanel);
        loadProgress();

        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

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

        titleLabel.setText(phrases[currentIndex]);
        contentLabel.setText(meanings[currentIndex]);

        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                currentIndex++;
                if (currentIndex >= phrases.length) {
                    currentIndex = 0;
                }
                titleLabel.setText(phrases[currentIndex]);
                contentLabel.setText(meanings[currentIndex]);

                saveProgress();
            }
        });

        previousButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                currentIndex--;
                if (currentIndex < 0) {
                    currentIndex = phrases.length - 1;
                }
                titleLabel.setText(phrases[currentIndex]);
                contentLabel.setText(meanings[currentIndex]);
            }
        });


    }

    private void saveProgress() {

        Properties properties = new Properties();
        int prevIndex;
        try {
            prevIndex = Integer.parseInt(properties.getProperty("currentIndex"));
        } catch (Exception e) {
            prevIndex = -1;
        }
        if (prevIndex < currentIndex) {
            properties.setProperty("currentIndex", Integer.toString(currentIndex));
            properties.setProperty("totalPhrasees", Integer.toString(phrases.length));

            try (FileOutputStream out = new FileOutputStream("phra_progress.properties")) {
                properties.store(out, "Progress Information");
            } catch (IOException e) {
                e.printStackTrace();
                // Handle the exception appropriately
            }
        }

    }


    private void loadProgress() {

        Properties properties = new Properties();
        try (FileInputStream in = new FileInputStream("phra_progress.properties")) {
            properties.load(in);
            currentIndex = Integer.parseInt(properties.getProperty("currentIndex"));
        } catch (IOException e) {
            // Handle the exception appropriately
            currentIndex = 0;
        }
    }
}

