package de.kyleonaut.verification.user;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class User {

    @Id
    @Column(length = 36)
    private String uuid;

    @Column(length = 15)
    @Enumerated(EnumType.STRING)
    private STATUS status;

    public enum STATUS {
        AUTHORIZED,
        UNAUTHORIZED
    }
}
