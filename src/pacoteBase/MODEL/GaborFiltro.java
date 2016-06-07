package pacoteBase.MODEL;

import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.awt.image.RenderedImage;

/**
 filtro da Gabor
 */
public class GaborFiltro {

   private double[] orientations;
   private double waveLength;
   private double phaseOffset;
   private double aspectRatio;
   private double bandwidth;
   private int width;
   private int height;



   public GaborFiltro(double waveLength, double[] orientations, double phaseOffset, double aspectRatio, double bandwidth, int width, int height) {
      this.waveLength = waveLength;
      this.orientations = orientations;
      this.phaseOffset = phaseOffset;
      this.aspectRatio = aspectRatio;
      this.bandwidth = bandwidth;
      this.width = width;
      this.height = height;
     
   }

   /**
    * Orienta��o 
    *
    * @return - 
    */
   public double[] getOrientations() {
      return orientations;
   }

   /**
    * Setando orienta��o
    *
    * @param orienta��o - nova Orientations array
    */
   public void setOrientations(double[] orientations) {
      this.orientations = orientations;
   }

   /**
    * Comprimento da onda
    *
    * @return - Wave Length
    */
   public double getWaveLength() {
      return waveLength;
   }

   /**
    * Setando comprimento de onda
    *
    * @param waveLength - a new Wave Length
    */
   public void setWaveLength(double waveLength) {
      if(waveLength > 0) {
         this.waveLength = waveLength;
      }
   }

   /**
    *  Phase Offset
    *
    * @return - Phase Offset
    */
   public double getPhaseOffset() {
      return phaseOffset;
   }

   /**
    * Setando Phase Offset
    *
    * @param phaseOffset - a new Phase Offset
    */
   public void setPhaseOffset(double phaseOffset) {
      this.phaseOffset = phaseOffset;
   }

   /**
    * Gets the Aspect Ratio
    *
    * @return - Aspect Ratio
    */
   public double getAspectRatio() {
      return aspectRatio;
   }


   /**
    * Gets the Bandwidth
    *
    * @return - Bandwidth
    */
   public double getBandwidth() {
      return bandwidth;
   }

   /**
    * Sets the Bandwidth
    *
    * @param bandwidth - a new Bandwidth
    */
   public void setBandwidth(double bandwidth) {
      this.bandwidth = bandwidth;
   }

   /**
    * Calcula o Sigma para os WaveLength e Bandwidth
    *
    * @param waveLength - Wave Length
    * @param bandwidth - Bandwidth
    * @return - Sigma 
    */
   private static double calculateSigma(double waveLength, double bandwidth) {
      return waveLength*Math.sqrt(Math.log(2)/2)*(Math.pow(2, bandwidth) + 1)/((Math.pow(2, bandwidth) - 1)*Math.PI);
   }

   /**
    * Calculates Gabor function value for the given data
    *
    * @param x - X
    * @param y - Y
    * @param sigma - Sigma
    * @param aspectRatio - Aspect Ratio
    * @param waveLength - Wave Length
    * @param phaseOffset - Phase Offset
    * @return - Gabor function value
    */
   private static double gaborFunction(double x, double y, double sigma, double aspectRatio, double waveLength, double phaseOffset) {
      double gaborReal = Math.exp(-(Math.pow(x/sigma, 2) + Math.pow(y*aspectRatio/sigma, 2))/2)*Math.cos(2*Math.PI*x/waveLength + phaseOffset);
      double gaborImage = Math.exp(-(Math.pow(x/sigma, 2) + Math.pow(y*aspectRatio/sigma, 2))/2)*Math.sin(2*Math.PI*x/waveLength + phaseOffset);
      return Math.sqrt(Math.pow(gaborReal, 2) + Math.pow(gaborImage, 2));
   }

   /**
    * Returns the ConvolveOp for the Gabor Filter
    *
    * @return - ConvolveOp
    */
   public ConvolveOp getConvolveOp() {
      return new ConvolveOp(getKernel(), ConvolveOp.EDGE_NO_OP, null);
   }

   /**
    * kernel filtro da gabor
    *
    * @return - Kernel
    */
   public Kernel getKernel() {
      double sigma = calculateSigma(waveLength, bandwidth);
      float[] data = new float[width*height];
      for(int k = 0, x = -width/2; x <= width/2; x++) {
         for(int y = -height/2; y <= height/2; y++) {
            for(double orientation : orientations) {
               double x1 = x*Math.cos(orientation) + y*Math.sin(orientation);
               double y1 = -x*Math.sin(orientation) + y*Math.cos(orientation);
               data[k] += (float)(gaborFunction(x1, y1, sigma, aspectRatio, waveLength, phaseOffset));
            }
            k++;
         }
      }
      float sum = 0f;
      for(int i = 0; i < width; i++) {
         for(int j = 0; j < height; j++) {
            sum += data[i*j + j];
         }
      }
      sum /= width*height;
      for(int i = 0; i < width; i++) {
         for(int j = 0; j < height; j++) {
            data[i*j + j] -= sum;
         }
      }
      return new Kernel(width, height, data);
   }

   /**
    * Gets the Width
    *
    * @return - Width
    */
   public int getWidth() {
      return width;
   }

   /**
    * Sets the Width
    *
    * @param width - a new Width
    */
   public void setWidth(int width) {
      this.width = width;
   }

   /**
    * Gets the Height
    *
    * @return - Height
    */
   public int getHeight() {
      return height;
   }

   /**
    * Sets the Height
    *
    * @param height - a new Height
    */
   public void setHeight(int height) {
      this.height = height;
   }

   /**
    * Filters the bufferedImage using the Gabor filter. If the bufferedImageDestination is not null
    * the bufferedImage is used as the destination
    *
    * @param bufferedImage - buffered image to be used as the source
    * @param bufferedImageDestination - buffered image to be used as the destination
    * @return - the rendered image
    */
   public RenderedImage filter(BufferedImage bufferedImage, BufferedImage bufferedImageDestination) {

      return getConvolveOp().filter(bufferedImage, bufferedImageDestination);
   }
}