/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import Helper.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author GEEK STORE
 */
class UsuarioAD {
    
    private Date fecha_ingreso;
    private double tomadas;
    private String jefe;
    
    public UsuarioAD(String fecha_ingreso, String tomadas, String jefe) {
        this.fecha_ingreso = new Date(fecha_ingreso);
        this.tomadas = Double.parseDouble(tomadas);
        this.jefe = jefe;
    }

    public int antiguedad() {
        return fecha_ingreso.antiguedad();
    }

    public String nombreSupervisor() {
        return jefe;
    }

    public double vacacionesTomadas() {
        return tomadas;
    }

    public double vacacionesCumplidas() {
        double vacacionesCumplidas = 0;
        List<Map<String, String>> gestiones = gestiones();
        for (Map<String, String> gestion : gestiones) {
            vacacionesCumplidas = vacacionesCumplidas + Double.parseDouble(gestion.get("saldo"));
        }
        return vacacionesCumplidas;
    }

    public boolean hayExcedentes() {
        int antiguedad = antiguedad();
        double saldoTotalCumulado = saldoVacaciones();
        int maximo = 0;
        boolean hayExcedentes = false;
        if (antiguedad > 0 && antiguedad <= 4) {
            maximo = 30;
        } else {
            if (antiguedad >= 5 && antiguedad <= 9) {
                maximo = 40;
            } else {
                if (antiguedad >= 10) {
                    maximo = 60;
                }
            }
        }
        if(saldoTotalCumulado >= maximo){
            hayExcedentes = true;
        }else{
            if (saldoTotalCumulado==0) {
                hayExcedentes = false;
            }
        }        
        return hayExcedentes;
    }

    public double saldoVacaciones() {
        return vacacionesCumplidas() - vacacionesTomadas();
    }
    
     public List<Map<String, String>> gestiones() {
        List<Map<String, String>> funcionarioList = new LinkedList<>();
        int antiguedad = fecha_ingreso.antiguedad();
        int gestion = fecha_ingreso.getGestion();
        int mes = fecha_ingreso.getMes();
        for (int i = 1; i <= antiguedad; i++) {
            Map<String, String> map = new HashMap<>();
            if (i <= 5 && i > 0) {
                map.put("gestion", ((gestion + i) + "/" + mes));
                map.put("saldo", String.valueOf(15));
                funcionarioList.add(map);
            } else {
                if (i <= 10 && i > 5) {
                    map.put("gestion", ((gestion + i) + "/" + mes));
                    map.put("saldo", String.valueOf(20));
                    funcionarioList.add(map);
                } else {
                    if (i > 10) {
                        map.put("gestion", ((gestion + i) + "/" + mes));
                        map.put("saldo", String.valueOf(30));
                        funcionarioList.add(map);
                    }
                }
            }
        }
        return funcionarioList;
    }
}
