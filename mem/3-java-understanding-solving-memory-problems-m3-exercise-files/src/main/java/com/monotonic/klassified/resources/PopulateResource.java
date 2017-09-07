package com.monotonic.klassified.resources;

import com.codahale.metrics.annotation.Timed;
import com.monotonic.klassified.jdbi.Advert;
import com.monotonic.klassified.jdbi.AdvertDAO;
import com.monotonic.klassified.jdbi.Advertiser;
import com.monotonic.klassified.jdbi.AdvertiserDAO;
import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * NB: must be thread-safe
 */
@Path("/populate")
public class PopulateResource
{
    private static final int POPULATE_FACTOR = 30_000;

    private final AdvertDAO advertDAO;
    private final AdvertiserDAO advertiserDAO;

    public PopulateResource(final AdvertDAO advertDAO, final AdvertiserDAO advertiserDAO)
    {
        this.advertDAO = advertDAO;
        this.advertiserDAO = advertiserDAO;
    }

    @GET
    @Timed
    @UnitOfWork
    public String populate()
    {
        for (int i = 0; i < POPULATE_FACTOR; i++)
        {
            final Advertiser keen = new Advertiser("Keen Kustomer " + i, "123 Fake Street");
            advertiserDAO.save(keen);

            advertDAO.save(new Advert(
                "Job",
                "Java Developer " + i,
                "Agile, TDD, Spring, Hibernate ... " + i,
                keen));
        }

        for (int i = 0; i < POPULATE_FACTOR; i++)
        {
            final Advertiser sofaSales = new Advertiser("Refurbisher " + i, "456 Fake Street");
            advertiserDAO.save(sofaSales);

            advertDAO.save(new Advert(
                "Furniture",
                "Second Hand Sofa " + i,
                "Very soft and comfortable number" + i,
                sofaSales));
        }

        for (int i = 0; i < POPULATE_FACTOR; i++)
        {
            final Advertiser rentalAgent = new Advertiser("Richard's Rentals " + i, "789 Fake Street");
            advertiserDAO.save(rentalAgent);

            advertDAO.save(new Advert(
                "property",
                "Fantastic Room " + i,
                "Location, location ... rising damp number" + i,
                rentalAgent));
        }

        return "Ok";
    }
}
