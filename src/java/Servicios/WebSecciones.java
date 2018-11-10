/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicios;

import Procesos.PaginaWebEP;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author administrador
 */
@Path("WebSecciones")
public class WebSecciones {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GenericResource
     */
    public WebSecciones() {
    }

    /**
     * Retrieves representation of an instance of Servicios.GenericResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        //que esto lo retorne en json 
        PaginaWebEP pagina = new PaginaWebEP();
        return pagina.CargarSecciones();
    }

    /**
     * PUT method for updating or creating an instance of GenericResource
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }

}
