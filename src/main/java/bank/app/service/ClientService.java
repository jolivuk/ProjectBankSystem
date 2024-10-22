package bank.app.service;

import bank.app.entity.Client;

import java.util.List;

public interface ClientService {

    List<Client> getAll();
    Client create(Client client);
}
