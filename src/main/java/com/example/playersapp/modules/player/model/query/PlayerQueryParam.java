package com.example.playersapp.modules.player.model.query;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;


public class PlayerQueryParam {

    //@NotBlank(message = "First name must not be blank")
    @Size(min = 1, max = 50, message = "First name length must be between 1 and 50")
    private String firstName;

    private String lastName;

    private String playerID;

    @Min(value = 0, message = "Page number must be positive")
    private Integer page = QueryParamsConstants.DEFAULT_PAGE_NUMBER;

    @Min(value = 1, message = "Size must be at least 1")
    private Integer size = QueryParamsConstants.DEFAULT_PAGE_SIZE;

    public PlayerQueryParam() {
    }


    public PlayerQueryParam(String firstName, String lastName, String playerID, Integer page, Integer size) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.playerID = playerID;
        this.page = page;
        this.size = size;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPlayerID() {
        return playerID;
    }

    public Integer getPage() {
        return page;
    }

    public Integer getSize() {
        return size;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPlayerID(String playerID) {
        this.playerID = playerID;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
