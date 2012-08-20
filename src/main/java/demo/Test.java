package demo;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.mongodb.Mongo;

import java.text.ParseException;
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

        Programmer scott = createScott();
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

        Programmer jeff = createJeff();
        ds.save(jeff);

        // jeff is following scott, so increment scott's followers and re-save
        scott.followers++;
        ds.save(scott);
    }

    private static Programmer createScott() throws ParseException {
        Programmer scott = new Programmer();
        scott.userName = "scotthernandez";
        scott.name = "Scott Hernandez";
        scott.since = SimpleDateFormat.getDateInstance().parse("Aug 12, 2009");
        scott.active = true;
        scott.followers = 8;
        scott.following = Arrays.asList("moraes", "stickfigure");
        return scott;
    }

    private static Programmer createJeff() throws ParseException {
        Programmer scott = new Programmer();
        scott.userName = "jyemin";
        scott.name = "Jeff Yemin";
        scott.since = SimpleDateFormat.getDateInstance().parse("Oct 7, 2011");
        scott.active = true;
        scott.followers = 4;
        scott.following = Arrays.asList("scotthernandez");
        return scott;
    }
}



