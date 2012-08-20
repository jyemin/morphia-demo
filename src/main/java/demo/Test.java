package demo;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.mongodb.Mongo;

import java.text.SimpleDateFormat;
import java.util.Arrays;

public class Test {
    public static void main(String[] args) throws Exception {
        Morphia morphia = new Morphia();
        morphia.map(Repository.class).map(Programmer.class);
        Mongo mongo = new Mongo();
        mongo.dropDatabase("test");
        Datastore ds = morphia.createDatastore(mongo, "test");
        ds.ensureIndexes();

        Programmer scott = new Programmer();
        scott.userName = "scotthernandez";
        scott.name = "Scott Hernandez";
        scott.since = SimpleDateFormat.getDateInstance().parse("Aug 12, 2009");
        scott.active = true;
        scott.followers = 8;
        scott.following = Arrays.asList("moraes", "stickfigure");
        ds.save(scott);

        Organization mongodb = new Organization();
        mongodb.userName = "mongodb";
        mongodb.name = "mongodb";
        mongodb.since = SimpleDateFormat.getDateInstance().parse("Jan 8, 2009");
        ds.save(mongodb);

        Repository mongoDocs = new Repository(mongodb, "docs");
        ds.save(mongoDocs);

        Repository scottDocs = new Repository(scott, "docs", mongoDocs);
        ds.save(scottDocs);

        for (Repository cur : ds.find(Repository.class).field("owner").equal(mongodb)) {
            System.out.println(cur);
        }

        for (Repository cur : ds.find(Repository.class).field("owner").equal(scott)) {
            System.out.println(cur);
        }

    }
}



