package org.jboss.examples.deltaspike.dsPasswd;

/**
 * @author Tomas Remes
 */
public class User {

    private final String name;
    private final int uid;
    private final int gid;
    private final String homedir;
    private final String description;
    private final String shell;

    public User(String name, int uid, int gid, String homedir, String description, String shell){
        this.name = name;
        this.uid = uid;
        this.gid = gid;
        this.homedir = homedir;
        this.description = description;
        this.shell = shell;
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

    public String getShell() {
        return shell;
    }

}
