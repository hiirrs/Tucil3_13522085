import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public class WordLadderGUI extends JFrame implements ActionListener {
    private JTextField startField, endField;
    private JButton ucsButton, gbfsButton, aStarButton;
    private JPanel resultPanel;
    private JLabel algorithmLabel;
    private JLabel visitedNodesLabel; 
    private JLabel pathLabel; 
    private JLabel timeLabel; 
    private Set<String> wordSet;

    public WordLadderGUI(Set<String> wordSet) {
        super("Word Ladder Game");
        this.wordSet = wordSet;

        startField = createTextField();
        endField = createTextField();
        ucsButton = new JButton("UCS");
        gbfsButton = new JButton("GBFS");
        aStarButton = new JButton("A*");
        resultPanel = new JPanel(new GridLayout(0, 1)); 

        Font font = new Font("Arial", Font.PLAIN, 14);
        startField.setFont(font);
        endField.setFont(font);
        ucsButton.setFont(font);
        gbfsButton.setFont(font);
        aStarButton.setFont(font);

        ucsButton.setBackground(new Color(110, 105, 103)); 
        gbfsButton.setBackground(new Color(110, 105, 103)); 
        aStarButton.setBackground(new Color(110, 105, 103)); 
        ucsButton.setForeground(Color.WHITE);
        gbfsButton.setForeground(Color.WHITE);
        aStarButton.setForeground(Color.WHITE);

        Font labelFont = new Font("Monospace", Font.BOLD, 14);

        JLabel startLabel = new JLabel("Start Word: ");
        startLabel.setFont(labelFont);

        JLabel endLabel = new JLabel("End Word: ");
        endLabel.setFont(labelFont);

        algorithmLabel = new JLabel("Algorithm: ");
        algorithmLabel.setFont(labelFont);
        algorithmLabel.setMaximumSize(new Dimension(10, 5));

        pathLabel = new JLabel();
        pathLabel.setFont(labelFont);
        pathLabel.setMaximumSize(new Dimension(10, 5));

        visitedNodesLabel = new JLabel();
        visitedNodesLabel.setFont(labelFont);
        visitedNodesLabel.setMaximumSize(new Dimension(10, 5));

        timeLabel = new JLabel();
        timeLabel.setFont(labelFont);
        timeLabel.setMaximumSize(new Dimension(10, 5));

        JPanel inputPanel = new JPanel(new GridLayout(4, 4, 4, 4));
        inputPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        inputPanel.setBackground(Color.WHITE);
        inputPanel.add(startLabel);
        inputPanel.add(startField);
        inputPanel.add(endLabel);
        inputPanel.add(endField);
        inputPanel.add(new JLabel());
        inputPanel.add(ucsButton);
        inputPanel.add(gbfsButton);
        inputPanel.add(aStarButton);

        ucsButton.addActionListener(this);
        gbfsButton.addActionListener(this);
        aStarButton.addActionListener(this);

        ucsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        gbfsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        aStarButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        inputPanel.add(new JLabel());
        inputPanel.add(algorithmLabel);
        inputPanel.add(new JLabel());
        inputPanel.add(pathLabel);
        inputPanel.add(new JLabel());
        inputPanel.add(visitedNodesLabel);
        inputPanel.add(new JLabel());
        inputPanel.add(timeLabel);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(inputPanel, BorderLayout.NORTH);
        getContentPane().add(new JScrollPane(resultPanel), BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static boolean wordExists(String filePath, String word) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().equalsIgnoreCase(word)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private JTextField createTextField() {
        JTextField textField = new JTextField(10);
        textField.setBorder(new LineBorder(Color.BLACK));
        return textField;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String start = startField.getText().trim().toLowerCase();
        String end = endField.getText().trim().toLowerCase();
    
        if (start.length() != end.length()) {
            JOptionPane.showMessageDialog(this, "Make sure the start and end words have the same length", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        if (!start.matches("[a-zA-Z]+") || !end.matches("[a-zA-Z]+")) {
            JOptionPane.showMessageDialog(this, "Please input alphabetic words only", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!wordExists("words.txt", start)) {
            JOptionPane.showMessageDialog(this, "Try other word for the start word", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!wordExists("words.txt", end)) {
            JOptionPane.showMessageDialog(this, "Try other word for the end word", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        Pair<List<String>, Integer> resultPair;
        String endWord = ""; 
        String algorithm = "";
        int visitedNodes = 0;
        long startTime = System.currentTimeMillis();
    
        try {
            if (e.getSource() == ucsButton) {
                resultPair = UCS.optimumSolution(start, end, wordSet);
                algorithm = "UCS";
            } else if (e.getSource() == gbfsButton) {
                resultPair = GBFS.solution(start, end, wordSet);
                algorithm = "GBFS";
            } else {
                resultPair = Astar.optimumSolution(start, end, wordSet);
                algorithm = "A*";
            }
    
            long endTime = System.currentTimeMillis();
    
            List<String> path = resultPair.getKey(); 
            visitedNodes = resultPair.getValue(); 
    
            if (path == null || path.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No path found", "Not Found", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
    
            endWord = endField.getText().trim().toLowerCase();
    
            displayPath(path, endWord, algorithm, visitedNodes, endTime - startTime);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void displayPath(List<String> path, String endWord, String algorithm, int visitedNodes, long elapsedTime) {
        resultPanel.removeAll();
        if (path != null) {
            for (String word : path) {
                JPanel wordPanel = createWordPanel(word.toUpperCase(), endWord.toUpperCase());
                resultPanel.add(wordPanel);
            }
            algorithmLabel.setText("Algorithm: " + algorithm);
            timeLabel.setText("Execution Time: " + elapsedTime + " ms");
            pathLabel.setText("Path Length: " + path.size());
            visitedNodesLabel.setText("Visited Nodes: " + visitedNodes);
        } else {
            resultPanel.add(new JLabel("No path found"));
            timeLabel.setText("Execution Time: " + elapsedTime + " ms");
            visitedNodesLabel.setText("Visited Nodes: " + visitedNodes);
            algorithmLabel.setText("Algorithm: N/A");
        }
        resultPanel.revalidate();
        resultPanel.repaint();
        pack();
    }
    

    

    private JPanel createWordPanel(String word, String endWord) {
        word = word.toUpperCase(); 
        JPanel wordPanel = new JPanel(new GridLayout(1, word.length(), 5, 5));
        wordPanel.setBorder(new LineBorder(Color.WHITE));
        wordPanel.setBackground(Color.WHITE);
        wordPanel.setPreferredSize(new Dimension(30 * word.length(), 30)); 
    
        for (int i = 0; i < word.length(); i++) {
            JPanel letterPanel = createLetterPanel(word.charAt(i), i, endWord.charAt(i), endWord);
            wordPanel.add(letterPanel);
        }
        return wordPanel;
    }

    private JPanel createLetterPanel(char letter, int position, char endLetter, String endWord) {
        JPanel panel = new JPanel();
    
        int panelSize = 30;
        panel.setPreferredSize(new Dimension(panelSize, panelSize));
        panel.setMaximumSize(new Dimension(panelSize, panelSize)); 

        panel.setBackground(new Color(191, 181, 178));
    
        JLabel label = new JLabel(String.valueOf(letter));
        label.setHorizontalAlignment(SwingConstants.CENTER); 
        label.setVerticalAlignment(SwingConstants.CENTER); 
    
        if (position < endWord.length() && letter == endLetter) {
            panel.setBackground(Color.BLACK);
            label.setForeground(Color.WHITE);
        }
    
        panel.add(label); 
        return panel;
    }
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                Set<String> wordSet = WordLadderGame.readWordsFromFile("words.txt");
                new WordLadderGUI(wordSet);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error reading words from file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
