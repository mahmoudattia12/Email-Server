package Controller.Sort;
import Controller.Profile.Components.Contacts.Contact;

import java.util.*;
public class SortContacts {
    boolean order;

    public SortContacts(boolean order) {
        this.order = order;
    }

    public ArrayList<Contact> sort(ArrayList<Contact> C)
    {
        Contact [] sorted=C.toArray(Contact[]::new);
        Arrays.sort(sorted,Comparator.comparing(cont -> cont.getName().toLowerCase()));
        ArrayList<Contact> sortedlist=new ArrayList<>(Arrays.asList(sorted));
        if(!this.order)
        {
            Collections.reverse(sortedlist);
        }
        return sortedlist;
    }

}
