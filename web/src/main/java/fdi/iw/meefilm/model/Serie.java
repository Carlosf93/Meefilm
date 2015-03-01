package fdi.iw.meefilm.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.JoinColumn;


@Entity
@NamedQueries({
    @NamedQuery(name="serieById",
            query="select s from Serie s where s.id = :idParam"),
    @NamedQuery(name="delSerie",
        	query="delete from Serie s where s.id= :idParam")
})
public class Serie {
	
	
	private long id;
	private String nombre;
	private String creador;
	private String genero;
	private String reparto;
	private String sinopsis;
	private String year;
	private String IMDbId;
	private List<Temporada> temporadas;
	private List<User> listaUsuarios;
	private List<Comentario> comentarios;
	
	public static Serie createSerie(String nombre, String creador, String genero,
			  String reparto, String sinopsis, String year, String IMDbId) {
		
		Serie s = new Serie();
		s.nombre = nombre;
		s.creador = creador;
		s.genero = genero;
		s.reparto = reparto;
		s.sinopsis = sinopsis;
		s.year = year;
		s.IMDbId = IMDbId;
		s.temporadas = new ArrayList<Temporada>();
		s.listaUsuarios = new ArrayList<User>();
		s.comentarios = new ArrayList<Comentario>();
		
		return s;
	}
	
	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}
	
	@OneToMany(targetEntity=Temporada.class, fetch=FetchType.EAGER)
	public List<Temporada> getTemporadas() {
		return temporadas;
	}

	public void setTemporadas(List<Temporada> temporadas) {
		this.temporadas = temporadas;
	}
	
	@OneToMany(targetEntity=Comentario.class, fetch=FetchType.EAGER)
	public List<Comentario> getComentarios() {
		return comentarios;
	}

	public void setComentarios(List<Comentario> comentarios) {
		this.comentarios = comentarios;
	}

	@ManyToMany(targetEntity=User.class,mappedBy="listaSeries", fetch=FetchType.EAGER)
	public List<User> getListaUsuarios() {
		return listaUsuarios;
	}

	public void setListaUsuarios(List<User> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}
	

	public String getIMDbId() {
		return IMDbId;
	}

	public void setIMDbId(String iMDbId) {
		IMDbId = iMDbId;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public void setIdserie(long idserie) {
		id = idserie;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getCreador() {
		return creador;
	}
	public void setCreador(String creador) {
		this.creador = creador;
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

	public void addUsuarioLista(User u) {
		this.listaUsuarios.add(u);
	}
	
	public void deleteUsuario(User usuario) {
		this.listaUsuarios.remove(usuario);
	}
	
	public boolean existeTemporada(int n){
		int i = 0;
		while (i<this.temporadas.size()){
			if (this.temporadas.get(i).getNumero()==n)
				return true;
			else
				i++;
			}
				
		return false;
	}
	
	private int indexTemporada(int n){
		int i=0;
		int index=-1;
		
		while(i<this.temporadas.size()){
			if(this.temporadas.get(i).getNumero()==n){
				index=i;
				return index;}
			else
				i++;
		}
		return index;
	}

	public void addCapituloTemporada(int n, Capitulo c) {
		int i = this.indexTemporada(n);
		this.temporadas.get(i).addCapitulo(c);
		}

	public void addTemporada(Temporada t) {
		
		if(this.temporadas.size()==0)
			this.temporadas.add(t);
		else{
		int index = 0;
		for (int i=0; i<this.temporadas.size();i++){
			if(this.temporadas.get(i).getNumero()>t.getNumero())
				index=i;
			else
				index=this.temporadas.size();
		}
		this.temporadas.add(index, t);
		}
		
	}

	public void addComment(Comentario co) {
		this.comentarios.add(co);
	}

	public boolean existeCapEnTemp(int t, int c) {
		int i=0;
		boolean existe=false;
		int index = this.indexTemporada(t);
		if(index != -1)
		while(i<this.temporadas.get(index).getCapitulos().size()&&existe==false)
			if(this.temporadas.get(index).getCapitulos().get(i).getNumero()==c)
				existe=true;
			else
				i++;
		
		return existe;
	}	
}

