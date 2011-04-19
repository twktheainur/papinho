package bzr.client;

import bzr.shared.FieldVerifier;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SubmitButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Voodleclass implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	/*private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";*/

	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	/*private final GreetingServiceAsync greetingService = GWT
		*	.create(GreetingService.class);*/

	private VerticalPanel loginPanel = new VerticalPanel();
	private TextBox userField = new TextBox();
	private PasswordTextBox passField = new PasswordTextBox();
	private SubmitButton submitAuth = new SubmitButton("Login");
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		loginPanel.add(userField);
		loginPanel.add(passField);
		loginPanel.add(submitAuth);
		RootPanel.get("login").add(loginPanel);
	}
}
