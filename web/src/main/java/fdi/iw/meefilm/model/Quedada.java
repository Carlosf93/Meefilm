package fdi.iw.meefilm.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQueries({
    @NamedQuery(name="quedadaById",
            query="select q from Quedada q where q.id = :idParam"),
    @NamedQuery(name="delQuedada",
			query="delete from Quedada q where q.id= :idParam")
})
public class Quedada {
	
	private long id;
	private String comunidadAut;
	private String ciudad;
	private String cine;
	private String pelicula;
	private String descripcion;
	private List<User> listaUsuarios;
	private List<Comentario> comentarios;
	
	public static Quedada createQuedada(String caut, String ciudad, String cine,
			  String pelicula, String descripcion) {
		Quedada q = new Quedada();
		q.comunidadAut = caut;
		q.ciudad = ciudad;
		q.cine = cine;
		q.pelicula = pelicula;
		q.descripcion = descripcion;
		q.listaUsuarios = new ArrayList<User>();
		return q;
}
	
	
	
	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getComunidadAut() {
		return comunidadAut;
	}
	public void setComunidadAut(String comunidadAut) {
		this.comunidadAut = comunidadAut;
	}
	public String getCiudad() {
		return ciudad;
	}
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	public String getCine() {
		return cine;
	}
	public void setCine(String cine) {
		this.cine = cine;
	}
	public String getPelicula() {
		return pelicula;
	}
	public void setPelicula(String pelicula) {
		this.pelicula = pelicula;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	@ManyToMany(targetEntity=User.class, fetch=FetchType.EAGER)
	public List<User> getListaUsuarios() {
		return listaUsuarios;
	}
	public void setListaUsuarios(List<User> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}

	public void addUsuario(User u) {
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



	public boolean userEnLista(long id) {
		int i=0;
		boolean existe = false;
		while(i<this.listaUsuarios.size()&&existe==false){
			if(this.listaUsuarios.get(i).getId()==id)
				existe=true;
			else
				i++;
		}
		return existe;
	}

}
