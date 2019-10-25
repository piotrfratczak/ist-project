package ist.java.server;

import java.util.Scanner;
import java.lang.Thread;

class ServerSwitchOff extends Thread {

	private static boolean isOn = true;

	public ServerSwitchOff(){}

	//input q to close server after processing the next request
	public void run(){
		Scanner scanner = new Scanner(System.in);
		String input = scanner.next();
		while(!scanner.next().equals("q")){}
		isOn = false;
	}

	public boolean switchIsOn(){
		return isOn;
	}
}
