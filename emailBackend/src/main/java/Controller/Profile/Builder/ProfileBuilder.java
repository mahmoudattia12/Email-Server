package Controller.Profile.Builder;

import Controller.Container;
import Controller.Profile.Profile;
import Controller.Profile.Components.Draft;
import Controller.Profile.Components.Inbox;
import Controller.Profile.Components.Sent;
import Controller.Profile.Components.Trash;
import Controller.Profile.Components.Contacts.ContactsProfile;

public class ProfileBuilder implements ProfilePlanI{
     
	Profile profile;
	
	public ProfileBuilder() {

		this.profile = new Profile();
	}
	
	@Override
	public void setUserName(String Name) {
		// TODO Auto-generated method stub
		profile.setUsername(Name);
	}

	@Override
	public void setLoginKey(String loginKey) {
		// TODO Auto-generated method stub
		profile.setLoginKey(loginKey);
	}

	@Override
	public void setContainer(Container container) {
		// TODO Auto-generated method stub
		profile.setProfileContainer(container);
	}

	@Override
	public void setSent(Sent sent) {
		// TODO Auto-generated method stub
		profile.setSentFolder(sent);
	}

	@Override
	public void setTrash(Trash trash) {
		// TODO Auto-generated method stub
		profile.setTrashFolder(trash);
	}


	@Override
	public void setInbox(Inbox inbox) {
		// TODO Auto-generated method stub
		profile.setInboxFolder(inbox);
	}

	@Override
	public void setDraft(Draft draft) {
		// TODO Auto-generated method stub
		profile.setDraftFolder(draft);
	}

	

	@Override
	public void setContacts(ContactsProfile contacts) {
		// TODO Auto-generated method stub
		profile.setContacts(contacts);
	}
	
	@Override
	public Profile getProfile() {
		// TODO Auto-generated method stub
		return profile;
	}



	@Override
	public void setPassword(String password) {
		// TODO Auto-generated method stub
		profile.setPassword(password);
	}
	
}
