package com.github.labazhang.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main test
 *
 * @author Laba Zhang
 */
public class Main {
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(Main.class);
        logger.trace("trace: hello logger!");
        logger.debug("debug: hello logger!");
        logger.info("info: hello logger!");
        logger.warn("warn: hello logger!");
        logger.error("error: hello logger!");
    }
}