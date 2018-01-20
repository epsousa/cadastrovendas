package com.br.cadastrovendas.bll;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.br.cadastrovendas.enums.TipoProduto;
import com.br.cadastrovendas.model.Produto;

public class GerenciaVendas {

	// lista de produtos a serem gerenciados
	private List<Produto> totalProdutos = new ArrayList<Produto>();

	// id dos produtos.
	int id = 0;

	/**
	 * Adiciona uma produto com autoincremento do ID.
	 * 
	 * @param produto
	 */
	public void adicionaProduto(Produto produto) {
		produto.setId(id);
		if (produto != null) {
			totalProdutos.add(produto);
		}
		id++;
	}

	/**
	 * Remove um produto atraves do id fornecido.
	 * 
	 * @param id
	 */
	public Produto removeProduto(int id) {
		Produto produto = obtemProduto(id);
		totalProdutos.remove(totalProdutos.indexOf(produto));
		return produto;
	}

	/**
	 * Retorna um produto atraves do id fornecido.
	 * 
	 * @param id
	 * @return
	 */
	public Produto obtemProduto(int id) {
		List<Produto> produto = totalProdutos.stream().filter(x -> x.getId() == id).collect(Collectors.toList());
		if (!produto.isEmpty()) {
			return produto.get(0);
		} else {
			return null;
		}
	}

	/**
	 * Retorna uma lista de produtos filtrado por um tipo de produto.
	 * 
	 * @param tpProduto
	 * @return
	 */
	public List<Produto> listaProdutosPorTipo(TipoProduto tpProduto) {
		return totalProdutos.stream().filter(x -> x.getTipoProduto() == tpProduto).collect(Collectors.toList());
	}

	/**
	 * Atualiza um produto através do id e dos dados fornecidos.
	 * 
	 * @param produto
	 * @return
	 */
	public Produto atualizaProduto(Produto produto) {
		removeProduto(produto.getId());
		totalProdutos.add(produto);
		return produto;
	}

	/**
	 * Retorna a lista de produtos ordenados por id.
	 * 
	 * @return
	 */
	public List<Produto> obtemProduto() {
		Collections.sort(totalProdutos, new Comparator<Produto>() {
			@Override
			public int compare(Produto e1, Produto e2) {
				if (e1.getId() < e2.getId()) {
					return 1;
				} else {
					return -1;
				}
			}
		});
		return totalProdutos;
	}

	/**
	 * Retorna o valor total por um tipo de produto.
	 * 
	 * @param tipoProduto
	 * @return
	 */
	public Double retornaProdutoPorTipo(TipoProduto tipoProduto) {
		Double total = 0D;
		for (Produto prod : totalProdutos) {
			if (prod.getTipoProduto() == tipoProduto) {
				total += prod.getValor();
			}
		}
		return total;
	}

	/**
	 * Retorna a soma dos produtos na lista.
	 * 
	 * @return
	 */
	public Double retornaTotalProdutos() {
		Double total = 0D;
		for (Produto prod : totalProdutos) {
			total += prod.getValor();
		}
		return total;
	}

	/**
	 * Busca por uma string entre os nomes dos produtos dentro da lista.
	 * 
	 * @param nome
	 * @return
	 */
	public boolean validaSeNomeExisteNaLista(String nome) {
		return !this.totalProdutos.stream().filter(x -> x.getNome().equalsIgnoreCase(nome)).collect(Collectors.toList())
				.isEmpty();
	}

}
