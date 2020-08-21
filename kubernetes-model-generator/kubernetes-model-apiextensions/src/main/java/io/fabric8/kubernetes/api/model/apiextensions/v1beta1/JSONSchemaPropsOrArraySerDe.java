/**
 * Copyright (C) 2015 Red Hat, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.fabric8.kubernetes.api.model.apiextensions.v1beta1;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class JSONSchemaPropsOrArraySerDe {

  private JSONSchemaPropsOrArraySerDe() {
  }

  public static class Serializer extends JsonSerializer<JSONSchemaPropsOrArray> {
    @Override
    public void serialize(JSONSchemaPropsOrArray jsonSchemaPropsOrArray,
                          JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException {
      if (jsonSchemaPropsOrArray.getJSONSchemas() != null && !jsonSchemaPropsOrArray.getJSONSchemas().isEmpty()) {
        jsonGenerator.writeStartArray();
        for (JSONSchemaProps schema : jsonSchemaPropsOrArray.getJSONSchemas()) {
          jsonGenerator.writeObject(schema);
        }
        jsonGenerator.writeEndArray();
      } else {
        jsonGenerator.writeObject(jsonSchemaPropsOrArray.getSchema());
      }
    }
  }

  public static class Deserializer extends JsonDeserializer<JSONSchemaPropsOrArray> {

    @Override
    public JSONSchemaPropsOrArray deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
      JSONSchemaPropsOrArrayBuilder builder = new JSONSchemaPropsOrArrayBuilder();
      if (jsonParser.isExpectedStartObjectToken()) {
        builder.withSchema(
          jsonParser.readValueAs(JSONSchemaProps.class));
      } else if (jsonParser.isExpectedStartArrayToken()) {
        builder.withJSONSchemas(jsonParser.<List<JSONSchemaProps>>readValueAs(new TypeReference<List<JSONSchemaProps>>() {}));
      }
      return builder.build();
    }
  }
}