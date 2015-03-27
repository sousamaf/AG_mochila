package br.catolica.ia;

import java.util.Random;

public class AG {

	// Representa o números de genes de um indivíduo.
	private static int QTD_LIVROS = 200;
	// Representa a quantidade de indivíduos de uma população.
	private static int TAM_POPULACAO = 20; //50000;
	// Representa a quantidade de gerações criadas até o fim do ciclo de execução do algoritmo.
	private static int CICLO_POPULACIONAL = 50;
	// Representa a carga máxima da mochila.
	private static int CARGA_MAX = 20;
	
	// Gerador de valores aleatórios.
	private Random rand = new Random(); 
	
	// População principal.
	private int POP[][] = new int[TAM_POPULACAO][QTD_LIVROS];
	// População auxiliar.
	private int POP_AUX[][] = new int[TAM_POPULACAO][QTD_LIVROS];
	
	// Vetor para armazenar a aptidão da população corrente.
	private float APTIDAO[] = new float[TAM_POPULACAO]; 
	
	// Pesos dos livros
	private float PESOS[] = new float[QTD_LIVROS];
	
	// Taxa de crescimento da população auxiliar.
	private int taxaPopAux = -1;
	
	
	private void criaPopulacaoInicial() {
		int i, j;
		
		for(j = 0; j < TAM_POPULACAO; j++)
			for(i = 0; i < QTD_LIVROS; i++)
			{
				POP[j][i] = rand.nextInt(2);
			}
			
	}
	
	public void bigBang() {
		int i;
		
		int ciclo = 0;
		
		this.atribuiPesos();
		
		this.criaPopulacaoInicial();
		this.funcaoDeAvaliacao();
		this.exibeMelhorIndividuo();

		
		while(ciclo < CICLO_POPULACIONAL)
		{
			
			for(i = 0; i < 9; i++)
				this.cruzamentoSimples();
			

			this.mutacao();
			this.mutacao();
			
			this.substituicao();
			
			this.funcaoDeAvaliacao();
			this.exibeMelhorIndividuo();
			
			ciclo++;
		}
		
	}
	private void funcaoDeAvaliacao()
	{ 
		int i, j;
		float peso = 0f;
		for(j = 0; j < TAM_POPULACAO; j++)
		{
			for(i = 0; i < QTD_LIVROS; i++)
			{
				peso += (POP[j][i] * PESOS[i]);
			}
			if(peso > CARGA_MAX)
			{
				peso = 0f;
			}
			
			APTIDAO[j] = peso;
			peso = 0f;
		}

		
	}
	
	private int selecaoSimples()
	{
		return rand.nextInt(TAM_POPULACAO);
	}
	
	private void cruzamentoSimples()
	{
		// Variável para iteração no laço.
		int i;
		
		// Indica a linha dos indivíduos sorteados para cruzamento.
		int i1 = -1;
		int i2 = -1;
		
		// Vetores dos indivíduos para cruzamento.
		int ind1[] = new int[QTD_LIVROS];
		int ind2[] = new int[QTD_LIVROS];

		// Vetores auxiliares para armazenar os descendentes criados.
		int des1[] = new int[QTD_LIVROS];
		int des2[] = new int[QTD_LIVROS];

		// Sorteia os indivíduos.
		i1 = this.selecaoSimples();
		i2 = this.selecaoSimples();
		
		//System.out.println("i1: " + i1 + " \ni2: " + i2);
		
		// Garante que os indivíduos sorteados são diferentes.
		while(i1 == i2)
			i2 = this.selecaoSimples();
		
		// Copia os indivíduos sorteados. 
		for(i = 0; i < QTD_LIVROS; i++)
		{
			ind1[i] = POP[i1][i];
			ind2[i] = POP[i2][i];
		}
		
		// Realiza, finalmente, o cruzamento.
		for(i = 0; i < QTD_LIVROS; i++)
		{
			if(i < QTD_LIVROS/2) {
				des1[i] = ind1[i];
				des2[i] = ind2[i];
			} else {
				des1[i] = ind2[i];
				des2[i] = ind1[i];
			}
		}
		
		// Armazena os descendentes na população auxiliar.
		this.addPopAux(des1);
		this.addPopAux(des2);
	}

	
	private void mutacao()
	{
		// Variável para iteração no laço.
		int i;
		
		// Indica a linha do indivíduo sorteado para mutação.
		int i1 = -1;

		
		// Vetor do indivíduo para mutação.
		int ind1[] = new int[QTD_LIVROS];
		


		// Sorteia o indivíduo.
		i1 = this.selecaoSimples();
		
		//System.out.println("Mutação i1: " + i1);
		
		
		// Copia os indivíduos sorteados. 
		for(i = 0; i < QTD_LIVROS; i++)
		{
			ind1[i] = POP[i1][i];
		}
		
		// Realiza a mutação no individuo.
		i = rand.nextInt(QTD_LIVROS);
		
		ind1[i] = ind1[i] == 1 ? 0 : 1;
		
		// Armazena os descendentes na população auxiliar.
		this.addPopAux(ind1);
	}

	private void substituicao()
	{
		int i, j;
		
		for(j = 0; j < TAM_POPULACAO; j++)
		{
			for(i = 0; i < QTD_LIVROS; i++)
			{
				POP[j][i] = POP_AUX[j][i];
			}
		}
		taxaPopAux = -1;
	}
	
	private void addPopAux(int[] des) {
		int i;
		taxaPopAux++;
		
		for(i = 0; i < QTD_LIVROS; i++)
		{
			POP_AUX[taxaPopAux][i] = des[i];
		}
	
	}
	
	public void exibePopulacao() {
		int i, j;
		
		for(j = 0; j < TAM_POPULACAO; j++) 
		{
			System.out.print(" " + j + "\t");
			for(i = 0; i < QTD_LIVROS; i++)
			{
				System.out.print(" " + POP[j][i]);
			}
			System.out.println(" ");
		}
		
	}

	public void exibePopulacaoAux() {
		int i, j;
		
		for(j = 0; j <= taxaPopAux; j++) 
		{
			System.out.print(" " + j + "\t");
			for(i = 0; i < QTD_LIVROS; i++)
			{
				System.out.print(" " + POP_AUX[j][i]);
			}
			System.out.println(" ");
		}
				
	}

	public void exibeAptidao() {
		int j;
		
		for(j = 0; j < TAM_POPULACAO; j++) 
		{
			System.out.println(" " + j + "\t " + APTIDAO[j]);
		}
		
	}

	public void atribuiPesos()
	{
		int i; 
		for(i = 0; i < QTD_LIVROS; i++)
		{
			PESOS[i] = rand.nextFloat() * 5;
			System.out.println(PESOS[i]);
		}

	}

	public void exibeMelhorIndividuo()
	{
		int i; 
		int m = 0; 
		
		for (i = 1; i < TAM_POPULACAO; i++) {
			if(APTIDAO[m] < APTIDAO[i])
				m = i;
		}
		System.out.print("Melhor: " + APTIDAO[m] + " -> ");
		for (i = 0; i < QTD_LIVROS; i++) {
			System.out.print(" " + POP[m][i]);
		}
		System.out.println(" ");

	}

}
