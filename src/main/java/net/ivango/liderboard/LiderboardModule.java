package net.ivango.liderboard;

import com.google.inject.Singleton;
import com.google.inject.servlet.ServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
import net.ivango.liderboard.rest.LiderboardResource;
import net.ivango.liderboard.services.LiderBoardServiceImpl;
import net.ivango.liderboard.services.LiderboardService;
import net.ivango.liderboard.storage.LiderboardDAO;
import net.ivango.liderboard.storage.LiderboardDAOImpl;
import org.eclipse.jetty.servlet.DefaultServlet;

public class LiderboardModule extends ServletModule {
    @Override
    protected void configureServlets() {
        bind(DefaultServlet.class).in(Singleton.class);
        bind(LiderboardResource.class).in(Singleton.class);
        bind(LiderboardDAO.class).to(LiderboardDAOImpl.class).in(Singleton.class);
        bind(LiderboardService.class).to(LiderBoardServiceImpl.class).in(Singleton.class);
        serve("/*").with(GuiceContainer.class);
    }
}
