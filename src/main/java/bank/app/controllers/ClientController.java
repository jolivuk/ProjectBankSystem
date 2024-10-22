package bank.app.controllers;

import bank.app.dto.ClientDto;
import bank.app.entity.Client;
import bank.app.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping("/1")
    public List<Client> getAllClients() {
        return clientService.getAll();
    }

    @PostMapping("/2")
    public Client createClient(@RequestBody ClientDto clientDto) {
        Client cl = new Client(clientDto.firstName(),clientDto.lastName(), clientDto.email(), clientDto.username(), clientDto.password(), clientDto.telephone());
        return clientService.create(cl);
    }

}
