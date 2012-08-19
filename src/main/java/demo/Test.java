package demo;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.mongodb.Mongo;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Test {
    public static void main(String[] args) throws Exception {
        Morphia morphia = new Morphia();
        Mongo mongo = new Mongo();
        mongo.dropDatabase("test");
        Datastore ds = morphia.createDatastore(mongo, "test");

        Programmer programmer = new Programmer();
        programmer.githubUserName = "scotthernandez";
        programmer.name= "Scott Hernandez";
        programmer.memberSince = SimpleDateFormat.getDateInstance().parse("Aug 12, 2009");
        programmer.active = true;
        programmer.followers = 8;
        programmer.following = Arrays.asList("moraes", "stickfigure");
        ds.save(programmer);

        System.out.println(programmer);
    }
}

@Entity("programmers")
class Programmer {
    @Id
    String githubUserName;
    String name;
    Date memberSince;
    boolean active;
    int followers;
    List<String> following;

    @Override
    public String toString() {
        return "Programmer{" +
                "githubUserName='" + githubUserName + '\'' +
                ", name='" + name + '\'' +
                ", memberSince=" + memberSince +
                ", active=" + active +
                ", followers=" + followers +
                ", following=" + following +
                '}';
    }
  }

