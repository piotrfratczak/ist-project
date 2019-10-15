package ist.java.data;

import java.util.*;
import java.text.*;

public class Post extends AbstractPost {

	public Post(String author, String tweet){

		timestamp = new Date();
		this.author = author;
		this.tweet = tweet;

		//probably not here
		SimpleDateFormat formatter = new SimpleDateFormat("LLL dd, yyyy, hh:mm:ss a");
		String date = formatter.format(timestamp);
	}
}