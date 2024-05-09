package dev.anes.service.json.serial.Owner;

import java.io.IOException;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import dev.anes.core.model.Owner;


public class OwnerDeserializer extends StdDeserializer<Owner> {

    public OwnerDeserializer() {
        this(null);
    }

    public OwnerDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Owner deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException, JacksonException {
        ObjectCodec codec = parser.getCodec();
        JsonNode node = codec.readTree(parser);

        int  ownerID = node.get("ownerID").asInt();
        String userName=node.get("userName").asText();
        String password=node.get("password").asText();
        String firstName=node.get("firstName").asText();
        String lastName=node.get("lastName").asText();
        int gender = node.get("gender").asInt();
        String email=node.get("email").asText();
        String phoneNumber=node.get("phoneNumber").asText();
       
        return new Owner(ownerID, userName, password, firstName, lastName, gender, email, phoneNumber);
    }

}