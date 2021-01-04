package com.taxi.taxi.dto;

import com.taxi.taxi.model.Opinion;

public class OpinionDto implements Dto {
    private Long id;

    private Long rate;

    private String comment;

    public OpinionDto(Opinion opinion) {
        this.comment = opinion.getComment();
        this.id = opinion.getId();
        this.rate = opinion.getRate();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRate() {
        return rate;
    }

    public void setRate(Long rate) {
        this.rate = rate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
