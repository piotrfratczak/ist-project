package ist.java.data;

import ist.java.data.AbstractPost;
import java.util.Date;

public class Post extends AbstractPost {

	public Post(String author, String tweet){

		timestamp = new Date();
		this.author = author;
		this.tweet = tweet;
	}
}