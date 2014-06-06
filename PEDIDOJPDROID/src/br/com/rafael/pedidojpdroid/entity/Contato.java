package br.com.rafael.pedidojpdroid.entity;

import java.io.Serializable;

import br.com.rafael.jpdroid.annotations.Column;
import br.com.rafael.jpdroid.annotations.Entity;
import br.com.rafael.jpdroid.annotations.ForeignKey;
import br.com.rafael.jpdroid.annotations.PrimaryKey;

@Entity
public class Contato  implements Serializable{
	
  private static final long serialVersionUID = 1L;
  
	@PrimaryKey
	@Column(name="_id")
	private long _id;
	
	@ForeignKey(joinEntity="Pessoa",joinPrimaryKey="_id",deleteCascade=true)
	@Column(name="idPessoa")
	private long idPessoa;

	@Column(name="tipo")
	private String tipo;
	
	@Column(name="contato")
	private String contato;

	public long get_id() {
		return _id;
	}

	public void set_id(long _id) {
		this._id = _id;
	}

	public long getIdPessoa() {
		return idPessoa;
	}

	public void setIdPessoa(long idPessoa) {
		this.idPessoa = idPessoa;
	}

	public String getContato() {
		return contato;
	}

	public void setContato(String contato) {
		this.contato = contato;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}
