package es.unileon.ulebankoffice.domain;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


/**
 * La fecha de alta se establece automáticamente al momento de creación del objeto.
 * @author Razvan Raducu
 *
 */
@Document(collection = "Clientes")
public class ClienteDomain {

	@Id
	private String id;

	private String name;
	private String lastName;
	private String email;
	private String nacionalidad;

	private Date fechaNacimiento;
	private Date fechaDeAlta;

	@Indexed(unique = true)
	private Handler dni;

	private Documentos documentos;

	/**
	 * Constructor por defecto utilizado para instanciar objetos de esta clase
	 * cuando se obtiene el DBObject a través de mongo repository. La
	 * anotación @PersistanceConstructor indica que sea este el constructor que
	 * ha de usarse para tal acción. <b>Ver también:</b>
	 * http://docs.spring.io/spring-data/data-mongo/docs/1.1.0.RELEASE/reference
	 * /html/#mapping-chapter
	 * 
	 * @param name
	 * @param lastName
	 * @param email
	 * @param fechaNacimiento
	 * @param dni
	 * @param nacionalidad
	 * @param documentos
	 * @param fechaDeAlta
	 * @throws ParseException
	 */
	@PersistenceConstructor
	public ClienteDomain(String name, String lastName, String email, Date fechaNacimiento, Handler dni,
			 String nacionalidad, Documentos documentos, Date fechaDeAlta) throws ParseException {

		this.name = name;
		this.lastName = lastName;
		this.email = email;
		this.fechaNacimiento = fechaNacimiento;
		this.nacionalidad = nacionalidad;
		this.dni = dni;
		this.documentos = documentos;
		this.fechaDeAlta = fechaDeAlta;
		
	}

	/**
	 * Constructor usado para instanciar manualmente objetos de la clase
	 * ClienteDomain. <b>Su función principal es la creación de un cliente por
	 * primer vez</b> Recibe como parámetros de tipo String la fecha y el dni
	 * para que sea más fácul su declaración. Se comprueba si estos parámetros
	 * son adecuados sintáctica y semánticamente. Crea la instancia de
	 * Documentos que se persistirá.
	 * 
	 * @param name
	 * @param lastName
	 * @param email
	 * @param fechaNacimiento
	 * @param dni
	 * @param nacionalidad
	 * @param fechaDeAlta
	 * @throws ParseException
	 * @throws DNIException
	 */
	public ClienteDomain(String name, String lastName, String email, String fechaNacimiento, String dni,
			 String nacionalidad, Date fechaDeAlta) throws ParseException, DNIException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date userDate = df.parse(fechaNacimiento);

		this.name = name;
		this.lastName = lastName;
		this.email = email;
		this.fechaNacimiento = userDate;
		setDni(dni);
		this.nacionalidad = nacionalidad;
		this.documentos = new Documentos(new ArrayList<String>());
		this.fechaDeAlta = fechaDeAlta;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastname) {
		this.lastName = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public Handler getDni() {
		return dni;
	}

	public void setDni(String dni) throws DNIException {
		this.dni = new DNIHandler(dni);
	}

	public void setDni(Handler dni) throws DNIException {
		this.dni = dni;
	}

	/**
	 * Método que añade un documento al atributo de clase lista de id de
	 * documentos asignados a este cliente List<String> idDocumentos y que
	 * delega la inserción en la base de datos del documento a la clase agregada
	 * Documentos
	 * 
	 * @param documento
	 */
	public void addDocument(DocumentoAdjuntoDomain documento) {
		documentos.add(documento);
	}

	/**
	 * Método que obtiene el iterador concreto de la instancia de la clase
	 * agregada y que, uno por uno, obtiene todos los elementos de la colección
	 * y los devuelve en forma de lista.
	 * 
	 * @return Una lista con todos los documentos de la colección.
	 * @throws EmptyCollectionException 
	 */
	public List<DocumentoAdjuntoDomain> getDocumentos() throws EmptyCollectionException {
		// Este código sustituye al documentos.getDocumentos que había antes y
		// que hacía un repo.findByIdIn
		Iterator<DocumentoAdjuntoDomain> iterator = documentos.createIterator();
		List<DocumentoAdjuntoDomain> listaDocs = new ArrayList<>();

		for (iterator.firstElement(); iterator.hasMoreElements(); iterator.nextElement()) {

			listaDocs.add((DocumentoAdjuntoDomain) iterator.currentElement());

		}
		return listaDocs;
	}

	public String getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	public Date getFechaDeAlta() {
		return fechaDeAlta;
	}

	public void setFechaDeAlta(Date fechaDeAlta) {
		this.fechaDeAlta = fechaDeAlta;
	}
	
	

	@Override
	public String toString() {
		return "ClienteDomain [name=" + name + ", lastname=" + lastName + ", email=" + email + ", nacionalidad="
				+ nacionalidad + ", fechaNacimiento=" + fechaNacimiento + ", fechaDeAlta=" + fechaDeAlta + ", dni="
				+ dni + ", documentos=" + documentos + "]";
	}

	public String getId() {
		return id;
	}

}
