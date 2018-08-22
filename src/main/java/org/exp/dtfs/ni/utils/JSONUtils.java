package org.exp.dtfs.ni.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONUtils {
    private JSONUtils() {
        // Do nothing.
    }

    public static String buildJSONString(Object obj) throws IOException {
        try (OutputStream stream = new ByteArrayOutputStream();
                JsonGenerator generator = new ObjectMapper().getFactory().createGenerator(stream, JsonEncoding.UTF8);) {
            // ObjectMapper mapper = new ObjectMapper();
            generator.writeObject(obj);
            // String reportString = mapper.writeValueAsString(report);
            return stream.toString();
        }
    }

}
