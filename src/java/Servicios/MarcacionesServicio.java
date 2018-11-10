/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicios;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author Administrador
 */
@Path("Marcaciones")
public class MarcacionesServicio {

    @Context
    private UriInfo context;
    static CallableStatement cstmt;
    static PreparedStatement pstm;
    static Connection conSqlserver = null;

    /**
     * Creates a new instance of GenericResource
     */
    public MarcacionesServicio() {
    }

    /**
     * Retrieves representation of an instance of Servicios.GenericResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        //TODO return proper representation object
        return "{\"prueba\":\"Mauricio\"}";
    }

    /**
     * PUT method for updating or creating an instance of MarcacionesServicio
     *
     * @param content representation for the resource
     * @return
     * @throws java.sql.SQLException
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public String putJson(String content) throws SQLException {
        String msn = "Todo Ok";
        String[] lista = content.split("Ç");
        try {
            conectarSqlServer();
            for (String lista1 : lista) {
                String[] data = lista1.split(",");
                String tipo = "E";
                if (data[3].equals("1") || data[3].equals("2")) {
                    tipo = "S";
                }
                String query = " insert into Checkinout2 values (" + data[1] + ",'" + data[2].substring(0, 19) + "'," + data[3] + "," + data[4] + ",0,0,0,0,0,'" + tipo + "',null)";
                pstm = conSqlserver.prepareStatement(query);
                pstm.executeUpdate();
            }
        } catch (SQLException e) {
            msn = e.getMessage();
        } finally {
            if (pstm != null) {
                pstm.close();
            }
            conSqlserver.close();
        }
        return msn;
    }

    public Connection conectarSqlServer() {
        String connectionUrl = "jdbc:sqlserver://192.168.10.19:1433;"
                + "databaseName=biometrico;user=sa;password=26031980;";
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conSqlserver = DriverManager.getConnection(connectionUrl);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("error ppp" + e);
        }
        return conSqlserver;
    }
}
