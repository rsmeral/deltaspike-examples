package org.jboss.examples.deltaspike.dsPasswd;

import javax.enterprise.inject.Model;
import javax.inject.Inject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;
import org.apache.deltaspike.core.api.resourceloader.InjectableResource;

/**
 * @author Tomas Remes
 */
@Model
public class BackingBean {

    private List<User> users = new ArrayList<User>();

    @Inject
    @InjectableResource(location = "passwd")
    InputStream file;

    public List<User> getUsers() {

        BufferedReader reader = new BufferedReader(new InputStreamReader(file));
        String line;
        StringTokenizer tokenizer;

        try {
            while ((line = reader.readLine()) != null) {
                tokenizer = new StringTokenizer(line, ":");
                int i = 0;
                String name = null;
                int uid = 0;
                int gid = 0;
                String homedir = null;
                String description = null;

                while (tokenizer.hasMoreElements()) {
                    String currentToken = tokenizer.nextToken();
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
                    }
                    i++;
                }
                if (!name.equals(null)) {
                    users.add(new User(name, uid, gid, homedir, description));
                }
            }
            return users;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

}
