package actions;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by pli on 14-7-3.
 */
public abstract class UploadExcelAction extends ActionSupport implements ServletRequestAware {
    private static Logger LOG = LoggerFactory.getLogger(UploadExcelAction.class);

    // myFile属性用来封装上传的文件
    protected File myFile;

    // myFileContentType属性用来封装上传文件的类型
    protected String myFileContentType;
    // myFileFileName属性用来封装上传文件的文件名
    protected String myFileFileName;

    protected ArrayList<Object[]> arrayListError = new ArrayList<Object[]>();

    public ArrayList<Object[]> getArrayListError() {
        return arrayListError;
    }

    public void setArrayListError(ArrayList<Object[]> arrayListError) {
        this.arrayListError = arrayListError;
    }

    public String getMyFileContentType() {
        return myFileContentType;
    }

    public void setMyFileContentType(String myFileContentType) {
        this.myFileContentType = myFileContentType;
    }

    public String getMyFileFileName() {
        return myFileFileName;
    }

    public void setMyFileFileName(String myFileFileName) {
        this.myFileFileName = myFileFileName;
    }

    public File getMyFile() {
        return myFile;
    }

    public void setMyFile(File myFile) {
        this.myFile = myFile;
    }

    /**
     * Sub class need to be implemented this method to deal with Excel.
     */
    public abstract String prepareExcel();

    protected String cellConvert(Cell cell) {
        String cellValue = "";
        if (cell == null) {
            return cellValue;
        }
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                cellValue = cell.getRichStringCellValue().getString();
                break;
            case Cell.CELL_TYPE_NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    cellValue = cell.getDateCellValue().toString();
                } else {
                    cellValue = String.valueOf((long) (cell.getNumericCellValue()));
                }
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_FORMULA:
                cellValue = cell.getCellFormula();
                break;
            default:
        }
        cellValue = cellValue.trim().replaceAll("\n", "");
        return cellValue;
    }

    public String batchImportEntry() {
        return SUCCESS;
    }

    public static void main(String[] args) throws Exception {
    }

    private HttpServletRequest request;

    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }

}
