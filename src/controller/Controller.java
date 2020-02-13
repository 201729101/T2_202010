package controller;

import java.util.Scanner;

import model.data_structures.Cola;
import model.logic.Infraccion;
import model.logic.Modelo;
import view.View;

public class Controller {

	/* Instancia del Modelo*/
	private Modelo modelo;

	/* Instancia de la Vista*/
	private View view;

	/**
	 * Crear la vista y el modelo del proyecto
	 * @param capacidad tamaNo inicial del arreglo
	 */
	public Controller ()
	{
		view = new View();
		modelo = new Modelo();
	}

	/**
	 * Corre el sistema mediante la consola 
	 */
	public void run() 
	{
		Scanner lector = new Scanner(System.in);
		boolean fin = false;

		while( !fin ){
			view.printMenu();

			int option = lector.nextInt();
			switch(option){
			case 1:
				modelo = new Modelo(); 
				modelo.leerDatos("comparendos_dei_2018.geojson");
				view.printMessage("Comparendos cargados");
				view.printMessage("Numero actual de elementos: " + modelo.darTamanoCola() + "\n---------");
				view.printMessage("Primer comparendo en la pila");
				view.printComparendo((Infraccion) modelo.darPila().darPrimero());
				view.printMessage("Primer comparendo en la cola");
				view.printComparendo((Infraccion) modelo.darCola().darPrimero());
				break;

			case 2:
				view.printCola(modelo.clusterComparendos());
				view.printMessage("Numero actual de elementos " + modelo.darTamanoCola() + "\n---------");						
				break;

			case 3:
				view.printMessage("--------- \nDar numero de comparendos a buscar: ");
				int pId = lector.nextInt();
				view.printMessage("--------- \nDar código de infracción a buscar: ");
				String pInf = lector.next();
				Cola resp = modelo.darUltimos(pId , pInf);
				if ( resp != null)
				{
					view.printCola(resp);
				}
				else if(resp.darTamano()==0)
				{
					view.printMessage("No hay comparendos con este codigo");
				}
				else
				{
					view.printMessage("Datos NO encontrados");
				}
				view.printMessage("Numero actual de elementos " + modelo.darTamanoCola() + "\n---------");						
				break;

			case 4: 
				view.printMessage("--------- \n Hasta pronto !! \n---------"); 
				lector.close();
				fin = true;
				break;	

			default: 
				view.printMessage("--------- \n Opcion Invalida !! \n---------");
				break;
			}
		}

	}	

}
