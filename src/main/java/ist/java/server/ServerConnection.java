package ist.java.server;

import java.lang.Thread;
import ist.java.data.*;
import ist.java.request.*;
import java.io.*;
import java.net.Socket;
import java.util.List;
import java.lang.ClassNotFoundException;

public class ServerConnection extends Thread {

	private Socket socket;
	private Blog blog;

	public ServerConnection(Socket socket, Blog blog){
		this.socket = socket;
		this.blog = blog;
	}

	public void run(){    	

    	try{

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

    			objectOutStream.close();
    			outputStream.close();

    		}else if(request instanceof PostSubmission){

    			PostSubmission postSubmission = (PostSubmission) request;
    			Post post = postSubmission.getPost();
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
}