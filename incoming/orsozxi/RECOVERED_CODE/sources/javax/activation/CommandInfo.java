package javax.activation;

import java.beans.Beans;
import java.io.Externalizable;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

/* loaded from: classes3.dex */
public class CommandInfo {
    private String className;
    private String verb;

    public CommandInfo(String str, String str2) {
        this.verb = str;
        this.className = str2;
    }

    public String getCommandName() {
        return this.verb;
    }

    public String getCommandClass() {
        return this.className;
    }

    public Object getCommandObject(DataHandler dataHandler, ClassLoader classLoader) throws IOException, ClassNotFoundException {
        InputStream inputStream;
        Object objInstantiate = Beans.instantiate(classLoader, this.className);
        if (objInstantiate != null) {
            if (objInstantiate instanceof CommandObject) {
                ((CommandObject) objInstantiate).setCommandContext(this.verb, dataHandler);
            } else if ((objInstantiate instanceof Externalizable) && dataHandler != null && (inputStream = dataHandler.getInputStream()) != null) {
                ((Externalizable) objInstantiate).readExternal(new ObjectInputStream(inputStream));
            }
        }
        return objInstantiate;
    }
}
