package com.github.azureresourcemanagement.controllers;

import com.github.azureresourcemanagement.models.Aks;
import com.github.azureresourcemanagement.models.VirtualMachine;
import com.github.azureresourcemanagement.services.ResourceManagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class ResourceManagementController {

    private static final Logger log = LoggerFactory.getLogger(ResourceManagementController.class);

    private final ResourceManagementService resourceManagementService;

    @Autowired
    ResourceManagementController(final ResourceManagementService resourceManagementService) {
        this.resourceManagementService = resourceManagementService;
    }

    @GetMapping("/vm")
    public String showCreateVmForm(final VirtualMachine virtualMachine) {
        return "create-azure-vm";
    }

    @PostMapping("/vm")
    public String createVirtualMachine(@Valid final VirtualMachine virtualMachine, final BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "create-azure-vm";
        }
        this.resourceManagementService.createVirtualMachine(virtualMachine);

        return "redirect:/success";
    }

    @GetMapping("/aks")
    public String showCreateAksForm(final Aks aks) {
        return "create-azure-aks";
    }

    @PostMapping("/aks")
    public String createAks(@Valid final Aks aks, final BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "create-azure-aks";
        }
        this.resourceManagementService.createAks(aks);

        return "redirect:/success";
    }

    @GetMapping("/")
    public String showIndexPage() {
        return "index";
    }

    @GetMapping("/success")
    public String showSuccessPage() {
        return "success";
    }
}
