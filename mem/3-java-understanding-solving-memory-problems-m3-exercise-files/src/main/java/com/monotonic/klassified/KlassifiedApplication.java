package com.monotonic.klassified;

import com.monotonic.klassified.jdbi.Advert;
import com.monotonic.klassified.jdbi.AdvertDAO;
import com.monotonic.klassified.jdbi.Advertiser;
import com.monotonic.klassified.jdbi.AdvertiserDAO;
import com.monotonic.klassified.resources.PopulateResource;
import com.monotonic.klassified.resources.PostingResource;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.setup.JerseyEnvironment;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class KlassifiedApplication extends Application<KlassifiedConfiguration>
{
    private final HibernateBundle<KlassifiedConfiguration> hibernate = new HibernateBundle<KlassifiedConfiguration>(
        Advert.class, Advertiser.class) {
        @Override
        public DataSourceFactory getDataSourceFactory(KlassifiedConfiguration configuration) {
            return configuration.getDatabase();
        }
    };

    public static void main(String[] args) throws Exception
    {
        new KlassifiedApplication().run(args);
    }

    @Override
    public String getName()
    {
        return "klassified";
    }

    @UnitOfWork
    @Override
    public void run(
        final KlassifiedConfiguration klassifiedConfiguration, final Environment environment) throws Exception
    {
        final AdvertDAO advertDAO = getAdvertDAO();
        final AdvertiserDAO advertiserDAO = getAdvertiserDAO();
        final JerseyEnvironment jersey = environment.jersey();

        jersey.register(new PostingResource(advertDAO));
        jersey.register(new PopulateResource(advertDAO, advertiserDAO));
    }

    public AdvertiserDAO getAdvertiserDAO()
    {
        return new AdvertiserDAO(hibernate.getSessionFactory());
    }

    public AdvertDAO getAdvertDAO()
    {
        return new AdvertDAO(hibernate.getSessionFactory());
    }

    @Override
    public void initialize(final Bootstrap<KlassifiedConfiguration> bootstrap)
    {
        bootstrap.addBundle(hibernate);
    }
}
