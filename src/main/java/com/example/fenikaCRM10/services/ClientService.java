package com.example.fenikaCRM10.services;

import com.example.fenikaCRM10.models.ClientInfo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClientService {
    private List<ClientInfo> clientInfos = new ArrayList<>();
    private long ID = 0;
    {
        clientInfos.add(new ClientInfo(++ID, "Igor Petrov",
                "+79218589636", "фальц",
                "Хернекялла, Гачимучинское снт","YO-1232",
                "1452",
                1458765, 987563, 471202, "отгрузка 45 августа", "Жорка суперпродавец"
                ));
    }
    public List<ClientInfo> listClients() {
        return clientInfos;
    }
    public void saveClient(ClientInfo clientInfo){
        clientInfo.setId(++ID);
        clientInfos.add(clientInfo);
    }
    public void deleteClient(Long id){
        clientInfos.removeIf(clientInfo -> clientInfo.getId().equals(id));
    }
}
