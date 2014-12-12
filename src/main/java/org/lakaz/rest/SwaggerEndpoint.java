package org.lakaz.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.lakaz.model.Swagger;

/**
 *
 */
@Stateless
@Path("/swaggers")
/**
 * This class shows an example of the swagger API annotations.
 */
@Api(value="/swaggers", description = "testing")
public class SwaggerEndpoint
{
   @PersistenceContext(unitName = "swagger-swagger-persistence-unit")
   private EntityManager em;

   @POST
   @Consumes("application/json")
   @ApiOperation(value="", notes="hahaha")
   public Response create(Swagger entity)
   {
      em.persist(entity);
      return Response.created(UriBuilder.fromResource(SwaggerEndpoint.class).path(String.valueOf(entity.getId())).build()).build();
   }

   @DELETE
   @Path("/{id:[0-9][0-9]*}")
   public Response deleteById(@PathParam("id") Long id)
   {
      Swagger entity = em.find(Swagger.class, id);
      if (entity == null)
      {
         return Response.status(Status.NOT_FOUND).build();
      }
      em.remove(entity);
      return Response.noContent().build();
   }

   @GET
   @Path("/{id:[0-9][0-9]*}")
   @Produces("application/json")
   public Response findById(@PathParam("id") Long id)
   {
      TypedQuery<Swagger> findByIdQuery = em.createQuery("SELECT DISTINCT s FROM Swagger s WHERE s.id = :entityId ORDER BY s.id", Swagger.class);
      findByIdQuery.setParameter("entityId", id);
      Swagger entity;
      try
      {
         entity = findByIdQuery.getSingleResult();
      }
      catch (NoResultException nre)
      {
         entity = null;
      }
      if (entity == null)
      {
         return Response.status(Status.NOT_FOUND).build();
      }
      return Response.ok(entity).build();
   }

   @GET
   @Produces("application/json")
   public List<Swagger> listAll(@QueryParam("start") Integer startPosition, @QueryParam("max") Integer maxResult)
   {
      TypedQuery<Swagger> findAllQuery = em.createQuery("SELECT DISTINCT s FROM Swagger s ORDER BY s.id", Swagger.class);
      if (startPosition != null)
      {
         findAllQuery.setFirstResult(startPosition);
      }
      if (maxResult != null)
      {
         findAllQuery.setMaxResults(maxResult);
      }
      final List<Swagger> results = findAllQuery.getResultList();
      return results;
   }

   @PUT
   @Path("/{id:[0-9][0-9]*}")
   @Consumes("application/json")
   public Response update(Swagger entity)
   {
      try
      {
         entity = em.merge(entity);
      }
      catch (OptimisticLockException e)
      {
         return Response.status(Response.Status.CONFLICT).entity(e.getEntity()).build();
      }

      return Response.noContent().build();
   }
}
