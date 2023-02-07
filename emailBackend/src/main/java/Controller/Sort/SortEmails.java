package Controller.Sort;

import Controller.Profile.Components.Email.Email;

import java.util.*;



public class SortEmails implements ISort{

    boolean order;//Ascending:True or Descending:False

    public SortEmails(boolean order) {
        this.order = order;
    }

    public boolean whichOrder() {
        return order;
    }

    public void setOrder(boolean order) {
        this.order = order;
    }
    private static ArrayList<String> priority=new ArrayList<>(
            Arrays.asList("So Important","Important","Default","Low")
    );
    @Override
    public ArrayList<Email> SortMails(ArrayList<Email> E, String SortPolicy) throws Exception {
        ArrayList<Email> Res;
        switch (SortPolicy)
        {
            case "Subject":
            {
                Res=this.sortBysubject(E);
                break;
            }
            case "Date":
            {
                Res=this.sortBydate(E);
                break;
            }
            case "Attachment Size":
            {
                Res=this.sortByAttachSize(E);
                break;
            }
            case "Sender":
            {
                Res=this.sortBySender(E);
                break;
            }
            case "Receiver":
            {
                Res=this.sortByReciever(E);
                break;
            }
            case "Body":
            {
                Res=this.sortByBody(E);
                break;
            }
            case "Priority":
            {
                Res=this.sortByPriority(E);
                break;
            }
            default: throw new Exception("Sorry! There is no sort criteria like "+SortPolicy);
        }
        return Res;
    }

    private ArrayList<Email> sortBydate(ArrayList<Email> E){
        Email[] sorted=E.toArray(Email[]::new);
        Arrays.sort(sorted, (E1, E2) -> {
            if(Long.parseLong(E1.getTimeSent()) >= Long.parseLong(E2.getTimeSent())){
                return 1;
            }
            else{
                return -1;
            }
        });
        ArrayList<Email> sortedlist=new ArrayList<>(Arrays.asList(sorted));
        if(!this.order)//descending
        {
            Collections.reverse(sortedlist);
        }
        return sortedlist;
    }
    private ArrayList<Email> sortBysubject(ArrayList<Email> E){
        Email[] sorted=E.toArray(Email[]::new);
        Arrays.sort(sorted, Comparator.comparing(email -> email.getSubject().toLowerCase()));
        ArrayList<Email> sortedlist=new ArrayList<>(Arrays.asList(sorted));

        if(!this.order)
        {
            Collections.reverse(sortedlist);
        }
        return sortedlist;

    }
    private ArrayList<Email> sortByAttachSize(ArrayList<Email> E){
        Email[] sorted=E.toArray(Email[]::new);
        Arrays.sort(sorted, Comparator.comparing(email -> email.getAttachments().size()));
        ArrayList<Email> sortedlist=new ArrayList<>(Arrays.asList(sorted));
        if(!this.order)
        {
            Collections.reverse(sortedlist);
        }
        return sortedlist;
    }
    private ArrayList<Email> sortBySender(ArrayList<Email> E){
        Email[] sorted=E.toArray(Email[]::new);
        Arrays.sort(sorted,Comparator.comparing(email -> email.getSenderName().toLowerCase()));
        ArrayList<Email> sortedlist=new ArrayList<>(Arrays.asList(sorted));
        if(!this.order)
        {
            Collections.reverse(sortedlist);
        }
        return sortedlist;
    }
    private ArrayList<Email> sortByReciever(ArrayList<Email> E){//you may need to change getRecieverNames().get(0)
        Email[] sorted=E.toArray(Email[]::new);
        Arrays.sort(sorted, Comparator.comparing(email -> email.getReceiverNames().get(0).toLowerCase()));
        ArrayList<Email> sortedlist=new ArrayList<>(Arrays.asList(sorted));
        if(!this.order)
        {
            Collections.reverse(sortedlist);
        }
        return sortedlist;
    }
    private ArrayList<Email> sortByBody(ArrayList<Email> E){
        Email[] sorted=E.toArray(Email[]::new);
        Arrays.sort(sorted,Comparator.comparing(email -> email.getBody().toLowerCase()));
        ArrayList<Email> sortedlist=new ArrayList<>(Arrays.asList(sorted));
        if(!this.order)
        {
            Collections.reverse(sortedlist);
        }
        return sortedlist;
    }
    private ArrayList<Email> sortByPriority(ArrayList<Email> E){
        Email[] sorted=E.toArray(Email[]::new);
        Arrays.sort(sorted, (first, second) -> {
            if(priority.indexOf(first.getPriority()) < priority.indexOf(second.getPriority())){
                return 1;
            }
            else{
                return -1;
            }
        });
        ArrayList<Email> sortedlist=new ArrayList<>(Arrays.asList(sorted));
        if(!this.order)
        {
            Collections.reverse(sortedlist);
        }
        return sortedlist;
    }
}
