package com.br.cadastrovendas.start;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import com.br.cadastrovendas.bll.GerenciaVendas;
import com.br.cadastrovendas.enums.TipoProduto;
import com.br.cadastrovendas.model.Produto;
import com.br.cadastrovendas.model.Responsavel;

public class StartCadastroProduto {
	// scanner para ler entrada do usuário
	static Scanner sc = new Scanner(System.in);
	// classe responsavel por gerenciar os produtos
	static GerenciaVendas gerenciadorDeVendas = new GerenciaVendas();
	// classe produto para gerenciar os dados do produto.
	static Produto produtoCadastro = new Produto();
	// classe Reponsavel para gerenciar os dados do responsavel.
	static Responsavel resp = new Responsavel();

	// classe principal.
	public static void main(String[] args) {

		retornaLinha();
		System.out.println("Seja bem vindo para o gerenciador de vendas:");
		retornaLinha();

		System.out.println("Por favor, insira seu nome:");
		System.out.print("Digite sua resposta: ");
		String nome = sc.nextLine();
		resp.setNome(nome);
		int idade = -1;
		System.out.println("Por favor, Digite sua idade:");
		while (idade == -1) {
			try {
				System.out.print("Digite sua resposta: ");
				idade = sc.nextInt();
				if (idade <= 0) {
					System.out.println("Digite uma idade valida.");
					idade = -1;
				} else {
					resp.setIdade(idade);
				}
			} catch (Exception ex) {
				System.out.println("Digite uma idade valida.");
				sc.next();
				idade = -1;
			}
		}
		int resp = 1;

		// enquanto a resposta enviada pela função menu() for diferente de zero
		// o sistema fica em funcionamento.
		while (resp != 0) {

			resp = menu();

			switch (resp) {
			case 1:
				adicionaProduto();
				break;
			case 2:
				alterarProduto();
				break;
			case 3:
				deletarProduto();
				break;
			case 4:
				selecionarProduto();
				break;
			case 0:				
				salvarArquivoDeProdutos();
				System.out.println("Obrigado por usar nosso sistema! :-)");
				break;
			}

		}

	}

	/**
	 * Grava os produtos inseridos pelo usuário em um arquivo txt com o nome
	 * gerado atraves da data e hora de execução na mesma pasta de execução do
	 * sistema.
	 */
	@SuppressWarnings("resource")
	private static void salvarArquivoDeProdutos() {
		System.out.println("Iniciando geração do relatório...");
		if (gerenciadorDeVendas.obtemProduto().isEmpty()) {
			System.out.println("Não há dados para geração de relatório");
			return;			
		}

		FileWriter writer = null;
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss_SSS");
			writer = new FileWriter("MinhasVendas" + LocalDateTime.now().format(formatter) + ".txt");
		} catch (IOException e) {
			System.out.println("Houve um erro ao gerar o arquivo, certifique-se que você tem permissão para executar este processo!");
			e.printStackTrace();
			return;
		}

		try {
			writer.write("Cadastro de vendas gerados por: " + resp.getNome() + ", cuja a idade é: " + resp.getIdade()
					+ "\n");
			writer.write("-------------------------------------------------------------------------------------\n");
		} catch (IOException e) {
			System.out.println("Houve um erro ao tentar inserir dados no arquivo, certifique-se que você tem permissão para executar este processo!");
			e.printStackTrace();
			return;
		}

		try {
			writer.write("DETALHE DOS PRODUTOS VENDIDOS:\n" + "-------------------------------------------------------------------------------------\n");
		} catch (IOException e2) {
			System.out.println("Houve um erro ao tentar inserir dados no arquivo, certifique-se que você tem permissão para executar este processo!");
			e2.printStackTrace();
		}
		
		for (Produto produtoVendido : gerenciadorDeVendas.obtemProduto()) {
			try {
				writer.write("Produto: " + produtoVendido.getNome() + " Valor: R$ " + produtoVendido.getValor()
						+ " Tipo do produto: " + produtoVendido.getTipoProduto().getValue() + "\n");
			} catch (IOException e) {
				System.out.println("Houve um erro ao tentar inserir dados no arquivo, certifique-se que você tem permissão para executar este processo!");
				e.printStackTrace();
				return;
			}
		}

		exibeValorPorTipoProduto(TipoProduto.ELETRONICOS, writer);
		exibeValorPorTipoProduto(TipoProduto.MOVEIS, writer);
		exibeValorPorTipoProduto(TipoProduto.ALIMENTOS, writer);
		exibeValorPorTipoProduto(TipoProduto.SERVICOS, writer);
		exibeValorPorTipoProduto(TipoProduto.OUTROS, writer);

		try {
			writer.write("Valor total de todos os Produtos: R$ " + gerenciadorDeVendas.retornaTotalProdutos() + "\n");
		} catch (IOException e1) {
			System.out.println("Houve um erro ao tentar inserir dados no arquivo, certifique-se que você tem permissão para executar este processo!");
			e1.printStackTrace();
		}

		try {
			writer.close();
		} catch (IOException e) {
			System.out.println("Houve um erro ao tentar inserir dados no arquivo, certifique-se que você tem permissão para executar este processo!");
			e.printStackTrace();
			return;
		}
		System.out.println("Relatório gerado com sucesso!");
	}

	public static void exibeValorPorTipoProduto(TipoProduto tipo, FileWriter writer) {
		try {
			writer.write("-------------------------------------------------------------------------------------\n");
		} catch (IOException e) {
			System.out.println("Houve um erro ao tentar inserir dados no arquivo, certifique-se que você tem permissão para executar este processo!");
			e.printStackTrace();
			return;
		}

		try {
			writer.write("Valor total dos produtos do tipo " + tipo.getValue() + " R$ "
					+ gerenciadorDeVendas.retornaProdutoPorTipo(tipo) + ".\n");
		} catch (IOException e1) {
			System.out.println("Houve um erro ao tentar inserir dados no arquivo, certifique-se que você tem permissão para executar este processo!");
			e1.printStackTrace();
		}

		try {
			writer.write("-------------------------------------------------------------------------------------\n");
		} catch (IOException e) {
			System.out.println("Houve um erro ao tentar inserir dados no arquivo, certifique-se que você tem permissão para executar este processo!");
			e.printStackTrace();
			return;
		}
	}

	/**
	 * Retorna o valor inteiro inserido pelo usuário sobre a decisão de
	 * atividade.
	 * 
	 * @return
	 */
	public static int menu() {
		int decisao = -1;
		retornaLinha();
		System.out.println("O que deseja executar agora?");
		retornaLinha();

		System.out.println("1 - Adicionar nova venda.");
		System.out.println("2 - Alterar uma venda.");
		System.out.println("3 - Deletar uma venda.");
		System.out.println("4 - Selecionar uma venda.");
		System.out.println("0 - Sair.");
		while (decisao == -1) {
			try {
				System.out.print("Digite sua resposta: ");
				decisao = sc.nextInt();
				if (decisao < 0 || decisao > 4) {
					decisao = -1;
					System.out.println("Insira um valor entre 0 e 4 inclusive");
				}
			} catch (Exception ex) {
				System.out.println("Insira um valor entre 0 e 4 inclusive");
				sc.next();
			}
		}
		return decisao;
	}

	/**
	 * Permite o usuário adicionar uma venda.
	 */
	public static void adicionaProduto() {
		retornaLinha();
		String dsProd = "";
		System.out.println("Por favor, adicione o nome do produto :");
		System.out.print("Digite sua resposta: ");
		while (dsProd.equals("")) {
			dsProd = sc.nextLine();
			if (gerenciadorDeVendas.validaSeNomeExisteNaLista(dsProd)) {
				System.out.println("Produto já cadastrada com o nome " + dsProd
						+ ", Por favor, digite um produto ainda não cadastrado.");
				dsProd = "";
			}
		}
		produtoCadastro.setNome(dsProd);
		retornaLinha();
		while (produtoCadastro.getValor() == null) {
			System.out.println("Por favor, adicione o valor do produto:");
			try {
				System.out.print("Digite sua resposta: ");
				double val = sc.nextDouble();
				produtoCadastro.setValor(val);
			} catch (Exception ex) {
				System.out.println("Adicione um valor numérico.");
				sc.next();
			}
		}

		while (produtoCadastro.getTipoProduto() == null) {
			System.out.println("Por favor, digite o numero do tipo do produto conforme abaixo:");
			System.out.println(TipoProduto.ELETRONICOS.getKey() + " - " + TipoProduto.ELETRONICOS.getValue());
			System.out.println(TipoProduto.MOVEIS.getKey() + " - " + TipoProduto.MOVEIS.getValue());
			System.out.println(TipoProduto.ALIMENTOS.getKey() + " - " + TipoProduto.ALIMENTOS.getValue());
			System.out.println(TipoProduto.SERVICOS.getKey() + " - " + TipoProduto.SERVICOS.getValue());
			System.out.println(TipoProduto.OUTROS.getKey() + " - " + TipoProduto.OUTROS.getValue());
			System.out.print("Digite sua resposta: ");
			int i = sc.nextInt();
			try {
				switch (i) {
				case 1:
					produtoCadastro.setTipoProduto(TipoProduto.ELETRONICOS);
					break;
				case 2:
					produtoCadastro.setTipoProduto(TipoProduto.MOVEIS);
					break;
				case 3:
					produtoCadastro.setTipoProduto(TipoProduto.ALIMENTOS);
					break;
				case 4:
					produtoCadastro.setTipoProduto(TipoProduto.SERVICOS);
					break;
				case 5:
					produtoCadastro.setTipoProduto(TipoProduto.OUTROS);
					break;
				default:
					break;
				}
			} catch (Exception ex) {

				sc.next();
			}
		}

		gerenciadorDeVendas.adicionaProduto(produtoCadastro);
		retornaLinha();
		produtoCadastro = new Produto();
		System.out.println("Sua lista de produtos vendidos: ");
		listaProdutos(-1);
	}

	/**
	 * Permite o usuário alterar um produto.
	 */
	private static void alterarProduto() {
		// verifica se os produtos estao vazias, se estiver, exibe mensagem e
		// encerra a execução da função.
		if (gerenciadorDeVendas.obtemProduto().isEmpty()) {
			System.out.println("Não há produtos para serem alterados.");
			return;
		}

		System.out.println("Insira o id do produto que deseja alterar conforme abaixo:");
		listaProdutos(-1);
		Integer v = null;

		// faz com que o usuário digite um valor inteiro dentre os existentes na
		// listagem de produtos.
		while (v == null) {
			try {
				System.out.print("Digite sua resposta: ");
				v = sc.nextInt();
			} catch (Exception ex) {
				System.out.println("Digite um valor inteiro dentre os id's mostrados abaixo");
				listaProdutos(-1);
			}
		}
		produtoCadastro.setId(v);
		System.out.println("Deseja alterar esse produto? " + gerenciadorDeVendas.obtemProduto(v) + " 1 - Sim, 0 - Não");
		try {
			if (sc.nextInt() == 1) {
				System.out.println("Digite um novo nome para esse produto");
				String dsProduto = "";
				while (dsProduto.equals("")) {
					System.out.print("Digite sua resposta: ");
					dsProduto = sc.nextLine();
					if (gerenciadorDeVendas.validaSeNomeExisteNaLista(dsProduto)) {
						System.out.println("Produto já cadastrado com o nome " + dsProduto
								+ ", Por favor, digite um nome ainda não cadastrado.");
						dsProduto = "";
					}
				}
				produtoCadastro.setNome(dsProduto);
				System.out.println("Digite um novo valor para esse produto");
				Double valor = null;
				while (valor == null) {
					try {
						System.out.print("Digite sua resposta: ");
						valor = sc.nextDouble();
					} catch (Exception ex) {
						sc.next();
						System.out.println("Digite um valor numérico.");
					}
				}
				produtoCadastro.setValor(valor);

				while (produtoCadastro.getTipoProduto() == null) {
					System.out.println("Por favor, digite o numero do tipo do produto conforme abaixo:");
					System.out.println(TipoProduto.ELETRONICOS.getKey() + " - " + TipoProduto.ELETRONICOS.getValue());
					System.out.println(TipoProduto.MOVEIS.getKey() + " - " + TipoProduto.MOVEIS.getValue());
					System.out.println(TipoProduto.ALIMENTOS.getKey() + " - " + TipoProduto.ALIMENTOS.getValue());
					System.out.println(TipoProduto.SERVICOS.getKey() + " - " + TipoProduto.SERVICOS.getValue());
					System.out.println(TipoProduto.OUTROS.getKey() + " - " + TipoProduto.OUTROS.getValue());
					System.out.print("Digite sua resposta: ");
					int i = sc.nextInt();
					try {
						switch (i) {
						case 1:
							produtoCadastro.setTipoProduto(TipoProduto.ELETRONICOS);
							break;
						case 2:
							produtoCadastro.setTipoProduto(TipoProduto.MOVEIS);
							break;
						case 3:
							produtoCadastro.setTipoProduto(TipoProduto.ALIMENTOS);
							break;
						case 4:
							produtoCadastro.setTipoProduto(TipoProduto.SERVICOS);
							break;
						case 5:
							produtoCadastro.setTipoProduto(TipoProduto.OUTROS);
							break;
						default:
							break;
						}
					} catch (Exception ex) {

						sc.next();
					}
				}

				gerenciadorDeVendas.atualizaProduto(produtoCadastro);
				produtoCadastro = new Produto();
				listaProdutos(-1);
			}
		} catch (Exception ex) {

		}
	}

	/**
	 * Permite deletar um produto através do id passado pelo usuário.
	 */
	private static void deletarProduto() {
		// verifica se as vendas estao vazias, se estiver, exibe mensagem e
		// encerra a execução da função.
		if (gerenciadorDeVendas.obtemProduto().isEmpty()) {
			System.out.println("Não há produtos para serem deletados.");
			return;
		}

		System.out.println("Insira o id do produto que deseja deletar conforme abaixo:");
		listaProdutos(-1);
		Integer v = null;

		// faz com que o usuário digite um valor inteiro.
		while (v == null) {
			try {
				System.out.print("Digite sua resposta: ");
				v = sc.nextInt();
				if (gerenciadorDeVendas.obtemProduto(v) == null) {
					v = null;
					System.out.println("Digite um valor inteiro dentre os id's mostrados abaixo");
					listaProdutos(-1);
					sc.next();
				}
			} catch (Exception ex) {
				System.out.println("Digite um valor inteiro dentre os id's mostrados abaixo");
				listaProdutos(-1);
				sc.next();
			}
		}
		System.out.println("Deseja deletar esse produto? " + gerenciadorDeVendas.obtemProduto(v) + " 1 - Sim, 0 - Não");
		// confirma se o usuário deseja realmente deletar o produto selecionado.
		try {
			System.out.print("Digite sua resposta: ");
			if (sc.nextInt() == 1) {
				gerenciadorDeVendas.removeProduto(v);
				listaProdutos(-1);
			}
		} catch (Exception ex) {
			sc.next();
		}
	}

	/**
	 * Permite fazer busca por id dentre a lista de produtos.
	 */
	private static void selecionarProduto() {
		// verifica se as produtos estao vazios, se estiver, exibe mensagem e
		// encerra a execução da função.
		if (gerenciadorDeVendas.obtemProduto().isEmpty()) {
			System.out.println("Não há produtos para serem listados.");
			return;
		}

		System.out.println("Insira o id de algum produto que deseja selecionar:");
		Integer v = null;

		// faz com que o usuário digite um valor inteiro.
		while (v == null) {
			try {
				System.out.print("Digite sua resposta: ");
				v = sc.nextInt();
			} catch (Exception ex) {
				System.out.println("Digite um valor inteiro.");
			}
		}

		// busca pelo id do produto.
		try {
			listaProdutos(v);
		} catch (Exception ex) {
			System.out.println("Id não encontrado");
		}
	}

	/**
	 * Se o valor de v for igual a -1, lista todos os produtos se não, lista
	 * apenas o correspondente ao valor de V
	 * 
	 * @param v
	 */
	private static void listaProdutos(int v) {
		retornaLinha();
		if (v == -1) {
			for (Produto produto : gerenciadorDeVendas.obtemProduto()) {
				System.out.println(produto);
			}
		} else {
			System.out.println(gerenciadorDeVendas.obtemProduto(v));
		}
		retornaLinha();
	}

	/**
	 * Escreve no console o texto:
	 * "-------------------------------------------------------------------------------------"
	 */
	public static void retornaLinha() {
		// Escreve no console o texto abaixo.
		System.out.println("-------------------------------------------------------------------------------------");
	}

}
