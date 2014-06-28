package bl.beans;

import org.mongodb.morphia.annotations.Entity;

/**
 * Created by pli on 14-6-28.
 */
@Entity(value = "study")
public class StudyBean extends Bean {
    private String code;
    private String englishName;

    private String description;
    private short state = 0; //0 草稿 1 发布
    private String diseaseId; //单病种 指向disease主键
    private String startPoint;//随访起点
    public short getState() {
        return state;
    }

    public void setState(short state) {
        this.state = state;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

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
