package com.monotonic.klassified.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Posting
{
    private long id;

    private String category;
    private String title;
    private String description;
    private Long postedAt;
    private Long lastModifiedAt;

    private String poster;
    private String contactDetails;

    public Posting()
    {
        // Jackson deserialization
    }

    public Posting(
        final long id,
        final String category,
        final String title,
        final String description,
        final Long postedAt,
        final Long lastModifiedAt,
        final String poster,
        final String contactDetails)
    {
        this.id = id;
        this.category = category;
        this.title = title;
        this.description = description;
        this.postedAt = postedAt;
        this.lastModifiedAt = lastModifiedAt;
        this.poster = poster;
        this.contactDetails = contactDetails;
    }

    @JsonProperty
    public long getId()
    {
        return id;
    }

    @JsonProperty
    public String getTitle()
    {
        return title;
    }

    @JsonProperty
    public String getDescription()
    {
        return description;
    }

    @JsonProperty
    public Long getPostedAt()
    {
        return postedAt;
    }

    @JsonProperty
    public Long getLastModifiedAt()
    {
        return lastModifiedAt;
    }

    @JsonProperty
    public String getPoster()
    {
        return poster;
    }

    @JsonProperty
    public String getContactDetails()
    {
        return contactDetails;
    }

    @JsonProperty
    public String getCategory()
    {
        return category;
    }
}
