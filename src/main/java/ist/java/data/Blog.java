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
        }catch(IOException e){
            e.printStackTrace();
        }
        //TODO
    }

    private void populateFromDisk() throws IOException {
        //TODO
    }

    //Readable interface methods
    public AbstractPost readOne(){
        return null;
    }

    public List<AbstractPost> readAll() throws IOException {
        return null;
    }

    public List<AbstractPost> readOwnPost() throws IOException {
        return null;
    }

    //Writable interface method
    public void save() throws IOException {

    }
}
