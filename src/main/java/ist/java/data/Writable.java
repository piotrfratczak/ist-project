package ist.java.data;

import java.io.IOException;


// This class is only used for saving the data/tweets.
public interface Writable {
    void save() throws IOException; //save() rewrites the whole file, erasing previous data
}
