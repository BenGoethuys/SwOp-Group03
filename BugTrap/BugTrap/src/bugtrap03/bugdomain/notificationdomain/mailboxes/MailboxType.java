package bugtrap03.bugdomain.notificationdomain.mailboxes;

import bugtrap03.bugdomain.DomainAPI;

/**
 * @author Group 03
 */
@DomainAPI
public enum MailboxType {
    CREATION_BUGREP{
        /**
         * A getter for the type info of this enum type.
         * @return A string containing type information.
         */
        @Override
        @DomainAPI
        public String getMBTypeInfo(){
            return "the creation of bugreports";
        }
    },
    VERSIONID_UPDATE{
        /**
         * A getter for the type info of this enum type.
         * @return A string containing type information.
         */
        @Override
        @DomainAPI
        public String getMBTypeInfo(){
            return "the update of versionIDs";
        }
    };

    /**
     * An abstract method to return the type info of this enum.
     * @return
     */
    @DomainAPI
    public abstract String getMBTypeInfo();
}
