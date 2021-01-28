package com.github.labazhang.es.index;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * TODO
 *
 * @author Laba Zhang
 */
class IndexOperationTest {

    @org.junit.jupiter.api.Test
    void createIndex() throws IOException {
        String index = IndexOperation.createIndex("laba_zhang");
        System.out.println(index);
    }

    @org.junit.jupiter.api.Test
    void deleteIndex() throws IOException {
        boolean ok = IndexOperation.deleteIndex("laba_zhang");
        System.out.println(ok);
    }

    @org.junit.jupiter.api.Test
    void getAllIndices() throws IOException {
        List<String> allIndices = IndexOperation.getAllIndices();
        System.out.println(allIndices);
    }

    @org.junit.jupiter.api.Test
    void openIndex() {
    }

    @org.junit.jupiter.api.Test
    void closeIndex() {
    }

    @org.junit.jupiter.api.Test
    void analyze() {
    }
}