package bugtrap03.usersystem;

import bugtrap03.permission.RolePerm;

/**
 * 
 * @author Group 03
 * @version 1.0
 */
public enum Role {
    
    LEAD(RolePerm.SET_TAG_RESOLVED, RolePerm.SET_TAG_CLOSED, 
    		RolePerm.SET_TAG_NOT_A_BUG, RolePerm.SET_TAG_DUPLICATE, RolePerm.ASSIGN_DEV_PROJECT, RolePerm.ASSIGN_DEV_BUGREPORT),
    TESTER(RolePerm.SET_TAG_UNDER_REVIEW),
    PROGRAMMER(RolePerm.SET_TAG_UNDER_REVIEW);
	
	private Role(RolePerm... perms){
		this.permissions = perms;
	}
	
	private RolePerm[] permissions;
	
	public boolean hasPermission(RolePerm perm){
		if (perm == RolePerm.SPECIAL){
			return false;
		}
		for (RolePerm permission: this.permissions){
			if (permission == perm){
				return true;
			}
		}
		return false;
	}
}
