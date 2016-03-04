/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

/**
 *
 * @author piotr
 */
import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) throws IOException {
        String hostName = "localhost";
        int portNumber = 4444;
        
        Socket clientSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;
        BufferedReader br = null;

        try {
            clientSocket = new Socket(hostName, portNumber);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));
            br = new BufferedReader(new InputStreamReader(System.in));
        } catch (IOException e) {
            System.err.println("Client exception");
        }        
       
        String message = null;
        String response = null;        
        try {
            System.out.println("Enter nickname: ");            
            while(response == null || !response.equals("OK")) { 
                message = br.readLine(); // reading from standard input
                out.println(message);
                out.flush();
                response = in.readLine();
                System.out.println(response);
            }
            
            /*message = br.readLine();
            while(message.compareTo("!QUIT") != 0) {
                out.println(message);
                out.flush();
                response = in.readLine();
                System.out.println("Server response: " + response);
                message = br.readLine();
            }*/
        } catch(IOException e) {
            System.err.println("Reading error");
        } finally {
            in.close();
            out.close();
            br.close();
            clientSocket.close();
        }
    }
}
