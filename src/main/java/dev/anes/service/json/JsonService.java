package dev.anes.service.json;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import dev.anes.core.model.Owner;
import dev.anes.service.json.serial.Owner.OwnerDeserializer;
import dev.anes.service.json.serial.Owner.OwnerSerializer;

public class JsonService {

    private static ObjectMapper objectMapper = _getDefaultMapper();

    private static ObjectMapper _getDefaultMapper() {
        // -->
        SimpleModule ownerModule = new SimpleModule();
        ownerModule.addSerializer(Owner.class, new OwnerSerializer());
        ownerModule.addDeserializer(Owner.class, new OwnerDeserializer());

        ObjectMapper defaultObjectMapper = new ObjectMapper();
        defaultObjectMapper.registerModules(
                new JavaTimeModule(),
                // -->
                ownerModule);
        defaultObjectMapper.setSerializationInclusion(Include.NON_NULL);
        defaultObjectMapper.setSerializationInclusion(Include.NON_EMPTY);
        defaultObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return defaultObjectMapper;
    }

    public static String readFromFile(File file) throws FileNotFoundException {
        if (file.exists()) {
            StringBuilder strBuilder = new StringBuilder();
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()) {
                strBuilder.append(reader.nextLine());
            }
            reader.close();
            return strBuilder.toString();
        }
        return null;
    }

    public static <T> void saveToFile(List<T> list, File file) throws IOException {
        JsonNode node = toJson(list);
        String jsonString = toString(node);

        FileWriter writer = new FileWriter(file);
        writer.write(jsonString);
        writer.close();
    }

    public static void saveToFile(String jsonString, File file) throws IOException {
        FileWriter writer = new FileWriter(file);
        writer.write(jsonString);
        writer.close();
    }

    public static JsonNode parse(String src) throws JsonMappingException, JsonProcessingException {
        return objectMapper.readTree(src);
    }

    public static <T> T fromJson(JsonNode node, Class<T> T) throws JsonProcessingException, IllegalArgumentException {
        return objectMapper.treeToValue(node, T);
    }

    public static <T> T fromJson(String nodeString, Class<T> T)
            throws JsonProcessingException, IllegalArgumentException {
        JsonNode node = parse(nodeString);
        return objectMapper.treeToValue(node, T);
    }

    public static <T> JsonNode toJson(T t) {
        return objectMapper.valueToTree(t);
    }

    public static <T> List<T> parseList(JsonNode node, Class<T> t) throws JsonParseException, IOException {
        JsonFactory factory = new JsonFactory();
        JsonParser parser = factory.createParser(node.toString());
        CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, t);
        return objectMapper.readValue(parser, listType);
    }

    public static <T> List<T> parseList(String jsonStr, Class<T> t)
            throws JsonMappingException, JsonProcessingException {
        TypeFactory factory = objectMapper.getTypeFactory();
        CollectionType listType = factory.constructCollectionType(ArrayList.class, t);
        return objectMapper.readValue(jsonStr, listType);
    }

    public static String toString(JsonNode node) throws JsonProcessingException {
        ObjectWriter writer = objectMapper.writer();
        writer = writer.with(SerializationFeature.INDENT_OUTPUT);
        return writer.writeValueAsString(node);
    }

    public static <T> String toString(T t) throws JsonProcessingException {
        JsonNode node = toJson(t);
        return toString(node);
    }

    public static <T> String toString(List<T> list) throws JsonProcessingException {
        JsonNode node = toJson(list);
        return toString(node);
    }

    public static <T> JsonNode serialize(T t) {
        return JsonService.toJson(t);
    }

    public static <T> T deserialize(JsonNode node, Class<T> tc)
            throws JsonProcessingException, IllegalArgumentException {
        return fromJson(node, tc);
    }

    public static <T> List<T> clone(List<T> t, Class<T> tc) {
        try {
            return parseList(serialize(t), tc);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Arrays.asList();
    }

    public static <T> T clone(T t, Class<T> tc) {
        try {
            return deserialize(serialize(t), tc);
        } catch (JsonProcessingException | IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        }
    }
}
