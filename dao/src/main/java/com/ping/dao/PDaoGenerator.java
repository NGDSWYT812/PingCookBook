package com.ping.dao;

import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;

public class PDaoGenerator {
    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(1, "com.ping.cookbook.bean");
        schema.setDefaultJavaPackageDao("com.ping.cookbook.dao");
        addCookList(schema);
        addCookIndex(schema);
        addUser(schema);
        new de.greenrobot.daogenerator.DaoGenerator().generateAll(schema, "dao/src/main/java-gen");
    }

    public static void addCookList(Schema schema){
        Entity entity = schema.addEntity("CookList");
        entity.implementsSerializable();
        entity.addIdProperty().primaryKey();
        entity.addLongProperty("saveTime");
        entity.addStringProperty("type");
        entity.addLongProperty("tid");
    }
    public static void addUser(Schema schema){
        Entity entity = schema.addEntity("User");
        entity.implementsSerializable();
        entity.addIdProperty().primaryKey();
        entity.addStringProperty("nickname");
        entity.addStringProperty("usericon");
        entity.addStringProperty("sign");
    }
    public static void addCookIndex(Schema schema){
        Entity entity = schema.addEntity("CookIndex");
        entity.implementsSerializable();
        Property property = entity.addIdProperty().primaryKey().getProperty();
        entity.addIntProperty("type");
        entity.addLongProperty("saveTime");
        entity.addStringProperty("title");
        entity.addStringProperty("tags");
        entity.addStringProperty("intro");
        entity.addStringProperty("ingredients");
        entity.addStringProperty("burden");
        entity.addStringProperty("albums");
        Entity order = schema.addEntity("Step");
        order.addIdProperty();
        order.addStringProperty("img");
        order.addStringProperty("step");
        Property p=order.addLongProperty("IndexId").getProperty();
        entity.addToMany(property,order,p).setName("steps");
        order.addToOne(entity,p);
    }
}
