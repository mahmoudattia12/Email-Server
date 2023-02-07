package Controller.Profile.Components;

import Controller.Container;

public class UserFolder extends EmailFolder{
    private String name;
    public UserFolder(Container container, String name) throws Exception {
        super(container);
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
