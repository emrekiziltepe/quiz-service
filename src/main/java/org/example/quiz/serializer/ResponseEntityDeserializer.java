package org.example.quiz.serializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public class ResponseEntityDeserializer extends StdDeserializer<ResponseEntity> {
    private static final String BODY_FIELD = "body";
    private static final String STATUS_FIELD = "status";

    public ResponseEntityDeserializer() {
        super(ResponseEntity.class);
    }

    public ResponseEntity deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException, JacksonException {
        ObjectMapper mapper = (ObjectMapper)parser.getCodec();
        JsonNode jsonNode = (JsonNode)mapper.readTree(parser);
        Object body = mapper.convertValue(jsonNode.get("body"), Object.class);
        HttpStatus httpStatus = this.parseHttpStatus(jsonNode);
        return new ResponseEntity(body, httpStatus);
    }

    private HttpStatus parseHttpStatus(JsonNode jsonNode) {
        JsonNode statusNode = jsonNode.get("status");
        return !statusNode.has(1) ? null : HttpStatus.valueOf(statusNode.get(1).textValue());
    }
}
