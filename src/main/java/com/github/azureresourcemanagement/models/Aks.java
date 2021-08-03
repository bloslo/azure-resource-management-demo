package com.github.azureresourcemanagement.models;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class Aks extends Resource {

    @NotBlank(message = "Agent Pool cannot be blank")
    @Size(
            min = 1,
            max = 6,
            message = "The agent pool name must be between {min} and {max} characters long"
    )
    private String agentPool;

    @NotBlank(message = "DNS prefix cannot be blank")
    @Size(
            min = 5,
            max = 12,
            message = "The DNS prefix must be between {min} and {max} characters long"
    )
    private String dnsPrefix;

    public String toString() {
        return "AKS name: " + this.getResourceName() + ", ResourceGroup: " + this.getResourceGroup()
                + ", Root user: " + this.getRootUsername() + ", Agent pool: " + this.agentPool
                + ", DNS prefix: " + this.dnsPrefix;
    }

    public void setAgentPool(String agentPool) {
        this.agentPool = agentPool;
    }

    public String getAgentPool() {
        return agentPool;
    }

    public String getDnsPrefix() {
        return dnsPrefix;
    }

    public void setDnsPrefix(String dnsPrefix) {
        this.dnsPrefix = dnsPrefix;
    }
}
