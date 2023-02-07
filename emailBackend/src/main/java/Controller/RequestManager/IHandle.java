package Controller.RequestManager;

import Controller.Profile.Components.Email.Email;

public interface IHandle {
    String request = null;
    IHandle successor = null;

    void handle(String request, Email email, String folderName) throws Exception;
}
