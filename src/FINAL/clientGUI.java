package FINAL;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;

import FINAL.serverGUI.ServerStart;
import sun.reflect.generics.tree.Tree;


public class clientGUI extends JFrame {
	
	private BufferedImage image;
	
	JButton btn = new JButton("Enter Server");
    JFrame newFrame;
    JButton sendMessage;
    JTextArea messageBox;
    JTextArea chatBox;
    JTextArea userList;
    String username;
    JTextField usernameField;
    ArrayList<String> users;
    int port = 9000;
    String address = "localhost";
    Boolean isConnected = false;
    
    Socket sock;
    BufferedReader reader;
    PrintWriter writer;
	
	public clientGUI() {
		
		try {
			image = ImageIO.read(new File("Untitled-1.jpg"));
			this.setContentPane(new JLabel(new ImageIcon(image)));
		} 
		catch (IOException e) {
		}

		JLabel username = new JLabel("Username:");
		username.setForeground(Color.WHITE);
		/*
		GridBagConstraints preRight = new GridBagConstraints();
        preRight.insets = new Insets(10, 10, 10, 10);
        preRight.anchor = GridBagConstraints.SOUTH;
        GridBagConstraints preLeft = new GridBagConstraints();
        preLeft.anchor = GridBagConstraints.WEST;
        preLeft.insets = new Insets(0, 10, 0, 10);
        // preRight.weightx = 2.0;
        preRight.fill = GridBagConstraints.HORIZONTAL;
        preRight.gridwidth = GridBagConstraints.REMAINDER;
        */
        
        usernameField = new JTextField("", 12);
        usernameField.setBackground(Color.BLACK);
        usernameField.setForeground(Color.GRAY);

		this.add(username);
		this.add(usernameField);
        this.add(btn);
        
		this.setSize(400,200);
		this.setLayout(new GridBagLayout());
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("ConeChat");
		this.setVisible(true);
        INITIALIZE();
    }
    
    private void INITIALIZE(){
    	setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE); //it closes everything on the window even the thread and the port allowing for reuse of port
    	btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	enterServerButtonListener(evt);
            }
        });
    }
    
    
	public void mainGUI() {
		this.setVisible(false);
		JFrame newFrame = new JFrame(); 
		
		try {
			image = ImageIO.read(new File("UnPic.jpg"));
			newFrame.setContentPane(new JLabel(new ImageIcon(image)));
		} 
		catch (IOException e) {
		}
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new GridBagLayout());
		
		JPanel westPanel = new JPanel();
		westPanel.setLayout(new GridBagLayout());
		
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new GridBagLayout());		
		
		userList = new JTextArea(27, 10);
		userList.requestFocusInWindow();
		userList.setForeground(Color.WHITE);
		userList.setEditable(false);
		userList.setFont(new Font("DialogInput", Font.PLAIN, 12));
		userList.setLineWrap(true);
		
        
        chatBox = new JTextArea(20, 30);
        chatBox.requestFocusInWindow();
        chatBox.setForeground(Color.WHITE);
        chatBox.setEditable(false);
        chatBox.setFont(new Font("DialogInput", Font.PLAIN, 12));
        chatBox.setLineWrap(true);
        
        
        messageBox = new JTextArea(1,30);
        messageBox.requestFocusInWindow();
        messageBox.setEditable(true);
        messageBox.setFont(new Font("DialogInput", Font.PLAIN, 12));
        messageBox.setLineWrap(true);
        messageBox.setForeground(Color.WHITE);
        
         
        sendMessage = new JButton("Send");
        sendMessage.addActionListener(new sendMessageButtonListener());
        
        JButton disconnect = new JButton("Disconnect");
        disconnect.addActionListener(new disconnectButtonListener());
        
        JButton i = new JButton("i");
        //i.setPreferredSize(new Dimension(100,15));
        
        JLabel dateLabel = new JLabel();
        Date date = new Date();
        dateLabel.setText(""+ date);
        dateLabel.setForeground(Color.WHITE);
        dateLabel.setFont(new Font("", Font.PLAIN, 10));
        
        

        GridBagConstraints left = new GridBagConstraints();
        left.anchor = GridBagConstraints.LINE_START;
        left.fill = GridBagConstraints.HORIZONTAL;
        left.weightx = 512.0D;
        left.weighty = 1.0D;

        GridBagConstraints right = new GridBagConstraints();
        right.insets = new Insets(0, 10, 0, 0);
        right.anchor = GridBagConstraints.LINE_END;
        right.fill = GridBagConstraints.NONE;
        right.weightx = 1.0D;
        right.weighty = 1.0D;
        
        userList.append("  " + username + "\n");
        chatBox.append("  " + username + " connected..." + "\n");
       
        
        JLabel name = new JLabel(" <" + username + "> ");
        name.setForeground(Color.WHITE);
        southPanel.add(name);
        
        Border lines = BorderFactory.createLineBorder(Color.white);
        
        southPanel.setBorder(lines);
        westPanel.setBorder(lines);
        northPanel.setBorder(lines);
        
        westPanel.add(userList);

        southPanel.add(messageBox, left);
        southPanel.add(sendMessage, right);
        
        northPanel.add(dateLabel, left);
        northPanel.add(disconnect, right);
        northPanel.add(i);
        

        panel.add(chatBox);
        panel.add(BorderLayout.WEST, westPanel);
        panel.add(BorderLayout.SOUTH, southPanel);
        panel.add(BorderLayout.NORTH, northPanel);
        
        chatBox.setOpaque(false);
        userList.setOpaque(false);
        messageBox.setOpaque(false);
        
        northPanel.setOpaque(false);
        westPanel.setOpaque(false);
        southPanel.setOpaque(false);
        panel.setOpaque(false);
        
        newFrame.add(panel);
        newFrame.setLayout(new GridLayout());
        newFrame.setTitle("ConeChat");
        newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        newFrame.setSize(600, 500);
        newFrame.setLocationRelativeTo(null);
        newFrame.setVisible(true);
    }
	
    private void enterServerButtonListener(ActionEvent evt) {
    	
            if (isConnected == false) 
            {
                    username = usernameField.getText();
                    usernameField.setEditable(false);

                try 
                {
                    sock = new Socket(address, port);
                    InputStreamReader streamreader = new InputStreamReader(sock.getInputStream());
                    reader = new BufferedReader(streamreader);
                    writer = new PrintWriter(sock.getOutputStream());
                    writer.println(username + ": has connected.");
                    writer.flush(); 
                    isConnected = true; 
                } 
                catch (Exception ex) 
                {
                    chatBox.append("Cannot Connect! Try Again. \n");
                    usernameField.setEditable(true);
                }
                
                mainGUI();
                Read();
                
            } else if (isConnected == true) 
            {
                chatBox.append("You are already connected. \n");
            }
    }

	class sendMessageButtonListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				  String nothing = "";
			        if ((messageBox.getText()).equals(nothing)) {
			        	messageBox.setText("");
			        	messageBox.requestFocus();
			        } else {
			            try {
			               writer.println(username + ":" + messageBox.getText());
			               writer.flush(); // flushes the buffer
			            } catch (Exception ex) {
			            	chatBox.append("Message was not sent. \n");
			            }
			            messageBox.setText("");
			            messageBox.requestFocus();
			            messageBox.setText("");
			            messageBox.requestFocus();
			        }
			}
    }
	
	class disconnectButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            Disconnect();
        }
    }

    public void Read() 
    {
         Thread IncomingReader = new Thread(new IncomingReader());
         IncomingReader.start();
    }
    
    public class IncomingReader implements Runnable
    {
		@Override
        public void run() 
        {
            String[] data;
            String stream;

            try 
            {
                while ((stream = reader.readLine()) != null) 
                {
                     data = stream.split(":");
                     chatBox.append(data[0] + ": " + data[1] + "\n");
                }
           }catch(Exception ex) { }
        }
    }
	    
	    
	    public void Disconnect() 
	    {
	        try 
	        {
	        	chatBox.append("Disconnected.\n");
	            sock.close();
	        } catch(Exception ex) {
	        	chatBox.append("Failed to disconnect. \n");
	        }
	    }
	    

	public static void main(String[] args) {
		
		  java.awt.EventQueue.invokeLater(new Runnable() 
	        {
	            @Override
	            public void run() {
	                new clientGUI().setVisible(true);
	            }
	        });
	}

}