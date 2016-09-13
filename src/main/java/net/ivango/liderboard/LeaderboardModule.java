package net.ivango.liderboard;

import com.google.inject.Singleton;
import com.google.inject.servlet.ServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
import net.ivango.liderboard.rest.LeaderboardResource;
import net.ivango.liderboard.services.LeaderBoardServiceImpl;
import net.ivango.liderboard.services.LeaderboardService;
import net.ivango.liderboard.storage.LeaderboardDAO;
import net.ivango.liderboard.storage.LeaderboardDAOImpl;
import org.eclipse.jetty.servlet.DefaultServlet;

public class LeaderboardModule extends ServletModule {
    @Override
    protected void configureServlets() {
        bind(DefaultServlet.class).in(Singleton.class);
        bind(LeaderboardResource.class).in(Singleton.class);
        bind(LeaderboardDAO.class).to(LeaderboardDAOImpl.class).in(Singleton.class);
        bind(LeaderboardService.class).to(LeaderBoardServiceImpl.class).in(Singleton.class);
        serve("/*").with(GuiceContainer.class);
    }
}
