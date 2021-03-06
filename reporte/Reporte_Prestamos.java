package reporte;

import java.util.HashMap;

import javax.swing.JFrame;

import SQL.Connexion;



import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

@SuppressWarnings("serial")
public class Reporte_Prestamos extends JFrame {
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Reporte_Prestamos() {
		try {
			JasperReport report = JasperCompileManager.compileReport(System.getProperty("user.dir")+"\\src\\Reportes\\Reporte_Prestamos.jrxml");
			
			JasperPrint print = JasperFillManager.fillReport(report, new HashMap(), new Connexion().conexion());
			JasperViewer.viewReport(print, false);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}

}
