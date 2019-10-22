package ist.java.request;

import ist.java.data.Post;
import java.io.Serializable;

//PostSubmission is a class used to modelize a request to add a tweet to the server.
//It should contain the action you want to accomplish and the data.
public class PostSubmission implements Serializable {

	private Post post;

	public PostSubmission(Post post){
		this.post = post;
	}

	public Post getPost(){
		return post;
	}

}
