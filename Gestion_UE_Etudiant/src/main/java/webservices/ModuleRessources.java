package webservices;

import entities.Module;
import entities.UniteEnseignement;
import metiers.ModuleBusiness;
import metiers.UniteEnseignementBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/modules")
public class ModuleRessources {

    public static ModuleBusiness moduleBusiness = new ModuleBusiness();
    public static UniteEnseignementBusiness ueBusiness = new UniteEnseignementBusiness(); // Pour la recherche par UE

    /**
     * Point d'accès pour GET /modules.
     * Liste tous les modules (JSON output).
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listAllModules() {
        List<Module> modules = moduleBusiness.getAllModules();
        return Response.ok(modules).build();
    }

    /**
     * Point d'accès pour GET /modules/UE?codeUE=X.
     * Liste tous les modules d'une UE donnée.
     */
    @GET
    @Path("/UE")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listModulesByUE(@QueryParam("codeUE") int codeUE) {
        UniteEnseignement ue = ueBusiness.getUEByCode(codeUE);
        if (ue == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Unité d'enseignement non trouvée.").build();
        }
        List<Module> modules = moduleBusiness.getModulesByUE(ue);
        return Response.ok(modules).build();
    }

    /**
     * Point d'accès pour GET /modules/{matricule}.
     * Récupère un module par sa matricule.
     */
    @GET
    @Path("/{matricule}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getModuleByMatricule(@PathParam("matricule") String matricule) {
        Module module = moduleBusiness.getModuleByMatricule(matricule);
        if (module != null) {
            return Response.ok(module).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Module non trouvé.").build();
        }
    }

    /**
     * Point d'accès pour POST /modules.
     * Crée un nouveau module (JSON input).
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createModule(Module module) {
        if (moduleBusiness.addModule(module)) {
            return Response.status(Response.Status.CREATED).entity("Module créé.").build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Erreur de création du module.").build();
        }
    }

    /**
     * Point d'accès pour PUT /modules/{matricule}.
     * Met à jour un module (JSON input).
     */
    @PUT
    @Path("/{matricule}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateModule(@PathParam("matricule") String matricule, Module module) {
        if (moduleBusiness.updateModule(matricule, module)) {
            return Response.ok().entity("Module mis à jour.").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Module non trouvé.").build();
        }
    }

    /**
     * Point d'accès pour DELETE /modules/{matricule}.
     * Supprime un module.
     */
    @DELETE
    @Path("/{matricule}")
    public Response deleteModule(@PathParam("matricule") String matricule) {
        if (moduleBusiness.deleteModule(matricule)) {
            return Response.ok().entity("Module supprimé.").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("Module non trouvé.").build();
        }
    }
}
