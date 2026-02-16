package com.clinic.models;

public class Role {
	private int roleId;
    private String roleName;

    public Role(int roleId, String roleName) {
        this.roleId = roleId;
        this.roleName = roleName;
    }
    
    // Getters
    
    public int getRoleId() { return roleId; }
    public String getRoleName() { return roleName; }
}
