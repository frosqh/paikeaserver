package com.frosqh.paikeaserver.database.exceptions;

public class ObjectNotFound extends Exception{

    public ObjectNotFound(Class objectClass){
        super(objectClass.getName()+" not found");
    }
}
