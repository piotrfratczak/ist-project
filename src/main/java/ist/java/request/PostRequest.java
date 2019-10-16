package ist.java.request;

import java.io.Serializable;
//PostRequest is a class used to modelize a request to the server.
//It should contain the action you want to accomplish.
public class PostRequest implements Serializable {

	public static final int READ_ONE_POST = 1;
	public static final int READ_ALL_POSTS = 2;

	private int action;

	public PostRequest(int action){
		this.action = action;
	}

	public int getAction(){
		return action;
	}
}
