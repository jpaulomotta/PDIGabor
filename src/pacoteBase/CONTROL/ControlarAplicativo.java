package pacoteBase.CONTROL;


import pacoteBase.MODEL.GaborFiltro;
import pacoteBase.VIEW.*;
import java.awt.Graphics;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;



public class ControlarAplicativo implements ActionListener {

	private MontarPainelInicial pnCenario;
	private Graphics            desenhoCentro, desenhoDireita;
	private ControlarImagem     controleImagem;
	private String              nomeArquivoImagemDada;

	private char[][]            imagemAtual;

	private boolean             estadoDesenho;
	
	public static final int OPERACAO_ABRIR = 1, OPERACAO_SALVAR = 2;

	
	//*******************************************************************************************
	public ControlarAplicativo( )
	{
		pnCenario = new MontarPainelInicial( this );
		pnCenario.showPanel();
		estadoDesenho  = false;
	}
 
	
	//*******************************************************************************************
	// METODO PARA CONTROLE DOS BOTOES DO APLICATIVO
	
	public void actionPerformed(ActionEvent e)
	{
		String comando, nomeArquivo;

		comando = e.getActionCommand();

		// DEFINE AMBIENTE GRAFICO
		if ( !estadoDesenho ) {
			pnCenario.iniciarGraphics();
			desenhoCentro = pnCenario.getDesenhoCentro();
			desenhoDireita = pnCenario.getDesenhoDireita();
		}

		// ENDS THE PROGRAM
		if(comando.equals("botaoFim")) {
			System.exit(0);	
		}

		// INICIA O PROGRAMA
		if(comando.equals("botaoImagem")) {
			nomeArquivoImagemDada = pnCenario.escolherArquivo ( OPERACAO_ABRIR );
			carregarImagem();
		}
		
		
//BOTAO MORFOLOGIAA *****************************************
		
		if ( comando.equals( "botaoOpMorfologica" ) && estadoDesenho ) {
		
			
			if(pnCenario.isTransictionsSelected()){   
				//if (pnCenario.getbtOpMorfologica2() ==2){
				
				
				char[][] temp = controleImagem.copiarImagem(imagemAtual);
				
				imagemAtual = controleImagem.aplicaMorfologia(imagemAtual);
				pnCenario.limpaPainelCen();
				controleImagem.mostrarImagem(temp, desenhoCentro);
				pnCenario.limpaPainelDir();
			}
					
				if (pnCenario.isNewImageSelected())
					imagemAtual = controleImagem.aplicaMorfologia(imagemAtual);
					
					pnCenario.limpaPainelDir();
					controleImagem.mostrarImagem(imagemAtual, desenhoDireita);	
				}	
			
	//	}



//FILTRO DE GABORRR****************************************************
		
		
		if ( comando.equals( "botaoAcao1" )&& estadoDesenho  )  {

		  
		    //Criando buffer image a partir do arquivo dado. 
		    BufferedImage bufferedImage = controleImagem.transformarMatriz2Buffer ( imagemAtual );
		    // passando parametros para classe GaborFilter e salvando a imagem filtrada no disco
		     
		  
	//*******************************************************
	//APLICA FILTRO GABOR DE ACORDO COM PARAMETRO SELECIONADO
		  
		if (pnCenario.getbtGabor1() ==2){
			
			
			if (pnCenario.getbtGabor12() ==2) {
				bufferedImage = (BufferedImage) new GaborFiltro(1, new double[] {0}, 100, 0, 1, 3, 3).filter(bufferedImage, null);
			}	
			
			if (pnCenario.getbtGabor22() ==2) {
				bufferedImage = (BufferedImage) new GaborFiltro(1, new double[] {Math.PI/4}, 100, 0, 1, 3, 3).filter(bufferedImage, null);
			}	
			
			if (pnCenario.getbtGabor32() ==2) {
				bufferedImage = (BufferedImage) new GaborFiltro(1, new double[] {Math.PI/2}, 100, 0, 1, 3, 3).filter(bufferedImage, null);
			}	
			
			if (pnCenario.getbtGabor42() ==2) {
				bufferedImage = (BufferedImage) new GaborFiltro(1, new double[] {Math.PI/0.75}, 100, 0, 1, 3, 3).filter(bufferedImage, null);
			}	
			
			controleImagem.mostrarImagem(bufferedImage, desenhoDireita);	
		}

if (pnCenario.getbtGabor2() ==2){
			
			
			if (pnCenario.getbtGabor12() ==2) {
				bufferedImage = (BufferedImage) new GaborFiltro(2, new double[] {0}, 100, 0, 1, 3, 3).filter(bufferedImage, null);
			}	
			
			if (pnCenario.getbtGabor22() ==2) {
				bufferedImage = (BufferedImage) new GaborFiltro(2, new double[] {Math.PI/4}, 100, 0, 1, 3, 3).filter(bufferedImage, null);
			}	
			
			if (pnCenario.getbtGabor32() ==2) {
				bufferedImage = (BufferedImage) new GaborFiltro(2, new double[] {Math.PI/2}, 100, 0, 1, 3, 3).filter(bufferedImage, null);
			}	
			
			if (pnCenario.getbtGabor42() ==2) {
				bufferedImage = (BufferedImage) new GaborFiltro(2, new double[] {Math.PI/0.75}, 100, 0, 1, 3, 3).filter(bufferedImage, null);
			}	
			
			controleImagem.mostrarImagem(bufferedImage, desenhoDireita);
		}

		
if (pnCenario.getbtGabor3() ==2){
	
	
	if (pnCenario.getbtGabor12() ==2) {
		bufferedImage = (BufferedImage) new GaborFiltro(3, new double[] {0}, 100, 0, 1, 3, 3).filter(bufferedImage, null);
	}	
	
	if (pnCenario.getbtGabor22() ==2) {
		bufferedImage = (BufferedImage) new GaborFiltro(3, new double[] {Math.PI/4}, 100, 0, 1, 3, 3).filter(bufferedImage, null);
	}	
	
	if (pnCenario.getbtGabor32() ==2) {
		bufferedImage = (BufferedImage) new GaborFiltro(3, new double[] {Math.PI/2}, 100, 0, 1, 3, 3).filter(bufferedImage, null);
	}	
	
	if (pnCenario.getbtGabor42() ==2) {
		bufferedImage = (BufferedImage) new GaborFiltro(3, new double[] {Math.PI/0.75}, 100, 0, 1, 3, 3).filter(bufferedImage, null);
	}	
	
	controleImagem.mostrarImagem(bufferedImage, desenhoDireita);
}


if (pnCenario.getbtGabor4() ==2){
	
	
	if (pnCenario.getbtGabor12() ==2) {
		bufferedImage = (BufferedImage) new GaborFiltro(4, new double[] {0}, 100, 0, 1, 3, 3).filter(bufferedImage, null);
	}	
	
	if (pnCenario.getbtGabor22() ==2) {
		bufferedImage = (BufferedImage) new GaborFiltro(4, new double[] {Math.PI/4}, 100, 0, 1, 3, 3).filter(bufferedImage, null);
	}	
	
	if (pnCenario.getbtGabor32() ==2) {
		bufferedImage = (BufferedImage) new GaborFiltro(4, new double[] {Math.PI/2}, 100, 0, 1, 3, 3).filter(bufferedImage, null);
	}	
	
	if (pnCenario.getbtGabor42() ==2) {
		bufferedImage = (BufferedImage) new GaborFiltro(4, new double[] {Math.PI/0.75}, 100, 0, 1, 3, 3).filter(bufferedImage, null);
	}	
	
	controleImagem.mostrarImagem(bufferedImage, desenhoDireita);
	imagemAtual = controleImagem.criarImagemCinza(bufferedImage);
}

		
} 
		
		//ZOOM **************************************************
		
		if (comando.equals("botaoZoom") && estadoDesenho) {
	        
			int percentageZoom = 0; // porcentagem de zoom escolhida
			
			if(pnCenario.getBotaoZoomIn25() == 2){
				percentageZoom = 25;		
			}
			
			if(pnCenario.getBotaoZoomIn50() == 2){
				percentageZoom = 50;		
			}
						
			if (pnCenario.getBotaoZoomIn75() == 2) {				
				percentageZoom = 75;
			}
			
			if(pnCenario.getBotaoZoomIn100() == 2){
				percentageZoom = 100;
			}
			
			if (pnCenario.getBotaoZoomOut25() == 2){
				percentageZoom = -25;
			}
			
			if(pnCenario.getBotaoZoomOut50() == 2){
				percentageZoom = -50;
			}
			
			if(pnCenario.getBotaoZoomOut75() == 2){
				percentageZoom = -75;
			}
			if(pnCenario.getBotaoZoomOut95() == 2){
				percentageZoom = -95;
			}
			//TEXTFILD
			if(pnCenario.getbtZoomText()!=0){
				percentageZoom = pnCenario.getbtZoomText();
				
			}
			
			controlZoom(percentageZoom);	
			
		}

		if ( comando.equals( "botaoSalva" ) && estadoDesenho ) {
			nomeArquivo = pnCenario.escolherArquivo ( OPERACAO_SALVAR );
			controleImagem.gravarImagem( nomeArquivo, imagemAtual );
		}

		if ( comando.equals( "botaoReset" ) && estadoDesenho ) {
			pnCenario.limpaPainelCen();
			carregarImagem();
			pnCenario.resetaSistema();
		}
	}
	
	
	
	private void carregarImagem() {
		
		if ( nomeArquivoImagemDada != null ) {
			controleImagem = new ControlarImagem( nomeArquivoImagemDada, desenhoCentro );
			estadoDesenho  = true;
			imagemAtual    = controleImagem.criarImagemCinza(controleImagem.getImagemDada());

			pnCenario.mudarBotoes();
			pnCenario.limpaPainelDir();
			controleImagem.mostrarImagem ( imagemAtual, desenhoDireita );

		}
	}


	private void controlZoom(int percentageZoom) {		
							
		if(pnCenario.isTransictionsSelected()){   
			
			char[][] temp = controleImagem.copiarImagem(imagemAtual);
			
			imagemAtual = controleImagem.aplicaZoom(imagemAtual, percentageZoom);
			pnCenario.limpaPainelCen();
			controleImagem.mostrarImagem(temp, desenhoCentro);
			pnCenario.limpaPainelDir();
		}
				
		if (pnCenario.isNewImageSelected()){
			imagemAtual = controleImagem.aplicaZoom(imagemAtual, percentageZoom);
			
			pnCenario.limpaPainelDir();
			controleImagem.mostrarImagem(imagemAtual, desenhoDireita);
		}
			
	}
	
}