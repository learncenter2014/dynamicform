package bl.beans;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Indexed;

/**
 * 科室Bean
 * 
 * @author zlj Date: 2014/07/08
 * @version 1.0
 *
 */
@Entity(value = "department")
public class DepartmentBean extends Bean{

	private static final long serialVersionUID = 1L;
	@Indexed
	private String uniqueDepartmentId;// 科室代码
	private String deptName;// 科室名称
	private int fuRole;// 随访角色
	private String outsideThePrefix;//外线前缀
	private String longDistanceThePrefix;//拨打长途加拨号码
	private int hasLongDistance;//是否有长途
	public String getUniqueDepartmentId() {
		return uniqueDepartmentId;
	}
	public void setUniqueDepartmentId(String uniqueDepartmentId) {
		this.uniqueDepartmentId = uniqueDepartmentId;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public int getFuRole() {
		return fuRole;
	}
	public void setFuRole(int fuRole) {
		this.fuRole = fuRole;
	}
	public String getOutsideThePrefix() {
		return outsideThePrefix;
	}
	public void setOutsideThePrefix(String outsideThePrefix) {
		this.outsideThePrefix = outsideThePrefix;
	}
	public String getLongDistanceThePrefix() {
		return longDistanceThePrefix;
	}
	public void setLongDistanceThePrefix(String longDistanceThePrefix) {
		this.longDistanceThePrefix = longDistanceThePrefix;
	}
	public int getHasLongDistance() {
		return hasLongDistance;
	}
	public void setHasLongDistance(int hasLongDistance) {
		this.hasLongDistance = hasLongDistance;
	}
	
}
