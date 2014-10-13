package org.jboss.examples.deltaspike.dsPasswd;

import javax.enterprise.inject.Model;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;
import javax.inject.Inject;

/**
 * @author Tomas Remes
 */
@Model
public class BackingBean {

    private List<User> users = new ArrayList<User>();

    @Inject
    private InputStream passwdInputStream;

    public List<User> getUsers() {

        BufferedReader reader = new BufferedReader(new InputStreamReader(passwdInputStream));
        String line;
        StringTokenizer tokenizer;

        try {
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(":");
                String name = null;
                int uid = 0;
                int gid = 0;
                String homedir = null;
                String description = null;
                String shell = null;

                for (int i = 0; i < tokens.length; i++) {
                    String currentToken = tokens[i];
                    switch (i) {
                        case 0:
                            name = currentToken;
                            break;
                        case 2:
                            uid = Integer.valueOf(currentToken);
                            break;
                        case 3:
                            gid = Integer.valueOf(currentToken);
                            break;
                        case 4:
                            description = currentToken;
                            break;
                        case 5:
                            homedir = currentToken;
                            break;
                        case 6:
                            shell = currentToken;
                            break;
                    }
                }
                if (name != null) {
                    users.add(new User(name, uid, gid, homedir, description, shell));
                }
            }
            return users;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

}
