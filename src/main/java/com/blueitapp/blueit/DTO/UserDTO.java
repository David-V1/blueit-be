package com.blueitapp.blueit.DTO;

import java.util.Optional;
import java.util.UUID;

public class UserDTO {

    public Optional<UUID> uuid;
    public String username;
    public String password;
    public String email;
    public String profilePicture;
}
