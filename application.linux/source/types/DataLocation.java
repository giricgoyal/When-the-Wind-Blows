/**
 * 
 */
package types;


/**
 * @author giric
 *
 */
public class DataLocation {
	int id;
	String name;
	String category;
	int cid;
	String parentId;
	
	public DataLocation(int id, String name, String category, int cid, String parentId){
		this.id = id;
		this.name = name;
		this.category = category;
		this.cid = cid;
		this.parentId = parentId;
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getCategory() {
		return this.category;
	}
	
	public int getCid() {
		return this.cid;
	}
	
	public String getParentId() {
		return this.parentId;
	}
}
