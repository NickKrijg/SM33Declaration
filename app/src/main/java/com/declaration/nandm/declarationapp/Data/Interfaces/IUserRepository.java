package com.declaration.nandm.declarationapp.Data.Interfaces;

import com.declaration.nandm.declarationapp.Domain.User;

public interface IUserRepository {
    User getUser(int id);
    boolean addUser(User user);
    boolean editUser(User user);
}
