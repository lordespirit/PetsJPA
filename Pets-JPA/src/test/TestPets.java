package test;


import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import dao.DBManager;
import model.Mascota;
import model.Persona;

public class TestPets {
	DBManager dbManager; 
	Persona person;
	Mascota mascota;
	
	@Before
	public void init(){
		dbManager = new DBManager(); 	
		dbManager.connect();
		dbManager.deleteAll(Persona.class); 
		dbManager.close(); 		
	}
	
	@Test
	public void testInsert(){	
		
		Mascota mascota1 = getMockMascota("Perro1", "canido");
		Mascota mascota2 = getMockMascota("Loro1", "ave");
		Persona person1 = getMockPersona("Juan", "Garcia");
		Persona person2 = getMockPersona("Anna", "Real");
		
		dbManager.connect();
			dbManager.getEntityManager().getTransaction().begin();

			dbManager.getEntityManager().persist(person1);
			dbManager.getEntityManager().persist(person2);
			
			person1.getMascota().add(mascota1);
			person2.getMascota().add(mascota2);
			
			dbManager.getEntityManager().getTransaction().commit();
		dbManager.close(); 	
		
		Assert.assertEquals(true,mascota1.getId()>0);
		Assert.assertEquals(true,mascota2.getId()>0);
	
	}
	
	
	@Test
	public void testSelect(){
		
		Mascota mascota1 = getMockMascota("Perro2", "canido");
		Mascota mascota2 = getMockMascota("Gato1", "felino");
		Mascota mascota3 = getMockMascota("Perro1", "canido");
		Mascota mascota4 = getMockMascota("Loro1", "ave");
		
		Persona person1 = getMockPersona("Pedro", "Zafon");
		Persona person2 = getMockPersona("Julian", "Gotero");
		Persona person3 = getMockPersona("Juan", "Garcia");
		Persona person4 = getMockPersona("Anna", "Real");
		
		dbManager.connect();
		
			dbManager.getEntityManager().getTransaction().begin();
	
				dbManager.getEntityManager().persist(person1);
				dbManager.getEntityManager().persist(person2);
				dbManager.getEntityManager().persist(person3);
				dbManager.getEntityManager().persist(person4);
				
				person1.getMascota().add(mascota1);
				person2.getMascota().add(mascota2);
				person3.getMascota().add(mascota3);
				person4.getMascota().add(mascota4);
			
			dbManager.getEntityManager().getTransaction().commit();
			
			ArrayList<Persona> result;
			result = dbManager.selectLike(Persona.class, "nombre", "ju");
		
		dbManager.close(); 	
		
		Assert.assertEquals(2, result.size());
		Assert.assertEquals("Julian", result.get(0).getNombre());
		Assert.assertEquals("Juan", result.get(1).getNombre());
		
	}
	
	/*
	@Test
	public void testUpdate(){
		
		Mascota mascota1 = getMockMascota("Perro2", "canido");
		Mascota mascota2 = getMockMascota("Gato1", "felino");
		Mascota mascota3 = getMockMascota("Perro1", "canido");
		Mascota mascota4 = getMockMascota("Loro1", "ave");
		Mascota mascota5 = getMockMascota("Raton1", "roedor");
		Persona person1 = getMockPersona("Pedro", "Zafon");
		Persona person2 = getMockPersona("Julian", "Gotero");
		Persona person3 = getMockPersona("Juan", "Garcia");
		Persona person4 = getMockPersona("Anna", "Real");
		
		
		mascota1.setPersona(person1);
		mascota2.setPersona(person2);
		mascota3.setPersona(person3);
		mascota4.setPersona(person4);
		mascota5.setPersona(person2);
		mascota3.setPersona(person2);
		person2.getMascota().


		dbManager.connect();
		dbManager.insert(mascota1);
		dbManager.insert(mascota2);
		dbManager.insert(mascota3);
		dbManager.insert(mascota4);
		dbManager.insert(mascota5);
		dbManager.close(); 	

		// Aquí empiezo un update del empleado (employee), se obtiene el objeto y se realizan cambios dentro de una transaccion.
		// Cuando los cambios estan hechos se realiza el 'commit' para que el cambio se haga efectivo.
		// Este modo de actualizar los datos evita la creación de un nuevo registro de 'address' y evita basura en la BBDD.
		
		dbManager.connect();
		dbManager.getEntityManager().getTransaction().begin();
		
		Mascota mascotaUpdate = dbManager.getEntityManager().find(Mascota.class, mascota1.getId());
		
		mascotaUpdate.setNombre("Perrito1");
		mascotaUpdate.setAltura(1.75f);
		mascotaUpdate.setTipo("canido");

		mascotaUpdate.getPersona().setNombre("Persona12");
		mascotaUpdate.getPersona().setEmail("persona12@movistar.es");
		mascotaUpdate.getPersona().setTelefono("987123456");
		
		dbManager.getEntityManager().getTransaction().commit();
		dbManager.close();
		
		Assert.assertEquals(true, mascotaUpdate.getPersona().getId()>0);
		Assert.assertEquals("Persona12", mascotaUpdate.getPersona().getNombre());
		Assert.assertEquals("Perrito1", mascotaUpdate.getNombre());
		Assert.assertEquals(1.75f, mascotaUpdate.getAltura(),0.1);
		
		Assert.assertEquals(3,person2.getMascota().size());
		
	}
	
	*/

	private Persona getMockPersona(String nombre, String apellido) {
		 Persona person = new Persona();
		 person.setNombre(nombre);
		 person.setApellido(apellido);
		 person.setEmail("prueba123@gmail.com");
		 person.setTelefono("000000000");
		 person.setDireccion("Calle falsa 123");
		 return person;
	}
	
	private	Mascota getMockMascota(String nombre, String tipo) {
		Mascota mascota = new Mascota();
		mascota.setNombre(nombre);
		mascota.setTipo(tipo);
		mascota.setCalidad(1f);
		mascota.setLargo(1.1f);
		mascota.setAltura(0.9f);
		mascota.setPeso(123.321f);
		return  mascota;
	}

}