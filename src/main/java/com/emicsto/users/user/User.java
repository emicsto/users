package com.emicsto.users.user;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Data
@Entity
class User {
    @Id
    private String login;

    private int requestCount;
}
