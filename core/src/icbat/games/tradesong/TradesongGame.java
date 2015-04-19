package icbat.games.tradesong;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import icbat.games.tradesong.engine.*;
import icbat.games.tradesong.engine.screens.OverviewScreen;
import icbat.games.tradesong.game.Item;
import icbat.games.tradesong.game.PlayerHoldings;
import icbat.games.tradesong.game.TurnTaker;
import icbat.games.tradesong.game.contracts.Contract;
import icbat.games.tradesong.game.contracts.ContractFactory;
import icbat.games.tradesong.game.workers.WorkerImpl;
import icbat.games.tradesong.game.workers.WorkerPool;
import icbat.games.tradesong.game.workshops.MutatorWorkshop;
import icbat.games.tradesong.game.workshops.ProducerWorkshop;
import icbat.games.tradesong.game.workshops.StorefrontWorkshop;
import icbat.games.tradesong.game.workshops.Workshop;
import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class TradesongGame extends Game {
	public static ContractFactory factory;
	public static PlayerHoldings holdings = new PlayerHoldings();
	public static Collection<Workshop> potentialWorkshops = new ArrayList<Workshop>();
	public static TurnTaker turnTaker;
	public static GameSkin skin;
	public static ScreenManager screenManager;
	public static List<Contract> contracts;
	public static List<Item> items;
	private static Item basicItem;
	private static Item betterItem;
	private static Item assembledItem;

	public void setupContracts() {
		factory = new ContractFactory(new Random(), new RandomGenerator<Item>(items, new Random()));
		contracts = new ArrayList<Contract>();
		contracts.add(factory.buildRandomContract());
		contracts.add(factory.buildRandomContract());
		contracts.add(factory.buildRandomContract());
	}

	public void setupWorkerPool() {
		WorkerPool spareWorkers = holdings.getSpareWorkers();
		spareWorkers.addWorker(new WorkerImpl());
		spareWorkers.addWorker(new WorkerImpl());
		spareWorkers.addWorker(new WorkerImpl());
		spareWorkers.addWorker(new WorkerImpl());
	}

	public void setupItems() {
		items = new ArrayList<Item>();
		basicItem = new Item("an Item", 300);
		betterItem = new Item("a better item", 1000);
		assembledItem = new Item("Assembled thing", 1500);
		items.addAll(new ItemJsonReader().read(readAssetFileToString("items.json")));
		System.out.println(items);
	}

	private String readAssetFileToString(String path) {
		final FileHandle assetFile = Gdx.files.internal(path);
		final String string;
		try {
			string = FileUtils.readFileToString(assetFile.file());
		} catch (IOException e) {
			Gdx.app.error("Loading items", "IO error reading from" +assetFile.file().getAbsolutePath(), e);
			throw new RuntimeException(e); //TODO make this less dirty
		}
		return string;
	}

	public void setupWorkshops() {
		potentialWorkshops.add(new ProducerWorkshop(basicItem));
		potentialWorkshops.add(new ProducerWorkshop(betterItem, 3));
		potentialWorkshops.add(new MutatorWorkshop(assembledItem, basicItem, betterItem));
		potentialWorkshops.add(new StorefrontWorkshop(holdings));
	}

	@Override
	public void create () {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);

		skin = new GameSkin();
		setupItems();
		setupContracts();
		turnTaker = new TurnTaker(holdings, contracts, factory);
		setupWorkshops();
		setupWorkerPool();

		holdings.addCurrency(1000);
		holdings.addWorkshop(new StorefrontWorkshop(holdings));

		screenManager = new SimpleScreenManager(this);
		screenManager.goToScreen(new OverviewScreen());
	}
}
