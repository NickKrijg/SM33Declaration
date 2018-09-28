package com.declaration.nandm.declarationapp.Data.Interfaces;

import com.declaration.nandm.declarationapp.Domain.Declaration;

import java.util.List;

public interface IDeclarationRepository {
    boolean addDeclaration(Declaration declaration);
    List<Declaration> getDeclarationsUser(int userId);
}
