package catalogos;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableColumn;

import SQL.Connexion;

import objetos.Obj_Alimentacion_Cuadrante;

@SuppressWarnings("serial")
public class Cat_Alimentacion_Cuadrante extends JFrame {

	private final String NOMBRECOMPLETO;
	
	JLabel lblCuadranteLleno = new JLabel("<Cuadrante Concluido>");
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	JButton btnGuardarLibre = new JButton("Guardar");
	JButton btnGuardarMultiple = new JButton("Guardar");
	JButton btnGuardarMultipleConts = new JButton("Guardar");
	JButton btnEditarMultipleConts = new JButton("Editar");

	/* OPCION MEDIA MULTIPLE JERARQUICA */
	Cat_Plantilla_Tabla_Cuadrante plantillaMultipleJerarquica = new Cat_Plantilla_Tabla_Cuadrante();
	
	/* OPCION MULTIPLE */
	Cat_Plantilla_Tabla_Cuadrante plantillaMultiple = new Cat_Plantilla_Tabla_Cuadrante();
	
	/* OPCION MULTIPLE CONTESTADAS */
	Cat_Plantilla_Tabla_Cuadrante plantillaMultipleContestada = new Cat_Plantilla_Tabla_Cuadrante();
	
	/* OPCION LIBRE */
	Cat_Plantilla_Tabla_Cuadrante plantillaLibre = new Cat_Plantilla_Tabla_Cuadrante();
	
	JTextField txtNombre_Completo 	= new JTextField();
	JTextField txtPuesto 			= new JTextField();
	JTextField txtEstablecimiento	= new JTextField();
	JTextField txtEquipo_Trabajo 	= new JTextField();
	JTextField txtJefatura 			= new JTextField();
	JTextField txtDia 				= new JTextField();
	JTextField txtFecha 			= new JTextField();
	JTextField txtCuadrante 		= new JTextField();
	
	@SuppressWarnings("rawtypes")
	JComboBox cmbMultiple = new JComboBox();
	
	JButton btnSalir = new JButton("Salir");
	JButton btnTerminarCaptura = new JButton("Terminar Captura");
	JButton btnFoto = new JButton();
	
	JLayeredPane panelMultiple = new JLayeredPane();
	JLayeredPane panelMultipleContes = new JLayeredPane();
	JLayeredPane panelLibre    = new JLayeredPane();
	
	
	JTabbedPane paneles = new JTabbedPane();
	
	public Cat_Alimentacion_Cuadrante(String Nombre_Usuario)	{
		NOMBRECOMPLETO = Nombre_Usuario;
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Iconos/cuadrante_captura_icon&16.png"));
		this.setTitle("Alimentaci�n de Cuadrante");
		this.panel.setBorder(BorderFactory.createTitledBorder("Alimentaci�n de Cuadrante"));
		
		this.panel.add(new JLabel("Nombre:")).setBounds(40,30,50,20);
		this.panel.add(txtNombre_Completo).setBounds(150,30,250,20);
		
		this.panel.add(btnTerminarCaptura).setBounds(750,30,100,20);
		this.panel.add(btnSalir).setBounds(750,60,100,20);
		
		this.panel.add(new JLabel("Puesto:")).setBounds(40,60,50,20);
		this.panel.add(txtPuesto).setBounds(150,60,250,20);
		
		this.panel.add(new JLabel("Establecimiento:")).setBounds(40,90,80,20);
		this.panel.add(txtEstablecimiento).setBounds(150,90,250,20);
		
		this.panel.add(new JLabel("Equipo De Trabajo:")).setBounds(40,120,100,20);
		this.panel.add(txtEquipo_Trabajo).setBounds(150,120,250,20);
		
		this.panel.add(new JLabel("Jefatura:")).setBounds(40,150,50,20);
		this.panel.add(txtJefatura).setBounds(150,150,250,20);
		
		this.panel.add(new JLabel("Fecha:")).setBounds(40,180,40,20);
		this.panel.add(txtFecha).setBounds(150,180,100,20);
		
		this.panel.add(new JLabel("Dia:")).setBounds(260,180,40,20);
		this.panel.add(txtDia).setBounds(300,180,100,20);
		
		this.panel.add(new JLabel("Cuadrante:")).setBounds(40,210,60,20);
		this.panel.add(txtCuadrante).setBounds(150,210,250,20);
	
		this.panel.add(btnFoto).setBounds(470,30,235,200);
		
		this.panel.add(lblCuadranteLleno).setBounds(530,230,235,20);
	
		this.paneles.addTab("Actividades de Rutina", panelMultiple);
		this.paneles.addTab("Actividades de Rutina Contestadas", panelMultipleContes);
		this.paneles.addTab("Actividades por Avance", panelLibre);
		
		this.panelMultiple.add(btnGuardarMultiple).setBounds(10,10,115,20);
		this.panelMultipleContes.add(btnGuardarMultipleConts).setBounds(10,10,75,20);
		this.panelMultipleContes.add(btnEditarMultipleConts).setBounds(90,10,75,20);
		this.panelLibre.add(btnGuardarLibre).setBounds(10,10,115,20);
		
		this.panelMultiple.add(plantillaMultipleJerarquica.scrollMultiple).setBounds(5,35,905,150);
		this.panelMultiple.add(plantillaMultiple.scrollMultiple).setBounds(5,200,905,165);
		this.panelMultipleContes.add(plantillaMultipleContestada.scrollMultiple).setBounds(5, 35, 905, 330);
		this.panelLibre.add(plantillaLibre.scrollLibre).setBounds(5,35,905,330);
		
		this.panel.add(paneles).setBounds(8,240,920,400);
		
		this.lblCuadranteLleno.setVisible(false);
		this.lblCuadranteLleno.setForeground(Color.BLUE);
		
		this.btnSalir.addActionListener(salir);
		this.btnGuardarMultiple.addActionListener(op_guardar_multiple);
		this.btnGuardarMultipleConts.addActionListener(op_guardar_multiple_conts);
		this.btnEditarMultipleConts.addActionListener(op_editar_multiple_conts);
		this.btnTerminarCaptura.addActionListener(op_terminar_captura);

		this.CamposEnabled(false);
		
		this.init(Nombre_Usuario);
		this.init_tabla_multiple_conts();

		this.cont.add(panel);
		this.setResizable(false);
		
		
		this.setSize(940,680);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	ActionListener op_terminar_captura = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(new Obj_Alimentacion_Cuadrante().terminar_captura(NOMBRECOMPLETO)){
				JOptionPane.showMessageDialog(null,"Se ha teminado la edici�n del cuadrante", "Aviso", JOptionPane.INFORMATION_MESSAGE);
				while(plantillaMultipleJerarquica.tablaPlantillaMultiple.getRowCount() > 0)
					plantillaMultipleJerarquica.modelPlantillaMultiple.removeRow(0);
				while(plantillaMultiple.tablaPlantillaMultiple.getRowCount() > 0)
					plantillaMultiple.modelPlantillaMultiple.removeRow(0);
				while(plantillaMultipleContestada.tablaPlantillaMultiple.getRowCount() > 0){
					plantillaMultipleContestada.modelPlantillaMultiple.removeRow(0);
				}
				tablas_procesar();
			}else{
				JOptionPane.showMessageDialog(null, "No se pudo terminar la edici�n del cuadrante", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	};
	
	public void init_tabla_multiple_conts(){
		this.plantillaMultipleContestada.tablaPlantillaMultiple.setEnabled(false);
		this.btnGuardarMultipleConts.setEnabled(false);
		
	}
	
	ActionListener op_editar_multiple_conts = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			plantillaMultipleContestada.tablaPlantillaMultiple.setEnabled(true);
			btnGuardarMultipleConts.setEnabled(true);
		}
	};
	
	public void init(String Nombre_Usuario){
		Obj_Alimentacion_Cuadrante datos_cuadrante = new Obj_Alimentacion_Cuadrante().buscarEmpleado(Nombre_Usuario);

		txtNombre_Completo.setText(datos_cuadrante.getNombre());
		txtPuesto.setText(datos_cuadrante.getPuesto());
		txtEstablecimiento.setText(datos_cuadrante.getEstablecimiento());
		txtEquipo_Trabajo.setText(datos_cuadrante.getEquipo_trabajo());
		txtJefatura.setText(datos_cuadrante.getJefatura());
		txtFecha.setText(datos_cuadrante.getFecha());
		txtDia.setText(datos_cuadrante.getDia());
		txtCuadrante.setText(datos_cuadrante.getCuadrante());
		
		ImageIcon tmpIconAux = new ImageIcon(System.getProperty("user.dir")+"/tmp/tmp_cuadrante/tmp.jpg");
	    btnFoto.setIcon(new ImageIcon(tmpIconAux.getImage().getScaledInstance(230, 195, Image.SCALE_DEFAULT)));	
	    
	    tablas_procesar();
		
	}
	
	public void tablas_procesar(){
		
		if(new Obj_Alimentacion_Cuadrante().status_llanado_tabla(NOMBRECOMPLETO)){
			JOptionPane.showMessageDialog(null, "En buena hora!!! \n Ya llen� su cuadrante.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
			lblCuadranteLleno.setVisible(true);
			
		}else{
			
			String[][] info_tabla_multiple_jerarquico = new Obj_Alimentacion_Cuadrante().buscarTablaMultipleJerarquico(NOMBRECOMPLETO);
	    	
			String [] fila_multiple_jerarquico = new String[4];
			
			List<String[]> lista_jerarquico = new ArrayList<String[]>();
			
			for(int i=0; i<info_tabla_multiple_jerarquico.length; i++){
		    
				lista_jerarquico.add(new Obj_Alimentacion_Cuadrante().ComboBox(Integer.parseInt(info_tabla_multiple_jerarquico[i][0].toString())));
	            	            
	            TableColumn col = plantillaMultipleJerarquica.tablaPlantillaMultiple.getColumnModel().getColumn(2);
	            
	            col.setCellEditor(new MyComboEditor(lista_jerarquico));
	            
	            fila_multiple_jerarquico[0]= info_tabla_multiple_jerarquico[i][1]+"  ";
	            fila_multiple_jerarquico[1]= "   "+info_tabla_multiple_jerarquico[i][2];
	            fila_multiple_jerarquico[2]= "   Respuestas";
	            fila_multiple_jerarquico[3]= "   ";
				
				plantillaMultipleJerarquica.modelPlantillaMultiple.addRow(fila_multiple_jerarquico);
			}
			
			String[][] info_tabla_multiple = new Obj_Alimentacion_Cuadrante().buscarTablaMultiple(NOMBRECOMPLETO);
	    	
			String [] fila_multiple = new String[4];
			
			List<String[]> lista = new ArrayList<String[]>();
			
			for(int i=0; i<info_tabla_multiple.length; i++){
		    
	            lista.add(new Obj_Alimentacion_Cuadrante().ComboBox(Integer.parseInt(info_tabla_multiple[i][0].toString())));
	            	            
	            TableColumn col = plantillaMultiple.tablaPlantillaMultiple.getColumnModel().getColumn(2);
	            
	            col.setCellEditor(new MyComboEditor(lista));
	            
				fila_multiple[0]= info_tabla_multiple[i][1]+"  ";
				fila_multiple[1]= "   "+info_tabla_multiple[i][2];
				fila_multiple[2]= "   Respuestas";
				fila_multiple[3]= "   ";
				
				plantillaMultiple.modelPlantillaMultiple.addRow(fila_multiple);
			}
			
			String[][] info_tabla_multiple_capturada = new Obj_Alimentacion_Cuadrante().buscarTablaMultipleCapturada(NOMBRECOMPLETO);
			String [] fila_multiple_capturada = new String[6];
			List<String[]> listaCapturada = new ArrayList<String[]>();			
			
			for(int i=0; i<info_tabla_multiple_capturada.length; i++){
			    
				listaCapturada.add(new Obj_Alimentacion_Cuadrante().ComboBox(Integer.parseInt(info_tabla_multiple_capturada[i][0].toString())));
	            	            
	            TableColumn col = plantillaMultipleContestada.tablaPlantillaMultiple.getColumnModel().getColumn(2);
	            
	            col.setCellEditor(new MyComboEditor(listaCapturada));
	            
	            fila_multiple_capturada[0]= info_tabla_multiple_capturada[i][1]+"  ";
	            fila_multiple_capturada[1]= "   "+info_tabla_multiple_capturada[i][2];
	            fila_multiple_capturada[2]= "   "+info_tabla_multiple_capturada[i][3];
	            fila_multiple_capturada[3]= "   "+info_tabla_multiple_capturada[i][4];
				
				plantillaMultipleContestada.modelPlantillaMultiple.addRow(fila_multiple_capturada);
			}
		}
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
	
	private class MyComboEditor extends DefaultCellEditor{
        List<String[]> values;
        @SuppressWarnings("rawtypes")
		public MyComboEditor(List<String[]> values){
        	super(new JComboBox());
            this.values = values;
        }
         
        @SuppressWarnings({ "rawtypes", "unchecked" })
		public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
        	JComboBox combo = (JComboBox)getComponent();
        	
        	combo.removeAllItems();
            String[] valores = values.get(row);
                
            for(int i=0; i<valores.length; i++){
            	combo.addItem(valores[i]);
            }

            return combo;          
        }
    }
	
	public int getFilas(String qry){
		int filas=0;
		try {
			Statement s = new Connexion().conexion().createStatement();
			ResultSet rs = s.executeQuery(qry);
			while(rs.next()){
				filas++;
			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return filas;
	}	
	
	ActionListener op_guardar_multiple = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(plantillaMultiple.tablaPlantillaMultiple.isEditing())
				plantillaMultiple.tablaPlantillaMultiple.getCellEditor().stopCellEditing();
			
			if(celdasVaciasMultiple().equals("")){
				if(new Obj_Alimentacion_Cuadrante().guardar(tabla_multiple())){
					JOptionPane.showMessageDialog(null, "El registro se guard� con exito!" , "Aviso", JOptionPane.INFORMATION_MESSAGE);
					while(plantillaMultiple.tablaPlantillaMultiple.getRowCount() > 0)
						plantillaMultiple.modelPlantillaMultiple.removeRow(0);
					while(plantillaMultipleContestada.tablaPlantillaMultiple.getRowCount() > 0)
						plantillaMultipleContestada.modelPlantillaMultiple.removeRow(0);
					tablas_procesar();
					return;
				}else{
					JOptionPane.showMessageDialog(null, "Ocurri� un problema al tratar de almacenar el registro" , "Aviso", JOptionPane.ERROR_MESSAGE);
					return;
				}
			}else{
				JOptionPane.showMessageDialog(null, "Los siguientes campos son requeridos\n"+celdasVaciasLibre() +celdasVaciasMultiple() , "Aviso", JOptionPane.WARNING_MESSAGE);
				return;
			}
		}
	};
	
	ActionListener op_guardar_multiple_conts = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(plantillaMultipleContestada.tablaPlantillaMultiple.isEditing())
				plantillaMultipleContestada.tablaPlantillaMultiple.getCellEditor().stopCellEditing();
			
			if(celdasVaciasMultipleConts().equals("")){
				if(new Obj_Alimentacion_Cuadrante().guardar(tabla_multiple_conts())){
					plantillaMultipleContestada.tablaPlantillaMultiple.setEnabled(false);
					btnGuardarMultipleConts.setEnabled(false);
					JOptionPane.showMessageDialog(null, "El registro se guard� con exito!" , "Aviso", JOptionPane.INFORMATION_MESSAGE);
					return;
				}else{
					JOptionPane.showMessageDialog(null, "Ocurri� un problema al tratar de almacenar el registro" , "Aviso", JOptionPane.ERROR_MESSAGE);
					return;
				}
			}else{
				JOptionPane.showMessageDialog(null, "Los siguientes campos son requeridos\n"+celdasVaciasLibre() +celdasVaciasMultiple() , "Aviso", JOptionPane.WARNING_MESSAGE);
				return;
			}
		}
	};
	
	public Object[][] tabla_multiple(){
		Object[][] multiple = new Object[plantillaMultiple.tablaPlantillaMultiple.getRowCount()][11];
		try {
			String HOST  = InetAddress.getLocalHost().getHostName();
			String IP = InetAddress.getLocalHost().getHostAddress();
			for(int i = 0; i<plantillaMultiple.tablaPlantillaMultiple.getRowCount(); i++){
				multiple[i][0] = txtCuadrante.getText().toString().trim();
				multiple[i][1] = txtNombre_Completo.getText().toString().trim();
				multiple[i][2] = txtEstablecimiento.getText().toString().trim();
				multiple[i][3] = txtPuesto.getText().toString().trim();
				multiple[i][4] = HOST; 
				multiple[i][5] = IP;
				multiple[i][6] = plantillaMultiple.modelPlantillaMultiple.getValueAt(i,0).toString().trim();
				multiple[i][7] = plantillaMultiple.modelPlantillaMultiple.getValueAt(i,1).toString().trim();
				multiple[i][8] = plantillaMultiple.modelPlantillaMultiple.getValueAt(i,2).toString().trim();
				multiple[i][9] = plantillaMultiple.modelPlantillaMultiple.getValueAt(i,3).toString().trim();
				
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return multiple;
	}
	
	public Object[][] tabla_multiple_conts(){
		Object[][] multiple = new Object[plantillaMultipleContestada.tablaPlantillaMultiple.getRowCount()][11];
		try {
			String HOST  = InetAddress.getLocalHost().getHostName();
			String IP = InetAddress.getLocalHost().getHostAddress();
			for(int i = 0; i<plantillaMultipleContestada.tablaPlantillaMultiple.getRowCount(); i++){
				multiple[i][0] = txtCuadrante.getText().toString().trim();
				multiple[i][1] = txtNombre_Completo.getText().toString().trim();
				multiple[i][2] = txtEstablecimiento.getText().toString().trim();
				multiple[i][3] = txtPuesto.getText().toString().trim();
				multiple[i][4] = HOST; 
				multiple[i][5] = IP;
				multiple[i][6] = plantillaMultipleContestada.modelPlantillaMultiple.getValueAt(i,0).toString().trim();
				multiple[i][7] = plantillaMultipleContestada.modelPlantillaMultiple.getValueAt(i,1).toString().trim();
				multiple[i][8] = plantillaMultipleContestada.modelPlantillaMultiple.getValueAt(i,2).toString().trim();
				multiple[i][9] = plantillaMultipleContestada.modelPlantillaMultiple.getValueAt(i,3).toString().trim();
				
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return multiple;
	}
	
	public Object[][] tabla_libre(){
		Object[][] libre = new Object[plantillaLibre.tablaPlantillaLibre.getRowCount()][3];
		
		for(int i = 0; i<plantillaLibre.tablaPlantillaLibre.getRowCount(); i++){
			libre[i][0] = plantillaLibre.modelPlantillaLibre.getValueAt(i,0).toString().trim();
			libre[i][1] = plantillaLibre.modelPlantillaLibre.getValueAt(i,2).toString().trim();
			libre[i][2] = plantillaLibre.modelPlantillaLibre.getValueAt(i,3).toString().trim();
		}
		return libre;
	}
	
	public String celdasVaciasMultiple(){
		String error = "";
		
		for(int i=0; i<plantillaMultiple.tablaPlantillaMultiple.getRowCount(); i++){
			if(plantillaMultiple.modelPlantillaMultiple.getValueAt(i, 2).toString().trim().equals("Respuestas")){
				error += "   La actividad con el folio: [ "+plantillaMultiple.modelPlantillaMultiple.getValueAt(i,0).toString().trim()+" ] en actividades de rutina no tiene una respuesta asignada.        \n";
			}
		}		
		return error;
	}
	
	public String celdasVaciasMultipleConts(){
		String error = "";
		
		for(int i=0; i<plantillaMultipleContestada.tablaPlantillaMultiple.getRowCount(); i++){
			if(plantillaMultipleContestada.modelPlantillaMultiple.getValueAt(i, 2).toString().trim().equals("Respuestas")){
				error += "   La actividad con el folio: [ "+plantillaMultipleContestada.modelPlantillaMultiple.getValueAt(i,0).toString().trim()+" ] en actividades de rutina no tiene una respuesta asignada.        \n";
			}
		}		
		return error;
	}
	
	public String celdasVaciasLibre(){
		String error="";
		for(int i=0; i<plantillaLibre.tablaPlantillaLibre.getRowCount(); i++){
			if(plantillaLibre.modelPlantillaLibre.getValueAt(i,2).toString().trim().equals("")){
				error += "   La actividad con el folio: [ "+plantillaLibre.modelPlantillaLibre.getValueAt(i,0).toString().trim()+" ] en actividades por avance no tiene una respuesta asignada.        \n";
			}
		}
		
		return error;
	}
	
	public void CamposLimpiar()	{
		txtNombre_Completo.setText("");
		txtPuesto.setText("");
		txtEstablecimiento.setText("");
		txtEquipo_Trabajo.setText("");
		txtJefatura.setText("");
		txtFecha.setText("");
		txtDia.setText("");
		txtCuadrante.setText("");
	}
	
	public void CamposEnabled(boolean variable){
		txtNombre_Completo.setEditable(variable);
		txtPuesto.setEditable(variable);
		txtEstablecimiento.setEditable(variable);
		txtEquipo_Trabajo.setEditable(variable);
		txtJefatura.setEditable(variable);
		txtFecha.setEditable(variable);
		txtDia.setEditable(variable);
		txtCuadrante.setEditable(variable);
	}
	
	ActionListener salir = new ActionListener() {
		public void actionPerformed(ActionEvent arg0)  {
			dispose();
		}
	};
	
}
