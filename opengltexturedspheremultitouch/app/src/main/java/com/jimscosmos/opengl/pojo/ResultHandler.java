package com.jimscosmos.opengl.pojo;

import java.util.List;

public interface ResultHandler<T> {

    void handleResults(List<T> meteorList);

    void handleError(Throwable t);

    int getNInterestedObject();

}