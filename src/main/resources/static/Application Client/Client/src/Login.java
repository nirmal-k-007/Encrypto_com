import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.function.LongFunction;
import javax.swing.*;

public class Login extends JFrame {

    static void loginVerification(String email,String password) throws JsonProcessingException {
        if(!email.isEmpty() && !password.isEmpty()) {
            ObjectMapper om = new ObjectMapper();
            String obj = new String(om.writeValueAsString(new UserData(email,Sign.computeSHA256(password),null,null,null)));
            String response = Sign.makePostRequest("http://localhost:8080/login",obj);
            if(!response.equals("Wrong Password Entered") && !response.equals("User Not Registered\nKindly Sign Up"))
            {
                String[] keys = Sign.generateKeyPair();
                System.out.println("Private : "+keys[0]);
                System.out.println("Public : "+keys[1]);
                String UserCredentials = om.writeValueAsString(new FileObject(email,password,keys[0]));
                Sign.writeToFile("ChatApp/AppData/UserCredentials.json",UserCredentials);
                String newData = om.writeValueAsString(new UpdateObject(email,Sign.computeSHA256(password),keys[1]));
                if(Sign.makePostRequest("http://localhost:8080/updateCredentials",newData).equals("User Updated Successfully!!"))
                {
                    JOptionPane.showMessageDialog(null, "Login Successful !!");
                }
                else {
                    JOptionPane.showMessageDialog(null, "Login Unsuccessful !!\nSecurity Error");
                }

            }
            else
            {
                JOptionPane.showMessageDialog(null, response);
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "Email or Password box cannot be empty");
        }
    }



    Login()
    {
        JLabel label = new JLabel("Log In", JLabel.CENTER);
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

        JLabel plabel = new JLabel("Password");
        plabel.setBounds(100, 600, 200, 50);
        plabel.setFont(new Font("Arial", Font.PLAIN, 30));
        add(plabel);

        JPasswordField password = new JPasswordField(30);
        password.setFont(new Font("Arial", Font.PLAIN, 20));
        password.setBounds(300, 600, 400, 50);
        add(password);

        JButton btn = new JButton("Submit");
        btn.setFont(new Font("Arial", Font.PLAIN, 20));
        btn.setBounds(400, 700, 200, 50);
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    loginVerification(email.getText(),new String(password.getPassword()));
                } catch (JsonProcessingException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        add(btn);

        JLabel changeL = new JLabel("New user?....");
        changeL.setBounds(370, 800, 300, 50);
        changeL.setFont(new Font("Arial", Font.PLAIN, 20));
        add(changeL);

        JButton changeB = new JButton("Sign Up");
        changeB.setFont(new Font("Arial", Font.PLAIN, 20));
        changeB.setBounds(550, 800, 150, 50);
        add(changeB);

        changeB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Signup();
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
        new Login();
    }
}
