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
        }catch(ClassNotFoundException e){
            e.printStackTrace();
        }
        //TODO
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

    public List<AbstractPost> readOwnPosts() throws IOException {
        return null;
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
