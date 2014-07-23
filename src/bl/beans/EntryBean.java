package bl.beans;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Transient;

import java.util.List;

/**
 * Created by wangronghua on 14-6-17.
 */
@Entity(value = "entry")
public class EntryBean extends Bean implements Comparable<EntryBean>{
    private int sequence; //序号
    private String englishName; //实体变量名称
    private String code; //代码  作为后续生成数据存储模型的表的列名
    private short elementType; //0 定性有数据源1 定量表示没有数据源

    public static enum ElementType {
        QualitativeData(0), QuantitativeData(1);
        private int value;

        ElementType(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }
    private short dataType;  //0  字符型 1 整数型 2浮点型 3 日期型

    public static enum DataType {
        String(0), Integer(1), Float(2), Date(3);
        private int value;

        DataType(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }
    private short subElementType; //元素归类 0 主元素 1 子元数 2 伪参考值主元素 3 伪检查值主元素 4 伪单位主元素 5 伪主元素  6 伪问卷主元素

    public static enum SubElementType {
        MainElement(0), SubElement(1), PseudoRefMainElement(2), PseudoCheckMainElement(3), PseudoUnitMainElement(4), PseudoMainElement(5);
        private int value;

        SubElementType(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }
    private short htmlType; //0 label  1 text  2 textarea  3 select 4 checkbox 5 radio 6 date

    public static enum HtmlType {
        Label(0), Text(1), Textarea(2), Select(3), Checkbox(4), Radio(5), Date(6);
        private int value;

        HtmlType(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }
    private String defaultValue; //缺省值负责一些默认的值业务
    private short standardEntry; //标准分类: 0:CDISC, 1:机构标准, 2:非标准
    private double minValue = 0; //验证最小值
    private double maxValue = 9999; //验证最大值
    private int maxLength = 30; //HTML前端最大输入长度
    private int size = 30; //HTML最大长度
    private String regularExpression; //正则表达式，结合JS或者后台验证
    private String description;//实体描述
    private int precision = 2; //小数位数
    private double pseudoReferenceLowerValue; //参考值下限
    private double pseudoReferenceUpperValue; //参考值上限
    private String pseudoReferenceUnitCode;
    private String documentId; //归属于哪个Document

    @Transient
    private List<EntryCodeBean> entryCodeBeanList;
    @Transient
    private String innerHTML;
    @Transient
    private DocumentBean document;
    @Transient
    private int resolution;

    public String getPseudoReferenceUnitCode() {
        return pseudoReferenceUnitCode;
    }

    public void setPseudoReferenceUnitCode(String pseudoReferenceUnitCode) {
        this.pseudoReferenceUnitCode = pseudoReferenceUnitCode;
    }

    public double getPseudoReferenceLowerValue() {
        return pseudoReferenceLowerValue;
    }

    public void setPseudoReferenceLowerValue(double pseudoReferenceLowerValue) {
        this.pseudoReferenceLowerValue = pseudoReferenceLowerValue;
    }

    public double getPseudoReferenceUpperValue() {
        return pseudoReferenceUpperValue;
    }

    public void setPseudoReferenceUpperValue(double pseudoReferenceUpperValue) {
        this.pseudoReferenceUpperValue = pseudoReferenceUpperValue;
    }

    public int getPrecision() {
        return precision;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public List<EntryCodeBean> getEntryCodeBeanList() {
        if(this.entryCodeBeanList!=null){
            return this.entryCodeBeanList;
        }
        return super.getSubBeans(EntryCodeBean.class, "entryId");
    }

    public void setEntryCodeBeanList(List<EntryCodeBean> entryCodeBeanList) {
        this.entryCodeBeanList = entryCodeBeanList;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public short getElementType() {
        return elementType;
    }

    public void setElementType(short elementType) {
        this.elementType = elementType;
    }

    public short getDataType() {
        return dataType;
    }

    public void setDataType(short dataType) {
        this.dataType = dataType;
    }

    public short getSubElementType() {
        return subElementType;
    }

    public void setSubElementType(short subElementType) {
        this.subElementType = subElementType;
    }

    public short getHtmlType() {
        return htmlType;
    }

    public void setHtmlType(short htmlType) {
        this.htmlType = htmlType;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public short getStandardEntry() {
        return standardEntry;
    }

    public void setStandardEntry(short standardEntry) {
        this.standardEntry = standardEntry;
    }

    public double getMinValue() {
        return minValue;
    }

    public void setMinValue(double minValue) {
        this.minValue = minValue;
    }

    public double getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(double maxValue) {
        this.maxValue = maxValue;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getRegularExpression() {
        return regularExpression;
    }

    public void setRegularExpression(String regularExpression) {
        this.regularExpression = regularExpression;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public DocumentBean getDocument() {
        return document;
    }

    public void setDocument(DocumentBean document) {
        this.document = document;
    }

    public String getInnerHTML() {
        return innerHTML;
    }

    public void setInnerHTML(String innerHTML) {
        this.innerHTML = innerHTML;
    }

    public int getResolution() {
        return resolution;
    }

    public void setResolution(int resolution) {
        this.resolution = resolution;
    }

    @Override
    public int compareTo(EntryBean o) {
        return Integer.valueOf(this.getSequence()).compareTo(o.getSequence());
    }
}
