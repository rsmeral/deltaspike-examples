package org.jboss.examples.deltaspike.dsPasswd;

/**
 * @author Tomas Remes
 */
public class User {

    private String name;
    private int uid;
    private int gid;
    private String homedir;
    private String description;

    public User(String name, int uid, int gid, String homedir, String description){
        this.name = name;
        this.uid = uid;
        this.gid = gid;
        this.homedir = homedir;
        this.description = description;

    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public int getUid() {
        return uid;
    }

    public int getGid() {
        return gid;
    }

    public String getHomedir() {
        return homedir;
    }

}
