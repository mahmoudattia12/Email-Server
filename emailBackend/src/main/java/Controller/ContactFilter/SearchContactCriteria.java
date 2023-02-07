package Controller.ContactFilter;

import Controller.Profile.Components.Contacts.Contact;

import java.util.ArrayList;

public class SearchContactCriteria implements IContactCriteria{
    String content;//searched key

    public SearchContactCriteria(String content) {
        this.content = content;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public SearchContactCriteria () {}
    @Override
    public ArrayList<Contact> FilteredContact(ArrayList<Contact> contact) {
        ArrayList<Contact> searchres=new ArrayList<>();
        String searchedcontact = "";
        for (Contact c: contact)
        {
            searchedcontact=c.getName();
            if(searchedcontact.contains(this.content.toLowerCase()))
            {
                searchres.add(c);
            }
        }
        return searchres;
    }
}
