package fdi.iw.meefilm.model;

import java.util.List;

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
    @NamedQuery(name="mensajeById",
            query="select m from Mensaje m where m.id = :idParam"),
    @NamedQuery(name="delMensaje",
        	query="delete from Mensaje m where m.id= :idParam")
})
public class Mensaje {
	
	private long id;
	private String tipo;
	private String asunto;
	private String texto;
	private boolean leido;
	private User usuario;
	
	public static Mensaje createMP(String a, String s, User u) {
		
		Mensaje mp = new Mensaje();
		mp.asunto=a;
		mp.texto=s;
		mp.usuario=u;
		mp.leido=false;
		mp.tipo="MP";
		return mp;
	}
	
	public static Mensaje createFR(User ue) {
		
		Mensaje fr = new Mensaje();
		fr.asunto="";
		fr.texto="";
		fr.usuario=ue;
		fr.leido=false;
		fr.tipo="FR";
		
		return fr;
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
	
	public String getAsunto() {
		return asunto;
	}

	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}

	@ManyToOne(targetEntity=User.class, fetch=FetchType.EAGER)
	public User getUsuario() {
		return usuario;
	}

	public void setUsuario(User usuario) {
		this.usuario = usuario;
	}

	public boolean isLeido() {
		return leido;
	}

	public void setLeido(boolean leido) {
		this.leido = leido;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	

}
