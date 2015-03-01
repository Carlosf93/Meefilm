package fdi.iw.meefilm.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQueries({
    @NamedQuery(name="capituloById",
            query="select c from Capitulo c where c.id = :idParam")
})

public class Capitulo {
	
	private long id;
	private int numero;
	private int temporada;
	private String nombre;
	private String director;
	private String reparto;
	private String fechaemision;
	private String sinopsis;
	private String IMDbId;
	private List<Comentario> comentarios;
	
	public static Capitulo createCapitulo(int numero, String nombre, String director, 
			String reparto, String fechaemision, String sinopsis, String IMDbId) {
		
		Capitulo c = new Capitulo();
		c.numero=numero;
		c.nombre = nombre;
		c.director = director;
		c.reparto = reparto;
		c.fechaemision = fechaemision;
		c.sinopsis = sinopsis;
		c.IMDbId = IMDbId;
		c.comentarios = new ArrayList<Comentario>();
		
		return c;
	}
	
	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}
	public void setId(long idcapitulo) {
		this.id = idcapitulo;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	public String getReparto() {
		return reparto;
	}
	public void setReparto(String reparto) {
		this.reparto = reparto;
	}
	public String getFechaemision() {
		return fechaemision;
	}
	public void setFechaemision(String fechaemision) {
		this.fechaemision = fechaemision;
	}
	public String getSinopsis() {
		return sinopsis;
	}
	public void setSinopsis(String sinopsis) {
		this.sinopsis = sinopsis;
	}	
	public int getTemporada() {
		return temporada;
	}

	public void setTemporada(int temporada) {
		this.temporada = temporada;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}
	
	
	public String getIMDbId() {
		return IMDbId;
	}

	public void setIMDbId(String iMDbId) {
		IMDbId = iMDbId;
	}

	@OneToMany(targetEntity=Comentario.class, fetch=FetchType.EAGER)
	public List<Comentario> getComentarios() {
		return comentarios;
	}

	public void setComentarios(List<Comentario> comentarios) {
		this.comentarios = comentarios;
	}
	
	public void addComment(Comentario co) {
		this.comentarios.add(co);
	}

}

