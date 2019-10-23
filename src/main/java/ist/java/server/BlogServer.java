package ist.java.server;

import ist.java.data.*;
import ist.java.request.*;
import java.io.*;
import java.net.*;
import java.util.List;
import java.lang.ClassNotFoundException;
// The blogserver class is the server and it handles all the connections 
// to the client. It checks whether the user connects to read tweets or
// write tweets etc.
public class BlogServer {

	private static ServerSocket connection;
    private static Blog blog = new Blog();

    public static void main(String... args){

    	int port = Integer.parseInt(args[0]);

    	try{
    		connection = new ServerSocket(port);
    	}catch(IOException e){
    		e.printStackTrace();
    	}
    	
    	//TODO: multithreading
    	while(true){
	    	try{

	    		Socket socket = connection.accept();

	    		InputStream inputStream = socket.getInputStream();
	    		ObjectInputStream objectInStream = new ObjectInputStream(inputStream);

	    		Object request = objectInStream.readObject();

	    		if(request instanceof PostRequest){

	    			PostRequest postRequest = (PostRequest) request;

	    			OutputStream outputStream = socket.getOutputStream();
	    			ObjectOutputStream objectOutStream = new ObjectOutputStream(outputStream);

	    			if(postRequest.getAction() == PostRequest.READ_ONE_POST){

	    				AbstractPost lastPost = blog.readOne();
	    				objectOutStream.writeObject(lastPost);

	    			}else if(postRequest.getAction() == PostRequest.READ_ALL_POSTS){
	    				
	    				List<AbstractPost> allPosts = blog.readAll();
						objectOutStream.writeObject(allPosts);

					}else if(postRequest.getAction() == PostRequest.READ_OWN_POSTS){

						String author = postRequest.getUsername();
						List<AbstractPost> myPosts = blog.readOwnPosts(author);
						objectOutStream.writeObject(myPosts);

					}
					
					
					//TODO: maybe - else{throw new NoSuchActionException();}	    			

	    			objectOutStream.close();
	    			outputStream.close();

	    		}else if(request instanceof PostSubmission){

	    			PostSubmission postSubmission = (PostSubmission) request;
	    			Post post = postSubmission.getPost();
	    			System.out.println("New post Received!\n" + post.toString() + "\n");
	    			blog.addPost(post);

	    			blog.save();

	    		}else{
	    			System.out.println("Request not recognised");
	    		}

	    		objectInStream.close();
	    		inputStream.close();
	    		socket.close();

	    	}catch(ArrayIndexOutOfBoundsException e){
	    		e.printStackTrace();

	    	}catch(IOException e){
	    		System.err.println("I/O ERROR");
	    		e.printStackTrace();

	    	}catch(ClassNotFoundException e){
	    		e.printStackTrace();

	    	}
	    }
	    //TODO: we need to handle this properly
	    //connection.close();
    }
}
