/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicios;


import java.sql.SQLException;
import net.sf.jasperreports.engine.JRException;

/**
 *
 * @author administrador
 */
public class test {

    public static void main(String[] args) throws SQLException, JRException {
        EnvrialDespServicio g = new EnvrialDespServicio();
        g.pdf();   
//        g.EliminarDocumentos("Z:\\1113626301.pdf");
    }
}
