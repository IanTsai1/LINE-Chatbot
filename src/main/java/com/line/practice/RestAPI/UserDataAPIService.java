package com.line.practice.RestAPI;
 
import java.util.HashSet;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping; 

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//import com.line.practice.RestAPI.UserData;

@RestController
@RequestMapping("/userdata")
public class UserDataAPIService {
	
	UserData data;
	HashSet<UserData> items = new HashSet<>();
	
	
	@GetMapping("/{userId}") 
	public UserData getData(@PathVariable String userId) {
		for (UserData item : items) {
			if (item.getUserId().equals(userId)) {
				return item;
			}
    	}
		return null;
	}
	
	@DeleteMapping("/{userId}") 
	public String deleteData(String userID) {
		//this.data = null;
		deleteItem(items,userID);
		return "Cloud Vendor Deleted Succesfully";
	}
	
	
	@PostMapping("/") //Create info 
	public UserData createData(@RequestBody UserData data) {
		//this.data = data;
		updateOrAddItem(items, data.getUserId(),data.getUserText());
		return getData(data.getUserId());
	}
	
	
	@PutMapping("/") //Update info
	public String updateData(@RequestBody UserData data) {
		//this.data = data;
		updateOrAddItem(items, data.getUserId(),data.getUserText());
		return "Cloud Vendor Updated Succesfully";
	}
	
	
	@PostMapping("/random-content")
	public String fromPython(@RequestBody String text) {
		System.out.println("I'm the sent text from python: " + text + "\n");
		return "";
	}
	
	/*
	@PostMapping("/id") //not sending in json format
	public String sendId(@RequestBody String id) {
		System.out.println("I'M IN SEND ID");
		return "Sent";
	}*/
	
	public void updateOrAddItem(HashSet<UserData> items, String id, String text) {
	    boolean isExistingItem = false;
	    for (UserData item : items) {
	        if (item.getUserId().equals(id)) {
	            item.setUserText(text);
	            isExistingItem = true;
	            break;
	        }
	    }

	    if (!isExistingItem) {
	        UserData newItem = new UserData();
	        newItem.setUserId(id);
	        newItem.setUserText(text);
	        items.add(newItem);
	    }
	}
	
	public void deleteItem(HashSet<UserData> items, String userId) { 
		UserData itemToRemove = null;
	    for (UserData item : items) {
	        if (item.getUserId().equals(userId)) {
	            itemToRemove = item;
	            break;
	        }
	    }
	  
	    if (itemToRemove != null) {
	        items.remove(itemToRemove);
	    }
	}

}
