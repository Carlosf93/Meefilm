package fdi.iw.meefilm.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;


@Entity
@NamedQueries({
    @NamedQuery(name="comentarioById",
            query="select co from Comentario co where co.id = :idParam"),
    @NamedQuery(name="delComentario",
        	query="delete from Comentario co where co.id= :idParam")
})
public class Comentario {
	
	private long id;
	private String texto;
	private User usuario;
	
	public static Comentario createComentario(String s, User u) {
		
		Comentario co = new Comentario();
		co.texto=s;
		co.usuario=u;
		return co;
	}
	
	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}
	
	@ManyToOne(targetEntity=User.class, fetch=FetchType.EAGER)
	public User getUsuario() {
		return usuario;
	}

	public void setUsuario(User usuario) {
		this.usuario = usuario;
	}

	

}
