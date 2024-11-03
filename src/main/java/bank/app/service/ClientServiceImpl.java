package bank.app.service;

import bank.app.model.entity.Client;
import bank.app.repository.ClientJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientJpa client;

    @Override
    public List<Client> getAll() {
        return client.findAll();
    }

    @Override
    public Client create(Client newClient) {

        return client.save(newClient);
    }
}
