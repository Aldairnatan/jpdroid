package br.com.rafael.jpdroid.converters;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import android.database.Cursor;
import android.util.Log;
import br.com.rafael.jpdroid.annotations.Entity;
import br.com.rafael.jpdroid.annotations.RelationClass;

/**
 * Classe responsável pela conversão objetos para o formato CSV.
 * 
 * @author Rafael Centenaro
 */
public class JpdroidCsvConverter {

	private static StringBuilder arquivo = null;

	public static String toCsv(Object entity) throws Exception {
		arquivo = new StringBuilder();
		return getCsv(entity);
	}

	public static String toCsv(Cursor cursor) throws Exception {
		arquivo = new StringBuilder();
		return getCsv(cursor);
	}

	public static String getCsv(Object entity) throws Exception {
		Object[] element = null;
		String linha = "";
		String cabecalho = "";
		arquivo.append(cabecalho);

		if (entity instanceof List) {
			element = ((List<?>) entity).toArray(new Object[((List<?>) entity).size()]);

		} else {
			element = new Object[1];
			element[0] = entity;
		}

		for (Object item : element) {
			linha = "";
			cabecalho = "";

			Class<?> classe = item.getClass();
			Field[] declaredFields = classe.getDeclaredFields();
			Collections.reverse(Arrays.asList(declaredFields));
			for (Field field : declaredFields) {
				field.setAccessible(true);
				Object child = field.get(item);
				if (child != null) {
					if (!(child instanceof List || child.getClass().getAnnotation(Entity.class) != null)) {
						if (cabecalho.length() == 0) {
							cabecalho = field.getName();
						} else {
							cabecalho += ";" + field.getName();
						}
						if (linha.length() == 0) {
							linha = child.toString();
						} else {
							linha += ";" + child.toString();
						}
					}
				} else {
					if (field.getAnnotation(RelationClass.class) == null) {
						if (cabecalho.length() == 0) {
							cabecalho = field.getName();
						} else {
							cabecalho += ";" + field.getName();
						}
						if (linha.length() == 0) {
							linha = "null";
						} else {
							linha += ";null";
						}
					}
				}
			}
			arquivo.append("\n");
			arquivo.append(linha);
		}

		arquivo.insert(0, cabecalho);
		return arquivo.toString();
	}

	public static String getCsv(Cursor cursor) {
		String linha = "";
		String cabecalho = "";
		arquivo.append(cabecalho);

		cursor.moveToFirst();
		while (cursor.isAfterLast() == false) {
			linha = "";
			cabecalho = "";
			int totalColumn = cursor.getColumnCount();

			for (int i = 0; i < totalColumn; i++) {
				if (cursor.getColumnName(i) != null) {
					if (cabecalho.length() == 0) {
						cabecalho = cursor.getColumnName(i);
					} else {
						cabecalho += ";" + cursor.getColumnName(i);
					}
					try {

						if (cursor.getString(i) != null) {
							if (linha.length() == 0) {
								linha = cursor.getString(i);
							} else {
								linha += ";" + cursor.getString(i);
							}
						} else {
							if (linha.length() == 0) {
								linha = "null";
							} else {
								linha += ";null";
							}
						}
					} catch (Exception e) {
						Log.d("TAG_NAME", e.getMessage());
					}
				}

			}
			arquivo.append("\n");
			arquivo.append(linha);
			cursor.moveToNext();
		}

		arquivo.insert(0, cabecalho);
		return arquivo.toString();
	}

}
