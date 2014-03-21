package com.icbat.game.tradesong;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.icbat.game.tradesong.assetReferences.MusicAssets;
import com.icbat.game.tradesong.assetReferences.SoundAssets;
import com.icbat.game.tradesong.assetReferences.TextureAssets;
import com.icbat.game.tradesong.screens.MapScreen;
import com.icbat.game.tradesong.utils.UIStyles;

public class Tradesong extends Game {

    public static ScreenManager screenManager;
    public static AssetManager assetManager = new AssetManager();
    public static UIStyles uiStyles;
    public static ItemPrototypes itemPrototypes;
    public static WorkshopListing workshopListing;
    public static ContractGenerator contractGenerator;
    public static GameState saveableState;
    public static Clock clock;

    @Override
    public void create() {
        Gdx.app.setLogLevel(3);

        initializeStaticData();
        playLoopingMusic(MusicAssets.MYSTERIOUS);
        screenManager.goToMainMenu();
    }

    private void initializeStaticData() {
        Tradesong.assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        initializeAssets();
        saveableState = new GameState();
        uiStyles = new UIStyles();
        screenManager = new ShallowSelectiveScreenStack(this);
        itemPrototypes = new ItemPrototypes();
        contractGenerator = new ContractGenerator(itemPrototypes.getAll());
        workshopListing = new WorkshopListing();
        clock = new Clock();
    }

    private void initializeAssets() {

        for (TextureAssets texture : TextureAssets.values()) {
            assetManager.load(texture.getPath(), Texture.class);
        }

        for (SoundAssets sound : SoundAssets.values()) {
            assetManager.load(sound.getPath(), Sound.class);
        }

        for (MusicAssets music : MusicAssets.values()) {
            assetManager.load(music.getPath(), Music.class);
        }
        assetManager.finishLoading();
    }

    /**
     * Convenience method to prevent having to call assetManager.get(longConstantName)
     *
     * @return the object in assetManager by that name
     * @throws Error if the file could not be found
     */
    public static Texture getTexture(TextureAssets toFind) {
        return assetManager.get(toFind.getPath());
    }

    /**
     * Convenience method to prevent having to call assetManager.get(longConstantName)
     *
     * @return the object in assetManager by that name
     * @throws Error if the file could not be found
     */
    public static Sound getSound(SoundAssets toFind) {
        return assetManager.get(toFind.getPath());
    }

    /**
     * Plays the music on loop given the asset name.
     *
     * @throws Error if the file could not be found
     */
    public static void playLoopingMusic(MusicAssets toFind) {
        Music currentTrack = assetManager.get(toFind.getPath());
        currentTrack.setLooping(true);
        if (!currentTrack.isPlaying()) {
            currentTrack.play();
        }

        currentTrack.setVolume(0.9f);
    }

    static void startGame() {
        screenManager.goToScreen(new MapScreen("bigger_static_wyld"));
    }

    public static void setupNewGame() {
        saveableState = new GameState();
        clock.startDay();
        startGame();
    }
}
