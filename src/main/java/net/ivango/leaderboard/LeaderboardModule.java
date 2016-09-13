package net.ivango.leaderboard;

import com.google.inject.Singleton;
import com.google.inject.servlet.ServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
import net.ivango.leaderboard.rest.LeaderboardResource;
import net.ivango.leaderboard.services.LeaderBoardServiceImpl;
import net.ivango.leaderboard.services.LeaderboardService;
import net.ivango.leaderboard.storage.LeaderboardDAO;
import net.ivango.leaderboard.storage.LeaderboardDAOImpl;
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
