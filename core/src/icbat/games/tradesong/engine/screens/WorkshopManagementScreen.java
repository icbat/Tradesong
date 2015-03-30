package icbat.games.tradesong.engine.screens;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import icbat.games.tradesong.TradesongGame;
import icbat.games.tradesong.engine.screens.components.AddWorkshopListener;
import icbat.games.tradesong.engine.screens.components.BasicTextButton;
import icbat.games.tradesong.engine.screens.components.RemoveWorkshopListener;
import icbat.games.tradesong.engine.screens.components.VerticalWorkshopDisplay;
import icbat.games.tradesong.game.workshops.Workshop;

/***/
public class WorkshopManagementScreen extends AbstractBaseScreen {
    @Override
    protected String getScreenName() {
        return "Manage Workshops";
    }

    @Override
    protected Table buildCentralLayout() {
        final Table layout = new Table(TradesongGame.skin);
        layout.add("Potential Workshops").colspan(2);
        layout.add("Active Workshops").colspan(2);
        layout.row();

        layout.add(new VerticalWorkshopDisplay(TradesongGame.potentialWorkshops)).space(15);
        layout.add(buildAddTable()).space(15);

        layout.add(new VerticalWorkshopDisplay(TradesongGame.holdings.getWorkshops())).space(15);
        layout.add(buildRemoveTable()).space(15);

        return layout;
    }

    private Table buildAddTable() {
        final Table addTable = new Table(TradesongGame.skin);
        for (Workshop workshop : TradesongGame.potentialWorkshops) {
            addTable.add(new BasicTextButton("Add workshop", new AddWorkshopListener(workshop))).row();
        }
        return addTable;
    }

    private Actor buildRemoveTable() {
        final Table removeTable = new Table(TradesongGame.skin);
        for (Workshop workshop : TradesongGame.holdings.getWorkshops()) {
            removeTable.add(new BasicTextButton("Remove workshop", new RemoveWorkshopListener(workshop))).row();
        }
        return removeTable;
    }
}
