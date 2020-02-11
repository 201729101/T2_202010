package model.logic;

import java.util.Date;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import model.data_structures.Cola;
import model.data_structures.Pila;
/**
 * Definicion del modelo del mundo
 *
 */
public class Modelo
{
	/**
	 * Estrutura de datos que tendrá los comparendos
	 */
	private Pila pila;

	private Cola cola;

	/**
	 * Constructor
	 */
	public Modelo ()
	{
		pila = new Pila();
		cola = new Cola();
	}

	/**
	 * Inicia la lectura del archivo JSON y rellena la lista
	 * @param path, ruta del archivo a leer
	 */
	public void leerDatos(String path)
	{
		Gson gson = new Gson();
		JsonReader reader;

		try 
		{
			readJsonStream(new FileInputStream("./data/"+path));
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Lee el archivo JSON
	 * @param in InputStream mediante el cual se hace la lectura, in!=null
	 * @return lista rellenada con los datos
	 * @throws IOException si no es posible leer el archivo 
	 */
	public void readJsonStream(InputStream in) throws IOException 
	{
		JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
		try 
		{
			readGeneral(reader);
		} 
		finally 
		{
			reader.close();
		}
	}

	/**
	 * Lee el mensaje general en el archivo
	 * @param reader JsonReader que debe leer
	 * @return lista con los comparendos
	 * @throws IOException si no es posible leer el archivo
	 */
	public void readGeneral(JsonReader reader) throws IOException 
	{
		String type = null;
		String n = null;
		String crs = null;


		reader.beginObject();
		while (reader.hasNext()) 
		{
			String name = reader.nextName();
			if (name.equals("type")) 
			{
				type = reader.nextString();
			} 
			else if (name.equals("name")) 
			{
				n = reader.nextString();
			} 
			else if (name.equals("crs"))
			{
				String t = null;

				reader.beginObject();
				while (reader.hasNext())
				{
					String read = reader.nextName();
					if(read.equals("type"))
					{
						t = reader.nextString();
					}
					else if(read.equals("properties"))
					{
						String p = null;

						reader.beginObject();
						while(reader.hasNext())
						{
							String read2 = reader.nextName();
							if(read2.equals("name"))
							{
								p = reader.nextString();
							}
							else
							{
								reader.skipValue();
							}
						}
						reader.endObject();
					}
					else
					{
						reader.skipValue();
					}
				}
				reader.endObject();
			}
			else if( name.equals("features"))
			{
				readMessagesArray(reader);
			}
			else
			{
				reader.skipValue();
			}
		}
		reader.endObject();
	}

	/**
	 * Lee el arreglo de mensajes en la sección "features" del archivo de comparendos
	 * @param reader JsonReeader a leer
	 * @return lista con los comparendos en el archivo
	 * @throws IOException si no es posible leer la lista
	 */
	public void readMessagesArray(JsonReader reader) throws IOException 
	{
		//		Pila messages = new Pila();

		reader.beginArray();
		while (reader.hasNext()) 
		{
			try
			{
				Infraccion inf = readFeatures(reader);
				pila.push(inf);
				cola.agregar(inf);
			}
			catch(Exception e)
			{
				System.out.println(e.getMessage());
			}
		}
		reader.endArray();
		//		return messages;
	}


	/**
	 * Lee las infracciones de la sección features en el archivo json de los comparendos
	 * @param reader JsonReader a leer
	 * @return Infraccion leida del archivo
	 * @throws IOException si no se puede leer el archivo
	 */
	public Infraccion readFeatures(JsonReader reader) throws IOException 
	{
		String type = null;
		String[] datos = null;
		ArrayList<Double> geo = null;

		reader.beginObject();
		while (reader.hasNext()) 
		{
			String name = reader.nextName();
			if (name.equals("type")) 
			{
				type = reader.nextString();
			} 
			else if (name.equals("properties")) 
			{
				datos = readProperties(reader);
			} 
			else if (name.equals("geometry"))
			{
				geo = readGeometry(reader);
			}
			else
			{
				reader.skipValue();
			}
		}
		reader.endObject();
		try
		{
			Infraccion retorno = new Infraccion(Integer.parseInt(datos[0]),datos[1],datos[2],datos[3],datos[4],datos[5],datos[6],datos[7],geo.get(0),geo.get(1));
			return retorno;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Lee los datos de la sección properties en el archivo json de los comparendos
	 * @param reader JsonReader a leer
	 * @return arreglo de strings con los datos leidos
	 * @throws IOException si no se pudo leer el archivo
	 */
	public String[] readProperties(JsonReader reader) throws IOException 
	{
		String[] data = new String[8];
		reader.beginObject();
		while (reader.hasNext()) 
		{
			String name = reader.nextName();
			if (name.equals("OBJECTID")) 
			{
				data[0] = reader.nextString();
			} 
			else if (name.equals("FECHA_HORA")) 
			{
				data[1] = reader.nextString();
			} 
			else if (name.equals("MEDIO_DETE")) 
			{
				data[2] = reader.nextString();
			} 
			else if (name.equals("CLASE_VEHI")) 
			{
				data[3] = reader.nextString();
			} 
			else if (name.equals("TIPO_SERVI")) 
			{
				data[4] = reader.nextString();
			} 
			else if (name.equals("INFRACCION")) 
			{
				data[5] = reader.nextString();
			} 
			else if (name.equals("DES_INFRAC")) 
			{
				data[6] = reader.nextString();
			}
			else if (name.equals("LOCALIDAD"))
			{
				data[7] = reader.nextString();
			}
			else
			{
				reader.skipValue();
			}
		}
		reader.endObject();
		return data;
	}

	/**
	 * Lee los datos de la sección geometry del archivo json con los comparendos
	 * @param reader JsonReader a leer
	 * @return ArrayList con los doubles de la longitud y latitud
	 * @throws IOException si no se pudo leer el archivo
	 */
	public ArrayList<Double> readGeometry(JsonReader reader) throws IOException 
	{
		ArrayList<Double> data = new ArrayList();
		String type = null;

		reader.beginObject();
		while (reader.hasNext()) 
		{
			String name = reader.nextName();
			if (name.equals("type"))
			{
				type = reader.nextString();
			}
			else if (name.equals("coordinates")) 
			{
				data = (ArrayList<Double>) readDoublesArray(reader);
			}
			else
			{
				reader.skipValue();
			}
		}
		reader.endObject();
		return data;
	}

	/**
	 * Lee el arreglo de doubles en el archivo json con los comparendos en la sección geometry
	 * @param reader JsonReader a leer
	 * @return Lista con los doubles leidos
	 * @throws IOException si no se pudo leer los datos
	 */
	public List<Double> readDoublesArray(JsonReader reader) throws IOException 
	{
		List<Double> doubles = new ArrayList<Double>();

		reader.beginArray();
		while (reader.hasNext()) 
		{
			doubles.add(reader.nextDouble());
		}
		reader.endArray();
		return doubles;
	}

//	/**
//	 * Busca una infracción en la lista con un ID recibido por parámetro
//	 * @param pId ID de la infracción a buscar
//	 * @return Infracción buscada, null si no es encontrada
//	 */
//	public Infraccion buscarPila(int pId)
//	{
//		Infraccion buscada = null;
//		for(int i=0 ; i<pila.darTamano() ; i++)
//		{
//			Infraccion actual = (Infraccion) pila.getElementos()[i];
//
//			if(actual.getId()==pId)
//			{
//				return actual;
//			}
//		}
//		return null;
//	}
//
//	/**
//	 * Elimina y retorna una infracción con un id recibido por parámetro
//	 * @param pId ID de la infraccion a eliminar
//	 * @return infraccion eliminada, null si no está la infraccion
//	 */
//	public Infraccion eliminarPila()
//	{
//		try
//		{
//			Infraccion inf = (Infraccion) pila.pop();
//			return inf;
//		}
//		catch(Exception e)
//		{
//			return null;
//		}
//	}
//
	/**
	 * Retona el tamaño de la lista
	 * @return tamaño de la lista
	 */
	public int darTamanoCola()
	{
		return cola.darTamano();
	}
//
//	/**
//	 * Agrega una infracción recibida por parámetro al final de la lista
//	 * @param pInf infracción a agregar
//	 */
//	public void agregarPila(Infraccion pInf)
//	{
//		try
//		{
//			pila.push(pInf);
//		}
//		catch(Exception e)
//		{
//			System.out.println(e.getMessage());
//		}
//	}
//
//	/**
//	 * Agrega una infracción rebida por parámetro a la cola
//	 * @param pInf nfracción a agregar
//	 */
//	public void agregarCola(Infraccion pInf)
//	{
//		try
//		{
//			cola.agregar(pInf);
//		}
//		catch(Exception e)
//		{
//			System.out.println(e.getMessage());
//		}
//	}

	/**
	 * retorna la lista encadenada
	 * @return lista encadenada
	 */
	public Pila darPila() {
		return pila;
	}

	public Cola darCola()
	{
		return cola;
	}

	public Cola clusterComparendos()
	{
		if(cola.esVacia())
		{
			return null;
		}

		Cola agregar = new Cola();
		Cola anterior = new Cola();
		Infraccion primero = (Infraccion) cola.darPrimero();

		try
		{
			agregar.agregar(primero);
			String actual = primero.getInfr();
			String masRep = primero.getInfr();
			for(int i = cola.getP()+1 ; i<cola.darTamano() ; i++)
			{
				Infraccion inf = (Infraccion) cola.getElementos()[i];
				if(!inf.getInfr().equals(actual))
				{
					actual = inf.getInfr();
					if(anterior.darTamano()<agregar.darTamano())
					{
						anterior = agregar;
					}
					agregar = new Cola();
					agregar.agregar(inf);
				}
				else
				{
					agregar.agregar(inf);
				}
			}
			return agregar.darTamano()>anterior.darTamano()? agregar : anterior;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}			
	}

	public Cola darUltimos(int pN, String pInfr)
	{
		Cola retorno = new Cola();
		int contador = 0;
		try
		{
			int i = pila.darTamano();
			while(pila.darTamano()!= 0 && contador<pN)
			{
				Infraccion inf = (Infraccion) pila.pop();
				if(inf.getInfr().equals(pInfr))
				{
					retorno.agregar(inf);
					contador ++;
				}
			}
			
			return retorno;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
}
