import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.awt.*;
import java.awt.event.*;
import java.util.Base64;
import javax.swing.*;

class FileObject{
    String email,pwd,private_key;

    public FileObject() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPrivate_key() {
        return private_key;
    }

    public void setPrivate_key(String private_key) {
        this.private_key = private_key;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    FileObject(String email, String pwd, String private_key) {
        this.email = email;
        this.pwd = pwd;
        this.private_key = private_key;
    }
}

class UpdateObject{
    String email,pwd,public_key;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getPublic_key() {
        return public_key;
    }

    public void setPublic_key(String public_key) {
        this.public_key = public_key;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public UpdateObject(String email, String pwd, String public_key) {
        this.email = email;
        this.pwd = pwd;
        this.public_key = public_key;
    }
}

class UserData{
    String email,pwd,uname,otp,name,public_key=" ";
    boolean active;

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public UserData(String email, String pwd, String uname, String otp, String name, String public_key, boolean active) {
        this.email = email;
        this.pwd = pwd;
        this.uname = uname;
        this.otp = otp;
        this.name = name;
        this.public_key = public_key;
        this.active = active;
    }

    public UserData(String email, String pwd, String uname, String otp, String name) {
        this.email = email;
        this.pwd = pwd;
        this.uname = uname;
        this.otp = otp;
        this.name = name;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPublic_key() {
        return public_key;
    }

    public void setPublic_key(String public_key) {
        this.public_key = public_key;
    }



    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

public class Sign extends JFrame {

    static UserData userdata;
    static ObjectMapper om = new ObjectMapper();


    public static void writeToFile(String fileName, String text) {
        // Create a File object with the provided file name
        File file = new File(fileName);

        // Get the parent directory of the file
        File parentDir = file.getParentFile();

        try {
            // Check if the parent directory exists, if not, create it
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs(); // Create the directories
            }

            // Create the file if it does not exist
            if (!file.exists()) {
                file.createNewFile();
            }

            // Use FileWriter and BufferedWriter to write to the file
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            // Write the provided text to the file
            bufferedWriter.write(text);

            // Close the BufferedWriter to release resources
            bufferedWriter.close();

            System.out.println("Text written to " + fileName + " successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String computeSHA256(String input) {
        try {
            // Create a MessageDigest instance for SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // Compute the hash of the input string
            byte[] hashBytes = digest.digest(input.getBytes());

            // Convert the byte array to a hexadecimal string
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                // Convert each byte to a two-digit hexadecimal representation
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            // Return the SHA-256 hash as a hexadecimal string
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String makePostRequest(String url, String jsonInput) {
        try {
            // Create HTTP client
            HttpClient client = HttpClient.newHttpClient();

            // Build HTTP request with the provided URL and JSON input
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonInput))
                    .build();

            // Send request and get the response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // If the response is plain text, return both the response code and body
            String responseBody = response.body();
            int statusCode = response.statusCode();

            // If you want to process the response further (e.g., logging or parsing), you can do it here
            System.out.println("Response Status Code: " + statusCode);
            System.out.println("Response Body: " + responseBody);

            // Return response details (you can customize this further)
            return response.body();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    public static String[] generateKeyPair() {
        try {
            // Initialize the KeyPairGenerator for RSA
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048); // 2048-bit key size
            KeyPair keyPair = keyPairGenerator.generateKeyPair();

            // Encode the private and public keys to Base64
            String encodedPrivateKey = Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded());
            String encodedPublicKey = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());

            // Return both keys as an array (private key first, then public key)
            return new String[]{encodedPrivateKey, encodedPublicKey};

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }


    static void loginVerification(String email,String password) throws JsonProcessingException {
        if(!email.isEmpty() && !password.isEmpty()) {
            ObjectMapper om = new ObjectMapper();
            String obj = new String(om.writeValueAsString(new UserData(email,computeSHA256(password),null,null,null)));
            System.out.println(makePostRequest("http://localhost:8080/login",obj));
        }
        else{
            JOptionPane.showMessageDialog(null, "Email or Password box cannot be empty");
        }
    }

    static void sendOTP(String email) throws JsonProcessingException {
        if(!email.isEmpty()) {
            userdata = new UserData(email,null,null,null,null);
            String obj = new String(om.writeValueAsString(userdata));
            if(makePostRequest("http://localhost:8080/generation",obj).equals("Generated"))
            {
                JOptionPane.showMessageDialog(null, "Otp Sent to " + email);
            }
            else {
                JOptionPane.showMessageDialog(null, "User Already Registered! \nKindly Login");
            }
        }
        else{
            JOptionPane.showMessageDialog(null, "Email box cannot be empty");
        }
    }

    static void verifyOTP(String otp) throws JsonProcessingException {
        if(!otp.isEmpty()) {
            userdata.setOtp(otp);
            String obj = new String(om.writeValueAsString(userdata));
            makePostRequest("http://localhost:8080/verification",obj);
        }
        else{
            JOptionPane.showMessageDialog(null, "Otp box cannot be empty");
        }
    }

    Sign() {

        JLabel label = new JLabel("Log In");
        label.setBounds(450, 200, 200, 50);
        label.setFont(new Font("Arial", Font.BOLD, 30));
        add(label);

        JTextField email = new JTextField(30);
        email.setFont(new Font("Arial", Font.PLAIN, 20));
        email.setBounds(300, 500, 400, 50);
        add(email);

        JPasswordField password = new JPasswordField(30);
        password.setFont(new Font("Arial", Font.PLAIN, 20));
        password.setBounds(300, 600, 400, 50);
        add(password);

        JButton btn = new JButton("Submit");
        btn.setFont(new Font("Arial", Font.PLAIN, 20));
        btn.setBounds(400, 700, 200, 50);
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(label.getText().equals("Log In")) {
                    try {
                        loginVerification(email.getText(),new String(password.getPassword()));
                    } catch (JsonProcessingException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                else if(label.getText().equals("Sign Up"))
                {
                    try {
                        sendOTP(email.getText());
                        label.setText("Verify OTP");
                        email.setText("");
                    } catch (JsonProcessingException ex) {
                        throw new RuntimeException(ex);
                    }
                } else if (label.getText().equals("Verify OTP")) {
                    try {
                        verifyOTP(email.getText());
                    } catch (JsonProcessingException ex) {
                        throw new RuntimeException(ex);
                    }
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
                if (label.getText().equals("Log In")) {
                    label.setText("Sign Up");
                    changeL.setText("Existing User?....");
                    password.setVisible(false);
                    changeB.setText("Log In");
                } else {
                    label.setText("Log In");
                    changeL.setText("New user?....");
                    password.setVisible(true);
                    changeB.setText("Sign Up");
                }
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
        new Sign();
    }
}
