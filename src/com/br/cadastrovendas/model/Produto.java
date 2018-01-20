package com.br.cadastrovendas.model;

import com.br.cadastrovendas.enums.TipoProduto;

/**
 * Classe para representar produto.
 * @author evair
 *
 */
public class Produto {

	/**
	 * Identificador unico do produto.
	 */
	private int id;

	/**
	 * Nome do produto.
	 */
	private String nome;
	
	/**
	 * valor do produto.
	 */
	private Double valor;
	
	/**
	 * Tipo do produto cadastrado.
	 */
	private TipoProduto tipoProduto;		
	
	public TipoProduto getTipoProduto() {
		return tipoProduto;
	}

	public void setTipoProduto(TipoProduto tipoProduto) {
		this.tipoProduto = tipoProduto;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	@Override
	public String toString() {
		return "Produto= id:" + id + ", nome:" + nome + ", valor:" + valor + ", Tipo do produto:" + tipoProduto.getValue();
	}

	

	

}
