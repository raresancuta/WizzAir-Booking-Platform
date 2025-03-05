package ubb.scs.map.zboruri_practic.Utils;

public interface Observable {

    public void addObserver(Observer observer);
    public void removeObserver(Observer observer);
    public void notifyObservers();
}
