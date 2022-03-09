package net.woolgens.library.vault;

/**
 * Copyright (c) Maga, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Maga
 **/
public interface VaultProvider {

    String getSecret(String category, String key);
}
