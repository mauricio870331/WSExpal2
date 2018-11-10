/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Procesos;

import Conecion.sqlServer10_1;
import Conecion.sqlServer20_55;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author administrador
 */
public class LiquidacionContable {

    static CallableStatement cstmt;

    //sqlServer20_55 pool = new sqlServer20_55();
    sqlServer10_1 pool = new sqlServer10_1();

    public void InicioProceso() {
        try {
            Contabilizar();
        } catch (SQLException ex) {
            Logger.getLogger(LiquidacionContable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void Contabilizar() throws SQLException {
        try {
            pool.con = pool.dataSource.getConnection();
            cstmt = pool.con.prepareCall("{call LiquidacionContabilizacion}");
            cstmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("error ex :" + ex.toString());
        } finally {
            pool.con.close();
        }
    }

    public void ContabilizarMan() throws SQLException {
        try {
            pool.con = pool.dataSource.getConnection();
            cstmt = pool.con.prepareCall("{call LiquidacionContabilizacionMAN}");
            cstmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("error ex :" + ex.toString());
        } finally {
            pool.con.close();
        }
    }

}
