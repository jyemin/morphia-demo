package demo;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.mongodb.Mongo;

public class Test {
    public static void main(String[] args) throws Exception {
        Morphia morphia = new Morphia();
        Mongo mongo = new Mongo();
        Datastore ds = morphia.createDatastore(mongo, "test");
    }
}

