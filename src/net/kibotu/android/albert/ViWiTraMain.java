package net.kibotu.android.albert;

import com.badlogic.gdx.ApplicationListener;
import net.kibotu.LoggingAdapter.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Apu
 * Date: 07.02.13
 * Time: 17:56
 * To change this template use File | Settings | File Templates.
 */
public class ViWiTraMain implements ApplicationListener {
    @Override
    public void create() {
        Logger.v(this, "on create");
    }

    @Override
    public void resize(int width, int height) {
        Logger.v(this, "on resize");
    }

    @Override
    public void render() {
        Logger.v(this, "on render");
    }

    @Override
    public void pause() {
        Logger.v(this, "on pause");
    }

    @Override
    public void resume() {
        Logger.v(this, "on resume");
    }

    @Override
    public void dispose() {
        Logger.v(this, "on dispose");
    }
}