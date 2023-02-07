package Controller.Profile.Builder;

import Controller.Container;
import Controller.Profile.Profile;
import Controller.Profile.Components.Draft;
import Controller.Profile.Components.Inbox;
import Controller.Profile.Components.Sent;
import Controller.Profile.Components.Trash;
import Controller.Profile.Components.Contacts.ContactsProfile;

public interface ProfilePlanI {

	public void setUserName(String Name);
	public void setPassword(String password);
	public void setLoginKey(String loginKey);
	public void setContainer(Container container);
	public void setSent(Sent sent);
	public void setTrash(Trash trash);
	public void setContacts(ContactsProfile contacts);
	public void setInbox(Inbox inbox);
	public void setDraft(Draft draft);
	
	public Profile getProfile();
}
