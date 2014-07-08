package core;

/**
 * Created by wangronghua on 14-6-28.
 */
public enum EntryType {
    LABEL("label", 0),
    TEXT("text", 1),
    TEXTAREA("textarea", 2),
    SELECT("select", 3),
    CHECKBOX("checkbox", 4),
    RADIO("radio", 5),
    DATE("date", 6),
    TABLE("table", 666),
    DOCUMENT("document", 777),
    VIEW("form", 888),
    NULL("null", 999);

    private int typeId;
    private String name;
    EntryType(String name, int typeId) {
        this.name = name;
        this.typeId = typeId;
    }

    public String getName() {
        return this.name;
    }

    public static EntryType parse(int typeId) {
        EntryType[] types = EntryType.values();
        for(EntryType type : types) {
            if(type.typeId == typeId) {
                return type;
            }
        }
        return EntryType.NULL;
    }
}
