package bugtrap03.permission;

public enum RolePerm {
	ASSIGN_DEV_PROJECT, 
	ASSIGN_DEV_BUGREPORT, 
	SET_TAG_CLOSED, 
	SET_TAG_DUPLICATE, 
	SET_TAG_UNDER_REVIEW, 
	SET_TAG_RESOLVED, 
	SET_TAG_NOT_A_BUG,
	SPECIAL; // nobody has permission
}
