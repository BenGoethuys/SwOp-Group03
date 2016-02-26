package bugtrap03.usersystem;

import bugtrap03.permission.RolePerm;

/**
 * 
 * @author Group 03
 * @version 1.0
 */
public enum Role {
    
    LEAD(RolePerm.SPECIAL, RolePerm.SET_TAG_RESOLVED, RolePerm.SET_TAG_CLOSED, 
    		RolePerm.SET_TAG_NOT_A_BUG, RolePerm.SET_TAG_DUPLICATE, RolePerm.ASSIGN_DEV_PROJECT, 
    		RolePerm.ASSIGN_DEV_BUGREPORT, RolePerm.ASSIGN_PROG_ROLE, RolePerm.ASSIGN_TEST_ROLE),
    TESTER(RolePerm.ASSIGN_TEST_ROLE ,RolePerm.SET_TAG_UNDER_REVIEW),
    PROGRAMMER(RolePerm.ASSIGN_PROG_ROLE ,RolePerm.SET_TAG_UNDER_REVIEW);
	
	private Role(RolePerm neededPerm, RolePerm... perms){
		this.neededPerm = neededPerm;
		this.permissions = perms;
	}
	
	private RolePerm neededPerm;
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
	
	public RolePerm getNeededPerm(){
		return this.neededPerm;
	}
}
