import java.awt.*;
import javax.swing.*;
/*
 * Created by JFormDesigner on Tue Nov 19 23:11:27 IST 2024
 */



/**
 * @author nk
 */
public class Home  {

    Home()
    {
        initComponents();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner Evaluation license - nk
        frame1 = new JFrame();
        panel1 = new JPanel();
        panel2 = new JPanel();
        textField2 = new JTextField();
        button3 = new JButton();
        panel3 = new JPanel();
        panel4 = new JPanel();
        panel5 = new JPanel();
        textField3 = new JTextField();
        button4 = new JButton();
        panel6 = new JPanel();

        //======== frame1 ========
        {
            var frame1ContentPane = frame1.getContentPane();
            frame1ContentPane.setLayout(new GridLayout());

            //======== panel1 ========
            {
                panel1.setBorder (new javax. swing. border. CompoundBorder( new javax .swing .border .TitledBorder (new javax.
                swing. border. EmptyBorder( 0, 0, 0, 0) , "JF\u006frmDes\u0069gner \u0045valua\u0074ion", javax. swing. border
                . TitledBorder. CENTER, javax. swing. border. TitledBorder. BOTTOM, new java .awt .Font ("D\u0069alog"
                ,java .awt .Font .BOLD ,12 ), java. awt. Color. red) ,panel1. getBorder
                ( )) ); panel1. addPropertyChangeListener (new java. beans. PropertyChangeListener( ){ @Override public void propertyChange (java
                .beans .PropertyChangeEvent e) {if ("\u0062order" .equals (e .getPropertyName () )) throw new RuntimeException
                ( ); }} );
                panel1.setLayout(new BorderLayout());

                //======== panel2 ========
                {
                    panel2.setLayout(new GridLayout());
                    panel2.add(textField2);

                    //---- button3 ----
                    button3.setText("text");
                    panel2.add(button3);
                }
                panel1.add(panel2, BorderLayout.NORTH);

                //======== panel3 ========
                {
                    panel3.setLayout(new GridLayout(10, 1, 10, 10));
                }
                panel1.add(panel3, BorderLayout.CENTER);
            }
            frame1ContentPane.add(panel1);

            //======== panel4 ========
            {
                panel4.setLayout(new BorderLayout());

                //======== panel5 ========
                {
                    panel5.setLayout(new GridLayout());
                    panel5.add(textField3);

                    //---- button4 ----
                    button4.setText("text");
                    panel5.add(button4);
                }
                panel4.add(panel5, BorderLayout.PAGE_END);

                //======== panel6 ========
                {
                    panel6.setLayout(new GridLayout(10, 1, 10, 10));
                }
                panel4.add(panel6, BorderLayout.CENTER);
            }
            frame1ContentPane.add(panel4);
            frame1.pack();
            frame1.setVisible(true);
            frame1.setLocationRelativeTo(frame1.getOwner());
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner Evaluation license - nk
    private JFrame frame1;
    private JPanel panel1;
    private JPanel panel2;
    private JTextField textField2;
    private JButton button3;
    private JPanel panel3;
    private JPanel panel4;
    private JPanel panel5;
    private JTextField textField3;
    private JButton button4;
    private JPanel panel6;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
    public static void main(String[] args) {
        new Home();
    }
}
