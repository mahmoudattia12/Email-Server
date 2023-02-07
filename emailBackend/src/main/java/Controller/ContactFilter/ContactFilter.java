package Controller.ContactFilter;

import Controller.Profile.Components.Contacts.Contact;

import java.util.ArrayList;

public class ContactFilter implements IContactCriteria{

    String filteredkey;
    String content;//the content we will check the contact contains or not.

    public String getFilteredkey() {
        return this.filteredkey;
    }

    public void setFilteredkey(String filteredkey) {
        this.filteredkey = filteredkey;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ContactFilter() {}
    public ContactFilter(String filteredkey, String content) {
        this.filteredkey = filteredkey;
        this.content = content;
    }

    @Override
    public ArrayList<Contact> FilteredContact(ArrayList<Contact> contact) {
        ArrayList<Contact> filterres=new ArrayList<>();//Array of contacts
        switch (this.filteredkey.toLowerCase())
        {
            case "username":
            {
                for(Contact E:contact)
                {
                    if(E.getName().equalsIgnoreCase(this.content))
                    {
                        filterres.add(E);
                    }
                }
                break;
            }
        }
        return filterres;
    }
}
