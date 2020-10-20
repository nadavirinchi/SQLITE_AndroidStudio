package com.example.sqlite;

public class Computer {
    private int id;
    private String computername;
    private String computertype;

    public Computer(){
        super();
    }
    public Computer(int id,String computername,String computertype){
        this.computername = computername;
        this.computertype = computertype;
        this.id = id;
    }
    public Computer(String computername,String computertype){
        this.computername = computername;
        this.computertype = computertype;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComputername() {
        return computername;
    }

    public void setComputername(String computername) {
        this.computername = computername;
    }

    public String getComputertype() {
        return computertype;
    }

    public void setComputertype(String computertype) {
        this.computertype = computertype;
    }
}
