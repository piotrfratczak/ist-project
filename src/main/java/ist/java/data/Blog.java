package ist.java.data;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class Blog implements Readable, Writable{
    List<AbstractPost> tweets;

    public Blog(){
        tweets = new LinkedList<>();
        try{
            populateFromDisk();
        }catch(Exception e){}
        //TODO
    }

    private void populateFromDisk() throws IOException {
        //TODO
    }


}
