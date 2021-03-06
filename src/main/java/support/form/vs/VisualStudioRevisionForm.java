package support.form.vs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import support.utils.vs.VisualStudioTaskData;
import support.utils.vs.VisualStudioTaskState;
import support.utils.vs.master.VisualStudioUtil;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VisualStudioRevisionForm {

	private VisualStudioFieldForm fields;
	private List<VisualStudioRelationsForm> relations = new ArrayList<VisualStudioRelationsForm>();
	private Integer id;

	public VisualStudioFieldForm getFields() {
		return fields;
	}
	public void setFields(VisualStudioFieldForm fields) {
		this.fields = fields;
	}

	public List<VisualStudioRelationsForm> getRelations() {
		return relations;
	}
	public void setRelations(List<VisualStudioRelationsForm> relations) {
		this.relations = relations;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	//Methods
	@JsonIgnore
	public String getTitle() {
		return fields.getTitle();
	}
	
	@JsonIgnore
	public Boolean haveRelations(){
		return relations != null && relations.size() > 0;
	}
	
	@JsonIgnore
	public List<VisualStudioRelationsForm> getParents(){
		return Arrays.asList(relations.stream().filter((rel) -> rel.isParent()).toArray(VisualStudioRelationsForm[]::new));
	}
	
	@JsonIgnore
	public List<VisualStudioRelationsForm> getChilds(){
		return Arrays.asList(relations.stream().filter((rel) -> rel.isChild()).toArray(VisualStudioRelationsForm[]::new));
	}
	
	@JsonIgnore
	public VisualStudioRelationsForm getRelationNumber(Integer number){
		if(haveRelations() && relations.size() >= number){
			return relations.get(number-1);
		} else {
			return null;
		}
	}

	@JsonIgnore
	public String getAssignedToEmail() throws Exception {
		if(fields != null){
			return fields.getAssignedToEmail();
		} 
		throw new Exception("VisualStudioRevisionForm.getAssignedToEmail");
	}
	
	@JsonIgnore
	public String getAssignedToName() throws Exception {
		if(fields != null){
			return fields.getAssignedToName();
		} 
		throw new Exception("VisualStudioRevisionForm.getAssignToName");
	}
	
	@JsonIgnore
	public String getCreatedByEmail() throws Exception {
		if(fields != null){
			return fields.getCreatedByEmail();
		} 
		throw new Exception("VisualStudioRevisionForm.getCreatedByEmail");
	}

	@JsonIgnore
	public String getCreatedByName() throws Exception {
		if(fields != null){
			return fields.getCreatedByName();
		} 
		throw new Exception("VisualStudioRevisionForm.getCreatedByName");
	}
	
	public boolean isDevelopFix(){
		String title = getTitle();
		return (title != null) ? title.toLowerCase().contains(VisualStudioTaskData.DEVELOP_FIX.value.toLowerCase()) : false;
	}

	@JsonIgnore
	public boolean isInvestigateBug() {
		String title = getTitle();
		return (title != null) ? title.toLowerCase().contains(VisualStudioTaskData.INVESTIGATE_BUG.value.toLowerCase()) : false;
	}
	
	@JsonIgnore
	public boolean isMergeToMasterBug(){
		String title = getTitle();
		return (title != null) ? title.toLowerCase().contains(VisualStudioTaskData.MERGE_TO_MASTER_BUG.value.toLowerCase()) : false;
	}
	
	@JsonIgnore
	public boolean isTestStrategyBug() {
		String title = getTitle();
		return (title != null) ? title.toLowerCase().contains(VisualStudioTaskData.TEST_STRATEGY_BUG.value.toLowerCase()) : false;
	}
	
	@JsonIgnore
	public boolean isCreateANewBranch() {
		String title = getTitle();
		return (title != null) ? title.toLowerCase().contains(VisualStudioTaskData.CREATE_A_NEW_BRANCH.value.toLowerCase()) : false;
	}
	
	@JsonIgnore
	public boolean isReproduceWithAutomatedTest() {
		String title = getTitle();
		return (title != null) ? title.toLowerCase().contains(VisualStudioTaskData.REPRODUCE_WITH_AUTOMATED_TESTS.value.toLowerCase()) : false;
	}
	
	@JsonIgnore
	public boolean isGetCodeReview() {
		String title = getTitle();
		return (title != null) ? title.toLowerCase().contains(VisualStudioTaskData.GET_CODE_REVIEW.value.toLowerCase()) : false;
	}
	
	@JsonIgnore
	public boolean isFunctionalTest() {
		String title = getTitle();
		return (title != null) ? title.toLowerCase().contains(VisualStudioTaskData.FUNCTIONAL_TEST.value.toLowerCase()) : false;
	}
	
	@JsonIgnore
	public boolean isRebaseIntegrationToMaster() {
		String title = getTitle();
		return (title != null) ? title.toLowerCase().contains(VisualStudioTaskData.REBASE_INTEGRATION_TO_MASTER.value.toLowerCase()) : false;
	}
	
	@JsonIgnore
	public boolean isTestOnMasterBug() {
		String title = getTitle();
		return (title != null) ? title.toLowerCase().contains(VisualStudioTaskData.TEST_ON_MASTER_BUG.value.toLowerCase()) : false;
	}
	
	@JsonIgnore
	public boolean isEstimateStory() {
		String title = getTitle();
		return (title != null) ? title.toLowerCase().contains(VisualStudioTaskData.ESTIMATE_STORY.value.toLowerCase()) : false;
	}
	
	@JsonIgnore
	public boolean isTestingStrategyStory() {
		String title = getTitle();
		return (title != null) ? title.toLowerCase().contains(VisualStudioTaskData.TEST_STRATEGY_STORY.value.toLowerCase()) : false;
	}
	
	@JsonIgnore
	public boolean isDevelopTDD() {
		String title = getTitle();
		return (title != null) ? title.toLowerCase().contains(VisualStudioTaskData.DEVELOP_TDD_INTEGRATION_TESTS.value.toLowerCase()) : false;
	}
	
	@JsonIgnore
	public boolean isDevelopCodeStory() {
		String title = getTitle();
		return (title != null) ? title.toLowerCase().contains(VisualStudioTaskData.DEVELOP_CODE_FOR_STORY.value.toLowerCase()) : false;
	}
	
	@JsonIgnore
	public boolean isPostStoryMovie() {
		String title = getTitle();
		return (title != null) ? title.toLowerCase().contains(VisualStudioTaskData.POST_STORY_MOVIE.value.toLowerCase()) : false;
	}
	
	@JsonIgnore
	public boolean isApproveStoryMovie() {
		String title = getTitle();
		return (title != null) ? title.toLowerCase().contains(VisualStudioTaskData.APPROVE_STORY_MOVIE.value.toLowerCase()) : false;
	}
	
	@JsonIgnore
	public boolean isPullRequestForStory() {
		String title = getTitle();
		return (title != null) ? title.toLowerCase().contains(VisualStudioTaskData.PULL_REQUEST_FOR_STORY.value.toLowerCase()) : false;
	}
	
	@JsonIgnore
	public boolean isFunctionalTestOnIntegration() {
		String title = getTitle();
		return (title != null) ? title.toLowerCase().contains(VisualStudioTaskData.FUNCTIONAL_TEST_ON_INTEGRATION.value.toLowerCase()) : false;
	}
	
	@JsonIgnore
	public boolean isUIAutomatedTesting() {
		String title = getTitle();
		return (title != null) ? title.toLowerCase().contains(VisualStudioTaskData.UI_AUTOMATED_TESTING.value.toLowerCase()) : false;
	}
	
	@JsonIgnore
	public boolean isMergeToMasterStory() {
		String title = getTitle();
		return (title != null) ? title.toLowerCase().contains(VisualStudioTaskData.MERGE_TO_MASTER_STORY.value.toLowerCase()) : false;
	}
	
	@JsonIgnore
	public boolean isTestOnMasterStory() {
		String title = getTitle();
		return (title != null) ? title.toLowerCase().contains(VisualStudioTaskData.TEST_ON_MASTER_STORY.value.toLowerCase()) : false;
	}
	
	@JsonIgnore
	public boolean isApproveForRelease() {
		String title = getTitle();
		return (title != null) ? title.toLowerCase().contains(VisualStudioTaskData.APPROVE_FOR_RELEASE.value.toLowerCase()) : false;
	}
	
	@JsonIgnore
	public boolean isABug(){
		return getFields().isABug();
	}
	
	
	@JsonIgnore
	public boolean isAStory(){
		return getFields().isAStory();
	}
	
	@JsonIgnore
	public boolean isATask(){
		return getFields().isATask();
	}
	
	@JsonIgnore
	public boolean isCritical() {
		return getFields().isCritical();
	}
	
	//Lambda Methods
	interface finderRelation {
		boolean evaluate(VisualStudioRevisionForm workItem);
	}
	
	@JsonIgnore	
	public VisualStudioRevisionForm find(VisualStudioUtil vsUtil, finderRelation finder, String account) throws Exception {
		for(VisualStudioRelationsForm relation : relations){
			VisualStudioRevisionForm workItem = vsUtil.getWorkItem(relation.getRelationID(), account);
			if(finder.evaluate(workItem)) return workItem;
		}
		return null;
	}
	
	@JsonIgnore	
	public VisualStudioRevisionForm findDevelopFixTask(VisualStudioUtil vsUtil, String account) throws Exception {
		return find(vsUtil, (workItem) -> workItem.isDevelopFix(), account);
	}
	
	@JsonIgnore	
	public VisualStudioRevisionForm findMergeToMasterTask(VisualStudioUtil vsUtil, String account) throws Exception {
		return find(vsUtil, (workItem) -> workItem.isMergeToMasterBug(), account);
	}
	
	@JsonIgnore	
	public VisualStudioRevisionForm findCreateNewBranch(VisualStudioUtil vsUtil, String account) throws Exception {
		return find(vsUtil, (workItem) -> workItem.isCreateANewBranch(), account);
	}
	
	@JsonIgnore	
	public VisualStudioRevisionForm findInvestigateBug(VisualStudioUtil vsUtil, String account) throws Exception {
		return find(vsUtil, (workItem) -> workItem.isInvestigateBug(), account);
	}
	
	@JsonIgnore	
	public VisualStudioRevisionForm findTestStrategy(VisualStudioUtil vsUtil, String account) throws Exception {
		return find(vsUtil, (workItem) -> workItem.isTestStrategyBug(), account);
	}

	@JsonIgnore	
	public VisualStudioRevisionForm findReproduceWithAutomatedTest(VisualStudioUtil vsUtil, String account) throws Exception {
		return find(vsUtil, (workItem) -> workItem.isReproduceWithAutomatedTest(), account);
	}

	@JsonIgnore	
	public VisualStudioRevisionForm findDevelopFix(VisualStudioUtil vsUtil, String account) throws Exception {
		return find(vsUtil, (workItem) -> workItem.isDevelopFix(), account);
	}

	@JsonIgnore	
	public VisualStudioRevisionForm findGetCodeReview(VisualStudioUtil vsUtil, String account) throws Exception {
		return find(vsUtil, (workItem) -> workItem.isGetCodeReview(), account);
	}

	@JsonIgnore	
	public VisualStudioRevisionForm findFunctionalTest(VisualStudioUtil vsUtil, String account) throws Exception {
		return find(vsUtil, (workItem) -> workItem.isFunctionalTest(), account);
	}

	@JsonIgnore	
	public VisualStudioRevisionForm findMergeToMaster(VisualStudioUtil vsUtil, String account) throws Exception {
		return find(vsUtil, (workItem) -> workItem.isMergeToMasterBug(), account);
	}

	@JsonIgnore	
	public VisualStudioRevisionForm findRebaseIntegrationToMaster(VisualStudioUtil vsUtil, String account) throws Exception {
		return find(vsUtil, (workItem) -> workItem.isRebaseIntegrationToMaster(), account);
	}

	@JsonIgnore	
	public VisualStudioRevisionForm findTestOnMasterBug(VisualStudioUtil vsUtil, String account) throws Exception {
		return find(vsUtil, (workItem) -> workItem.isTestOnMasterBug(), account);
	}
	
	@JsonIgnore	
	public VisualStudioRevisionForm findEstimateStory(VisualStudioUtil vsUtil, String account) throws Exception {
		return find(vsUtil, (workItem) -> workItem.isEstimateStory(), account);
	}
	
	@JsonIgnore	
	public VisualStudioRevisionForm findDevelopTDD(VisualStudioUtil vsUtil, String account) throws Exception {
		return find(vsUtil, (workItem) -> workItem.isDevelopTDD(), account);
	}
	
	@JsonIgnore	
	public VisualStudioRevisionForm findDevelopCodeForStory(VisualStudioUtil vsUtil, String account) throws Exception {
		return find(vsUtil, (workItem) -> workItem.isDevelopCodeStory(), account);
	}
	
	@JsonIgnore	
	public VisualStudioRevisionForm findPostStoryMovie(VisualStudioUtil vsUtil, String account) throws Exception {
		return find(vsUtil, (workItem) -> workItem.isPostStoryMovie(), account);
	}
	
	@JsonIgnore	
	public VisualStudioRevisionForm findApproveStoryMovie(VisualStudioUtil vsUtil, String account) throws Exception {
		return find(vsUtil, (workItem) -> workItem.isApproveStoryMovie(), account);
	}
	
	@JsonIgnore	
	public VisualStudioRevisionForm findPullRequestForStory(VisualStudioUtil vsUtil, String account) throws Exception {
		return find(vsUtil, (workItem) -> workItem.isPullRequestForStory(), account);
	}
	
	@JsonIgnore	
	public VisualStudioRevisionForm findTestingStrategy(VisualStudioUtil vsUtil, String account) throws Exception {
		return find(vsUtil, (workItem) -> workItem.isTestingStrategyStory(), account);
	}
	
	@JsonIgnore	
	public VisualStudioRevisionForm findFunctionalTestOnIntegration(VisualStudioUtil vsUtil, String account) throws Exception {
		return find(vsUtil, (workItem) -> workItem.isFunctionalTestOnIntegration(), account);
	}
	
	@JsonIgnore	
	public VisualStudioRevisionForm findUIAutomatedTesting(VisualStudioUtil vsUtil, String account) throws Exception {
		return find(vsUtil, (workItem) -> workItem.isUIAutomatedTesting(), account);
	}
	
	@JsonIgnore	
	public VisualStudioRevisionForm findMergeToMasterStory(VisualStudioUtil vsUtil, String account) throws Exception {
		return find(vsUtil, (workItem) -> workItem.isMergeToMasterStory(), account);
	}
	
	@JsonIgnore	
	public VisualStudioRevisionForm findRebaseIntegrationToMasterStory(VisualStudioUtil vsUtil, String account) throws Exception {
		return find(vsUtil, (workItem) -> workItem.isRebaseIntegrationToMaster(), account);
	}
	
	@JsonIgnore	
	public VisualStudioRevisionForm findTestOnMasterStory(VisualStudioUtil vsUtil, String account) throws Exception {
		return find(vsUtil, (workItem) -> workItem.isTestOnMasterStory(), account);
	}
	
	@JsonIgnore	
	public VisualStudioRevisionForm findApproveForRelease(VisualStudioUtil vsUtil, String account) throws Exception {
		return find(vsUtil, (workItem) -> workItem.isApproveForRelease(), account);
	}
	
	@JsonIgnore	
	public void changeState(String value) throws Exception {
		if(fields != null){
			fields.setState(value);
		} else{
			throw new Exception("fields not assigned.");
		}
	}

	@JsonIgnore	
	public String getProject() throws Exception {
		if(fields != null){
			return fields.getProject();
		}
			throw new Exception("VisualStudioRevisionForm.getProject()");
	}
	
	@JsonIgnore	
	public String getIteration() throws Exception {
		if(fields != null){
			return fields.getIteration();
		}
			throw new Exception("VisualStudioRevisionForm.getIteration()");
	}
	
	@JsonIgnore	
	public String getCreatedBy() throws Exception {
		if(fields != null){
			return fields.getCreatedBy();
		}
			throw new Exception("VisualStudioRevisionForm.getCreatedBy()");
	}

	@JsonIgnore	
	public String getState() {
		return fields.getState();
	}

	@JsonIgnore	
	public boolean isState(VisualStudioTaskState state) {
		return fields.getState().equals(state.value);
	}
	
	@JsonIgnore	
	public boolean isStateToDo() {
		return fields.getState().equals(VisualStudioTaskState.TO_DO.value);
	}
	
	@JsonIgnore	
	public boolean isStateInProgress() {
		return fields.getState().equals(VisualStudioTaskState.IN_PROGRESS.value);
	}
	
	@JsonIgnore	
	public boolean isStateDone() {
		return fields.getState().equals(VisualStudioTaskState.DONE.value);
	}
	
	@JsonIgnore	
	public boolean isStateRemoved() {
		return fields.getState().equals(VisualStudioTaskState.REMOVED.value);
	}
	
	@JsonIgnore	
	public String getType() {
		return fields.getType();
	}
	
	@JsonIgnore	
	public Boolean noHaveParent() {
		return getParents().size() == 0;
	}
	
}
