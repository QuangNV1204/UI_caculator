import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CalculatorUI extends JFrame {

    private JTextField displayField;
    private JLabel historyLabel;

    public CalculatorUI() {
        initUI();
    }

    private void initUI() {
        setTitle("Máy Tính Bỏ Túi");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(340, 520);
        setMinimumSize(new Dimension(300, 480));
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(24, 24, 27));
        setLayout(new BorderLayout(0, 10));
        JPanel screenPanel = createScreenPanel();
        add(screenPanel, BorderLayout.NORTH);

        JPanel keypadPanel = createKeypadPanel();
        add(keypadPanel, BorderLayout.CENTER);
    }

    private JPanel createScreenPanel() {
        JPanel screenPanel = new JPanel();
        screenPanel.setLayout(new BorderLayout(0, 5));
        screenPanel.setBackground(new Color(32, 33, 36));
        screenPanel.setBorder(new EmptyBorder(25, 20, 20, 20));

        historyLabel = new JLabel(" ");
        historyLabel.setFont(new Font("SansSerif", Font.PLAIN, 15));
        historyLabel.setForeground(new Color(156, 163, 175));
        historyLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        displayField = new JTextField("0");
        displayField.setEditable(false);
        displayField.setBackground(new Color(32, 33, 36));
        displayField.setForeground(Color.WHITE);
        displayField.setFont(new Font("SansSerif", Font.BOLD, 38));
        displayField.setHorizontalAlignment(SwingConstants.RIGHT);
        displayField.setBorder(null);

        screenPanel.add(historyLabel, BorderLayout.NORTH);
        screenPanel.add(displayField, BorderLayout.CENTER);

        return screenPanel;
    }
    private JPanel createKeypadPanel() {
        JPanel keypadPanel = new JPanel();
        keypadPanel.setBackground(new Color(24, 24, 27));
        keypadPanel.setLayout(new GridLayout(5, 4, 10, 10));
        keypadPanel.setBorder(new EmptyBorder(10, 16, 18, 16));
        String[][] buttons = {
            {"C", "⌫", "%", "÷"},
            {"7", "8", "9", "×"},
            {"4", "5", "6", "−"},
            {"1", "2", "3", "+"},
            {"±", "0", ".", "="}
        };
        Color numberBg = new Color(39, 39, 42);
        Color numberFg = Color.WHITE;

        Color funcBg = new Color(63, 63, 70);
        Color funcFg = new Color(244, 244, 245);

        Color opBg = new Color(249, 115, 22);
        Color opFg = Color.WHITE;

        Color equalBg = new Color(34, 197, 94);
        Color equalFg = Color.WHITE;

        Font btnFont = new Font("SansSerif", Font.BOLD, 20);

        for (String[] row : buttons) {
            for (String text : row) {
                Color bg;
                Color fg;

                if (text.equals("÷") || text.equals("×") || text.equals("−") || text.equals("+")) {
                    bg = opBg;
                    fg = opFg;
                } else if (text.equals("=")) {
                    bg = equalBg;
                    fg = equalFg;
                } else if (text.equals("C") || text.equals("⌫") || text.equals("%") || text.equals("±")) {
                    bg = funcBg;
                    fg = funcFg;
                } else {
                    bg = numberBg;
                    fg = numberFg;
                }

                JButton button = new RoundedButton(text, bg, fg, btnFont);
                keypadPanel.add(button);
            }
        }

        return keypadPanel;
    }

    private static class RoundedButton extends JButton {
        private final Color normalColor;
        private final Color hoverColor;
        private final Color pressedColor;
        private final int cornerRadius = 18;

        public RoundedButton(String text, Color bgColor, Color fgColor, Font font) {
            super(text);
            this.normalColor = bgColor;
            this.hoverColor = brighten(bgColor, 0.18f);
            this.pressedColor = darken(bgColor, 0.18f);

            setFont(font);
            setForeground(fgColor);
            setFocusPainted(false);
            setContentAreaFilled(false);
            setBorderPainted(false);
            setCursor(new Cursor(Cursor.HAND_CURSOR));

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    setBackground(hoverColor);
                    repaint();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    setBackground(normalColor);
                    repaint();
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    setBackground(pressedColor);
                    repaint();
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    if (contains(e.getPoint())) {
                        setBackground(hoverColor);
                    } else {
                        setBackground(normalColor);
                    }
                    repaint();
                }
            });

            setBackground(normalColor);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);

            g2.dispose();
            super.paintComponent(g);
        }

        private Color brighten(Color color, float fraction) {
            int r = Math.min(255, (int) (color.getRed() + (255 - color.getRed()) * fraction));
            int g = Math.min(255, (int) (color.getGreen() + (255 - color.getGreen()) * fraction));
            int b = Math.min(255, (int) (color.getBlue() + (255 - color.getBlue()) * fraction));
            return new Color(r, g, b);
        }

        private Color darken(Color color, float fraction) {
            int r = Math.max(0, (int) (color.getRed() * (1 - fraction)));
            int g = Math.max(0, (int) (color.getGreen() * (1 - fraction)));
            int b = Math.max(0, (int) (color.getBlue() * (1 - fraction)));
            return new Color(r, g, b);
        }
    }
}
