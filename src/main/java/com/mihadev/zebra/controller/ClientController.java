package com.mihadev.zebra.controller;

import com.mihadev.zebra.dto.ClientAbonDto;
import com.mihadev.zebra.service.ClientService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }


    @GetMapping("/{phone}")
    public List<ClientAbonDto> getByClientPhone(@PathVariable String phone) {
        return clientService.getClientAbons(phone);
    }
}
