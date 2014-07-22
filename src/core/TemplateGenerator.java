package core;

import bl.beans.*;
import bl.constants.BusTieConstant;
import bl.instancepool.SingleBusinessPoolManager;
import bl.mongobus.DocumentBusiness;
import bl.mongobus.EntryBusiness;
import bl.mongobus.StudyBusiness;
import bl.mongobus.ViewBusiness;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by wangronghua on 14-1-27.
 */
public class TemplateGenerator {

    private static TemplateGenerator instance = new TemplateGenerator();

    public static TemplateGenerator get() {
        return instance;
    }

    public boolean genTemplate(String viewId) {
        ViewBusiness viewBus = (ViewBusiness)SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_VIEWBUSINESS);
        ViewBean viewBean = (ViewBean)viewBus.getLeaf(viewId).getResponseData();
        if(null != viewBean) {
            return genTemplate(viewBean);
        } else {
            return false;
        }
    }

    public boolean genTemplate(ViewBean viewBean) {
        DocumentBusiness documentBus = (DocumentBusiness)SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_DOCUMENTBUSINESS);
        ViewBusiness viewBus = (ViewBusiness)SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_VIEWBUSINESS);
        StudyBusiness studyBus = (StudyBusiness)SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_STUDYBUSINESS);

        List<ViewDocumentBean> viewDocumentBeanList = viewBus.getViewDocumentList(viewBean.getId());
        StudyBean studyBean = (StudyBean)studyBus.getLeaf(viewBean.getStudyId()).getResponseData();
        viewBean.setStudy(studyBean);

        StringBuilder innerHTML = new StringBuilder();
        for(ViewDocumentBean viewDocumentBean : viewDocumentBeanList) {
            String documentId = viewDocumentBean.getDocumentId();
            DocumentBean documentBean = (DocumentBean)documentBus.getLeaf(documentId).getResponseData();
            if(null != documentBean) {
                innerHTML.append(this.genTemplate(viewBean.getStudyId(), documentBean));
            }
        }

        viewBean.setInnerHTML(innerHTML.toString());
        String ftlContent = TemplateHelper.get().getTemplate(EntryType.VIEW, viewBean);

        File template = new File(Constants.TEMPLATE_PATH_TEMP + "/" + viewBean.getId() + ".ftl");
        try {
            Writer templateWriter = new OutputStreamWriter(new FileOutputStream(template), "UTF-8");
            templateWriter.write(ftlContent);
            templateWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            //todo exception
            return false;
        }
        return true;
    }

    private String genTemplate(String studyId, DocumentBean documentBean) {
        if(null == documentBean) return "";
        EntryBusiness entryBus = (EntryBusiness)SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_ENTRYBUSINESS);
        StudyBusiness studyBus = (StudyBusiness)SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_STUDYBUSINESS);
        List<StudyDocumentEntryBean> studyDocumentEntryBeanList = studyBus.getStudyDocumentEntryList(studyId, documentBean.getId()); //get beans by studyId and DocumentId
        StringBuilder innerHTML = new StringBuilder();
        int resolution = 12/documentBean.getColumnCount();
        List<EntryBean> rows = new ArrayList<EntryBean>();
        List<EntryBean> columns = new ArrayList<EntryBean>();

        for(StudyDocumentEntryBean bean : studyDocumentEntryBeanList) {
            EntryBean entryBean = (EntryBean)entryBus.getLeaf(bean.getEntryId()).getResponseData();
            if((documentBean.getType() == DocumentBean.Type.NoSub.getValue() || entryBean.getSubElementType() == EntryBean.SubElementType.PseudoMainElement.getValue()) && null != entryBean) {
                entryBean.setDocument(documentBean);
                entryBean.setResolution(resolution);
                innerHTML.append(genTemplate(entryBean));
            } else {
                if(entryBean.getSubElementType() == DocumentBean.Type.HasSub.getValue()) {
                    rows.add(entryBean);
                } else {
                    columns.add(entryBean);
                }
            }
        }

        if(documentBean.getType() != DocumentBean.Type.NoSub.getValue()) {
            this.sortEntryBySequence(rows);
            this.sortEntryBySequence(columns);

            documentBean.setRows(rows);
            documentBean.setColumns(columns);
            innerHTML.append(TemplateHelper.get().getTemplate(EntryType.TABLE, documentBean));
        }

        documentBean.setInnerHTML(innerHTML.toString());
        return TemplateHelper.get().getTemplate(EntryType.DOCUMENT, documentBean);

    }

    private void sortEntryBySequence(List<EntryBean> entryList) {

        Collections.sort(entryList, new Comparator<EntryBean>() {
            @Override
            public int compare(EntryBean o1, EntryBean o2) {
                if(o1.getSequence() > o2.getSequence()){
                    return 1;
                } else if(o1.getSequence() == o2.getSequence()) {
                    return 0;
                }
                return -1;
            }
        });
    }

    private String genTemplate(EntryBean entryBean) {
        EntryType type = EntryType.parse(entryBean.getHtmlType());
        if (type == EntryType.NULL) {
            return "";
        } else {
            return TemplateHelper.get().getTemplate(type, entryBean);
        }
    }

//    public boolean genTemplate(String xmlFilePath, String templateId) {
//        File template = new File(Constants.TEMPLATE_PATH_TEMP + "/" + templateId);
//        Writer templateWriter = null;
//        try {
//            templateWriter = new OutputStreamWriter(new FileOutputStream(template), "UTF-8");
//            XmlReader reader = new XmlReader(xmlFilePath);
//            Document document = reader.readDocument();
//            templateWriter.write(this.genTemplate(document));
//            templateWriter.close();
//        } catch (XmlFileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//            //todo exception
//        } catch (JAXBException e) {
//            e.printStackTrace();
//        }
//        return true;
//    }
//
//    String genTemplate(Document document) {
//        ComponentEnhancer enhancer = new ComponentEnhancer();
//        DocumentExtension formExt = (DocumentExtension)(document.enhance(enhancer));
//        List<Form> forms = document.getForm();
//        formExt.setInnerHTML(genTemplate(forms.toArray(new Form[0])));
//        return TemplateHelper.get().getTemplate("document", formExt);
//    }
//
//    String genTemplate(Form[] forms) {
//        ComponentEnhancer enhancer = new ComponentEnhancer();
//        StringBuilder result = new StringBuilder();
//        for (Form form: forms) {
//            FormExtension formExt = (FormExtension)(form.enhance(enhancer));
//            List<Section> fieldSets = form.getSection();
//            formExt.setInnerHTML(genTemplate(fieldSets.toArray(new Section[0])));
//            result.append(TemplateHelper.get().getTemplate("form", formExt));
//        }
//        return result.toString();
//    }
//
//    String genTemplate(Section[] fieldSets) {
//        ComponentEnhancer enhancer = new ComponentEnhancer();
//        StringBuilder result = new StringBuilder();
//        for(Section fieldSet: fieldSets) {
//            List<Row> rows = fieldSet.getRow();
//            String dtString = genTemplate(rows.toArray(new Row[0]));
//
//            FieldSetExtension fieldSetExtension = (FieldSetExtension)fieldSet.enhance(enhancer);
//            fieldSetExtension.setInnerHTML(dtString);
//            result.append(TemplateHelper.get().getTemplate("section", fieldSetExtension));
//        }
//        return result.toString();
//    }
//
//    String genTemplate(Row[] rows) {
//        ComponentEnhancer enhancer = new ComponentEnhancer();
//        StringBuilder result = new StringBuilder();
//        for(Row row: rows) {
//            List<DynamicType> dynamicTypes = row.getElement();
//            String dtString = genTemplate(dynamicTypes.toArray(new DynamicType[0]), row.getSize());
//
//            RowExtension rowExtension = (RowExtension)row.enhance(enhancer);
//            rowExtension.setInnerHTML(dtString);
//            result.append(TemplateHelper.get().getTemplate("row", rowExtension));
//        }
//        return result.toString();
//    }
//
//    String genTemplate(DynamicType[] types, int size) {
//        int resolution = Constants.TOTAL_COLUMN_SIZE/size;
//        DynamicType[] dynamicTypes = new DynamicType[size];
//        for(DynamicType dynamicType: types) {
//            dynamicTypes[dynamicType.getPosition()] = dynamicType;
//        }
//
//        StringBuilder result = new StringBuilder();
//        ComponentEnhancer enhancer = new ComponentEnhancer();
//        for(DynamicType dynamicType: dynamicTypes) {
//            if(dynamicType == null) {
//                HashMap map = new HashMap();
//                map.put("resolution",resolution);
//                result.append(TemplateHelper.get().getTemplate("blankcell", map));
//                continue;
//            }
//            StringBuilder type = new StringBuilder("get").append(dynamicType.getType());
//            type.setCharAt(3, Character.toUpperCase(type.charAt(3)));
//            try {
//                Method med = DynamicType.class.getDeclaredMethod(type.toString());
//                Component component = (Component)med.invoke(dynamicType);
//                ComponentBase extension = component.enhance(enhancer);
//                extension.setResolution(resolution);
//                //todo use vistior mode to enhance the componenet
//                result.append(TemplateHelper.get().getTemplate(dynamicType.getType().toLowerCase(), extension)) ;
//            } catch (NoSuchMethodException e) {
//                //todo LOG.error();
//            } catch (InvocationTargetException e) {
//                e.printStackTrace();
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            }
//
//        }
//        return result.toString();
//    }

}