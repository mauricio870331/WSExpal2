/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Procesos;

import Conecion.sqlServer10_1;
import Pojos.Alarmas;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import javax.json.Json;

/**
 *
 * @author administrador
 */
public final class AlarmasTerminales {

    static TreeMap<Integer, TreeMap<Integer, List>> map = new TreeMap<>();

    static int secuencia = 0;

    public static String IniciarProceso() {
        int a=map.size();
        if (map.size() > 2) {
            int key = 0;
            Set set = map.entrySet();
            Iterator iterator = set.iterator();
            while (iterator.hasNext()) {
                Map.Entry mentry = (Map.Entry) iterator.next();
                key = (int) mentry.getKey();
                if (key > 0) {
                    break;
                }
            }
            map.remove(key);
        }
        List<Alarmas> Listalarmas = new ArrayList();
        TreeMap<Integer, List> MapAlarmas = new TreeMap<>();

        List<Integer> Terminales = new ArrayList();
        Terminales.add(1);
        Terminales.add(2);
        Terminales.add(4);
        Terminales.add(5);
        Terminales.add(6);
        Terminales.add(13);
        Terminales.add(14);
        Terminales.add(15);
        Terminales.add(16);
        Terminales.add(17);
        Terminales.add(20);
        Terminales.add(21);
        Terminales.add(22);
        Terminales.add(23);
//        Terminales.add(2);

        AlarmasTerminales star = new AlarmasTerminales();

        for (Integer idterminal : Terminales) {
//            Listalarmas.clear();
            Listalarmas = star.ListaTerminal(idterminal);
            System.out.println("Cargo : " + Listalarmas.size());
            MapAlarmas.put(idterminal, Listalarmas);
        }
        int s=secuencia;
        if(secuencia==0){
            secuencia=1;
        }else{
            secuencia++;
        }
        
        int s2=secuencia;
        map.put(secuencia, MapAlarmas);
        return "Size :" + map.size() + " secuencia:" + secuencia+" A:"+a+ "S:"+s+"S2:"+s2;
    }

    public static String CargaXTerminal(String idTerminal) {
        List<Alarmas> listAlarmas = new ArrayList();
        String json = "";
        String json2 = "";
        if (map.size() > 0) {
            listAlarmas = map.get(secuencia).get(Integer.parseInt(idTerminal));
            //JsonArray array = new JsonArray();
            //array.add(listAlarmas);
            json = new Gson().toJson(listAlarmas);
            //json = "El Json tiene : " + json2.length();
        }
//else {
//            json = "{mns : 'Error'}";
//        }
        return json;
    }

    public static List<Alarmas> ListaTerminal(int terminal) {
        sqlServer10_1 poolSql_1 = new sqlServer10_1();
        List<Alarmas> Alarmas = new ArrayList();
        try {
            String prepareQuery = "";
            prepareQuery = "select id,viaje,Origen,Destino,Bus,servicio,\n"
                    + "case when Horario is null then convert(char(5), FechaPartida, 108)  else convert(char(5), Horario, 108)  end horario,\n"
                    + "butacaslibres,(butacasTotales-butacaslibres) Ocupacion,\n"
                    + "(valor/Costo)*100 Porcentaje , dbo.FicsEstadoViaje(Numero_Orden,case when Horario is null then FechaPartida else  Horario end) estado,case when ((valor/Costo)*100)>=85.0 then 'Verde' \n"
                    + "when ((valor/Costo)*100) between 60.0 and 84.0 then 'Amarillo' \n"
                    + "when ((valor/Costo)*100)<60.0 then 'Rojo'  \n"
                    + "end alarma\n"
                    + "from AlarmasFICS(" + terminal + ") order by 1,7";
            poolSql_1.con = poolSql_1.dataSource.getConnection();
            ResultSet rs = poolSql_1.query(prepareQuery);
            while (rs.next()) {
                Alarmas Alarma = new Alarmas();
                Alarma.setTerminal(rs.getInt(1));
                Alarma.setViaje(rs.getInt(2));
                Alarma.setOrigen(rs.getString(3));
                Alarma.setDestino(rs.getString(4));
                Alarma.setBus(rs.getString(5));
                Alarma.setServicio(rs.getString(6));
                Alarma.setHora(rs.getString(7));
                Alarma.setButacasLibres(rs.getInt(8));
                Alarma.setButacasOcupadas(rs.getInt(9));
                Alarma.setPorcentaje(rs.getFloat(10));
                Alarma.setEstado(rs.getString(11));
                Alarma.setAlarma(rs.getString(12));

                Alarmas.add(Alarma);
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
        return Alarmas;
    }

}
