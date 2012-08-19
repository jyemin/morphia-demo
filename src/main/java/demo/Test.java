package demo;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.google.code.morphia.annotations.Id;
import com.mongodb.Mongo;
import org.bson.types.ObjectId;

public class Test {
    public static void main(String[] args) throws Exception {
        Morphia morphia = new Morphia();
        Mongo mongo = new Mongo();
        mongo.dropDatabase("test");
        Datastore ds = morphia.createDatastore(mongo, "test");

        Programmer programmer = new Programmer();
        programmer.name= "Jeff";

        ds.save(programmer);

        System.out.println(programmer);
    }
}

class Programmer {
    @Id
    ObjectId id;
    String name;

    @Override
    public String toString() {
        return "Programmer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}

