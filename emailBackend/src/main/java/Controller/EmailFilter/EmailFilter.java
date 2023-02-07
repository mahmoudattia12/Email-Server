package Controller.EmailFilter;

import Controller.EmailFilter.IEmailCriteria;
import Controller.Profile.Components.Email.Email;

import java.util.ArrayList;

public class EmailFilter implements IEmailCriteria {

    //private String senderName, owner, subject, body, timeSent, priority, emailID, emailType;
    String filterkey;//filter by what?
    String contain;//the content that we will search or filter in all emails if existed.
    public EmailFilter(String filterkey, String contain)
    {
        this.filterkey=filterkey;
        this.contain=contain;
    }
    public String getFilterkey() {
        return this.filterkey;
    }

    public String getContain() {
        return this.contain;
    }

    public void setFilterkey(String filterkey) {
        this.filterkey = filterkey;
    }

    public void setContain(String contain) {
        this.contain = contain;
    }

    @Override
    public ArrayList<Email> meetCriteria(ArrayList<Email> emailset) {
        ArrayList<Email> filterres=new ArrayList<>();
        switch (this.filterkey)
        {
            case "Subject":
            {
                for(Email E:emailset)
                {
                    if(E.getSubject().equalsIgnoreCase(this.contain))
                    {
                        filterres.add(E);
                    }
                }
                break;
            }
            case "Sender":
            {
                for(Email E:emailset)
                {
                    if(E.getSenderName().equalsIgnoreCase(this.contain))
                    {
                        filterres.add(E);
                    }
                }
                break;
            }
            case "Time":
            {
                for(Email E:emailset)
                {
                    if(E.getTimeSent().equalsIgnoreCase(this.contain))
                    {
                        filterres.add(E);
                    }
                }
                break;
            }
        }
        //private String senderName, owner, subject, body, timeSent, priority, emailID, emailType;
        return filterres;
    }


}
