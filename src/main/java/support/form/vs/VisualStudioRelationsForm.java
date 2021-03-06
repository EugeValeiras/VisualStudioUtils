package support.form.vs;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VisualStudioRelationsForm {

	/**
	 * 	- relations
	 * 
	 * */
	private String rel;
	private String url;
	
	
	public String getRel() {
		return rel;
	}
	public void setRel(String rel) {
		this.rel = rel;
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	@JsonIgnore
	public String getRelationID(){
		String[] split = url.split("/");
		return split.length > 1 ? split[split.length-1] : "NO ID";
	}
	
	@JsonIgnore
	public String getEditURL(String account, String project){
		return "https://"+account+".visualstudio.com/DefaultCollection/"+project+"/_workitems/edit/"+getRelationID();
	}

	@JsonIgnore
	public Boolean isParent(){
		return rel.equals("System.LinkTypes.Hierarchy-Reverse");
	}
	
	@JsonIgnore
	public Boolean isChild(){
		return rel.equals("System.LinkTypes.Hierarchy-Forward");
	}

	
}
