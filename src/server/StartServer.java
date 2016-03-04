/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.Server;

/**
 *
 * @author piotr
 */
public class StartServer {
    public static void main(String[] args) {
        try {
            Server server = new Server(4444);
            server.runServer();
        } catch (IOException ex) {
            System.err.println("Can not start server");
        }
        
    }
}
