package Project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;

public class gram extends JFrame {

    private Random random = new Random();

    private String[] correctSentences = {
            "I am going to the market.",
            "He is taller than she is.",
            "The cat lies on the mat.",
            "You should bring your coat.",
            "She writes beautifully.",
            "They're going to the store.",
            "He's better at this than I am.",
            "Who are you?",
            "Whose book is this?",
            "I could have done better.",
            "I have fewer pencils than you.",
            "She has less money.",
            "It's raining outside.",
            "They're all good options.",
            "I'll take two pieces of cake.",
            "There are many reasons.",
            "You're looking well.",
            "I've been waiting for an hour.",
            "She was affected by the news.",
            "They're over there with their friends.",
            "The company is laying off workers.",
            "The principal is your pal.",
            "It's a serious problem.",
            "You and I should go.",
            "She neither called nor wrote."
    };

    private String[] incorrectSentences = {
            "I am go to the market.",
            "He is taller then she is.",
            "The cat lays on the mat.",
            "You should take you're coat.",
            "She writes good.",
            "Their going to the store.",
            "He's better at this then me am.",
            "Who is you?",
            "Who's book is this?",
            "I could of done better.",
            "I have less pencils then you.",
            "She has fewer money.",
            "Its raining outside.",
            "There all good options.",
            "I'll take to pieces of cake.",
            "There is many reasons.",
            "Your looking good.",
            "I've been waiting since an hour.",
            "She was effected by the news.",
            "Their over they're with there friends.",
            "The company is lieing off workers.",
            "The principle is your pal.",
            "Its a serious problem.",
            "You and me should go.",
            "She neither called or wrote."
    };

    private String[] explanations = {
            "The correct form is 'going to', not 'go to'.",
            "'Than' is the correct word when making comparisons, not 'then'.",
            "'Lies' is the correct word when there is no direct object.",
            "'Your' is possessive; 'you're' means 'you are'.",
            "'Beautifully' is the correct adverb form of 'beautiful'.",
            "'They're' is short for 'they are'; 'their' is possessive.",
            "'Than' is used for comparison; 'I am' is the correct pronoun and verb.",
            "'Who' is the correct subject pronoun.",
            "'Whose' is possessive; 'who's' means 'who is' or 'who has'.",
            "'Could have' is correct; 'could of' is incorrect.",
            "'Fewer' is for countable objects; 'than' is for comparison.",
            "'Less' is for uncountable objects.",
            "'It's' means 'it is'; 'its' is possessive.",
            "'They're' means 'they are'; 'all' refers to 'options'.",
            "'Two' is the correct spelling; 'pieces' is the correct plural form.",
            "'There are' is correct for plural objects.",
            "'You're looking well' is correct for describing health.",
            "'For an hour' is correct; 'since' is used with a specific past time.",
            "'Affected' is the correct word; 'effected' means 'brought about'.",
            "'They're' means 'they are'; 'over there' is a location; 'their' is possessive.",
            "'Laying off' is the correct form; 'lieing' is not a word.",
            "'Principal' is a head or leader; 'pal' is a mnemonic device.",
            "'It's' means 'it is'; 'its' is possessive.",
            "'You and I' is the correct subject; 'should go' is the correct verb phrase.",
            "'Neither' is paired with 'nor', not 'or'."
    };


    private int currentQuestionIndex = 0;

    private JButton goBackButton;
    private JPanel topPanel;
    private JButton answer1Buttton;
    private JButton answer2Buttton;
    private JButton nextButton;
    private JPanel answer1Panel;
    private JPanel answer2Panel;
    private JPanel feedbackPanel;
    private JLabel feedbackLabel;
    private JPanel gramPanel;

    private int correctAnswers = 0;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    gram frame = new gram();
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

    public gram() {
        loadProgress();

        setTitle("LangLearn! - Grammar");
        setContentPane(gramPanel);

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

        answer1Buttton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                checkAnswer(answer1Buttton.getText());
            }
        });

        answer2Buttton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                checkAnswer(answer2Buttton.getText());
            }
        });

        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadNewQuestion();
            }
        });

        loadNewQuestion();

    }

    private void saveProgress() {

        Properties properties = new Properties();
        int prevQuestionIndex;
        try {
            prevQuestionIndex = Integer.parseInt(properties.getProperty("currentQuestionIndex"));
        } catch (Exception e) {
            prevQuestionIndex = -1;
        }
        if (prevQuestionIndex < currentQuestionIndex) {
            properties.setProperty("correctAnswers", Integer.toString(correctAnswers));
            properties.setProperty("currentQuestionIndex", Integer.toString(currentQuestionIndex));
            properties.setProperty("totalQuestions", Integer.toString(correctSentences.length));

            try (FileOutputStream out = new FileOutputStream("gram_progress.properties")) {
                properties.store(out, "Progress Information");
            } catch (IOException e) {
                e.printStackTrace();
                // Handle the exception appropriately
            }
        }

    }


    private void loadProgress() {

        Properties properties = new Properties();
        try (FileInputStream in = new FileInputStream("gram_progress.properties")) {
            properties.load(in);
            correctAnswers = Integer.parseInt(properties.getProperty("correctAnswers"));
            currentQuestionIndex = Integer.parseInt(properties.getProperty("currentQuestionIndex"));
        } catch (IOException e) {
            // Handle the exception appropriately
            correctAnswers = 0;
            currentQuestionIndex = 0;

        }
    }

    private void loadNewQuestion() {
//        currentQuestionIndex = random.nextInt(correctSentences.length);
        if (random.nextBoolean()) {
            answer1Buttton.setText(correctSentences[currentQuestionIndex]);
            answer2Buttton.setText(incorrectSentences[currentQuestionIndex]);
        } else {
            answer1Buttton.setText(incorrectSentences[currentQuestionIndex]);
            answer2Buttton.setText(correctSentences[currentQuestionIndex]);
        }

        feedbackLabel.setText("");
        answer1Buttton.setBackground(null);
        answer2Buttton.setBackground(null);
    }

    private void checkAnswer(String selectedSentence) {
        if (selectedSentence.equals(correctSentences[currentQuestionIndex])) {
            correctAnswers++;
            feedbackLabel.setText("Correct! " + explanations[currentQuestionIndex]);
            answer1Buttton.setBackground(selectedSentence.equals(answer1Buttton.getText()) ? Color.GREEN : null);
            answer2Buttton.setBackground(selectedSentence.equals(answer2Buttton.getText()) ? Color.GREEN : null);
        } else {
            feedbackLabel.setText("Incorrect! " + explanations[currentQuestionIndex]);
            answer1Buttton.setBackground(selectedSentence.equals(answer1Buttton.getText()) ? Color.RED : null);
            answer2Buttton.setBackground(selectedSentence.equals(answer2Buttton.getText()) ? Color.RED : null);
        }
        currentQuestionIndex++;
        saveProgress();

    }

}
