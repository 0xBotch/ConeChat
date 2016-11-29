package FINAL;

import java.awt.*;

import javax.swing.*;

import org.omg.CORBA.INITIALIZE;

import java.awt.event.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.io.*;

import jdk.nashorn.internal.parser.TokenStream;


class serverGUI extends JFrame implements ActionListener{

    TextArea ta = new TextArea();
    JButton connect;
    BufferedReader br;
    BufferedWriter bw;
    ArrayList clientOutputStreams;
    ArrayList<String> users;
    String[] username = new String[0];
    int port = 9000;
    serverGUI(){
        this.setBounds(0,0,500,500);
        this.setTitle("Server");
        this.setLayout(new BorderLayout());
        this.add(ta, BorderLayout.CENTER);
        connect = new JButton();
        connect.setText("Start");
        this.add(connect, BorderLayout.SOUTH);
        INITIALIZE();
    }
    
    private void INITIALIZE(){
    	setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE); //it closes everything on the window even the thread and the port allowing for reuse of port
        connect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	startActionPerformed(evt);
            }
        });
    }
    
    public class ServerStart implements Runnable 
    {
        
        public void run() 
        {
            clientOutputStreams = new ArrayList();
            users = new ArrayList();  

            try 
            {
                ServerSocket serverSock = new ServerSocket(9000);

                while (true) 
                {
				Socket clientSock = serverSock.accept();
				ta.append(clientSock.getInetAddress().getCanonicalHostName() + "\n");
				PrintWriter writer = new PrintWriter(clientSock.getOutputStream());
				clientOutputStreams.add(writer);
	            
				Thread listener = new Thread(new ClientHandler(clientSock, writer));
				listener.start();
				ta.append("Got a connection. \n");
                }
            }
            catch (Exception ex)
            {
                ta.append("Error making a connection. \n");
            }
        }
    }
    
    
    public class ClientHandler implements Runnable	
    {
        BufferedReader reader;
        Socket sock;
        PrintWriter client;

        public ClientHandler(Socket clientSocket, PrintWriter writer) 
        {
             client = writer;
             try 
             {
                 sock = clientSocket;
                 InputStreamReader isReader = new InputStreamReader(sock.getInputStream());
                 reader = new BufferedReader(isReader);
             }
             catch (Exception ex) 
             {
                 ta.append("Unexpected error... \n");
             }

        }

       
        public void run() 
        {
             String message;
             try 
             {
                 while ((message = reader.readLine()) != null) 
                 {
                     ta.append("Received: " + message + "\n");

                     tellEveryone(message);

                 } 
              } 
              catch (Exception ex) 
              {
                 ta.append("Lost a connection. \n");
                 ex.printStackTrace();
                 clientOutputStreams.remove(client);
              } 
 	} 
     }
    
    public void tellEveryone(String message) 
    {
    	Iterator it = clientOutputStreams.iterator();

        while (it.hasNext()) 
        {
            try 
            {
                PrintWriter writer = (PrintWriter) it.next();
                writer.println(message);
                ta.append("Sending: " + message + "\n");
                writer.flush();
            } 
            catch (Exception ex) 
            {
            	ta.append("Error telling everyone. \n");
            }
        } 
    }

    public void incrementUser(String user){
    	users.add(user);
    	ta.append(users.toString());
    }
    
    public static void main(String[] args) throws Exception{
        java.awt.EventQueue.invokeLater(new Runnable() 
        {
            
            public void run() {
                new serverGUI().setVisible(true);
            }
        });
    }
    
    private void startActionPerformed(ActionEvent evt) {
        Thread starter = new Thread(new ServerStart());
        starter.start();
        
        ta.append("Server started...\n");
    }
    
	
	public void actionPerformed(ActionEvent e) {
		  try 
	        {
	            Thread.sleep(5000);                 //5000 milliseconds is five second.
	        } 
	        catch(InterruptedException ex) {Thread.currentThread().interrupt();}
	        
	        tellEveryone("Server:is stopping and all users will be disconnected.\n:Chat");
	        ta.append("Server stopping... \n");
	        
	        ta.setText("");
	}
}