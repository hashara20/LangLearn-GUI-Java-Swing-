package Project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class voca extends JFrame {
    private JPanel vocaPanel;
    private JPanel correctPanel;
    private JButton goBackButton;
    private JButton answer2Button;
    private JButton nextButton;
    private JButton answer1Button;
    private JPanel nextButtonPanel;
    private JPanel wordPanel;
    private JLabel wordLabel;
    private JPanel anwer1Panel;
    private JPanel answer2Panel;
    private JLabel correctPercentageLabel;

    private int correctAnswers = 0;
    private int totalAnswers = 0;

    private HashMap<String, String> vocabulary = new HashMap<String, String>() {{
        put("Incessant", "Uninterrupted");
        put("Aptitude", "Natural ability");
        put("Perfidy", "Betrayal");
        put("Impartial", "Unbiased");
        put("Affinity", "Natural attraction");
        put("Ambiguous", "Open to more than one interpretation");
        put("Benevolent", "Well-meaning and kind");
        put("Capitulate", "To surrender, often after negotiation");
        put("Dubious", "Hesitating or doubting; not to be relied upon");
        put("Eloquent", "Fluent or persuasive in speaking or writing");
        put("Fickle", "Changing frequently, especially in loyalties");
        put("Gregarious", "Fond of company; sociable");
        put("Harangue", "A lengthy and aggressive speech");
        put("Ineffable", "Too great or extreme to be expressed in words");
        put("Juxtapose", "Place or deal with close together for contrasting effect");
        put("Kaleidoscope", "A constantly changing pattern or sequence of elements");
        put("Languid", "Lacking spirit or liveliness");
        put("Malleable", "Capable of being shaped or bent");
        put("Nefarious", "Wicked or criminal");
        put("Obfuscate", "Render obscure, unclear, or unintelligible");
        put("Palliate", "To make less severe or unpleasant without removing the cause");
        put("Quintessential", "Representing the most perfect example of a quality");
        put("Rhetoric", "Effective writing or speaking");
        put("Sycophant", "A person who acts obsequiously toward someone to gain advantage");
        put("Tenacious", "Tending to keep a firm hold of something; clinging or adhering closely");
        put("Ubiquitous", "Present, appearing, or found everywhere");
        put("Vex", "To annoy, frustrate, or worry");
        put("Whimsical", "Playfully quaint or fanciful, especially in an appealing and amusing way");
        put("Xenophobic", "Having or showing a dislike of or prejudice against people from other countries");
        put("Yearn", "To have an intense feeling of desire for something, typically something that one has lost or been separated from");
        put("Zealous", "Having or showing zeal; fervent");
        put("Apathetic", "Showing or feeling no interest, enthusiasm, or concern");
        put("Blasé", "Unimpressed or indifferent to something because one has experienced or seen it so often before");
        put("Cognizant", "Having knowledge or being aware of");
        put("Dearth", "A scarcity or lack of something");
        put("Exacerbate", "To make a situation worse");
        put("Facetious", "Treating serious issues with deliberately inappropriate humor");
        put("Gravitas", "Dignity, seriousness, or solemnity of manner");
        put("Hubris", "Excessive pride or self-confidence");
        put("Intricate", "Very complicated or detailed");
        put("Jovial", "Cheerful and friendly");
        put("Knack", "An acquired or natural skill at performing a task");
        put("Lucrative", "Producing a great deal of profit");
        put("Myriad", "A countless or extremely great number");
        put("Nuance", "A subtle difference in or shade of meaning, expression, or sound");
        put("Opaque", "Not able to be seen through; not transparent");
        put("Pernicious", "Having a harmful effect, especially in a gradual or subtle way");
        put("Quintuple", "Consisting of five parts or things");
        put("Rambunctious", "Uncontrollably exuberant; boisterous");
        put("Sagacious", "Having or showing keen mental discernment and good judgment; wise");
        put("Taciturn", "Reserved or uncommunicative in speech; saying little");
        put("Voracious", "Wanting or devouring great quantities of food; having a very eager approach to an activity");
        put("Wistful", "Having or showing a feeling of vague or regretful longing");
        put("Zealot", "A person who is fanatical and uncompromising in pursuit of their religious, political, or other ideals");
    }};

    private String correctWord;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    voca frame = new voca();
                    frame.setVisible(true);
                    frame.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosing(WindowEvent e) {
                            frame.loadProgress();
                        }
                    });
                    frame.refreshQuiz();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public voca() {
        loadProgress();
        setContentPane(vocaPanel);

        setTitle("LangLearn! - Vocabulary");
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

        answer1Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                totalAnswers++;
                if (answer1Button.getText().equals(vocabulary.get(correctWord))) {
                    correctAnswers++;
                    wordLabel.setForeground(Color.GREEN);
                } else {
                    wordLabel.setForeground(Color.RED);
                }
                updateProgressLabel();
                answer1Button.setEnabled(false);
                answer2Button.setEnabled(false);
            }
        });

        answer2Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                totalAnswers++;
                if (answer2Button.getText().equals(vocabulary.get(correctWord))) {
                    correctAnswers++;
                    wordLabel.setForeground(Color.GREEN);
                } else {
                    wordLabel.setForeground(Color.RED);
                }
                updateProgressLabel();
                answer1Button.setEnabled(false);
                answer2Button.setEnabled(false);
            }
        });

        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                refreshQuiz();
                answer1Button.setEnabled(true);  // Re-enable the button
                answer2Button.setEnabled(true);  // Re-enable the button
            }
        });

        updateProgressLabel();
        refreshQuiz();

    }

    public void refreshQuiz() {
        Random rand = new Random();
        Object[] keys = vocabulary.keySet().toArray();
        correctWord = (String) keys[rand.nextInt(keys.length)];
        String correctDefinition = vocabulary.get(correctWord);

        List<String> wrongDefinitions = new ArrayList<>(vocabulary.values());
        wrongDefinitions.remove(correctDefinition);

        String wrongDefinition = wrongDefinitions.get(rand.nextInt(wrongDefinitions.size()));
        wordLabel.setText(correctWord);
        wordLabel.setForeground(Color.BLACK);

        if (rand.nextBoolean()) {
            answer1Button.setText(correctDefinition);
            answer2Button.setText(wrongDefinition);
        } else {
            answer2Button.setText(correctDefinition);
            answer1Button.setText(wrongDefinition);
        }
    }

    private void updateProgressLabel() {
        float progress = (totalAnswers == 0) ? 0 : ((float) correctAnswers / totalAnswers) * 100;
        correctPercentageLabel.setText(String.format("%.1f%%", progress));
        saveProgress();
    }

    private void saveProgress() {

        Properties properties = new Properties();
        properties.setProperty("correctAnswers", Integer.toString(correctAnswers));
        properties.setProperty("totalAnswers", Integer.toString(totalAnswers));

        try (FileOutputStream out = new FileOutputStream("voca_progress.properties")) {
            properties.store(out, "Progress Information");
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }
    }



    private void loadProgress() {

        Properties properties = new Properties();
        try (FileInputStream in = new FileInputStream("voca_progress.properties")) {
            properties.load(in);
            correctAnswers = Integer.parseInt(properties.getProperty("correctAnswers"));
            totalAnswers = Integer.parseInt(properties.getProperty("totalAnswers"));
        } catch (IOException e) {
            // Handle the exception appropriately
            correctAnswers = 0;
            totalAnswers = 0;

        }
    }


}
