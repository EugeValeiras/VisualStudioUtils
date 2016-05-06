package support.utils.vs.master;

public class VisualStudioBuild {

	private VisualStudioBuildDefinition definition;
	private String sourceBranch;
	
	public VisualStudioBuildDefinition getDefinition() {
		return definition;
	}
	public void setDefinition(VisualStudioBuildDefinition definition) {
		this.definition = definition;
	}
	public String getSourceBranch() {
		return sourceBranch;
	}
	public void setSourceBranch(String sourceBranch) {
		this.sourceBranch = sourceBranch;
	}
}
