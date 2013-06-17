package com.icbat.game.tradesong.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.icbat.game.tradesong.Tradesong;

public class MainMenuScreen extends AbstractScreen {
		
	Skin skin;
	Stage stage;
	SpriteBatch batch;
		
	public MainMenuScreen(Tradesong game) {
		super(game);
		batch = new SpriteBatch();
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
	
		// A skin can be loaded via JSON or defined programmatically, either is fine. Using a skin is optional but strongly
		// recommended solely for the convenience of getting a texture, region, etc as a drawable, tinted drawable, etc.
		skin = new Skin();
	
		// Generate a 1x1 white texture and store it in the skin named "pixel".
		Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		skin.add("pixel", new Texture(pixmap));
	
		// Store the default libgdx font under the name "default".
		skin.add("default", new BitmapFont());
	
		// Configure a TextButtonStyle and name it "default". Skin resources are stored by type, so this doesn't overwrite the font.
		TextButtonStyle textButtonStyle = new TextButtonStyle();
//		textButtonStyle.up = skin.newDrawable("pixel", Color.DARK_GRAY);
//		textButtonStyle.down = skin.newDrawable("pixel", Color.DARK_GRAY);
//		textButtonStyle.checked = skin.newDrawable("pixel", Color.BLUE);
		textButtonStyle.over = skin.newDrawable("pixel", Color.LIGHT_GRAY);
		textButtonStyle.downFontColor = Color.LIGHT_GRAY;
		textButtonStyle.fontColor = Color.WHITE;
		textButtonStyle.font = skin.getFont("default");
		skin.add("default", textButtonStyle);
	
		// Create a table that fills the screen. Everything else will go inside this table.
		Table table = new Table();
		table.setFillParent(true);
		stage.addActor(table);
	
		// Create a button with the "default" TextButtonStyle. A 3rd parameter can be used to specify a name other than "default".
		final TextButton newButton = new TextButton("Start", skin);
		final TextButton exitButton = new TextButton("Exit", skin);
		table.add(newButton);
		table.row();
		table.add(exitButton);
	
		// Add a listener to the button. ChangeListener is fired when the button's checked state changes, eg when clicked,
		// Button#setChecked() is called, via a key press, etc. If the event.cancel() is called, the checked state will be reverted.
		// ClickListener could have been used, but would only fire when clicked. Also, canceling a ClickListener event won't
		// revert the checked state.
//		button.addListener(new ChangeListener() {
//		public void changed (ChangeEvent event, Actor actor) {
//		System.out.println("Clicked! Is checked: " + button.isChecked());
//		button.setText("Good job!");
//		}
//		});
	
		// Add an image actor. Have to set the size, else it would be the size of the drawable (which is the 1x1 texture).
//		table.add(new Image(skin.newDrawable("pixel", Color.RED))).size(64);
	}
	
	
	@Override
	public void render( float delta ) {
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();
		Table.drawDebug(stage);
	}
	
	@Override
	public void resize(int width, int height) {
		stage.setViewport(width, height, false);
	}
	
	@Override
	public void dispose() {
		stage.dispose();
		skin.dispose();
	}
}