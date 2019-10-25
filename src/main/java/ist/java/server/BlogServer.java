package ist.java.server;

import ist.java.server.ServerConnection;
import ist.java.server.ServerSwitchOff;
import ist.java.data.Blog;
import java.util.Scanner;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;


import java.lang.Thread;

// The blogserver class is the server and it handles all the connections 
// to the client. It checks whether the user connects to read tweets or
// write tweets etc.
public class BlogServer {

    private static Blog blog = new Blog();

    public static void main(String... args) {

    	int port = Integer.parseInt(args[0]);

    	//way to close server socket
    	ServerSwitchOff isOn = new ServerSwitchOff();
    	isOn.start();

    	try{

    		ServerSocket server = new ServerSocket(port);
    		int connectionCounter = 0;
    		while(isOn.switchIsOn()){
    			connectionCounter++;
    			Socket socket = server.accept();
    			System.out.println("> Client No: " + connectionCounter);
    			ServerConnection newConnection = new ServerConnection(socket, blog);
    			newConnection.start();
    		}
    		server.close();
    		
    	}catch(IOException e){
    		e.printStackTrace();
    	}
    }
}