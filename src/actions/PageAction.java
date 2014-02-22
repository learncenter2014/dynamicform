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
    private final Map<String, List<TemplateBean>> pageCheckbox = new LinkedHashMap<String, List<TemplateBean>>();
    private final Map<String, List<TemplateBean>> pageUnCheckbox = new LinkedHashMap<String, List<TemplateBean>>();
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

    public Map<String, List<TemplateBean>> getPageCheckbox() {
        return pageCheckbox;
    }
    
    public Map<String, List<TemplateBean>> getPageUnCheckbox() {
        return pageUnCheckbox;
    }
    
    public Map<String, String> getPageMap() {
        return pageMap;
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

                // update page info in page table.
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
            @SuppressWarnings("unchecked")
            List<TemplateBean> elements = (List<TemplateBean>) br.getResponseData();

            PageBusiness pab = (PageBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_PAGEBUSINESS);
            Object record = pab.getAllLeaves().getResponseData();

            if (record != null) {

                @SuppressWarnings("unchecked")
                List<PageBean> pbs = (List<PageBean>) record;
                for (int j = 0; j < pbs.size(); j++) {
                    List<TemplateBean> listRecord = pbs.get(j).getTemplateList();
                    this.pageCheckbox.put(pbs.get(j).getName(), listRecord);
                    
                    List<TemplateBean> uncheckTemplate = new ArrayList<TemplateBean>();
                    for(int i=0;i<elements.size();i++){
                        boolean exist = false;
                        for(int k=0;k<listRecord.size();k++){
                            if(elements.get(i).getName().equals(listRecord.get(k).getName())){
                                exist = true;
                                break;
                            }
                        }
                        if(!exist){
                            uncheckTemplate.add(elements.get(i));  
                        }
                    }
                    this.pageUnCheckbox.put(pbs.get(j).getName(), uncheckTemplate);
                }
            }
            
            //checked pageCheckbox is fully empty.
            Iterator<String> it = this.pageMap.keySet().iterator();
            while(it.hasNext()){
                String key = it.next();
                if(!this.pageCheckbox.containsKey(key)){
                    this.pageUnCheckbox.put(key, elements);
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
