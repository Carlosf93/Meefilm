package fdi.iw.meefilm.model;

import javax.persistence.CascadeType;
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
    @NamedQuery(name="peliById",
            query="select p from Pelicula p where p.id = :idParam"),
    @NamedQuery(name="delPelicula",
        	query="delete from Pelicula p where p.id= :idParam")
})
public class Pelicula {
	
	private long id;
	private String nombre;
	private String director;
	private String genero;
	private String reparto;
	private String sinopsis;
	private String year;
	private String IMDbId;
	private List<User> listaUsuarios;
	private List<Comentario> comentarios;
	
	public static Pelicula createPelicula(String nombre, String director, String genero,
			  String reparto, String sinopsis, String year, String IMDbID) {
		
		Pelicula p = new Pelicula();
		p.nombre = nombre;
		p.director = director;
		p.genero = genero;
		p.reparto = reparto;
		p.sinopsis = sinopsis;
		p.year=year;
		p.IMDbId=IMDbID;
		p.listaUsuarios = new ArrayList<User>();
		p.comentarios = new ArrayList<Comentario>();
		
		return p;
	}
	
	public String getYear() {
		return year;
	}



	public void setYear(String year) {
		this.year = year;
	}



	public String getIMDbId() {
		return IMDbId;
	}



	public void setIMDbId(String iMDbId) {
		IMDbId = iMDbId;
	}



	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}
	public void setId(long idpelicula) {
		this.id = idpelicula;
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
	public String getGenero() {
		return genero;
	}
	public void setGenero(String genero) {
		this.genero = genero;
	}
	public String getReparto() {
		return reparto;
	}
	public void setReparto(String reparto) {
		this.reparto = reparto;
	}
	public String getSinopsis() {
		return sinopsis;
	}
	public void setSinopsis(String sinopsis) {
		this.sinopsis = sinopsis;
	}
	
	@ManyToMany(targetEntity=User.class, fetch=FetchType.EAGER)
	public List<User> getListaUsuarios() {
		return listaUsuarios;
	}
	public void setListaUsuarios(List<User> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}

	public void addUsuarioLista(User u) {
		this.listaUsuarios.add(u);
	}
	
	public void deleteUsuario(User usuario) {
		this.listaUsuarios.remove(usuario);
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
