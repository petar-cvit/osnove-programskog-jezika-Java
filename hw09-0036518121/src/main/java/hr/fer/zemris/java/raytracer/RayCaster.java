package hr.fer.zemris.java.raytracer;

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
 * RayCaster class creates GUI and paints it's pixels. We are looking at some predetermined scene. This scene we are looking at
 * has some objects and light sources. Each light source produces light that is casted to scene's objects. As we are looking at
 * the scene, it's objects reflect light from light sources. Each pixel's colour depends on whether or not ray that goes to observers
 * eye that goes through pixel has intersection with any object in scene. Those pixels that were mentioned earlier are placed in
 * some virtual plane between observer and scene's objects. If there is no previously mentioned intersection with scene objects, 
 * this pixel will be dark. If there actually is some intersection this pixel is coloured. Pixel's colour is determined by ambient, 
 * diffuse and reflected component. Ambient component is set to constant value, while other two components depend on light sources.
 * If light ray from any light source can't reach intersection of light from observers eye and scene's object, that light source
 * won't influence pixel's colour.
 * 
 * @author Petar Cvitanović
 *
 */
public class RayCaster {

	/**
	 * Main method calls RayTraceViewer instance and gives it RayTracerProducer that is defined later.
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
	 * Creates new implementation of IRayTracerProducer interface.
	 * 
	 * @return RayTracerProducer
	 */
	private static IRayTracerProducer getIRayTracerProducer() {
		return new IRayTracerProducer() {

			@Override
			public void produce(Point3D eye, Point3D view, Point3D viewUp,
					double horizontal, double vertical, int width, int height,
					long requestNo, IRayTracerResultObserver observer, AtomicBoolean cancel) {
				
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

				short[] rgb = new short[3];
				int offset = 0;
				for(int y = 0; y < height; y++) {
					for(int x = 0; x < width; x++) {
						Point3D screenPoint = screenCorner.add(xAxis.scalarMultiply(horizontal * x / (width - 1.0)))
								.sub(yAxis.scalarMultiply(vertical * y / (height - 1.0)));
						
						Ray ray = Ray.fromPoints(eye, screenPoint);
						
						tracer(scene, ray, rgb);
						
						red[offset] = rgb[0] > 255 ? 255 : rgb[0];
						green[offset] = rgb[1] > 255 ? 255 : rgb[1];
						blue[offset] = rgb[2] > 255 ? 255 : rgb[2];
						offset++;
					}
				}
				System.out.println("Izračuni gotovi...");
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Dojava gotova...");
			}

			/**
			 * Determines whether or not pixels colour should be black or should this pixel be coloured.
			 * 
			 * @param scene
			 * @param ray
			 * @param Array of three components:red, green, blue. Each number represents intensity of each colour.
			 */
			private void tracer(Scene scene, Ray ray, short[] rgb) {
				rgb[0] = 0;
				rgb[1] = 0;
				rgb[2] = 0;
				
				RayIntersection closest = findClosestIntersection(scene, ray);
				
				if(closest==null) {
					return;
				}

				pickColor(closest, scene, ray.start, rgb);
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
			private void pickColor(RayIntersection intersection, Scene scene, Point3D eye, short[] rgb) {
				rgb[0] = 15;
				rgb[1] = 15;
				rgb[2] = 15;
				
				for(LightSource ls : scene.getLights()) {
					Ray lightRay = Ray.fromPoints(ls.getPoint(), intersection.getPoint());
					
					RayIntersection lightRayIntersection = findClosestIntersection(scene, lightRay);
					
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
			private RayIntersection findClosestIntersection(Scene scene, Ray ray) {
				RayIntersection out = null;

				for(GraphicalObject o : scene.getObjects()) {
					RayIntersection tmp = o.findClosestRayIntersection(ray);
					
					if(out == null || tmp != null && Double.compare(tmp.getDistance(), out.getDistance()) < 0) {
						out = tmp;
					}
				}

				return out;
			}

		};
	}
}
