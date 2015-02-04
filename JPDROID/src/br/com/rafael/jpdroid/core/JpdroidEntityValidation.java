package br.com.rafael.jpdroid.core;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import br.com.rafael.jpdroid.annotations.Entity;
import br.com.rafael.jpdroid.annotations.ForeignKey;
import br.com.rafael.jpdroid.annotations.PrimaryKey;
import br.com.rafael.jpdroid.annotations.RelationClass;
import br.com.rafael.jpdroid.enums.RelationType;
import br.com.rafael.jpdroid.exceptions.JpdroidException;

/**
 * Classe respons�vel por validar as entidades.
 * 
 * @author Rafael Centenaro
 *
 */
public class JpdroidEntityValidation {

	private List<Class<?>> entidades = new ArrayList<Class<?>>();

	public List<Class<?>> getEntidades() {
		return entidades;
	}

	public JpdroidEntityValidation() {

	}
	public JpdroidEntityValidation addClasses(Class<?>... classes) throws Exception {
		for (Class<?> clazz : classes) {
			addClass(clazz);
		}
		return this;
	}
	public JpdroidEntityValidation addClass(Class<?> entityClass) throws JpdroidException {
		int pkCount = 0;
		String columnName;
		Entity entity = entityClass.getAnnotation(Entity.class);
		if (entity == null) {
			throw new JpdroidException("A classe " + entityClass
					+ " n�o � uma entidade v�lida, pois n�o possui a anota��o @Entity");
		}
		Field[] fields = entityClass.getDeclaredFields();
		for (Field field : fields) {
			Annotation[] annotations = field.getAnnotations();
			for (Annotation annotation : annotations) {
				if (annotation.annotationType() == PrimaryKey.class) {
					if(!field.getType().getSimpleName().equalsIgnoreCase("long")){
						throw new JpdroidException("O tipo para chave prim�ria deve ser long.");
					}
					columnName = field.getName();
					if((columnName.equals("") && !field.getName().equals("_id")) || (!columnName.equals("") && !columnName.equals("_id"))){
						throw new JpdroidException("As tabelas no SQLite devem possuir um atributo '_id'  como chave prim�ria.");
					}
					pkCount++;
				}
				if (annotation.annotationType() == RelationClass.class) {
					if( !((RelationClass)annotation).relationType().equals(RelationType.ManyToMany) && ((RelationClass)annotation).joinColumn().equalsIgnoreCase("")){
						throw new JpdroidException("O par�metro joinColumn da anota��o @RelationClass n�o pode ser vazio.");
					}
					if(((RelationClass)annotation).relationType().equals(RelationType.ManyToMany) && ((RelationClass)annotation).joinTable().equalsIgnoreCase("")){
						throw new JpdroidException("O par�metro joinTable da anota��o @RelationClass n�o pode ser vazio.");
					}
				}
				if (annotation.annotationType() == ForeignKey.class) {
					if(((ForeignKey)annotation).joinEntity() == null){
						throw new JpdroidException("O par�metro joinEntity da anota��o @ForeignKey n�o pode ser nula.");
					}
					if(((ForeignKey)annotation).joinEntity().getAnnotation(Entity.class) == null){
						throw new JpdroidException("O par�metro joinEntity possui relacionamento inv�lido, pois a classe "+((ForeignKey)annotation).joinEntity().getSimpleName()+" n�o possui anota��o @Entity.");
					}

					if(((ForeignKey)annotation).joinPrimaryKey().equalsIgnoreCase("")){
						throw new JpdroidException("O par�metro joinPrimaryKey da anota��o @ForeignKey n�o pode ser vazio.");
					}
				}
			}
		}
		if (pkCount == 0) {
			throw new JpdroidException("A classe " + entityClass
					+ " n�o � uma entidade v�lida, pois n�o possui atributo vinculado com a anota��o @PrimaryKey.");
		}
		if (pkCount > 1) {
			throw new JpdroidException("A classe " + entityClass
					+ " n�o � uma entidade v�lida, pois possui mais de um atributo vinculado com a anota��o @PrimaryKey.");
		}

		this.entidades.add(entityClass);
		return this;
	}


}
