/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helper;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author home
 */
public class Date extends GregorianCalendar{
    private int gestion;
    private int mes;
    private int dia;

    public int getGestion() {
        return gestion;
    }

    public void setGestion(int gestion) {
        this.gestion = gestion;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public Date(int year, int mes, int dia) {
        super(year,mes,dia);
        this.gestion = year;
        this.mes = mes;
        this.dia = dia;
    }

    public Date() {
        
    }
    
    public Date(String fecha){
        super(Integer.parseInt(fecha.split("-")[0])
                ,Integer.parseInt(fecha.split("-")[1])-1
                ,Integer.parseInt(fecha.split("-")[2]));
        String[] cadena = fecha.split("-");
        this.gestion = Integer.parseInt(cadena[0]);
        this.mes = Integer.parseInt(cadena[1]);
        this.dia = Integer.parseInt(cadena[2]);
    }

    @Override
    public String toString() {
        return gestion + "-" + mes + "-" + dia;
    }
    
    
    public int antiguedad(){
        Date newActual = fechaActual();
        float mes = newActual.getMes();
        float meses12 = 12;
        float meses =mes/meses12;
        float dias360 = 360;
        float dia = newActual.getDia();
        float dias = dia/dias360;
        float total = newActual.getGestion()+meses+dias;
        mes = this.mes;
        meses = mes/meses12;
        dia = this.dia;
        dias = dia/dias360;
        int antiguedad=(int)(total-(this.gestion+meses+dias));        
        return antiguedad ;
    }
    
    private long conversionDias(Date fecha){
        long time = fecha.getTime().getTime();
        return (long)Math.floor(time/(1000*60*60*24));
    }
    public Date fechaActual(){
        Calendar c = new GregorianCalendar();
        Date actual = new Date();
        int yearAcutal = Integer.parseInt(Integer.toString(c.get(Calendar.YEAR)));
        int mesAcutal = 1+Integer.parseInt(Integer.toString(c.get(Calendar.MONTH)));
        int diaAcutal = Integer.parseInt(Integer.toString(c.get(Calendar.DATE)));
        Date newActual = new Date(yearAcutal, mesAcutal, diaAcutal);
        return newActual;
    }
    public String fechaParaCodigo(){
        return mes+""+gestion;
    }
    public String fechaImpresion(){
        if(gestion==1500 && mes==1 && dia==1){
            return "NINGUNO";
        }else{
            return  dia+"/"+mes+"/"+gestion;
        }
    }
}
