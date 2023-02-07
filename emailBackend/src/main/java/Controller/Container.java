package Controller;

import java.io.File;

public class Container {
    private String containerName;
    private String containerPath;
    private File file;

    public Container(String containerPath, String containerName, File file) {
        this.containerPath = containerPath;
        this.containerName = containerName;
        this.file = file;
    }


    public String getContainerName() {
        return containerName;
    }

    public void setContainerName(String containerName) {
        this.containerName = containerName;
    }

    public String getContainerPath() {
        return containerPath;
    }

    public void setContainerPath(String containerPath) {
        this.containerPath = containerPath;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
