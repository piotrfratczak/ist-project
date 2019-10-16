package ist.java.data;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.*;
import java.text.*;

public abstract class AbstractPost implements Serializable {

    protected String author;
    protected String tweet;
    protected Date timestamp;


    public String toJson() {
        return new Gson().toJson(this);
    }

    public String toString(){

		SimpleDateFormat formatter = new SimpleDateFormat("LLL dd, yyyy, hh:mm:ss a");
		String date = formatter.format(timestamp);

		return "Author: @" + author + "\n" +
				"Tweet: " + tweet + "\n" +
				"Date: " + date;
	}
}