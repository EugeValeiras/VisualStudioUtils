package support.utils.vs.builder;

import support.utils.vs.build.VisualStudioBuild;
import support.utils.vs.build.VisualStudioBuildDefinition;

public class VisualStudioBuildBuilder {

	private VisualStudioBuild build;
	
	public VisualStudioBuildBuilder() {
		build = new VisualStudioBuild();
	}
	
	public VisualStudioBuildBuilder addDefinition(VisualStudioBuildDefinition definition){
		build.setDefinition(definition);
		return this;
	}
	
	public VisualStudioBuildBuilder setBranch(String branchName){
		build.setSourceBranch("refs/heads/"+branchName);
		return this;
	}
	
	public VisualStudioBuild build(){
		return build;
	}
	
}
