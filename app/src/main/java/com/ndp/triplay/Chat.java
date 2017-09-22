package com.ndp.triplay;

/**
 * Created by NatavanMirzayeva on 8/26/2017.
 */

public class Chat
{
    private String name;
    private String authorName;
    public String text;
    private int orderNo;
    private String response;

   // public String photoUrl;

    public Chat(){}

    public Chat(String text, String name,int orderNo, String response,String authorName)
    {
        this.text = text;
        this.name = name;
        this.orderNo = orderNo;
        this.response = response;
        this.authorName = authorName;

    }



    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public int getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public void increase(int orderNo)
    {
        orderNo = orderNo + 1;
        this.orderNo = orderNo;

    }

}
