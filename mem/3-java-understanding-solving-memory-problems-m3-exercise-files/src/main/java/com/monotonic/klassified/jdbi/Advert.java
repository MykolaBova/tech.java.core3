package com.monotonic.klassified.jdbi;

import javax.persistence.*;
import java.time.ZonedDateTime;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "adverts")
@NamedQueries({
    @NamedQuery(
        name = "com.monotonic.klassified.jdbi.Advert.findAll",
        query = "SELECT a FROM Advert a"
    )
})
public class Advert
{
    private long id;
    private String category;
    private String title;
    private String description;
    private ZonedDateTime postedAt;
    private ZonedDateTime lastModifiedAt;
    private Advertiser postedBy;

    public Advert()
    {
    }

    public Advert(
        final String category, final String title, final String description, final Advertiser postedBy)
    {
        this.category = category;
        this.title = title;
        this.description = description;
        this.postedBy = postedBy;
        postedAt = lastModifiedAt = ZonedDateTime.now();
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    public long getId()
    {
        return id;
    }

    public Advert setId(final long id)
    {
        this.id = id;
        return this;
    }

    public String getCategory()
    {
        return category;
    }

    public Advert setCategory(final String category)
    {
        this.category = category;
        return this;
    }

    public String getTitle()
    {
        return title;
    }

    public Advert setTitle(final String title)
    {
        this.title = title;
        return this;
    }

    public String getDescription()
    {
        return description;
    }

    public Advert setDescription(final String description)
    {
        this.description = description;
        return this;
    }

    public ZonedDateTime getPostedAt()
    {
        return postedAt;
    }

    public Advert setPostedAt(final ZonedDateTime postedAt)
    {
        this.postedAt = postedAt;
        return this;
    }

    public ZonedDateTime getLastModifiedAt()
    {
        return lastModifiedAt;
    }

    public Advert setLastModifiedAt(final ZonedDateTime lastModifiedAt)
    {
        this.lastModifiedAt = lastModifiedAt;
        return this;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "postedById", nullable = false)
    public Advertiser getPostedBy()
    {
        return postedBy;
    }

    public Advert setPostedBy(final Advertiser postedBy)
    {
        this.postedBy = postedBy;
        return this;
    }

}
