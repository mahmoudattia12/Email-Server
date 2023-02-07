package Controller;

import Controller.Profile.Profile;

import java.io.File;
import java.io.FileFilter;
import java.util.List;
import java.util.ArrayList;

public class Database {
    private final static String databasePath = "src/main/java/Model/";
    private static Database databaseInstance;
    private static int databaseSize = 0;
    private static List<Profile> dataBaseList;


    private Database() throws Exception{
        dataBaseList = new ArrayList<>();
        setDatabase();
    }

    public static Database getInstance() throws Exception{
        if (databaseInstance == null) {
            databaseInstance = new Database();
            System.out.println("New Database");
        }
        return databaseInstance;
    }

    private static void setDatabase() throws Exception{
        Creator creator = Creator.getInstance();
        File file = new File(databasePath);
        File[] files = file.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.isDirectory();
            }
        });
        if(files == null || file == null){
            throw new Exception("oops Directory not found");
        }
        databaseSize = files.length;
        for(int i = 0; i < databaseSize; i++){
            dataBaseList.add(creator.setProfile(databasePath, files[i].getName()));
        }
        System.out.println(databaseSize);
    }

    public int getSize(){
        return databaseSize;
    }

    public String getDatabasePath(){
        return this.databasePath;
    }

    public Profile getProfileByName(String loginKey, String username){

        if(username == ""){
            username = loginKey.substring(0, loginKey.indexOf("$"));
        }
        Profile profile = null;
        for(int i = 0; i < databaseSize; i++){
            if(username.equals(dataBaseList.get(i).getUsername())){
                profile = dataBaseList.get(i);
            }
        }
        return profile;
    }

    public Profile getProfileByKey(String loginKey)throws Exception{
        Profile profile = null;
        for(int i = 0; i < databaseSize; i++){
            if(loginKey.equals(dataBaseList.get(i).getLoginKey())){
                profile = dataBaseList.get(i);
            }
        }
        if(profile == null){
            throw new Exception("oops Profile not found");
        }
        return profile;
    }



    public void addProfile(String loginKey) throws Exception{
        Creator creator = Creator.getInstance();
        if(this.databaseSize>0){
            if(getProfileByName(loginKey, "") != null){
                throw new Exception("oops Profile already exists");
            }else{
                Profile profile = creator.createProfile(this.databasePath, loginKey);
                this.dataBaseList.add(profile);
                this.databaseSize++;
            }
        }else{
            Profile profile = creator.createProfile(this.databasePath, loginKey);
            this.dataBaseList.add(profile);
            this.databaseSize++;
        }
    }

    public void deleteProfile(String loginKey) throws Exception{
        Delete delete = Delete.getInstance();
        Profile profile = getProfileByName(loginKey, "");
        if(this.databaseSize > 0){
            if(profile != null){
                delete.deleteProfile(profile);
                this.dataBaseList.remove(profile);
                this.databaseSize--;
            }
            else{
                throw new Exception("Profile Not Found To Delete");
            }
        }
        else{
            throw new Exception("oops Database is Empty");
        }
    }

    public void createDataFile(String loginKey) throws Exception{
        Creator creator = Creator.getInstance();
        Profile tmpProfile = getProfileByKey(loginKey);
        if(tmpProfile == null){
            throw new Exception("Profile Not Exist");
        }
        creator.createDataFile(tmpProfile);
    }


}
