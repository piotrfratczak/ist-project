package ist.java.data;

import java.io.*;
import java.util.*;

// This class creates a list of all the tweets and places the in a file.
public class Blog implements Readable, Writable{

    List<AbstractPost> tweets;

    public Blog(){
        
        tweets = new LinkedList<>();
        try{
            populateFromDisk();
        }catch(EOFException e){
            System.out.println("No tweets to read");
        }catch(IOException e){
            e.printStackTrace();
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    private void populateFromDisk() throws IOException, ClassNotFoundException {

        FileInputStream fileInStream = new FileInputStream("database");
        ObjectInputStream objectInStream = new ObjectInputStream(fileInStream);

        int count = objectInStream.readInt();

        for(int i = 0 ; i < count ; ++i){
            AbstractPost post = (AbstractPost) objectInStream.readObject();
            tweets.add(post);
        }

        objectInStream.close();
        fileInStream.close();
    }

    public void addPost(AbstractPost post){
        tweets.add(post);
    }

    //Readable interface methods
    public AbstractPost readOne(){

        int indexOfLast = tweets.size()-1;
        return tweets.get(indexOfLast);
    }

    public List<AbstractPost> readAll() throws IOException {
        return tweets;
    }

    public List<AbstractPost> readOwnPosts(String username) throws IOException {
        
        List<AbstractPost> list = new LinkedList<>();

        for(AbstractPost post : tweets){
            if(post.getAuthor().equalsIgnoreCase(username)) list.add(post);
        }

        return list;
    }

    //Writable interface method
    public void save() throws IOException {

        FileOutputStream fileOutStream = new FileOutputStream("database");
        ObjectOutputStream objectOutStream = new ObjectOutputStream(fileOutStream);

        //write how many posts are to be saved to the file
        objectOutStream.writeInt(tweets.size());

        for(AbstractPost post : tweets){
            objectOutStream.writeObject(post);
        }

        objectOutStream.close();
        fileOutStream.close();
    }
}
