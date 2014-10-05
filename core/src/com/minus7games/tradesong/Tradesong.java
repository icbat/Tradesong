package com.minus7games.tradesong;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class Tradesong extends Game {
    @Override
    public void create() {
        setupGame();
        setScreen(new BaseScreen());
    }

    private void setupGame() {
        ItemIndex items = new ItemIndex(Gdx.files.internal("items.json"));
        NodeIndex nodes = new NodeIndex(Gdx.files.internal("nodes.json"));
    }
}
