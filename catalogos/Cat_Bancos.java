package catalogos;

import izagar.Cat_IZAGAR_Pasar_Netos_De_Nomina_A_Bancos;
import izagar.Cat_IZAGAR_Selecionar_Nomina_Para_Netos;

import java.awt.Component;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;

import Objetos_IZAGAR.Obj_IZAGAR_Netos_Nominas;

import objetos.Obj_Bancos;

@SuppressWarnings("serial")
public class Cat_Bancos extends Cat_Root {
	public static JCheckBox chbHabilitarBanamex = new JCheckBox("Habilitar");	
	public static JCheckBox chbHabilitarBanorte = new JCheckBox("Habilitar");
	public JCheckBox chbNegativos = new JCheckBox("Valores Negativos");
	
	public JTextField txtBanamex = new JTextField();
	public JTextField txtBanorte = new JTextField();
	public JTextField txtTotales = new JTextField();
	    
	public static DefaultTableModel tabla_model = new DefaultTableModel(new Obj_Bancos().get_tabla_model(),
            new String[]{"Folio", "Nombre Completo", "Establecimientos", "Banamex", "Banorte", "Total a Pagar" }
			){
//		@SuppressWarnings({ "unchecked", "rawtypes" })
//		public Class getColumnClass(int columnIndex) {
//			return types[columnIndex];
//		}
        public boolean isCellEditable(int fila, int columna){
        	switch(columna){
        		case 0 : return false; 
        	 	case 1 : return false; 
        	 	case 2 : return false; 
        	 	case 3 :
    	 	if(chbHabilitarBanamex.isSelected()){
    	 				if(tabla_model.getValueAt(fila,4).toString().length() != 0){
    	 					return false;
    	 				}else{
        	 				return true;
    	 				}        	 				
    	 			 }else{
    	 				 return false;
    	 			}        	 			
        	 	case 4 : 
    	 			if(chbHabilitarBanorte.isSelected()){
    	 				if(tabla_model.getValueAt(fila,3).toString().length() != 0){
    	 					return false;
    	 				}else{
        	 				return true;
    	 				}
    	 			 }else{
    	 				 return false;
    	 			 }
        	 	case 5 : 
        	 		return false;

        	 } 				
 			return false;
 		}
                
	};
	
	public static JTable tabla = new JTable(tabla_model);
	public JScrollPane scroll_tabla = new JScrollPane(tabla);
	
	JButton btn_lay_out = new JButton(new ImageIcon("Iconos/PAG.png"));
	JButton btn_123 = new JButton(new ImageIcon("Iconos/calendar_icon&16.png"));
    
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public TableRowSorter trsfiltro = new TableRowSorter(tabla_model); 
		
    public Cat_Bancos(){
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Iconos/money_icon&16.png"));
		this.setTitle("Bancos");
			
		this.panel.add(cmbEstablecimientos).setBounds(463,35,210,20);
		this.panel.add(chbHabilitarBanamex).setBounds(700,35,90,20);
		this.panel.add(chbHabilitarBanorte).setBounds(820,35,90,20);
		this.panel.add(chbNegativos).setBounds(920,35,120,20);
		
		this.panel.add(scroll_tabla).setBounds(30,60,1035,615);
		this.panel.add(btn_lay_out).setBounds(1015,0,25,25);
		this.panel.add(btn_123).setBounds(1040,0,25,25);
		
		this.panel.add(new JLabel("Total Banamex:")).setBounds(1080,70,100,20);
		this.panel.add(txtBanamex).setBounds(1160,70,120,20);
		this.txtBanamex.setEditable(false);
		this.txtBanamex.setFont(new Font("",0,14));
		
		this.panel.add(new JLabel("Total Banorte:")).setBounds(1080,95,250,20);
		this.panel.add(txtBanorte).setBounds(1160,95,120,20);
		this.txtBanorte.setEditable(false);
		this.txtBanorte.setFont(new Font("",0,14));
		
		this.panel.add(new JLabel("Totales:")).setBounds(1080,120,250,20);
		this.panel.add(txtTotales).setBounds(1160,120,120,20);
		this.txtTotales.setEditable(false);
		this.txtTotales.setFont(new Font("",0,14));
		
		this.cont.add(panel);
		
		this.init_tabla();
		
		this.btn_guardar.addActionListener(op_guardar);
		this.btn_lay_out.addActionListener(op_lay_out);
		this.btn_123.addActionListener(op_123);
		
		this.btn_refrescar.setVisible(false);
			
		btn_lay_out.setToolTipText("Generar Lay Out");
		btn_123.setToolTipText("Comparacion");		
		
		this.txtFolio.addKeyListener(op_filtro_folio);
		this.txtNombre_Completo.addKeyListener(op_filtro_nombre);
		this.cmbEstablecimientos.addActionListener(op_filtro_establecimiento);
		this.chbNegativos.addActionListener(op_negativos);
		
		this.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds()); 
		this.setLocationRelativeTo(null);
		this.addWindowListener(op_cerrar);
//		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
	}
    
    WindowListener op_cerrar = new WindowListener() {
		public void windowOpened(WindowEvent e) {}
		public void windowIconified(WindowEvent e) {}
		public void windowDeiconified(WindowEvent e) {}
		public void windowDeactivated(WindowEvent e) {}
		public void windowClosing(WindowEvent e) {
			if(JOptionPane.showConfirmDialog(null, "�Desea guardar antes de cerrar?", "Aviso!", JOptionPane.YES_NO_OPTION) == 0){
				new Obj_Bancos().guardar(tabla_guardar());
			}
		}
		public void windowClosed(WindowEvent e) {}
		public void windowActivated(WindowEvent e) {}
	};
	
    ActionListener op_guardar = new ActionListener() {
		@SuppressWarnings("unchecked")
		public void actionPerformed(ActionEvent arg0) {
			trsfiltro.setRowFilter(RowFilter.regexFilter("", 0));
			trsfiltro.setRowFilter(RowFilter.regexFilter("", 1));
			trsfiltro.setRowFilter(RowFilter.regexFilter("", 2));
			trsfiltro.setRowFilter(RowFilter.regexFilter("", 3));
			
			txtFolio.setText("");
			txtNombre_Completo.setText("");
			cmbEstablecimientos.setSelectedIndex(0);
			chbNegativos.setSelected(false);
			
			if(tabla.isEditing()){
				tabla.getCellEditor().stopCellEditing();
			}
		
			if(valida_tabla() != ""){
				JOptionPane.showMessageDialog(null, "Las siguientes celdas est�n mal en su formato:\n"+valida_tabla(),"Error",JOptionPane.ERROR_MESSAGE);
				return;
			}else{
				if(JOptionPane.showConfirmDialog(null, "�Desea guardar la lista de bancos?") == 0){
					Obj_Bancos banco = new Obj_Bancos();
					if(banco.guardar(tabla_guardar())){
						txtBanamex.setText("$ "+returnBanamex());
						txtBanorte.setText("$ "+returnBanorte());
						txtTotales.setText("$ "+returnTotales());
						JOptionPane.showMessageDialog(null, "La tabla bancos se guard� exitosamente","Aviso",JOptionPane.INFORMATION_MESSAGE);
						return;
					}else{
						JOptionPane.showMessageDialog(null, "Ocurri� un error al intentar guardar la tabla","Error",JOptionPane.ERROR_MESSAGE);
						return;
					}
				}else{
					return;
				}
			}
		}
	};
	
    ActionListener op_lay_out = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			new Cat_Lay_Out().setVisible(true);
		}
	};
	
	   ActionListener op_123 = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new filtroSeleccion().setVisible(true);
			}
		};
	
		public Object[][] tabla_guardar(){
			Object[][] matriz = new Object[tabla.getRowCount()][6];
			for(int i=0; i<tabla.getRowCount(); i++){
				for(int j=0; j<tabla.getColumnCount()-1; j++){
					switch(j){
						case 0: 
							matriz[i][j] = Integer.parseInt(tabla_model.getValueAt(i,j).toString().trim());
							break;
						case 1: 
							matriz[i][j] = tabla_model.getValueAt(i,j).toString().trim();
							break;
						case 2: 
							matriz[i][j] = tabla_model.getValueAt(i,j).toString().trim();
							break;
						case 3: 
							if(tabla_model.getValueAt(i,j).toString().equals("")){
								matriz[i][j] = Float.parseFloat("0.0");
							}else{
								matriz[i][j] = Float.parseFloat(tabla_model.getValueAt(i,j).toString());
							}
							break;
						case 4: 
							if(tabla_model.getValueAt(i,j).toString().equals("")){
								matriz[i][j] = Float.parseFloat("0.0");
							}else{
								matriz[i][j] = Float.parseFloat(tabla_model.getValueAt(i,j).toString());
							}
							break;
					}
				}
			}
			return matriz;
		}
	
		public String valida_tabla(){
			String error = "";
			for(int i=0; i<tabla.getRowCount(); i++){
				for(int j=3; j<tabla.getColumnCount()-1; j++){
					try{
						if(!isNumeric(tabla_model.getValueAt(i,j).toString())){
							switch(j){
								case 3: 
									error += "   La celda de la columna Banamex no es un n�mero en el [Folio: "+tabla_model.getValueAt(i,0)+"]\t\n";
								break;
								case 4:
									error += "   La celda de la columna Banorte no es un n�mero en el [Folio: "+tabla_model.getValueAt(i,0)+"]\t\n";
								break;
							}
						}
					} catch(Exception e){
						JOptionPane.showMessageDialog(null, "La tabla tiene una celda con texto en lugar de un valor num�rico: \n"+e,"Error",JOptionPane.ERROR_MESSAGE);
					}
				}
			}
			return error;
		}
		
    @SuppressWarnings({ "unchecked", "static-access" })
	public void init_tabla(){
    	this.tabla.getTableHeader().setReorderingAllowed(false) ;
    	
    	this.tabla.getColumnModel().getColumn(0).setMaxWidth(72);
    	this.tabla.getColumnModel().getColumn(0).setMinWidth(72);		
    	this.tabla.getColumnModel().getColumn(1).setMaxWidth(360);
    	this.tabla.getColumnModel().getColumn(1).setMinWidth(360);
    	this.tabla.getColumnModel().getColumn(2).setMaxWidth(210);
    	this.tabla.getColumnModel().getColumn(2).setMinWidth(210);
    	this.tabla.getColumnModel().getColumn(3).setMaxWidth(120);
    	this.tabla.getColumnModel().getColumn(3).setMinWidth(120);
    	this.tabla.getColumnModel().getColumn(4).setMaxWidth(120);
    	this.tabla.getColumnModel().getColumn(4).setMinWidth(120);		
    	this.tabla.getColumnModel().getColumn(5).setMaxWidth(130);
    	this.tabla.getColumnModel().getColumn(5).setMinWidth(130);
    	
		TableCellRenderer render = new TableCellRenderer() { 
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, 
				boolean hasFocus, int row, int column) { 
					JLabel lbl = new JLabel(value == null? "": value.toString());
					if(row%2==0){
							lbl.setOpaque(true); 
							lbl.setBackground(new java.awt.Color(177,177,177));
					} 
					
					if(table.getSelectedRow() == row){
						lbl.setOpaque(true); 
						lbl.setBackground(new java.awt.Color(186,143,73));
					}
					
					switch(column){
						case 0 : lbl.setHorizontalAlignment(SwingConstants.RIGHT); break;
						case 1 : lbl.setHorizontalAlignment(SwingConstants.LEFT); break;
						case 2 : lbl.setHorizontalAlignment(SwingConstants.LEFT); break;
						case 3 : lbl.setHorizontalAlignment(SwingConstants.RIGHT); break;
						case 4 : lbl.setHorizontalAlignment(SwingConstants.RIGHT); break;
						case 5 : lbl.setHorizontalAlignment(SwingConstants.RIGHT); break;
					}
				return lbl; 
				} 
			}; 

		for(int x = 0; x<tabla.getColumnCount(); x++){
			this.tabla.getColumnModel().getColumn(x).setCellRenderer(render); 
		}
		
		this.tabla.setRowSorter(trsfiltro);  
		
		this.txtBanamex.setText("$ "+returnBanamex());
		this.txtBanorte.setText("$ "+returnBanorte());
		this.txtTotales.setText("$ "+returnTotales());
				
    }
    
    public static boolean isNumeric(String cadena){
    	try {
    		if(cadena.equals("")){
    			return true;
    		}else{
    			Float.parseFloat(cadena);
        		return true;
    		}
    	} catch (NumberFormatException nfe){
    		return false;
    	}
    }
    
    KeyListener op_filtro_folio = new KeyListener(){
		@SuppressWarnings("unchecked")
		public void keyReleased(KeyEvent arg0) {
			trsfiltro.setRowFilter(RowFilter.regexFilter(txtFolio.getText(), 0));
		}
		public void keyTyped(KeyEvent arg0) {
			char caracter = arg0.getKeyChar();
			if(((caracter < '0') ||
				(caracter > '9')) &&
			    (caracter != KeyEvent.VK_BACK_SPACE)){
				arg0.consume(); 
			}	
		}
		public void keyPressed(KeyEvent arg0) {}
		
	};
	
	KeyListener op_filtro_nombre = new KeyListener(){
		@SuppressWarnings("unchecked")
		public void keyReleased(KeyEvent arg0) {
			trsfiltro.setRowFilter(RowFilter.regexFilter(txtNombre_Completo.getText().toUpperCase().trim(), 1));
		}
		public void keyTyped(KeyEvent arg0) {}
		public void keyPressed(KeyEvent arg0) {}		
	};
	
	ActionListener op_filtro_establecimiento = new ActionListener(){
		@SuppressWarnings("unchecked")
		public void actionPerformed(ActionEvent arg0){
			if(cmbEstablecimientos.getSelectedIndex() != 0){
				trsfiltro.setRowFilter(RowFilter.regexFilter(cmbEstablecimientos.getSelectedItem()+"", 2));
			}else{
				trsfiltro.setRowFilter(RowFilter.regexFilter("", 2));
			}
		}
	};
	
	ActionListener op_negativos = new ActionListener(){
		@SuppressWarnings("unchecked")
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(chbNegativos.isSelected()){
				trsfiltro.setRowFilter(RowFilter.regexFilter("-", 5));
			}else{
				trsfiltro.setRowFilter(RowFilter.regexFilter("", 5));
			}
		}
		
	};
    
	public float returnBanamex(){
		float valor = 0;
		
		for(int i=0; i<tabla.getRowCount(); i++){
			if(tabla_model.getValueAt(i, 3).toString().length() != 0){
				valor = valor + Float.parseFloat(tabla_model.getValueAt(i,3)+"");
			}				
		}
		return valor;
	}
	
	public float returnBanorte(){
		float valor = 0;
		for(int i=0; i<tabla.getRowCount(); i++){
			if(tabla_model.getValueAt(i,4).toString().length() != 0){
				valor = valor + Float.parseFloat(tabla_model.getValueAt(i,4)+"");
			}				
		}
		return valor;
	}
	
	public float returnTotales(){
		float valor = 0;
		for(int i=0; i<tabla.getRowCount(); i++){
			if(tabla_model.getValueAt(i,3).toString().length() != 0){
				valor = valor + Float.parseFloat(tabla_model.getValueAt(i,3)+"");
			}	
			if(tabla_model.getValueAt(i,4).toString().length() != 0){
				valor = valor + Float.parseFloat(tabla_model.getValueAt(i,4)+"");
			}		
		}
		return valor;
	}
	
	public static void main (String [] arg) {
		new Cat_Bancos().setVisible(true);
	}
	
	public class filtroSeleccion extends Cat_IZAGAR_Selecionar_Nomina_Para_Netos{
		public filtroSeleccion(){
			this.btnconsultanomina.removeActionListener(buscar);
			this.btnconsultanomina.addActionListener(optBuscar);
		}
		ActionListener optBuscar = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(txtFolionomina.getText().equals("")){
					JOptionPane.showMessageDialog(null, "Ingrese el No. de Folio", "Error", JOptionPane.WARNING_MESSAGE);
					return;
				}else{
					try{
						new asignarBancos(txtFolionomina.getText()).setVisible(true);
						dispose();
					}catch (NumberFormatException e1){
						
					}
				}
			}
		};
	}
	
	public class asignarBancos extends Cat_IZAGAR_Pasar_Netos_De_Nomina_A_Bancos{

		public asignarBancos(String folio_nomina) {
			super(folio_nomina);
			this.btnAplicar.addActionListener(optAplicar);
		}
		ActionListener optAplicar = new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				
			if(new Obj_IZAGAR_Netos_Nominas().guardar_totales_deposito_nomina_bancos()){
				dispose();
				
				while(tabla.getRowCount()>0){
					tabla_model.removeRow(0);
				}
				
				Object [][] lista = new Obj_Bancos().get_tabla_model();
				
				for(Object[] ps : lista){
					tabla_model.addRow(ps);
				}
				JOptionPane.showMessageDialog(null,"El Transpaso Se a Realizado Exitosamente", "Aviso", JOptionPane.INFORMATION_MESSAGE);
				return;
				
		}else{
			JOptionPane.showMessageDialog(null,"No Se A Realizado El Transpaso", "Error", JOptionPane.WARNING_MESSAGE);
			return;
		}
				
				
				
				
			}
		};
	}
}