/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Procesos;

import Conecion.sqlServer10_1;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author admin
 */
public class GeneradorSeguridadSocial {

    sqlServer10_1 poolSql_1 = new sqlServer10_1();
    String ruta = "";

    public static void main(String[] args) {
        GeneradorSeguridadSocial t = new GeneradorSeguridadSocial();
        try {
            t.InicioProceso("prueba", 201805);
        } catch (IOException ex) {
            System.out.println("error : " + ex.toString());
        }
    }

    public void InicioProceso(String empresa, int aniomes) throws IOException {
        ruta = "";
        ruta = CrearFolder(empresa, aniomes);
        CrearParte1SQL();
        CrearParte2SQL();
        CrearParte3SQL();
        CrearParte4SQL();
        CrearParte5SQL();
        CrearParte6SQL();
        CrearParte7SQL();
        CrearParte8SQL();
        CrearParte9SQL();
        CrearParte10SQL();
        CrearParte11SQL();
    }

    public String CrearFolder(String empresa, int aniomes) throws IOException {
        String ruta = "C:/SeguridadSocial/" + aniomes;
        String aniomesEmpresa = "C:/SeguridadSocial/" + aniomes + "/" + empresa + ".txt";

        File archivo = new File(ruta);
        File archivo2 = new File(aniomesEmpresa);

        if (archivo.exists()) {
            System.out.println("Existe");
        } else {
            System.out.println("No Existe");
            archivo.mkdir();
        }

        //Borramos Archivo si existe
        BufferedWriter bw;
        if (archivo2.delete()) {
            //Creamos Archivo
            bw = new BufferedWriter(new FileWriter(archivo2));
            bw.write("El fichero de texto ya estaba creado.\n");
        } else if (!archivo2.exists()) {
            bw = new BufferedWriter(new FileWriter(archivo2));
            bw.write("El fichero de texto ya estaba creado.\n");
        }
        return aniomesEmpresa;
    }

    public void Reporte() throws IOException {
        FileWriter fichero = null;
        PrintWriter pw = null;
        try {
            String Registro2 = "";
            poolSql_1.con = poolSql_1.dataSource.getConnection();
            ResultSet rs = poolSql_1.query("select A.cod_emp,nro_trans,cod_cta,cod_dpto,fec_doc,cod_docum,A.cod_tit,B.nro_dgi1,B.nom_tit,B.razon_social,B.dir_tit,B.tel_tit,B.email_tit,B.cod_provincia,\n"
                    + "(select cod_sucuremp from ct_sucuremp where cod_sucuremp=A.cod_sucursal and cod_emp='expresop'),\n"
                    + "(select nom_sucuremp from ct_sucuremp where cod_sucuremp=a.cod_sucursal and cod_emp='expresop'),\n"
                    + "A.imp_mov_mn,A.signo,A.ref1_mov,A.formulario,A.usuario_mod,A.fecha_mod\n"
                    + " from cpf_contauxB A , ct_proveedores B\n"
                    + "where A.cod_emp=B.cod_emp and A.cod_tit=B.cod_tit\n"
                    + "and fec_doc between '2017-03-16 00:00:00' and '2017-03-28 23:59:59'");
            fichero = new FileWriter(this.ruta, true);
            pw = new PrintWriter(fichero);

            while (rs.next()) {
                pw.println(rs.getString(1) + "," + rs.getString(2) + "," + rs.getString(3)
                        + "," + rs.getString(4) + "," + rs.getString(5) + "," + rs.getString(6) + "," + rs.getString(7)
                        + "," + rs.getString(8) + "," + rs.getString(9) + "," + rs.getString(10) + "," + rs.getString(11)
                        + "," + rs.getString(12) + "," + rs.getString(13) + "," + rs.getString(14) + "," + rs.getString(15)
                        + "," + rs.getString(16) + "," + "" + rs.getDouble(17) + ""
                        + "," + rs.getString(18) + "," + rs.getString(19) + "," + rs.getString(20) + "," + rs.getString(21)
                        + "," + rs.getString(22));
            }

            System.out.println(Registro2);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
// Nuevamente aprovechamos el finally para 
// asegurarnos que se cierra el fichero. 
            try {
                poolSql_1.con.close();
            } catch (SQLException ex) {
                System.out.println("Error 10.19 Close : " + ex.toString());
            }

            if (null != fichero) {
                fichero.close();
            }
            try {
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void CrearParte1SQL() throws IOException {
        FileWriter fichero = null;
        PrintWriter pw = null;
        try {
            String Registro2 = "";
            poolSql_1.con = poolSql_1.dataSource.getConnection();
            ResultSet rs = poolSql_1.query("select * from SSRegistroTipo1()");
            fichero = new FileWriter(this.ruta, true);
            pw = new PrintWriter(fichero);

            while (rs.next()) {
                pw.println(rs.getString(1) + rs.getString(2) + rs.getString(3)
                        + rs.getString(4) + rs.getString(5) + rs.getString(6) + rs.getString(7)
                        + rs.getString(8) + rs.getString(9) + rs.getString(10) + rs.getString(11)
                        + rs.getString(12) + rs.getString(13) + rs.getString(14) + rs.getString(15)
                        + rs.getString(16) + rs.getString(17)
                        + rs.getString(18) + rs.getString(19) + rs.getString(20) + rs.getString(21)
                        + rs.getString(22));
            }

            System.out.println(Registro2);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
// Nuevamente aprovechamos el finally para 
// asegurarnos que se cierra el fichero. 
            try {
                poolSql_1.con.close();
            } catch (SQLException ex) {
                System.out.println("Error 10.19 Close : " + ex.toString());
            }

            if (null != fichero) {
                fichero.close();
            }
            try {
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void CrearParte2SQL() throws IOException {
        FileWriter fichero = null;
        PrintWriter pw = null;
        try {
            String Registro2 = "";
            poolSql_1.con = poolSql_1.dataSource.getConnection();
            ResultSet rs = poolSql_1.query("select * from  SSRegistroTipo2() order by 4");
            fichero = new FileWriter(this.ruta, true);
            pw = new PrintWriter(fichero);

            while (rs.next()) {
                pw.println(rs.getString(1) + rs.getString(2) + rs.getString(3)
                        + rs.getString(4) + rs.getString(5) + rs.getString(6) + rs.getString(7)
                        + rs.getString(8) + rs.getString(9) + rs.getString(10) + rs.getString(11)
                        + rs.getString(12) + rs.getString(13) + rs.getString(14) + rs.getString(15)
                        + rs.getString(16) + rs.getString(17)
                        + rs.getString(18) + rs.getString(19) + rs.getString(20) + rs.getString(21)
                        + rs.getString(22) + rs.getString(23) + rs.getString(24) + rs.getString(25)
                        + rs.getString(26) + rs.getString(27) + rs.getString(28) + rs.getString(29)
                        + rs.getString(30) + rs.getString(31) + rs.getString(32) + rs.getString(33)
                        + rs.getString(34) + rs.getString(35) + rs.getString(36) + rs.getString(37)
                        + rs.getString(38) + rs.getString(39) + rs.getString(40) + rs.getString(41)
                        + rs.getString(42) + rs.getString(43) + rs.getString(44) + rs.getString(45)
                        + rs.getString(46) + rs.getString(47) + rs.getString(48) + rs.getString(49)
                        + rs.getString(50) + rs.getString(51) + rs.getString(52) + rs.getString(53)
                        + rs.getString(54) + rs.getString(55) + rs.getString(56) + rs.getString(57)
                        + rs.getString(58) + rs.getString(59) + rs.getString(60) + rs.getString(61)
                        + rs.getString(62) + rs.getString(63) + rs.getString(64) + rs.getString(65)
                        + rs.getString(66) + rs.getString(67) + rs.getString(68) + rs.getString(69)
                        + rs.getString(70) + rs.getString(71) + rs.getString(72) + rs.getString(73)
                        + rs.getString(74) + rs.getString(75) + rs.getString(76) + rs.getString(77)
                        + rs.getString(78) + rs.getString(79) + rs.getString(80) + rs.getString(81)
                        + rs.getString(82) + rs.getString(83) + rs.getString(84) + rs.getString(85)
                        + rs.getString(86) + rs.getString(87) + rs.getString(88) + rs.getString(89)
                        + rs.getString(90) + rs.getString(91) + rs.getString(92) + rs.getString(93)
                        + rs.getString(94) + rs.getString(95));
            }

            System.out.println(Registro2);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
// Nuevamente aprovechamos el finally para 
// asegurarnos que se cierra el fichero. 
            try {
                poolSql_1.con.close();
            } catch (SQLException ex) {
                System.out.println("Error 10.19 Close : " + ex.toString());
            }

            if (null != fichero) {
                fichero.close();
            }
            try {
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void CrearParte3SQL() throws IOException {
        FileWriter fichero = null;
        PrintWriter pw = null;
        try {
            String Registro2 = "";
            poolSql_1.con = poolSql_1.dataSource.getConnection();
            ResultSet rs = poolSql_1.query("select * from  SSRegistroTipo3()");
            fichero = new FileWriter(this.ruta, true);
            pw = new PrintWriter(fichero);

            while (rs.next()) {
                pw.println(rs.getString(1) + rs.getString(2) + rs.getString(3)
                        + rs.getString(4) + rs.getString(5) + rs.getString(6) + rs.getString(7)
                        + rs.getString(8) + rs.getString(9) + rs.getString(10) + rs.getString(11)
                        + rs.getString(12) + rs.getString(13) + rs.getString(14)
                        + rs.getString(15) + rs.getString(16));
            }

            System.out.println(Registro2);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
// Nuevamente aprovechamos el finally para 
// asegurarnos que se cierra el fichero. 
            try {
                poolSql_1.con.close();
            } catch (SQLException ex) {
                System.out.println("Error 10.19 Close : " + ex.toString());
            }

            if (null != fichero) {
                fichero.close();
            }
            try {
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void CrearParte4SQL() throws IOException {
        FileWriter fichero = null;
        PrintWriter pw = null;
        try {
            String Registro2 = "";
            poolSql_1.con = poolSql_1.dataSource.getConnection();
            ResultSet rs = poolSql_1.query("select * from SSRegistroTipo4()");
            fichero = new FileWriter(this.ruta, true);
            pw = new PrintWriter(fichero);

            while (rs.next()) {
                pw.println(rs.getString(1) + rs.getString(2) + rs.getString(3)
                        + rs.getString(4) + rs.getString(5) + rs.getString(6) + rs.getString(7)
                        + rs.getString(8) + rs.getString(9) + rs.getString(10) + rs.getString(11)
                        + rs.getString(12) + rs.getString(13) + rs.getString(14)
                        + rs.getString(15) + rs.getString(16) + rs.getString(17) + rs.getString(18)
                        + rs.getString(19) + rs.getString(20) + rs.getString(21) + rs.getString(22)
                        + rs.getString(23) + rs.getString(24) + rs.getString(25));
            }

            System.out.println(Registro2);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                poolSql_1.con.close();
            } catch (SQLException ex) {
                System.out.println("Error 10.19 Close : " + ex.toString());
            }

            if (null != fichero) {
                fichero.close();
            }
            try {
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void CrearParte5SQL() throws IOException {
        FileWriter fichero = null;
        PrintWriter pw = null;
        try {
            String Registro2 = "";
            poolSql_1.con = poolSql_1.dataSource.getConnection();
            ResultSet rs = poolSql_1.query("select * from SSRegistroTipo5()");
            fichero = new FileWriter(this.ruta, true);
            pw = new PrintWriter(fichero);

            while (rs.next()) {
                pw.println(rs.getString(1) + rs.getString(2) + rs.getString(3)
                        + rs.getString(4) + rs.getString(5) + rs.getString(6) + rs.getString(7)
                        + rs.getString(8) + rs.getString(9) + rs.getString(10) + rs.getString(11)
                        + rs.getString(12) + rs.getString(13) + rs.getString(14)
                        + rs.getString(15) + rs.getString(16) + rs.getString(17) + rs.getString(18)
                );
            }

            System.out.println(Registro2);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                poolSql_1.con.close();
            } catch (SQLException ex) {
                System.out.println("Error 10.19 Close : " + ex.toString());
            }

            if (null != fichero) {
                fichero.close();
            }
            try {
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void CrearParte6SQL() throws IOException {
        FileWriter fichero = null;
        PrintWriter pw = null;
        try {
            String Registro2 = "";
            poolSql_1.con = poolSql_1.dataSource.getConnection();
            ResultSet rs = poolSql_1.query("select * from SSRegistroTipo6()");
            fichero = new FileWriter(this.ruta, true);
            pw = new PrintWriter(fichero);

            while (rs.next()) {
                pw.println(rs.getString(1) + rs.getString(2) + rs.getString(3)
                        + rs.getString(4) + rs.getString(5) + rs.getString(6) + rs.getString(7)
                        + rs.getString(8) + rs.getString(9) + rs.getString(10));
            }

            System.out.println(Registro2);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                poolSql_1.con.close();
            } catch (SQLException ex) {
                System.out.println("Error 10.19 Close : " + ex.toString());
            }

            if (null != fichero) {
                fichero.close();
            }
            try {
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void CrearParte7SQL() throws IOException {
        FileWriter fichero = null;
        PrintWriter pw = null;
        try {
            String Registro2 = "";
            poolSql_1.con = poolSql_1.dataSource.getConnection();
            ResultSet rs = poolSql_1.query("select * from SSRegistroTipo7()");
            fichero = new FileWriter(this.ruta, true);
            pw = new PrintWriter(fichero);

            while (rs.next()) {
                pw.println(rs.getString(1) + rs.getString(2) + rs.getString(3)
                        + rs.getString(4) + rs.getString(5) + rs.getString(6) + rs.getString(7)
                        + rs.getString(8) + rs.getString(9));
            }

            System.out.println(Registro2);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                poolSql_1.con.close();
            } catch (SQLException ex) {
                System.out.println("Error 10.19 Close : " + ex.toString());
            }

            if (null != fichero) {
                fichero.close();
            }
            try {
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void CrearParte8SQL() throws IOException {
        FileWriter fichero = null;
        PrintWriter pw = null;
        try {
            String Registro2 = "";
            poolSql_1.con = poolSql_1.dataSource.getConnection();
            ResultSet rs = poolSql_1.query("select * from SSRegistroTipo8()");
            fichero = new FileWriter(this.ruta, true);
            pw = new PrintWriter(fichero);

            while (rs.next()) {
                pw.println(rs.getString(1) + rs.getString(2) + rs.getString(3)
                        + rs.getString(4) + rs.getString(5) + rs.getString(6) + rs.getString(7)
                        + rs.getString(8) + rs.getString(9));
            }

            System.out.println(Registro2);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                poolSql_1.con.close();
            } catch (SQLException ex) {
                System.out.println("Error 10.19 Close : " + ex.toString());
            }

            if (null != fichero) {
                fichero.close();
            }
            try {
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void CrearParte9SQL() throws IOException {
        FileWriter fichero = null;
        PrintWriter pw = null;
        try {
            String Registro2 = "";
            poolSql_1.con = poolSql_1.dataSource.getConnection();
            ResultSet rs = poolSql_1.query("select * from SSRegistroTipo9()");
            fichero = new FileWriter(this.ruta, true);
            pw = new PrintWriter(fichero);

            while (rs.next()) {
                pw.println(rs.getString(1) + rs.getString(2) + rs.getString(3)
                        + rs.getString(4) + rs.getString(5) + rs.getString(6) + rs.getString(7)
                        + rs.getString(8) + rs.getString(9));
            }

            System.out.println(Registro2);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                poolSql_1.con.close();
            } catch (SQLException ex) {
                System.out.println("Error 10.19 Close : " + ex.toString());
            }

            if (null != fichero) {
                fichero.close();
            }
            try {
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void CrearParte10SQL() throws IOException {
        FileWriter fichero = null;
        PrintWriter pw = null;
        try {
            String Registro2 = "";
            poolSql_1.con = poolSql_1.dataSource.getConnection();
            ResultSet rs = poolSql_1.query("select * from SSRegistroTipo10()");
            fichero = new FileWriter(this.ruta, true);
            pw = new PrintWriter(fichero);

            while (rs.next()) {
                pw.println(rs.getString(1) + rs.getString(2) + rs.getString(3)
                        + rs.getString(4) + rs.getString(5) + rs.getString(6) + rs.getString(7)
                        + rs.getString(8) + rs.getString(9));
            }

            System.out.println(Registro2);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                poolSql_1.con.close();
            } catch (SQLException ex) {
                System.out.println("Error 10.19 Close : " + ex.toString());
            }

            if (null != fichero) {
                fichero.close();
            }
            try {
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void CrearParte11SQL() throws IOException {
        FileWriter fichero = null;
        PrintWriter pw = null;
        try {
            String Registro2 = "";
            poolSql_1.con = poolSql_1.dataSource.getConnection();
            ResultSet rs = poolSql_1.query("select * from SSRegistroTipo11()");
            fichero = new FileWriter(this.ruta, true);
            pw = new PrintWriter(fichero);

            while (rs.next()) {
                pw.println(rs.getString(1) + rs.getString(2) + rs.getString(3)
                        + rs.getString(4) + rs.getString(5) + rs.getString(6) + rs.getString(7)
                        + rs.getString(8) + rs.getString(9) + rs.getString(10) + rs.getString(11) + rs.getString(12)
                        + rs.getString(13) + rs.getString(14));
            }

            System.out.println(Registro2);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                poolSql_1.con.close();
            } catch (SQLException ex) {
                System.out.println("Error 10.19 Close : " + ex.toString());
            }

            if (null != fichero) {
                fichero.close();
            }
            try {
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public void Modificar() throws IOException {
        System.out.println("iniciamos");
        FileWriter fichero = null;
        PrintWriter pw = null;
        try {
            fichero = new FileWriter("src/archivo.txt", true);
            pw = new PrintWriter(fichero);
            int i = 0;
            for (; i < 10; i++) {
                pw.println("Linea " + i);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
// Nuevamente aprovechamos el finally para 
// asegurarnos que se cierra el fichero. 
            if (null != fichero) {
                fichero.close();
            }
            try {
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

    }
}
