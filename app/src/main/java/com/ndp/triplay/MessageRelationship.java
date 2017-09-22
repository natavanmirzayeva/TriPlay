package com.ndp.triplay;

import java.util.ArrayList;

/**
 * Created by NatavanMirzayeva on 9/3/2017.
 */

public class MessageRelationship
{
    private String userEmail;
    private String characterName;
    private String messageText;
    private int orderNo;
    private  String response;
    private ArrayList<MessageRelationship> messages = new ArrayList<MessageRelationship>();



    public  MessageRelationship(String userEmail,String characterName,String messageText, int orderNo, String response )
    {
        this.userEmail = userEmail;
        this.characterName = characterName;
        this.messageText = messageText;
        this.orderNo = orderNo;
        this.response = response;

    }

    public MessageRelationship(String userEmail,String characterName,int OrderNo)
    {
        this.userEmail = userEmail;
        this.characterName = characterName;
        this.orderNo = OrderNo;
        messages.add(this);

    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getMessageText() {
        return messageText;
    }


    public String getUserEmail() {
        return userEmail;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getCharacterName() {
        return characterName;
    }

    public int getOrderNo() {
        return orderNo;
    }


}
