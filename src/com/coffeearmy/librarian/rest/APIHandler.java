//package com.coffeearmy.librarian.rest;
//
//import retrofit.RestAdapter;
//
//public  class APIHandler {
//	
//	private static final String API_URL = "https://www.dropbox.com/1/oauth2/";
//	private static  RestAdapter restAdapter;
// 
//	public APIHandler() {}
//	
//	private static  RestAdapter getRestAdapter(){
//		if(restAdapter==null){
//			restAdapter = new RestAdapter.Builder()
//	       .setEndpoint(API_URL)
//	        .build();
//		}
//		return restAdapter;
//	}
//	
//	public static DropboxAPI getApiInterface(){
//		
//		// Create an instance of our  API interface.
//		DropboxAPI sfAPI=null;
//		try {
//			if(restAdapter==null){
//				restAdapter=getRestAdapter();
//			}
//			sfAPI = restAdapter.create(DropboxAPI.class);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return sfAPI;
//	}
// 
//}