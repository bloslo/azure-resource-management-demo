package com.github.azureresourcemanagement.models;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class Resource {

    @NotBlank(message = "Subscription ID cannot be blank")
    private String subscriptionId;

    @NotBlank(message = "Client ID cannot be blank")
    private String clientId;

    @NotBlank(message = "Client Secret cannot be blank")
    private String clientSecret;

    @NotBlank(message = "Tenant ID cannot be blank")
    private String tenantId;

    @NotBlank(message = "Resource Name cannot be blank")
    @Size(min = 1, max = 63)
    private String resourceName;

    @NotBlank(message = "Resource Group cannot be blank")
    @Size(
            min = 3,
            max = 63,
            message = "The resource group name must be between {min} and {max} characters long"
    )
    private String resourceGroup;

    @NotBlank(message = "Root username cannot be blank")
    @Size(
            min = 3,
            max = 18,
            message = "The root username must be between {min} and {max} characters long"
    )
    private String rootUsername;

    public String getResourceGroup() {
        return resourceGroup;
    }

    public void setResourceGroup(String resourceGroup) {
        this.resourceGroup = resourceGroup;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getRootUsername() {
        return rootUsername;
    }

    public void setRootUsername(String rootUsername) {
        this.rootUsername = rootUsername;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(String subscriptionId) {
        this.subscriptionId = subscriptionId;
    }
}
