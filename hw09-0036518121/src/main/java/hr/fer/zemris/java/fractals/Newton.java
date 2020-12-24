package hr.fer.zemris.java.fractals;

import java.util.ArrayList;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * Paints complex plane to colours depending on each point's properties. User needs to enter roots of complex polynomial.
 * After the user has entered all roots and enter "done" as final input, a GUI is shown. In that GUI every pixel represents
 * one complex number. For every complex number program calculates to which root of given complex polynomial this number converges.
 * In every iteration this number is subtracted by f(z)/f'(z) where z is complex number, and f(z) is function of given polynomial.
 * If difference between z and z - f(z)/f'(z) is less than predetermined threshold or number of iterations exceeds predetermined
 * maximum number of iterations program breaks loop and calculates with which root it makes the least difference. If this value
 * is bigger than another predetermined threshold this pixel is painted in some colour. On the other hand if it is smaller
 * it is painted in some colour related to index of that root.
 * 
 * @author Petar Cvitanović
 *
 */
public class Newton {

	/**
	 * Main method takes roots as input and calls fractal producer with given polynomial.
	 * 
	 * @param no arguments
	 */
	public static void main(String[] args) {
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
		System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done.");

		Scanner sc = new Scanner(System.in);

		List<Complex> rootsList = new ArrayList<Complex>();
		
		int i = 1;
		
		while(true) {
			System.out.print("Root " + i +">");
			String input = sc.nextLine();

			if(input.trim().toLowerCase().equals("done")) {
				break;	
			}

			try {
				rootsList.add(new Complex(input));
				i++;
			} catch (NumberFormatException e) {
				System.out.println("Ivnvalid number format!");
			}
		}
		
		sc.close();
		
		System.out.println("Image of fractal will appear shortly. Thank you.");
		
		Complex[] roots = new Complex[rootsList.size()];
		rootsList.toArray(roots);
		
		ComplexRootedPolynomial polynomial = new ComplexRootedPolynomial(
				Complex.ONE, roots);
		FractalViewer.show(new FractalProducer(polynomial));
	}
	
	/**
	 * Class that represents one thread's job.
	 * 
	 * @author Petar Cvitanović
	 *
	 */
	public static class CalculateNewton implements Callable<Void> {
		
		/**
		 * distance between root and complex number
		 */
		private static final double ROOT_DISTANCE = 0.002;
		
		/**
		 * distance between two complex numbers in iterations
		 */
		private static final double TRESHOLD = 0.001;
		
		/**
		 * minimum value of real part
		 */
		double reMin;
		
		/**
		 * maximum value of real part
		 */
		double reMax;
		
		/**
		 * minimum value of imaginary part
		 */
		double imMin;
		
		/**
		 * maximum value of imaginary part
		 */
		double imMax;
		
		/**
		 * screen width
		 */
		int width;
		
		/**
		 * screen height
		 */
		int height;
		
		/**
		 * minimum y value
		 */
		int yMin;
		
		/**
		 * maximum y value
		 */
		int yMax;
		
		/**
		 * maximum number of iterations
		 */
		int m;
		
		/**
		 * indexes of roots used to determine colour
		 */
		short[] data;
		
		/**
		 * entered polynomial
		 */
		ComplexRootedPolynomial rootedPolynomial;
		
		/**
		 * cancels started job
		 */
		AtomicBoolean cancel;
		
		/**
		 * Constructor with all needed parameters.
		 * 
		 * @param reMin
		 * @param reMax
		 * @param imMin
		 * @param imMax
		 * @param width
		 * @param height
		 * @param yMin
		 * @param yMax
		 * @param m
		 * @param data
		 * @param polynomial
		 * @param cancel
		 */
		public CalculateNewton(double reMin, double reMax, double imMin, double imMax, int width, int height, int yMin,
				int yMax, int m, short[] data, ComplexRootedPolynomial polynomial, AtomicBoolean cancel) {
			super();
			this.reMin = reMin;
			this.reMax = reMax;
			this.imMin = imMin;
			this.imMax = imMax;
			this.width = width;
			this.height = height;
			this.yMin = yMin;
			this.yMax = yMax;
			this.m = m;
			this.data = data;
			this.rootedPolynomial = polynomial;
			this.cancel = cancel;
		}

		@Override
		public Void call() throws Exception {
			int offset = yMin * width;
			
			for(int y = yMin;y <= yMax;y++) {
				for(int x = 0;x < width;x++) {
					if(cancel.get()) {
						return null;
					}
					
					Complex c = mapToComplex(x, y);
					Complex zn = c.add(Complex.ZERO);
					Complex znold;
					ComplexPolynomial polynomial = rootedPolynomial.toComplexPolynom();
					int iter = 0;
					do {
						Complex function = polynomial.apply(zn);
						Complex derivedFunction = polynomial.derive().apply(zn);
						znold = zn;
						zn = zn.sub(function.divide(derivedFunction));
						iter++;
					} while(znold.sub(zn).module() > TRESHOLD && iter < m);
					int index = rootedPolynomial.indexOfClosestRootFor(zn, ROOT_DISTANCE);
					data[offset++] = (short) (index + 1);
				}
			}
			
			return null;
		}
		
		/**
		 * Maps (x, y) given as pixel to complex number in complex plain.
		 * 
		 * @param x
		 * @param y
		 * @return complex number in complex plain
		 */
		private Complex mapToComplex(int x, int y) {
			return new Complex(x / (width - 1.0) * (reMax - reMin) + reMin,
					((height - 1 - y) / (height - 1.0)) * (imMax - imMin) + imMin);
		}
	}
	
	/**
	 * Has method that creates new threads and starts them. Those threads split screen in multiple lanes.
	 * Every thread is assigned for calculating indexes of roots for numbers in one lane.
	 * 
	 * @author Petar Cvitanović
	 *
	 */
	public static class FractalProducer implements IFractalProducer{
		
		/**
		 * polynomial with roots
		 */
		private ComplexRootedPolynomial polynomial;
		
		/**
		 * daemonic thread pool
		 */
		private ExecutorService pool;
		
		/**
		 * Constructor with polynomial
		 * 
		 * @param polynomial
		 */
		public FractalProducer(ComplexRootedPolynomial polynomial) {
			this.polynomial = polynomial;
			pool = Executors.newFixedThreadPool(
					Runtime.getRuntime().availableProcessors() * 8, new DaemonicThreadFactory());
			
		}
		
		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax, int width, int height, long requestNo,
				IFractalResultObserver observer, AtomicBoolean cancel) {

			int m = 16 * 16 * 16;
			short[] data = new short[width * height];

			final int numberOfLanes = 8 * Runtime.getRuntime().availableProcessors();
			int numberYPerLane = height / numberOfLanes;
			
			List<Future<Void>> rezultati = new ArrayList<>();
			
			for(int i = 0;i < numberOfLanes;i++) {
				int yMin = i * numberYPerLane;
				int yMax = (i + 1) * numberYPerLane - 1;
				if(i == numberOfLanes - 1) {
					yMax = height - 1;
				}
				
				CalculateNewton job = new CalculateNewton(reMin, reMax, imMin, imMax, width, height, yMin, yMax, m, data, polynomial, cancel);
				rezultati.add(pool.submit(job));
			}
			
			for(Future<Void> job : rezultati) {
				try {
					job.get();
				} catch (InterruptedException | ExecutionException e) {
				}
			}
			
			observer.acceptResult(data, (short)(polynomial.toComplexPolynom().order() + 1), requestNo);
		}
		
	}

}
