package SQL;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.StringTokenizer;
import java.util.Vector;

import ObjetoChecador.ObjHorario;
import ObjetoChecador.Obj_Dias_Inhabiles;
import ObjetoChecador.Obj_Encargado_De_Solicitudes;
import ObjetoChecador.Obj_Entosal;
import ObjetoChecador.Obj_Mensaje_Personal;
import ObjetoChecador.Obj_Permisos_Checador;
import ObjetoChecador.Obj_Seleccion_Operaciones;

import objeto_fuente_sodas.Obj_Captura_Fuente_Sodas;
import objetos.Obj_Actividad;
import objetos.Obj_Actividad_Asignadas_Nivel_Jerarquico;
import objetos.Obj_Actividades_Por_Proyecto;
import objetos.Obj_Actividades_Relacionadas;
import objetos.Obj_Alimentacion_Cortes;
import objetos.Obj_Alimentacion_Cuadrante;
import objetos.Obj_Alimentacion_De_Vacaciones;
import objetos.Obj_Alimentacion_Por_Denominacion;
import objetos.Obj_Arduino;
import objetos.Obj_Asignacion_Mensajes;
import objetos.Obj_Asistencia_Puntualidad;
import objetos.Obj_Atributos;
import objetos.Obj_Auto_Auditoria;
import objetos.Obj_Auto_Finanzas;
import objetos.Obj_Bono_Complemento_Sueldo;
import objetos.Obj_Conexion_BD;
import objetos.Obj_Configuracion_Sistema;
import objetos.Obj_Cuadrante;
import objetos.Obj_Denominaciones;
import objetos.Obj_Departamento;
import objetos.Obj_Directorios;
import objetos.Obj_Divisa_Y_TipoDeCambio;
import objetos.Obj_Diferencia_Cortes;
import objetos.Obj_Empleado;
import objetos.Obj_Empleados_Cuadrantes;
import objetos.Obj_Equipo_Trabajo;
import objetos.Obj_Establecimiento;
import objetos.Obj_Gen_Code_Bar;
import objetos.Obj_Grupo_De_Vacaciones;
import objetos.Obj_Horario_Empleado3;
import objetos.Obj_Jefatura;
import objetos.Obj_Mensajes;
import objetos.Obj_Nivel_Critico;
import objetos.Obj_Nivel_Jerarquico;
import objetos.Obj_OpRespuesta;
import objetos.Obj_Ponderacion;
import objetos.Obj_Prestamo;
import objetos.Obj_Puesto;
import objetos.Obj_Rango_Prestamos;
import objetos.Obj_Sueldo;
import objetos.Obj_Tabla_De_Vacaciones;
import objetos.Obj_Temporada;
import objetos.Obj_Tipo_Banco;
import objetos.Obj_Horario_Empleado;
import objetos.Obj_Horario_Empleado2;
import objetos.Obj_Usuario;
import objetos.Obj_fuente_sodas_auxf;
import objetos.Obj_fuente_sodas_rh;

public class BuscarSQL {
	
	Connexion con = new Connexion();
	
	public Obj_fuente_sodas_rh buscarautoizacionfs(){
		Obj_fuente_sodas_rh fs_autorizacion = new Obj_fuente_sodas_rh();
		String query = "select autorizar_comparacion_fuente_sodas from tb_autorizaciones";
		try {				
			Statement s = con.conexion().createStatement();
			ResultSet rs = s.executeQuery(query);
			
			while(rs.next())
				fs_autorizacion.setStatus_autorizacion(Boolean.valueOf(rs.getString(1).trim()));
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
			
		return fs_autorizacion;
	}
	
	/*
	public Obj_fuente_sodas_rh buscarautoizacionfs() throws SQLException{
		Obj_fuente_sodas_rh fs_autorizacion = new Obj_fuente_sodas_rh();
		String query = "select autorizar_comparacion_fuente_sodas from tb_autorizaciones ";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			while(rs.next()){
			fs_autorizacion.setStatus_autorizacion(Boolean.valueOf(rs.getString(1)));
//				algo =  (Boolean.valueOf(rs.getString(1)));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
//			return false;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
//		return algo;
		return fs_autorizacion;
	}
	*/
	
	public Obj_Establecimiento Establecimiento(int folio) throws SQLException{
		Obj_Establecimiento establecimiento = new Obj_Establecimiento();
		String query = "select * from tb_establecimiento where folio ="+ folio;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
		    ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				establecimiento.setFolio(rs.getInt("folio"));
				establecimiento.setNombre(rs.getString("nombre").trim());
				establecimiento.setAbreviatura(rs.getString("abreviatura").trim());
				establecimiento.setStatus((rs.getString("status").equals("1"))?true:false);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error");
			return null;
		}
		finally{
			 if (stmt != null) { stmt.close(); }
		}
		return establecimiento;
	}
	
	public boolean nombre_disponible(String nombre){
		String query = "exec sp_existe_empleado '" + nombre + "';";
		boolean disponible = false;
		try {				
			Statement s = con.conexion().createStatement();
			ResultSet rs = s.executeQuery(query);
			
			while(rs.next()){
				disponible = rs.getBoolean(1);
			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
			
		return disponible;
	}
	
	public Obj_Alimentacion_Cortes Folio_Nuevo(String establecimiento){
		Obj_Alimentacion_Cortes folio = new Obj_Alimentacion_Cortes();
		String query = "exec sp_select_folio_corte '" + establecimiento + "';";
		try {				
			Statement s = con.conexion().createStatement();
			ResultSet rs = s.executeQuery(query);
			
			while(rs.next()){
				folio.setFolio_corte(rs.getString("folio_corte"));
			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
			
		return folio;
	}
	
	public Obj_Alimentacion_Cortes Folio_Corte(String folio_corte){
		Obj_Alimentacion_Cortes folio = new Obj_Alimentacion_Cortes();
		String query = "exec sp_select_comprovar_folio_corte '" + folio_corte + "';";
		try {				
			Statement s = con.conexion().createStatement();
			ResultSet rs = s.executeQuery(query);
			
			while(rs.next()){
				folio.setFolio_corte(rs.getString("folio_corte"));
			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
			
		return folio;
	}
	
	public Obj_Alimentacion_Cortes Folio_Corte_Deposito(String folio_corte){
		Obj_Alimentacion_Cortes folio = new Obj_Alimentacion_Cortes();
		String query = "exec sp_select_comprovar_folio_corte_deposito '" + folio_corte + "';";
		try {				
			Statement s = con.conexion().createStatement();
			ResultSet rs = s.executeQuery(query);
			
			while(rs.next()){
				folio.setFolio_corte(rs.getString("folio_corte"));
			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
			
		return folio;
	}
	
	public Obj_Alimentacion_Por_Denominacion Datos_Denominacion(String folio_corte){
		Obj_Alimentacion_Por_Denominacion alimentacion = new Obj_Alimentacion_Por_Denominacion();
		String query = "exec sp_select_datos_alimentacion_denominaciones "+folio_corte;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			while(rs.next()){
				alimentacion.setFolio_corte(rs.getString("folio_corte"));
				alimentacion.setAsignacion(rs.getString("asignacion"));
				alimentacion.setEmpleado(rs.getString("empleado"));
				alimentacion.setFecha(rs.getString("fecha"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return alimentacion;
	}
	
	public Obj_Establecimiento Establecimiento_Nuevo() throws SQLException{
		Obj_Establecimiento establecimiento = new Obj_Establecimiento();
		String query = "select max(folio) as 'Maximo' from tb_establecimiento";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				establecimiento.setFolio(rs.getInt("Maximo"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			 if (stmt != null) { stmt.close(); }
		}
		return establecimiento;
	}
	
	public Obj_Sueldo Sueldo(int folio) throws SQLException{
		Obj_Sueldo sueldo = new Obj_Sueldo();
		String query = "select * from tb_sueldo where folio ="+ folio;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				sueldo.setFolio(rs.getInt("folio"));
				sueldo.setSueldo(rs.getFloat("sueldo"));
				sueldo.setPuesto(rs.getInt("puesto_id"));
				sueldo.setStatus((rs.getString("status").equals("1"))?true:false);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt != null){stmt.close();}
		}
		return sueldo;
	}

	public Obj_Sueldo Sueldo_Nuevo() throws SQLException{
		Obj_Sueldo sueldo = new Obj_Sueldo();
		String query = "select max(folio) as 'Maximo' from tb_sueldo";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				sueldo.setFolio(rs.getInt("Maximo"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return sueldo;
	}
	
	public Obj_Bono_Complemento_Sueldo Bono(int folio) throws SQLException{
		Obj_Bono_Complemento_Sueldo bono = new Obj_Bono_Complemento_Sueldo();
		String query = "select * from tb_bono where folio ="+ folio;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				bono.setFolio(rs.getInt("folio"));
				bono.setBono(rs.getFloat("bono"));
				bono.setAbreviatura(rs.getString("abreviatura").trim());
				bono.setStatus((rs.getString("status").equals("1"))?true:false);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return bono;
	}
	
	public Obj_Bono_Complemento_Sueldo BonoValor(float valor) throws SQLException{
		Obj_Bono_Complemento_Sueldo bono = new Obj_Bono_Complemento_Sueldo();
		String query = "select * from tb_bono where bono = "+ valor;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				bono.setFolio(rs.getInt("folio"));
				bono.setBono(rs.getFloat("bono"));
				bono.setAbreviatura(rs.getString("abreviatura").trim());
				bono.setStatus((rs.getString("status").equals("1"))?true:false);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return bono;
	}
	
	public Obj_Bono_Complemento_Sueldo Bono_Nuevo() throws SQLException{
		Obj_Bono_Complemento_Sueldo bono = new Obj_Bono_Complemento_Sueldo();
		String query = "select max(folio) as 'Maximo' from tb_bono";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				bono.setFolio(rs.getInt("Maximo"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return bono;
	}
	
	public Obj_Puesto Puesto(int folio) throws SQLException{
		Obj_Puesto puesto = new Obj_Puesto();
		String query = "select * from tb_puesto where folio ="+ folio;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				puesto.setFolio(rs.getInt("folio"));
				puesto.setPuesto(rs.getString("nombre").trim());
				puesto.setAbreviatura(rs.getString("abreviatura").trim());
				puesto.setStatus((rs.getString("status").equals("1"))?true:false);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return puesto;
	}
	
	public Obj_Dias_Inhabiles diaInA(int folio) throws SQLException{
		Obj_Dias_Inhabiles diaIA = new Obj_Dias_Inhabiles();
		String query = "select * from tb_dias_inhabiles where folio ="+ folio;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				diaIA.setFolio(rs.getInt("folio"));
				diaIA.setFecha(rs.getString("fecha").trim());
				diaIA.setDescripcion(rs.getString("descripcion").trim());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return diaIA;
	}
	
	public Obj_Atributos Atributos(int folio) throws SQLException{
		Obj_Atributos atrib = new Obj_Atributos();
		String query = "select * from tb_atributo where folio ="+ folio;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				atrib.setFolio(rs.getInt("folio"));
				atrib.setDescripcion(rs.getString("Descripcion").trim());
				atrib.setValor(rs.getFloat("Valor"));
				atrib.setStatus((rs.getString("status").equals("1"))?true:false);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return atrib;
	}
	
	public Obj_Jefatura Jefatura(int folio) throws SQLException{
		Obj_Jefatura jefat = new Obj_Jefatura();
		String query = "select * from tb_jefatura where folio ="+ folio;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				jefat.setFolio(rs.getInt("folio"));
				jefat.setDescripcion(rs.getString("Descripcion").trim());
				jefat.setStatus((rs.getString("status").equals("1"))?true:false);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return jefat;
	}
	
	public Obj_Equipo_Trabajo Eq_Tabajo(int folio) throws SQLException{
		Obj_Equipo_Trabajo EqTrabajo = new Obj_Equipo_Trabajo();
		String query = "select * from tb_equipo_trabajo where folio ="+ folio;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				EqTrabajo.setFolio(rs.getInt("folio"));
				EqTrabajo.setDescripcion(rs.getString("Descripcion").trim());
				EqTrabajo.setStatus((rs.getString("status").equals("1"))?true:false);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return EqTrabajo;
	}
	
	public Obj_Ponderacion Ponderacion(int folio) throws SQLException{
		Obj_Ponderacion pond = new Obj_Ponderacion();
		String query = "exec sp_select_ponderado "+ folio;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				pond.setFolio(rs.getInt("folio"));
				pond.setDescripcion(rs.getString("Descripcion").trim());
				pond.setValor(rs.getFloat("Valor"));
				pond.setFechaIn(rs.getString("FechaInicio").trim());
				pond.setFechaFin(rs.getString("FechaFin").trim());
				pond.setStatus((rs.getString("status").equals("1"))?true:false);
				
				pond.setDomingo(rs.getString("Domingo").equals("1")?true:false);
				pond.setLunes((rs.getString("Lunes").equals("1"))?true:false);
				pond.setMartes((rs.getString("Martes").equals("1"))?true:false);
				pond.setMiercoles((rs.getString("Miercoles").equals("1"))?true:false);
				pond.setJueves((rs.getString("Jueves").equals("1"))?true:false);
				pond.setViernes((rs.getString("Viernes").equals("1"))?true:false);
				pond.setSabado((rs.getString("Sabado").equals("1"))?true:false);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return pond;
	}
	
	public Obj_Nivel_Critico Nivel(int folio) throws SQLException{
		Obj_Nivel_Critico nc = new Obj_Nivel_Critico();
		String query = "select * from tb_nivel_critico where folio ="+ folio;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				nc.setFolio(rs.getInt("folio"));
				nc.setDescripcion(rs.getString("Descripcion").trim());
				nc.setValor(rs.getInt("Valor"));
				nc.setStatus((rs.getString("status").equals("1"))?true:false);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return nc;
	}
	
	public Obj_OpRespuesta OpRespuesta(int folio) throws SQLException{
		Obj_OpRespuesta opR = new Obj_OpRespuesta();
		String query = "exec sp_select_opLibre "+ folio;
		
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
//				opR.setOpcion(rs.getString("opciones"));
//				opR.setNombre(rs.getString("nombre"));
//				opR.setStatus((rs.getString("status").equals("1"))?true:false);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return opR;
	}
	
	public Obj_Tipo_Banco Tipo_Banco(int folio) throws SQLException{
		Obj_Tipo_Banco banck = new Obj_Tipo_Banco();
		String query = "select * from tb_tipo_banco where folio ="+ folio;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				banck.setFolio(rs.getInt("folio"));
				banck.setBanco(rs.getString("nombre").trim());
				banck.setAbreviatura(rs.getString("abreviatura").trim());
				banck.setStatus((rs.getString("status").equals("1"))?true:false);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return banck;
	}
		

	public Obj_Alimentacion_Cortes corte(int folio) throws SQLException{
		Obj_Alimentacion_Cortes corte = new Obj_Alimentacion_Cortes();
		String query = "select * from tb_alimentacion_cortes where folio ="+ folio;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				corte.setFolio_corte(rs.getString("folio_corte"));
				corte.setFolio_empleado(rs.getInt("folio_empleado"));
				corte.setPuesto(rs.getString("nombre_empleado").trim());
				corte.setPuesto(rs.getString("puesto").trim());
				corte.setEstablecimiento(rs.getString("establecimiento").trim());
				corte.setAsignacion(rs.getString("asignacion").trim());
				corte.setCorte_sistema(rs.getFloat("corte_del_sistema"));
				corte.setDeposito(rs.getFloat("deposito"));
				corte.setEfectivo(rs.getFloat("efectivo"));
				corte.setDiferencia_corte(rs.getFloat("diferencia_corte"));
				corte.setFecha(rs.getString("fecha").trim());
				corte.setStatus((rs.getString("status").equals("1"))?true:false);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return corte;
	}
	
	public Obj_Alimentacion_Cortes Corte(String asignacion) throws SQLException{
		Obj_Alimentacion_Cortes corte = new Obj_Alimentacion_Cortes();
		String query = "select * from tb_alimentacion_denominaciones where asignacion ="+ asignacion;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				corte.setFolio_corte(rs.getString("folio_corte"));
				corte.setFolio_empleado(rs.getInt("folio_empleado"));
				corte.setPuesto(rs.getString("nombre_empleado").trim());
				corte.setPuesto(rs.getString("puesto").trim());
				corte.setEstablecimiento(rs.getString("establecimiento").trim());
				corte.setAsignacion(rs.getString("asignacion").trim());
				corte.setCorte_sistema(rs.getFloat("corte_del_sistema"));
				corte.setDeposito(rs.getFloat("deposito"));
				corte.setEfectivo(rs.getFloat("efectivo"));
				corte.setDiferencia_corte(rs.getFloat("diferencia_corte"));
				corte.setFecha(rs.getString("fecha").trim());
				corte.setStatus((rs.getString("status").equals("1"))?true:false);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return corte;
	}
	
	public Obj_Divisa_Y_TipoDeCambio divisas(int folio) throws SQLException{
		Obj_Divisa_Y_TipoDeCambio divisas = new Obj_Divisa_Y_TipoDeCambio();
		String query = "select * from tb_divisas_tipo_de_cambio where folio ="+ folio;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				divisas.setFolio(rs.getInt("folio"));
				divisas.setNombre(rs.getString("nombre_divisas").trim());
				divisas.setValor(rs.getFloat("valor"));
				divisas.setStatus((rs.getString("status").equals("1"))?true:false);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return divisas;
	}
	
	public Obj_Denominaciones denominaciones(int folio) throws SQLException{
		Obj_Denominaciones denominaciones = new Obj_Denominaciones();
		String query = "select * from tb_denominaciones where folio ="+ folio;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				denominaciones.setFolio(rs.getInt("folio"));
				denominaciones.setDenominacion(rs.getString("nombre").trim());
				denominaciones.setMoneda(rs.getString("moneda"));
				denominaciones.setStatus((rs.getString("status").equals("1"))?true:false);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return denominaciones;
	}
	
	public Obj_Puesto Puesto_Nuevo() throws SQLException{
		Obj_Puesto puesto = new Obj_Puesto();
		String query = "select max(folio) as 'Maximo' from tb_puesto";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				puesto.setFolio(rs.getInt("Maximo"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return puesto;
	}
	
	public Obj_Dias_Inhabiles DiaInA_Nuevo() throws SQLException{
		Obj_Dias_Inhabiles diaIA = new Obj_Dias_Inhabiles();
		String query = "select max(folio) as 'Maximo' from tb_dias_inhabiles";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				diaIA.setFolio(rs.getInt("Maximo"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return diaIA;
	}
	
	public Obj_Atributos Atributo_Nuevo() throws SQLException{
		Obj_Atributos atrib = new Obj_Atributos();
		String query = "select max(folio) as 'Maximo' from tb_atributo";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				atrib.setFolio(rs.getInt("Maximo"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return atrib;
	}
	
	public Obj_Jefatura Jefatura_Nuevo() throws SQLException{
		Obj_Jefatura jefat = new Obj_Jefatura();
		String query = "select max(folio) as 'Maximo' from tb_jefatura";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				jefat.setFolio(rs.getInt("Maximo"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return jefat;
	}
	
	public Obj_Equipo_Trabajo Eq_Trabajo_Nuevo() throws SQLException{
		Obj_Equipo_Trabajo EqTrajajo = new Obj_Equipo_Trabajo();
		String query = "select max(folio) as 'Maximo' from tb_equipo_trabajo";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				EqTrajajo.setFolio(rs.getInt("Maximo"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return EqTrajajo;
	}
	
	public Obj_Ponderacion Ponderacion_Nuevo() throws SQLException{
		Obj_Ponderacion pond = new Obj_Ponderacion();
		String query = "select max(folio) as 'Maximo' from tb_ponderacion";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				pond.setFolio(rs.getInt("Maximo"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return pond;
	}
	
	public Obj_Nivel_Critico Nivel_Nuevo() throws SQLException{
		Obj_Nivel_Critico nc = new Obj_Nivel_Critico();
		String query = "select max(folio) as 'Maximo' from tb_nivel_critico";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				nc.setFolio(rs.getInt("Maximo"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return nc;
	}
	
	public int Cuadrante_Nuevo() throws SQLException{
		int folio = 0;
		String query = "exec sp_nuevo_cuadrante";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				folio =  rs.getInt("Maximo");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return 1;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return folio;
	}
	
	public int Relacion_Actividad_Nueva() throws SQLException{
		int folio = 0;
		String query = "exec sp_nueva_relacion_actividad";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				folio =  rs.getInt("Maximo");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return 1;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return folio;
	}
	
	public int Proyecto_Nuevo() throws SQLException{
		int folio = 0;
		String query = "exec sp_nuevo_proyecto";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				folio =  rs.getInt("Maximo");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return 1;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return folio;
	}
	
	public Obj_Actividad Actividad_Nuevo() throws SQLException{
		Obj_Actividad actividad = new Obj_Actividad();
		String query = "select max(folio) as 'Maximo' from tb_actividad";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				actividad.setFolio(rs.getInt("Maximo"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return actividad;
	}
	
	public int OpRespuesta_Nuevo() throws SQLException{
		int numero  = 0;
		String query = "exec sp_nuevo_opciones_respuesta";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				numero = rs.getInt("Maximo");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return numero;
	}
	
	public Obj_Tipo_Banco Tipo_Banco_Nuevo() throws SQLException{
		Obj_Tipo_Banco banco = new Obj_Tipo_Banco();
		String query = "select max(folio) as 'Maximo' from tb_tipo_banco";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				banco.setFolio(rs.getInt("Maximo"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return banco;
	}
		
		
	public Obj_Divisa_Y_TipoDeCambio Divisas_Nuevo() throws SQLException{
		Obj_Divisa_Y_TipoDeCambio divisas = new Obj_Divisa_Y_TipoDeCambio();
		String query = "select max(folio) as 'Maximo' from tb_divisas_tipo_de_cambio";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				divisas.setFolio(rs.getInt("Maximo"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return divisas;
	}
	
	public Obj_Denominaciones Denominaciones_Nuevo() throws SQLException{
		Obj_Denominaciones denominaciones = new Obj_Denominaciones();
		String query = "select max(folio) as 'Maximo' from tb_denominaciones";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				denominaciones.setFolio(rs.getInt("Maximo"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return denominaciones;
	}
	
	public Obj_Empleado Empleado(int folio) throws SQLException{
		Obj_Empleado empleado = new Obj_Empleado();
		String query = "exec sp_empleados "+ folio;
		Statement stmt = null;

		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while(rs.next()){
//				datos personales	
				empleado.setFolio(rs.getInt("folio"));
				empleado.setNo_checador(rs.getString("no_checador").trim());
				empleado.setNombre(rs.getString("nombre").trim());
				empleado.setAp_paterno(rs.getString("ap_paterno").trim());
				empleado.setAp_materno(rs.getString("ap_materno").trim());
				empleado.setFecha_nacimiento(rs.getString("fecha_nacimiento").trim());
				empleado.setCalle(rs.getString("calle").trim());
				empleado.setColonia(rs.getString("colonia").trim());
				empleado.setPoblacion(rs.getString("poblacion").trim());
				empleado.setTelefono_familiar(rs.getString("telefono_familiar").trim());
				empleado.setTelefono_propio(rs.getString("telefono_propio").trim());
				empleado.setTelefono_cuadrante(rs.getString("telefono_cuadrante"));
				empleado.setRfc(rs.getString("rfc").trim());
				empleado.setCurp(rs.getString("curp").trim());
				empleado.setSexo(rs.getInt("sexo"));
				
//				laboral
				empleado.setHorario(rs.getInt("horario"));
				empleado.setHorario2(rs.getInt("horario2"));
				empleado.setStatus_h1(rs.getInt("status_h1"));
				empleado.setStatus_h2(rs.getInt("status_h2"));
				empleado.setStatus_rotativo(rs.getInt("status_rotativo"));
				empleado.setFecha_ingreso(rs.getString("fecha_ingreso"));
				empleado.setStatus(rs.getInt("status"));
				empleado.setFecha_baja(rs.getString("fecha_baja"));
				empleado.setCuadrante_parcial(rs.getInt("cuadrante_parcial") == 1 ? true : false);
				empleado.setDepartameto(rs.getInt("departamento"));
				empleado.setImss(rs.getString("imss"));
				empleado.setStatus_imss(rs.getInt("status_imss"));
				empleado.setNumero_infonavit(rs.getString("numero_infonavit"));
				empleado.setEstablecimiento(rs.getInt("establecimiento_id"));
				empleado.setPuesto(rs.getInt("puesto_id"));
				empleado.setDescanso(rs.getString("descanso"));
				empleado.setDobla(rs.getString("dobla"));
				
//				percepciones y deducciones
				empleado.setSalario_diario(rs.getFloat("salario_diario"));
				empleado.setSalario_diario_integrado(rs.getFloat("salario_diario_integrado"));
				empleado.setForma_pago(rs.getString("forma_pago"));
				empleado.setSueldo(rs.getInt("sueldo_id"));				
				empleado.setBono(rs.getInt("bono_id"));
				empleado.setPrestamo(rs.getInt("rango_prestamo_id"));
				empleado.setPension_alimenticia(rs.getFloat("pension_alimenticia"));
				empleado.setInfonavit(rs.getFloat("infonavit"));
				empleado.setTargeta_nomina(rs.getString("targeta_nomina"));
				empleado.setTipo_banco(rs.getInt("tipo_banco_id"));
				empleado.setGafete(rs.getBoolean("gafete") ? true : false);
				empleado.setFuente_sodas(rs.getBoolean("fuente_sodas") ? true : false);
				empleado.setObservasiones(rs.getString("observaciones"));
				empleado.setFecha_actualizacion(rs.getString("fecha_actualizacion"));
				
				empleado.setHorario3(rs.getInt("horario3"));
				empleado.setStatus_h3(rs.getInt("status_h3"));
				
				empleado.setFecha_ingreso_imss(rs.getString("fecha_ingreso_imss"));
				empleado.setFecha_vencimiento_licencia(rs.getString("fecha_vencimiento_licencia"));
				
				File photo = new File(System.getProperty("user.dir")+"/tmp/tmp.jpg");
				FileOutputStream fos = new FileOutputStream(photo);
				
		            byte[] buffer = new byte[1];
		            InputStream is = rs.getBinaryStream("foto");
		            while (is.read(buffer) > 0) {
		                fos.write(buffer);
		            }
		            fos.close();
		            
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return empleado;
	}
	
	public Obj_Captura_Fuente_Sodas CapturaFuenteSodas(String clave) throws SQLException{
		Obj_Captura_Fuente_Sodas empleado = new Obj_Captura_Fuente_Sodas();
		String query = "exec sp_select_empleado_fuente_sodas '"+clave+"'";
		Statement stmt = null;

		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while(rs.next()){
//				datos personales	
				
				empleado.setFolio_empleado(rs.getInt("folio"));
				empleado.setClave(rs.getString("clave"));
				empleado.setEmpleado(rs.getString("empleado"));
				empleado.setEstablecimiento(rs.getString("establecimiento"));
				empleado.setPuesto(rs.getString("puesto"));
				empleado.setTotal(rs.getFloat("total"));
				
				
				File photo = new File(System.getProperty("user.dir")+"/tmp/tmp.jpg");
				FileOutputStream fos = new FileOutputStream(photo);
				
		            byte[] buffer = new byte[1];
		            InputStream is = rs.getBinaryStream("foto");
		            while (is.read(buffer) > 0) {
		                fos.write(buffer);
		            }
		            fos.close();
		            
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return empleado;
	}
	
	public Obj_Captura_Fuente_Sodas CapturaFuenteSodas_UltimiTicket(String clave) throws SQLException{
		Obj_Captura_Fuente_Sodas empleado = new Obj_Captura_Fuente_Sodas();
		String query = "exec sp_reporte_ultimo_ticket_por_empleado '"+clave+"';";
		Statement stmt = null;

		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while(rs.next()){
//				datos personales	
				
				
				empleado.setUsuario(rs.getString("usuario"));
				empleado.setFecha(rs.getString("fecha"));
				empleado.setEmpleado(rs.getString("empleado"));
				empleado.setEstablecimiento(rs.getString("establecimiento"));
				empleado.setPuesto(rs.getString("puesto"));
				empleado.setTicket(rs.getString("ticket"));
				empleado.setImporte(rs.getFloat("importe"));
		            
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return empleado;
	}
	
//	public Obj_Alimentacion_Denominacion Denom(String asignacion) throws SQLException{
//		Obj_Alimentacion_Denominacion denom = new Obj_Alimentacion_Denominacion();
//		String query = "select * from tb_alimentacion_denominaciones where asignacion ='"+ asignacion+"'";
//		Statement stmt = null;
//		try {
//			stmt = con.conexion().createStatement();
//			ResultSet rs = stmt.executeQuery(query);
//			while(rs.next()){
////				denom.setAsignacion(rs.getString("asignacion").trim());
////				denom.setFolio_empleado(rs.getInt("folio_empleado"));
////				denom.setFolio_denominacion(rs.getInt("folio_denominacion"));
////				denom.setDenominacion(rs.getString("denominacion").trim());
////				denom.setValor(rs.getFloat("valor"));
////				denom.setCantidad(rs.getFloat("cantidad"));
////				denom.setFecha(rs.getString("fecha").trim());
//			}
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}
//		finally{
//			if(stmt!=null){stmt.close();}
//		}
//		return denom;
//	}
	
//	public Obj_Revision_Lista_Raya ListaR(int numero_lista) throws SQLException{
//		Obj_Revision_Lista_Raya LR = new Obj_Revision_Lista_Raya();
//		String query = "select * from tb_lista_raya where numero_lista ="+ numero_lista;
//		Statement stmt = null;
//		try {
//			stmt = con.conexion().createStatement();
//			ResultSet rs = stmt.executeQuery(query);
//			while(rs.next()){
//				LR.setNumero_lista(rs.getInt("numero_lista"));
//				
//			}
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}
//		finally{
//			if(stmt!=null){stmt.close();}
//		}
//		return LR;
//	}
	
	public Obj_Prestamo Prestamo(int folio) throws SQLException{
		Obj_Prestamo pre = new Obj_Prestamo();
		String query = "select * from tb_prestamo where folio_empleado ="+ folio;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				pre.setFolio(rs.getInt("folio"));
				pre.setFolio(rs.getInt("folio_empleado"));
				pre.setNombre_Completo(rs.getString("nombre_completo").trim());
				pre.setFecha(rs.getString("fecha"));
				pre.setCantidad(rs.getDouble("cantidad"));
				pre.setDescuento(rs.getDouble("descuento"));
				pre.setSaldo(rs.getDouble("saldo"));
				pre.setAbonos(rs.getInt("abonos"));
				pre.setStatus(rs.getInt("status"));
				pre.setStatus(rs.getInt("status_descuento"));
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return pre;
	}
	
	public Obj_Empleado Empleado_Nuevo() throws SQLException{
		Obj_Empleado empleado = new Obj_Empleado();
		String query = "select max(folio) as 'Maximo' from tb_empleado";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				empleado.setFolio(rs.getInt("Maximo"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return empleado;
	}
	
//	public Obj_Alimentacion_Cortes Corte_Nuevo() throws SQLException{
//		Obj_Alimentacion_Cortes corte = new Obj_Alimentacion_Cortes();
//		String query = "select max(folio_corte) as 'Maximo' from tb_alimentacion_cortes";
//		Statement stmt = null;
//		try {
//			stmt = con.conexion().createStatement();
//			ResultSet rs = stmt.executeQuery(query);
//			while(rs.next()){
//				corte.setFolio_corte(rs.getInt("Maximo"));
//			}
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}
//		finally{
//			if(stmt!=null){stmt.close();}
//		}
//		return corte;
//	}
	
	public Obj_Usuario Usuario(int folio) throws SQLException{
		Obj_Usuario usuario = new Obj_Usuario();
		String query = "exec sp_select_usuario_login "+folio;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){			
				usuario.setFolio(rs.getInt("folio"));
				usuario.setNombre_completo(rs.getString("nombre_completo").trim());
				usuario.setContrasena(rs.getString("contrasena").trim());
				usuario.setPermiso_id(rs.getInt("permiso_id"));
				usuario.setFecha_alta(rs.getString("fecha").trim());
				usuario.setStatus(rs.getInt("status"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return usuario;
	}
	
	public Obj_Usuario BuscarUsuarios(String Nombre_Completo) throws SQLException{
		Obj_Usuario usuario = new Obj_Usuario();
		String query = "select * from tb_usuario where nombre_completo ='"+Nombre_Completo+"'";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){			
				usuario.setFolio(rs.getInt("folio"));
				usuario.setNombre_completo(rs.getString("nombre_completo").trim());
				usuario.setContrasena(rs.getString("contrasena").trim());
				usuario.setFecha_alta(rs.getString("fecha").trim());
				usuario.setFecha_alta(rs.getString("fecha_actu").trim());
				usuario.setSesion(rs.getString("sesion"));
				usuario.setStatus(rs.getInt("status"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return usuario;
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Vector returnPermiso(String Nombre_Completo, int menu) throws SQLException{
		Vector prueba = new Vector();
		String query = "exec sp_get_permisos '"+ Nombre_Completo +"', "+menu ;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){			
				prueba.add(rs.getBoolean(1));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return prueba;
	}
	
	public boolean existeUsuario(String Nombre_Completo) throws SQLException{
		boolean existe;
		int filas = getFilas("select * from tb_permisos where nombre_completo ='"+Nombre_Completo+"'");
		if(filas > 1){
			existe = true;
		}else{
			existe = false;
		}
		return existe;
	}
	
	
	public Obj_Usuario Maximo() throws SQLException{
		Obj_Usuario usuario = new Obj_Usuario();
		String query = "select max(folio) as 'Maximo' from tb_usuario";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				usuario.setFolio(rs.getInt("Maximo"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error");
			return null;
		}
		finally{
			 if (stmt != null) { stmt.close(); }
		}
		return usuario;
	}
	
	public Obj_fuente_sodas_rh MaximoFuente() throws SQLException{
		Obj_fuente_sodas_rh bono = new Obj_fuente_sodas_rh();
		String query = "select max(folio) as 'Maximo' from tb_fuente_sodas_rh";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				bono.setFolio(rs.getInt("Maximo"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return bono;
	}
	
	public Obj_fuente_sodas_auxf MaximoFuente_auxf() throws SQLException{
		Obj_fuente_sodas_auxf bono = new Obj_fuente_sodas_auxf();
		String query = "select max(folio) as 'Maximo' from tb_fuente_sodas_auxf";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				bono.setFolio(rs.getInt("Maximo"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return bono;
	}
	
	public Obj_Prestamo maximoPrestamo() throws SQLException{
		Obj_Prestamo bono = new Obj_Prestamo();
		String query = "select max(folio) as 'Maximo' from tb_prestamo";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				bono.setFolio(rs.getInt("Maximo"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return bono;
	}
	
	public Obj_Rango_Prestamos Rango_Prestamos(int folio) throws SQLException{
		Obj_Rango_Prestamos prestamos = new Obj_Rango_Prestamos();
		String query = "select * from tb_rango_prestamos where folio ="+ folio;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				prestamos.setFolio(rs.getInt("folio"));
				prestamos.setPrestamo_minimo(rs.getDouble("minimo"));
				prestamos.setPrestamo_maximo(rs.getDouble("maximo"));
				prestamos.setDescuento(rs.getDouble("descuento"));
				prestamos.setStatus((rs.getString("status").equals("1"))?true:false);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return prestamos;
	}
	
	public Obj_Rango_Prestamos Rango_Prestamos_Nuevo() throws SQLException{
		Obj_Rango_Prestamos rango_nuevo = new Obj_Rango_Prestamos();
		String query = "select max(folio) as 'Maximo' from tb_rango_prestamos";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				rango_nuevo.setFolio(rs.getInt("Maximo"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return rango_nuevo;
	}
	
	public Obj_Asistencia_Puntualidad Asistencia_Puntualidad() throws SQLException{
		Obj_Asistencia_Puntualidad numero = new Obj_Asistencia_Puntualidad();
		String query = "select max(folio) as 'Maximo' from tb_asistencia_puntualidad";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				numero.setExiste(rs.getInt("Maximo"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return numero;
	}
	
	public Obj_Asistencia_Puntualidad Asistencia_Puntualidad(int folio) throws SQLException{
		Obj_Asistencia_Puntualidad numero = new Obj_Asistencia_Puntualidad();
		String query = "select * from tb_asistencia_puntualidad where folio="+folio;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				numero.setValorAsistencia(rs.getFloat("asistencia"));
				numero.setValorPuntualidad(rs.getFloat("puntualidad"));
				numero.setValorGafete(rs.getFloat("gafete"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return numero;
	}
	
	public Obj_fuente_sodas_rh fuente_sodas_rh(int folio) throws SQLException {
		Obj_fuente_sodas_rh fuente_sodas = new Obj_fuente_sodas_rh();
		String query = "select status_ticket from tb_fuente_sodas_rh where folio ="+ folio;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				fuente_sodas.setStatus_ticket(rs.getInt("status_ticket"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return fuente_sodas;
	}
	
	public Obj_fuente_sodas_auxf fuente_sodas_ax(int folio) throws SQLException{
		Obj_fuente_sodas_auxf fuente_sodas = new Obj_fuente_sodas_auxf();
		String query = "select status_ticket from tb_fuente_sodas_auxf where folio ="+ folio;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				fuente_sodas.setStatus_ticket(rs.getInt("status_ticket"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return fuente_sodas;
	}
	
	public Obj_Diferencia_Cortes maximo_diferencia_cortes() throws SQLException{
		Obj_Diferencia_Cortes bono = new Obj_Diferencia_Cortes();
		String query = "select max(folio) as 'Maximo' from tb_prestamo";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				bono.setFolio(rs.getInt("Maximo"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return bono;
	}
	
	public Obj_Departamento Departamento(int folio) throws SQLException{
		Obj_Departamento departamento = new Obj_Departamento();
		String query = "select * from tb_departamento where folio ="+ folio;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				departamento.setFolio(rs.getInt("folio"));
				departamento.setDepartamento(rs.getString("departamento").trim());
				departamento.setAbreviatura(rs.getString("abreviatura").trim());
				departamento.setStatus(rs.getBoolean("status"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt != null){stmt.close();}
		}
		return departamento;
	}
	
	public Obj_Departamento Departamento_Nuevo() throws SQLException{
		Obj_Departamento departamento = new Obj_Departamento();
		String query = "select max(folio) as 'Maximo' from tb_departamento";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				departamento.setFolio(rs.getInt("Maximo"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return departamento;
	}
	
	@SuppressWarnings({ "rawtypes", "resource", "unchecked" })
	public Obj_Conexion_BD Conexion_BD() throws IOException {
		Vector myVector = new Vector();
		Obj_Conexion_BD config = new Obj_Conexion_BD();
		
		try{
			FileReader archivo = new FileReader(System.getProperty("user.dir")+"\\Config\\config");
			BufferedReader bufferedWriter = new BufferedReader(archivo);
			String cadena = "";
			while( (cadena = bufferedWriter.readLine()) !=null)
				myVector.addElement(cadena);
				
				config.setDireccionIPV4(myVector.get(0).toString());
				config.setNombreBD(myVector.get(1).toString());
				config.setUsuario(myVector.get(2).toString());
				config.setContrasena(myVector.get(3).toString());
				
		}catch(FileNotFoundException e) {
			System.out.println(e.getMessage());
			return config=null;
		}
		return config;
			
	}
	
	public Obj_Establecimiento Establ_buscar(String nombre) throws SQLException{
		Obj_Establecimiento estab = new Obj_Establecimiento();
		String query = "select folio from tb_establecimiento where nombre='"+nombre+"'";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				estab.setFolio(rs.getInt("folio"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return estab;
	}
	
	public ObjHorario Horario_buscar(String nombre) throws SQLException{
		ObjHorario horario = new ObjHorario();
		String query = "select folio from tb_horarios where nombre='"+nombre+"'";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			while(rs.next()){
				horario.setFolio(rs.getInt("folio"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return horario;
	}
	
	public Obj_Puesto Pues_buscar(String nombre) throws SQLException{
		Obj_Puesto puest = new Obj_Puesto();
		String query = "select folio from tb_puesto where nombre='"+nombre+"'";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				puest.setFolio(rs.getInt("folio"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return puest;
	}
	
	public Obj_Tipo_Banco Banco_Buscar(String nombre) throws SQLException{
		Obj_Tipo_Banco banck = new Obj_Tipo_Banco();
		String query = "select folio from tb_tipo_banco where nombre='"+nombre+"'";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				banck.setFolio(rs.getInt("folio"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return banck;
	}
		
	public Obj_Divisa_Y_TipoDeCambio Divisas_buscar(String nombre) throws SQLException{
		Obj_Divisa_Y_TipoDeCambio divisas = new Obj_Divisa_Y_TipoDeCambio();
		String query = "select valor,nombre_divisas from tb_divisas_tipo_de_cambio where nombre_divisas='"+nombre+"'";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				divisas.setNombre(rs.getString("nombre_divisas"));
				divisas.setValor(rs.getFloat("valor"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return divisas;
	}
	
		
//	public Obj_Denominaciones Denominaciones_buscar(String nombre) throws SQLException{
//		Obj_Denominaciones denominaciones = new Obj_Denominaciones();
//		String query = "select folio from tb_denominaciones where nombre='"+nombre+"'";
//		Statement stmt = null;
//		try {
//			stmt = con.conexion().createStatement();
//			ResultSet rs = stmt.executeQuery(query);
//			while(rs.next()){
//				denominaciones.setFolio(rs.getInt("folio"));
//			}
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}
//		finally{
//			if(stmt!=null){stmt.close();}
//		}
//		return denominaciones;
//	}
	
	public Obj_Horario_Empleado Turn_buscar(String nombre) throws SQLException{
		Obj_Horario_Empleado turno = new Obj_Horario_Empleado();
		String query = "exec sp_select_horario_desc_dobla '"+nombre+"'";
		
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				turno.setFolio(rs.getInt("folio"));
				turno.setDescanso(rs.getString("descanso"));
				turno.setDobla(rs.getString("dobla"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return turno;
	}
	
	public Obj_Horario_Empleado2 Turn_buscar2(String nombre) throws SQLException{
		Obj_Horario_Empleado2 turno2 = new Obj_Horario_Empleado2();
		String query = "exec sp_select_horario_desc_dobla '"+nombre+"'";
		
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				turno2.setFolio(rs.getInt("folio"));
				turno2.setDescanso(rs.getString("descanso"));
				turno2.setDobla(rs.getString("dobla"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return turno2;
	}
	
	public Obj_Horario_Empleado3 Turn_buscar3(String nombre) throws SQLException{
		Obj_Horario_Empleado3 turno3 = new Obj_Horario_Empleado3();
		String query = "exec sp_select_horario_desc_dobla '"+nombre+"'";
		
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				turno3.setFolio(rs.getInt("folio"));
				turno3.setDescanso(rs.getString("descanso"));
				turno3.setDobla(rs.getString("dobla"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return turno3;
	}
	
	public Obj_Establecimiento Establ_buscar_folio(int folio) throws SQLException{
		Obj_Establecimiento estab = new Obj_Establecimiento();
		String query = "select nombre from tb_establecimiento where folio="+folio;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				estab.setNombre(rs.getString("nombre"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return estab;
	}
	
	public Obj_Puesto Pues_buscar(int folio) throws SQLException{
		Obj_Puesto puest = new Obj_Puesto();
		String query = "select nombre from tb_puesto where folio="+folio;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				puest.setPuesto(rs.getString("nombre"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return puest;
	}
	
	public Obj_Tipo_Banco buscar_Banck(int folio) throws SQLException{
		Obj_Tipo_Banco banck = new Obj_Tipo_Banco();
		String query = "select nombre from tb_tipo_banco where folio="+folio;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				banck.setBanco(rs.getString("nombre"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return banck;
	}
	
	public Obj_Divisa_Y_TipoDeCambio Divisas_buscar(int folio) throws SQLException{
		Obj_Divisa_Y_TipoDeCambio denominaciones = new Obj_Divisa_Y_TipoDeCambio();
		String query = "select nombre from tb_divisas_tipo_de_cambio where folio="+folio;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				denominaciones.setNombre(rs.getString("nombre_divisas"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return denominaciones;
	}
	
	public Obj_Horario_Empleado Turn_buscar(int folio) throws SQLException{
		Obj_Horario_Empleado turno = new Obj_Horario_Empleado();
		String query = "select tb_horarios.nombre from tb_horarios where folio="+folio;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				turno.setNombre(rs.getString("nombre"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return turno;
	}
	
	public Obj_Horario_Empleado2 Turn_buscar2(int folio) throws SQLException{
		Obj_Horario_Empleado2 turno2 = new Obj_Horario_Empleado2();
		String query = "select tb_horarios.nombre from tb_horarios where folio="+folio;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				turno2.setNombre(rs.getString("nombre"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return turno2;
	}
	
	public Obj_Horario_Empleado3 Turn_buscar3(int folio) throws SQLException{
		Obj_Horario_Empleado3 turno3 = new Obj_Horario_Empleado3();
		String query = "select tb_horarios.nombre from tb_horarios where folio="+folio;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				turno3.setNombre(rs.getString("nombre"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return turno3;
	}
	
//	public Obj_Revision_Lista_Raya Lista_buscar_folio(int folio) throws SQLException{
//		Obj_Revision_Lista_Raya lista = new Obj_Revision_Lista_Raya();
//		String query = "select folio from tb_pre_listaraya where folio_empleado = "+folio+" and status = 1;";
//		Statement stmt = null;
//		try {
//			stmt = con.conexion().createStatement();
//		    ResultSet rs = stmt.executeQuery(query);
//			while(rs.next()){
//				lista.setFolio(rs.getInt("folio"));
//			}
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//			System.err.println("Error");
//			return null;
//		}
//		finally{
//			 if (stmt != null) { stmt.close(); }
//		}
//		return lista;
//	}
	
	
	public Obj_Configuracion_Sistema Configuracion_sistema() throws SQLException{
		Obj_Configuracion_Sistema configs = new Obj_Configuracion_Sistema();
		String query ="select count(bono_10_12) as 'Couns' from tb_configuracion_sistema";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
		    ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				configs.setCouns(rs.getInt("Couns"));
			
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error");
			return null;
		}
		finally{
			 if (stmt != null) { stmt.close(); }
		}
		return configs;
	}
	
	public Obj_Configuracion_Sistema Configuracion_sistema2() throws SQLException{
		Obj_Configuracion_Sistema configs = new Obj_Configuracion_Sistema();
		String query ="exec sp_select_configuracion_del_sistema";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
		    ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				configs.setBono_10_12(rs.getBoolean("bono_10_12"));
				configs.setBono_dia_extra(rs.getBoolean("bono_dia_extra"));
				configs.setGuardar_horario(rs.getBoolean("guardar_horario"));
				configs.setGuardar_departamento(rs.getBoolean("guardar_departamento"));
				configs.setPorcentaje_fs(rs.getInt("porcentaje_fuente_sodas"));
				configs.setFechaLR(rs.getString("fecha_lista_raya_pasada"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error");
			return null;
		}
		finally{
			 if (stmt != null) { stmt.close(); }
		}
		return configs;
	}
	
	public Obj_Departamento Departamento_buscar(int folio) throws SQLException{
		Obj_Departamento departamento = new Obj_Departamento();
		String query = "select horario from tb_departamento where folio="+folio;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				departamento.setDepartamento(rs.getString("departamento"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return departamento;
	}
	
	public Obj_Departamento Departamento_buscar_nombre(String dep) throws SQLException{
		Obj_Departamento departamento = new Obj_Departamento();
		String query = "select folio from tb_departamento where departamento='"+dep+"'";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				departamento.setFolio(rs.getInt("folio"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return departamento;
	}
	
	public Obj_Auto_Auditoria Autorizar_Audi() throws SQLException{
		Obj_Auto_Auditoria auditoria = new Obj_Auto_Auditoria();
		String query = "select autorizar_auditoria from tb_autorizaciones";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
		    ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				auditoria.setAutorizar(rs.getBoolean(1));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error");
			return null;
		}
		finally{
			 if (stmt != null) { stmt.close(); }
		}
		return auditoria;
	}
	
	public Obj_Auto_Finanzas Autorizar_Finanzas() throws SQLException{
		Obj_Auto_Finanzas auditoria = new Obj_Auto_Finanzas();
		String query = "select autorizar_finanzas from tb_autorizaciones";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
		    ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				auditoria.setAutorizar(rs.getBoolean(1));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error");
			return null;
		}
		finally{
			 if (stmt != null) { stmt.close(); }
		}
		return auditoria;
	}
	
//	public Obj_Revision_Lista_Raya Lista_Exist_Emp(int folio) throws SQLException{
//		Obj_Revision_Lista_Raya lista = new Obj_Revision_Lista_Raya();
//		String query = "select folio_empleado from tb_pre_listaraya where status=1 and folio_empleado ="+ folio;
//		Statement stmt = null;
//		try {
//			stmt = con.conexion().createStatement();
//			ResultSet rs = stmt.executeQuery(query);
//			while(rs.next()){
//				lista.setFolio_empleado(rs.getInt("folio_empleado"));
//				
//			}
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}
//		finally{
//			if(stmt!=null){stmt.close();}
//		}
//		return lista;
//	}
	
	public String[][] getUsuarioPermisos(){
		String datos = "exec lista_usuario_permisos";
		String[][] Matriz = new String[getFilas(datos)][3];
		Statement s;
		ResultSet rs;
		try {			
			s = con.conexion().createStatement();
			rs = s.executeQuery(datos);
			int i=0;
			while(rs.next()){
				Matriz[i][0] = rs.getString(1);
				Matriz[i][1] = rs.getString(2);
				Matriz[i][2] = rs.getString(3);
				i++;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return Matriz; 
	}
	
	public int getFilas(String qry){
		int filas=0;
		try {
			Statement s = con.conexion().createStatement();
			ResultSet rs = s.executeQuery(qry);
			while(rs.next()){
				filas++;
			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return filas;
	}
	
	public String[][] getNomina(int Folio){
		String datos = "exec sp_select_nomina "+Folio;
		String[][] Matriz = new String[getFilas(datos)+1][6];
		Statement s;
		ResultSet rs;
		try {		
			DecimalFormat format = new DecimalFormat("#0.00");
			s = con.conexion().createStatement();
			rs = s.executeQuery(datos);
			int i=0;
			
			while(rs.next()){
				if(i==0){
					Matriz[i][0] = "Establecimiento";
					Matriz[i][1] = "Nomina";
					Matriz[i][2] = "Pago en Linea";
					Matriz[i][3] = "Total Cheque Nomina";
					Matriz[i][4] = "Lista de raya";
					Matriz[i][5] = "Diferencia";
					Matriz[i+1][0] = "   "+rs.getString(1);
					Matriz[i+1][1] = format.format(rs.getFloat(2))+"";
					Matriz[i+1][2] = format.format(rs.getFloat(3))+"";
					Matriz[i+1][3] = format.format(rs.getFloat(4))+"";
					Matriz[i+1][4] = format.format(rs.getFloat(5))+"";
					Matriz[i+1][5] = format.format(rs.getFloat(6))+"";
					i+=2;
				}else{
					Matriz[i][0] = "   "+rs.getString(1);
					Matriz[i][1] = format.format(rs.getFloat(2))+"";
					Matriz[i][2] = format.format(rs.getFloat(3))+"";
					Matriz[i][3] = format.format(rs.getFloat(4))+"";
					Matriz[i][4] = format.format(rs.getFloat(5))+"";
					Matriz[i][5] = format.format(rs.getFloat(6))+"";
					i++;
				}
				
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return Matriz; 
	}
	
	public int MaximoListaRaya(){
		String datos = "select	max(numero_lista)+1 from tb_lista_raya";
		int valor = 0;
		Statement s;
		ResultSet rs;
		try {			
			s = con.conexion().createStatement();
			rs = s.executeQuery(datos);
			while(rs.next()){
				valor = rs.getInt(1);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return valor; 
	}
	
	
	public int MaximoListaNomina(){
		String datos = "select max(lista_raya) from tb_captura_totales_nomina";
		int valor = 0;
		Statement s;
		ResultSet rs;
		try {			
			s = con.conexion().createStatement();
			rs = s.executeQuery(datos);
			while(rs.next()){
				valor = rs.getInt(1);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return valor; 
	}
	
	
	public String[] getTotalesNomina(int Folio){
		
		String datos = "exec sp_total_nomina "+Folio;
		String[] Matriz = new String[6];
		Statement s;
		ResultSet rs;
		try {		
			DecimalFormat format = new DecimalFormat("#0.00");
			s = con.conexion().createStatement();
			rs = s.executeQuery(datos);
			while(rs.next()){
				Matriz[0] = "   "+rs.getString(1);
				Matriz[1] = rs.getDouble(2)+"";
				Matriz[2] = format.format(rs.getFloat(3))+"";
				float totalChecke = rs.getFloat(4);
				float listaRaya = rs.getFloat(5);
				Matriz[3] = format.format(totalChecke)+"";
				Matriz[4] = format.format(listaRaya)+"";
				Matriz[5] = format.format(listaRaya-totalChecke)+"";
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return Matriz; 
	}
	
	public String[] getTotalesCheque1(int Folio){
		String datos = "exec sp_total_cheque_1_super "+Folio;
		String[] Matriz = new String[6];
		Statement s;
		ResultSet rs;
		try {		
			DecimalFormat format = new DecimalFormat("#0.00");
			s = con.conexion().createStatement();
			rs = s.executeQuery(datos);
			while(rs.next()){
				Matriz[0] = "   "+rs.getString(1);
				Matriz[1] = format.format(rs.getFloat(2))+"";
				Matriz[2] = format.format(rs.getFloat(3))+"";
				Matriz[3] = format.format(rs.getFloat(4))+"";
				Matriz[4] = "Cheque (2) super";
				Matriz[5] = format.format(rs.getFloat(6))+"";
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return Matriz; 
	}
	
	public String[] getTotalesCheque1Ferre(int Folio){
		String datos = "exec sp_total_cheque_1_ferreteria "+Folio;
		String[] Matriz = new String[6];
		Statement s;
		ResultSet rs;
		try {		
			DecimalFormat format = new DecimalFormat("#0.00");
			s = con.conexion().createStatement();
			rs = s.executeQuery(datos);
			while(rs.next()){
				Matriz[0] = "   "+rs.getString(1);
				Matriz[1] = format.format(rs.getFloat(2))+"";
				Matriz[2] = format.format(rs.getFloat(3))+"";
				Matriz[3] = format.format(rs.getFloat(4))+"";
				Matriz[4] = "Cheque (2) ferre refa";
				Matriz[5] = format.format(rs.getFloat(6))+"";
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return Matriz; 
	}
	
	public String[] getTotalesCheque1Izacel(int Folio){
		String datos = "exec sp_total_cheque_1_izacel "+Folio;
		String[] Matriz = new String[6];
		Statement s;
		ResultSet rs;
		try {		
			DecimalFormat format = new DecimalFormat("#0.00");
			s = con.conexion().createStatement();
			rs = s.executeQuery(datos);
			while(rs.next()){
				Matriz[0] = "   "+rs.getString(1);
				Matriz[1] = format.format(rs.getFloat(2))+"";
				Matriz[2] = format.format(rs.getFloat(3))+"";
				Matriz[3] = format.format(rs.getFloat(4))+"";
				Matriz[4] = "";
				Matriz[5] = "";
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return Matriz; 
	}
	
	public String[] getNominaChequeABC(int Folio){
		String sp_total_cheques = "exec sp_total_cheques "+Folio;
		String[] Matriz = new String[6];
		Statement s;
		ResultSet rs;
		try {		
			DecimalFormat format = new DecimalFormat("#0.00");
			s = con.conexion().createStatement();
			
			rs = s.executeQuery(sp_total_cheques);
			
			while(rs.next()){
				
				Matriz[0] = "   "+rs.getString(1);
				Matriz[1] = format.format(rs.getFloat(2))+"";
				Matriz[2] = "   "+rs.getString(3);
				Matriz[3] = format.format(rs.getFloat(4))+"";
				Matriz[4] = "   "+rs.getString(5);
				Matriz[5] = format.format(rs.getFloat(6))+"";
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return Matriz; 
	}
	
	public float getNominaIndividual(String Establecimiento, int lista){
		String sp_total_cheques = "exec sp_return_nomina_value '"+Establecimiento+"',"+lista+";";
		float resultado = 0;
		Statement s;
		ResultSet rs;
		try {		
			s = con.conexion().createStatement();
			rs = s.executeQuery(sp_total_cheques);
			
			while(rs.next()){
				resultado = rs.getFloat(1);
				
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return resultado; 
	}
	
	public float getBancosIndividual(String Establecimiento, int lista){
		String sp_total_cheques = "exec sp_return_bancos_value '"+Establecimiento+"',"+lista+";";
		float resultado = 0;
		Statement s;
		ResultSet rs;
		try {		
			s = con.conexion().createStatement();
			rs = s.executeQuery(sp_total_cheques);
			
			while(rs.next()){
				resultado = rs.getFloat(1);
				
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return resultado; 
	}
	
	public float getListaRayaIndividual(String Establecimiento, int lista){
		String sp_total_cheques = "exec sp_return_lista_raya_value '"+Establecimiento+"',"+lista+";";
		float resultado = 0;
		Statement s;
		ResultSet rs;
		try {		
			s = con.conexion().createStatement();
			rs = s.executeQuery(sp_total_cheques);
			
			while(rs.next()){
				resultado = rs.getFloat(1);
				
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return resultado; 
	}
	
	public float getDiferenciaIndividual(String Establecimiento, int lista){
		String sp_total_cheques = "exec sp_return_diferencia_value "+lista+",'"+Establecimiento+"';";
		float resultado = 0;
		Statement s;
		ResultSet rs;
		try {		
			s = con.conexion().createStatement();
			rs = s.executeQuery(sp_total_cheques);
			
			while(rs.next()){
				resultado = rs.getFloat(1);
				
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return resultado; 
	}
	
	public int getMaximoNomina(){
		String sp_total_cheques = "exec sp_maximo_nomina";
		int resultado = 0;
		Statement s;
		ResultSet rs;
		try {		
			s = con.conexion().createStatement();
			rs = s.executeQuery(sp_total_cheques);
			
			while(rs.next()){
				resultado = rs.getInt(1);
				
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return resultado; 
	}
	
	public int TemporadaNuevo(){
		String sp_total_cheques = "exec sp_temporada_nuevo";
		int resultado = 0;
		Statement s;
		ResultSet rs;
		try {		
			s = con.conexion().createStatement();
			rs = s.executeQuery(sp_total_cheques);
			
			while(rs.next()){
				resultado = rs.getInt(1);
				
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return resultado; 
	}
	
	public Obj_Temporada TemporadaBuscar(int folio) throws SQLException{
		Obj_Temporada temporada = new Obj_Temporada();
		String query = "select * from tb_temporada where folio ="+ folio;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				temporada.setFolio(rs.getInt("folio"));
				temporada.setDescripcion(rs.getString("descripcion"));
				temporada.setFecha_in(rs.getString("fecha_in"));
				temporada.setFecha_fin(rs.getString("fecha_fin"));
				temporada.setDia(rs.getString("dia"));	
				temporada.setStatus(rs.getBoolean("status") ? true : false);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return temporada;
	}

	public int Nueva_Actividad(){
		String sp_total_cheques = "exec sp_nueva_actividad";
		int resultado = 0;
		Statement s;
		ResultSet rs;
		try {		
			s = con.conexion().createStatement();
			rs = s.executeQuery(sp_total_cheques);
			
			while(rs.next()){
				resultado = rs.getInt(1);
				
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return resultado; 
	}
	
	public int Nueva_Actividad_Jerarquico(){
		String sp_total_cheques = "exec [sp_nueva_actividad_jerarquico]";
		int resultado = 0;
		Statement s;
		ResultSet rs;
		try {		
			s = con.conexion().createStatement();
			rs = s.executeQuery(sp_total_cheques);
			
			while(rs.next()){
				resultado = rs.getInt(1);
				
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return resultado; 
	}
	
	public Object[] Permisos(int folio){
		String query = "exec sp_select_usuario_permisos_false_por_folio "+folio;
		String[] permisos = new String[getFilas(query)];
		Statement s;
		ResultSet rs;
		
		try {				
			s = con.conexion().createStatement();
			rs = s.executeQuery(query);
			int i=0;
			while(rs.next()){
				permisos[i] = rs.getString(1);
				i++;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
			
		return permisos;
	}
	
	public boolean ActividadExiste(int actividad){
		String query = "exec sp_folio_actividad "+actividad;
		
		boolean existe = false;
		Statement s;
		ResultSet rs;
		
		try {				
			s = con.conexion().createStatement();
			rs = s.executeQuery(query);
			
			while(rs.next()){
				existe = Boolean.parseBoolean(rs.getString("Existe").trim());
			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
			
		return existe;
	}
	
	public boolean ActividadExiste(String actividad){
		String query = "exec sp_actividad_duplicada '"+actividad+"'";
		
		boolean existe = false;
		Statement s;
		ResultSet rs;
		
		try {				
			s = con.conexion().createStatement();
			rs = s.executeQuery(query);
			
			while(rs.next()){
				existe = Boolean.parseBoolean(rs.getString("Existe").trim());
			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
			
		return existe;
	}
	
	
	public String ActividadExisteNameOld(int actividad){
		String query = "exec sp_select_actividad_nombre "+actividad;
		
		String nombre = "";
		Statement s;
		ResultSet rs;
		
		try {				
			s = con.conexion().createStatement();
			rs = s.executeQuery(query);
			
			while(rs.next()){
				nombre = rs.getString("actividad").trim();
			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
			
		return nombre;
	}
	
	public String ActividadExisteNameOldJerarquica(int actividad){
		String query = "exec sp_select_actividad_nombre_jerarquico "+actividad;
		String nombre = "";
		Statement s;
		ResultSet rs;
		
		try {				
			s = con.conexion().createStatement();
			rs = s.executeQuery(query);
			
			while(rs.next()){
				nombre = rs.getString("actividad").trim();
			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
			
		return nombre;
	}
	
	public boolean ActividadExisteJerarquico(int actividad){
		String query = "exec [sp_folio_actividad_jerarquico] "+actividad;
		
		boolean existe = false;
		Statement s;
		ResultSet rs;
		
		try {				
			s = con.conexion().createStatement();
			rs = s.executeQuery(query);
			
			while(rs.next()){
				existe = Boolean.parseBoolean(rs.getString("Existe").trim());
			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
			
		return existe;
	}
	
	public boolean ActividadExisteJerarquico(String actividad){
		String query = "exec [sp_actividad_jerarquico_duplicada] '"+actividad+"'";
		
		boolean existe = false;
		Statement s;
		ResultSet rs;
		
		try {				
			s = con.conexion().createStatement();
			rs = s.executeQuery(query);
			
			while(rs.next()){
				existe = Boolean.parseBoolean(rs.getString("Existe").trim());
			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
			
		return existe;
	}
	
	public String ActividadExisteJerarquicoNameOld(int actividad){
		String query = "exec [sp_select_actividad_jerarquica_nombre] '"+actividad+"'";
		
		String existe = "";
		Statement s;
		ResultSet rs;
		
		try {				
			s = con.conexion().createStatement();
			rs = s.executeQuery(query);
			
			while(rs.next()){
				existe = rs.getString("actividad").trim();
			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
			
		return existe;
	}
	
	public String puestosDependientes(){
		Obj_Usuario user = new Obj_Usuario().LeerSession();
		String query = "exec sp_puestos_dependientes '"+ procesa_texto(user.getNombre_completo()) +"';";
		
		String existe = "";
		Statement s;
		ResultSet rs;
		
		try {				
			s = con.conexion().createStatement();
			rs = s.executeQuery(query);
			
			while(rs.next()){
				existe = rs.getString("puesto_dependiente") + ", ";
			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		if(existe.length() > 0){
			existe = existe.substring(0, existe.length()-2);
		}else{
			existe = "";
		}
		return existe;
	}
	
	public String procesa_texto(String texto) {
        StringTokenizer tokens = new StringTokenizer(texto);
        texto = "";
        while(tokens.hasMoreTokens()){
            texto += " "+tokens.nextToken();
        }
        texto = texto.toString();
        texto = texto.trim().toUpperCase();
        return texto;
    }

	public Obj_Directorios BucarDirectorios(int folio) throws SQLException{
		Obj_Directorios directorio = new Obj_Directorios();
		String query = "select * from tb_direccion_telefonicos where folio_empleado ="+ folio;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				directorio.setFolio(rs.getInt("folio"));
				directorio.setFolio_empleado(rs.getInt("folio_empleado"));
				directorio.setNombre(rs.getString("nombre"));
				directorio.setTelefono(rs.getString("numero"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt != null){stmt.close();}
		}
		return directorio;
	}
	
	//agregar buscar nivel_gerarquico
	public Obj_Nivel_Jerarquico Gerarquico(int folio) throws SQLException{
		Obj_Nivel_Jerarquico pond = new Obj_Nivel_Jerarquico();
		String query = "exec sp_nivel_gerarquico "+ folio;
		
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				pond.setFolio(rs.getInt("folio"));
				pond.setDescripcion(rs.getString("descripcion"));
				pond.setPuesto_dependiente(rs.getString("puestodependiente"));
				pond.setPuesto_principal(rs.getString("puestoprincipal"));
				pond.setPuesto(rs.getString("puesto"));
				pond.setEstablecimiento(rs.getString("establecimiento"));
				pond.setStatus((rs.getString("status").equals("1"))?true:false);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
			
		}
		return pond;
	}

	public boolean OpRespuesta_Existe(String Nombre) throws SQLException{
		boolean respuesta = false;
		String query = "exec sp_existe_opcion_respuesta '"+Nombre+"';";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				respuesta = rs.getBoolean("Existe");

			}
		
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		finally{
			if(stmt!=null){stmt.close();
			}
		}
		return respuesta;
	}

	public Obj_Nivel_Jerarquico Gerarquico_nuevo() throws SQLException{
		Obj_Nivel_Jerarquico pond = new Obj_Nivel_Jerarquico();
		String query = "select max(folio) as 'Maximo' from tb_Nivel_gerarquico";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				pond.setFolio(rs.getInt("Maximo"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return pond;
	}
	
	public Obj_Actividad Buscar_Actividad(int folio) throws SQLException{
		Obj_Actividad actividad = new Obj_Actividad();
		String query = "exec sp_select_actividad "+folio+";";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				actividad.setActividad(rs.getString("actividad"));
				actividad.setDescripcion(rs.getString("descripcion"));
				actividad.setRespuesta(rs.getString("respuesta"));
				actividad.setAtributos(rs.getString("atributo"));
				actividad.setNivel_critico(rs.getString("critico"));
				actividad.setTemporada(rs.getString("temporada"));
				actividad.setCarga(rs.getInt("carga") == 1 ? true : false);
				actividad.setRepetir(rs.getInt("repetir"));
				actividad.setStatus(rs.getString("status").equals("1") ? true : false);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return actividad;
	}
	
	public Obj_Actividad_Asignadas_Nivel_Jerarquico Buscar_Actividad_Nivel_Jerarquico(int folio) throws SQLException{
		Obj_Actividad_Asignadas_Nivel_Jerarquico actividad = new Obj_Actividad_Asignadas_Nivel_Jerarquico();
		String query = "exec sp_select_actividad_nivel_jerarquico "+folio+";";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				actividad.setActividad(rs.getString("actividad"));
				actividad.setDescripcion(rs.getString("descripcion"));
				actividad.setRespuesta(rs.getString("respuesta"));
				actividad.setAtributos(rs.getString("atributo"));
				actividad.setNivel_critico(rs.getString("critico"));
				actividad.setTemporada(rs.getString("temporada"));
				actividad.setCarga(rs.getInt("carga") == 1 ? true : false);
				actividad.setRepetir(rs.getInt("repetir"));
				actividad.setStatus(rs.getString("status").equals("1") ? true : false);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return actividad;
	}
	
	public boolean existeActividadRelacionada(int relacion) throws SQLException{
		boolean resultado = false;
		String query = "exec sp_existe_relacion_actividad "+relacion+";";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
		    ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				resultado = rs.getBoolean(1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error");
			return false;
		}
		finally{
			 if (stmt != null) { stmt.close(); }
		}
		return resultado;
	}
	
	public boolean existeActividadRelacionada(String relacion) throws SQLException{
		boolean resultado = false;
		String query = "exec sp_existe_relacion_actividad_nombre '"+relacion+"';";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
		    ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				resultado = rs.getBoolean(1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error");
			return false;
		}
		finally{
			 if (stmt != null) { stmt.close(); }
		}
		return resultado;
	}
	
	public boolean existeProyecto(int proyecto) throws SQLException{
		boolean resultado = false;
		String query = "exec sp_existe_proyecto "+proyecto+";";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
		    ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				resultado = rs.getBoolean(1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error");
			return false;
		}
		finally{
			 if (stmt != null) { stmt.close(); }
		}
		return resultado;
	}
	
	public boolean existeProyecto(String proyecto) throws SQLException{
		boolean resultado = false;
		String query = "exec sp_existe_proyecto_nombre '"+proyecto+"';";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
		    ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				resultado = rs.getBoolean(1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error");
			return false;
		}
		finally{
			 if (stmt != null) { stmt.close(); }
		}
		return resultado;
	}
	
	public boolean existeCuadrante(int cuadrante) throws SQLException{
		boolean resultado = false;
		String query = "exec sp_existe_cuadrante "+cuadrante+";";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
		    ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				resultado = rs.getBoolean(1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error");
			return false;
		}
		finally{
			 if (stmt != null) { stmt.close(); }
		}
		return resultado;
	}
	
	public boolean existeCuadrante(String cuadrante) throws SQLException{
		boolean resultado = false;
		String query = "exec sp_existe_cuadrante_nombre '"+cuadrante+"';";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
		    ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				resultado = rs.getBoolean(1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error");
			return false;
		}
		finally{
			 if (stmt != null) { stmt.close(); }
		}
		return resultado;
	}
	
	public Obj_Actividades_Relacionadas Actividades_Relacionadas(int folio) throws SQLException{
		Obj_Actividades_Relacionadas relacion = new Obj_Actividades_Relacionadas();
		String query = "exec sp_select_relacion_actividad_folio "+ folio;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
		    ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				relacion.setProyecto(rs.getString(1));
				relacion.setDescripcion(rs.getString(2));
				relacion.setNivel_critico(rs.getString(3));
				relacion.setStatus(rs.getInt(4));
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error");
			return null;
		}
		finally{
			 if (stmt != null) { stmt.close(); }
		}
		return relacion;
	}

	public Obj_Actividades_Por_Proyecto ProyectoCuadrante(int folio) throws SQLException{
		Obj_Actividades_Por_Proyecto proyect = new Obj_Actividades_Por_Proyecto();
		String query = "exec sp_select_proyecto_cuadrante_folio "+ folio;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
		    ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				proyect.setProyecto(rs.getString(1));
				proyect.setDescripcion(rs.getString(2));
				proyect.setNivel_critico(rs.getString(3));
				proyect.setStatus(rs.getInt(4));
				proyect.setFecha_inicial(rs.getString(5));
				proyect.setFecha_final(rs.getString(6));
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error");
			return null;
		}
		finally{
			 if (stmt != null) { stmt.close(); }
		}
		return proyect;
	}
	
	public String[][] getTablaActividadesRelacionadas(int folio_proyecto){
		String[][] Matriz = null;
		
		String datosif = "exec sp_select_tabla_relacion_actividad " + folio_proyecto;
		
		Matriz = new String[getFilas(datosif)][5];
		Statement s;
		ResultSet rs;
		try {			
			s = con.conexion().createStatement();
			rs = s.executeQuery(datosif);
			int i=0;
			while(rs.next()){
				Matriz[i][0] = rs.getString(1);
				Matriz[i][1] = rs.getString(2);
				Matriz[i][2] = rs.getString(3);
				Matriz[i][3] = rs.getString(4);
				Matriz[i][4] = rs.getString(5);
				
				i++;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		return Matriz;
	}
	
	public String[][] getTabla(int folio_proyecto){
		String[][] Matriz = null;
		
		String datosif = "exec sp_select_tabla_proyecto_cuadrante " + folio_proyecto;
		
		Matriz = new String[getFilas(datosif)][5];
		Statement s;
		ResultSet rs;
		try {			
			s = con.conexion().createStatement();
			rs = s.executeQuery(datosif);
			int i=0;
			while(rs.next()){
				Matriz[i][0] = rs.getString(1);
				Matriz[i][1] = rs.getString(2);
				Matriz[i][2] = rs.getString(3);
				Matriz[i][3] = rs.getString(4);
				Matriz[i][4] = rs.getString(5);
				
				i++;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		return Matriz;
	}
	
	public String[][] getTablaCapturaFuenteSodas(String clave){
		String[][] Matriz = null;
		
		String datosif = "exec sp_select_tabla_captura_fuente_sodas '"+clave+"'";
		
		Matriz = new String[getFilas(datosif)][5];
		Statement s;
		ResultSet rs;
		try {			
			s = con.conexion().createStatement();
			rs = s.executeQuery(datosif);
			int i=0;
			while(rs.next()){
				Matriz[i][0] = rs.getString(1);
				Matriz[i][1] = rs.getString(2);
				Matriz[i][2] = rs.getString(3);
				Matriz[i][3] = rs.getString(4);
				Matriz[i][4] = rs.getString(5);
				
				i++;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		return Matriz;
	}
	
	public String[][] getTablaTicketFuenteSodas(int folio){
		String[][] Matriz = null;
		
		String datosif = "exec sp_acumulado_ticket_fuente_de_sodas_por_empleado "+folio;
		
		Matriz = new String[getFilas(datosif)][3];
		Statement s;
		ResultSet rs;
		try {			
			s = con.conexion().createStatement();
			rs = s.executeQuery(datosif);
			int i=0;
			while(rs.next()){
				Matriz[i][0] = rs.getString(1);
				Matriz[i][1] = rs.getString(2);
				Matriz[i][2] = rs.getString(3);
				
				i++;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		return Matriz;
	}
	
	public String[][] getTablaTicketFuenteSodas_dh(int folio){
		String[][] Matriz = null;
		
		String datosif = "exec sp_acumulado_ticket_fuente_de_sodas_por_empleado_dh "+folio;
		
		Matriz = new String[getFilas(datosif)][4];
		Statement s;
		ResultSet rs;
		try {			
			s = con.conexion().createStatement();
			rs = s.executeQuery(datosif);
			int i=0;
			while(rs.next()){
				Matriz[i][0] = rs.getString(1);
				Matriz[i][1] = rs.getString(2);
				Matriz[i][2] = rs.getString(3);
				Matriz[i][3] = rs.getString(4);

				
				i++;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		return Matriz;
	}
	
	public Obj_Cuadrante Cuadrante(int folio) throws SQLException{
		Obj_Cuadrante cuadrante = new Obj_Cuadrante();
		String query = "exec sp_select_cuadrante_folio "+ folio;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
		    ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				cuadrante.setCuadrante(rs.getString(1));
				cuadrante.setPerfil(rs.getString(2));
				cuadrante.setJefatura(rs.getString(3));
				cuadrante.setNivel_gerarquico(rs.getString(4));
				cuadrante.setEquipo_trabajo(rs.getString(5));
				cuadrante.setEstablecimiento(rs.getString(6));
				cuadrante.setDomingo(rs.getInt(7));
				cuadrante.setLunes(rs.getInt(8));
				cuadrante.setMartes(rs.getInt(9));
				cuadrante.setMiercoles(rs.getInt(10));
				cuadrante.setJueves(rs.getInt(11));
				cuadrante.setViernes(rs.getInt(12));
				cuadrante.setSabado(rs.getInt(13));
				cuadrante.setStatus(rs.getInt(14));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error");
			return null;
		}
		finally{
			 if (stmt != null) { stmt.close(); }
		}
		return cuadrante;
	}
	
	
	public String[][] getTablaDias(int cuadrante){
		String[][] Matriz = null;
		
		String datosif = "exec sp_select_tabla_cuadrante " + cuadrante;
		
		Matriz = new String[getFilas(datosif)][7];
		Statement s;
		ResultSet rs;
		try {			
			s = con.conexion().createStatement();
			rs = s.executeQuery(datosif);
			int i=0;
			while(rs.next()){
				Matriz[i][0] = rs.getString(1);
				Matriz[i][1] = rs.getString(2)+"  ";
				Matriz[i][2] = "   "+rs.getString(3);
				Matriz[i][3] = "   "+rs.getString(4);
				Matriz[i][4] = rs.getString(5);
				Matriz[i][5] = rs.getString(6);
				Matriz[i][6] = rs.getString(7);
				i++;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		return Matriz;
	}
	
	public String[][] getTablaActividadesCuadrante(int folio){
		String[][] Matriz = null;
		
		String datosif = "exec sp_select_tabla_cuadrante_actividad " + folio;
		
		Matriz = new String[getFilas(datosif)][7];
		Statement s;
		ResultSet rs;
		try {			
			s = con.conexion().createStatement();
			rs = s.executeQuery(datosif);
			int i=0;
			while(rs.next()){
				Matriz[i][0] = rs.getString(1);
				Matriz[i][1] = rs.getString(2)+"  ";
				Matriz[i][2] = "   "+rs.getString(3);
				Matriz[i][3] = "   "+rs.getString(4);
				Matriz[i][4] = rs.getString(5);
				Matriz[i][5] = rs.getString(6);
				Matriz[i][6] = rs.getString(7);
				i++;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		return Matriz;
	}
	
	public String OpNivel() throws SQLException{
		String numero  ="";
		String query = "exec sp_nivel_jerarquico";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				numero = rs.getString("Maximo");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return numero;
	}
	
	public Obj_Nivel_Jerarquico buscarnivel(int folio) throws SQLException{
		Obj_Nivel_Jerarquico nivel_gerarquico = new Obj_Nivel_Jerarquico();
		String query = "select tb_nivel_jerarquico.folio as folio" +
						",tb_nivel_jerarquico.descripcion as descripcion" +
						",tb_puesto.nombre as puesto_principal " +
						"from tb_nivel_jerarquico " +
						"inner join tb_puesto on tb_puesto.folio=tb_nivel_jerarquico.puesto_principal " +
						"where tb_nivel_jerarquico.folio ="+ folio;
		
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				nivel_gerarquico.setFolio(rs.getInt("folio"));
				
				nivel_gerarquico.setDescripcion(rs.getString("descripcion"));
				nivel_gerarquico.setPuesto_principal(rs.getString("puesto_principal"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return nivel_gerarquico;
	}

	public Obj_Nivel_Jerarquico buscarDescripcion(String descripcion) throws SQLException{
		Obj_Nivel_Jerarquico nivel_gerarquico = new Obj_Nivel_Jerarquico();
		String query = "select tb_nivel_jerarquico.folio as folio " +
					   "from tb_nivel_jerarquico " +
					   "where tb_nivel_jerarquico.descripcion = "+"'"+descripcion+"'";
				
		
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				nivel_gerarquico.setFolio(rs.getInt("folio"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return nivel_gerarquico;
	}
	
	public Obj_Nivel_Jerarquico buscarPDependiente(Object nombrePuestoDependiente) throws SQLException{
		Obj_Nivel_Jerarquico nivel_gerarquico = new Obj_Nivel_Jerarquico();
//		String query2 = "select tb_nivel_jerarquico.folio as folio" +
//						",tb_nivel_jerarquico.descripcion as descripcion" +
//						",tb_puesto.nombre as puesto_principal " +
//						"from tb_nivel_jerarquico " +
//						"inner join tb_puesto on tb_puesto.folio=tb_nivel_jerarquico.puesto_principal " +
//						"where tb_nivel_jerarquico.folio ="+ folio;
		
		String query = "select tb_tabla_nivel_jerarquico where nombre = "+nombrePuestoDependiente;
//		String query = "delete tb_tabla_nivel_jerarquico where nombre = "+nombrePuestoDependiente;
		
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				nivel_gerarquico.setFolio(rs.getInt("folio"));
				
				nivel_gerarquico.setDescripcion(rs.getString("descripcion"));
				nivel_gerarquico.setPuesto_principal(rs.getString("puesto_principal"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return nivel_gerarquico;
	}
	
	public Obj_Nivel_Jerarquico buscartablanivel(int folio) throws SQLException{
		Obj_Nivel_Jerarquico nivel_gerarquico = new Obj_Nivel_Jerarquico();
		String query = "select * from tb_tabla_nivel_jerarquico where folio ="+ folio;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				nivel_gerarquico.setFolio(rs.getInt("folio"));
				
				nivel_gerarquico.setPuesto_dependiente(rs.getString("puesto"));
				nivel_gerarquico.setEstablecimiento(rs.getString("establecimiento"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return nivel_gerarquico;
	}
	
	public Obj_Empleados_Cuadrantes buscar_empleado_cuadrante(int folio) throws SQLException{
		Obj_Empleados_Cuadrantes empleado_cuadrante = new Obj_Empleados_Cuadrantes();
		String query = "exec sp_select_buscar_empleado_cuadrante "+ folio;
		
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				empleado_cuadrante.setFolio(rs.getInt("folio"));
				empleado_cuadrante.setCuadrante(rs.getString("cuadrante"));
				empleado_cuadrante.setStatus(rs.getInt("status")==1 ? true : false);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return empleado_cuadrante;
	}
	
	public Obj_Empleados_Cuadrantes buscar_cuadrante_con_empleado(int folio) throws SQLException{
		Obj_Empleados_Cuadrantes empleado_cuadrante = new Obj_Empleados_Cuadrantes();
		String query = "exec sp_select_empleado_con_cuadrante "+ folio;
		
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				empleado_cuadrante.setFolio(rs.getInt("folio"));
				empleado_cuadrante.setCuadrante(rs.getString("cuadrante"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return empleado_cuadrante;
	}
	
	public Obj_Mensaje_Personal buscar_mensaje(int folio) throws SQLException{
		Obj_Mensaje_Personal MsjPersonal = new Obj_Mensaje_Personal();
		String query = "exec sp_select_mensaje "+ folio;
		
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				MsjPersonal.setFolioMensaje(rs.getInt("folio_mensaje"));
				MsjPersonal.setAsunto(rs.getString("asunto"));
				MsjPersonal.setFechaInicial(rs.getString("fecha_inicio"));
				MsjPersonal.setFechaFin(rs.getString("fecha_fin"));
				MsjPersonal.setMensaje(rs.getString("mensaje"));
				MsjPersonal.setStatus(rs.getInt("status")==1 ? true : false);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return MsjPersonal;
	}
	
	public Obj_Permisos_Checador buscar_permiso(int folio) throws SQLException{
		Obj_Permisos_Checador permisoChecador = new Obj_Permisos_Checador();
		String query = "exec sp_select_permiso_checador "+ folio;
		
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				
				permisoChecador.setFolio(rs.getInt("folio"));
				permisoChecador.setFolio_empleado(rs.getInt("folio_empleado"));
				permisoChecador.setNombre_empleado(rs.getString("nombre_empleado"));
				permisoChecador.setFolio_usuario(rs.getInt("folio_usuario"));
				permisoChecador.setFecha(rs.getString("fecha_permiso"));
				
				permisoChecador.setTipo_de_permiso(rs.getInt("tipo_de_permiso"));
				permisoChecador.setDescanso(rs.getInt("trabajar_como_el_dia"));
				permisoChecador.setTiempo_comida(rs.getString("tiempo_comida"));
				permisoChecador.setMotivo(rs.getString("motivo"));
				
				permisoChecador.setStatus(rs.getInt("status")==1?true:false);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return permisoChecador;
	}
	
	public Obj_Permisos_Checador comparacionDeFecha(String fecha) throws SQLException{
		Obj_Permisos_Checador permisoChecador = new Obj_Permisos_Checador();
		
		String query = "exec sp_comparar_fecha_permiso "+ "'"+fecha+"'";
		
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				permisoChecador.setFecha(rs.getString("trae_fecha"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return permisoChecador;
	}
	
	
	public int NuevoEmpleadoCuadrante() throws SQLException{
		int folio = 0;
		String query = "exec sp_nuevo_empleado_cuadrante";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				folio =  rs.getInt("Maximo");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return 1;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return folio;
	}
	
	public int NuevoMensajePersonal() throws SQLException{
		int folio = 0;
		String query = "exec sp_nuevo_mensaje";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				folio =  rs.getInt("Maximo");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return 1;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return folio;
	}
	
	public int NuevoPermisoChecador() throws SQLException{
		int folio = 0;
		String query = "exec sp_nuevo_permiso_checador";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				folio =  rs.getInt("Maximo");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return 1;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return folio;
	}
	
	public boolean existeEmpleadoCuadrante(int folio) throws SQLException{
		boolean existe = false;
		String query = "exec sp_existe_empleado_cuadrante "+folio;
		
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				existe = rs.getBoolean("existe");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return existe;
	}
	
	public String[][] getTablaEmpleadoCuadrante(int folio){
		String datos = "exec sp_select_tabla_empleado_cuadrante "+folio;
		
		String[][] Matriz = new String[getFilas(datos)][2];
		Statement s;
		ResultSet rs;
		try {			
			s = con.conexion().createStatement();
			rs = s.executeQuery(datos);
			int i=0;
			while(rs.next()){
				Matriz[i][0] = rs.getString(1);
				Matriz[i][1] = rs.getString(2);
				i++;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return Matriz; 
	}
	
	@SuppressWarnings({ "rawtypes", "resource", "unchecked" })
	public Obj_Usuario getSession() throws IOException {
		Vector myVector = new Vector();
		Obj_Usuario usuario = new Obj_Usuario();
		
		try{
			FileReader archivo = new FileReader(System.getProperty("user.dir")+"\\Config\\users");
			BufferedReader bufferedWriter = new BufferedReader(archivo);
			String cadena = "";
			while( (cadena = bufferedWriter.readLine()) !=null)
				myVector.addElement(cadena);
				
				usuario.setFolio(Integer.parseInt(myVector.get(0).toString()));
				usuario.setNombre_completo(myVector.get(1).toString());
	
				
		}catch(FileNotFoundException e) {
			System.out.println(e.getMessage());
			return usuario=null;
		}
		return usuario;
			
	}
	
	public Obj_Alimentacion_Cuadrante EmpleadoNombre(String nombre) throws SQLException{
		Obj_Alimentacion_Cuadrante empleado_cuadrante = new Obj_Alimentacion_Cuadrante();
		String query = "exec sp_select_cuadrante_empleado '"+nombre+"'";

		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				empleado_cuadrante.setNombre(rs.getString("Nombre"));
				empleado_cuadrante.setPuesto(rs.getString("Puesto"));
				empleado_cuadrante.setEstablecimiento(rs.getString("Establecimiento"));
				empleado_cuadrante.setEquipo_trabajo(rs.getString("Equipo_Trabajo"));
				empleado_cuadrante.setJefatura(rs.getString("Jefatura"));
				empleado_cuadrante.setDia(rs.getString("Dia"));
				empleado_cuadrante.setFecha(rs.getString("Fecha"));
				empleado_cuadrante.setCuadrante(rs.getString("Cuadrante"));
				
				File photo = new File(System.getProperty("user.dir")+"/tmp/tmp_cuadrante/tmp.jpg");
				FileOutputStream fos = new FileOutputStream(photo);

		        byte[] buffer = new byte[1];
		        InputStream is = rs.getBinaryStream("Foto");
		        while (is.read(buffer) > 0) {
		        	fos.write(buffer);
		        }
		        fos.close();

			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return empleado_cuadrante;
	}
	
	public String[][] tabla_alimentacion_cuadrante_libre(String nombre, String dia){
		String[][] Matriz = null;
		
		String datosif = "exec sp_select_tabla_alimentacion_libre '"+nombre+"', '"+dia+"';";
			
		Matriz = new String[getFilas(datosif)][3];
		Statement s;
		ResultSet rs;
		try {			
			s = con.conexion().createStatement();
			rs = s.executeQuery(datosif);
			int i=0;
			while(rs.next()){

				Matriz[i][0] = rs.getString(1);
				Matriz[i][1] = rs.getString(2);
				
				i++;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return Matriz;
	}
	
	public String[][] tabla_alimentacion_cuadrante_multiple_jerarquico(String nomgbre){
		String[][] Matriz = null;
		
		String datosif = "exec sp_select_tabla_alimentacion_multiple_jerar '"+nomgbre+"';";

		Matriz = new String[getFilas(datosif)][4];
		Statement s;
		ResultSet rs;
		try {			
			s = con.conexion().createStatement();
			rs = s.executeQuery(datosif);
			int i=0;
			while(rs.next()){
				Matriz[i][0] = rs.getString(1);
				Matriz[i][1] = rs.getString(2);
				Matriz[i][2] = rs.getString(3);
				i++;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return Matriz;
	}
	
	public String[][] tabla_alimentacion_cuadrante_multiple(String nomgbre){
		String[][] Matriz = null;
		
		String datosif = "exec sp_select_tabla_alimentacion_multiple '"+nomgbre+"';";
		String datosfi = "exec sp_select_tabla_alimentacion_multiple_por_proyecto '"+nomgbre+"';";
		
		Matriz = new String[getFilas(datosif)+getFilas(datosfi)][4];
		Statement s;
		Statement s1;
		ResultSet rs;
		ResultSet rs1;
		
		try {			
			s = con.conexion().createStatement();
			rs = s.executeQuery(datosif);
			
			s1 = con.conexion().createStatement();
			rs1 = s1.executeQuery(datosfi);
			
			int i=0;
			while(rs.next()){

				Matriz[i][0] = rs.getString(1);
				Matriz[i][1] = rs.getString(2);
				Matriz[i][2] = rs.getString(3);
				
				i++;
			}
			while(rs1.next()){
				Matriz[i][0] = rs1.getString(1);
				Matriz[i][1] = "N/A";
				Matriz[i][2] = rs1.getString(3);
				
				i++;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return Matriz;
	}
	
	public String[][] tabla_alimentacion_cuadrante_multiple_capturada(String nombre){
		String[][] Matriz = null;
		String datosif = "exec sp_select_tabla_alimentacion_multiple_capturada '"+nombre+"';";

		Matriz = new String[getFilas(datosif)][6];
		Statement s;
		ResultSet rs;
		try {			
			s = con.conexion().createStatement();
			rs = s.executeQuery(datosif);
			int i=0;
			while(rs.next()){

				Matriz[i][0] = rs.getString(1);
				Matriz[i][1] = rs.getString(2).equals("0") ? "N/A" : rs.getString(2);
				Matriz[i][2] = rs.getString(3);
				Matriz[i][3] = rs.getString(4);
				Matriz[i][4] = rs.getString(5);
				i++;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return Matriz;
	}
	
	public String[][] tabla_alimentacion_cuadrante_multiple_capturada_jerarquica(String nombre){
		String[][] Matriz = null;
		String datosif = "exec sp_select_tabla_alimentacion_multiple_capturada_jerarquica '"+nombre+"';";

		Matriz = new String[getFilas(datosif)][6];
		Statement s;
		ResultSet rs;
		try {			
			s = con.conexion().createStatement();
			rs = s.executeQuery(datosif);
			int i=0;
			while(rs.next()){

				Matriz[i][0] = rs.getString(1);
				Matriz[i][1] = rs.getString(2).equals("0") ? "N/A" : rs.getString(2);
				Matriz[i][2] = rs.getString(3);
				Matriz[i][3] = rs.getString(4);
				Matriz[i][4] = rs.getString(5);
				i++;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return Matriz;
	}
	
	public String[][] tabla_alimentacion_cuadrante_primera_parte(String nombre){
		String[][] Matriz = null;
		String datosif = "exec sp_pre_captura_cuadrante '"+nombre+"';";
		System.out.println(datosif);
		Matriz = new String[getFilas(datosif)][5];
		Statement s;
		ResultSet rs;
		try {			
			s = con.conexion().createStatement();
			rs = s.executeQuery(datosif);
			int i=0;
			while(rs.next()){

				Matriz[i][0] = rs.getString(1);
				Matriz[i][1] = rs.getString(2);
				Matriz[i][2] = rs.getString(3);
				Matriz[i][3] = rs.getString(4);
				Matriz[i][4] = rs.getString(5);
				i++;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return Matriz;
	}
	

	
	public Obj_OpRespuesta buscar_opcion_respuesta(int folio) throws SQLException{
		Obj_OpRespuesta respuesta = new Obj_OpRespuesta();
		String query = "exec sp_select_opcion_respuesta "+folio;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				respuesta.setFolio(rs.getInt("folio"));
				respuesta.setNombre(rs.getString("nombre"));
				respuesta.setTipo_opcion(rs.getInt("tipo_opcion") == 0 ? "Opci�n Libre" : "Opci�n M�ltiple");
				respuesta.setStatus(rs.getInt("status") == 0 ? false : true);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return respuesta;
	}
	
	public boolean buscar_respuesta_folio(int folio) throws SQLException{
		String query = "exec sp_select_folio_opcion_respuesta "+ folio;
		boolean sentencia = false;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
		    ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				sentencia = rs.getBoolean(1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error");
			return false;
		}
		finally{
			 if (stmt != null) { stmt.close(); }
		}
		return sentencia;
	}
	
	
	public boolean isFoto(int folio) throws SQLException{
		String query = "exec sp_existe_foto"+ folio;
		boolean sentencia = false;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				sentencia = rs.getBoolean(1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		finally{
			if(stmt != null){stmt.close();}
		}
		return sentencia;
	}

	public boolean existe_submenu(String nombre) {
		//se manda el nombre para el procedimiento almacenado
		String query = "exec sp_comprobar_submenus '" + nombre+"'";
		boolean sentencia = false;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				sentencia = rs.getBoolean(1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		finally{
			if(stmt != null){try {
				stmt.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}}
		}
		return sentencia;
	
	}
	
	public int[]  hora_minut_segundo(){
		int[] horas = new int[3];
		
		String query = "exec sp_hora_sincronizacion";
		Statement stmt = null;
		
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				horas[0] = rs.getInt(1);
				horas[1] = rs.getInt(2);
				horas[2] = rs.getInt(3);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			if(stmt != null){try {
				stmt.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}}
		}
		return horas;
	}
	
	public boolean Existe_Cuadrante_Muliple(String nombre){
		String query = "exec sp_existe_respuesta_cuadrante '" + nombre + "';";
		boolean disponible = false;
		try {				
			Statement s = con.conexion().createStatement();
			ResultSet rs = s.executeQuery(query);
			
			while(rs.next()){
				disponible = rs.getBoolean(1);
			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
			
		return disponible;
	}
	
	//Buscar Mensaje
	
		public Obj_Mensajes mensaje(int folio) throws SQLException{
			Obj_Mensajes mensaje = new Obj_Mensajes();
			String query = "select * from tb_mensajes where folio ="+ folio;
			Statement stmt = null;
			try {
				stmt = con.conexion().createStatement();
				ResultSet rs = stmt.executeQuery(query);
				while(rs.next()){
					mensaje.setFolio(rs.getInt("folio"));
					mensaje.setMensaje(rs.getString("mensaje"));
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			finally{
				if(stmt!=null){stmt.close();}
			}
			return mensaje;
		}
		
		//agregar nuevo mensaje
		public Obj_Mensajes Mensaje_Nuevo() throws SQLException{
			Obj_Mensajes mensaje = new Obj_Mensajes();
			String query = "select max(folio) as 'Maximo' from tb_mensajes";
			Statement stmt = null;
			try {
				stmt = con.conexion().createStatement();
				ResultSet rs = stmt.executeQuery(query);
				while(rs.next()){
					mensaje.setFolio(rs.getInt("Maximo"));
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			finally{
				if(stmt!=null){stmt.close();}
			}
			return mensaje;
		}
		
		/////
		public Obj_Asignacion_Mensajes Mensaje_New() throws SQLException{
			Obj_Asignacion_Mensajes mensajes = new Obj_Asignacion_Mensajes();
			String query = "select max(folio) as 'Maximo' from tb_asignacion_mensaje";
			Statement stmt = null;
			try {
				stmt = con.conexion().createStatement();
				ResultSet rs = stmt.executeQuery(query);
				while(rs.next()){
					mensajes.setFolio(rs.getInt("Maximo"));
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			finally{
				if(stmt!=null){stmt.close();}
			}
			return mensajes;
		}
		
		//Buscar el folio de la asignacion del mensaje
		
			public Obj_Asignacion_Mensajes Asignacion(int folio) throws SQLException{
				Obj_Asignacion_Mensajes mensaje = new Obj_Asignacion_Mensajes();
				String query = "select * from tb_asignacion_mensaje where folio ="+ folio;
				Statement stmt = null;
				try {
					stmt = con.conexion().createStatement();
					ResultSet rs = stmt.executeQuery(query);
					while(rs.next()){
						mensaje.setFolio(rs.getInt("folio"));
						mensaje.setNo_mensajes(rs.getInt("mensaje"));
						mensaje.setMensaje(rs.getString("mensajearea"));
						mensaje.setPuesto(rs.getString("puesto"));
						mensaje.setEquipo(rs.getString("equipo"));
						mensaje.setJefatura(rs.getString("jefatura"));
						mensaje.setEmpleado(rs.getString("empleado"));
						
						
						mensaje.setRbpuesto(rs.getBoolean("rbpuesto"));
						mensaje.setRbequipo(rs.getBoolean("rbequipo"));
						mensaje.setRbjefatura(rs.getBoolean("rbjefatura"));
						mensaje.setRbempleado(rs.getBoolean("rbempleado"));
					}
					
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
				finally{
					if(stmt!=null){stmt.close();}
				}
				return mensaje;
			}
	
//	buscar entosal
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Vector buscar_entosal(int folio) throws SQLException{
		Vector fila = new Vector();
		String query = "exec sp_select_entosal "+ folio;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
		    ResultSet rs = stmt.executeQuery(query);
		    
			while(rs.next()){
				fila.add(rs.getObject(1));
				fila.add(rs.getObject(2));
				fila.add(rs.getObject(3));
				fila.add(rs.getObject(4));
				fila.add(rs.getObject(5));
				fila.add(rs.getObject(6));
				fila.add(rs.getObject(7));
				fila.add(rs.getObject(8));
				fila.add(rs.getObject(9));
				fila.add(rs.getObject(10));
						
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error");
			return null;
		}
		finally{
			 if (stmt != null) { stmt.close(); }
		}
		return fila;
	}
	
	public Obj_Entosal Entosal() throws SQLException{
		Obj_Entosal entosal = new Obj_Entosal();
		String query = "select * from tb_key_check_master";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				entosal.setClave(rs.getString("keyCheckMaster"));
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return entosal;
	}
	
	public boolean existeColisionTiempo(int folio)throws SQLException{
		boolean existe = false;
		String query = "exec sp_valida_hora_recurrente_minuto "+folio;
		
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				existe = rs.getBoolean("valor");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return existe;
	}
	
	public boolean IntentaChecarDiaDescanso(int folio)throws SQLException{
		boolean descanso = false;
		String query = "exec sp_valida_checada_dia_descanso "+folio;
		
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				descanso = rs.getBoolean("valor");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return descanso;
	}
	public boolean obtener_checadas_dia_dobla(int folio)throws SQLException{
		boolean descanso = false;
		String query = "sp_valida_cantidad_de_checadas_en_dia_dobla "+folio;
		
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				descanso = rs.getBoolean("valor");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return descanso;
	}
	public boolean valida_si_dobla_y_esta_saliendo_a_comer(int folio)throws SQLException{
		boolean dobla = false;
		String query = "exec sp_valida_checadas_vs_dia_dobla "+folio;
		
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				dobla = rs.getBoolean("valor");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return dobla;
	}
	
	//Buscamos el horario por su nombre
	public ObjHorario buscahorario(int folio) throws SQLException{
		ObjHorario horaa = new ObjHorario();
		String query = "exec sp_select_horarios "+folio;
		
		Statement stmt = null;
		try {
			
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				horaa.setFolio(rs.getInt("folio"));
				horaa.setNombre(rs.getString("nombre").trim());
				
				horaa.setDiaD(rs.getString("diaD").trim());
				horaa.setDomingo1(rs.getString("inicioD"));
				horaa.setDomingo2(rs.getString("finD"));
				horaa.setDomingo3(rs.getString("entradaD"));
				horaa.setDomingo4(rs.getString("salidaD"));
				horaa.setDomingo5(rs.getString("recesoD"));
				
				horaa.setDiaL(rs.getString("diaL").trim());
				horaa.setLunes1(rs.getString("inicioL"));
				horaa.setLunes2(rs.getString("finL"));
				horaa.setLunes3(rs.getString("entradaL"));
				horaa.setLunes4(rs.getString("salidaL"));
				horaa.setLunes5(rs.getString("recesoL"));
				
				horaa.setDiaM(rs.getString("diaM").trim());
				horaa.setMartes1(rs.getString("inicioM"));
				horaa.setMartes2(rs.getString("finM"));
				horaa.setMartes3(rs.getString("entradaM"));
				horaa.setMartes4(rs.getString("salidaM"));
				horaa.setMartes5(rs.getString("recesoM"));
				
				horaa.setDiaMI(rs.getString("diaMI").trim());
				horaa.setMiercoles1(rs.getString("inicioMI"));
				horaa.setMiercoles2(rs.getString("finMI"));
				horaa.setMiercoles3(rs.getString("entradaMI"));
				horaa.setMiercoles4(rs.getString("salidaMI"));
				horaa.setMiercoles5(rs.getString("recesoMI"));
				
				horaa.setDiaJ(rs.getString("diaJ").trim());
				horaa.setJueves1(rs.getString("inicioJ"));
				horaa.setJueves2(rs.getString("finJ"));
				horaa.setJueves3(rs.getString("entradaJ"));
				horaa.setJueves4(rs.getString("salidaJ"));
				horaa.setJueves5(rs.getString("recesoJ"));
				
				horaa.setDiaV(rs.getString("diaV").trim());
				horaa.setViernes1(rs.getString("inicioV"));
				horaa.setViernes2(rs.getString("finV"));
				horaa.setViernes3(rs.getString("entradaV"));
				horaa.setViernes4(rs.getString("salidaV"));
				horaa.setViernes5(rs.getString("recesoV"));
				
				horaa.setDiaS(rs.getString("diaS").trim());
				horaa.setSabado1(rs.getString("inicioS"));
				horaa.setSabado2(rs.getString("finS"));
				horaa.setSabado3(rs.getString("entradaS"));
				horaa.setSabado4(rs.getString("salidaS"));
				horaa.setSabado5(rs.getString("recesoS"));
				
				horaa.setDescanso(rs.getInt("descanso"));
				horaa.setDiaDobla(rs.getInt("dobla"));
				horaa.setDiaDobla2(rs.getInt("doblaExtra1"));
				horaa.setDiaDobla3(rs.getInt("doblaExtra2"));
				
				horaa.setHorarioDeposito(rs.getInt("horario_deposito"));
				horaa.setRecesoDiarioExtra(rs.getInt("receso_extra_diario"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return horaa;
	}
	
//	buscarHorario
	public boolean HorarioExiste(int horario){
		String query = "exec sp_folio_horario "+horario;
		
		boolean existe = false;
		Statement s;
		ResultSet rs;
		
		try {				
			s = con.conexion().createStatement();
			rs = s.executeQuery(query);
			
			while(rs.next()){
				existe = Boolean.parseBoolean(rs.getString("Existe").trim());
			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
			
		return existe;
	}
	
	public ObjHorario Horario_Nuevo() throws SQLException{
		ObjHorario horario = new ObjHorario();
		String query = "select max(folio) as 'Maximo' from tb_horarios";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			while(rs.next()){
				horario.setFolio(rs.getInt("Maximo"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return horario;
	}
			
			/**buscamos los campos llenos del arduino**/
			public Obj_Arduino Buscar_Arduino() throws SQLException{
				Obj_Arduino arduino = new Obj_Arduino();
				String query = "exec sp_hora_arduino";
				
				Statement stmt = null;
				try {
					stmt = con.conexion().createStatement();
					ResultSet rs = stmt.executeQuery(query);
					while(rs.next()){
						arduino.setMa�ana(rs.getString("ma�ana"));
						arduino.setMediodia(rs.getString("medio"));
						arduino.setTarde(rs.getString("tarde"));
					}
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
				finally{
					if(stmt!=null){stmt.close();}
				}
				return arduino;
			}
			
			
			//Buscar el nombre de la cajera de fuente de sodas
			
			public Obj_Captura_Fuente_Sodas fuente(int folio) throws SQLException{
				Obj_Captura_Fuente_Sodas sodas = new Obj_Captura_Fuente_Sodas();
				String query = "select * from tb_captura_fuente_sodas where folio ="+ folio;
				Statement stmt = null;
				try {
					stmt = con.conexion().createStatement();
					ResultSet rs = stmt.executeQuery(query);
					while(rs.next()){
						
//						txtNo_Checador_Cliente.setText(soda.getNo_cliente()+"");
//						txtClave.setText(soda.getNo_cliente()+"");
//						txtTicket.setText(soda.getTicket()+"");
//						txtImporte.setText(soda.getImporte()+"");
//						
//						lblNombre_Empleado.setText(soda.getNombre_cliente()+"");
//						lblEstablecimiento_empleado.setText(soda.getEstablecimiento_cliente()+"");
//						lblpuesto_empleado.setText(soda.getPuesto_cliente()+"");
						
//						sodas.setNombre_cajera(rs.getString("nombre_empleado"));
//						sodas.setNo_cliente(rs.getInt("no_cliente"));
//						sodas.setTicket(rs.getString("ticket"));
//						sodas.setImporte(rs.getInt("importe"));
//						
//						sodas.setNombre_cliente(rs.getString("nombre_cliente"));
//						sodas.setEstablecimiento_cliente(rs.getString("establecimiento_cliente"));
//						sodas.setPuesto_cliente(rs.getString("puesto_cliente"));
//						
						File photo = new File(System.getProperty("user.dir")+"/tmp/tmp.jpg");
						FileOutputStream fos = new FileOutputStream(photo);
						
						 byte[] buffer = new byte[1];
				            InputStream is = rs.getBinaryStream("foto");
				            while (is.read(buffer) > 0) {
				                fos.write(buffer);
				            }
				            fos.close();
						
					}
					
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
				finally{
					if(stmt!=null){stmt.close();}
				}
				return sodas;
			}
//trae el nombre del empleado para el codigo
	public void Gafetes_masivos(String listas) throws SQLException{
		String insertIds = "exec sp_insert_ids "+listas.substring(0, listas.length()-1)+";";
		System.out.println(listas.substring(0, listas.length()-1));
		String query =  "exec sp_genera_clave_checador "+listas.substring(0, listas.length()-1)+";";

		Statement stmt = null;
		
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;

		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(insertIds);
			pstmt.execute();
			con.commit();
			
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			int j = 1;
			while(rs.next()){
				new Obj_Gen_Code_Bar().Generar_Code(rs.getString("codigo"),j+"".trim());
				File photo = new File(System.getProperty("user.dir")+"/AssetGafete/Users_Images/"+j+".png");
				FileOutputStream fos = new FileOutputStream(photo);

				byte[] buffer = new byte[1];
		            InputStream is = rs.getBinaryStream("foto");
		            while (is.read(buffer) > 0) {
		                fos.write(buffer);
		            }
		            fos.close();
		    	j++;
		           
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
	}
	
	public String[][] tabla_libre_jerarquico(String nomgbre){
		String datos = "exec sp_select_tabla_alimentacion_jerarquico_libre '"+nomgbre+"';";

		String[][] Matriz = new String[getFilas(datos)][4];
		
		Statement s;
		ResultSet rs;
		try {			
			s = con.conexion().createStatement();
			rs = s.executeQuery(datos);
			
			int i=0;
			while(rs.next()){
				Matriz[i][0] = String.valueOf(i+1)+"  ";
				Matriz[i][1] = "  "+rs.getString(2);
				Matriz[i][2] = "  ";
				Matriz[i][3] = "  ";
				i++;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return Matriz;
	}
	
	public String[][] tabla_libre_jerarquico_contestada(String nomgbre){
		String datos = "exec sp_select_tabla_alimentacion_libre_jerarquico_contestada '"+nomgbre+"';";

		String[][] Matriz = new String[getFilas(datos)][4];
		
		Statement s;
		ResultSet rs;
		try {			
			s = con.conexion().createStatement();
			rs = s.executeQuery(datos);
			
			int i=0;
			while(rs.next()){
				Matriz[i][0] = String.valueOf(i+1)+"  ";
				Matriz[i][1] = "  "+rs.getString(2);
				Matriz[i][2] = "  "+rs.getString(3);
				Matriz[i][3] = "  "+rs.getString(4);
				i++;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return Matriz;
	}
	
	public String[][] tabla_libre(String nomgbre){
		String datos = "exec sp_select_tabla_alimentacion_libre '"+nomgbre+"';";

		String[][] Matriz = new String[getFilas(datos)][4];
		
		Statement s;
		ResultSet rs;
		try {			
			s = con.conexion().createStatement();
			rs = s.executeQuery(datos);
			
			int i=0;
			while(rs.next()){
				Matriz[i][0] = String.valueOf(i+1)+"  ";
				Matriz[i][1] = "  "+rs.getString(2);
				Matriz[i][2] = "  ";
				Matriz[i][3] = "  ";
				i++;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return Matriz;
	}
	
	public String[][] tabla_libre_contestada(String nomgbre){
		String datos = "exec sp_select_tabla_alimentacion_libre_contestada '"+nomgbre+"';";

		String[][] Matriz = new String[getFilas(datos)][4];
		
		Statement s;
		ResultSet rs;
		try {			
			s = con.conexion().createStatement();
			rs = s.executeQuery(datos);
			
			int i=0;
			while(rs.next()){
				Matriz[i][0] = String.valueOf(i+1)+"  ";
				Matriz[i][1] = "  "+rs.getString(2);
				Matriz[i][2] = "  "+rs.getString(3);
				Matriz[i][3] = "  "+rs.getString(4);
				i++;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return Matriz;
	}
	
	public Obj_Encargado_De_Solicitudes Emcargado_de_solicitudes(String encargado) throws SQLException{
		Obj_Encargado_De_Solicitudes objEncargado = new Obj_Encargado_De_Solicitudes();
		String query = "exec sp_select_seleccion_empleados_por_encargado '"+ encargado +"';";
		Statement stmt = null;

		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while(rs.next()){
				objEncargado.setFolio(rs.getInt("folio_encargado"));
				objEncargado.setEstablecimiento(rs.getInt("establecimiento_id"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return objEncargado;
	}
	
	public Obj_Seleccion_Operaciones Solicitud_empleado(int folio) throws SQLException{
		Obj_Seleccion_Operaciones solicitud = new Obj_Seleccion_Operaciones();
		String query = "exec sp_select_solicitud_permiso_empleado_operaciones "+ folio;
		Statement stmt = null;

		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while(rs.next()){
				
				solicitud.setFolio_solicitud(rs.getInt("folio_solicitud"));
				solicitud.setFolio_empleado(rs.getInt("folio_empleado"));
				solicitud.setNombre_empleado(rs.getString("nombre_empleado"));
				solicitud.setEstablecimiento(rs.getString("establecimiento"));
				solicitud.setPuesto(rs.getString("puesto"));
				solicitud.setDescanso(rs.getString("descanso"));
				solicitud.setDobla(rs.getString("dobla"));
				solicitud.setStatus(rs.getString("status"));
				solicitud.setTelefono(rs.getString("telefono_propio"));
				solicitud.setNombre_encargado(rs.getString("encargado"));
				solicitud.setSopeca(rs.getString("SoPeCa"));
				solicitud.setCambio(rs.getString("Cambio"));
				
				solicitud.setFecha_solicitada(rs.getString("fecha_solicitada"));
				solicitud.setTipo_solicitud(rs.getString("tipo_solicitud"));
				solicitud.setPuntualidad(rs.getInt("punt"));
				solicitud.setCumplimiento(rs.getInt("cumplimiento"));
				solicitud.setDiciplina(rs.getInt("diciplina"));
				solicitud.setRespeto(rs.getInt("respeto_gral"));
				solicitud.setMotivo(rs.getString("motivo"));
							
				File photo = new File(System.getProperty("user.dir")+"/tmp/tmp.jpg");
				FileOutputStream fos = new FileOutputStream(photo);
				
		            byte[] buffer = new byte[1];
		            InputStream is = rs.getBinaryStream("foto");
		            while (is.read(buffer) > 0) {
		                fos.write(buffer);
		            }
		            fos.close(); 
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return solicitud;
	}
	
//	buscarHorario
	public boolean Permiso_de_usuario_para_horario(int usuario){
		String query = "exec sp_permiso_a_horarios "+usuario;
		
		boolean permiso = false;
		Statement s;
		ResultSet rs;
		
		try {				
			s = con.conexion().createStatement();
			rs = s.executeQuery(query);
			
			while(rs.next()){
				permiso = Boolean.parseBoolean(rs.getString("permiso").trim());
			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
			
		return permiso;
	}
	
	public boolean permiso_nuevo(){
		String query = "select guardar_horario from tb_configuracion_sistema";
		
		boolean permiso_horario_n = false;
		Statement s;
		ResultSet rs;
		
		try {				
			s = con.conexion().createStatement();
			rs = s.executeQuery(query);
			
			while(rs.next()){
				permiso_horario_n = Boolean.parseBoolean(rs.getString("guardar_horario").trim());
			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
			
		return permiso_horario_n;
	}
	
	public Obj_Grupo_De_Vacaciones Grupo_Nuevo() throws SQLException{
		Obj_Grupo_De_Vacaciones grupo = new Obj_Grupo_De_Vacaciones();
		String query = "select max(folio) as 'Maximo' from tb_grupo_de_vacaciones";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				grupo.setFolio(rs.getInt("Maximo"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return grupo;
	}
	
	public Obj_Grupo_De_Vacaciones Grupo(int folio) throws SQLException{
		Obj_Grupo_De_Vacaciones grupo = new Obj_Grupo_De_Vacaciones();
		String query = "select * from tb_grupo_de_vacaciones where folio ="+ folio;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				grupo.setFolio(rs.getInt("folio"));
				grupo.setDescripcion(rs.getString("descripcion").trim());
				grupo.setStatus(rs.getBoolean("status"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt != null){stmt.close();}
		}
		return grupo;
	}
	
	public boolean Buscar_Grupo_De_Vacaciones(Obj_Tabla_De_Vacaciones grupo_vacacionesObj){
		String query = "exec sp_existe_tabla_grupos_de_vacaciones "+grupo_vacacionesObj.getGrupo().toUpperCase().trim()+","+
				grupo_vacacionesObj.getAnios_trabajados()+","+grupo_vacacionesObj.getDias_correspondientes()+","+
				grupo_vacacionesObj.getPrima_vacacional()+";";
		
		boolean existe = false;
		Statement s;
		ResultSet rs;
		
		try {				
			s = con.conexion().createStatement();
			rs = s.executeQuery(query);
			
			while(rs.next()){
				existe = Boolean.valueOf(rs.getString("existe").trim());
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return existe;
	}
	
	public Obj_Alimentacion_De_Vacaciones Empleado_En_Vacaciones(int folio) throws SQLException{
		Obj_Alimentacion_De_Vacaciones alimentacion_vacaciones = new Obj_Alimentacion_De_Vacaciones();
		String query = "exec sp_select_empleado_en_vacaciones "+ folio;
		Statement stmt = null;

		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while(rs.next()){
//				datos empleado	
				alimentacion_vacaciones.setFolio_vacaciones(rs.getInt("folio_vacaciones"));
				alimentacion_vacaciones.setFolio_empleado(rs.getInt("folio_empleado"));
				alimentacion_vacaciones.setEmpleado(rs.getString("empleado").trim());
				alimentacion_vacaciones.setEstablecimiento(rs.getString("establecimiento").trim());
				alimentacion_vacaciones.setPuesto(rs.getString("puesto").trim());
				alimentacion_vacaciones.setFecha_ingreso(rs.getString("fecha_ingreso").trim());
				alimentacion_vacaciones.setFecha_ingreso_imss(rs.getString("fecha_ingreso_imss").trim());
				alimentacion_vacaciones.setSalario_diario_integrado(rs.getFloat("salario_diario_integrado"));
				alimentacion_vacaciones.setGrupo_vacacional(rs.getString("tipo_de_vacaciones"));
				
				File photo = new File(System.getProperty("user.dir")+"/tmp/tmp.jpg");
				FileOutputStream fos = new FileOutputStream(photo);
				
		            byte[] buffer = new byte[1];
		            InputStream is = rs.getBinaryStream("foto");
		            while (is.read(buffer) > 0) {
		                fos.write(buffer);
		            }
		            fos.close();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return alimentacion_vacaciones;
	}
	
	public Obj_Alimentacion_De_Vacaciones calcular_vacaciones(int folio_empleado, int anios_disfritar, String fecha_inicio) throws SQLException{
		Obj_Alimentacion_De_Vacaciones alimentacion_vacaciones = new Obj_Alimentacion_De_Vacaciones();
		String query = "exec sp_select_empleado_en_vacaciones "+ folio_empleado;
		Statement stmt = null;

		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while(rs.next()){
//				datos empleado	
				alimentacion_vacaciones.setFolio_vacaciones(rs.getInt("folio_vacaciones"));
				alimentacion_vacaciones.setFolio_empleado(rs.getInt("folio_empleado"));
				alimentacion_vacaciones.setEmpleado(rs.getString("empleado").trim());
				alimentacion_vacaciones.setEstablecimiento(rs.getString("establecimiento").trim());
				alimentacion_vacaciones.setPuesto(rs.getString("puesto").trim());
				alimentacion_vacaciones.setFecha_ingreso(rs.getString("fecha_ingreso").trim());
				alimentacion_vacaciones.setFecha_ingreso_imss(rs.getString("fecha_ingreso_imss").trim());
				alimentacion_vacaciones.setSalario_diario_integrado(rs.getFloat("salario_diario_integrado"));
				alimentacion_vacaciones.setGrupo_vacacional(rs.getString("tipo_de_vacaciones"));
				
				File photo = new File(System.getProperty("user.dir")+"/tmp/tmp.jpg");
				FileOutputStream fos = new FileOutputStream(photo);
				
		            byte[] buffer = new byte[1];
		            InputStream is = rs.getBinaryStream("foto");
		            while (is.read(buffer) > 0) {
		                fos.write(buffer);
		            }
		            fos.close();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return alimentacion_vacaciones;
	}
	
	public Obj_Alimentacion_De_Vacaciones empleado_para_ultimas_vacaciones(int folio_empleado) throws SQLException{
		Obj_Alimentacion_De_Vacaciones alimentacion_vacaciones = new Obj_Alimentacion_De_Vacaciones();
		String query = "exec sp_select_empleado_para_vacaciones_pasadas "+ folio_empleado;
		Statement stmt = null;

		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while(rs.next()){
//				datos empleado	
				alimentacion_vacaciones.setFolio_empleado(rs.getInt("folio_empleado"));
				alimentacion_vacaciones.setEmpleado(rs.getString("empleado").trim());
				alimentacion_vacaciones.setEstablecimiento(rs.getString("establecimiento").trim());
				alimentacion_vacaciones.setPuesto(rs.getString("puesto").trim());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return alimentacion_vacaciones;
	}
}
