package fdi.iw.meefilm.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries({
    @NamedQuery(name="reporteById",
            query="select r from Reporte r where r.id = :idParam"),
    @NamedQuery(name="delReporte",
        	query="delete from Reporte r where r.id= :idParam")
})
public class Reporte {
	
	private long id;
	private String motivo;
	private long idelemento;
	private String tipo;
	private String nombre;
	
	public static Reporte createReporte(String motivo, long idelemento, String tipo, String nombre) {
		
		Reporte r = new Reporte();
		
		r.motivo = motivo;
		r.idelemento = idelemento;
		r.tipo=tipo;
		r.nombre=nombre;
		
		return r;
	}
	
	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}
	public void setId(long idpelicula) {
		this.id = idpelicula;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public long getIdelemento() {
		return idelemento;
	}

	public void setIdelemento(long idelemento) {
		this.idelemento = idelemento;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
}
