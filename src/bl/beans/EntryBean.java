package bl.beans;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Transient;

import java.util.List;

/**
 * Created by wangronghua on 14-6-17.
 */
@Entity(value = "entry")
public class EntryBean extends Bean {
    private String entryName; //实体变量名称作为后续生成数据存储模型的表的列名
    private String code; //代码
    private String abbreviation;//英文缩写
    private short elementType; //0 定性有数据源1 定量表示没有数据源
    private short dataType;  //0  字符型 1 数字型 2 日期型
    private short subElementType; //元素归类 0 主元素 1 子元数
    private short htmlType; //0 label  1 text  2 textarea  3 select 4 checkbox 5 radio 6 date
    private String defaultValue; //缺省值负责一些默认的值业务
    private short standardEntry; //0系统缺省元数据 1 国际标准元数据 2 用户临时创建元数据
    private double minValue; //验证最小值
    private double maxValue; //验证最大值
    private int maxLength = 30; //HTML前端最大输入长度
    private int size = 30; //HTML最大长度
    private String regularExpression; //正则表达式，结合JS或者后台验证
    private String documentId; //归属于哪个Document
    @Transient
    private List<EntryCodeBean> entryCodeBeanList;
    @Transient
    private String innerHTML;
    @Transient
    private DocumentBean document;

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

    public String getEntryName() {
        return entryName;
    }

    public void setEntryName(String entryName) {
        this.entryName = entryName;
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

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
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

}
