package me.felnstaren.starlight.engine.util;

public class ArrayUtil {

	public static Object[] insert(Object[] array, Object object, int pos) {
		Object[] added = new Object[array.length + 1];
		
		for(int i = 0; i < array.length; i++) {
			if(i == pos) 
				added[i] = object;
			else if(i > pos)
				added[i] = array[i - 1];
			else
				added[i] = array[i];
		}
			
		return added;
	}
	
}
