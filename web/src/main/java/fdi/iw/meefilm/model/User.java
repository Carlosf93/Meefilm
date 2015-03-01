package fdi.iw.meefilm.model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;


@Entity
@NamedQueries({
    @NamedQuery(name="userById",
        query="select u from User u where u.id = :idParam"),
    @NamedQuery(name="userByLogin",
    query="select u from User u where u.login = :loginParam"),
    @NamedQuery(name="delUser",
	query="delete from User u where u.id= :idParam")
	
})
public class User {	
	
	private long id;
	private String login;
	private String role;
	private String hashedAndSalted;
	private String salt;
	private String nombre;
	private int edad;
	private String correo;
	private String profesion;
	private String aficiones;
	private String ciudad;
	private String fecha_nacimiento;
	private List<Pelicula> listaPeliculas;
	private List<Serie> listaSeries;
	private List<Mensaje> bandejaEntrada;
	private List<User> listaAmigos;
	private List<Quedada> listaQuedadas;
	private String usuariosParecidos;
	private String pelisParecidas;
	private String seriesParecidas;
	
	public static User createUser(String login, String pass, String nombre,
								  String nombreAp, String correo,
								  String profesion, String aficiones,
								  String ciudad, String fecha_nacimiento) {
		User u = new User();
		u.login = login;
		u.nombre = nombre + " " +nombreAp;
		u.correo = correo;
		//u.pass = pass;
		u.profesion = profesion;
		u.aficiones = aficiones;
		u.ciudad = ciudad;
		u.fecha_nacimiento = fecha_nacimiento;
		u.edad = calcularEdad(fecha_nacimiento);
		u.listaSeries = new ArrayList<Serie>();
		u.listaPeliculas = new ArrayList<Pelicula>();
		u.listaAmigos = new ArrayList<User>();
		u.bandejaEntrada = new ArrayList<Mensaje>();
		u.listaQuedadas = new ArrayList<Quedada>();
		u.usuariosParecidos = "";
		u.pelisParecidas = "";
		u.seriesParecidas = "";
		Random r = new Random();
		
		
		// generate new, random salt; build hashedAndSalted
		byte[] saltBytes = new byte[16];
		r.nextBytes(saltBytes);
		u.salt = byteArrayToHexString(saltBytes);
		u.hashedAndSalted = generateHashedAndSalted(pass, u.salt);
		u.role = "user";
		return u;
	}
	
	public boolean isPassValid(String pass) {
		return generateHashedAndSalted(pass, this.salt).equals(hashedAndSalted);		
	}
	
	/**
	 * Generate a hashed&salted hex-string from a user's pass and salt
	 * @param pass to use; no length-limit!
	 * @param salt to use
	 * @return a string to store in the BD that does not reveal the password even
	 * if the DB is compromised. Note that brute-force is possible, but it will
	 * have to be targeted (ie.: use the same salt)
	 */
	public static String generateHashedAndSalted(String pass, String salt) {
		byte[] saltBytes = hexStringToByteArray(salt);
		byte[] passBytes = pass.getBytes();
		byte[] toHash = new byte[saltBytes.length + passBytes.length];
		System.arraycopy(passBytes, 0, toHash, 0, passBytes.length);
		System.arraycopy(saltBytes, 0, toHash, passBytes.length, saltBytes.length);
		return byteArrayToHexString(hash(toHash));
	}	

	/**
	 * Converts a byte array to a hex string
	 * @param b converts a byte array to a hex string; nice for storing
	 * @return the corresponding hex string
	 */
	public static String byteArrayToHexString(byte[] b) {
		StringBuilder sb = new StringBuilder();
		for (int i=0; i<b.length; i++) {
			sb.append(Integer.toString((b[i]&0xff) + 0x100, 16).substring(1));
		}
		return sb.toString();
	}
	
	/**
	 * Converts a hex string to a byte array
	 * @param hex string to convert
	 * @return equivalent byte array
	 */
	public static byte[] hexStringToByteArray(String hex) {
		byte[] r = new byte[hex.length()/2];
		for (int i=0; i<r.length; i++) {
			String h = hex.substring(i*2, (i+1)*2);
			r[i] = (byte)Integer.parseInt(h, 16);
		}
		return r;
	}
	
	/**
	 * Returns the SHA-1 of a byte array
	 * @return
	 */
	public static byte[] hash(byte[] bytes) {
		MessageDigest md = null;
	    try {
	        md = MessageDigest.getInstance("SHA-1");
	    } catch(NoSuchAlgorithmException e) {
	        e.printStackTrace();
	    } 
	    return md.digest(bytes);
	}
	
	@Id
	@GeneratedValue
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public String getFecha_nacimiento() {
		return fecha_nacimiento;
	}

	public void setFecha_nacimiento(String fecha_nacimiento) {
		this.fecha_nacimiento = fecha_nacimiento;
	}

	@Column(unique=true)
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getHashedAndSalted() {
		return hashedAndSalted;
	}

	public void setHashedAndSalted(String hashedAndSalted) {
		this.hashedAndSalted = hashedAndSalted;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getProfesion() {
		return profesion;
	}

	public void setProfesion(String profesion) {
		this.profesion = profesion;
	}

	public String getAficiones() {
		return aficiones;
	}

	public void setAficiones(String aficiones) {
		this.aficiones = aficiones;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	
	@ManyToMany(targetEntity=Pelicula.class, fetch=FetchType.EAGER)
	public List<Pelicula> getListaPeliculas() {
		return listaPeliculas;
	}

	public void setListaPeliculas(List<Pelicula> listaPeliculas) {
		this.listaPeliculas = listaPeliculas;
	}

	@ManyToMany(targetEntity=Serie.class, fetch=FetchType.EAGER)
	public List<Serie> getListaSeries() {
		return listaSeries;
	}

	public void setListaSeries(List<Serie> listaSeries) {
		this.listaSeries = listaSeries;
	}
	
	@ManyToMany(targetEntity=Quedada.class, fetch=FetchType.EAGER)
	public List<Quedada> getListaQuedadas() {
		return listaQuedadas;
	}

	public void setListaQuedadas(List<Quedada> listaQuedadas) {
		this.listaQuedadas = listaQuedadas;
	}
	
	@ManyToMany(targetEntity=User.class, fetch=FetchType.EAGER)
	public List<User> getListaAmigos() {
		return listaAmigos;
	}

	public void setListaAmigos(List<User> listaAmigos) {
		this.listaAmigos = listaAmigos;
	}

	public String getUsuariosParecidos() {
		return usuariosParecidos;
	}

	public void setUsuariosParecidos(String usuariosParecidos) {
		this.usuariosParecidos = usuariosParecidos;
	}

	public String getPelisParecidas() {
		return pelisParecidas;
	}

	public void setPelisParecidas(String pelisParecidas) {
		this.pelisParecidas = pelisParecidas;
	}
	
	public String getSeriesParecidas() {
		return seriesParecidas;
	}

	public void setSeriesParecidas(String seriesParecidas) {
		this.seriesParecidas = seriesParecidas;
	}

	private static int calcularEdad(String FdN){
		System.out.println(FdN);
		Date fechaActual = new Date();
	    SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
	    String hoy = formato.format(fechaActual);
	    String[] dat1 = FdN.split("-");
	    String[] dat2 = hoy.split("-");
	    int edad = Integer.parseInt(dat2[0]) - Integer.parseInt(dat1[0]);
	    int mes = Integer.parseInt(dat2[1]) - Integer.parseInt(dat1[1]);
	    if (mes < 0) {
	      edad = edad - 1;
	    } else if (mes == 0) {
	      int dia = Integer.parseInt(dat2[2]) - Integer.parseInt(dat1[2]);
	      if (dia > 0) {
	        edad = edad - 1;
	      }
	    }
		return edad;
	}
	
	public void addSerieLista(Serie s){
		this.listaSeries.add(s);
	}
	
	public void addPeliculaLista(Pelicula p){
		this.listaPeliculas.add(p);
	}
	
	public void addQuedadaLista(Quedada q){
		this.listaQuedadas.add(q);
	}
	
	@OneToMany(targetEntity=Mensaje.class, fetch=FetchType.EAGER, cascade = CascadeType.REMOVE)
	public List<Mensaje> getBandejaEntrada() {
		return bandejaEntrada;
	}

	public void setBandejaEntrada(List<Mensaje> bandejaEntrada) {
		this.bandejaEntrada = bandejaEntrada;
	}

	public void addMP(Mensaje mp) {
		this.bandejaEntrada.add(0,mp);
	}
	
	public void addFR(Mensaje fr) {
		this.bandejaEntrada.add(fr);
	}
	
	public void eliminarMensaje(long id) {
		int i=0;
		
		while(i<this.bandejaEntrada.size()){
			if(this.bandejaEntrada.get(i).getId()==id)
				this.bandejaEntrada.remove(i);
			else
				i++;
		}	
	}
	
	public int mensajesNoLeidos(){
		int i=0;
		int nl=0;
		Mensaje m = null;
		
		
		while(i<this.bandejaEntrada.size()){
			m = this.bandejaEntrada.get(i);
			if(!m.isLeido()&&m.getTipo().equalsIgnoreCase("MP")){
				nl++;
				i++;
			}
			else
				i++;
		}
		
		
		return nl;
	}
	
	public int peticionesNoLeidas(){
		int i=0;
		int nl=0;
		Mensaje m = null;
		while(i<this.bandejaEntrada.size()){
			m = this.bandejaEntrada.get(i);
			if(!m.isLeido()&&m.getTipo().equalsIgnoreCase("FR")){
				nl++;
				i++;
			}
			else
				i++;
		}
		
		return nl;
	}
	
	public String toString() {
		return "" + id + " " + login + " " + hashedAndSalted + " " + salt;
	}

	public void addAmigo(User u) {
		this.listaAmigos.add(u);
	}
	
	public void deleteAmigo(User u) {
		this.listaAmigos.remove(u);
	}
	
	public void peliDeleteFav(Pelicula p) {
		this.listaPeliculas.remove(p);
	}
	
	public void serieDeleteFav(Serie s) {
		this.listaSeries.remove(s);
	}
	
	public void quedadaDelete(Quedada q) {
		this.listaQuedadas.remove(q);
	}
	
	

	public boolean serieEnFav(long n) {
		int i=0;
		boolean existe = false;
				
		while(i<this.listaSeries.size()&&existe==false){
			if(this.listaSeries.get(i).getId()==n)
				existe=true;
			else
				i++;
		}
		return existe;
	}
	
	public boolean peliculaEnFav(long n) {
		int i=0;
		boolean existe = false;
		while(i<this.listaPeliculas.size()&&existe==false){
			if(this.listaPeliculas.get(i).getId()==n)
				existe=true;
			else
				i++;
		}
		return existe;
	}
	
	public boolean sonAmigos(long n) {
		int i=0;
		boolean existe = false;
		while(i<this.listaAmigos.size()&&existe==false){
			if(this.listaAmigos.get(i).getId()==n)
				existe=true;
			else
				i++;
		}
		
		return existe;
	}

	public boolean peticionExiste(long idud) {
		int i=0;
		boolean existe = false;
		while(i<this.bandejaEntrada.size()&&existe==false){
			if((this.bandejaEntrada.get(i).getTipo().equalsIgnoreCase("FR"))&&(this.bandejaEntrada.get(i).getUsuario().getId()==idud))
				existe=true;
			else
				i++;
		}
		return existe;
	}
	
	public void eliminarPeticion(long id){
		int i=0;
		boolean existe = false;
		while(i<this.bandejaEntrada.size()&&existe==false){
			if((this.bandejaEntrada.get(i).getTipo().equalsIgnoreCase("FR"))&&(this.bandejaEntrada.get(i).getId()==id))
				this.bandejaEntrada.remove(i);
			else
				i++;
		}
	}
	
	public void updateUser(String pass, String nombre,
			  String correo,
			  String profesion, String aficiones,
			  String ciudad, String fecha_nacimiento) {
	
		this.nombre = nombre;
		this.correo = correo;
		this.profesion = profesion;
		this.aficiones = aficiones;
		this.ciudad = ciudad;
		this.fecha_nacimiento = fecha_nacimiento;
		this.edad = calcularEdad(fecha_nacimiento);
				
		if(pass!=""){
			this.hashedAndSalted = new String(generateHashedAndSalted(pass, this.salt));
		}
	}

	public void marcarLeidos() {
		for(Mensaje m: this.bandejaEntrada)
			if(m.getTipo().equalsIgnoreCase("MP"))
				m.setLeido(true);
		
	}

	public void addUsuarioParecido(String id) {
		if(this.usuariosParecidos.equalsIgnoreCase("")){
			this.usuariosParecidos+=id;
		}
		else
			this.usuariosParecidos+=","+id;
	}

	public void addPeliParecida(long id) {
		if(this.pelisParecidas.equalsIgnoreCase("")){
			this.pelisParecidas+=Long.toString(id);
		}
		else
			this.pelisParecidas+=","+Long.toString(id);
	}
	
	public void addSerieParecida(long id) {
		if(this.seriesParecidas.equalsIgnoreCase("")){
			this.seriesParecidas+=Long.toString(id);
		}
		else
			this.seriesParecidas+=","+Long.toString(id);
	}

	
}