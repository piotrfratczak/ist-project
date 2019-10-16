package ist.java.request;

import ist.java.data.AbstractPost;
import java.util.Date;

//PostSubmission is a class used to modelize a request to add a tweet to the server.
//It should contain the action you want to accomplish and the data.
public class PostSubmission extends AbstractPost {

	public PostSubmission(String author, String tweet){

		timestamp = new Date();
		this.author = author;
		this.tweet = tweet;
	}

}
