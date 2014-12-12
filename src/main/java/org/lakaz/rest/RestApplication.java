package org.lakaz.rest;

import org.reflections.Reflections;

import javax.ws.rs.core.Application;
import javax.ws.rs.ApplicationPath;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/rest")
public class RestApplication extends Application {

    /**
     * All this stuff is added to support the 'swagger' library. So much work!
     *
     * Usually defining an empty 'RestApplication' is good enough, but I had to
     * add those extra stuff because of the 'swagger' library.
     *
     * I override some methods. I use the 'reflections' library to scan for the
     * 'rest' package for classes that is annotated with '@Path'. That means
     * that this class will be used as a REST endpoint and that I should add it
     * to the singletons set. I need to do that so that:
     *
     * 1. Resteasy sees the classes that serves a REST endpoint
     * 2. Swagger scans through those classes to find relevant documentation.
     *
     * This is adapted from the various swagger-jaxrs examples found on Github.
     * I have adapted those examples so that:
     *
     * 1. I use the minimum configuration needed in that class and in the
     *    web.xml
     * 2. It auto-scans the package for REST endpoints, instead of having to
     *    hard-code the classes to add. Doing so reduces the number of places
     *    where code needs to be modified when a new REST endpoint class is
     *    added or removed.
     */
    Set<Class<?>> singletons;
    Set<Object> clazz;

    public RestApplication() {
        Reflections reflections = new Reflections(this.getClass()
                                                      .getPackage()
                                                      .getName());
        singletons = reflections.getTypesAnnotatedWith(javax.ws.rs.Path.class);

        clazz = new HashSet<Object>();

        for (Class<?> annotated_class : singletons) {
            try {
                clazz.add(annotated_class.newInstance());
            } catch (Exception e) {
                System.out.println("Oh oh something didn't load");
                e.printStackTrace();
            }
        }
    }

    @Override
    public Set<Class<?>> getClasses() {
        return singletons;
    }

    @Override
    public Set<Object> getSingletons() {
        return clazz;
    }
}