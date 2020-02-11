package view;

import model.data_structures.Cola;
import model.data_structures.Pila;
import model.logic.Infraccion;
import model.logic.Modelo;

public class View 
{
	    /**
	     * Metodo constructor
	     */
	    public View()
	    {
	    	
	    }
	    
	    /**
	     *Imprime el menú 
	     */
		public void printMenu()
		{
			System.out.println("1. Cargar lista de comparendos");
			System.out.println("2. Dar mayor cluster");
			System.out.println("3. Dar últimos N comparendos");
			System.out.println("4. Exit");
			System.out.println("Dar el numero de opcion a resolver, luego oprimir tecla Return: (e.g., 1):");
		}

		/**
		 * Imprime un mensaje recibido por parámetro
		 * @param mensaje mensaje a imprimir
		 */
		public void printMessage(String mensaje) {

			System.out.println(mensaje);
		}
		
		public void printComparendo(Infraccion inf)
		{
			System.out.println("[");
			System.out.println("ID: "+inf.getId());
			System.out.println("Fecha: "+inf.getFecha());
			System.out.println("Medio de detección: " + inf.getMedio());
			System.out.println("Clase de vehículo: "+inf.getClase());
			System.out.println("Tipo de servicio: "+inf.getTipo());
			System.out.println("Infracción: "+inf.getInfr());
			System.out.println("Descripción: "+inf.getDesc());
			System.out.println("Localidad: "+inf.getLocalidad());
			System.out.println("Coordenadas: "+inf.getLatitud()+" , "+inf.getLongitud());
			System.out.println("]");
		}
		
		/**
		 * Imprime todo un modelo recibido por parámetro
		 * @param modelo Modelo a imprimir
		 */
		public void printCola(Cola cola)
		{
			System.out.println("Comparendos buscados: {");
			for(int i=cola.getP() ; i<cola.darTamano() ; i++)
			{
				Infraccion inf = (Infraccion) cola.getElementos()[i];
				printComparendo(inf);
			}
			System.out.println("}");
		}
}
