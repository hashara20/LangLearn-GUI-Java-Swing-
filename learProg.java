package Project;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.List;

public class learProg extends JFrame {
    private JButton goBackButton;
    private JPanel learnPanel;
    private JPanel bottomPanel;
    private JPanel userNamePanel;
    private JPanel userIDPanel;
    private JPanel footerPanel;
    private JPanel footerLeftPanel;
    private JPanel footerRightPanel;
    private JButton calendarButton;
    private JPanel vocabCorrectPanel;
    private JPanel grammarCorrectPanel;
    private JLabel userNameLabel;
    private JLabel userIDLabel;
    private JLabel vocaLabel;
    private JLabel grammarLabel;
    private JLabel daysLabel;
    private JLabel lastLabel;

    private String userName;
    private String userID;
    private String vocabularyCorrectness;
    private String grammarCorrectness;

    List<String> dates;
    List<Integer> progress_per_date;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    learProg frame = new learProg();
                    frame.setVisible(true);
                    frame.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosing(WindowEvent e) {
                            frame.loadData();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public learProg() {
        loadData();
        setContentPane(learnPanel);

        setTitle("LangLearn! - Learning Progress");
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

        calendarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        try {
//                            List<String> names = List.of("Red Segment", "Blue Segment", "Green Segment", "fkhdfhjdf");
//                            List<Integer> values = List.of(50, 30, 20, 10);
                            PieChart frame = new PieChart(dates, progress_per_date);
                            frame.setVisible(true);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                // Close the current window
//                dispose();
            }
        });
        loadPieChartData();


        userNameLabel.setText(userName);
        userIDLabel.setText(userID);
        daysLabel.setText(dates.size()-1 + " Days");
        lastLabel.setText(dates.get(dates.size()-2));


    }

    public void loadPieChartData() {
        String filename = "progress.txt";
        int total_completed_progress = 0;

        dates = new LinkedList<>();
        progress_per_date = new LinkedList<>();

        Map<LocalDate, Integer> progressByDate = new HashMap<>();
        LocalDate currentDate = null;
        LocalDate previousDate = null;
        int previousProgress = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length != 2) {
                    continue; // skip malformed lines
                }

                LocalDate date = LocalDate.parse(parts[0].split(" ")[0]);
                int income = Integer.parseInt(parts[1]);

                if (currentDate != null && !currentDate.equals(date)) {
                    int difference = progressByDate.get(currentDate) - previousProgress;
                    dates.add(currentDate.toString());
                    progress_per_date.add(difference);
                    total_completed_progress += difference;
                    previousProgress = progressByDate.get(currentDate);
                } else if (currentDate == null) {
                    previousProgress = 0;
                }

                currentDate = date;
                progressByDate.put(date, income);
            }

            if (currentDate != null) {
                int difference = progressByDate.get(currentDate) - previousProgress;
                dates.add(currentDate.toString());
                progress_per_date.add(difference);
                total_completed_progress += difference;

//                System.out.println(currentDate + ": " + difference);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        Properties properties = new Properties();
        int total_progress = 0;
        try (FileInputStream in = new FileInputStream("gram_progress.properties")) {
            properties.load(in);
            total_progress = Integer.parseInt(properties.getProperty("totalQuestions"));
        } catch (IOException ignored) {
        }

        try (FileInputStream in = new FileInputStream("phra_progress.properties")) {
            properties.load(in);
            total_progress += Integer.parseInt(properties.getProperty("totalPhrasees"));
        } catch (IOException ignored) {
        }

        if (total_progress == 0)
            total_progress = 55;

        dates.add("Remaining");
        progress_per_date.add(total_progress - total_completed_progress);




    }

    public void loadData() {
        Properties properties = new Properties();
        try (FileInputStream in = new FileInputStream("userInfo.properties")) {
            properties.load(in);
            userName = properties.getProperty("userName");
            userID = properties.getProperty("userId");
        } catch (IOException e) {
            // Handle the exception appropriately
            userName = "";
            userID = "";

        }

        try (FileInputStream in = new FileInputStream("voca_progress.properties")) {
            properties.load(in);
            int totalAnswers = Integer.parseInt(properties.getProperty("totalAnswers"));
            int correctAnswers = Integer.parseInt(properties.getProperty("correctAnswers"));

            float progress = (totalAnswers == 0) ? 0 : ((float) correctAnswers / totalAnswers) * 100;
            vocaLabel.setText(String.format("%.1f%%", progress));
        } catch (IOException e) {
            // Handle the exception appropriately
            vocaLabel.setText("0.0%");

        }

        try (FileInputStream in = new FileInputStream("gram_progress.properties")) {
            properties.load(in);
            int correctAnswers = Integer.parseInt(properties.getProperty("correctAnswers"));
            int currentQuestionIndex = Integer.parseInt(properties.getProperty("currentQuestionIndex"));

            float progress = (currentQuestionIndex == 0) ? 0 : ((float) correctAnswers / currentQuestionIndex) * 100;
            grammarLabel.setText(String.format("%.1f%%", progress));
        } catch (IOException e) {
            // Handle the exception appropriately
            grammarLabel.setText("0.0%");

        }
    }


}
