package Controller.Sort;

import Controller.Profile.Components.Email.Email;

import java.util.ArrayList;

public interface ISort {
    ArrayList<Email> SortMails(ArrayList<Email> E,String SortPolicy) throws Exception;
}
