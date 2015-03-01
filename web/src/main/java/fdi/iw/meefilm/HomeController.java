package fdi.iw.meefilm;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.AccessibleObject;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import fdi.iw.meefilm.ContextInitializer;
import fdi.iw.meefilm.model.Capitulo;
import fdi.iw.meefilm.model.Comentario;
import fdi.iw.meefilm.model.Mensaje;
import fdi.iw.meefilm.model.Quedada;
import fdi.iw.meefilm.model.Reporte;
import fdi.iw.meefilm.model.Temporada;
import fdi.iw.meefilm.model.User;
import fdi.iw.meefilm.model.Pelicula;
import fdi.iw.meefilm.model.Serie;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Scanner;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@PersistenceContext
	private EntityManager entityManager;
	
	/* -----------------------METODOS QUE REDIRECCIONAN A LOS JSP---------------------------*/
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String empty(Locale locale, Model model,HttpSession session) {
		try{
		User usu = (User) session.getAttribute("user");
		List<Pelicula> pe = entityManager.createQuery("select p from Pelicula p").getResultList();
		List<Pelicula> pelisParecidas = new ArrayList<Pelicula>();
		List<Serie> se = entityManager.createQuery("select s from Serie s").getResultList();
		List<Serie> seriesParecidas = new ArrayList<Serie>();
		List<User> us = entityManager.createQuery("select u from User u").getResultList();
		List<User> usuariosParecidos = new ArrayList<User>();
		String[] parecidasuseru = usu.getUsuariosParecidos().split(",");
		String[] parecidasuserp = usu.getPelisParecidas().split(",");
		String[] parecidasusers = usu.getSeriesParecidas().split(",");
		
		if(usu!=null){
			
			for(User u: us){
				for(int i=0; i<parecidasuseru.length;i++)
					if(Long.toString(u.getId()).equalsIgnoreCase(parecidasuseru[i]))
						usuariosParecidos.add(u);
			}
			
			for(Pelicula p: pe){
				for(int i=0; i<parecidasuserp.length;i++)
					if(Long.toString(p.getId()).equalsIgnoreCase(parecidasuserp[i]))
						pelisParecidas.add(p);
			}
			
			for(Serie s: se){
				for(int i=0; i<parecidasusers.length;i++)
					if(Long.toString(s.getId()).equalsIgnoreCase(parecidasusers[i]))
						seriesParecidas.add(s);
			}
			
			model.addAttribute("usPar", usuariosParecidos);
			model.addAttribute("pePar", pelisParecidas);
			model.addAttribute("sePar", seriesParecidas);
		}
		
		}catch(NullPointerException e){}
		
		model.addAttribute("pageTitle", "Meefilm");
		
		return "home";
	}
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String index(Locale locale, Model model,HttpSession session) {
		return empty(locale, model, session);
	}	
	
	@RequestMapping(value = "/peliculas", method = RequestMethod.GET)
	@Transactional
	public String listaPelicula(Locale locale, Model model) {
		List<Pelicula> pe = entityManager.createQuery("select p from Pelicula p order by p.nombre").getResultList();
		model.addAttribute("pelicula", pe);
		model.addAttribute("pageTitle", "Peliculas");
		return "peliculas";
	}
	
	@RequestMapping(value = "/pelicula", method = RequestMethod.GET)
	@Transactional
	public String peli(HttpServletRequest request, 
			Model model, @RequestParam("p") long id, HttpSession session){
		Pelicula p = null;
		try {
			User c = (User) session.getAttribute("user");
			p = (Pelicula)entityManager.createNamedQuery("peliById")
				.setParameter("idParam", id).getSingleResult();
			model.addAttribute("p", p);
			if(this.usuarioLogueado(session)){
				if(c.peliculaEnFav(id)){
					model.addAttribute("fp",1);}
				else{
					model.addAttribute("fp",0);}
				}
		} catch (NoResultException nre) {}
		model.addAttribute("pageTitle", p.getNombre());
		return "pelicula";
	}
	
	@RequestMapping(value = "/series", method = RequestMethod.GET)
	@Transactional
	public String listaSeries(Locale locale, Model model) {
		logger.info("Serie is looking up 'about us'");
		List<Serie> se = entityManager.createQuery("select s from Serie s order by s.nombre").getResultList();
		model.addAttribute("serie", se);
		model.addAttribute("pageTitle", "Series");
		return "series";
	}
	
	@RequestMapping(value = "/serie", method = RequestMethod.GET)
	@Transactional
	public String serie(HttpServletRequest request, 
			Model model, @RequestParam("s") long id, HttpSession session){
		Serie s = null;
		try {
			User c = (User) session.getAttribute("user");

			s = (Serie)entityManager.createNamedQuery("serieById")
				.setParameter("idParam", id).getSingleResult();
			model.addAttribute("s", s);
			if(this.usuarioLogueado(session)){
				if(c.serieEnFav(id)){
					model.addAttribute("fs",1);}
				else{
					model.addAttribute("fs",0);}
				}
		} catch (NoResultException nre) {}
		model.addAttribute("pageTitle", s.getNombre());
		return "serie";
	}
	
	@RequestMapping(value = "/capitulo", method = RequestMethod.GET)
	@Transactional
	public String capitulo(HttpServletRequest request, 
			Model model, @RequestParam("t") long idt, @RequestParam("c") long idc, @RequestParam("s") long ids){
		Serie s = null;
		Capitulo c = null;
		try {
			s = (Serie)entityManager.createNamedQuery("serieById")
				.setParameter("idParam", ids).getSingleResult();
			
			c = (Capitulo)entityManager.createNamedQuery("capituloById")
					.setParameter("idParam", idc).getSingleResult();
			Temporada t = (Temporada) entityManager.createNamedQuery("temporadaById")
					.setParameter("idParam", idt).getSingleResult();

			model.addAttribute("t", t);
			model.addAttribute("c", c);
			model.addAttribute("s", s);
		} catch (NoResultException nre) {}
		model.addAttribute("pageTitle", s.getNombre()+" - "+c.getNombre());
		return "capitulo";
	}
	
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	@Transactional
	public String about(Locale locale, Model model) {
		List<User> us = entityManager.createQuery("select u from User u").getResultList();
		System.err.println(us.size());
		model.addAttribute("users", us);
		model.addAttribute("pageTitle", "Usuarios");
		return "users";
	}
	
	@RequestMapping(value = "/usuario", method = RequestMethod.GET)
	@Transactional
	public String usuario(HttpServletRequest request, Model model, @RequestParam("u") long id, HttpSession session){
		User u = null;
		try {
			User c = (User) session.getAttribute("user");
			u = (User)entityManager.createNamedQuery("userById")
				.setParameter("idParam", id).getSingleResult();
			model.addAttribute("u", u);
			
			if(this.usuarioLogueado(session)){
			if(c.sonAmigos(id)){
				model.addAttribute("fr",1);}
			else{
				model.addAttribute("fr",0);}
			}
		} catch (NoResultException nre) {}
		model.addAttribute("pageTitle", u.getLogin());
		return "usuario";
	}

	@RequestMapping(value = "/quedadas", method = RequestMethod.GET)
	public String Quedadas(HttpServletRequest request, Locale locale, Model model) {
		List<Quedada> q = entityManager.createQuery("select q from Quedada q").getResultList();
		List<Quedada> qc = new ArrayList<Quedada>();
		String formAutonoma = request.getParameter("autonoma");
				
		if(formAutonoma==null||formAutonoma.equalsIgnoreCase("Todas"))
			qc=q;
		
		else{
			
		for(int i=0;i<q.size();i++){
			if(q.get(i).getComunidadAut().equalsIgnoreCase(formAutonoma))
				qc.add(q.get(i));
			}
		}
		
		model.addAttribute("quedada", qc);
		model.addAttribute("pageTitle", "Quedadas");
		return "quedadas";
	}
	
	@RequestMapping(value = "/perfil", method = RequestMethod.GET)
	public String perfil(Locale locale, Model model) {
		model.addAttribute("pageTitle", "Mi perfil");
		return "perfil";
	}
	
	@RequestMapping(value = "/modificarperfil", method = RequestMethod.GET)
	public String modperfil(Locale locale, Model model) {
		model.addAttribute("pageTitle", "Modificar perfil");
		return "modificarperfil";
	}
	
	@RequestMapping(value = "/administrador", method = RequestMethod.GET)
	@Transactional
	public String admin(Locale locale, Model model) {
		List<Reporte> re = entityManager.createQuery("select r from Reporte r").getResultList();
		model.addAttribute("reportes", re);
		model.addAttribute("pageTitle", "Meefilm - Admin");
		return "administrador";
	}
	
	@RequestMapping(value = "/adminusers", method = RequestMethod.GET)
	@Transactional
	public String adminusers(Locale locale, Model model) {
		List<User> us = entityManager.createQuery("select u from User u").getResultList();
		model.addAttribute("users", us);
		model.addAttribute("pageTitle", "Meefilm - Admin");
		return "adminusers";
	}
	
	@RequestMapping(value = "/adminpeliculas", method = RequestMethod.GET)
	@Transactional
	public String adminpeliculas(Locale locale, Model model) {
		List<Pelicula> pe = entityManager.createQuery("select p from Pelicula p").getResultList();
		model.addAttribute("peliculas", pe);
		model.addAttribute("pageTitle", "Meefilm - Admin");
		return "adminpeliculas";
	}
	
	@RequestMapping(value = "/adminseries", method = RequestMethod.GET)
	@Transactional
	public String adminseries(Locale locale, Model model) {
		List<Serie> se = entityManager.createQuery("select s from Serie s").getResultList();
		model.addAttribute("series", se);
		model.addAttribute("pageTitle", "Meefilm - Admin");
		return "adminseries";
	}
	
	@RequestMapping(value = "/adminquedadas", method = RequestMethod.GET)
	@Transactional
	public String adminquedadas(Locale locale, Model model) {
		List<Quedada> qu = entityManager.createQuery("select q from Quedada q").getResultList();
		model.addAttribute("quedadas", qu);
		model.addAttribute("pageTitle", "Meefilm - Admin");
		return "adminquedadas";
	}

	@RequestMapping(value = "/quedada", method = RequestMethod.GET)
	@Transactional
	public String quedada(HttpServletRequest request, 
			Model model, @RequestParam("q") long id, HttpSession session){
		Quedada q = null;
		try {
			User c = (User) session.getAttribute("user");

			q = (Quedada)entityManager.createNamedQuery("quedadaById")
				.setParameter("idParam", id).getSingleResult();
			model.addAttribute("q", q);
			if(this.usuarioLogueado(session)){
				if(q.userEnLista(c.getId())){
					model.addAttribute("fq",1);}
				else{
					model.addAttribute("fq",0);}
				}
		} catch (NoResultException nre) {}
		model.addAttribute("pageTitle", q.getCine());
		return "quedada";
	}
	
	@RequestMapping(value = "/registro", method = RequestMethod.GET)
	public String registro(Locale locale, Model model) {
		model.addAttribute("pageTitle", "Registro");
		return "registro";
	}
	
	@RequestMapping(value = "/bandejaentrada", method = RequestMethod.GET)
	@Transactional
	public String bandejaentrada(HttpServletRequest request, HttpSession session, Model model, @RequestParam("u") long id){
		User logueado = (User)session.getAttribute("user");
		User u = (User)entityManager.createNamedQuery("userById").setParameter("idParam", id).getSingleResult();
		
		if(logueado!=null&&this.mismoUsuario(u.getId(), logueado.getId())){
			model.addAttribute("u", u);
			u.marcarLeidos();
			session.setAttribute("user", u);
			model.addAttribute("pageTitle", "Bandeja de entrada");
			return "bandejaentrada";
		}
		
		else
			return "redirect:home";
	}
	
	/* --------------------------------------------------------------------------------*/
	
	/* ---------------MÉTODOS QUE EJECUTAN FUNCIONALIDADES DE LA PAGINA----------------*/
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@Transactional
	public String login(HttpServletRequest request, Model model, HttpSession session,  @RequestParam("login") String login) {
		String formLogin = request.getParameter("login");
		String formPass = request.getParameter("pass");
		String formSource = request.getParameter("source");
		logger.info("Login attempt from '{}' while visiting '{}'", formLogin, formSource);
				
		User u = null;
		try{
		u = (User)entityManager.createNamedQuery("userByLogin")
			.setParameter("loginParam", formLogin).getSingleResult();
		}
		catch(NoResultException nre){
			logger.info("Usuario incorrecto");
			return "redirect:home";
		}
		
		if (u.isPassValid(formPass)) {
			session.setAttribute("user", u);
			getTokenForSession(session);
			if(formLogin.equals("admin")) {
				return "redirect:administrador";
			}
		} else {
			model.addAttribute("loginError", "Error al loguear");
		}
		
		return "redirect:home";
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		logger.info("User '{}' logged out", session.getAttribute("user"));
		session.invalidate();
		return "redirect:/";
	}
		
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@Transactional
	public String register(HttpServletRequest request, Model model, HttpSession session) {
		String formLogin = request.getParameter("nombreusu");
		String formNombre = request.getParameter("nombre");
		String formApellidos = request.getParameter("apellidos");
		String formEmail = request.getParameter("email");
		String formProfesion = request.getParameter("profesion");
		String formAficiones = request.getParameter("aficiones");
		String formCiudad = request.getParameter("ciudad");
		String formPass = request.getParameter("password");
		String formFDN = request.getParameter("fdn");
		String formSource = request.getParameter("source");
		logger.info("Login attempt from '{}' while visiting '{}'", formLogin, formSource);
		
		User user = User.createUser(formLogin, formPass, formNombre, formApellidos, formEmail, formProfesion, formAficiones, formCiudad, formFDN);
		entityManager.persist(user);				
		session.setAttribute("user", user);
		
		return "redirect:home";
	}
	
	@RequestMapping(value = "/updateprofile", method = RequestMethod.POST)
	@Transactional
	public String modificarPerfil(@RequestParam("photo") MultipartFile photo, HttpServletRequest request, Model model, HttpSession session) {
		User l = (User) session.getAttribute("user");
		User u = (User)entityManager.createNamedQuery("userById").setParameter("idParam", l.getId()).getSingleResult();
		String formNombre = request.getParameter("nombre");
		String formEmail = request.getParameter("email");
		String formProfesion = request.getParameter("profesion");
		String formAficiones = request.getParameter("aficiones");
		String formCiudad = request.getParameter("ciudad");
		String formPass = request.getParameter("password");
		String formFDN = request.getParameter("fdn");

        if (!photo.isEmpty()) {
            try {
                byte[] bytes = photo.getBytes();
                BufferedOutputStream stream =
                        new BufferedOutputStream(
                        		new FileOutputStream(ContextInitializer.getFile("user", Long.toString(l.getId()))));
                stream.write(bytes);
                stream.close();
            } catch (Exception e) {
                return "You failed to upload " + l.getId() + " => " + e.getMessage();
            }
        }
		
		u.updateUser(formPass, formNombre,formEmail, formProfesion, formAficiones, formCiudad, formFDN);
		session.setAttribute("user", u);
		
		return "redirect:perfil";
	}
	
	@RequestMapping(value = "/addquedada", method = RequestMethod.POST)
	@Transactional
	public String registerQuedada(HttpServletRequest request, Model model, HttpSession session) {
		User u = (User) session.getAttribute("user");
		
		if(u!=null){
		String formCaut = request.getParameter("caut");
		String formCiudad = request.getParameter("ciudad");
		String formCine = request.getParameter("cine");
		String formPelicula = request.getParameter("peli");
		String formDescripcion = request.getParameter("descripcion");
				
		Quedada quedada = Quedada.createQuedada(formCaut, formCiudad, formCine, formPelicula, formDescripcion);
		entityManager.persist(quedada);
		
		quedada.addUsuario(u);
		u.addQuedadaLista(quedada);
		}
	
		return "redirect:quedadas";
	}
	
	@RequestMapping(value = "/addpelicula", method = RequestMethod.POST)
	@Transactional
	public String addPelicula(HttpServletRequest request, Model model, HttpSession session){
		String imdbid = request.getParameter("imdbid");
		String contenido = null;
		List<Pelicula> pe = entityManager.createQuery("select p from Pelicula p").getResultList();
		boolean existe = false;
		int i=0;
		
		while(i<pe.size()&&existe==false){
			if(imdbid.equalsIgnoreCase(pe.get(i).getIMDbId()))
					existe=true;
			else i++;
		}
		
		if(!existe){
		try {
			URL u = new URL( "http://www.omdbapi.com/?i="+imdbid);
			BufferedReader in = new BufferedReader(new InputStreamReader(u.openStream()));			
			contenido = in.readLine();
			in.close();
		}  catch (IOException e) {logger.error("Imposible recuperar info");}
		
		try{
		JSONObject o = new JSONObject(contenido);
		String titulo = o.getString("Title");
	    String anio = o.getString("Year");
	    String genero = o.getString("Genre");
	    String director = o.getString("Director");
	    String reparto= o.getString("Actors");
	    String sinopsis= o.getString("Plot");
	    String urlimg = o.getString("Poster");
	    
	    Pelicula p = Pelicula.createPelicula(titulo, director, genero, reparto, sinopsis, anio, imdbid);
		entityManager.persist(p);
		
		logger.info("Pelicula " + p.getNombre() + " aï¿½adida con id " + p.getId());
		
		try {
			URL imgurl = new URL(urlimg);
			BufferedImage image = ImageIO.read(imgurl);
			File outputfile = ContextInitializer.getFile("pelicula", Long.toString(p.getId()));
				ImageIO.write(image, "jpg", outputfile);
				
			} catch (IOException e) {}		
		
		}
		catch(JSONException e1){};
		}

		return "redirect:peliculas";
	}
	
	@RequestMapping(value = "/addserie", method = RequestMethod.POST)
	@Transactional
	public String addSerie(HttpServletRequest request, Model model, HttpSession session){
		
		String imdbid = request.getParameter("imdbid");
		List<Serie> se = entityManager.createQuery("select s from Serie s").getResultList();
		String contenido = null;
		boolean existe = false;
		int i=0;
		
		while(i<se.size()&&existe==false){
			if(imdbid.equalsIgnoreCase(se.get(i).getIMDbId()))
					existe=true;
			else i++;
		}
		
		if(!existe){
		try {
			URL u = new URL( "http://www.omdbapi.com/?i="+imdbid);
			BufferedReader in = new BufferedReader(new InputStreamReader(u.openStream()));			
			contenido = in.readLine();
			in.close();
		}  catch (IOException e) {
			logger.error("Imposible recuperar info");
		}
		
		try{
		JSONObject o = new JSONObject(contenido);
		String titulo = o.getString("Title");
	    String anio = o.getString("Year");
	    String genero = o.getString("Genre");
	    String creador = o.getString("Writer");
	    String reparto= o.getString("Actors");
	    String sinopsis= o.getString("Plot");
	    String urlimg = o.getString("Poster");
	    
	    Serie s = Serie.createSerie(titulo, creador, genero, reparto, sinopsis, anio, imdbid);
		entityManager.persist(s);
		
		logger.info("Serie " + s.getNombre() + " aÃ±adida con id " + s.getId());
		
		try {
			URL imgurl = new URL(urlimg);
			BufferedImage image = ImageIO.read(imgurl);
			File outputfile = ContextInitializer.getFile("serie", Long.toString(s.getId()));
				ImageIO.write(image, "jpg", outputfile);
				
			} catch (IOException e) {}
		
		}
		catch(JSONException e1){};
		}

		return "redirect:" + "series";
	}
	
	@RequestMapping(value = "/addcapitulo", method = RequestMethod.POST)
	@Transactional
	public String addCapitulo(HttpServletRequest request, Model model, HttpSession session, @RequestParam("s") long id){
		int formTemporada;
		int formCapitulo;
		Serie s;
		String imdbid;
		try{
			formTemporada = Integer.parseInt(request.getParameter("temporada"));
			formCapitulo = Integer.parseInt(request.getParameter("capitulo"));
			s = (Serie)entityManager.createNamedQuery("serieById").setParameter("idParam", id).getSingleResult();
			imdbid = request.getParameter("imdbid");
		}
		catch(NumberFormatException nre){logger.info("Capitulo incorrecto"); return "redirect:" + "serie?s=" + id;}

		if(!s.existeCapEnTemp(formTemporada, formCapitulo)){
			try {
				URL u = new URL( "http://www.omdbapi.com/?i="+imdbid);
				BufferedReader in = new BufferedReader(new InputStreamReader(u.openStream()));
			    String contenido;
			    contenido = in.readLine();
			    in.close();
		
		
				try{
					JSONObject o = new JSONObject(contenido);
					String titulo = o.getString("Title");
					String fechaemision = o.getString("Released");
					String director = o.getString("Director");
					String reparto= o.getString("Actors");
					String sinopsis= o.getString("Plot");
		 
					Capitulo c = Capitulo.createCapitulo(formCapitulo,titulo,director,reparto,fechaemision,sinopsis, imdbid);
		    
					entityManager.persist(c);
		
					if(s.existeTemporada(formTemporada)){
						s.addCapituloTemporada(formTemporada, c);
					}
					else{
						Temporada t = Temporada.createTemporada(formTemporada);
						entityManager.persist(t);
						t.addCapitulo(c);
						s.addTemporada(t);
					}
				
				}
				catch(NumberFormatException nre){logger.info("Capitulo incorrecto"); return "redirect:" + "serie?s=" + id;}
				catch(JSONException e1){}
			} 
			catch(NumberFormatException nre){logger.info("Capitulo incorrecto"); return "redirect:" + "serie?s=" + id;}
			catch (IOException e) {logger.error("Imposible recuperar info");}	
		}	
		return "redirect:" + "serie?s=" + id;	
	}
	
	@ResponseBody
	@RequestMapping(value="/user/photo", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] userPhoto(@RequestParam("id") String id) throws IOException {
	    File f = ContextInitializer.getFile("user", id);
	    InputStream in = null;
	    if (f.exists()) {
	    	in = new BufferedInputStream(new FileInputStream(f));
	    } else {
	    	in = new BufferedInputStream(
	    			this.getClass().getClassLoader().getResourceAsStream("unknown-user.jpg"));
	    }
	    
	    return IOUtils.toByteArray(in);
	}
	
	@ResponseBody
	@RequestMapping(value="/pelicula/photo", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] peliPhoto(@RequestParam("id") String idpelicula) throws IOException {
	    File f = ContextInitializer.getFile("pelicula", idpelicula);
	    InputStream in = null;
	    if (f.exists()) {
	    	in = new BufferedInputStream(new FileInputStream(f));
	    } else {
	    	in = new BufferedInputStream(
	    			this.getClass().getClassLoader().getResourceAsStream("unknown-film.jpg"));
	    }
	    
	    return IOUtils.toByteArray(in);
	}
	
	@ResponseBody
	@RequestMapping(value="/serie/photo", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] seriePhoto(@RequestParam("id") String idserie) throws IOException {
	    File f = ContextInitializer.getFile("serie", idserie);
	    InputStream in = null;
	    if (f.exists()) {
	    	in = new BufferedInputStream(new FileInputStream(f));
	    } else {
	    	in = new BufferedInputStream(
	    			this.getClass().getClassLoader().getResourceAsStream("unknown-film.jpg"));
	    }
	    
	    return IOUtils.toByteArray(in);
	}
	
	@ResponseBody
	@RequestMapping(value="/buscaIncremental", method = RequestMethod.GET)
	public String buscaIncremental(@RequestParam("term") String term) {
		
		List<Serie> se = entityManager.createQuery("select s from Serie s").getResultList();
		List<Pelicula> pe = entityManager.createQuery("select p from Pelicula p").getResultList();
		List<User> us = entityManager.createQuery("select u from User u").getResultList();
		
		JSONObject o = new JSONObject();
		JSONArray j = new JSONArray();
		
		try{
		for(int i=0; i<se.size(); i++){
			o = new JSONObject();
			o.append("id", "serie?s="+se.get(i).getId());
			o.append("label", se.get(i).getNombre()+" (Serie)");
			o.append("value", se.get(i).getNombre());
			if(se.get(i).getNombre().contains(term))
				j.put(o);
			}
		
		for(int i=0; i<pe.size(); i++){
			o = new JSONObject();
			o.append("id", "pelicula?p="+pe.get(i).getId());
			o.append("label", pe.get(i).getNombre()+" (Pelicula)");
			o.append("value", pe.get(i).getNombre());
			if(pe.get(i).getNombre().contains(term))
			j.put(o);
			}
		
		for(int i=0; i<us.size(); i++){
			o = new JSONObject();
			o.append("id", "usuario?u="+us.get(i).getId());
			o.append("label", us.get(i).getLogin()+" (Usuario)");
			o.append("value", us.get(i).getLogin());
			if(!us.get(i).getRole().equalsIgnoreCase("admin")&& us.get(i).getLogin().contains(term))
				j.put(o);
			}
		
		} catch (JSONException e) {}
		
		return j.toString();

	}
	
	@RequestMapping(value = "/delUser", method = RequestMethod.POST)
	@ResponseBody
	@Transactional // needed to allow DB change
	public void delUser(@RequestParam("id") long id, @RequestParam("csrf") String token, HttpSession session) {
		List<Comentario> co = entityManager.createQuery("select c from Comentario c").getResultList();
		
		for(Comentario c: co)
			if(c.getUsuario().getId()==id){
				System.err.println("Comentario eliminado");
				entityManager.createNamedQuery("delComentario").setParameter("idParam", c.getId()).executeUpdate();}
		
		entityManager.createNamedQuery("delUser").setParameter("idParam", id).executeUpdate();
	}
	
	@RequestMapping(value = "/delPelicula", method = RequestMethod.POST)
	@ResponseBody
	@Transactional // needed to allow DB change
	public void delPeli(@RequestParam("id") long id, @RequestParam("csrf") String token, HttpSession session) {
		entityManager.createNamedQuery("delPelicula").setParameter("idParam", id).executeUpdate();
	}
	
	@RequestMapping(value = "/delSerie", method = RequestMethod.POST)
	@ResponseBody
	@Transactional // needed to allow DB change
	public void delSerie(@RequestParam("id") long id, @RequestParam("csrf") String token, HttpSession session) {
		entityManager.createNamedQuery("delSerie").setParameter("idParam", id).executeUpdate();
	}
	
	@RequestMapping(value = "/delQuedada", method = RequestMethod.POST)
	@ResponseBody
	@Transactional // needed to allow DB change
	public void delQuedada(@RequestParam("id") long id, @RequestParam("csrf") String token, HttpSession session) {
		entityManager.createNamedQuery("delQuedada").setParameter("idParam", id).executeUpdate();
	}
	
	@RequestMapping(value = "/delReporte", method = RequestMethod.POST)
	@ResponseBody
	@Transactional // needed to allow DB change
	public void delReporte(@RequestParam("id") long id, @RequestParam("csrf") String token, HttpSession session) {
		entityManager.createNamedQuery("delReporte").setParameter("idParam", id).executeUpdate();
	}
	
	static boolean isTokenValid(HttpSession session, String token) {
	    Object t=session.getAttribute("csrf_token");
	    return (t != null) && t.equals(token);
	}
	
	/**
	 * Returns an anti-csrf token for a session, and stores it in the session
	 * @param session
	 * @return
	 */
	static String getTokenForSession (HttpSession session) {
	    String token=UUID.randomUUID().toString();
	    session.setAttribute("csrf_token", token);
	    return token;
	}
	
	@RequestMapping(value = "/addserielista", method = RequestMethod.GET)
	@Transactional
	public String addserielista(HttpServletRequest request, Model model, @RequestParam("s") long id,HttpSession session){
		User l = (User) session.getAttribute("user");
		User u = (User)entityManager.createNamedQuery("userById").setParameter("idParam", l.getId()).getSingleResult();
		Serie s = (Serie)entityManager.createNamedQuery("serieById").setParameter("idParam", id).getSingleResult();
		
		if(!u.serieEnFav(s.getId())){
		u.addSerieLista(s);
		s.addUsuarioLista(u);
		session.setAttribute("user", u);
		}
		return "redirect:" + "serie?s=" + id;
	}
	
	@RequestMapping(value = "/addpeliculalista", method = RequestMethod.GET)
	@Transactional
	public String addpeliculalista(HttpServletRequest request, Model model, @RequestParam("p") long id,HttpSession session){
		User l = (User) session.getAttribute("user");
		User u = (User)entityManager.createNamedQuery("userById").setParameter("idParam", l.getId()).getSingleResult();
		Pelicula p = (Pelicula)entityManager.createNamedQuery("peliById").setParameter("idParam", id).getSingleResult();
		
		if(!u.peliculaEnFav(p.getId())){
		u.addPeliculaLista(p);
		p.addUsuarioLista(u);
		session.setAttribute("user", u);
		}
		return "redirect:" + "pelicula?p=" + id;
	}
	
	@RequestMapping(value = "/deletepeliculalista", method = RequestMethod.GET)
	@Transactional
	public String peliBorrar(HttpServletRequest request, Model model, HttpSession session,@RequestParam("p") long idue,@RequestParam("ud") long idu){
		User logueado = (User) session.getAttribute("user");
		User ue = (User)entityManager.createNamedQuery("userById").setParameter("idParam", idu).getSingleResult();

		Pelicula p = (Pelicula)entityManager.createNamedQuery("peliById")
				.setParameter("idParam", idue).getSingleResult();
		if((this.usuarioLogueado(session))&&(logueado.peliculaEnFav(idue))){
			ue.peliDeleteFav(p);
			p.deleteUsuario(ue);
			session.setAttribute("user", ue);
		}
		return "redirect:" + "pelicula?p=" + idue;
	}
	
	@RequestMapping(value = "/deleteserielista", method = RequestMethod.GET)
	@Transactional
	public String serieBorrar(HttpServletRequest request, Model model, HttpSession session,@RequestParam("s") long idue,@RequestParam("ud") long idu){
		User logueado = (User) session.getAttribute("user");
		User ue = (User)entityManager.createNamedQuery("userById").setParameter("idParam", idu).getSingleResult();

		Serie s = (Serie)entityManager.createNamedQuery("serieById")
				.setParameter("idParam", idue).getSingleResult();
		if((this.usuarioLogueado(session))&&(logueado.serieEnFav(idue))){
			ue.serieDeleteFav(s);
			s.deleteUsuario(ue);
			System.err.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAd");
			session.setAttribute("user", ue);
			System.err.println("BBBBBBBBBBBBBBBBBBBBBBBBBd");
		}
		return "redirect:" + "serie?s=" + idue;
	}
	
	@RequestMapping(value = "/deletequedadalista", method = RequestMethod.GET)
	@Transactional
	public String quedadaBorrar(HttpServletRequest request, Model model, HttpSession session,@RequestParam("q") long idue,@RequestParam("ud") long idu){
		User logueado = (User) session.getAttribute("user");
		User ue = (User)entityManager.createNamedQuery("userById").setParameter("idParam", idu).getSingleResult();

		Quedada q = (Quedada)entityManager.createNamedQuery("quedadaById")
				.setParameter("idParam", idue).getSingleResult();
		if(this.usuarioLogueado(session)&& q.userEnLista(idu)){
			q.deleteUsuario(ue);
			ue.quedadaDelete(q);
			session.setAttribute("user", ue);
		}
		return "redirect:" + "quedada?q=" + idue;
	}
	
	@RequestMapping(value = "/addquedadalista", method = RequestMethod.GET)
	@Transactional
	public String addquedadalista(HttpServletRequest request, Model model, @RequestParam("q") long id,HttpSession session){
		User l = (User) session.getAttribute("user");
		User u = (User)entityManager.createNamedQuery("userById").setParameter("idParam", l.getId()).getSingleResult();
		Quedada q = (Quedada)entityManager.createNamedQuery("quedadaById").setParameter("idParam", id).getSingleResult();
		
		if(!q.userEnLista(u.getId())){
		q.addUsuario(u);
		u.addQuedadaLista(q);
		session.setAttribute("user", u);
		}
		return "redirect:" + "quedada?q=" + id;
	}
	
	@RequestMapping(value = "/addcomentarioserie", method = RequestMethod.POST)
	@Transactional
	public String addCommentSerie(HttpServletRequest request, Model model, HttpSession session, @RequestParam("s") long ids, @RequestParam("u") long idu){
		User logueado = (User) session.getAttribute("user");
		String formTexto = request.getParameter("texto");
		Serie s = (Serie)entityManager.createNamedQuery("serieById").setParameter("idParam", ids).getSingleResult();
		User u = (User)entityManager.createNamedQuery("userById").setParameter("idParam", idu).getSingleResult();
		
		if(this.usuarioLogueado(session)&&this.mismoUsuario(logueado.getId(), idu)){
			Comentario co = Comentario.createComentario(formTexto, u);
			entityManager.persist(co);
			s.addComment(co);
		}
		return "redirect:" + "serie?s=" + ids;
	}
	
	@RequestMapping(value = "/addcomentarioquedada", method = RequestMethod.POST)
	@Transactional
	public String addCommentQuedada(HttpServletRequest request, Model model, HttpSession session, @RequestParam("q") long idp, @RequestParam("u") long idu){
		User logueado = (User) session.getAttribute("user");
		String formTexto = request.getParameter("texto");
		Quedada q = (Quedada)entityManager.createNamedQuery("quedadaById").setParameter("idParam", idp).getSingleResult();
		User u = (User)entityManager.createNamedQuery("userById").setParameter("idParam", idu).getSingleResult();
		
		if(this.usuarioLogueado(session)&&this.mismoUsuario(logueado.getId(), idu)){
			Comentario co = Comentario.createComentario(formTexto, u);
	    	entityManager.persist(co);
			q.addComment(co);
		}
	
		return "redirect:" + "quedada?q=" + idp;
	}
	
	@RequestMapping(value = "/addcomentariocapitulo", method = RequestMethod.POST)
	@Transactional
	public String addCommentCapitulo(HttpServletRequest request, Model model, HttpSession session, @RequestParam("s") long ids, 
			@RequestParam("t") long idt, @RequestParam("c") long idc, @RequestParam("u") long idu){
		User logueado = (User) session.getAttribute("user");
		String formTexto = request.getParameter("texto");
		Capitulo c = (Capitulo)entityManager.createNamedQuery("capituloById").setParameter("idParam", idc).getSingleResult();
		User u = (User)entityManager.createNamedQuery("userById").setParameter("idParam", idu).getSingleResult();
		
		if(this.usuarioLogueado(session)&&this.mismoUsuario(logueado.getId(), idu)){
			Comentario co = Comentario.createComentario(formTexto, u);
			entityManager.persist(co);
			c.addComment(co);
		}
	
		return "redirect:" + "capitulo?s=" + ids + "&t=" + idt + "&c=" + idc;
	}
	
	@RequestMapping(value = "/addcomentariopelicula", method = RequestMethod.POST)
	@Transactional
	public String addCommentPelicula(HttpServletRequest request, Model model, HttpSession session, @RequestParam("p") long idp, @RequestParam("u") long idu){
		User logueado = (User) session.getAttribute("user");
		String formTexto = request.getParameter("texto");
		Pelicula p = (Pelicula)entityManager.createNamedQuery("peliById").setParameter("idParam", idp).getSingleResult();
		User u = (User)entityManager.createNamedQuery("userById").setParameter("idParam", idu).getSingleResult();
		
		if(this.usuarioLogueado(session)&&this.mismoUsuario(logueado.getId(), idu)){
			Comentario co = Comentario.createComentario(formTexto, u);
	    	entityManager.persist(co);
			p.addComment(co);
		}
	
		return "redirect:" + "pelicula?p=" + idp;
	}
	
	@RequestMapping(value = "/enviarmensaje", method = RequestMethod.POST)
	@Transactional
	public String enviarMP(HttpServletRequest request, Model model, HttpSession session, @RequestParam("ue") long idue, @RequestParam("ud") long idud){
		User logueado = (User) session.getAttribute("user");
		String formAsunto = request.getParameter("asunto");
		String formTexto = request.getParameter("texto");
		User ue = (User)entityManager.createNamedQuery("userById").setParameter("idParam", idue).getSingleResult();
		User ud = (User)entityManager.createNamedQuery("userById").setParameter("idParam", idud).getSingleResult();
		
		if(this.usuarioLogueado(session)&&this.mismoUsuario(logueado.getId(), idue)){
			Mensaje mp = Mensaje.createMP(formAsunto, formTexto, ue);
	    	entityManager.persist(mp);
			ud.addMP(mp);
		}
		return "redirect:" + "usuario?u=" + idud;
	}
	
	@RequestMapping(value = "/eliminarmensaje", method = RequestMethod.GET)
	@Transactional
	public String eliminarMP(HttpServletRequest request, Model model, HttpSession session, @RequestParam("u") long idu, @RequestParam("m") long idm){
		User logueado = (User) session.getAttribute("user");
		String formAsunto = request.getParameter("asunto");
		String formTexto = request.getParameter("texto");
		User u = (User)entityManager.createNamedQuery("userById").setParameter("idParam", idu).getSingleResult();
		Mensaje m = (Mensaje)entityManager.createNamedQuery("mensajeById").setParameter("idParam", idm).getSingleResult();
		
		if(this.usuarioLogueado(session)&&this.mismoUsuario(logueado.getId(), idu)){
	    u.eliminarMensaje(m.getId());
	    entityManager.remove(m);
		}
	
		return "redirect:" + "bandejaentrada?u=" + idu;
	}
	
	@RequestMapping(value = "/friendrequest", method = RequestMethod.GET)
	@Transactional
	public String friendRequest(HttpServletRequest request, Model model, HttpSession session, @RequestParam("ue") long idue, @RequestParam("ud") long idud){
		User logueado = (User) session.getAttribute("user");
		User ue = (User)entityManager.createNamedQuery("userById").setParameter("idParam", idue).getSingleResult();
		User ud = (User)entityManager.createNamedQuery("userById").setParameter("idParam", idud).getSingleResult();
		
		if(this.usuarioLogueado(session)&&this.mismoUsuario(logueado.getId(), idue)
				&&(!ue.sonAmigos(idud))&&(!ud.peticionExiste(idue))){
			Mensaje fr = Mensaje.createFR(ue);
			entityManager.persist(fr);
			ud.addFR(fr);
		}
		return "redirect:" + "usuario?u=" + idud;
	}
	
	@RequestMapping(value = "/friendborrar", method = RequestMethod.GET)
	@Transactional
	public String friendBorrar(HttpServletRequest request, Model model, HttpSession session, @RequestParam("ue") long idue, @RequestParam("ud") long idud){
		User logueado = (User) session.getAttribute("user");
		User ue = (User)entityManager.createNamedQuery("userById").setParameter("idParam", idue).getSingleResult();
		User ud = (User)entityManager.createNamedQuery("userById").setParameter("idParam", idud).getSingleResult();
		
		if(this.usuarioLogueado(session)&&this.mismoUsuario(logueado.getId(), idue)
				&&(ue.sonAmigos(idud))){
			ud.deleteAmigo(ue);
			ue.deleteAmigo(ud);
			session.setAttribute("user", ud);
		}
		return "redirect:" + "usuario?u=" + idud;
	}
	
	
	@RequestMapping(value = "/acceptfr", method = RequestMethod.GET)
	@Transactional
	public String acceptFR(HttpServletRequest request, Model model, HttpSession session, @RequestParam("ue") long idue, @RequestParam("ud") long idud, @RequestParam("fr") long idfr){
		User logueado = (User) session.getAttribute("user");
		User ue = (User)entityManager.createNamedQuery("userById").setParameter("idParam", idue).getSingleResult();
		User ud = (User)entityManager.createNamedQuery("userById").setParameter("idParam", idud).getSingleResult();
		Mensaje fr = (Mensaje)entityManager.createNamedQuery("mensajeById").setParameter("idParam", idfr).getSingleResult();
		
		if(this.usuarioLogueado(session)&&this.mismoUsuario(logueado.getId(), idud)&&(!ue.sonAmigos(idud))){
			ue.addAmigo(ud);
			ud.addAmigo(ue);
			if(ue.peticionExiste(idud))
				ue.eliminarMensaje(fr.getId());
			ud.eliminarMensaje(fr.getId());
			fr.setUsuario(null);
			entityManager.createNamedQuery("delMensaje").setParameter("idParam", fr.getId()).executeUpdate();
			session.setAttribute("user", ud);
		}
		
		
		return "redirect:" + "bandejaentrada?u=" + idud;
	}
	
	@RequestMapping(value = "/rejectfr", method = RequestMethod.GET)
	@Transactional
	public String rejectFR(HttpServletRequest request, Model model, HttpSession session, @RequestParam("u") long idu, @RequestParam("fr") long idfr){
		User logueado = (User) session.getAttribute("user");
		Mensaje fr = (Mensaje)entityManager.createNamedQuery("mensajeById").setParameter("idParam", idfr).getSingleResult();
		User u = (User)entityManager.createNamedQuery("userById").setParameter("idParam", idu).getSingleResult();
		
		if(this.usuarioLogueado(session)&&this.mismoUsuario(logueado.getId(), idu)){
			u.eliminarMensaje(fr.getId());
			fr.setUsuario(null);
			entityManager.createNamedQuery("delMensaje").setParameter("idParam", fr.getId()).executeUpdate();
		}
		
		return "redirect:" + "bandejaentrada?u=" + idu;
	}
	
	@RequestMapping(value = "/report", method = RequestMethod.POST)
	@Transactional
	public String report(HttpServletRequest request, Model model, HttpSession session, @RequestParam("id") long id, @RequestParam("e") String tipo){
		String formMotivo=request.getParameter("motivo");
		Serie s = null;
		User u = null;
		Pelicula p = null;
		Capitulo c =null;
		Reporte r = null;
		
		if(tipo.equalsIgnoreCase("serie")){
			s = (Serie)entityManager.createNamedQuery("serieById").setParameter("idParam", id).getSingleResult();
			r = Reporte.createReporte(formMotivo, id, tipo, s.getNombre());
			}
		else if(tipo.equalsIgnoreCase("usuario")){
			u = (User)entityManager.createNamedQuery("userById").setParameter("idParam", id).getSingleResult();
			r = Reporte.createReporte(formMotivo, id, tipo, u.getLogin());
			}
		else if(tipo.equalsIgnoreCase("pelicula")){
			p = (Pelicula)entityManager.createNamedQuery("peliById").setParameter("idParam", id).getSingleResult();
			r = Reporte.createReporte(formMotivo, id, tipo, p.getNombre());
			}
		else if(tipo.equalsIgnoreCase("capitulo")){
			c = (Capitulo)entityManager.createNamedQuery("capituloById").setParameter("idParam", id).getSingleResult();
			r = Reporte.createReporte(formMotivo, id, tipo, c.getNombre());
			}
		entityManager.persist(r);
		
		
		return "redirect:home";
	}
	
	@RequestMapping(value = "/parecidos", method = RequestMethod.GET)
	@Transactional
	public String masParecido(){
		List<User> us = entityManager.createQuery("select u from User u").getResultList();
		HashMap<User, Set<Long> > pelisFavoritas = new HashMap<User, Set<Long> >();
		for (User u : us) {
			if (u.getListaPeliculas().size() > 0) {
				pelisFavoritas.put(u, conjuntoDeIds(u.getListaPeliculas()));
			}
		}

		HashMap<User, TreeSet<Parecido> > genteParecida = 
					new HashMap<User, TreeSet<Parecido> >();		
		for (User a : us) {
			if (pelisFavoritas.containsKey(a)) {
				for (User b : us) {
					if (a == b) {
						break;
					} else if (pelisFavoritas.containsKey(b)) {
					
						HashSet<Long> interseccion = new HashSet<Long>(pelisFavoritas.get(a));
						interseccion.retainAll(pelisFavoritas.get(b));
						
						HashSet<Long> union = new HashSet<Long>(pelisFavoritas.get(a));
						union.addAll(pelisFavoritas.get(b));
					
						// Jaccard distance
						double parecido = ((double)interseccion.size())/union.size();
						if (! genteParecida.containsKey(a)) {
							genteParecida.put(a, new TreeSet<Parecido>());
						}
						if (! genteParecida.containsKey(b)) {
							genteParecida.put(b, new TreeSet<Parecido>());
						}
						genteParecida.get(a).add(new Parecido(b, parecido));
						genteParecida.get(b).add(new Parecido(a, parecido));
					}
				}
			}
		}
		
		for (User u : us) {
			System.err.println("Los mas parecidos a " + u.getNombre() + " son:\n");
			if ( ! genteParecida.containsKey(u)) {
				System.err.println("nadie!\n");
			} else {
				Iterator it = (Iterator) genteParecida.get(u).iterator();
				for (int i=0; i<5 && i<genteParecida.get(u).size(); i++) {
					u.addUsuarioParecido(it.next().toString());
				}
			}
		}
		this.pelisParecidas();
		this.seriesParecidas();
		return "redirect:administrador";
	}
	
	/*---------------------------------------------------------------------------*/
	
	static boolean isAdmin(HttpSession session) {
		User u = (User)session.getAttribute("user");
		if (u != null) {
			return u.getRole().equals("admin");
		} else {
			return false;
		}
	}
	
	static boolean mismoUsuario(long l, long m) {
		if (l==m) {
			return true;
		} else {
			return false;
		}
	}
	
	static boolean usuarioLogueado(HttpSession session) {
		User u = (User)session.getAttribute("user");
		if (u==null) {
			return false;
		} else {
			return true;
		}
	}
	
	// Devuelve un conjunto de ids de peliculas
		Set<Long> conjuntoDeIds(List<Pelicula> pelis) {
			Set<Long> s = new HashSet<Long>();
			for (Pelicula p : pelis) {
				s.add(p.getId());
			}
			return s;
		}
		
		static class Parecido implements Comparable<Parecido> {
			public User quien;
			public double cuanto;
			Parecido(User quien, double cuanto) {
				this.quien = quien; this.cuanto = cuanto;
			}
			@Override
			public int compareTo(Parecido o) {
				return Double.compare(cuanto, o.cuanto);
			}
			public String toString() {
				return Long.toString(quien.getId());
				//return quien.getNombre() + " " + cuanto;
			}
		}
		
		public void pelisParecidas(){
			List<User> us = entityManager.createQuery("select u from User u").getResultList();
			List<Pelicula> pe = entityManager.createQuery("select p from Pelicula p").getResultList();
			String pelisParecidas = "";
			
			for(User u: us){
				List<Pelicula> pf = u.getListaPeliculas();
				u.setPelisParecidas("");
				
				for(Pelicula p: pf){
					String[] generopf= p.getGenero().split(", ");
						for(Pelicula pt: pe){
							if(p.getId() != pt.getId()){
							int rango=0;
							String[] generopt= pt.getGenero().split(", ");
							for(int i=0; i<generopt.length;i++){
								for(int j=0; j< generopf.length;j++){
									if(generopt[i].equalsIgnoreCase(generopf[j]))
										rango+=1;
								}
							}
							if (rango>=1&&!u.peliculaEnFav(pt.getId()))
								u.addPeliParecida(pt.getId());
						}
					}
					
				}
				
			}
			
		}
		
		public void seriesParecidas(){
			List<User> us = entityManager.createQuery("select u from User u").getResultList();
			List<Serie> se = entityManager.createQuery("select s from Serie s").getResultList();
			String seriesParecidas = "";
			
			for(User u: us){
				List<Serie> sf = u.getListaSeries();
				u.setSeriesParecidas("");
				
				for(Serie s: sf){
					String[] generosf= s.getGenero().split(", ");
						for(Serie st: se){
							if(s.getId() != st.getId()){
							int rango=0;
							String[] generost= st.getGenero().split(", ");
							for(int i=0; i<generost.length;i++){
								for(int j=0; j< generosf.length;j++){
									if(generost[i].equalsIgnoreCase(generosf[j]))
										rango+=1;
								}
							}
							if (rango>=1&&!u.serieEnFav(st.getId()))
								u.addSerieParecida(st.getId());
						}
					}
					
				}
				
			}
			
		}

		
}

