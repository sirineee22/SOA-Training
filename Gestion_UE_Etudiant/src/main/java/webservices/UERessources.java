package webservices;

import entities.UniteEnseignement;
import metiers.UniteEnseignementBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;

@Path("/UE")
public class UERessources {

    public static UniteEnseignementBusiness ueBusiness = new UniteEnseignementBusiness();

    /**
     * Point d'accès pour GET /UE.
     * - GET /UE : Liste toutes les unités (JSON output).
     * - GET /UE?semestre=2 : Liste les unités d'un semestre.
     * - GET /UE?code=123 : Récupère une UE par son code.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUnitesEnseignement(@QueryParam("semestre") Integer semestre, @QueryParam("code") Integer code) {
       
        if (semestre != null) {
            List<UniteEnseignement> ues = ueBusiness.getUEBySemestre(semestre);
            return Response.ok(ues).build();
        }
       
        else if (code != null) {
            UniteEnseignement ue = ueBusiness.getUEByCode(code);
            return Response.ok(ue != null ? Collections.singletonList(ue) : Collections.emptyList()).build();
        }
        
        else {
            List<UniteEnseignement> ues = ueBusiness.getListeUE();
            return Response.ok(ues).build();
        }
    }

    /**
     * Point d'accès pour POST /UE.
     * Crée une nouvelle unité d'enseignement (XML input).
     */
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public Response createUniteEnseignement(UniteEnseignement ue) {
        if (ueBusiness.addUniteEnseignement(ue)) {
            return Response.status(Response.Status.CREATED).entity("Unité d'enseignement créée.").build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Erreur de création.").build();
        }
    }

    /**
     * Point d'accès pour PUT /UE/{id}.
     * Met à jour une UE (XML input).
     */
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_XML)
    public Response updateUniteEnseignement(@PathParam("id") int id, UniteEnseignement ue) {
        if (ueBusiness.updateUniteEnseignement(id, ue)) {
            return Response.ok().entity("Unité d'enseignement mise à jour.").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Unité d'enseignement non trouvée.").build();
        }
    }

    /**
     * Point d'accès pour DELETE /UE/{id}.
     * Supprime une UE.
     */
    @DELETE
    @Path("/{id}")
    public Response deleteUniteEnseignement(@PathParam("id") int id) {
        if (ueBusiness.deleteUniteEnseignement(id)) {
            return Response.ok().entity("Unité d'enseignement supprimée.").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Unité d'enseignement non trouvée.").build();
        }
    }
}

