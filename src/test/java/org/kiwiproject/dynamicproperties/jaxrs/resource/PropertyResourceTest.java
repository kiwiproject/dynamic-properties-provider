package org.kiwiproject.dynamicproperties.jaxrs.resource;

import static org.kiwiproject.test.jaxrs.JaxrsTestHelper.assertNotFoundResponse;
import static org.kiwiproject.test.jaxrs.JaxrsTestHelper.assertOkResponse;

import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;
import org.json.JSONException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kiwiproject.dynamicproperties.data.Course;
import org.kiwiproject.dynamicproperties.data.Student;
import org.kiwiproject.test.util.Fixtures;
import org.skyscreamer.jsonassert.JSONAssert;

import java.util.Map;

@ExtendWith(DropwizardExtensionsSupport.class)
@DisplayName("PropertyResource")
class PropertyResourceTest {

    private static final PropertyResource PROPERTY_RESOURCE = new PropertyResource(
            Map.of("student", Student.class,
                    "course", Course.class));
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

        @Test
        void shouldReturnChoices() throws JSONException {
            var response = RESOURCE.client()
                    .target("/kiwi/dynamic-properties/course")
                    .request()
                    .get();

            assertOkResponse(response);

            var responseJson = response.readEntity(String.class);
            System.out.println(responseJson);
            var expectedJson = Fixtures.fixture("courseProperties.json");

            JSONAssert.assertEquals(expectedJson, responseJson, false);

        }
    }

    @Nested
    class GetAllProperties {

        @Test
        void shouldReturnExtractedProperties() throws JSONException {
            var response = RESOURCE.client()
                    .target("/kiwi/dynamic-properties")
                    .request()
                    .get();

            assertOkResponse(response);

            var responseJson = response.readEntity(String.class);
            var expectedJson = Fixtures.fixture("allProperties.json");

            JSONAssert.assertEquals(expectedJson, responseJson, false);
        }
    }

}
