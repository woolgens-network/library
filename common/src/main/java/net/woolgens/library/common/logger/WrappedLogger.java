package net.woolgens.library.common.logger;

import java.util.logging.Level;

/**
 * Copyright (c) Maga, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Maga
 **/
public interface WrappedLogger {

    void info(String message);
    void warning(String message);
    void severe(String message);
    void severe(String message, Throwable throwable);

    void log(Level level, String message);
}
