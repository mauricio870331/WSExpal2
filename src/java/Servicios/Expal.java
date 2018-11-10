/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicios;

import Procesos.AlarmasTerminales;
import static Procesos.AlarmasTerminales.*;
import Procesos.GeneradorSeguridadSocial;
import Procesos.GenerarPdfDesprendibles;
import Procesos.LiquidacionContable;
import Procesos.PaginaWebEP;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import net.sf.jasperreports.engine.JRException;

/**
 *
 * @author administrador
 */
@WebService(serviceName = "Expal")
public class Expal {

    /**
     * This is a sample web service operation
     *
     * @param txt
     */
    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
        String datos = "";
        try {
            PaginaWebEP pagina = new PaginaWebEP();
            datos = pagina.ConsultarRutas(txt);
        } catch (Exception ex) {
            datos = ex.toString();
        }
        return datos;
    }

    @WebMethod(operationName = "SeguridadSocial")
    public String SeguridadSocial(@WebParam(name = "Empresa") String Empresa, @WebParam(name = "aniomes") int aniomes) {
        GeneradorSeguridadSocial t = new GeneradorSeguridadSocial();
        try {
            t.InicioProceso(Empresa.trim(), aniomes);
        } catch (IOException ex) {
            return ex.toString();
        }

        return "Empresa :" + Empresa + " Aniomes:" + aniomes;
    }

    @WebMethod(operationName = "LiqContable")
    public String LiqContable() {
        LiquidacionContable liq = new LiquidacionContable();
        liq.InicioProceso();
        return "-";
    }

    @WebMethod(operationName = "LiqContableMan")
    public String LiqContableMan() {
        LiquidacionContable liq = new LiquidacionContable();
        try {
            liq.ContabilizarMan();
        } catch (SQLException ex) {
            Logger.getLogger(Expal.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "-";
    }

    @WebMethod(operationName = "Enviardesprendibles")
    public String Enviar() {
        String mensaje = "";
        try {
            GenerarPdfDesprendibles d = new GenerarPdfDesprendibles();
            mensaje = d.pdf();
        } catch (SQLException | JRException ex) {
            mensaje = ex.getMessage();
        }
        return mensaje;
    }

    @WebMethod(operationName = "AlarmasTerminales")
    public String AlarmasTerminales(@WebParam(name = "Condicion") String Condicion) {
        String mensaje;
        try {
            if (Condicion.equalsIgnoreCase("501")) {
                //hace llamado el servidor para recarga
                mensaje = IniciarProceso();
            } else {
                //Hace llamado las agencias para recarga
                mensaje = CargaXTerminal(Condicion);
            }
        } catch (Exception ex) {
            mensaje = "Error : " + ex.toString();
        }

        return mensaje;
    }

    @WebMethod(operationName = "PreciosWeb")
    public String PreciosWeb() {
        String mensaje;
        try {
            PaginaWebEP pagina = new PaginaWebEP();
            mensaje = pagina.CargaPrecios();
        } catch (Exception ex) {
            mensaje = "Error : " + ex.toString();
        }

        return mensaje;
    }

    @WebMethod(operationName = "PersonasVisitada")
    public String PersonasVisitada(@WebParam(name = "opc") String opc) {
        String datos = "";
        try {
            PaginaWebEP pagina = new PaginaWebEP();
            datos = pagina.CargarPersonas(opc);
        } catch (Exception ex) {
            datos = ex.toString();
        }
        return datos;
    }

    @WebMethod(operationName = "CargarCBoRutas")
    public String CargarCBoRutas() {
        String datos = "";
        try {
            PaginaWebEP pagina = new PaginaWebEP();
            datos = pagina.CargarCboRutas();
        } catch (Exception ex) {
            datos = ex.toString();
        }
        return datos;
    }

    /**
     * Web service operation
     *
     * @param idRegistro
     * @return
     */
    @WebMethod(operationName = "updateRodWS")
    public String updateRodWS(@WebParam(name = "idRegistro") String idRegistro) {
        //Este metodo es para actualizar el rod estado transferido a 1
        PaginaWebEP pagina = new PaginaWebEP();
        String r = pagina.ResponseUpdate(idRegistro);
        return r;
    }

    @WebMethod(operationName = "getRodToUpdate")
    public String getRodToUpdate(@WebParam(name = "idRegistro") String idRegistro) {
        //Este metodo es para actualizar el rod estado transferido a 1
        PaginaWebEP pagina = new PaginaWebEP();
        String r = pagina.getDataToUpdate(idRegistro);
        return r;
    }

}
