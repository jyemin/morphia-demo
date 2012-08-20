package demo;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Property;
import com.google.code.morphia.annotations.Reference;
import com.mongodb.Mongo;
import org.bson.types.ObjectId;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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

        System.out.println(scott);

        Organization mongodb = new Organization();
        mongodb.userName = "mongodb";
        mongodb.name = "mongodb";
        mongodb.since = SimpleDateFormat.getDateInstance().parse("Jan 8, 2009");
        ds.save(mongodb);

        Repository mongoDocs = new Repository(mongodb, "docs");
        ds.save(mongoDocs);

        Repository scottDocs = new Repository(scott, "docs", mongoDocs);
        ds.save(scottDocs);
    }
}

@Entity
abstract class Member {
    @Id String userName;
    @Property("memberSince") Date since;
    boolean active;
    String name;
}

@Entity("programmers")
class Programmer extends Member {
    int followers;
    List<String> following;

    @Override
    public String toString() {
        return "Programmer{" +
                "userName='" + userName + '\'' +
                ", name='" + name + '\'' +
                ", since=" + since +
                ", active=" + active +
                ", followers=" + followers +
                ", following=" + following +
                '}';
    }
}

@Entity("orgs")
class Organization extends Member {
    @Override
    public String toString() {
        return "Programmer{" +
                "userName='" + userName + '\'' +
                ", name='" + name + '\'' +
                ", since=" + since +
                ", active=" + active +
                '}';
    }
}

@Entity("repos")
class Repository {
    @Id ObjectId id;
    @Reference Member owner;
    String name;
    @Reference Repository forkedFrom;

    Repository() {}

    public Repository(final Member owner, final String name) {
        this(owner, name, null);
    }

    Repository(final Member owner, final String name, final Repository forkedFrom) {
        this.owner = owner;
        this.name = name;
        this.forkedFrom = forkedFrom;
    }

    @Override
    public String toString() {
        return "Repository{" +
                "id=" + id +
                ", owner=" + owner +
                ", name='" + name + '\'' +
                ", forkedFrom='" + forkedFrom + '\'' +
                '}';
    }
}



