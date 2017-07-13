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
		

		dbManager.connect();
		
		dbManager.getEntityManager().getTransaction().begin();
		
			dbManager.getEntityManager().persist(person1);
			person1.getMascota().add(mascota1);
				mascota1.setPersona(person1);
			
			dbManager.getEntityManager().persist(person2);
			person2.getMascota().add(mascota2);
			person2.getMascota().add(mascota5);
				mascota2.setPersona(person2);
				mascota5.setPersona(person2);
			
			dbManager.getEntityManager().persist(person3);
			person3.getMascota().add(mascota3);
				mascota3.setPersona(person3);

			dbManager.getEntityManager().persist(person4);
			person4.getMascota().add(mascota4);
				mascota4.setPersona(person4);

	
			dbManager.getEntityManager().getTransaction().commit();
		
		dbManager.close(); 	

		// Aquí empiezo un update del empleado (employee), se obtiene el objeto y se realizan cambios dentro de una transaccion.
		// Cuando los cambios estan hechos se realiza el 'commit' para que el cambio se haga efectivo.
		// Este modo de actualizar los datos evita la creación de un nuevo registro de 'address' y evita basura en la BBDD.
		
		dbManager.connect();
		dbManager.getEntityManager().getTransaction().begin();
		
		Persona personaUpdate = dbManager.getEntityManager().find(Persona.class, person2.getId());
		
		personaUpdate.setNombre("Juanito");
		personaUpdate.setApellido("Gaston");
		personaUpdate.setTelefono("000112233");
		
		dbManager.getEntityManager().getTransaction().commit();
		dbManager.close();
		
		Assert.assertEquals(true, personaUpdate.getId()>0);
		Assert.assertEquals("Juanito", personaUpdate.getNombre());
		Assert.assertEquals("Gaston", personaUpdate.getApellido());
		Assert.assertEquals("000112233", personaUpdate.getTelefono());
		
		Assert.assertEquals(2,person2.getMascota().size());
		
	}
	

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