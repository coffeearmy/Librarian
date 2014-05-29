package com.coffeearmy.librarian.events;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;

public class OnSuccessAuthorization extends AbstractEvent{
	private DropboxAPI<AndroidAuthSession> _item;
	private Type _type; 
		
	public enum Type
    {       
     SUCCESS,FAILURE   
    }
 
	public OnSuccessAuthorization(Type type,DropboxAPI<AndroidAuthSession> dropboxAPI) {
		super(type);
		this._type=type;
		this._item = dropboxAPI;
	}	
	
	
	public DropboxAPI<AndroidAuthSession> getDropboxAPI() {
		return _item;
	}
	
 
}
