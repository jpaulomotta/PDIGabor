package pacoteBase.CONTROL;

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;

import pacoteBase.MODEL.ImageUtils;




public class ControlarImagem {
	
	public	char[][]      imagemCinza;
	private int           nLinImagem;
	private int           nColImagem;
	private BufferedImage imagemDada;
	


	//*******************************************************************************************
	public ControlarImagem( String   nomeArquivoImagemDada,
	                        Graphics desenho
			              )
	{
		imagemDada = lerImagem ( nomeArquivoImagemDada );
		if ( imagemDada != null ) {
			mostrarImagemBuffer ( imagemDada, desenho );
			criarImagemCinza ( imagemDada );
		}
	}

	//*******************************************************************************************
	// METODO PARA GERAR A IMAGEM RASTER EM NIVEIS DE CINZA A PARTIR DA IMAGEM BUFERIZADA COLORIDA

	public void criarImagemCinza ( BufferedImage imagem ) 
	{
		int    x, y, r, g, b;
		Raster imagemRasterEntrada;
		char   valorSaida;

		// DIMENSOES DA MATRIZ CINZA
		nColImagem  = imagem.getWidth(null);
		nLinImagem  = imagem.getHeight(null);
		imagemCinza = new char[nColImagem][nLinImagem];

		// DEFININDO IMAGENS INTERMEDIARIAS
		imagemRasterEntrada = imagem.getRaster();

		// CRIANDO IMAGEM CINZA
		for ( y = 0; y < nLinImagem; y++ ) {
			for ( x = 0; x < nColImagem; x++ ) {

				// LENDA DADOS DAS BANDAS DA IMAGEM DADA
				r = imagemRasterEntrada.getSample(x,y,0);  // le dado da banda 0 da imagem de entrada 

				try {
					g = imagemRasterEntrada.getSample(x,y,1);  // le dado da banda 1 da imagem de entrada 
					b = imagemRasterEntrada.getSample(x,y,2);  // le dado da banda 2 da imagem de entrada
				} catch ( ArrayIndexOutOfBoundsException e) {
					g = r;
					b = r;
				}

				//  GERANDO NIVEL DE CINZA 
				valorSaida = (char)((r+g+b)/3);
				imagemCinza[x][y] = valorSaida;
			
			}
		}
	}
	
	
	//************************************************************************
	// METODO DE ZOOM REPLICACAO PIXEL
	public char[][] aplicaZoom(char[][] tempZoom, int linhaEn, int colunaEn, int auxZoom)
	{

		int n = 0, d = 0, j, k; // n e d sao os fatores de ampliacao ou reducao da imagem

		// reducao da imagem em 25%
		if(auxZoom == -25){
			n = 3;
			d = 4;
			nLinImagem = linhaEn/d + linhaEn/2;
			nColImagem = colunaEn/d + colunaEn/2;
			
		// reducao da imagem em 50%	
		}else if(auxZoom == -50){ 
			n = 1;
			d = 2;
			nLinImagem = linhaEn/d;
			nColImagem = colunaEn/d;
			
		// reducao da imagem em 75%			
		}else if(auxZoom == -75){
			n = 1;
			d = 4;
			nLinImagem = linhaEn/d;
			nColImagem = colunaEn/d;
		
			
		// reducao da imagem em 95%			ARRUMAR
		}else if(auxZoom == -95){
			n = 1;
			d = 4;
			nLinImagem = linhaEn - (linhaEn - (linhaEn/4));
			nColImagem = colunaEn - (colunaEn - (colunaEn/4));
			
			
		// ampliacao da imagem em 25%	
		}else if(auxZoom == 25){
			n = 5;
			d = 4;
			nLinImagem = linhaEn + (linhaEn/4);
			nColImagem = colunaEn + (colunaEn/4);
			
		// ampliacao da imagem em 50%			
		}else if(auxZoom == 50){
			n = 3;
			d = 2;
			nLinImagem = linhaEn + (linhaEn/d);
			nColImagem = colunaEn + (colunaEn/d);
			
		// ampliacao da imagem em 75%			
		}else if(auxZoom == 75){
			n = 7;
			d = 4;
			nLinImagem = linhaEn + (linhaEn - (linhaEn/4));
			nColImagem = colunaEn + (colunaEn - (colunaEn/4)); 
			
		// ampliacao da imagem em 100%
		}else if(auxZoom == 100){
			n = 2;
			d = 1;
			nLinImagem = linhaEn*n;
			nColImagem = colunaEn*n; 
		}
		
		//PARAMETRO ZOOM DO TEXTFILD *********************************
		else if(auxZoom >0)
		{	
		
			n= 100+auxZoom;
			d= 100;
					
			nLinImagem =(int) ((float)linhaEn*((float)(n)/100));
			nColImagem = (int) ((float)colunaEn*((float)(n)/100));
	
		}
		else
		{
			n=100-Math.abs(auxZoom);
			d= 100;	
			
			nLinImagem =(int) ((float)linhaEn*((float)(n)/100));
			nColImagem = (int) ((float)colunaEn*((float)(n)/100));
			
		}
		
		
// **************************************************************
		//APLICANDO ZOOM
		
		char [][] imagemZoom = new char [nColImagem][nLinImagem];
		
		
		for (j = 0; j < nColImagem-1; j++){
			for (k = 0; k < nLinImagem-1; k++){
				imagemZoom[j][k] = tempZoom[j*d/n][k*d/n];
			}
		}
		
		imagemCinza = copiarImagem(imagemZoom);
		  
		// retorna imagem
		return ( imagemCinza );
	}
	
	
	// *****************************************************************************************
			//APLICANDO OPERAÇÂO MORFOLÓGICA
	
	
	
	

	
	
	
	
	
	public char[][] aplicaMorfologia(char[][] tempMorfologia, int linhaEn, int colunaEn)
	{

		int  j, k; 

		


		char [][] imagemMorfologia = new char [nColImagem][nLinImagem];
		
		
		for (j = 0; j < nColImagem-1; j++){
			for (k = 0; k < nLinImagem-1; k++){
				if((int)tempMorfologia[j][k] < 128){
					imagemMorfologia[j][k] = (char)0;
				}
				else{
					imagemMorfologia[j][k] = (char)255;
				}

			}
		}
				
	
		char [][]b1 = new char[][] { 
			{(char)2, (char)255, (char)2}, 
			{(char)255, (char)0, (char)255}, 
			{(char)2, (char)255, (char)2} 
		}; 
	
		char [][]b2 = new char[][] { 
			{(char)255, (char)2, (char)255}, 
			{(char)2, (char)0, (char)2}, 
			{(char)255, (char)2, (char)255} 
		}; 
		
		char [][]b3 = new char[][] { 
			{(char)2, (char)2, (char)2}, 
			{(char)255, (char)0, (char)255}, 
			{(char)2, (char)2, (char)2} 
		}; 
		
		char [][]b4 = new char[][] { 
			{(char)2, (char)255, (char)2}, 
			{(char)2, (char)0, (char)2}, 
			{(char)2, (char)255, (char)2} 
		}; 
		
		char [][]b9 = new char[][] { 
			{(char)2, (char)2, (char)255}, 
			{(char)2, (char)0, (char)2}, 
			{(char)255, (char)2, (char)2} 
		}; 
		
		char [][]b10 = new char[][] { 
			{(char)255, (char)2, (char)2}, 
			{(char)2, (char)0, (char)2}, 
			{(char)2, (char)2, (char)255} 
		}; 
		
		
		char [][]b5 = new char[][] { 
			{(char)2, (char)0, (char)2}, 
			{(char)0, (char)255, (char)0}, 
			{(char)2, (char)0, (char)2} 
		}; 
	
		char [][]b6 = new char[][] { 
			{(char)0, (char)2, (char)0}, 
			{(char)2, (char)255, (char)2}, 
			{(char)0, (char)2, (char)0} 
		}; 
		
		char [][]b7 = new char[][] { 
			{(char)2, (char)2, (char)2}, 
			{(char)0, (char)255, (char)0}, 
			{(char)2, (char)2, (char)2} 
		}; 
		
		char [][]b8 = new char[][] { 
			{(char)2, (char)0, (char)2}, 
			{(char)2, (char)255, (char)2}, 
			{(char)2, (char)0, (char)2} 
		}; 
		
		char [][]b11 = new char[][] { 
			{(char)2, (char)2, (char)0}, 
			{(char)2, (char)255, (char)2}, 
			{(char)0, (char)2, (char)2} 
		}; 
		
		char [][]b12 = new char[][] { 
			{(char)0, (char)2, (char)2}, 
			{(char)2, (char)255, (char)2}, 
			{(char)2, (char)2, (char)0} 
		}; 
		
		
		// Elementos estruturantes
		
	
		/*char [][]b1 = new char[][] { 
			{(char)255, (char)255, (char)2}, 
			{(char)255, (char)0, (char)0}, 
			{(char)2, (char)0, (char)0} 
		}; 
		
		char [][]b2 = new char[][] { 
			{(char)2, (char)255, (char)255}, 
			{(char)0, (char)0, (char)255}, 
			{(char)0, (char)0, (char)2} 
		};
		
		
		char [][]b3 = new char[][] { 
			{(char)255, (char)2, (char)0}, 
			{(char)255, (char)0, (char)0}, 
			{(char)255, (char)2, (char)0} 
		};
		
	
		char [][]b4 = new char[][] { 
			{(char)0, (char)0, (char)2}, 
			{(char)0, (char)0, (char)255}, 
			{(char)2, (char)255, (char)255} 
		};
		
	
		char [][]b5 = new char[][] { 
			{(char)255, (char)255, (char)255}, 
			{(char)2, (char)0, (char)2}, 
			{(char)0, (char)0, (char)0} 
		};
		

		char [][]b6 = new char[][] { 
			{(char)2, (char)0, (char)0}, 
			{(char)255, (char)0, (char)0}, 
			{(char)255, (char)255, (char)2} 
		};
		

		char [][]b7 = new char[][] { 
			{(char)0, (char)2, (char)255}, 
			{(char)0, (char)0, (char)255}, 
			{(char)0, (char)2, (char)255} 
		};
		
		
		char [][]b8 = new char[][] { 
			{(char)255, (char)255, (char)2}, 
			{(char)255, (char)0, (char)0}, 
			{(char)2, (char)0, (char)0} 
		};
		*/
		
		
		
		
		for (j = 0; j < nColImagem-1; j++){
			for (k = 0; k < nLinImagem-1; k++){
				if(imagemMorfologia[j][k] == b1[1][1]){
					if(verificaForma(imagemMorfologia, j, k, b1)){
						imagemMorfologia[j][k] = (char)255;
					}
				}
			}
		}
		
	

		for (j = 0; j < nColImagem-1; j++){
			for (k = 0; k < nLinImagem-1; k++){
				if(imagemMorfologia[j][k] == b2[1][1]){
					if(verificaForma(imagemMorfologia, j, k, b2)){
						imagemMorfologia[j][k] = (char)255;
					}
				}
			}
		}
		
		for (j = 0; j < nColImagem-1; j++){
			for (k = 0; k < nLinImagem-1; k++){
				if(imagemMorfologia[j][k] == b3[1][1]){
					if(verificaForma(imagemMorfologia, j, k, b3)){
						imagemMorfologia[j][k] = (char)255;
					}
				}
			}
		}
		
		for (j = 0; j < nColImagem-1; j++){
			for (k = 0; k < nLinImagem-1; k++){
				if(imagemMorfologia[j][k] == b4[1][1]){
					if(verificaForma(imagemMorfologia, j, k, b4)){
						imagemMorfologia[j][k] = (char)255;
					}
				}
			}
		}
		
		for (j = 0; j < nColImagem-1; j++){
			for (k = 0; k < nLinImagem-1; k++){
				if(imagemMorfologia[j][k] == b9[1][1]){
					if(verificaForma(imagemMorfologia, j, k, b4)){
						imagemMorfologia[j][k] = (char)255;
					}
				}
			}
		}
		
		for (j = 0; j < nColImagem-1; j++){
			for (k = 0; k < nLinImagem-1; k++){
				if(imagemMorfologia[j][k] == b10[1][1]){
					if(verificaForma(imagemMorfologia, j, k, b4)){
						imagemMorfologia[j][k] = (char)255;
					}
				}
			}
		}
		
		for (j = 0; j < nColImagem-1; j++){
			for (k = 0; k < nLinImagem-1; k++){
				if(imagemMorfologia[j][k] == b5[1][1]){
					if(verificaForma(imagemMorfologia, j, k, b5)){
						imagemMorfologia[j][k] = (char)0;
					}
				}
			}
		}
		
		for (j = 0; j < nColImagem-1; j++){
			for (k = 0; k < nLinImagem-1; k++){
				if(imagemMorfologia[j][k] == b6[1][1]){
					if(verificaForma(imagemMorfologia, j, k, b6)){
						imagemMorfologia[j][k] = (char)0;
					}
				}
			}
		}
		
		for (j = 0; j < nColImagem-1; j++){
			for (k = 0; k < nLinImagem-1; k++){
				if(imagemMorfologia[j][k] == b7[1][1]){
					if(verificaForma(imagemMorfologia, j, k, b7)){
						imagemMorfologia[j][k] = (char)0;
					}
				}
			}
		}
		
		for (j = 0; j < nColImagem-1; j++){
			for (k = 0; k < nLinImagem-1; k++){
				if(imagemMorfologia[j][k] == b11[1][1]){
					if(verificaForma(imagemMorfologia, j, k, b7)){
						imagemMorfologia[j][k] = (char)0;
					}
				}
			}
		}
		
		for (j = 0; j < nColImagem-1; j++){
			for (k = 0; k < nLinImagem-1; k++){
				if(imagemMorfologia[j][k] == b12[1][1]){
					if(verificaForma(imagemMorfologia, j, k, b7)){
						imagemMorfologia[j][k] = (char)0;
					}
				}
			}
		}
		
		
		for (j = 0; j < nColImagem-1; j++){
			for (k = 0; k < nLinImagem-1; k++){
				if(imagemMorfologia[j][k] == b8[1][1]){
					if(verificaForma(imagemMorfologia, j, k, b8)){
						imagemMorfologia[j][k] = (char)0;
					}
				}
			}
		}
		
		
		
		imagemCinza = copiarImagem(imagemMorfologia);
		  
		// retorna imagem
		return ( imagemCinza );
	}

	//******************************************************************************************
	// METODO VERIFICA FORMA
	public boolean verificaForma(char[][] imagem, int j, int k, char[][] b)
	{
		int lmin, lmax, cmin, cmax, m, n, x = 0, y = 0;
		lmin = j-1;
		lmax = j+1;
		cmin = k-1;
		cmax = k+1;

		if(lmin < 0 || lmax > imagem.length-1 || cmin < 0 || cmax > imagem[0].length-1){
			return false;
		}
		
		for (m = lmin; m <= lmax; m++){
			y = 0;
			for (n = cmin; n <= cmax; n++){
				if(b[x][y] !=  (char)2){
					if(imagem[m][n] != b[x][y]){
						return false;
					}
				}
				y++;
			}
			x++;
		}
		return true;
	}

	
	
	
	
	//******************************************************************************************
	public char[][] copiarImagem ( char[][] imagemFonte)
	{
		int nColImg = ImageUtils.getNColunas(imagemFonte),
				nLinImg = ImageUtils.getNLinhas(imagemFonte);
		int      x, y;
		char[][] imagemDestino;

		imagemDestino = new char[nColImg][nLinImg];

		for ( x = 0; x < nColImg; x++ ) 
			for ( y = 0; y < nLinImg; y++ )
				imagemDestino[x][y] = imagemFonte[x][y];

		return ( imagemDestino );
	}

	
	
	
	
	//*******************************************************************************************
	private BufferedImage lerImagem ( String nomeArquivo )
	{
		File          arquivoImagem;
		BufferedImage imagem;

		// INICIALIZANDO VARIAVEIS
		imagem        = null;
		arquivoImagem = new File(nomeArquivo);

		// LEITURA DA IMAGEM
		try {
			imagem = ImageIO.read( arquivoImagem );
		} catch (IOException e) {
			System.out.println ( "imagem " + nomeArquivo + " nao existe");
		}

		return ( imagem );
	}

	
	
	
	//*******************************************************************************************
	// MOSTRAR IMAGEM BUFERIZADA

	public void mostrarImagemBuffer  ( BufferedImage imagem,
			                           Graphics      desenho 
			                         )
	{
		int imageWidth, imageHeight, x, sx, y, sy, cell, dx, dy;
		int cells[] = {0, 1, 2, 3};

		imageWidth  = imagem.getWidth(null);
		imageHeight = imagem.getHeight(null);

		for ( x = 0; x < 2; x++ ) {
		    sx = x * imageWidth;
		    for ( y = 0; y < 2; y++ ) {
		        sy   = y * imageHeight;
		        cell = cells[x*2+y];
		        dx   = (cell / 2) * imageWidth;
		        dy   = (cell % 2) * imageHeight;
		        desenho.drawImage( imagem, dx, dy, x + imageWidth, dy + imageHeight,  
		        		           sx, sy,  sx + imageWidth, sy + imageHeight, null );
		    }
		}
	}

	
	
	
	//*******************************************************************************************
	// MOSTRAR IMAGEM DO TIPO MATRIZ DE BYTES

	public void mostrarImagemMatriz  ( char[][] imagemM,
			                           int      nLin,
			                           int      nCol,
                                       Graphics desenho 
                                     )
	{
		BufferedImage imagemB;

		imagemB = transformarMatriz2Buffer ( imagemM, nLin, nCol );
		desenho.drawImage( imagemB, 0, 0, nCol, nLin,  null );  
	}
	
	
	
	

	//*******************************************************************************************
	public BufferedImage transformarMatriz2Buffer ( char[][] imagemM,
			                                         int      nLin,
			                                         int      nCol
			                                       )
	{
		int            x, y;
		char           valorSaida;
		WritableRaster imagemRasterSaida;
		BufferedImage  imagemB;

		imagemB           = new BufferedImage( nCol, nLin, BufferedImage.TYPE_BYTE_GRAY ); 
		imagemRasterSaida = imagemB.getRaster();

		for ( y = 0; y < nLin; y++ ) {
			for ( x = 0; x < nCol; x++ ) {
				valorSaida = imagemM[x][y];
				imagemRasterSaida.setSample( x, y, 0, valorSaida );
			}
		}

		return ( imagemB );
	}

	
	
	
	
	//*******************************************************************************************
	public void gravarImagem ( String   nomeArquivo,
			                   char[][] imagemM,
			                   int      nLin,
                               int      nCol
			                 )
	{
		File          arquivoImagem;
		BufferedImage imagemB;

		imagemB = transformarMatriz2Buffer ( imagemM, nLin, nCol );

		// INICIALIZANDO VARIAVEIS
		arquivoImagem = new File(nomeArquivo + ".jpg" );

		// LEITURA DA IMAGEM
		try {
			ImageIO.write( imagemB, "jpg", arquivoImagem );
		} catch (IOException e) {
			System.out.println ( "imagem " + nomeArquivo + " nao existe");
		}
	}
	
	
	
	
	
	

	//*******************************************************************************************
	public char[][] getImagemCinza ( )
	{
		return ( imagemCinza );
	}

	//*******************************************************************************************
	public int getNLin()
	{
		return ( nLinImagem );
	}

	//*******************************************************************************************
	public int getNCol()
	{
		return ( nColImagem );
	}

	//*******************************************************************************************
	
	public int getLin()
	{
		return ( nLinImagem );
	}

	//*******************************************************************************************
	public int getCol()
	{
		return ( nColImagem );
	}
	
	//*******************************************************************************************
}
