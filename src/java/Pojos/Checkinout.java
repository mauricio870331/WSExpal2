/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pojos;

/**
 *
 * @author clopez
 */
public class Checkinout {
    private int logId;
    private int Userid;
    private String CheckTime;
    private int CheckType;
    private int Sensorid;
    private int WorkType;
    private int AttFlag;
    private boolean Checked;
    private boolean Exported;
    private boolean OpenDoorFlag;
    private String tipoLlegada;
    private boolean transmitido;

    public Checkinout() {
    }

    @Override
    public String toString() {
        return "Checkinout{" + "logId=" + logId + ", Userid=" + Userid + ", CheckTime=" + CheckTime + ",tipoLlegada=" + tipoLlegada + ", transmitido=" + transmitido + '}';
    }
       

    public int getLogId() {
        return logId;
    }

    public void setLogId(int logId) {
        this.logId = logId;
    }

    public int getUserid() {
        return Userid;
    }

    public void setUserid(int Userid) {
        this.Userid = Userid;
    }

    public String getCheckTime() {
        return CheckTime;
    }

    public void setCheckTime(String CheckTime) {
        this.CheckTime = CheckTime;
    }

    public int getCheckType() {
        return CheckType;
    }

    public void setCheckType(int CheckType) {
        this.CheckType = CheckType;
    }

    public int getSensorid() {
        return Sensorid;
    }

    public void setSensorid(int Sensorid) {
        this.Sensorid = Sensorid;
    }

    public int getWorkType() {
        return WorkType;
    }

    public void setWorkType(int WorkType) {
        this.WorkType = WorkType;
    }

    public int getAttFlag() {
        return AttFlag;
    }

    public void setAttFlag(int AttFlag) {
        this.AttFlag = AttFlag;
    }

    public boolean isChecked() {
        return Checked;
    }

    public void setChecked(boolean Checked) {
        this.Checked = Checked;
    }

    public boolean isExported() {
        return Exported;
    }

    public void setExported(boolean Exported) {
        this.Exported = Exported;
    }

    public boolean isOpenDoorFlag() {
        return OpenDoorFlag;
    }

    public void setOpenDoorFlag(boolean OpenDoorFlag) {
        this.OpenDoorFlag = OpenDoorFlag;
    }

    public String getTipoLlegada() {
        return tipoLlegada;
    }

    public void setTipoLlegada(String tipoLlegada) {
        this.tipoLlegada = tipoLlegada;
    }

    public boolean getTransmitido() {
        return transmitido;
    }

    public void setTransmitido(boolean transmitido) {
        this.transmitido = transmitido;
    }
    
    
}
