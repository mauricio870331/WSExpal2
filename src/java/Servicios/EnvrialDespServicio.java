/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servicios;

import Conecion.sqlServer10_1;
import java.io.File;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 * REST Web Service
 *
 * @author administrador
 */
@Path("generic")
public class EnvrialDespServicio {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of EnvrialDespServicio
     */
    public EnvrialDespServicio() {
    }

    /**
     * Retrieves representation of an instance of Servicios.EnvrialDespServicio
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        try {
            return pdf();
        } catch (SQLException | JRException ex) {
            return ex.getMessage();
        }
    }

    /**
     * PUT method for updating or creating an instance of EnvrialDespServicio
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }

    static CallableStatement cstmt;
    static PreparedStatement pstm;

    //sqlServer20_55 pool = new sqlServer20_55();
    sqlServer10_1 pool = new sqlServer10_1();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
    SimpleDateFormat sdfc = new SimpleDateFormat("dd");
    ArrayList<String> listCorreos = new ArrayList();
    Date date = new Date();

    public String pdf() throws SQLException, JRException {
        String msn = "No hay desprendibles para enviar";
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, -1);
//        System.out.println("sdfc.format(date) " + sdf.format(c.getTime()));
//        System.out.println("sdfc.format(date) " + sdf.format(date));
        if (sdfc.format(date).equals("07") || sdfc.format(date).equals("08") || sdfc.format(date).equals("10")) {
            listCorreos.clear();
            JasperReport jasperReport;
            JasperPrint jasperPrint;
            try {
                //se carga el reporte

                Map parametros = new HashMap();
                String ruta = "C:\\Users\\administrador.EXPRESOPALMIRA\\Documents\\NetBeansProjects\\WSExpal\\src\\java\\reports\\";
                pool.con = pool.dataSource.getConnection();

                String consulta = "select top 1 u.documento, u.correo, u.periodo "
                        + "from usersToConsultas u "
                        + "where recibirCorreos = 'S' and enviado = 'N' and periodo = " + Integer.parseInt(sdf.format(c.getTime())) + " "
                        + "and (select count(documento) from tbl_consultasEmpleados where documento = u.documento and aniomes = " + Integer.parseInt(sdf.format(c.getTime())) + ") > 0 ";
                System.out.println("consulta " + consulta);
                ResultSet rs = pool.query(consulta);

                while (rs.next()) {
                    String doc = rs.getString(1);
                    String correo = rs.getString(2);
                    String rutaDoc = "C:\\war\\desprendibles\\" + doc + ".pdf";
                    System.out.println("rutaDoc "+rutaDoc);
                    listCorreos.add(doc + "," + correo.replace(" ", "") + "," + rutaDoc);
                    System.out.println(doc + "," + correo.replace(" ", "") + "," + rutaDoc);
                    parametros.put("periodoIni", sdf.format(date));
                    parametros.put("periodoFin", sdf.format(date));
                    parametros.put("ruta", ruta);
                    parametros.put("documento", doc);
                    String reporte = ruta + "DesprendiblesAll.jasper";
                    jasperReport = (JasperReport) JRLoader.loadObjectFromFile(reporte);
                    //             
                    jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, pool.con);
                    //se crea el archivo PDF
                    JasperExportManager.exportReportToPdfFile(jasperPrint, rutaDoc);
                    String prepareupdate = "update usersToConsultas set periodo = '" + getNuevoPeriodo(rs.getString(3)) + "', "
                            + "enviado = 'S' where documento = '" + doc + "'";

                    System.out.println("prepareupdate " + prepareupdate);
                    int result = pool.transaccion(prepareupdate);
                    System.out.println("resu = " + result);

                    msn = "Todo Ok";
                }
//                if (!msn.equals("")) {
//                    msn = enviarMails();
//                }
            } catch (JRException ex) {
                msn = ex.getMessage() + " * Por aqui linea 156";
            }
        } else {
            msn = "Aun no es fecha de envio";
        }
        return msn;
    }

    public String getNuevoPeriodo(String periodo) {
        String mes = periodo.substring(4);
        String year = periodo.substring(0, 4);
        String nuevoPeriodo = "";
        System.out.println(" nuevoPeriodo " + mes);
        if (mes.equals("12")) {
            mes = "01";
            int y = Integer.parseInt(year) + 1;
            year = Integer.toString(y);
            nuevoPeriodo = year + "" + mes;
        } else {
            int m = Integer.parseInt(mes);
            if (m >= 10) {
                m += 1;
                mes = Integer.toString(m);
            } else {
                m += 1;
                mes = "0" + Integer.toString(m);
            }
            nuevoPeriodo = year + "" + mes;
        }
        return nuevoPeriodo;
    }

    public void enviarConGMail(String destinatario, String asunto, String cuerpo, String documento) throws MessagingException {
        // Esto es lo que va delante de @gmail.com en tu cuenta de correo. Es el remitente también.
        String remitente = "desarrollo1expresopalmira@gmail.com";  //Para la dirección nomcuenta@gmail.com
        Properties props = System.getProperties();
        props.put("mail.smtp.host", "smtp.gmail.com");  //El servidor SMTP de Google
        props.put("mail.smtp.user", remitente);
        props.put("mail.smtp.clave", "1113626301");    //La clave de la cuenta
        props.put("mail.smtp.auth", "true");    //Usar autenticación mediante usuario y clave
        props.put("mail.smtp.starttls.enable", "true"); //Para conectar de manera segura al servidor SMTP
        props.put("mail.smtp.port", "587"); //El puerto SMTP seguro de Google

        Session session = Session.getDefaultInstance(props);
        BodyPart texto = new MimeBodyPart();
        texto.setText(cuerpo);
        BodyPart adjunto = new MimeBodyPart();
        adjunto.setDataHandler(new DataHandler(new FileDataSource("Z:/" + documento + ".pdf")));
        adjunto.setFileName(documento + ".pdf");
        MimeMultipart multiParte = new MimeMultipart();
        multiParte.addBodyPart(texto);
        multiParte.addBodyPart(adjunto);
        MimeMessage message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(remitente));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));   //Se podrían añadir varios de la misma manera
            message.setSubject(asunto);
            message.setContent(multiParte);
            Transport transport = session.getTransport("smtp");
            transport.connect("smtp.gmail.com", remitente, "1113626301");
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (MessagingException me) {
            System.out.println("error mail = " + me);
        }
    }

    public void enviarOutlook(String documento, String correo) {
        try {
            pool.con = pool.dataSource.getConnection();

            cstmt = pool.con.prepareCall("{call EnviarEmailConAdjuntos (?,?,?,?,?)}");
            cstmt.setString(1, "Email Sistemas");
            cstmt.setString(2, correo);
            cstmt.setString(3, "Desprendibles de Pago");
            cstmt.setString(4, "Expreso Palmira Adjunta los desprendibles de pago..!");
            cstmt.setString(5, "\\\\192.168.10.1\\ExcelParaEmail\\" + documento + ".pdf");
            cstmt.execute();

        } catch (SQLException ex) {
            System.out.println("error ex :" + ex.toString());
        } finally {
            try {
                pool.con.close();
            } catch (SQLException ex) {
                System.out.println("error " + ex);
            }
        }
    }

    public String enviarMails() {
        String msn = "No se enviaron los emails";
        int enviado = 0;
        for (String obj : listCorreos) {
            String partes[] = obj.split(",");
            String partCorreo[] = partes[1].split("@");
            if (partCorreo[1].contains("gmail")) {
                try {
                    enviarConGMail(partes[1], "Desprendibles de Pago", "Expreso Palmira Adjunta los desprendibles de pago..!", partes[0]);
                    enviado += 1;
                    msn = "Se enviaron " + enviado + " emails";
                } catch (MessagingException ex) {
                    msn = ex.getMessage();
                }
            } else {
                enviado += 1;
                msn = "Se enviaron " + enviado + " emails";
                enviarOutlook(partes[0], partes[1]);
            }
        }

        listCorreos.stream().map((obj) -> obj.split(",")).forEach((partes) -> {
            EliminarDocumentos(partes[2]);
        });
        return msn;
    }

    public void EliminarDocumentos(String ruta) {
        File fichero = new File(ruta);
        if (fichero.delete()) {
            System.out.println("Eliminado");
        } else {
            System.out.println("no se eleimino");
        }
    }
}
