package demo;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Reference;
import org.bson.types.ObjectId;

@Entity("repos")
public class Repository {
    @Id
    ObjectId id;
    @Reference
    Member owner;
    String name;
    @Reference(lazy = true) Repository forkedFrom;

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
