package ist.java.request;

import java.io.Serializable;

//PostRequest is a class used to modelize a request to the server.
//It should contain the action you want to accomplish.
public class PostRequest implements Serializable {

	public static final int READ_ONE_POST = 1;
	public static final int READ_ALL_POSTS = 2;
	public static final int READ_OWN_POSTS = 3;

	private int action;
	private String username;

	public PostRequest(int action){
		this.action = action;
	}

	public PostRequest(int action, String username){
		this.action = action;
		this.username = username;
	}

	public int getAction(){
		return action;
	}

	public String getUsername(){
		return username;
	}
}
