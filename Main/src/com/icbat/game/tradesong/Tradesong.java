package com.icbat.game.tradesong;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Json;
import com.icbat.game.tradesong.assetReferences.MusicAssets;
import com.icbat.game.tradesong.assetReferences.SoundAssets;
import com.icbat.game.tradesong.assetReferences.TextureAssets;
import com.icbat.game.tradesong.gameObjects.Contract;
import com.icbat.game.tradesong.gameObjects.Inventory;
import com.icbat.game.tradesong.screens.MapScreen;
import com.icbat.game.tradesong.utils.UIStyles;

import java.util.List;

/**
 * This class:
 * - sets up the game initially
 * - tracks/exposes game variables
 * - loads common/global assets
 * - Violates all sorts of principles like doing one thing.
 */
public class Tradesong extends Game {

    public static ScreenManager screenManager;
    public static AssetManager assetManager = new AssetManager();
    public static UIStyles uiStyles;
    public static ItemPrototypes itemPrototypes;
    public static Inventory inventory;
    public static WorkshopListing workshopListing;
    public static ContractGenerator contractGenerator;
    public static Clock clock;
    public static GameVariables gameVariables = new GameVariables();
    private static int saveSlotNumber = 1;

    public static List<Contract> contractList;

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
        uiStyles = new UIStyles();
        screenManager = new ShallowSelectiveScreenStack(this);
        clock = new Clock();
        itemPrototypes = new ItemPrototypes();
        contractGenerator = new ContractGenerator(itemPrototypes.getAll());
        workshopListing = new WorkshopListing();
    }

    public static void startNewGame() {
        inventory = new Inventory();
//        screenManager.goToScreen(new MapScreen("fairy_fountain"));
        screenManager.goToScreen(new MapScreen("bigger_static_wyld"));
        gameVariables = new GameVariables();
        clock.startDay();
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

    public static void saveGame() {
        FileHandle gameSaveFile = Gdx.files.external("Tradesong/tradesong_save_" + saveSlotNumber + ".json");
        Json json = new Json();
        gameSaveFile.delete();
        gameSaveFile.writeString(json.prettyPrint(gameVariables), true);
        gameSaveFile.writeString(json.prettyPrint(inventory), true);
        gameSaveFile.writeString(json.prettyPrint(contractList), true);
    }
}
