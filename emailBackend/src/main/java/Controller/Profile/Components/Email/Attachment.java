package Controller.Profile.Components.Email;

public class Attachment {
    String name;
    String type;
    byte[] encodedFile;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getEncodedFile() {
        return this.encodedFile;
    }

    public void setEncodedFile(byte[] encodedFile) {
        this.encodedFile = encodedFile;
    }
}
