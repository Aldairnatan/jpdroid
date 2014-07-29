package br.com.rafael.jpdroid.annotations;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import br.com.rafael.jpdroid.enums.Order;

/**
 * Define ordena��o pelo atributo configurado.
 * 
 * @author Rafael Centenaro
 *
 */
@Target({java.lang.annotation.ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DefaultOrder
{
	Order order() default Order.asc;
}