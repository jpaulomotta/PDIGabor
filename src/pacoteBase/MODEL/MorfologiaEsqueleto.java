package pacoteBase.MODEL;

public class MorfologiaEsqueleto {
	
	private static final char EROSAO = 0, DILATACAO = 1;
	
	public static char[][] aplicar(char[][] tempMorfologia)
	{
		
		int linhaEn = ImageUtils.getNLinhas(tempMorfologia);
		int colunaEn = ImageUtils.getNColunas(tempMorfologia);

		char [][] imagemMorfologia = new char [colunaEn][linhaEn];
		
		
		for (int j = 0; j < colunaEn-1; j++){
			for (int k = 0; k < linhaEn-1; k++){
				if((int)tempMorfologia[j][k] > 128){
					imagemMorfologia[j][k] = (char)0;
				}
				else{
					imagemMorfologia[j][k] = (char)1;
				}

			}
		}
				
	
		char [][]b1 = new char[][] { 
			{0, 0, 0}, 
			{2, 1, 2}, 
			{1, 1, 1} 
		}; 
		
		char [][]b2 = new char[][] { 
			{1, 2, 0}, 
			{1, 1, 0}, 
			{1, 2, 0} 
		};
		
		char [][]b3 = new char[][] { 
			{1, 1, 1}, 
			{2, 1, 2}, 
			{0, 0, 0}
		};
		
		char [][]b4 = new char[][] { 
			{0, 2, 1}, 
			{0, 1, 1}, 
			{0, 2, 1} 
		};
		
		//Elemento 2
		char [][]b5 = new char[][] { 
			{2, 0, 0}, 
			{1, 1, 0}, 
			{2, 1, 2} 
		}; 
		
		char [][]b6 = new char[][] { 
			{2, 1, 2}, 
			{1, 1, 0}, 
			{2, 0, 0} 
		}; 
		
		char [][]b7 = new char[][] { 
			{2, 1, 2}, 
			{0, 1, 1}, 
			{0, 0, 2}
		}; 
		
		char [][]b8 = new char[][] { 
			{0, 0, 2}, 
			{0, 1, 1}, 
			{2, 1, 2} 
		}; 
		
		
		
		
		boolean modificou = false;
		do {
			modificou = aplicarMorfologia(imagemMorfologia, b1, EROSAO) ||
			aplicarMorfologia(imagemMorfologia, b5, EROSAO) ||
			
			aplicarMorfologia(imagemMorfologia, b2, EROSAO) ||
			aplicarMorfologia(imagemMorfologia, b6, EROSAO) ||
			
			aplicarMorfologia(imagemMorfologia, b3, EROSAO) ||
			aplicarMorfologia(imagemMorfologia, b7, EROSAO) ||
			
			aplicarMorfologia(imagemMorfologia, b4, EROSAO) ||
			aplicarMorfologia(imagemMorfologia, b8, EROSAO);
		} while(modificou);
		
		
		
		for(int i=0; i<imagemMorfologia.length; i++) {
			for(int j=0;j<imagemMorfologia[0].length; j++) {
				if(imagemMorfologia[i][j] == 1) {
					imagemMorfologia[i][j] = 0x00;
				} else {
					imagemMorfologia[i][j] = 0xFF;
				}
			}
		}

		
		
		return imagemMorfologia;
	}
	
	
	private static boolean aplicarMorfologia(char[][] imagem, char[][] b, char aplicar) {
		boolean modificou = false;
		for (int j = 0; j < imagem.length-1; j++){
			for (int k = 0; k < imagem[0].length-1; k++){
				if(imagem[j][k] == b[1][1]){
					if(verificaForma(imagem, j, k, b)){
						imagem[j][k] = aplicar;
						modificou = true;
					}
				}
			}
		}
		return modificou;
	}

	//******************************************************************************************
	// METODO VERIFICA FORMA
	private static boolean verificaForma(char[][] imagem, int j, int k, char[][] b)
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
}
