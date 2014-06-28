package bl.beans;

import org.mongodb.morphia.annotations.Entity;

/**
 * Created by pli on 14-6-28.
 */
@Entity(value = "view")
public class ViewBean extends Bean {
    private String studyId;
    private int sequence; //序号
    private String description; //备注或者描述
    private short type; // 0随访基线 1 随访录入

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public short getType() {
        return type;
    }

    public void setType(short type) {
        this.type = type;
    }

    public String getStudyId() {
      return studyId;
    }

    public void setStudyId(String studyId) {
      this.studyId = studyId;
    }

}
