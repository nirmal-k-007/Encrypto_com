import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.security.*;
import java.util.Base64;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.function.LongFunction;
import javax.swing.*;

public class RegisterUser extends JFrame {






    RegisterUser(UserData userdata)
    {
        JLabel label = new JLabel("Register User", JLabel.CENTER);
        label.setBounds(0, 200, 1000, 50);
        label.setFont(new Font("Arial", Font.BOLD, 30));
        add(label);

        JLabel nlabel = new JLabel("Name");
        nlabel.setBounds(100, 300, 200, 50);
        nlabel.setFont(new Font("Arial", Font.PLAIN, 30));
        add(nlabel);

        JTextField name = new JTextField(30);
        name.setFont(new Font("Arial", Font.PLAIN, 20));
        name.setBounds(500, 300, 400, 50);
        add(name);

        JLabel ulabel = new JLabel("Username");
        ulabel.setBounds(100, 400, 200, 50);
        ulabel.setFont(new Font("Arial", Font.PLAIN, 30));
        add(ulabel);

        JTextField username = new JTextField(30);
        username.setFont(new Font("Arial", Font.PLAIN, 20));
        username.setBounds(500, 400, 400, 50);
        add(username);

        JLabel plabel = new JLabel("Password");
        plabel.setBounds(100, 500, 200, 50);
        plabel.setFont(new Font("Arial", Font.PLAIN, 30));
        add(plabel);

        JPasswordField password = new JPasswordField(30);
        password.setFont(new Font("Arial", Font.PLAIN, 20));
        password.setBounds(500, 500, 400, 50);
        add(password);

        JLabel cplabel = new JLabel("Confirm Password");
        cplabel.setBounds(100, 600, 300, 50);
        cplabel.setFont(new Font("Arial", Font.PLAIN, 30));
        add(cplabel);

        JPasswordField confirmpassword = new JPasswordField(30);
        confirmpassword.setFont(new Font("Arial", Font.PLAIN, 20));
        confirmpassword.setBounds(500, 600, 400, 50);
        add(confirmpassword);

        JButton btn = new JButton("Submit");
        btn.setFont(new Font("Arial", Font.PLAIN, 20));
        btn.setBounds(400, 700, 200, 50);
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(!username.getText().equals("") && !password.getText().equals("") && !confirmpassword.getText().equals("") && !name.getText().equals(""))
                {
                    if(password.getText().equals(confirmpassword.getText()))
                    {

                        userdata.setPwd(Sign.computeSHA256(password.getText()));
                        userdata.setUname(username.getText());
                        userdata.setName(name.getText());

                        String[] keys = Sign.generateKeyPair();
                        String UserCredentials = null;
                        try {
                            UserCredentials = new ObjectMapper().writeValueAsString(new FileObject(userdata.getEmail(),userdata.getPwd(),keys[0]));
                        } catch (JsonProcessingException ex) {
                            throw new RuntimeException(ex);
                        }
                        Sign.writeToFile("ChatApp/AppData/UserCredentials.json",UserCredentials);

                        userdata.setPublic_key(keys[1]);
                        userdata.setActive(false);
                        String obj = null;
                        try {
                            obj = new ObjectMapper().writeValueAsString(userdata);
                        } catch (JsonProcessingException ex) {
                            throw new RuntimeException(ex);
                        }
                        String response = Sign.makePostRequest("http://localhost:8080/registration",obj);
                        JOptionPane.showMessageDialog(null, response);
                        if(response.equals("User Created Successfully!!"))
                        {
                            JOptionPane.showMessageDialog(null, "You have already been signed in\nApp will be closed automatically\nOpen again to enjoy!!");
                            System.exit(0);
                        }
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(RegisterUser.this, "Passwords do not match");
                    }
                }
                else {
                    JOptionPane.showMessageDialog(RegisterUser.this, "Username and Password are Required");
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
//    public static void main(String[] args) {
//        new RegisterUser();
//    }
}
