import javax.swing.*;
import java.awt.*;

public class ChatFrameExample {
    public static void main(String[] args) {
        // Create a JFrame
        JFrame frame = new JFrame("Chat Interface Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(3000, 2100); // Set frame size to three times larger

        // Main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Top header panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.DARK_GRAY);
        headerPanel.setPreferredSize(new Dimension(frame.getWidth(), 150)); // Increased height
        JLabel headerLabel = new JLabel("Header", JLabel.CENTER);
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 72)); // Increased font size
        headerPanel.add(headerLabel);

        // Left panel with a scrollable chat list
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        for (int i = 1; i <= 20; i++) { // Add dummy chat list items
            JLabel chatLabel = new JLabel("Chat " + i);
            chatLabel.setFont(new Font("Arial", Font.PLAIN, 36)); // Increased font size
            leftPanel.add(chatLabel);
        }
        JScrollPane chatScrollPane = new JScrollPane(leftPanel);
        chatScrollPane.setPreferredSize(new Dimension(900, frame.getHeight())); // Increased width
        chatScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Right panel (chat content area) with a scrollable message area
        JPanel chatContentPanel = new JPanel(new BorderLayout());
        chatContentPanel.setBackground(Color.LIGHT_GRAY);

        // Create a panel to hold messages
        JPanel messagePanel = new JPanel();
        messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.Y_AXIS));

        // Add some dummy messages, alternating left and right alignment
        for (int i = 1; i <= 10; i++) {
            JPanel messageContainer = new JPanel();
            messageContainer.setLayout(new FlowLayout(FlowLayout.LEFT)); // Default left alignment for odd messages
            if (i % 2 == 0) {
                messageContainer.setLayout(new FlowLayout(FlowLayout.RIGHT)); // Align to the right for even-indexed messages
            }

            // Create a label to hold the message
            String message = "Message " + i + ". This is a long message that might span multiple lines. Let's see how it wraps!";
            JLabel messageLabel = new JLabel("<html>" + message.replace("\n", "<br>") + "</html>"); // HTML formatting for line breaks
            messageLabel.setOpaque(true);
            messageLabel.setBackground(Color.CYAN);
            messageLabel.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30)); // Increased padding

            // Set the message label properties to wrap text
            messageLabel.setFont(new Font("Arial", Font.PLAIN, 48)); // Increased font size
            messageLabel.setMaximumSize(new Dimension(2100, Short.MAX_VALUE)); // Increased width
            messageLabel.setPreferredSize(new Dimension(2100, messageLabel.getPreferredSize().height));

            // Add the label to the message container
            messageContainer.add(messageLabel);
            messageContainer.setBackground(Color.LIGHT_GRAY); // Match background to blend
            messagePanel.add(messageContainer);
        }

        // Wrap the message panel in a JScrollPane and hide the scrollbars
        JScrollPane messageScrollPane = new JScrollPane(messagePanel);
        messageScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        messageScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Input area at the bottom right
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JTextField inputField = new JTextField(90); // Increased width
        inputField.setFont(new Font("Arial", Font.PLAIN, 48)); // Increased font size
        JButton sendButton = new JButton("Send");
        sendButton.setFont(new Font("Arial", Font.PLAIN, 48)); // Increased font size
        inputPanel.add(inputField);
        inputPanel.add(sendButton);
        inputPanel.setPreferredSize(new Dimension(chatContentPanel.getWidth(), 150)); // Increased height

        // Add components to the chat content panel
        chatContentPanel.add(messageScrollPane, BorderLayout.CENTER);
        chatContentPanel.add(inputPanel, BorderLayout.SOUTH);

        // Add all panels to the main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(chatScrollPane, BorderLayout.WEST);
        mainPanel.add(chatContentPanel, BorderLayout.CENTER);

        // Add the main panel to the frame
        frame.add(mainPanel);
        frame.setVisible(true); // Make the frame visible
    }
}
