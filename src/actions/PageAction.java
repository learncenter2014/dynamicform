package actions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import util.ServerContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import bl.beans.PageBean;
import bl.beans.TemplateBean;
import bl.common.BusinessResult;
import bl.constants.BusTieConstant;
import bl.instancepool.SingleBusinessPoolManager;
import bl.mongobus.FormBusiness;
import bl.mongobus.PageBusiness;

public class PageAction extends ActionSupport {
    private List<String[]> templateList = new ArrayList<String[]>();
    private final Map<String, String[]> patientCheckbox = new LinkedHashMap<String, String[]>();
    private final Map<String, String> pageMap = new LinkedHashMap<String, String>();

    public PageAction() {
        String pagemanager = ServerContext.getValue("pagemanager");
        String[] array = pagemanager.split(",");
        // key=value
        for (int i = 0; i < array.length; i++) {
            String[] keyValue = array[i].split("=");
            pageMap.put(keyValue[0], keyValue[1]);
        }
    }

    public Map<String, String[]> getPatientCheckbox() {
        return patientCheckbox;
    }

    public Map<String, String> getPageMap() {
        return pageMap;
    }

    /**
     * @return the templateList
     */
    public List<String[]> getTemplateList() {
        return templateList;
    }

    /**
     * @param templateList
     *            the templateList to set
     */
    public void setTemplateList(List<String[]> templateList) {
        this.templateList = templateList;
    }

    public String pageSave() {
        try {
            PageBusiness pab = (PageBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_PAGEBUSINESS);
            FormBusiness fb = (FormBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_FORMBUSINESS);

            Iterator<String> it = this.pageMap.keySet().iterator();
            Map<String, Object> parameters = ActionContext.getContext().getParameters();
            while (it.hasNext()) {
                String key = it.next();
                String[] arrayTemplates = (String[]) parameters.get(key);
                Object record = pab.getLeafByName(key).getResponseData();
                ArrayList<TemplateBean> ar = new ArrayList<TemplateBean>();
                for (int i = 0; i < arrayTemplates.length; i++) {
                    Object o = fb.getLeafByName(arrayTemplates[i]).getResponseData();
                    if (o != null) {
                        ar.add((TemplateBean) o);
                    }
                }

                // update patient info in page table.
                if (record != null) {
                    PageBean orginalBean = (PageBean) record;
                    PageBean newBean = (PageBean) orginalBean.clone();
                    newBean.setTemplateList(ar);
                    pab.updateLeaf(orginalBean, newBean);
                } else {
                    PageBean pbean = new PageBean();
                    pbean.setName(key);
                    pbean.setLabel(this.pageMap.get(key));
                    pbean.setTemplateList(ar);
                    pab.createLeaf(pbean);
                }
            }

        } catch (Exception e) {
            LOG.error("this exception [#0]", e.getMessage());
        }
        return ActionSupport.SUCCESS;
    }

    public String pageEdit() {
        try {

            FormBusiness fb = (FormBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_FORMBUSINESS);
            // find all records in the template;
            BusinessResult br = fb.getAllLeaves();
            List<TemplateBean> elements = (List<TemplateBean>) br.getResponseData();
            for (TemplateBean te : elements) {
                String[] row = new String[] { te.getLabel(), te.getName() };
                this.templateList.add(row);
            }

            PageBusiness pab = (PageBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_PAGEBUSINESS);
            Object record = pab.getAllLeaves().getResponseData();

            if (record != null) {

                List<PageBean> pbs = ((List<PageBean>) record);
                for (int j = 0; j < pbs.size(); j++) {
                    List<TemplateBean> listRecord = pbs.get(j).getTemplateList();
                    String[] createString = new String[listRecord.size()];
                    for (int i = 0; i < listRecord.size(); i++) {
                        createString[i] = listRecord.get(i).getName();
                    }
                    this.patientCheckbox.put(pbs.get(j).getName(), createString);
                }
            }
        } catch (Exception e) {
            LOG.error("this exception [#0]", e.getMessage());
        }
        return ActionSupport.SUCCESS;
    }

    public static void main(String[] args) {
        FormBusiness fb = (FormBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_FORMBUSINESS);
        // create a leaf in the MongoDB.
        TemplateBean tb = new TemplateBean();
        tb.setLabel("测试数据1");
        tb.setName("template1");
        tb.setPath("xml/template1.xml");
        fb.createLeaf(tb);

        PageBusiness pa = (PageBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_PAGEBUSINESS);
        // create a page in the MongoDB.
        PageBean pab = new PageBean();
        pab.setLabel("病人管理");
        pab.setName("page1");
        List<TemplateBean> list = new ArrayList<TemplateBean>();
        list.add(tb);
        pab.setTemplateList(list);
        pa.createLeaf(pab);

        PageBean getP = (PageBean) pa.getLeafByName("page1").getResponseData();
        System.out.println(getP.getTemplateList().get(0).getPath());

        // delete this record.

        pa.deleteLeaf(getP.get_id().toString());

        fb.deleteLeaf(getP.getTemplateList().get(0).get_id().toString());
    }
}
