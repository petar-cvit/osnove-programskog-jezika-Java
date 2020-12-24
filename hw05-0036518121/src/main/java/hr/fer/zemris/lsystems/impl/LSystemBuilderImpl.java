package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

import hr.fer.zemris.java.custom.collections.Dictionary;
import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.commands.ColorCommand;
import hr.fer.zemris.lsystems.impl.commands.DrawCommand;
import hr.fer.zemris.lsystems.impl.commands.PopCommand;
import hr.fer.zemris.lsystems.impl.commands.PushCommand;
import hr.fer.zemris.lsystems.impl.commands.RotateCommand;
import hr.fer.zemris.lsystems.impl.commands.ScaleCommand;
import hr.fer.zemris.lsystems.impl.commands.SkipCommand;
import hr.fer.zemris.math.Vector2D;

/**
 * Configures and builds one Lindermayer's system. Class uses two dictionaries. One maps characters
 * to it's production, and the other maps characters to actions. 
 * 
 * @author Petar Cvitanović
 *
 */
public class LSystemBuilderImpl implements LSystemBuilder{

	/**
	 * dictionary with characters as keys and it's productions as values
	 */
	private Dictionary<Character, String> productions;
	
	/**
	 * dictionary with characters as keys and actions as values
	 */
	private Dictionary<Character, Command> actions;
	
	/**
	 * initial step length a turtle makes
	 */
	private double unitLength;
	
	/**
	 * scales step length every iteration step
	 */
	private double unitLengthDegreeScaler;
	
	/**
	 * initial position of a turtle represented as head of the vector
	 */
	private Vector2D origin;
	
	/**
	 * angle of a turtle's direction with respect to positive x axis 
	 */
	private double angle;
	
	/**
	 * initial string to generate new strings
	 */
	private String axiom;
	
	/**
	 * Default constructor
	 */
	public LSystemBuilderImpl() {
		unitLength = 0.1;
		unitLengthDegreeScaler = 1;
		origin = new Vector2D(0, 0);
		angle = 0;
		axiom = "";
		productions = new Dictionary<Character, String>();
		actions = new Dictionary<Character, Command>();
	}

	/**
	 * Returns new Lindermayer's system built with given parameters.
	 */
	@Override
	public LSystem build() {
		return new LSystem() {
			
			/**
			 * Recursively generates symbol sequence of given level.
			 */
			@Override
			public String generate(int level) {
				if(level == 0) {
					return axiom;
				
				} else {
					
					String out = "";
				
					for(char c : generate(level-1).toCharArray()) {
						if(productions.get(c) == null) {
							out = out.concat(Character.toString(c));
						} else {
							out = out.concat(productions.get(c));
						}
					}
					
					return out;
				}
			}
			
			/**
			 * Calls generate method and for every symbol calls certain actions. 
			 */
			@Override
			public void draw(int level, Painter painter) {
				Context context = new Context();
				
				Vector2D direction = new Vector2D(Math.cos(angle / 360 * 2 * Math.PI),
													Math.sin(angle / 360 * 2 * Math.PI));
				
				double shift = unitLength * Math.pow(unitLengthDegreeScaler, level);
				
				TurtleState firstState = new TurtleState(direction, origin, new Color(0), shift);
				context.pushState(firstState);
				
				char[] generatedSymbols = generate(level).toCharArray();
				
				for(char c : generatedSymbols) {
					Command action = actions.get(c);
					
					if(action != null) {
						action.execute(context, painter);
					}
				}
				
			}
		};
	}
	
	/**
	 * Parses given text as Lindermayer's system parameters.
	 */
	@Override
	public LSystemBuilder configureFromText(String[] lines) {
		for(String line : lines) {
			if(line.isBlank()) {
				continue;
			}
			
			String tmp[] = line.split(" ");
			int k = 0;
			
			for(int i = 0;i < tmp.length;i++) {
				if(!tmp[i].isBlank()) {
					k++;
				}
			}
			
			String[] arguments = new String[k];
			
			for(int i = 0, j = 0;i < tmp.length;i++) {
				if(!tmp[i].isBlank()) {
					arguments[j++] = tmp[i];
				}
			}
			
			switch (arguments[0]){
				case "origin":
					if(arguments.length != 3) {
						throw new IllegalArgumentException("Unable to parse document!");
					}
					
					this.origin = new Vector2D(Double.parseDouble(arguments[1]), Double.parseDouble(arguments[2]));
					break;
					
				case "angle":
					if(arguments.length != 2) {
						throw new IllegalArgumentException("Unable to parse document!");
					}
					
					this.angle = Double.parseDouble(arguments[1]);
					break;
				
				case "unitLength":
					if(arguments.length != 2) {
						throw new IllegalArgumentException("Unable to parse document!");
					}
					
					this.unitLength = Double.parseDouble(arguments[1]);
					break;
					
				case "unitLengthDegreeScaler":
					if(arguments.length > 4) {
						throw new IllegalArgumentException("Unable to parse document!");
					}
					
					if(arguments.length == 4) {
						unitLengthDegreeScaler = Double.parseDouble(arguments[1]) / 
													Double.parseDouble(arguments[3]);
					} else if(arguments.length == 3) {
						if(arguments[1].contains("/")) {
							unitLengthDegreeScaler = Double.parseDouble(arguments[1].substring(0, arguments[1].length()-1))
														/ Double.parseDouble(arguments[2]);
						} else {
							unitLengthDegreeScaler = Double.parseDouble(arguments[1])
									/ Double.parseDouble(arguments[2].substring(1, arguments[2].length()));
						}
					} else if(arguments.length == 2) {
						unitLengthDegreeScaler = Double.parseDouble(arguments[1]);
					} else {
						throw new IllegalArgumentException("Unable to parse document!");
					}
					
					break;
				
				case "axiom":
					if(arguments.length != 2) {
						throw new IllegalArgumentException("Unable to parse document!");
					}
					
					this.axiom = arguments[1];
					break;
					
				case "command":
					if(arguments.length == 3 && (arguments[2].equals("push") ||
							arguments[2].equals("pop"))) {
						
						registerCommand(arguments[1].charAt(0), arguments[2]);

					} else if(arguments.length == 4) {
						registerCommand(arguments[1].charAt(0), String.join(" ", arguments[2], arguments[3]));
					}
					break;
					
				case "production":
					if(arguments.length != 3) {
						throw new IllegalArgumentException("Unable to parse document!");
					}
					
					registerProduction(arguments[1].charAt(0), arguments[2]);
					break;
				
				default:
					throw new IllegalArgumentException("Unable to parse document!");
			}
		}
		
		return this;
	}

	/**
	 * Parses given string to action and maps it to given symbol.
	 */
	@Override
	public LSystemBuilder registerCommand(char symbol, String action) {
		
		String tmp[] = action.split(" ");
		int k = 0;
		
		for(int i = 0;i < tmp.length;i++) {
			if(!tmp[i].isBlank()) {
				k++;
			}
		}
		
		String[] arguments = new String[k];
		
		for(int i = 0, j = 0;i < tmp.length;i++) {
			if(!tmp[i].isBlank()) {
				arguments[j++] = tmp[i];
			}
		}
		
		switch (arguments[0]) {
			case "draw":
				if(arguments.length != 2) {
					throw new IllegalArgumentException("Unable to parse document!");
				}
				
				actions.put(symbol, new DrawCommand(Double.parseDouble(arguments[1])));
				break;
				
			case "color":
				if(arguments.length != 2) {
					throw new IllegalArgumentException("Unable to parse document!");
				}
				
				actions.put(symbol, new ColorCommand(Color.decode("#" + arguments[1])));
				break;
				
			case "pop":
				if(arguments.length != 1) {
					throw new IllegalArgumentException("Unable to parse document!");
				}
				
				actions.put(symbol, new PopCommand());
				break;
				
			case "push":
				if(arguments.length != 1) {
					throw new IllegalArgumentException("Unable to parse document!");
				}
				
				actions.put(symbol, new PushCommand());
				break;
				
			case "rotate":
				if(arguments.length != 2) {
					throw new IllegalArgumentException("Unable to parse document!");
				}
				
				actions.put(symbol, new RotateCommand(Double.parseDouble(arguments[1])));
				break;
				
			case "scale":
				if(arguments.length != 2) {
					throw new IllegalArgumentException("Unable to parse document!");
				}
				
				actions.put(symbol, new ScaleCommand(Double.parseDouble(arguments[1])));
				break;
				
			case "skip":
				if(arguments.length != 2) {
					throw new IllegalArgumentException("Unable to parse document!");
				}
				
				actions.put(symbol, new SkipCommand(Double.parseDouble(arguments[1])));
				break;
			default:
				throw new IllegalArgumentException("Unable to parse document!");
		}
		
		return this;
		
	}

	/**
	 * Maps given symbol to production given as string.
	 */
	@Override
	public LSystemBuilder registerProduction(char symbol, String production) {
		productions.put(symbol, production);
		return this;
	}

	/**
	 * Sets initial angle.
	 */
	@Override
	public LSystemBuilder setAngle(double angle) {
		this.angle = angle;
		return this;
	}

	/**
	 * Sets axiom.
	 */
	@Override
	public LSystemBuilder setAxiom(String axiom) {
		this.axiom = axiom;
		return this;
	}

	/**
	 * Sets turtle's origin.
	 */
	@Override
	public LSystemBuilder setOrigin(double x, double y) {
		this.origin = new Vector2D(x, y);
		return this;
	}

	/**
	 * Sets initial length.
	 */
	@Override
	public LSystemBuilder setUnitLength(double unitLength) {
		this.unitLength = unitLength;
		return this;
	}

	/**
	 * Sets scaler which is used to scale unit length.
	 */
	@Override
	public LSystemBuilder setUnitLengthDegreeScaler(double unitLengthDegreeScaler) {
		this.unitLengthDegreeScaler = unitLengthDegreeScaler;
		return this;
	}
	
}
