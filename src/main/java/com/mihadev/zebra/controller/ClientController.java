package com.mihadev.zebra.controller;

import com.mihadev.zebra.dto.ClientResponse;
import com.mihadev.zebra.service.ClientService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }


    @GetMapping("/{phone}")
    public ClientResponse getByClientPhone(@PathVariable String phone) {
        return clientService.getClientAbons(phone);
    }
}
