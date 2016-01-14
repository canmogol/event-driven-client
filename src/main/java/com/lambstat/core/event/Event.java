package com.lambstat.core.event;

import java.io.Serializable;

public interface Event extends Serializable {

    String getUuid();

    Event getParent();

    Event getRoot();

}
