/*
 * PapinhoApp.java
 */

package org.client;

import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

/**
 * The main class of the application.
 */
public class PapinhoApp extends SingleFrameApplication {

    /**
     * At startup create and show the main frame of the application.
     */
    @Override protected void startup() {
        view = new PapinhoView(this);
        client = new PapinhoClient(view);
        show(view);
    }



    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override protected void configureWindow(java.awt.Window root) {
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of PapinhoApp
     */
    public static PapinhoApp getApplication() {
        return Application.getInstance(PapinhoApp.class);
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
        launch(PapinhoApp.class, args);
    }

    private PapinhoView view;
    private PapinhoClient client;
}
