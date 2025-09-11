package com.pragma.users.domain.api;

import com.pragma.users.domain.model.ObjectModel;

import java.util.List;

public interface IObjectServicePort {

    void saveObject(ObjectModel objectModel);

    List<ObjectModel> getAllObjects();
}