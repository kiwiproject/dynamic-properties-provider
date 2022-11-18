package org.kiwiproject.dynamicproperties.jaxrs.resource;

import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toMap;
import static org.kiwiproject.base.KiwiStrings.f;
import static org.kiwiproject.jaxrs.KiwiStandardResponses.standardNotFoundResponse;

import org.kiwiproject.dynamicproperties.PropertyExtractor;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;
import java.util.Map.Entry;

@Path("/kiwi/dynamic-properties")
@Produces(MediaType.APPLICATION_JSON)
public class PropertyResource {

    private final Map<String, Class<?>> dynamicPropertyClasses;

    public PropertyResource(Map<String, Class<?>> dynamicPropertyClasses) {
        this.dynamicPropertyClasses = dynamicPropertyClasses;
    }

    @GET
    @Path("/{identifier}")
    public Response getPropertiesForIdentifier(@PathParam("identifier") String identifier) {
        var dynamicPropertyClass = dynamicPropertyClasses.get(identifier);

        if (isNull(dynamicPropertyClass)) {
            return standardNotFoundResponse(f("Unable to find dynamic property class for {}", identifier));
        }

        var extractedProperties = PropertyExtractor.extractPropertiesFromClass(dynamicPropertyClass);
        return Response.ok(extractedProperties).build();
    }

    @GET
    public Response getAllProperties() {
        var extractedProperties = dynamicPropertyClasses.entrySet().stream()
                .collect(toMap(
                        Entry::getKey,
                        entry -> PropertyExtractor.extractPropertiesFromClass(entry.getValue())));

        return Response.ok(extractedProperties).build();
    }
}
