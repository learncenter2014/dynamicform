package bl.beans;

import org.mongodb.morphia.annotations.Entity;

/**
 * Created by pli on 14-6-28.
 */
@Entity(value = "disease")
public class DiseaseBean extends Bean {
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
