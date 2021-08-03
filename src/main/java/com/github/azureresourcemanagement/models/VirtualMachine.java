package com.github.azureresourcemanagement.models;

public class VirtualMachine extends Resource {

    public String toString() {
        return "VM name: " + this.getResourceName() + ", ResourceGroup: " + this.getResourceGroup()
                + ", Root user: " + this.getRootUsername();
    }
}
