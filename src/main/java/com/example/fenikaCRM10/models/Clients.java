package com.example.fenikaCRM10.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "clients")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Clients {

    @Id
    @Column(name = "clientId")
    private Long clientId;

    @Column(name = "clientName")
    private String clientName;

    @Column(name = "clientContact")
    private String clientContact;

    @Column(name = "email")
    private String email;

    @Column(name = "phoneNumber")
    private String phoneNumber;

    @Column(name = "additionalContact")
    private String additionalContact;

//    @Column(name = "additionalPhone")
//    private String additionalPhone;

}
