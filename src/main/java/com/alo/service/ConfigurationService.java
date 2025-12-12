package com.alo.service;

import com.alo.config.Configuration;
import com.alo.domain.part.PartInstance;
import com.alo.domain.part.PartType;
import com.alo.domain.part.Property;

public interface ConfigurationService {

    PartInstance createInstance(PartType type);

    void addPart(Configuration configuration, PartInstance instance);

    void addProperty(PartInstance instance, Property property);

    boolean isValid(Configuration configuration);
}
