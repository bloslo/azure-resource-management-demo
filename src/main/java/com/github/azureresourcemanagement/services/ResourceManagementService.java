package com.github.azureresourcemanagement.services;

import com.azure.core.credential.TokenCredential;
import com.azure.core.management.AzureEnvironment;
import com.azure.core.management.Region;
import com.azure.core.management.profile.AzureProfile;
import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.azure.resourcemanager.compute.ComputeManager;
import com.azure.resourcemanager.compute.models.KnownLinuxVirtualMachineImage;
import com.azure.resourcemanager.compute.models.VirtualMachine;
import com.azure.resourcemanager.compute.models.VirtualMachineSizeTypes;
import com.azure.resourcemanager.containerservice.ContainerServiceManager;
import com.azure.resourcemanager.containerservice.models.AgentPoolMode;
import com.azure.resourcemanager.containerservice.models.AgentPoolType;
import com.azure.resourcemanager.containerservice.models.ContainerServiceVMSizeTypes;
import com.azure.resourcemanager.containerservice.models.KubernetesCluster;
import com.github.azureresourcemanagement.models.Aks;
import com.github.azureresourcemanagement.utils.SSHShell;
import com.jcraft.jsch.JSchException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public class ResourceManagementService {

    private static final Logger log = LoggerFactory.getLogger(ResourceManagementService.class);

    public void createVirtualMachine(final com.github.azureresourcemanagement.models.VirtualMachine virtualMachine) {
        final TokenCredential credential = buildCredentials(
                virtualMachine.getClientId(),
                virtualMachine.getClientSecret(),
                virtualMachine.getTenantId()
        );
        final AzureProfile profile = new AzureProfile(
                virtualMachine.getTenantId(),
                virtualMachine.getSubscriptionId(),
                AzureEnvironment.AZURE
        );
        final ComputeManager manager = ComputeManager.authenticate(credential, profile);
        final SSHShell.SshPublicPrivateKey sshKeys = this.generateSshKeys();

        final VirtualMachine linuxVM = manager.virtualMachines().define(virtualMachine.getResourceName())
                .withRegion(Region.EUROPE_WEST)
                .withNewResourceGroup(virtualMachine.getResourceGroup())
                .withNewPrimaryNetwork("10.0.0.0/28")
                .withPrimaryPrivateIPAddressDynamic()
                .withoutPrimaryPublicIPAddress()
                .withPopularLinuxImage(KnownLinuxVirtualMachineImage.UBUNTU_SERVER_18_04_LTS)
                .withRootUsername(virtualMachine.getRootUsername())
                .withSsh(sshKeys.getSshPublicKey())
                .withSize(VirtualMachineSizeTypes.STANDARD_A1_V2)
                .withSystemAssignedManagedServiceIdentity()
                .create();
    }

    public void createAks(final Aks aks) {
        final TokenCredential credential = buildCredentials(
                aks.getClientId(),
                aks.getClientSecret(),
                aks.getTenantId()
        );
        final AzureProfile profile = new AzureProfile(
                aks.getTenantId(),
                aks.getSubscriptionId(),
                AzureEnvironment.AZURE
        );
        final ContainerServiceManager manager = ContainerServiceManager.authenticate(credential, profile);
        final SSHShell.SshPublicPrivateKey sshKeys = this.generateSshKeys();

        final KubernetesCluster kubernetesCluster = manager.kubernetesClusters().define(aks.getResourceName())
                .withRegion(Region.EUROPE_WEST)
                .withNewResourceGroup(aks.getResourceGroup())
                .withDefaultVersion()
                .withRootUsername(aks.getRootUsername())
                .withSshKey(sshKeys.getSshPublicKey())
                .withServicePrincipalClientId(aks.getClientId())
                .withServicePrincipalSecret(aks.getClientSecret())
                .defineAgentPool(aks.getAgentPool())
                .withVirtualMachineSize(ContainerServiceVMSizeTypes.STANDARD_D2_V2)
                .withAgentPoolVirtualMachineCount(1)
                .withAgentPoolMode(AgentPoolMode.SYSTEM)
                .attach()
                .withDnsPrefix(aks.getDnsPrefix())
                .withSystemAssignedManagedServiceIdentity()
                .create();
    }

    private ClientSecretCredential buildCredentials(final String clientId, final String clientSecret,
                                                    final String tenantId) {
        return new ClientSecretCredentialBuilder()
                .clientId(clientId)
                .clientSecret(clientSecret)
                .tenantId(tenantId)
                .build();
    }

    private SSHShell.SshPublicPrivateKey generateSshKeys() {
        try {
            return SSHShell.generateSSHKeys("", "");
        } catch (UnsupportedEncodingException | JSchException e) {
            log.error(e.getMessage());
        }
        return null;
    }
}
