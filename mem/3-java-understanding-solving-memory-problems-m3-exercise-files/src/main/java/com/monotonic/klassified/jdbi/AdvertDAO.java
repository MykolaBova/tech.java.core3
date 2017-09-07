package com.monotonic.klassified.jdbi;

import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;

public class AdvertDAO extends AbstractDAO<Advert>
{
    public AdvertDAO(final SessionFactory sessionFactory)
    {
        super(sessionFactory);
    }

    public List<Advert> find()
    {
        return list(namedQuery("com.monotonic.klassified.jdbi.Advert.findAll"));
    }

    public long save(final Advert advert)
    {
        return persist(advert).getId();
    }
}
