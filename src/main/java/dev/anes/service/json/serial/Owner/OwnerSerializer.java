package dev.anes.service.json.serial.Owner;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import dev.anes.core.model.Owner;

public class OwnerSerializer extends StdSerializer<Owner> {

    public OwnerSerializer() {
        super(Owner.class);
    }

    @Override
    public void serialize(Owner owner, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField("ownerID", owner.getOwnerID());
        gen.writeStringField("userName", owner.getUsername());
        gen.writeStringField("password", owner.getPassword());
        gen.writeStringField("firstName", owner.getFirstname());
        gen.writeStringField("lastName", owner.getLastname());
        gen.writeNumberField("gender", owner.getGender());
        gen.writeStringField("email", owner.getEmail());
        gen.writeStringField("phoneNumber", owner.getPhoneNumber());
        
        gen.writeEndObject();
     
    
    }

  

}

