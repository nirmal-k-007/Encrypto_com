import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.function.LongFunction;
import javax.swing.*;

public class Signup extends JFrame {

    static UserData userdata;
    static ObjectMapper om = new ObjectMapper();
    int op = 0;

    static boolean sendOTP(String email) throws JsonProcessingException
    {
        if(!email.isEmpty()) {
            userdata = new UserData(email,null,null,null,null);
            String obj = new String(om.writeValueAsString(userdata));
            String response = Sign.makePostRequest("http://localhost:8080/generation",obj);
            if(response.equals("Generated"))
            {
                JOptionPane.showMessageDialog(null, "Otp Sent to " + email);
                return true;
            } else if (response.equals("Error")) {
                JOptionPane.showMessageDialog(null, "Some Error Occured");
                return false;
            } else {
                JOptionPane.showMessageDialog(null,response);
                return false;
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "Email box cannot be empty");
            return false;
        }
    }

    static boolean verifyOTP(String otp) throws JsonProcessingException {
        if(!otp.isEmpty()) {
            userdata.setOtp(otp);
            String obj = new String(om.writeValueAsString(userdata));
            String response = Sign.makePostRequest("http://localhost:8080/verification",obj);
            JOptionPane.showMessageDialog(null, response);
            if(response.equals("OTP Verified"))
            {
                return true;
            }
            return false;
        }
        else{
            JOptionPane.showMessageDialog(null, "Otp box cannot be empty");
            return false;
        }
    }


    Signup()
    {


        JLabel label = new JLabel("Sign up", JLabel.CENTER);
        label.setBounds(0, 200, 1000, 50);
        label.setFont(new Font("Arial", Font.BOLD, 30));
        add(label);

        JLabel elabel = new JLabel("Email");
        elabel.setBounds(100, 500, 200, 50);
        elabel.setFont(new Font("Arial", Font.PLAIN, 30));
        add(elabel);

        JTextField email = new JTextField(30);
        email.setFont(new Font("Arial", Font.PLAIN, 20));
        email.setBounds(300, 500, 400, 50);
        add(email);

        JLabel olabel = new JLabel("OTP");
        olabel.setBounds(100, 600, 200, 50);
        olabel.setFont(new Font("Arial", Font.PLAIN, 30));
        olabel.setVisible(false);
        add(olabel);

        JPasswordField otp = new JPasswordField(30);
        otp.setFont(new Font("Arial", Font.PLAIN, 20));
        otp.setBounds(300, 600, 400, 50);
        otp.setVisible(false);
        add(otp);

        JButton btn = new JButton("Submit");
        btn.setFont(new Font("Arial", Font.PLAIN, 20));
        btn.setBounds(400, 700, 200, 50);
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(op==0)
                {
                    try {
                        if(sendOTP(email.getText())){
                            email.setEnabled(false);
                            olabel.setVisible(true);
                            otp.setVisible(true);
                            op = 1;
                        }
                        else{
                            JOptionPane.showMessageDialog(null, "Try Again with Correct Input !!");
                        }
                    } catch (JsonProcessingException ex) {
                        throw new RuntimeException(ex);
                    }
                } else if (op==1) {
                    try {
                        if(verifyOTP(new String(otp.getPassword()))){
                            new RegisterUser(userdata);
                            dispose();
                        }
                    } catch (JsonProcessingException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        add(btn);

        JLabel changeL = new JLabel("Existing User?....");
        changeL.setBounds(370, 800, 300, 50);
        changeL.setFont(new Font("Arial", Font.PLAIN, 20));
        add(changeL);

        JButton changeB = new JButton("Login");
        changeB.setFont(new Font("Arial", Font.PLAIN, 20));
        changeB.setBounds(550, 800, 150, 50);
        add(changeB);

        changeB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Login();
                dispose();
            }
        });

        this.setTitle("ChatApplication");
        this.setLayout(null);
        this.setSize(1000, 1500);
        this.setVisible(true);

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }
    public static void main(String[] args) {
        new Signup();
    }
}
