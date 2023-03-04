package sample;

public class Car {

    String marque;
    int speed;
    String color;
    String made;

    //constructeur
    public Car(String marque, int speed, String color, String made) {
        this.marque = marque;
        this.speed = speed;
        this.color = color;
        this.made = made;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getMade() {
        return made;
    }

    public void setMade(String made) {
        this.made = made;
    }
}
