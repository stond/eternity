package cz.fourtwoone;

import cz.fourtwoone.eternity.model.Game;
import cz.fourtwoone.eternity.provider.ImageProvider;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.inject.Inject;

/**
 * Application object for your web application.
 * If you want to run this application without deploying, run the Start class.
 */
public class WicketApplication extends WebApplication {
	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	@Override
	public Class<? extends WebPage> getHomePage() {
		return HomePage.class;
	}

	@Inject
	ImageProvider imageProvider;

	@Inject
	Game game;

	/**
	 * @see org.apache.wicket.Application#init()
	 */
	@Override
	public void init() {
		super.init();

		ApplicationContext ctx = new ClassPathXmlApplicationContext("context.xml");
		imageProvider = (ImageProvider) ctx.getBean("imageProvider");
		game = (Game) ctx.getBean("game");

	}

	public ImageProvider getImageProvider() {
		return imageProvider;
	}
}
