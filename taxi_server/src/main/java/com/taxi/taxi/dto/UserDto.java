package com.taxi.taxi.dto;

public interface UserDto extends Dto {
    public Long getId();
    public void setId(Long id);
    public String getUsername();
    public void setUsername(String username);
    public String getEmail();
    public void setEmail(String email);
    public String getPhoneNumber();
    public void setPhoneNumber(String phoneNumber);
}
