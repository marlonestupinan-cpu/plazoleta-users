package com.pragma.users.domain.spi;

import com.pragma.users.domain.model.ObjectModel;
import java.util.List;

public interface IObjectPersistencePort {
    ObjectModel saveObject(ObjectModel objectModel);

    List<ObjectModel> getAllObjects();
}