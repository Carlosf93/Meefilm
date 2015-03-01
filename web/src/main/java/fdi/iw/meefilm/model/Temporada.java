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
    @NamedQuery(name="temporadaById",
            query="select t from Temporada t where t.id = :idParam")
})
public class Temporada {
	
	private long id;
	private int numero;
	private List<Capitulo> capitulos;
	
	public static Temporada createTemporada(int num) {
		
		Temporada t = new Temporada();
		t.numero = num;
		t.capitulos = new ArrayList<Capitulo>();
		return t;
	}
	
	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	@OneToMany(targetEntity=Capitulo.class, fetch=FetchType.EAGER)
	public List<Capitulo> getCapitulos() {
		return capitulos;
	}

	public void setCapitulos(List<Capitulo> capitulos) {
		this.capitulos = capitulos;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}
	
	public void addCapitulo(Capitulo c) {
		c.setTemporada(this.numero);
		
		if(this.capitulos.size()==0)
			this.capitulos.add(c);
		else{
		int index = 0;
		for (int i=0; i<this.capitulos.size();i++){
			if(this.capitulos.get(i).getNumero()>c.getNumero())
				index=i;
			else
				index=this.capitulos.size();
		}
		this.capitulos.add(index, c);
		}
	}

}
