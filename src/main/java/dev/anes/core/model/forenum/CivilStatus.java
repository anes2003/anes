package dev.anes.core.model.forenum;

public enum CivilStatus {
    SINGLE("Single"),
    MARRIED("Married"),
    WIDDOW("Widowed"),
    SEPARATED("Legally Separated");
    private String value;
    public String getValue(){
        return value;
    }   
    private CivilStatus(String value){
        this.value=value;
    }
    @Override
    public String toString(){
        return value;
    }
}
