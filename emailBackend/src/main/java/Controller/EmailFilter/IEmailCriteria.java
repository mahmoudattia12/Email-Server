package Controller.EmailFilter;

import Controller.Profile.Components.Email.Email;

import java.util.ArrayList;

public interface IEmailCriteria {
    ArrayList<Email> meetCriteria(ArrayList<Email> emailset);
}
