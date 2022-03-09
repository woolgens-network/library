package net.woolgens.library.common.logger.adapter;

import net.woolgens.library.common.logger.AnsiColors;
import net.woolgens.library.common.logger.WrappedLogger;

import java.io.PrintStream;
import java.util.Date;
import java.util.logging.*;

/**
 * Copyright (c) Maga, All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Maga
 **/
public class NamedLoggerAdapter implements WrappedLogger {

    private Logger logger;

    public NamedLoggerAdapter(String name) {
        this.logger = Logger.getLogger(name);
        overridePrintStream();
        load();
    }

    private void load() {
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new SimpleFormatter() {
            @Override
            public String format(LogRecord record) {
                String color = AnsiColors.ANSI_GREEN;
                if(record.getLevel() == Level.WARNING) {
                    color = AnsiColors.ANSI_YELLOW;
                } else if(record.getLevel() == Level.SEVERE) {
                    color = AnsiColors.ANSI_RED;
                }
                return String.format(color + "[%1$s] [%2$tF %2$tT] %3$s %n", record.getLevel().getLocalizedName(),
                        new Date(), record.getMessage());
            }
        });
        this.logger.setUseParentHandlers(false);
        this.logger.addHandler(consoleHandler);
    }

    private void overridePrintStream() {
        PrintStream stream = new PrintStream(System.err) {
            @Override
            public void println(String message) {
                if(!message.startsWith("SLF4J")) {
                    super.println();
                }
            }
        };
        System.setErr(stream);
    }

    @Override
    public void info(String message) {
        log(Level.INFO, message);
    }

    @Override
    public void warning(String message) {
        log(Level.WARNING, message);
    }

    @Override
    public void severe(String message) {
        logger.log(Level.SEVERE, message);
    }

    @Override
    public void severe(String message, Throwable throwable) {
        logger.log(Level.SEVERE, message, throwable);
    }

    @Override
    public void log(Level level, String message) {
        logger.log(level, message);
    }

}
