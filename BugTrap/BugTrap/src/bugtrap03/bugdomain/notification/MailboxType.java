package bugtrap03.bugdomain.notification;

/**
 * Created by Kwinten on 25/04/2016.
 */
public enum MailboxType {
    CREATION_BUGREP{
        @Override
        public String getMBTypeInfo(){
            return "the creation of bugreports";
        }
    },
    VERSIONID_UPDATE{
        @Override
        public String getMBTypeInfo(){
            return "the update of the VersionID";
        }
    };
    public abstract String getMBTypeInfo();
}
