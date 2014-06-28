package bl.beans;

import org.mongodb.morphia.annotations.Entity;

/**
 * Created by pli on 14-6-28.
 */
@Entity(value = "study")
public class StudyBean extends Bean {
    private String code;
    private String description;
    private String diseaseId; //单病种 指向disease主键

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDiseaseId() {
        return diseaseId;
    }

    public void setDiseaseId(String diseaseId) {
        this.diseaseId = diseaseId;
    }
}
