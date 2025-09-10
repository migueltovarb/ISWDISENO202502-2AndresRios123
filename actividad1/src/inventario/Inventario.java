package inventario;

import java.util.Scanner;
import java.util.Arrays;

public class Inventario {

	public static void main(String[] args) {
		final int MAX_PRODUCTOS = 5;
		String[] nombreProducto = new String[MAX_PRODUCTOS];
		int[] cantidadesDisponibles = new int[MAX_PRODUCTOS];
		Scanner myScanner = new Scanner(System.in);
		
		for(int i = 0; i < MAX_PRODUCTOS; i++) {
			System.out.println("Ingrese el nombre del producto " + (i+1));
			String nombre = myScanner.next();
			nombreProducto[i] = nombre;
			System.out.println("Ingrese la cantidad disponible del producto.");
			int cantidad = myScanner.nextInt();
			cantidadesDisponibles[i] = cantidad;
			System.out.println("El producto se ha agregado correctamente");
		}
		
		System.out.println(Arrays.toString(nombreProducto));
		
		int option = 0;
		
		while(option != 5){
			
			System.out.println("Seleccione alguna de las siguientes opciones:");
			System.out.println("1. Mostrar todos los productos y sus existencias");
			System.out.println("2. Buscar un producto por nombre");
			System.out.println("3. Actualizar el inventario");
			System.out.println("4. Productos con cantidad menor a 10");
			System.out.println("5. Salir");
			
			int optionSelected = myScanner.nextInt();
			
			switch(optionSelected) {
				case 1:
					for(int i = 0; i<MAX_PRODUCTOS; i++) {
						System.out.println(nombreProducto[i] + "   Cantidad disponible: " +cantidadesDisponibles[i]);
					}
				break;
				case 2:
					System.out.println("Ingrese el nombre del producto");
					String nomProducto = myScanner.next();
					boolean encontrado = false;
					int u = 0;
					int producto = -1;
					
					while(u < MAX_PRODUCTOS) {
						if (nomProducto.equalsIgnoreCase(nombreProducto[u])){
							encontrado = true;
							producto = u;
							break;
						}
						u++;
					}
					
					if (encontrado) {
						System.out.println(nombreProducto[producto] + "   Cantidad disponible: " +cantidadesDisponibles[producto]);
					}else {
						System.out.println("No se ha encontrado el producto");
					}
				break;
				case 3:
					for(int i = 0; i<MAX_PRODUCTOS; i++) {
						System.out.println(i+ " " +nombreProducto[i] + "   Cantidad disponible: " +cantidadesDisponibles[i]);
					}
					System.out.println("Seleccione el número del producto que desea Actualizar");
					int numProducto = myScanner.nextInt();
					System.out.println("Usted a seleccionado el producto " + nombreProducto[numProducto] + " el cual tiene " + cantidadesDisponibles[numProducto] + " unidades disponibles.");
					System.out.println("Por favor ingrese la nueva cantidad disponible");
					int cantidadNueva = myScanner.nextInt();
					cantidadesDisponibles[numProducto] = cantidadNueva;
					System.out.println("Ahora el producto " + nombreProducto[numProducto] + " tiene " + cantidadesDisponibles[numProducto] + " unidades disponibles.");
					
				break;
				
				case 4:
					int i = 0;
					int cantidadProductos = 0;
					while(i<MAX_PRODUCTOS) {
						if(cantidadesDisponibles[i]<10) {
							System.out.println("El producto " + nombreProducto[i] + " cuenta solo con " + cantidadesDisponibles[i] + " unidades disponibles.");
							cantidadProductos++;
						}
						i++;
					}
					if(cantidadProductos == 0) {
						System.out.println("No hay productos can cantidad disponible baja");
					}
				break;
				case 5:
					System.out.println("Programa finalizado!");
				break;
			}
			
			
		}
		
	}
	
}
