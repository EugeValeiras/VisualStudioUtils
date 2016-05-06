package support.utils.vs.master;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.springframework.web.util.UriUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import support.form.vs.VisualStudioIterationForm;
import support.form.vs.VisualStudioRevisionForm;
import support.utils.vs.VisualStudioField;
import support.utils.vs.VisualStudioRelation;
import support.utils.vs.VisualStudioWorkItem;
import support.utils.vs.VisualStudioWorkItemsForQuery;
import support.utils.vs.builder.VisualStudioBuildBuilder;

public class VisualStudioUtil {

	private final String username;
	private final String password;
	
	private final String encoding;
	private static final ObjectMapper mapper = new ObjectMapper();
	
	public VisualStudioUtil(String username, String password) {
		this.username = username;
		this.password = password;
		this.encoding = Base64.encodeBase64String((username+":"+password).getBytes());
	}
	
	public VisualStudioRevisionForm getWorkItem(String id, String account) throws Exception{

		HttpGet httpGet = new HttpGet(getWorkItemUrl(id, account));
		httpGet.addHeader("Authorization", "Basic " + encoding);
		
		//Run Patch
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpResponse response = httpClient.execute(httpGet);
		String json = EntityUtils.toString(response.getEntity());
		
		System.out.println(json);
		VisualStudioRevisionForm ObjectResponse = mapper.readValue(json, VisualStudioRevisionForm.class); 
		return ObjectResponse;
	}
	
	public Boolean createNewTask(VisualStudioWorkItem workItem, String project, String account) throws IOException, URISyntaxException{
		String urlString = getCreateWorkItemUrl(project, "Task", account);

		JSONArray fields = getJsonFields(workItem);
		
		return sendPatch(urlString, fields) != null;
	}
	
	public Integer createNewBug(VisualStudioWorkItem workItem, String project, String account) throws IOException, URISyntaxException{
		String urlString = getCreateWorkItemUrl(project, "Bug", account);

		JSONArray fields = getJsonFields(workItem);
		
		return sendPatch(urlString, fields);
	}

	public Boolean updateWorkItem(VisualStudioWorkItem workItem, String id, String project, String account) throws IOException, URISyntaxException{
		String urlString = getModifiedWorkItemUrl(id, account);

		JSONArray fields = getJsonFields(workItem); 
		
		return sendPatch(urlString, fields) != null;
	}

	private Integer sendPatch(String urlString, JSONArray fields) throws IOException, ClientProtocolException, URISyntaxException {
		//Create Data JSON
		StringEntity entity = new StringEntity(fields.toJSONString(), "UTF-8");
		entity.setContentType("application/json");
		
		//Create Patch
		String encode = UriUtils.encodeQuery(urlString, "UTF-8");
		HttpPatch httpPatch = new HttpPatch(encode);
		httpPatch.addHeader("Content-Type", "application/json-patch+json");
		httpPatch.addHeader("Authorization", "Basic " + encoding);
		httpPatch.setEntity(entity);
		
		//Run Patch
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpResponse response = httpClient.execute(httpPatch);
		
		System.out.println("URL: \n"+encode);
		System.out.println("Data: \n"+fields.toJSONString());
		
		System.out.println("Status: \n"+response.getStatusLine().getStatusCode());
		System.out.println("Response: \n"+response);
		
		try{
			VisualStudioRevisionForm readValue = mapper.readValue(response.getEntity().getContent(), VisualStudioRevisionForm.class);
			return readValue.getId();
		} catch(Exception ex){
			return null;
		}
	}

	public Integer countWorkInProgressCurrentIteration(String username, String project, String account) throws Exception{
		
		String postObject = "{\"query\": \"Select [System.Id]"+ 
			" From WorkItems"+ 
			" Where ([System.AssignedTo] = '"+username+"'"+ 
			" AND [System.State] = 'In Progress'"+ 
			" AND [System.WorkItemType] = 'Task'"+ 
			" AND [System.IterationPath] = @CurrentIteration"
		+")\"}";
		
		StringEntity entity = new StringEntity(postObject, "UTF-8");
		
		String urlString = getSendQueryUrl(project, account);
		String encodeUri = UriUtils.encodeQuery(urlString, "UTF-8");
		
		HttpPost httpPost = new HttpPost(encodeUri);
		httpPost.addHeader("Content-Type", "application/json");
		httpPost.addHeader("Authorization", "Basic " + encoding);
		httpPost.setEntity(entity);
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpResponse response = httpClient.execute(httpPost);
		
		if(response.getStatusLine().getStatusCode() != 200){
			System.out.println("URL: \n"+encodeUri);
			System.out.println("Data: \n"+postObject);
			
			System.out.println("Status: \n"+response.getStatusLine().getStatusCode());
			System.out.println("Response: \n"+response);
		}
		
		try{
			VisualStudioWorkItemsForQuery responseObject = mapper.readValue(response.getEntity().getContent(), VisualStudioWorkItemsForQuery.class);
			System.out.println(username +" - "+ responseObject.countWorkItems());
			return responseObject.countWorkItems();
		} catch(Exception ex){
			return null;
		}
	}
	
	public void queueABuild(VisualStudioBuild build, String project, String account) throws Exception{
		StringEntity entity = new StringEntity(mapper.writeValueAsString(build), "UTF-8");
		
		String urlString = getRunBuildUrl(project, account);
		String encodeUri = UriUtils.encodeQuery(urlString, "UTF-8");
		
		HttpPost httpPost = new HttpPost(encodeUri);
		httpPost.addHeader("Content-Type", "application/json");
		httpPost.addHeader("Authorization", "Basic " + encoding);
		httpPost.setEntity(entity);
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpResponse response = httpClient.execute(httpPost);
		
		if(response.getStatusLine().getStatusCode() != 200){
			System.out.println("URL: \n"+encodeUri);
			System.out.println("Data: \n"+mapper.writeValueAsString(build));
			
			System.out.println("Status: \n"+response.getStatusLine().getStatusCode());
			System.out.println("Response: \n"+response);
		}
	}
	
	public String getCurrentIteration(String project, String account) throws ParseException, IOException {
		String encodeUri = UriUtils.encodeQuery(getCurrentIterationUrl(project, account), "UTF-8");
		
		HttpGet httpGet = new HttpGet(encodeUri);
		httpGet.addHeader("Authorization", "Basic " + encoding);
		
		//Run Patch
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpResponse response = httpClient.execute(httpGet);
		String json = EntityUtils.toString(response.getEntity());
		
		System.out.println(json);
		VisualStudioIterationForm iterationForm = mapper.readValue(json, VisualStudioIterationForm.class); 
		
		return iterationForm.getPath();
	}
	
	private JSONArray getJsonFields(VisualStudioWorkItem workItem) {
		JSONArray fields = new JSONArray();
		for(VisualStudioField field : workItem.getFields()){
			fields.add(field.asJson());
		}
		for(VisualStudioRelation relation : workItem.getRelations()){
			fields.add(relation.asJson());
		}
		return fields;
	}
	
	private String getRunBuildUrl(String project, String account){
		return "https://"+account+".visualstudio.com/DefaultCollection/"+project+"/_apis/build/builds?api-version=2.0";
	}
	
	private String getCurrentIterationUrl(String project, String account){
		return "https://"+account+".visualstudio.com/DefaultCollection/"+project+"/_apis/work/teamsettings/iterations?$timeframe=current&api-version=v2.0-preview";
	}
	
	private String getModifiedWorkItemUrl(String id, String account){
		return "https://"+account+".visualstudio.com/DefaultCollection/_apis/wit/workitems/"+id+"?api-version=1.0";
	}
	
	private String getCreateWorkItemUrl(String project, String type, String account){
		return "https://"+account+".visualstudio.com/DefaultCollection/"+(project)+"/_apis/wit/workitems/$"+type+"?api-version=1.0";
	}
	
	private String getWorkItemUrl(String id, String account){
		return "https://"+account+".visualstudio.com/DefaultCollection/_apis/wit/workitems/"+id+"?api-version=1.0&$expand=relations";
	}
	
	public String getVisualWorkItemUrl(String id, String project, String account) {
		return "https://"+(account)+".visualstudio.com/DefaultCollection/"+(project)+"/_workitems?_a=edit&id="+id;
	}
	
	public String getVisualWorkItemUrlEncoded(String id, String project, String account) throws UnsupportedEncodingException {
		return UriUtils.encodeQuery("https://"+(account)+".visualstudio.com/DefaultCollection/"+(project)+"/_workitems?_a=edit&id="+id, "UTF-8");
	}
	
	private String getSendQueryUrl(String project, String account){
		return "https://"+(account)+".visualstudio.com/DefaultCollection/"+(project)+"/_apis/wit/wiql?api-version=1.0";
	}
}
