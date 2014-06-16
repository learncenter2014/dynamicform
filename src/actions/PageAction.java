package actions;

import bl.beans.PageBean;
import bl.beans.TemplateBean;
import bl.constants.BusTieConstant;
import bl.instancepool.SingleBusinessPoolManager;
import bl.mongobus.FormBusiness;
import bl.mongobus.PageBusiness;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

public class PageAction extends ActionSupport {
    PageBean pageBean = null;
    List<PageBean> pageBeans = null;
    List<TemplateBean> templateBeans = null;
    List<TemplateBean> templateExists = null;

    public List<TemplateBean> getTemplateExists() {
        return templateExists;
    }

    public void setTemplateExists(List<TemplateBean> templateExists) {
        this.templateExists = templateExists;
    }

    String name = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PageBean> getPageBeans() {
        return pageBeans;
    }

    public void setPageBeans(List<PageBean> pageBeans) {
        this.pageBeans = pageBeans;
    }

    public List<TemplateBean> getTemplateBeans() {
        return templateBeans;
    }

    public void setTemplateBeans(List<TemplateBean> templateBeans) {
        this.templateBeans = templateBeans;
    }

    public PageBean getPageBean() {
        return pageBean;
    }

    public void setPageBean(PageBean pageBean) {
        this.pageBean = pageBean;
    }

    public PageAction() {
    }

    private void structureTemplate() {
        FormBusiness fb = (FormBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_FORMBUSINESS);
        Object result = fb.getAllLeaves().getResponseData();
        if (result != null) {
            this.templateExists = (List<TemplateBean>) result;
        }
    }

    public String pageDelete() {
        if (this.name != null) {
            PageBusiness pab = (PageBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_PAGEBUSINESS);
            PageBean pb = (PageBean) pab.getLeafByName(this.name).getResponseData();
            if (pb != null)
                pab.deleteLeaf(pb.get_id().toString());
        }
        return ActionSupport.SUCCESS;
    }

    public String pageEdit() {
        structureTemplate();
        if (this.name != null) {
            PageBusiness pab = (PageBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_PAGEBUSINESS);
            this.pageBean = (PageBean) pab.getLeafByName(this.name).getResponseData();
        }
        return ActionSupport.SUCCESS;
    }

    public String pageSave() {
        try {
            PageBusiness pab = (PageBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_PAGEBUSINESS);
            FormBusiness fb = (FormBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_FORMBUSINESS);

            Object record = pab.getLeafByName(this.pageBean.getName()).getResponseData();
            List<TemplateBean> list = this.pageBean.getTemplateList();
            if (list != null) {
                List<TemplateBean> createArray = new ArrayList<TemplateBean>();
                for (int i = 0, length = list.size(); i < length; i++) {
                    TemplateBean tb = (TemplateBean) fb.getLeafByName(list.get(i).getName()).getResponseData();
                    if(tb!=null){
                        createArray.add(tb);
                    }
                }
                this.pageBean.setTemplateList(createArray);
            }
            // update page info in page table.
            if (record != null) {
                PageBean originalBean = (PageBean) record;
                PageBean newBean = (PageBean) BeanUtils.cloneBean(originalBean);
                this.pageBean.set_id(originalBean.get_id());
                BeanUtils.copyProperties(newBean, this.pageBean);
                pab.updateLeaf(originalBean, newBean);
            } else {
                this.pageBean.setName("page" + System.currentTimeMillis());
                pab.createLeaf(this.pageBean);
            }

        } catch (Exception e) {
            LOG.error("this exception [{}]", e.getMessage());
        }
        return ActionSupport.SUCCESS;
    }

    public String pageList() {
        try {
            PageBusiness pab = (PageBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_PAGEBUSINESS);
            Object record = pab.getAllLeaves().getResponseData();
            this.pageBeans = (List<PageBean>) record;

        } catch (Exception e) {
            LOG.error("this exception [{}]", e.getMessage());
        }
        return ActionSupport.SUCCESS;
    }

    public String templateDecorator(){
        try{
            PageBusiness pab = (PageBusiness) SingleBusinessPoolManager.getBusObj(BusTieConstant.BUS_CPATH_PAGEBUSINESS);
            Object record = pab.getAllLeaves().getResponseData();
            String requestURI = ((HttpServletRequest)ActionContext.getContext().get(org.apache.struts2.StrutsStatics.HTTP_REQUEST)).getRequestURI();
            List<PageBean> list = new ArrayList<PageBean>();
            List<PageBean> recordBeans = (List<PageBean>)record;
            for (int i = 0; i < recordBeans.size(); i++) {
                PageBean pb = recordBeans.get(i);
                String source = pb.getSource();
                if (requestURI != null && requestURI.indexOf(source) != -1) {
                    list.add(pb);
                }
            }
            this.pageBeans = list;
        } catch (Exception e) {
            LOG.error("this exception [{}]", e.getMessage());
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
