package com.example.fenikaCRM10.services;

import com.example.fenikaCRM10.models.Clients;
import com.example.fenikaCRM10.models.User;
import com.example.fenikaCRM10.repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;

    public List<Clients> searchClients(String query) {
        return clientRepository.searchClients1(query);
    }
    public List<Clients> searchClientsForUser(User user, String query) {
        return clientRepository.searchByNameOrPhoneForUser(user, query);
    }

    public Clients findOrCreateClient(String name, String phone, String email) {
        return clientRepository.findByClientNameAndPhoneNumber(name, phone)
                .orElseGet(() -> {
                    Clients newClient = new Clients();
                    newClient.setClientName(name);
                    newClient.setPhoneNumber(phone);
                    newClient.setEmail(email);
                    return clientRepository.save(newClient);
                });
    }

    public List<Clients> getAllClients() {
        return clientRepository.findAll();
    }

    public Optional<Clients> getClientById(Long clientId) {
        return clientRepository.findById(clientId);
    }

    public Clients getClientByIdForInfo(Long clientId) {
        return clientRepository.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Клиент с ID " + clientId + " не найден"));
    }


    public boolean existsByNameAndPhone(String clientName, String phoneNumber) {
        return clientRepository.existsByClientNameAndPhoneNumber(clientName, phoneNumber);
    }

    public void updateClient(Long clientId, Clients updatedClient) {
        Clients existingClient = clientRepository.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Клиент с ID " + clientId + " не найден"));

        // Обновляем поля клиента
        existingClient.setClientName(updatedClient.getClientName());
        existingClient.setPhoneNumber(updatedClient.getPhoneNumber());
        existingClient.setEmail(updatedClient.getEmail());
        existingClient.setClientContact(updatedClient.getClientContact());
        existingClient.setAdditionalContact(updatedClient.getAdditionalContact());

        // Сохраняем изменения
        clientRepository.save(existingClient);
    }

    public void saveClient(Clients client) {
        clientRepository.save(client);
    }

    public List<Clients> findClientsByUserId(Long userId) {
        return clientRepository.findAllByUser_UserId(userId);
    }

    public List<Clients> getClientsByUser(User user) {
        return clientRepository.findByUser(user);
    }


}
