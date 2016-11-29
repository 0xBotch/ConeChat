import java.awt.*;

import javax.swing.*;

import java.awt.event.*;
import java.net.*;
import java.io.*;
class ServerGui extends JFrame implements ActionListener{
    static JTextField tf = new JTextField();
    static TextArea ta = new TextArea();
    static BufferedReader br;
    static BufferedWriter bw;
    ServerGui(){
        this.setBounds(0,0,500,500);
        this.setTitle("Server");
        this.setLayout(new BorderLayout());
        this.add(tf, BorderLayout.SOUTH);
        this.add(ta, BorderLayout.CENTER);
        tf.addActionListener(this);
    }
    public void actionPerformed(ActionEvent ae){
        try{
           
                String msg = tf.getText();
                bw.write(msg+"\n");
                bw.flush();
                ta.append(msg+"\n");
                tf.setText("");
           
        }catch(Exception e){}
    }
    public static void main(String[] args) throws Exception{
   
        ServerSocket ss = new ServerSocket(8006);
        Socket s = null;
        while(true){
        	System.out.println("Waiting for clients...");
            s = ss.accept();
            System.out.println("Connection Established");
         
            InputStream is = s.getInputStream();
            OutputStream os = s.getOutputStream();
         
            br = new BufferedReader(new InputStreamReader(is));
            bw = new BufferedWriter(new OutputStreamWriter(os));
       
           
            ServerGui f = new ServerGui();
            f.setVisible(true);
           
            while(true){
                String msg = br.readLine();
                ta.append("Msg from Client:"+msg+"\n");
            }
        }
    }   
}