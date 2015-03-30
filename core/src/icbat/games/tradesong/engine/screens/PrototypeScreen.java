package icbat.games.tradesong.engine.screens;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import icbat.games.tradesong.TradesongGame;
import icbat.games.tradesong.engine.screens.components.PrototypeLayoutTable;

public class PrototypeScreen extends AbstractBaseScreen {

    public PrototypeScreen() {
        backgroundColor.setRed(0.2f);
        backgroundColor.setGreen(0.2f);
        backgroundColor.setBlue(0.2f);
        backgroundColor.setAlpha(1);
    }

    @Override
    protected String getScreenName() {
        return "Prototype Screen";
    }

    @Override
    protected Table buildCentralLayout() {
        return new PrototypeLayoutTable(TradesongGame.turnTaker, TradesongGame.potentialWorkshops, TradesongGame.holdings, TradesongGame.contracts);
    }

}
