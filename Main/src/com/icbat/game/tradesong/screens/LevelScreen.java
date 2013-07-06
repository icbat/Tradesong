package com.icbat.game.tradesong.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.*;
import com.badlogic.gdx.math.Vector3;
import com.icbat.game.tradesong.Tradesong;

/**
 * Generic level screen. The way maps are shown.
 * */
public class LevelScreen extends AbstractScreen {
	
	public String mapName = "";
	private TiledMap map = null;
	private TiledMapRenderer renderer = null;
	
	private OrthographicCamera camera = null;
	private InputAdapter cameraController;

	public LevelScreen(String level, Tradesong game) {
		super(game);
		
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, (w / h) * 10, 10);
		camera.zoom = 1;
		camera.update();

		cameraController = new OrthoCamController(camera);
		Gdx.input.setInputProcessor(cameraController);
		
		// Map loading Starts
		game.assets.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
		
		// "Internal" relative address. What the asset loader wants.
		this.mapName = "maps/" + level + ".tmx";
		
		log("Loading level" + level);
		long startTime, endTime;
		startTime = System.currentTimeMillis();
		game.assets.load(mapName, TiledMap.class);
		game.assets.finishLoading();
		endTime = System.currentTimeMillis();
		log("Loaded map in " + (endTime - startTime) + " milliseconds");
		// Map loading ends
		
		
		this.map = game.assets.get(mapName);
		this.renderer = new OrthogonalTiledMapRenderer(this.map, 1f / 64f);
		
	}
	
	@Override
	public void render(float delta) {
		super.render(delta);
		Gdx.gl.glClearColor(100f / 255f, 100f / 255f, 250f / 255f, 1f);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		camera.update();
		renderer.setView(camera);
		renderer.render();
		
	}

	@Override
	public void dispose() {
		// TODO dispose ALL the things
		super.dispose(); // Likely needs to be called last
		this.map.dispose();
	}

	/**
	 * Input Adapter for these maps. Directly from GDX test cases for now. Handles:
	 *  - touch-dragging 
	 * */
	class OrthoCamController extends InputAdapter {
		final OrthographicCamera camera;
		final Vector3 curr = new Vector3();
		final Vector3 last = new Vector3(-1, -1, -1);
		final Vector3 delta = new Vector3();

		public OrthoCamController (OrthographicCamera camera) {
			this.camera = camera;
		}

		@Override
		public boolean touchDragged (int x, int y, int pointer) {
			camera.unproject(curr.set(x, y, 0));
			
			if (!(last.x == -1 && last.y == -1 && last.z == -1)) {
				camera.unproject(delta.set(last.x, last.y, 0));
				delta.sub(curr);
				camera.position.add(delta.x, delta.y, 0);
			}
			
			last.set(x, y, 0);
			return false;
		}
	
		@Override
		public boolean touchUp (int x, int y, int pointer, int button) {
			last.set(-1, -1, -1);
			return false;
		}
	}
}