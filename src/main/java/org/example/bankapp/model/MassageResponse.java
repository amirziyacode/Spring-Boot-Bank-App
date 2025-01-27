package org.example.bankapp.model;


import lombok.Data;

@Data
public class MassageResponse {
    private  String Message;
    public  MassageResponse(String _message){
        this.Message = _message;
    }
}
