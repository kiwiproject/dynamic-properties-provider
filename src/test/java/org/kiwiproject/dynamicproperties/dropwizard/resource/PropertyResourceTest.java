package org.kiwiproject.dynamicproperties.dropwizard.resource;

import static org.junit.jupiter.api.Assertions.fail;
import static org.kiwiproject.test.jaxrs.JaxrsTestHelper.assertNotFoundResponse;
import static org.kiwiproject.test.jaxrs.JaxrsTestHelper.assertOkResponse;

import java.util.Map;

import org.json.JSONException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kiwiproject.dynamicproperties.data.Student;
import org.kiwiproject.jaxrs.KiwiResponses;
import org.kiwiproject.test.assertj.KiwiAssertJ;
import org.kiwiproject.test.assertj.jsonassert.JSONAssertSoftAssertions;
import org.kiwiproject.test.util.Fixtures;
import org.skyscreamer.jsonassert.JSONAssert;

import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;

@ExtendWith(DropwizardExtensionsSupport.class)
@DisplayName("PropertyResource")
class PropertyResourceTest {
    
    private static final PropertyResource PROPERTY_RESOURCE = new PropertyResource(Map.of("student", Student.class));
    private static final ResourceExtension RESOURCE = ResourceExtension.builder()
            .bootstrapLogging(false)
            .addResource(PROPERTY_RESOURCE)
            .build();

    @Nested
    class GetPropertiesForIdentifier {

        @Test
        void shouldReturn404WhenIdentifierNotFound() {
            var response = RESOURCE.client()
                    .target("/kiwi/dynamic-properties/teacher")
                    .request()
                    .get();

            assertNotFoundResponse(response);
        }

        @Test
        void shouldReturnExtractedPropertiesWhenFound() throws JSONException {
            var response = RESOURCE.client()
                    .target("/kiwi/dynamic-properties/student")
                    .request()
                    .get();

            assertOkResponse(response);

            var responseJson = response.readEntity(String.class);
            var expectedJson = Fixtures.fixture("studentProperties.json");

            JSONAssert.assertEquals(expectedJson, responseJson, false);
        }
    }

}
