package dev.anes.core.model.forenum;

public enum Gender {
    FEMALE("FEMALE"),
    MALE("MALE");
    private String value;
    public String getValue(){
        return value;
    }   
    private Gender(String value){
        this.value=value;
    }
    @Override
    public String toString(){
        return value;
    }
}
