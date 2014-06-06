package br.com.rafael.jpdroid.interfaces;

public interface ITransaction {
	/**
	 * Inicia a transa��o
	 */
	void begin();

	/**
	 * Efetiva altera��es ocorridas durante a transa��o.
	 */
	void commit();
	/**
	 * Finaliza a transa��o.
	 */
	void end();
}
