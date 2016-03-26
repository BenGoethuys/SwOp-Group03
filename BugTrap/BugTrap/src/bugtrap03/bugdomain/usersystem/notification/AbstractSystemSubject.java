package bugtrap03.bugdomain.usersystem.notification;

import purecollections.PList;

/**
 * Created by Kwinten on 23/03/2016.
 */
public abstract class AbstractSystemSubject extends Subject {

    public AbstractSystemSubject(){
        super();
        this.CreationSubs = PList.<CreationMailBox>empty();
    }

    private PList<CreationMailBox> CreationSubs;
}
