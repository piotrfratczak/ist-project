package ist.java.client;

import ist.java.data.*;
import ist.java.request.*;
import java.io.*;
import java.net.*;
import java.util.*;

// This class will allow the user to write and read tweets.
public class Client {

	private static boolean appIsOn = true;

	// Main program handles the inputs of user, e. g. what the user wants to do. It
	// also handles the exceptions.
    public static void main(String... args){

    	int port = Integer.parseInt(args[0]);

    	while(appIsOn){

    		displayMenu();

    		try{
    			
		    	switch(getChoice()){
		    		case 1:
		    			newTweet(port);
		    			break;
		    		case 2:
		    			readOneTweet(port);
						break;
					case 3:
						readOwnTweets(port);
						break;
		    		case 4:
		    			readAllTweets(port);
		    			break;
		    		case 5:
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
	// This subroutine prints out the options for user.
    private static void displayMenu(){

    	System.out.println("< 1: Write a tweet         >");
		System.out.println("< 2: Read the latest tweet >");
		System.out.println("< 3: Read my own tweets	   >");
 		System.out.println("< 4: Read all tweets       >");
    	System.out.println("< 5: Quit                  >");
    	System.out.println("Your choice: ");
    }
	// This subroutine checks what integer the users chooses and returns the choice. 
	// It also warns user if something else than integer is input.
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
	// In this subroutine new tweet is made. The user is connected to the server
	// and those communicate with each other.
    private static void newTweet(int port) throws UnknownHostException, IOException {

		Scanner scanner = new Scanner(System.in);
		
    	
		System.out.println("Please enter your username: ");
		// I set the username max length now to 15 letters,
		// don't knoew if it is good one, but at least it 
		// wont accept too long names or sentences.
		String author = "";

		while (scanner.hasNext()){
			if(author.length() < 15) {
				author = scanner.nextLine();
				break;
			} else {
				scanner.next();
				System.out.println("Error! Your username is too long");
			}
		}
		
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

	// In this subroutine the user requests latest tweet from the file/blogserver. 
	// Then the latest tweet is printed to screen of the user.
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

	// This subroutine prints out all the tweets of one user.
	private static void readOwnTweets(int port)  throws UnknownHostException, IOException, ClassNotFoundException {

		Scanner scanner = new Scanner(System.in);

		// Here is the same as before, that it checks if 
		// username is suitable.
    	System.out.println("Please enter your username: ");
		String author = "";

		while (scanner.hasNext()){
			if(author.length() < 15) {
				author = scanner.nextLine();
				break;
			} else {
				scanner.next();
				System.out.println("Error! Your username is too long");
			}
		}
		
		PostRequest postRequest = new PostRequest(PostRequest.READ_OWN_POSTS, author);
		
		Socket socket = new Socket("localhost", port);
    	OutputStream outputStream = socket.getOutputStream();
    	ObjectOutputStream objectOutStream = new ObjectOutputStream(outputStream);

    	objectOutStream.writeObject(postRequest);

    	InputStream inputStream = socket.getInputStream();
    	ObjectInputStream objectInStream = new ObjectInputStream(inputStream);

		List<AbstractPost> ownTweets = (List<AbstractPost>) objectInStream.readObject();
		
		// Checks that author is correct, so it will give tweets of spesific user.
		System.out.println("\nAll your tweets: \n");
		for(AbstractPost tweet : ownTweets){
    		System.out.println(tweet.toString() + "\n");
    	}

		objectOutStream.close();
    	outputStream.close();
    	objectInStream.close();
    	inputStream.close();
    	socket.close();
	}

	// This subroutine prints out all the tweets that are written in the blog.
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
