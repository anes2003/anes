package dev.anes.service.json;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import dev.anes.core.model.Owner;
import dev.anes.service.json.serial.Owner.OwnerDeserializer;
import dev.anes.service.json.serial.Owner.OwnerSerializer;

public class JsonModule {
    public static ObjectMapper MAPPER = _getDefaultMapper();

    private static ObjectMapper _getDefaultMapper() {
        // -->

        SimpleModule ownerModule = new SimpleModule();
        ownerModule.addSerializer(Owner.class, new OwnerSerializer());
        ownerModule.addDeserializer(Owner.class, new OwnerDeserializer());

        ObjectMapper defaultObjectMapper = new ObjectMapper();
        defaultObjectMapper.registerModules(
                new JavaTimeModule(),
                // -->
                ownerModule

        );
        defaultObjectMapper.setSerializationInclusion(Include.NON_NULL);
        defaultObjectMapper.setSerializationInclusion(Include.NON_EMPTY);
        defaultObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return defaultObjectMapper;
    }
}
