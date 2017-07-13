package model;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Mascota {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
    private String nombre; 
    private String tipo;
    
    @Column(name="ingreso", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Timestamp ingreso;
	private float peso;
	private float altura;
	private float largo;
	private float calidad;

	@ManyToOne(optional=false, cascade={CascadeType.PERSIST,CascadeType.REMOVE}) 
	@JoinColumn(name="persona_id", nullable = false, updatable = true, insertable = true)
	private Persona persona;
	
	
	/* GETTERS AND SETTERS */

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public int getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public void setTipo(String tipo){
		this.tipo = tipo;
	}
	
	public String getTipo(){
		return tipo;
	}

	public Timestamp getIngreso() {
		return ingreso;
	}

	public void setIngreso(Timestamp ingreso) {
		this.ingreso = ingreso;
	}

	public float getPeso() {
		return peso;
	}

	public void setPeso(float peso) {
		this.peso = peso;
	}

	public float getAltura() {
		return altura;
	}

	public void setAltura(float altura) {
		this.altura = altura;
	}

	public float getLargo() {
		return largo;
	}

	public void setLargo(float largo) {
		this.largo = largo;
	}
	
	public float getCalidad() {
		return calidad;
	}
	
	public void setCalidad(float calidad) {
		this.calidad = calidad;
	}
	
	
}