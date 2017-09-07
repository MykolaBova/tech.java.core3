package com.monotonic.klassified.jdbi;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
public class Advertiser
{
    private long id;
    private String name;
    private String contactDetails;

    public Advertiser()
    {
    }

    public Advertiser(final String name, final String contactDetails)
    {
        this.name = name;
        this.contactDetails = contactDetails;
    }

    public String getContactDetails()
    {
        return contactDetails;
    }

    public Advertiser setContactDetails(final String contactDetails)
    {
        this.contactDetails = contactDetails;
        return this;
    }

    public String getName()
    {
        return name;
    }

    public Advertiser setName(final String name)
    {
        this.name = name;
        return this;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    public long getId()
    {
        return id;
    }

    public Advertiser setId(final long id)
    {
        this.id = id;
        return this;
    }
}
