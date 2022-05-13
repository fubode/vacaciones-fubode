package Helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Calendario {

    private int gestion;
    private int mes;
    private int dia;

    public Calendario(int gestion, int mes) {
        this.gestion = gestion;
        this.mes = mes;
        this.dia = 1;
    }

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

    public List<List<Map<String, String>>> dias_calendario() {
        List<List<Map<String, String>>> lista = new LinkedList<>();
        int diasMes = diasMes(mes);
        List<Map<String, String>> semana = new LinkedList<>();
        int sabados = 0;
        boolean laboral = true;
        for (int i = 1; i <= diasMes; i++) {
            String fecha = gestion + "-" + mes + "-" + i;
            String dia = diaSemana(fecha);
            Map<String, String> dato = new HashMap<>();
            dato.put("dia", dia);
            dato.put("diaNumeral", String.valueOf(i));
            dato.put("fecha", fecha);
            dato.put("descripcion", "");
            dato.put("entidad", "");
            if((dia == "Sabado") && sabados<2){
                dato.put("tipo", "NO_LABORAL");
                sabados++;
            }else{
                dato.put("tipo", "");
            }
            if((dia == "Domingo")){
                dato.put("tipo", "NO_LABORAL");
            }
            if (dia == "Domingo" || i==diasMes) {
                semana.add(dato);
                lista.add(semana);
                semana = new LinkedList<>();
            } else {
                semana.add(dato);
            }
        }
        List<List<Map<String, String>>> listaArreglada = listaArreglada(lista);
        return listaArreglada;
    }

    private String diaSemana(String fecha) {
        String Valor_dia = null;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date fechaActual = null;
        try {
            fechaActual = df.parse(fecha);
        } catch (ParseException e) {
        }
        GregorianCalendar fechaCalendario = new GregorianCalendar();
        fechaCalendario.setTime(fechaActual);
        int diaSemana = fechaCalendario.get(Calendar.DAY_OF_WEEK);
        if (diaSemana == 1) {
            Valor_dia = "Domingo";
        } else if (diaSemana == 2) {
            Valor_dia = "Lunes";
        } else if (diaSemana == 3) {
            Valor_dia = "Martes";
        } else if (diaSemana == 4) {
            Valor_dia = "Miercoles";
        } else if (diaSemana == 5) {
            Valor_dia = "Jueves";
        } else if (diaSemana == 6) {
            Valor_dia = "Viernes";
        } else if (diaSemana == 7) {
            Valor_dia = "Sabado";
        }
        return Valor_dia;
    }

    public int diasMes(int mes) {
        int dias = 0;
        switch (mes) {
            case 1:
                dias = 31;
                break;
            case 2:
                dias = bisiesto();
                break;
            case 3:
                dias = 31;
                break;
            case 4:
                dias = 30;
                break;
            case 5:
                dias = 31;
                break;
            case 6:
                dias = 30;
                break;
            case 7:
                dias = 31;
                break;
            case 8:
                dias = 31;
                break;
            case 9:
                dias = 30;
                break;
            case 10:
                dias = 31;
                break;
            case 11:
                dias = 30;
                break;
            case 12:
                dias = 31;
                break;

        }
        return dias;
    }

    private int bisiesto() {
        int dias = 0;
        GregorianCalendar calendar = new GregorianCalendar();
        if (calendar.isLeapYear(gestion)) {
            dias = 29;
        } else {
            dias = 28;
        }
        return dias;
    }

    private List<List<Map<String, String>>> listaArreglada(List<List<Map<String, String>>> listas) {
        List<Map<String, String>> lista =  listas.get(0);
        List<Map<String, String>> listaVacio =  new LinkedList<>();
        int tamanio = lista.size();
        if(tamanio!=7){
            for (int i = 0; i < (7-tamanio); i++) {
                Map<String, String> dato = new HashMap<>();
                dato.put("dia", "");
                dato.put("diaNumeral","");
                dato.put("fecha", "");
                listaVacio.add(dato);
            }
            for (int i = 0; i < tamanio; i++) {
                listaVacio.add(lista.get(i));
            }
            listas.set(0, listaVacio);
        }
        return listas;
    }
    
    public ArrayList<Map<String,String>> meses(){
        ArrayList<Map<String,String>> meses = new ArrayList<>();
        Map<String,String> mes = new HashMap<>();
        mes.put("mes","ENERO");
        mes.put("valor", "1");
        meses.add(mes);
        
        mes = new HashMap<>();
        mes.put("mes","FEBRERO");
        mes.put("valor", "2");
        meses.add(mes);
        
        mes = new HashMap<>();
        mes.put("mes","MARZO");
        mes.put("valor", "3");
        meses.add(mes);
        
        mes = new HashMap<>();
        mes.put("mes","ABRIL");
        mes.put("valor", "4");
        meses.add(mes);
        
        mes = new HashMap<>();
        mes.put("mes","MAYO");
        mes.put("valor", "5");
        meses.add(mes);
        
        mes = new HashMap<>();
        mes.put("mes","JUNIO");
        mes.put("valor", "6");
        meses.add(mes);
        
        mes = new HashMap<>();
        mes.put("mes","JULIO");
        mes.put("valor", "7");
        meses.add(mes);
        
        mes = new HashMap<>();
        mes.put("mes","AGOSTO");
        mes.put("valor", "8");
        meses.add(mes);
        
        mes = new HashMap<>();
        mes.put("mes","SEPTIEMBRE");
        mes.put("valor", "9");
        meses.add(mes);
        
        mes = new HashMap<>();
        mes.put("mes","OCTUBRE");
        mes.put("valor", "10");
        meses.add(mes);
        
        mes = new HashMap<>();
        mes.put("mes","NOVIEMBRE");
        mes.put("valor", "11");
        meses.add(mes);
        
        mes = new HashMap<>();
        mes.put("mes","DICIEMBRE");
        mes.put("valor", "12");
        meses.add(mes);
        return meses;
    }
    
    public ArrayList<Integer> gestiones(){
        ArrayList<Integer> gestiones = new ArrayList<>();
        int inicio = 2000;
        int fin = 2050;
        for (int  i= inicio;  i<= fin; i++) {
            gestiones.add(i);
        }
        return gestiones;
    }
}
