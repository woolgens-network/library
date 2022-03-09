package net.woolgens.library.database;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Copyright (c) Maga, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Maga
 **/
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Credentials {

    private String host;
    private int port;

    private String database;
    private String user;
    private String password;
}
