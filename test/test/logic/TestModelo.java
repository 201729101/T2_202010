package test.logic;

import static org.junit.Assert.*;

import model.data_structures.Cola;
import model.logic.Infraccion;
import model.logic.Modelo;

import org.junit.Before;
import org.junit.Test;

public class TestModelo {

	private Modelo modelo;

	@Before
	public void setUp1() 
	{
		modelo= new Modelo();
	}

	public void setUp2() 
	{
		modelo.leerDatos("comparendos_dei_2018_small.geojson");
	}

	public void setUp3()
	{
		try
		{
			modelo.darCola().agregar(new Infraccion(0, null, null, null, null, null, null, null, 0, 0));
		}
		catch(Exception e)
		{
			fail("No debería generar excepción");
		}
	}

	@Test
	public void testModelo() {
		assertTrue(modelo!=null);
		assertEquals(0, modelo.darTamanoCola());  // Modelo con 0 elementos presentes.
	}

	@Test
	public void testDarTamanoCola() {
		setUp2();
		assertEquals(20,modelo.darTamanoCola());
		setUp3();
		assertEquals(21,modelo.darTamanoCola());
	}

	@Test
	public void testClusterComparendos() {
		setUp2();
		Cola r = modelo.clusterComparendos();
		Infraccion i = (Infraccion) r.darPrimero();
		assertEquals("C02",i.getInfr());
	}
	
	@Test
	public void testDarUltimos() {
		setUp2();
		Cola r = modelo.darUltimos(10, "C02");
		Infraccion i = (Infraccion) r.darPrimero();
		assertEquals(9,r.darTamano());
		assertEquals(467527,i.getId());
		assertEquals(0,modelo.darPila().darTamano());
	}

}
