
package com.line.practice.RestAPI;

public class UserData{
	
	private String userId;
	private String userText;
	
	public UserData(){
	}
	public UserData(String id) {
		this.userId = id;
	}
	
	public UserData(String id, String text){
		this.userId = id;
		this.userText = text;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getUserText() {
		return userText;
	}
	//item.getUserId().equals(id)
	public void setUserText(String userText) {
		this.userText = userText;
	}
	

	/*public String toString() {
		ArrayList<Object> jsonObjects = new ArrayList<>();
		Gson gson = new Gson();

	    for (UserData item : items) {
	        jsonObjects.add(gson.toJson(item));
	    }
	
	    ObjectMapper objectMapper = new ObjectMapper();
	    String jsonString;
		try {
			jsonString = objectMapper.writeValueAsString(jsonObjects);			
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			jsonString = "Error!";
			e.printStackTrace();
		}
		return jsonString;
	}*/
}
