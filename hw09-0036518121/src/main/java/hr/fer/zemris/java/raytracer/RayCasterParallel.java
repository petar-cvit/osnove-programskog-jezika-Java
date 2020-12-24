package hr.fer.zemris.java.raytracer;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.raytracer.model.GraphicalObject;
import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.LightSource;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

/**
 * We are looking at some predetermined scene. This scene we are looking at has some objects and light sources. Each light source
 * produces light that is casted to scene's objects. As we are looking at the scene, it's objects reflect light from light sources.
 * Each pixel's colour depends on whether or not ray that goes to observers eye that goes through pixel has intersection with any
 * object in scene. Those pixels that were mentioned earlier are placed in some virtual plane between observer and scene's objects.
 * If there is no previously mentioned intersection with scene objects, this pixel will be dark. If there actually is some intersection
 * this pixel is coloured. Pixel's colour is determined by ambient, diffuse and reflected component. Ambient component is set to
 * constant value, while other two components depend on light sources. If light ray from any light source can't reach intersection of
 * light from observers eye and scene's object, that light source won't influence pixel's colour.
 * 
 * Class creates GUI and paints pixels as described. This process is divided between multiple daemonic threads.
 * 
 * @author Petar Cvitanović
 *
 */
public class RayCasterParallel {
	
	/**
	 * daemonic thread pool
	 */
	private static ForkJoinPool pool = new ForkJoinPool();

	/**
	 * Main method calls RayTracerViewer's method show with instance of RayTracerProducer and shows GUI.
	 * 
	 * @param no arguments
	 */
	public static void main(String[] args) {
		RayTracerViewer.show(getIRayTracerProducer(),
				new Point3D(10,0,0),
				new Point3D(0,0,0),
				new Point3D(0,0,10),
				20, 20);
	}
	
	/**
	 * Calculates colour for every point in [0, width] x [minY, maxY] only if difference is less than
	 * predetermined threshold.
	 * 
	 * @author Petar Cvitanović
	 *
	 */
	public static class Job extends RecursiveAction {

		/**
		 * serial version UID
		 */
		private static final long serialVersionUID = 1L;
		
		/**
		 * minimum y value
		 */
		private int minY;
		
		/**
		 * maximum x value
		 */
		private int maxY;
		
		/**
		 * threshold that determines whether or not recursion should continue or not
		 */
		private static final int THRESHOLD = 16;

		/**
		 * intensities of red colour for every pixel
		 */
		private short[] red;
		
		/**
		 * intensities of green colour for every pixel
		 */
		private short[] green;
		
		/**
		 * intensities of blue colour for every pixel
		 */
		private short[] blue;
		
		/**
		 * scene that we are looking at
		 */
		private Scene scene;
		
		/**
		 * observers position
		 */
		private Point3D eye;
		
		/**
		 * j normalised vector
		 */
		private Point3D yAxis;
		
		/**
		 * i normalised vector
		 */
		private Point3D xAxis;
		
		/**
		 * point of upper left corner
		 */
		private Point3D screenCorner;
		
		/**
		 * screen width
		 */
		private int width;
		
		/**
		 * screen height
		 */
		private int height;
		
		/**
		 * length of x axis on screen
		 */
		private double horizontal;
		
		/**
		 * length of y axis on screen
		 */
		private double vertical;
		
		/**
		 * Constructor with all needed parameters.
		 * 
		 * @param minY
		 * @param maxY
		 * @param red
		 * @param green
		 * @param blue
		 * @param scene
		 * @param eye
		 * @param yAxis
		 * @param xAxis
		 * @param screenCorner
		 * @param width
		 * @param height
		 * @param horizontal
		 * @param vertical
		 */
		public Job(int minY, int maxY, short[] red, short[] green, short[] blue, Scene scene, Point3D eye,
				Point3D yAxis, Point3D xAxis, Point3D screenCorner, int width, int height, double horizontal,
				double vertical) {
			super();
			this.minY = minY;
			this.maxY = maxY;
			this.red = red;
			this.green = green;
			this.blue = blue;
			this.scene = scene;
			this.eye = eye;
			this.yAxis = yAxis;
			this.xAxis = xAxis;
			this.screenCorner = screenCorner;
			this.width = width;
			this.height = height;
			this.horizontal = horizontal;
			this.vertical = vertical;
		}

		@Override
		protected void compute() {
			if(maxY - minY + 1 <= THRESHOLD) {
				computeDirectly();
				return;
			}
			
			int middle = (maxY + minY) / 2;
			
			invokeAll(
					new Job(minY, middle, red, green, blue, scene, eye, yAxis, xAxis, screenCorner, width, height, horizontal, vertical),
					new Job(middle, maxY, red, green, blue, scene, eye, yAxis, xAxis, screenCorner, width, height, horizontal, vertical)
			);
		}

		/**
		 * Called if difference between maxY and minY is smaller than predetermined threshold.
		 * It calculates intensity of every colour on GUI.
		 */
		protected void computeDirectly() {
			int offset = minY * width;
			for(int y = minY; y < maxY; y++) {
				for(int x = 0; x < width; x++) {

					short[] rgb = new short[3];

					Point3D screenPoint = screenCorner.add(xAxis.scalarMultiply(horizontal * x / (width - 1.0)))
							.sub(yAxis.scalarMultiply(vertical * y / (height - 1.0)));

					Ray ray = Ray.fromPoints(eye, screenPoint);

					RayIntersection closest = findClosestIntersection(ray);

					if(closest==null) {
						rgb[0] = 0;
						rgb[1] = 0;
						rgb[2] = 0;
					} else {
						pickColor(closest, scene, ray, eye, rgb);
					}

					red[offset] = rgb[0] > 255 ? 255 : rgb[0];
					green[offset] = rgb[1] > 255 ? 255 : rgb[1];
					blue[offset] = rgb[2] > 255 ? 255 : rgb[2];
					
					offset++;
				}
			}

		}

		/**
		 * Calculates colour only if some intersection was found tracer method.
		 * 
		 * @param intersection			-intersection of ray from observers eye with scene's object
		 * @param scene
		 * @param ray					-ray from observers eye
		 * @param eye					-observer's position
		 * @param rgb					-colours intensity
		 */
		private void pickColor(RayIntersection intersection, Scene scene, Ray ray, Point3D eye, short[] rgb) {
			rgb[0] = 15;
			rgb[1] = 15;
			rgb[2] = 15;

			for(LightSource ls : scene.getLights()) {
				Ray lightRay = Ray.fromPoints(ls.getPoint(), intersection.getPoint());

				RayIntersection lightRayIntersection = findClosestIntersection(lightRay);

				if(lightRayIntersection == null || Double.compare(ls.getPoint().sub(intersection.getPoint()).norm(),
						lightRayIntersection.getDistance() + 0.0001) > 0) {
					continue;
				}

				Point3D n = intersection.getNormal();

				Point3D l = lightRay.direction.negate();

				Point3D r = lightRay.direction.sub(n.scalarMultiply(
						2 * lightRay.direction.scalarProduct(n))).normalize();

				Point3D v = eye.sub(intersection.getPoint()).normalize();

				rgb[0] += ls.getR() * (intersection.getKdr() * Math.max(l.scalarProduct(n), 0) +
						intersection.getKrr() * Math.pow(Math.max(r.scalarProduct(v), 0), intersection.getKrn()));

				rgb[1] += ls.getG() * (intersection.getKdg() * Math.max(l.scalarProduct(n), 0) +
						intersection.getKrg() * Math.pow(Math.max(r.scalarProduct(v), 0), intersection.getKrn()));

				rgb[2] += ls.getB() * (intersection.getKdb() * Math.max(l.scalarProduct(n), 0) +
						intersection.getKrb() * Math.pow(Math.max(r.scalarProduct(v), 0), intersection.getKrn()));
			}
		}

		/**
		 * Returns intersection that has the least distance between given ray's source and scene's objects.
		 * 
		 * @param scene
		 * @param ray
		 * @return first intersection from ray's source
		 */
		private RayIntersection findClosestIntersection(Ray ray) {
			RayIntersection out = null;

			for(GraphicalObject o : scene.getObjects()) {
				RayIntersection tmp = o.findClosestRayIntersection(ray);

				if(out == null || tmp != null && Double.compare(tmp.getDistance(), out.getDistance()) < 0) {
					out = tmp;
				}
			}

			return out;
		}
	}

	/**
	 * Creates new implementation of IRayTracerProducer interface.
	 * 
	 * @return RayTracerProducer
	 */
	private static IRayTracerProducer getIRayTracerProducer() {
		return new IRayTracerProducer() {

			@Override
			public void produce(Point3D eye, Point3D view, Point3D viewUp, double horizontal, double vertical, int width,
					int height, long requestNo, IRayTracerResultObserver observer, AtomicBoolean cancel) {
				System.out.println("Započinjem izračune...");
				short[] red = new short[width*height];
				short[] green = new short[width*height];
				short[] blue = new short[width*height];

				Point3D viewUpNormalized = viewUp.normalize();

				Point3D zAxis = view.sub(eye).normalize();
				Point3D yAxis = viewUpNormalized.sub(zAxis.scalarMultiply(zAxis.scalarProduct(viewUpNormalized))).normalize();
				Point3D xAxis = zAxis.vectorProduct(yAxis).normalize();

				Point3D screenCorner = view.sub(xAxis.scalarMultiply(horizontal / 2)).add(
						yAxis.scalarMultiply(vertical / 2));

				Scene scene = RayTracerViewer.createPredefinedScene();

				pool.invoke(new Job(0, height,red, green, blue, scene, eye, yAxis, xAxis, screenCorner, width, height, horizontal, vertical));

				System.out.println("Izračuni gotovi...");
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Dojava gotova...");
			}
		};
	}
}
