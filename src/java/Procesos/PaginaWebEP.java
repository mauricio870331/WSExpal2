/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Procesos;

import Conecion.sqlServer10_1;
import Pojos.InfoRutasweb;
import Pojos.PlataformaDC;
import Pojos.SeccionesWeb;
import Pojos.rutasWeb;
import com.google.gson.Gson;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author administrador
 */
public class PaginaWebEP {

    public String CargaPrecios() {
        List<rutasWeb> listruta = new ArrayList();
        String json = "";
        listruta = ListaRutas();
        json = new Gson().toJson(listruta);
        return json;
    }

    public String CargarSecciones() {
        List<SeccionesWeb> listaSecciones = new ArrayList();
        String json = "";
        listaSecciones = listaSecciones();
        json = new Gson().toJson(listaSecciones);
        return json;
    }

    public List<rutasWeb> ListaRutas() {
        sqlServer10_1 poolSql_1 = new sqlServer10_1();
        List<rutasWeb> listRutas = new ArrayList();
        try {
            String prepareQuery = "";
            prepareQuery = "select A.TerminalID,B.Nombre from WF_PALM.dbo.vo_terminales A , WF_PALM.dbo.Terminales B,\n"
                    + "WF_PALM.dbo.vo_terminales  C\n"
                    + "where A.TerminalID=B.Id and \n"
                    + "A.TerminalID=C.TerminalID";
            poolSql_1.con = poolSql_1.dataSource.getConnection();
            ResultSet rs = poolSql_1.query(prepareQuery);
            while (rs.next()) {
                rutasWeb ruta = new rutasWeb();
                ruta.setId(rs.getInt(1));
                ruta.setTerminal(rs.getString(2));

                listRutas.add(ruta);
            }
        } catch (SQLException ex) {
            System.out.println("Error Consulta : " + ex.toString());
        } finally {
            try {
                poolSql_1.con.close();
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }
        return listRutas;
    }

    public List<SeccionesWeb> listaSecciones() {
        sqlServer10_1 poolSql_1 = new sqlServer10_1();
        List<SeccionesWeb> listasecciones = new ArrayList();
        try {
            String prepareQuery = "";
            prepareQuery = "select desc_seccion, cod_seccion from ct_RHSecciones where cod_emp ='ExpresoP' \n"
                    + "			   and cod_seccion in (select cod_seccion from v_RHTrabajador where cod_sucursal = '76892'\n"
                    + "			   and cod_emp ='ExpresoP')			   \n"
                    + "			   UNION 			   \n"
                    + "			   select 'CASINO','CASINO' 			   \n"
                    + "			   UNION 			   \n"
                    + "				select 'SERVIAFIL','SERVI'\n"
                    + "				UNION 			   \n"
                    + "				select 'ESCANIA','ESCAN' order by desc_seccion";
            poolSql_1.con = poolSql_1.dataSource.getConnection();
            ResultSet rs = poolSql_1.query(prepareQuery);
            while (rs.next()) {
                SeccionesWeb ruta = new SeccionesWeb();
                ruta.setCod_seccion(rs.getString(1).trim());
                ruta.setDesc_seccion(rs.getString(2).trim());

                listasecciones.add(ruta);
            }
        } catch (SQLException ex) {
            System.out.println("Error Consulta : " + ex.toString());
        } finally {
            try {
                poolSql_1.con.close();
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }
        return listasecciones;
    }

    public String CargarPersonas(String opc) {
        List<SeccionesWeb> listaSecciones = new ArrayList();
        String json = "";
        listaSecciones = CargarpersonasVisitadas(opc);
        json = new Gson().toJson(listaSecciones);
        return json;
    }

    public List<SeccionesWeb> CargarpersonasVisitadas(String opc) {
        sqlServer10_1 poolSql_1 = new sqlServer10_1();
        List<SeccionesWeb> listasecciones = new ArrayList();

        String and = "";

        if (opc.equals("GEST")) {
            and = " UNION SELECT c.nombre_completo, cat.descrip FROM ct_RHPersonas c ,ct_RHTrabajador trab, ct_RHCategoriaTr cat "
                    + "where trab.cod_persona = c.cod_persona  and trab.cod_cate_tr = 128 "
                    + "and cat.cod_cate_tr = trab.cod_cate_tr and c.cod_persona = '1144054436' "
                    + "and cat.cod_emp ='Expresop'";
        }

        if (opc.equals("GERE")) {
            and = " UNION SELECT 'NAYRA SALOME PARRA MENDOZA','SECRETARIA DOCTOR JORGE'";
        }

        try {
            String prepareQuery = "select empleado.nombre_completo,categoria.descrip from v_RHTrabajador as empleado, ct_RHCategoriaTr as categoria"
                    + " where empleado.cod_emp = 'Expresop' and empleado.cod_sucursal = '76892'"
                    + " and empleado.cod_seccion ='" + opc + "' and empleado.estado_trab = 'ACTIVO' and empleado.cod_cate_tr = categoria.cod_cate_tr "
                    + "  and categoria.cod_emp ='Expresop'" + and + " order by 1";
            poolSql_1.con = poolSql_1.dataSource.getConnection();
            ResultSet rs = poolSql_1.query(prepareQuery);
            while (rs.next()) {
                SeccionesWeb ruta = new SeccionesWeb();
                ruta.setCod_seccion(rs.getString(1).trim());
                ruta.setDesc_seccion(rs.getString(2).trim());

                listasecciones.add(ruta);
            }
        } catch (SQLException ex) {
            System.out.println("Error Consulta : " + ex.toString());
        } finally {
            try {
                poolSql_1.con.close();
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }
        return listasecciones;
    }

    public String CargarCboRutas() {
        List<SeccionesWeb> listaSecciones = new ArrayList();
        String json = "";
        listaSecciones = CargarRutasWeb();
        json = new Gson().toJson(listaSecciones);
        return json;
    }

    public List<SeccionesWeb> CargarRutasWeb() {
        sqlServer10_1 poolSql_1 = new sqlServer10_1();
        List<SeccionesWeb> listasecciones = new ArrayList();
        try {
            String prepareQuery = "select distinct origen "
                    + "from cpp_rutasweb a ,cpt_rutasweb b  where a.nro_trans=b.nro_trans and "
                    + "a.fecha_mod in (select MAX(fecha_mod) from cpp_rutasweb) order by origen";
            poolSql_1.con = poolSql_1.dataSource.getConnection();
            ResultSet rs = poolSql_1.query(prepareQuery);
            while (rs.next()) {
                SeccionesWeb ruta = new SeccionesWeb();
                ruta.setDesc_seccion(rs.getString(1).trim());
                listasecciones.add(ruta);
            }
        } catch (SQLException ex) {
            System.out.println("Error Consulta : " + ex.toString());
        } finally {
            try {
                poolSql_1.con.close();
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }
        return listasecciones;
    }

    //----------------------
    public String ConsultarRutas(String data) {
        List<InfoRutasweb> listaSecciones = new ArrayList();
        String json = "";
        listaSecciones = ConsultarRutasDesc(data);
        json = new Gson().toJson(listaSecciones);
        return json;
    }

    public List<InfoRutasweb> ConsultarRutasDesc(String data) {
        sqlServer10_1 poolSql_1 = new sqlServer10_1();
        List<InfoRutasweb> lista = new ArrayList();
        try {
            String d[] = data.split(";");
            String origen = d[0].trim();
            String destino = d[1].trim();
            String servicio = d[2].trim();
            String andWhere = "";

            if (!origen.equals("") && !destino.equals("")) {
                andWhere = "and origen = '" + origen + "' and destino = '" + destino + "' ";
                if (!servicio.equals("Selecciona")) {
                    andWhere += " and servicio = '" + servicio.replace("*", " ") + "'";
                }
            } else {
                andWhere = "and origen = 'Cali' and destino = 'Bogota' ";
            }

            String prepareQuery = "select a.fecha as fecha, origen,destino,case when b.Temporada='Baja' then cast(precio as numeric) "
                    + "when b.Temporada='Alta' then cast(precio2 as numeric) end precio,servicio,"
                    + "SUBSTRING(hora,0,3)+':'+ SUBSTRING(hora,3,5) horario,"
                    + "observaciones from cpp_rutasweb a ,cpt_rutasweb b  where a.nro_trans=b.nro_trans and "
                    + "a.fecha_mod in (select MAX(fecha_mod) from cpp_rutasweb) " + andWhere + " order by horario";
            poolSql_1.con = poolSql_1.dataSource.getConnection();
            ResultSet rs = poolSql_1.query(prepareQuery);
            while (rs.next()) {
                InfoRutasweb ruta = new InfoRutasweb();
                ruta.setFecha(rs.getString(1));
                ruta.setOrigen(rs.getString(2));
                ruta.setDestino(rs.getString(3));
                ruta.setPrecio(rs.getString(4));
                ruta.setServicio(rs.getString(5));
                ruta.setHorario(rs.getString(6));
                ruta.setObservaciones(rs.getString(7));
                lista.add(ruta);
            }
        } catch (SQLException ex) {
            System.out.println("Error Consulta : " + ex.toString());
        } finally {
            try {
                poolSql_1.con.close();
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }
        return lista;
    }

    public String CargarDatosASubir() {
        List<PlataformaDC> lista = new ArrayList();
        String json = "";
        lista = ListarDatosPlataformaDc();
        json = new Gson().toJson(lista);
        return json;
    }

    public List<PlataformaDC> ListarDatosPlataformaDc() {
        sqlServer10_1 poolSql_1 = new sqlServer10_1();
        List<PlataformaDC> lista = new ArrayList();
        try {
            String prepareQuery = "";
            prepareQuery = "select top 1 * from datosPlataformaWS where cargado  = 0";
            poolSql_1.con = poolSql_1.dataSource.getConnection();
            ResultSet rs = poolSql_1.query(prepareQuery);
            while (rs.next()) {
                PlataformaDC datos = new PlataformaDC();
                datos.setIdregistro(rs.getInt(1));
                datos.setCodEmpresa(rs.getString(2));
                datos.setFechSalida(rs.getString(3));
                datos.setHoraSalida(rs.getString(4));
                datos.setCodRuta(rs.getString(5));
                datos.setPlacaVehiculo(rs.getString(6));
                datos.setConductor1(rs.getString(7));
                datos.setConductor2(rs.getString(8));
                datos.setConductor3(rs.getString(9));
                datos.setAlistamiento(rs.getString(10));
                datos.setEstado(rs.getString(11));
                datos.setCargado(rs.getInt(12));
                datos.setToken(rs.getString(13));
                datos.setViajeId(rs.getInt(15));
                lista.add(datos);
            }
        } catch (SQLException ex) {
            System.out.println("Error Consulta : " + ex.toString());
            PlataformaDC datos = new PlataformaDC();
            datos.setAlistamiento(ex.toString());
            lista.add(datos);
        } finally {
            try {
                poolSql_1.con.close();
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }
        return lista;
    }

    public String updateRodTransfer(String idRegistro) {
        sqlServer10_1 poolSql_1 = new sqlServer10_1();
        String response = "";
        String Datos[] = idRegistro.split(";");
        try {
            String prepareQuery = "Update datosPlataformaWS set cargado = 1, estado = '" + Datos[1] + "' where id_registro = " + Integer.parseInt(Datos[0]);
            poolSql_1.con = poolSql_1.dataSource.getConnection();
            PreparedStatement pstm = poolSql_1.con.prepareStatement(prepareQuery);
            if (pstm.executeUpdate() > 0) {
                response = "ok";
                CallableStatement cstmt = poolSql_1.con.prepareCall("{call EnviarEmail (?,?,?,?,?,?)}");
                cstmt.setString(1, "Email Sistemas");
                cstmt.setString(2, "desarrollo1@expresopalmira.com.co");
                cstmt.setString(3, "Cargar Rodarmiento TU");
                cstmt.setString(4, Datos[2]);
                cstmt.setString(5, "Informacion Tasa de Uso");
                cstmt.setInt(6, 2);
                cstmt.execute();
            }
        } catch (SQLException ex) {
            System.out.println("Error Consulta : " + ex.toString());
            response = "error " + ex.toString();
        } finally {
            try {
                poolSql_1.con.close();
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }
        return response;
    }

    public String ResponseUpdate(String data) {
        String json = "";
        json = new Gson().toJson(updateRodTransfer(data));
        return json;
    }

    public String transferDataToWS() {
        String json = "";
        json = new Gson().toJson(padarDatosAlWebServices());
        return json;
    }

    public String padarDatosAlWebServices() {
        sqlServer10_1 poolSql_1 = new sqlServer10_1();
        String response = "";
        try {
            poolSql_1.con = poolSql_1.dataSource.getConnection();
            CallableStatement cstmt = poolSql_1.con.prepareCall("{call prc_transferDataToWS()}");
            cstmt.executeQuery();
            response = "ok";
        } catch (SQLException ex) {
            response = "error " + ex;
        }
        return response;
    }

    //para traer datos de un rodamiento a actualizar
    public String getDataToUpdate(String idregistro) {
        List<PlataformaDC> lista = new ArrayList();
        String json = "";
        lista = getDatosToupdateWS(idregistro);
        json = new Gson().toJson(lista);
        return json;
    }

    public List<PlataformaDC> getDatosToupdateWS(String idregistro) {
        sqlServer10_1 poolSql_1 = new sqlServer10_1();
        List<PlataformaDC> lista = new ArrayList();
        try {
            String prepareQuery = "";
            prepareQuery = "select  * from datosPlataformaWS where id_registro  =" + idregistro;
            poolSql_1.con = poolSql_1.dataSource.getConnection();
            ResultSet rs = poolSql_1.query(prepareQuery);
            while (rs.next()) {
                PlataformaDC datos = new PlataformaDC();
                datos.setIdregistro(rs.getInt(1));
                datos.setCodEmpresa(rs.getString(2));
                datos.setFechSalida(rs.getString(3));
                datos.setHoraSalida(rs.getString(4));
                datos.setCodRuta(rs.getString(5));
                datos.setPlacaVehiculo(rs.getString(6));
                datos.setConductor1(rs.getString(7));
                datos.setConductor2(rs.getString(8));
                datos.setConductor3(rs.getString(9));
                datos.setAlistamiento(rs.getString(10));
                datos.setEstado(rs.getString(11));
                datos.setCargado(rs.getInt(12));
                datos.setToken(rs.getString(13));
                datos.setViajeId(rs.getInt(15));
                lista.add(datos);
            }
        } catch (SQLException ex) {
            System.out.println("Error Consulta : " + ex.toString());
            PlataformaDC datos = new PlataformaDC();
            datos.setAlistamiento(ex.toString());
            lista.add(datos);
        } finally {
            try {
                poolSql_1.con.close();
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }
        return lista;
    }

}
