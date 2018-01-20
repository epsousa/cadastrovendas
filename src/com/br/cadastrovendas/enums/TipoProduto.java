package com.br.cadastrovendas.enums;

public enum TipoProduto {

	ELETRONICOS(1, "Eletronicos"),
	MOVEIS(2, "Moveis"),
	ALIMENTOS(3, "Alimentos"),
	SERVICOS(4, "Serviços"),
	OUTROS(5, "Outros");
	
	public int key;
	public String value;
	
	TipoProduto(int key, String value){
		this.key = key;
		this.value = value;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
