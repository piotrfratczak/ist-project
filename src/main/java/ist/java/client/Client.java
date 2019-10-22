package ist.java.client;

import ist.java.data.*;
import ist.java.request.*;
import java.util.*;
import java.net.*;
import java.io.*;

public class Client {

	private static boolean appIsOn = true;

    public static void main(String... args){

    	int port = Integer.parseInt(args[0]);

    	while(appIsOn){

    		displayMenu();

    		try{
    			//TODO: add another option - readMyTweets(int port)
    			//should ask for your username to find tweet (not case sensitive)
		    	switch(getChoice()){
		    		case 1:
		    			newTweet(port);
		    			break;
		    		case 2:
		    			readOneTweet(port);
		    			break;
		    		case 3:
		    			readAllTweets(port);
		    			break;
		    		case 4:
		    			appIsOn = false;
		    			break;
		    		default:
		    			System.out.println("\nChoice invalid.\n");
		    	}
		    }catch(UnknownHostException e){
				e.printStackTrace();
			}catch(IOException e){
				e.printStackTrace();
			}catch(ClassNotFoundException e){
				e.printStackTrace();
			}
    	}

    }

    private static void displayMenu(){

    	System.out.println("< 1: Write a tweet         >");
    	System.out.println("< 2: Read the latest tweet >");
    	System.out.println("< 3: Read all tweets       >");
    	System.out.println("< 4: Quit                  >");
    	System.out.println("Your choice: ");
    }

    private static int getChoice(){

    	Scanner scanner = new Scanner(System.in);
    	//Accept only int
    	int choice = 0;
		while (scanner.hasNext()){
		    if (scanner.hasNextInt()){
		        choice = scanner.nextInt();
		        break;
		    } else {
		        scanner.next(); // Just discard this, not interested...
		        System.out.println("Input an integer.");
		    }
		}

		return choice;
    }

    private static void newTweet(int port) throws UnknownHostException, IOException {

    	Scanner scanner = new Scanner(System.in);

    	//TODO handle case when ppl mess with input
    	System.out.println("Please enter your username: ");
    	String author = scanner.nextLine();

    	System.out.println("What is on your mind? (120 characters)");
    	String message = scanner.nextLine();

    	Post newPost = new Post(author, message);
    	PostSubmission tweet = new PostSubmission(newPost);

    	Socket socket = new Socket("localhost", port);
    	OutputStream outputStream = socket.getOutputStream();
    	ObjectOutputStream objectOutStream = new ObjectOutputStream(outputStream);

    	objectOutStream.writeObject(tweet);

    	objectOutStream.close();
    	outputStream.close();
    	socket.close();

    }

    private static void readOneTweet(int port) throws UnknownHostException, IOException, ClassNotFoundException {

    	PostRequest postRequest = new PostRequest(PostRequest.READ_ONE_POST);

    	Socket socket = new Socket("localhost", port);
    	OutputStream outputStream = socket.getOutputStream();
    	ObjectOutputStream objectOutStream = new ObjectOutputStream(outputStream);

    	objectOutStream.writeObject(postRequest);

    	InputStream inputStream = socket.getInputStream();
    	ObjectInputStream objectInStream = new ObjectInputStream(inputStream);

    	AbstractPost tweet = (AbstractPost) objectInStream.readObject();

    	System.out.println("Latest tweet:\n");
    	System.out.println(tweet.toString() + "\n");

    	objectOutStream.close();
    	outputStream.close();
    	objectInStream.close();
    	inputStream.close();
    	socket.close();
    }

    private static void readAllTweets(int port) throws UnknownHostException, IOException, ClassNotFoundException {

    	PostRequest postRequest = new PostRequest(PostRequest.READ_ALL_POSTS);

    	Socket socket = new Socket("localhost", port);
    	OutputStream outputStream = socket.getOutputStream();
    	ObjectOutputStream objectOutStream = new ObjectOutputStream(outputStream);

    	objectOutStream.writeObject(postRequest);

    	InputStream inputStream = socket.getInputStream();
    	ObjectInputStream objectInStream = new ObjectInputStream(inputStream);

    	List<AbstractPost> tweets = (List<AbstractPost>) objectInStream.readObject();

    	System.out.println("\nAll the tweets:\n");
    	for(AbstractPost tweet : tweets){
    		System.out.println(tweet.toString() + "\n");
    	}

    	objectOutStream.close();
    	outputStream.close();
    	objectInStream.close();
    	inputStream.close();
    	socket.close();
    }
}
