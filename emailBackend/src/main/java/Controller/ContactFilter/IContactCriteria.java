package Controller.ContactFilter;

import Controller.Profile.Components.Contacts.Contact;

import java.util.ArrayList;

public interface IContactCriteria {
    ArrayList<Contact> FilteredContact(ArrayList<Contact> contact);
}
