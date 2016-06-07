package pacoteBase.MODEL;

public class ImageUtils {
	
	
	public static int getLargura(char[][] imagem) {
		return getNColunas(imagem);
	}
	
	public static int getAltura(char[][] imagem) {
		return getNLinhas(imagem);
	}
	
	public static int getNColunas(char[][] imagem) {
		return imagem.length;
	}
	
	public static int getNLinhas(char[][] imagem) {
		if(imagem == null || imagem[0] == null) {
			return 0;
		}
		
		return imagem[0].length;
	}
	
	
}
