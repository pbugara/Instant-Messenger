/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

/**
 *
 * @author piotr
 */
import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
    
    private ArrayList<ServerThread> clientsThreads;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private Map<String, PrintWriter> clientOutputs;
    
    public Server(int portNumber) throws IOException {
        this.serverSocket = new ServerSocket(portNumber);  
        clientsThreads = new ArrayList<>();
        clientOutputs = new HashMap<>();
    }
    
    public void runServer() {
        while(true) {
            try {
                clientSocket = serverSocket.accept();
                ServerThread s = new ServerThread(clientSocket);
                s.run();
                clientsThreads.add(s);
            } catch(Exception e) {
                System.err.println("Connection error 1");
                e.printStackTrace();
            }
            
        }
    }
    
    private class ServerThread implements Runnable {
        private PrintWriter output;
        private BufferedReader input;
        private String username;
        private Socket clientSocket;
        
        public ServerThread(Socket clientSocket) {
            this.clientSocket = clientSocket;
            System.out.println(clientSocket.getInetAddress());
            try {
                output = new PrintWriter(clientSocket.getOutputStream());
                input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            } catch (IOException ex) {
                System.err.println("Error with I/O stream");
            }            
        }
        
        @Override
        public void run() {
            registerUser();
        }
        
        private void registerUser() {
            try {
                username = input.readLine();
                while(clientOutputs.containsKey(username)) {
                   output.println("Duplicated username. Try again: ");
                   output.flush(); 
                   username = input.readLine();
                } 
                output.println("OK");
                output.flush(); 
                clientOutputs.put(username, output);
                
            } catch (IOException ex) {
                System.err.println("Can not read username");
            }
            
            System.out.println("User name: " + username);
        }  
        
        private void broadcastMessage() {
            String message = null;
            try {
                while((message = input.readLine()) != null) {
                    for(PrintWriter out : clientOutputs.values()) {
                        out.println("[" + username + "] " + message);
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}