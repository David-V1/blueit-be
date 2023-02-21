package com.blueitapp.blueit.DTO;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public class PostDTO {
    public Long id;
    public String title;
    public Integer likes;
    public Date postedDate;
    public String content;
    public UserDTO user;

    //TODO: Add the ImageDTO
}
