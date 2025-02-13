package org.example.bankapp.model;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class ResponseMassage {
    private  String Message;
    public ResponseMassage(String _message){
        this.Message = _message;
    }
}
