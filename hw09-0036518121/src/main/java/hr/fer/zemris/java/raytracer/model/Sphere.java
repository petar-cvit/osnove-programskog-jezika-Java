package hr.fer.zemris.java.raytracer.model;

/**
 * Sphere class extends GraphicalObject class. It represents a sphere in scene with it's
 * radius and center point.
 * 
 * @author Petar Cvitanović
 *
 */
public class Sphere extends GraphicalObject{

	/**
	 * sphere radius
	 */
	private Point3D center;
	
	/**
	 * sphere center
	 */
	private double radius;
	
	/**
	 * diffuse component for red colour
	 */
	private double kdr;
	
	/**
	 * diffuse component for green colour
	 */
	private double kdg;
	
	/**
	 * diffuse component for blue colour
	 */
	private double kdb;
	
	/**
	 * reflective component for red colour
	 */
	private double krr;
	
	/**
	 * reflective component for green colour
	 */
	private double krg;
	
	/**
	 * reflective component for blue colour
	 */
	private double krb;
	
	/**
	 * surface roughness factor
	 */
	private double krn;
	
	/**
	 * Constructor with all needed parameters.
	 * 
	 * @param center
	 * @param radius
	 * @param kdr
	 * @param kdg
	 * @param kdb
	 * @param krr
	 * @param krg
	 * @param krb
	 * @param krn
	 */
	public Sphere(Point3D center, double radius, double kdr, double kdg, double kdb, double krr, double krg, double krb,
			double krn) {
		super();
		this.center = center;
		this.radius = radius;
		this.kdr = kdr;
		this.kdg = kdg;
		this.kdb = kdb;
		this.krr = krr;
		this.krg = krg;
		this.krb = krb;
		this.krn = krn;
	}

	/**
	 * Returns closest intersection represented with RayIntersection class if there is any.
	 * If there isn't method returns null.
	 */
	@Override
	public RayIntersection findClosestRayIntersection(Ray ray) {
		double distanceFirst = -(ray.direction.scalarProduct(ray.start.sub(center)));
		
		double distanceSecond = Math.sqrt(Math.pow((ray.direction.scalarProduct(ray.start.sub(center))), 2) - 
								(Math.pow(ray.start.sub(center).norm(), 2) - Math.pow(radius, 2)));
		
		if(Double.valueOf(distanceSecond).isNaN()) {
			return null;
		}
			
		double distance = Math.min(distanceFirst + distanceSecond , distanceFirst - distanceSecond);
		
		Point3D point = ray.start.add(ray.direction.scalarMultiply(distance));
		
		return new RayIntersection(point, distance, true) {
			
			@Override
			public Point3D getNormal() {
				return point.sub(center).normalize();
			}

			@Override
			public double getKdr() {
				return kdr;
			}

			@Override
			public double getKdg() {
				return kdg;
			}

			@Override
			public double getKdb() {
				return kdb;
			}

			@Override
			public double getKrr() {
				return krr;
			}

			@Override
			public double getKrg() {
				return krg;
			}

			@Override
			public double getKrb() {
				return krb;
			}

			@Override
			public double getKrn() {
				return krn;
			}
		};
		
	}
}
