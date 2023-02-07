package Controller.Profile.Builder;

import Controller.Container;
import Controller.Profile.Profile;
//import Controller.Profile.Profile;
import Controller.Profile.Components.Draft;
import Controller.Profile.Components.Inbox;
import Controller.Profile.Components.Sent;
import Controller.Profile.Components.Trash;
import Controller.Profile.Components.Contacts.ContactsProfile;

public class ProfileDirector {
		ProfilePlanI profileBuilder;
		
		public ProfileDirector(ProfilePlanI profileBuilder ) {
			this.profileBuilder = profileBuilder;
		}
		
		
	 	public void buildTrash(Container container) throws Exception{
	        profileBuilder.setTrash(new Trash(container));
	    }

	    public void buildDraft(Container container) throws Exception{
	    	profileBuilder.setDraft(new Draft(container));
	    }

	    public void buildInbox(Container container) throws Exception{
	        profileBuilder.setInbox(new Inbox(container));
	    }

	    public void buildSent(Container container) throws Exception{
	        profileBuilder.setSent(new Sent(container));
	    }

	    public void buildContacts(Container Container)throws Exception{
	        profileBuilder.setContacts(new ContactsProfile(Container));
	    }

	    public void buildProfileData(String loginKey){
	        profileBuilder.setUserName(loginKey.substring(0, loginKey.indexOf("$")));
	        profileBuilder.setPassword(loginKey.substring(loginKey.indexOf("$") + 1));
	        profileBuilder.setLoginKey(loginKey);
	    }
	    
	    public void buildDataContainer(Container Container){
	        profileBuilder.setContainer(Container);
	    }
	    
	    public Profile getTheFinalProfile() {
	    	return profileBuilder.getProfile();
	    }
}
