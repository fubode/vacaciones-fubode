package Controlador;

import Helper.Calendario;
import Helper.Date;
import Modelo.Conexion;
import Modelo.Usuario;
import java.text.ParseException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class DAOGeneral extends Conexion {

    public DAOGeneral(Usuario usuario) {
        super(usuario);
    }

    public DAOGeneral() {
    }

    public JSONObject diasNoLaborables(String fecha_salida, String fecha_retorno, String turno_salida, String turno_retorno, String codigo) {

        int say = 0;
        try {
            say = Integer.parseInt(codigo);
        } catch (Exception e) {
            say = codigo_say;
        }
        String mensaje = "";
        JSONObject json = new JSONObject();
        String a = "asd";
        try {
            Date salida = new Date(fecha_salida);
            Date retorno = new Date(fecha_retorno);
            if (esFeriado(salida, codigo) || esFeriado(retorno, codigo)) {
                json.put("mensaje", "NO PUEDE SELECCIONAR UN DIA FERIADO");
                json.put("dias", "");
            } else {
                double descuentoDias = descuentoDias(salida, retorno, say);
                double descuentoTurno = descuentoTurno(turno_salida, turno_retorno, say);
                double cantDias = cantDias(fecha_salida, fecha_retorno);
                double totalDescuento = cantDias - descuentoDias - descuentoTurno;
                
                json.put("mensaje", "EXITO");
                json.put("dias", totalDescuento);
            }
        } catch (Exception e) {

        }
        return json;
    }

    public double vacaciones(int sai) throws JSONException {
        double totalVacaciones = totalVacaciones(sai);
        double vacacionesTomadas = vacacionesTomadas(sai);

        return totalVacaciones - vacacionesTomadas;
    }

    public double totalVacaciones(int sai) throws JSONException {
        List<Map<String, String>> gestiones = gestiones(sai);
        double gestioTotal = 0;
        for (Map<String, String> g : gestiones) {
            gestioTotal += Double.parseDouble(g.get("saldo").toString());
        }
        return gestioTotal;
    }

    public double vacacionesTomadas(int sai) {
        List<Map<String, Object>> funcionarioList;
        String funcionario = "select sum(dias)from solicitud_vacaciones s, funcionario f "
                + "where s.codigo_funcionario=f.codigo_sai and "
                + "s.estado = 'ACEPTADO' and "
                + "s.codigo_funcionario=" + sai;
        try {
            funcionarioList = this.jdbcTemplate.queryForList(funcionario);
            return Integer.parseInt(funcionarioList.get(0).get("sum").toString());
        } catch (Exception e) {
            return 0;
        }
    }

    public List<Map<String, String>> gestiones(int sai) throws JSONException {
        List<Map<String, String>> funcionarioList = new LinkedList<>();
        JSONObject usuario = this.datosFuncionario(sai);
        Date ingreso = new Date(usuario.get("fecha_ingreso").toString());
        int antiguedad = ingreso.antiguedad();
        int gestion = ingreso.getGestion();
        int mes = ingreso.getMes();
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

    private boolean esFeriado(Date fecha, String codigo) {
        boolean esFeriado = false;
        Usuario usuario = new Usuario(codigo_say);
        int entididad = usuario.getCodigoEntidad();

        List<Map<String, Object>> fechas;
        String sql = "SELECT id_fechas, fecha, descripcion_estado, tipo, entidad "
                + "FROM public.fechas "
                + "WHERE fecha='" + fecha.toString() + "'";
        fechas = this.jdbcTemplate.queryForList(sql);
        String detalle = "";
        if (fechas.size() == 1) {
            detalle = fechas.get(0).get("tipo").toString();
            if (detalle.equals("NO_LABORAL")) {
                esFeriado = true;
            }
        } else {
            for (int i = 0; i < fechas.size(); i++) {
                detalle = fechas.get(i).get("tipo").toString();
                if (detalle.equals("NO_LABORAL")) {
                    esFeriado = true;
                    break;
                }
            }
        }
        return esFeriado;
    }

    private double cantDias(String salida, String retorno) throws ParseException{
        Calendario calendario = new Calendario();
        return calendario.dias(salida, retorno).size();
    }
    private double descuentoDias(Date salida, Date retorno, int codigo) throws ParseException {
        double descuentoDias = 0;
        retorno.menosUnDia();
        String listaEntidades = "select fecha,tipo,entidad from fechas where entidad = 0 and fecha>= '" + salida.toString() + "'  and fecha<= '" + retorno.toString() + "' order by fecha";
        String listaEntidad = "select fecha,tipo,e.nombre_entidad from fechas f, entidad e, funcionario fun where "
                + "fecha>= '" + salida.toString() + "'  and fecha<= '" + retorno.toString() + "' "
                + "and  fun.entidad=e.codigo_entidad "
                + "and e.codigo_entidad=f.entidad "
                + "and fun.codigo_sai=" + codigo + " "
                + "order by fecha";
        List<Map<String, Object>> entidades = this.jdbcTemplate.queryForList(listaEntidades);
        List<Map<String, Object>> entidad = this.jdbcTemplate.queryForList(listaEntidad);
        Calendario calendario = new Calendario();
        List<Map<String, Object>> dias = calendario.dias(salida.toString(), retorno.toString());

        for (int i = 0; i < entidades.size(); i++) {
            String fecha = entidades.get(i).get("fecha").toString();
            for (int j = 0; j < dias.size(); j++) {
                String fechaDia = dias.get(j).get("fecha").toString();
                if (calendario.sonIguales(fecha, fechaDia)) {
                    String tipo = entidades.get(i).get("tipo").toString();
                    switch (tipo) {
                        case "NO_LABORAL":
                            dias.get(j).put("descuento", 1);
                            dias.get(j).put("tipo", "NO_LABORAL");
                            break;
                        case "TARDE":
                            dias.get(j).put("descuento", 0.5);
                            dias.get(j).put("tipo", "TARDE");
                            break;
                        case "MANANA":
                            dias.get(j).put("descuento", 0.5);
                            dias.get(j).put("tipo", "MANANA");
                            break;
                    }
                    break;
                }
            }
        }

        for (int i = 0; i < entidad.size(); i++) {
            String fecha = entidad.get(i).get("fecha").toString();
            for (int j = 0; j < dias.size(); j++) {
                String fechaDia = dias.get(j).get("fecha").toString();
                if (calendario.sonIguales(fecha, fechaDia)) {
                    String tipo = entidad.get(i).get("tipo").toString();
                    switch (tipo) {
                        case "NO_LABORAL":
                            dias.get(j).put("descuento", 1);
                            dias.get(j).put("tipo", "NO_LABORAL");
                            break;
                        case "TARDE":
                            dias.get(j).put("descuento", 0.5);
                            dias.get(j).put("tipo", "TARDE");
                            break;
                        case "MANANA":
                            dias.get(j).put("descuento", 0.5);
                            dias.get(j).put("tipo", "MANANA");
                            break;
                    }
                    break;
                }
            }
        }

        for (int i = 0; i < dias.size(); i++) {
            double descuento = Double.parseDouble(dias.get(i).get("descuento").toString());
            descuentoDias = descuentoDias + descuento;
        }
        return descuentoDias;
    }

    private double descuentoTurno(String turno_salida, String turno_retorno, int say) {
        double descuentoTurno = 0;
        if (turno_salida.equals("TARDE")) {
            descuentoTurno = 0.5;
        }

        if (turno_retorno.equals("TARDE")) {
            descuentoTurno = descuentoTurno + 0.5;
        } else {
            if (turno_retorno.equals("MAÑANA")) {
                descuentoTurno = descuentoTurno + 1;
            }
        }
        return descuentoTurno;
    }
}
