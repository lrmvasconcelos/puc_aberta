package br.pucminas.pucaberta.util;



/**
 * Created by lucas on 23/04/17.
 */

public class CardContent
{
    private String head;
    private int image;

    public CardContent(String head, int image)
    {
        this.head = head;
        this.image = image;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
