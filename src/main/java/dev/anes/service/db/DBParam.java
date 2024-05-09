package dev.anes.service.db;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DBParam {

    private final  IntegerProperty type;
    private final StringProperty field;
    private final ObjectProperty<Object>data;
    public DBParam(int type ,String field){
        this(type,field,null);
    }
    public DBParam(int type, String field,Object data){
        this.type=new SimpleIntegerProperty(type);
        this.field=new SimpleStringProperty(field);
        this.data=new SimpleObjectProperty<>(data);
    }
     //getters
     public int getType(){
        return type.get();
    }
    public String getField(){
        return field.get();
    }
    public Object getData(){
        return data.get();
    }
    //setters
    public void setType(int type){
        this.type.set(type);
    }
    public void setField(String field){
        this.field.set(field);
    }
    public void setData(Object data){
        this.data.set(data);
    }
    //property
    public IntegerProperty typeProperty(){
        return type;
    }
    public StringProperty fieldProperty(){
        return field;
    }
    public ObjectProperty<Object> dataProperty(){
        return data;
    }
}
