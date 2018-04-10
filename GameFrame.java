import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Path2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class GameFrame extends JFrame implements Runnable, KeyListener, MouseListener, MouseMotionListener {
	private Transformation transformation;
	private static final float FOV = (float) Math.toRadians(90.0f);
	private static final float Z_NEAR = 0.01f;
	private static final float Z_FAR = 10.f;
	private Camera camera;
	float aspectRatio = 750 / 500;
	int width = 750;
	int height = 500;
	Canvas canvas = new Canvas();
	BufferedImage bf;
	private boolean running = false;
	Random random = new Random();
	int _cachedcolor = 0;
	int _previous = 0;
	List<Tri> tris = new ArrayList<>();
	double alpha = 0;
	double beta = 0;
	double gamma = 0;
	BufferStrategy bs;
	Graphics graphics;
	private String frameRate = "";
	private Vector2 mousepos;
	private Vector2 prevmousepos;
	private Vector2 displacement;
	private boolean leftButtonPressed = false;
	private static final float MOUSE_SENSITIVITY = 0.02f;
	private static final double CAMERA_POS_STEP = 1f;
	private boolean rightButtonPressed = false;
	private Vector3 cameraInc;
	private List<Mesh> items = new ArrayList<>();
	private List<String> debugArr = new ArrayList<>();

	private Vector3 v1, v2, v3;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void init() {
		this.cameraInc = new Vector3(0, 0, 0);
		this.prevmousepos = new Vector2(-1, -1);
		this.mousepos = new Vector2(0, 0);
		this.displacement = new Vector2(0, 0);
		this.setVisible(true);
		this.setSize(this.width, this.height);
		this.add(this.canvas);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.canvas.addKeyListener(this);
		this.canvas.addMouseListener(this);
		this.canvas.addMouseMotionListener(this);
		this._cachedcolor = new Color(31, 64, 163).hashCode();
		this.canvas.createBufferStrategy(2);
		this.transformation = new Transformation();
		this.camera = new Camera();

		Cube cube = new Cube();
		cube.setPosition(0, 0, 0);
		this.items.add(cube);

		Cube cube1 = new Cube();
		cube1.setPosition(2, 2, -8);
		// this.items.add(cube1);

		Cube cube2 = new Cube();
		cube2.setPosition(0, -2, -1);
		// this.items.add(cube2);

		Plane plane = new Plane();
		plane.setPosition(0, 0, 0);
		// this.items.add(plane);

	}

	public void initTris(List<Tri> input) {
		this.tris.addAll(input);
	}

	private void handleInput(KeyEvent e) {

		this.cameraInc.set(0, 0, 0);
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			this.setVisible(false);
			this.running = false;
			this.dispose();
		}

		if (e.getKeyCode() == KeyEvent.VK_A) {
			this.cameraInc.setX(this.cameraInc.getX() + 1);
		}
		if (e.getKeyCode() == KeyEvent.VK_D) {
			this.cameraInc.setX((this.cameraInc.getX() - 1));
		}
		if (e.getKeyCode() == KeyEvent.VK_W) {
			this.cameraInc.setZ((this.cameraInc.getZ() + 1));
		}
		if (e.getKeyCode() == KeyEvent.VK_S) {
			this.cameraInc.setZ((this.cameraInc.getZ() - 1));
		}
		if (e.getKeyCode() == KeyEvent.VK_Z) {
			this.cameraInc.setY((this.cameraInc.getY() + 1));
		}
		if (e.getKeyCode() == KeyEvent.VK_X) {
			this.cameraInc.setY((this.cameraInc.getY() - 1));
		}

		if (e.getKeyCode() == KeyEvent.VK_UP) {
			this.camera.moveRotation(1, 0, 0);
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			this.camera.moveRotation(-1, 0, 0);
		}

		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			this.camera.moveRotation(0, 1, 0);

		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			this.camera.moveRotation(0, -1, 0);
		}

		if (e.getKeyCode() == KeyEvent.VK_V) {
			this._cachedcolor = new Color(31, 64, 163).hashCode();
		}
		if (e.getKeyCode() == KeyEvent.VK_B) {
			this._cachedcolor = _color().hashCode();
		}
	}

	private void updater() {
		this.displacement.setX(0);
		this.displacement.setY(0);

		if (this.rightButtonPressed) {
			if (this.prevmousepos.getX() > 0 && this.prevmousepos.getY() > 0 && this.contains((int) this.prevmousepos.getX(), (int) this.prevmousepos.getY())) {
				double deltax = this.mousepos.getX() - this.prevmousepos.getX();
				double deltay = this.mousepos.getX() - this.prevmousepos.getY();
				boolean rotateX = deltax != 0;
				boolean rotateY = deltay != 0;
				if (rotateX) {
					// System.out.println("a");
					this.displacement.setY(deltax);
				}
				if (rotateY) {
					// System.out.println("b");
					this.displacement.setX(deltay);
				}
			}
		}
		this.prevmousepos.setX(this.mousepos.getX());
		this.prevmousepos.setY(this.mousepos.getY());

	}

	public void render() {

		this.bf = new BufferedImage(this.getWidth(), this.getHeight(), java.awt.image.BufferedImage.TYPE_INT_ARGB);
		int color = this._cachedcolor;
		this.bs = this.canvas.getBufferStrategy();
		if (this.running) {
			this.graphics = this.bs.getDrawGraphics();
		}

		// this.camera.moveRotation((float) this.displacement.getX() *
		// MOUSE_SENSITIVITY, (float) this.displacement.getY() *
		// MOUSE_SENSITIVITY, 0.0f);
		this.camera.moveRotation((float) this.displacement.getX() * MOUSE_SENSITIVITY, (float) this.displacement.getY() * MOUSE_SENSITIVITY, 0.0f);
		this.camera.movePosition((float) (this.cameraInc.getX() * CAMERA_POS_STEP), (float) (this.cameraInc.getY() * CAMERA_POS_STEP), (float) (this.cameraInc.getZ() * CAMERA_POS_STEP));

		this.cameraInc.set(0, 0, 0);

		getDebug(this.camera.getPosition().toString());
		getDebug(this.camera.getRotation().toString());

		Matrix4 projectionMatrix = this.transformation.updateProjectionMatrix(FOV, this.getWidth(), this.getHeight(), Z_NEAR, Z_FAR);
		// this.transformation.updateViewMatrix(this.camera);
		Matrix4 viewMatrix = this.transformation.getViewMatrix(this.camera);
		Matrix4 worldMatrix = null;

		Graphics2D g2 = (Graphics2D) this.graphics;
		g2.setColor(new Color(color));
		g2.fillRect(0, 0, this.width, this.height);

		double[] zBuffer = new double[this.bf.getWidth() * this.bf.getHeight()];
		// initialize array with extremely far away depths
		for (int q = 0; q < zBuffer.length; q++) {
			zBuffer[q] = Double.NEGATIVE_INFINITY;
		}

		// System.out.println("FRAME: "+this.getWidth()+ " " +this.getHeight());
		for (Mesh it : this.items) {

			this.transformation.getWorldMatrix(it, viewMatrix);
			// this.transformation.modelViewProjectionMatrix(projectionMatrix);

			for (Tri t : it.getTris()) {

				// Convert vertices of the triangle to raster space
				this.v1 = this.transformation.projectedPoint(t.getV1());
				this.v2 = this.transformation.projectedPoint(t.getV2());
				this.v3 = this.transformation.projectedPoint(t.getV3());

				// getDebug(worldMatrix.toString());
				getDebug(new String(this.v1 + "\n" + this.v2 + "\n" + this.v3));
				// System.out.println(this.v1 + "\n" + this.v2 + "\n" +
				// this.v3);
				/*
				 * @Source:
				 * http://blog.rogach.org/2015/08/how-to-create-your-own-simple-
				 * 3d-render.html
				 */
				int minX = (int) Math.max(0, Math.ceil(Math.min(this.v1.getX(), Math.min(this.v2.getX(), this.v3.getX()))));
				int maxX = (int) Math.min(this.bf.getWidth() - 1, Math.floor(Math.max(this.v1.getX(), Math.max(this.v2.getX(), this.v3.getX()))));
				int minY = (int) Math.max(0, Math.ceil(Math.min(this.v1.getY(), Math.min(this.v2.getY(), this.v3.getY()))));
				int maxY = (int) Math.min(this.bf.getHeight() - 1, Math.floor(Math.max(this.v1.getY(), Math.max(this.v2.getY(), this.v3.getY()))));

				double angleCos = getLightAngle(this.v1, this.v2, this.v3);
				double triangleArea = (this.v1.getY() - this.v3.getY()) * (this.v2.getX() - this.v3.getX()) + (this.v2.getY() - this.v3.getY()) * (this.v3.getX() - this.v1.getX());

				for (int y = minY; y <= maxY; y++) {
					for (int x = minX; x <= maxX; x++) {
						double b1 = ((y - this.v3.getY()) * (this.v2.getX() - this.v3.getX()) + (this.v2.getY() - this.v3.getY()) * (this.v3.getX() - x)) / triangleArea;
						double b2 = ((y - this.v1.getY()) * (this.v3.getX() - this.v1.getX()) + (this.v3.getY() - this.v1.getY()) * (this.v1.getX() - x)) / triangleArea;
						double b3 = ((y - this.v2.getY()) * (this.v1.getX() - this.v2.getX()) + (this.v1.getY() - this.v2.getY()) * (this.v2.getX() - x)) / triangleArea;
						if (b1 >= 0 && b1 <= 1 && b2 >= 0 && b2 <= 1 && b3 >= 0 && b3 <= 1) {
							double depth = b1 * this.v1.getZ() + b2 * this.v2.getZ() + b3 * this.v3.getZ();
							int zIndex = y * this.bf.getWidth() + x;
							if (zBuffer[zIndex] < depth) {
								// this.bf.setRGB(x, y, getShade(t.getColor(),
								// angleCos).getRGB());
								this.bf.setRGB(x, y, t.getColor().getRGB());
								zBuffer[zIndex] = depth;
							}
						}
					}
				}
			}
		}
		getDebug(viewMatrix.toString());
		g2.drawImage(this.bf, 0, 0, null);
		drawText(g2, Font.PLAIN, Color.WHITE, 20, this.frameRate, 10, 25);
		if (this.debugArr != null) {
			int yIndex = 0;
			int xIndex = 10;

			drawText(g2, Font.PLAIN, Color.WHITE, 10, this.debugArr.get(this.debugArr.size() - 1), xIndex, 450);
			drawText(g2, Font.PLAIN, Color.WHITE, 10, this.debugArr.get(this.debugArr.size() - 2), xIndex, 440);
			drawText(g2, Font.PLAIN, Color.WHITE, 10, this.debugArr.get(this.debugArr.size() - 3), xIndex, 430);
			drawText(g2, Font.PLAIN, Color.WHITE, 10, this.debugArr.get(this.debugArr.size() - 4), xIndex, 420);
			drawText(g2, Font.PLAIN, Color.WHITE, 10, "World Matrix:", xIndex, 410);
			drawText(g2, Font.PLAIN, Color.WHITE, 10, this.debugArr.get(0), xIndex, 400);
			drawText(g2, Font.PLAIN, Color.WHITE, 10, "Camera Position: ", xIndex, 390);
			drawText(g2, Font.PLAIN, Color.WHITE, 10, this.debugArr.get(1), xIndex, 380);
			drawText(g2, Font.PLAIN, Color.WHITE, 10, "Camera Rotation: ", xIndex, 370);
			for (int x = 1; x < 5; x++) {
				this.debugArr.remove(this.debugArr.size() - 1);
			}
			this.debugArr.remove(0);
			this.debugArr.remove(0);
			for (int i = 0; i < this.debugArr.size(); i++) {
				yIndex = 360 - (i * 10);
				if (yIndex > 100) {
					if (this.debugArr.size() - i - 4 > 0)
						drawText(g2, Font.PLAIN, Color.WHITE, 10, this.debugArr.get(this.debugArr.size() - i - 4), xIndex, yIndex);
				}
			}
			if (this.debugArr.size() > 0)
				drawText(g2, Font.PLAIN, Color.WHITE, 10, "Mesh Vertices", xIndex, 100);
		}

		if (this.running)

		{
			this.bs.show();
		}
		this.graphics.dispose();
		this.debugArr.clear();
		this._previous = color;
	}

	private double getLightAngle(Vector3 v1, Vector3 v2, Vector3 v3) {
		Vector3 ab = new Vector3(v2.getX() - v1.getX(), v2.getY() - v1.getY(), v2.getZ() - v1.getZ());
		Vector3 ac = new Vector3(v3.getX() - v1.getX(), v3.getY() - v1.getY(), v3.getZ() - v1.getZ());
		Vector3 norm = new Vector3(ab.getY() * ac.getZ() - ab.getZ() * ac.getY(), ab.getZ() * ac.getX() - ab.getX() * ac.getZ(), ab.getX() * ac.getY() - ab.getY() * ac.getX());
		double normalLength = Math.sqrt(norm.getX() * norm.getX() + norm.getY() * norm.getY() + norm.getZ() * norm.getZ());
		norm.setX(norm.getX() / normalLength);
		norm.setY(norm.getY() / normalLength);
		norm.setZ(norm.getZ() / normalLength);

		return Math.abs(norm.getZ());
	}

	private void getDebug(String text) {
		String[] temp = text.split("\n");
		for (String s : temp) {
			this.debugArr.add(s);
		}
	}

	public static Vector3 update(Vector3 v, Matrix4 otherMatrix) {
		return otherMatrix.mulToVec3(new Vector4(v.getX(), v.getY(), v.getZ()));
	}

	public static Color getShade(Color color, double shade) {
		double redLinear = Math.pow(color.getRed(), 2.4) * shade;
		double greenLinear = Math.pow(color.getGreen(), 2.4) * shade;
		double blueLinear = Math.pow(color.getBlue(), 2.4) * shade;

		int red = (int) Math.pow(redLinear, 1 / 2.4);
		int green = (int) Math.pow(greenLinear, 1 / 2.4);
		int blue = (int) Math.pow(blueLinear, 1 / 2.4);

		return new Color(red, green, blue);
	}

	public void drawText(Graphics graphics, int style, Color color, int size, String msg, int x, int y) {
		final Font FONT = new Font("Helvetica", style, size);
		graphics.setFont(FONT);
		Color back = new Color(0, 0, 0);
		graphics.setColor(back);
		graphics.drawString(msg, x + 1, y + 1);
		graphics.setColor(color);
		graphics.drawString(msg, x, y);
	}

	private Color _color() {
		return new Color(this.random.nextInt(255), this.random.nextInt(255), this.random.nextInt(255));
	}

	private Color _compliment(Color color) {
		double y = (299 * color.getRed() + 587 * color.getGreen() + 114 * color.getBlue()) / 1000;
		return y >= 128 ? Color.black : Color.white;
	}

	public void start() {
		this.running = true;
		this.init();
		new Thread(this).start();
	}

	public void stop() {
		this.running = false;
	}

	public void run() {
		long lastTime = System.nanoTime();
		double nsPerTick = 1000000000D / 60D;

		int frames = 0;
		int ticks = 0;

		long lastTimer = System.currentTimeMillis();
		double delta = 0;

		while (this.running == true) {
			long now = System.nanoTime();
			delta += (now - lastTime) / nsPerTick;
			lastTime = now;
			boolean shouldRender = true;

			while (delta >= 1) {
				ticks++;
				delta -= 1;
			}

			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (shouldRender) {
				frames++;
				updater();
				render();
			}

			if (System.currentTimeMillis() - lastTimer > 1000) {
				lastTimer += 1000;
				setFrameRate(ticks, frames);
				frames = 0;
				ticks = 0;
			}
		}
	}

	public boolean isLeftButtonPressed() {
		return this.leftButtonPressed;
	}

	public boolean isRightButtonPressed() {
		return this.rightButtonPressed;
	}

	public void setFrameRate(int ticks, int frames) {
		this.frameRate = "FPS: " + frames;
		// System.out.println(frameRate);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		this.handleInput(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void mouseDragged(MouseEvent me) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent me) {
		if (this.rightButtonPressed) {
			this.mousepos.setX(me.getPoint().getX());
			this.mousepos.setY(me.getPoint().getY());
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		this.leftButtonPressed = e.getButton() == MouseEvent.BUTTON1;
		this.rightButtonPressed = e.getButton() == MouseEvent.BUTTON3;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// this.leftButtonPressed = false;
		// this.rightButtonPressed = false;

	}

}
