package cars.s16281.tau.labone.services;

import java.time.LocalDateTime;

public class DateTimeProvider{

    private LocalDateTime objectCreationTime;
    private LocalDateTime now;

    public DateTimeProvider(){
        this.objectCreationTime = LocalDateTime.now();
    }

    private void getTimeNow(){
        setNow(LocalDateTime.now());
    }

    public LocalDateTime get(){
        getTimeNow();
        return this.now;
    }

    // Getters and setters
    public LocalDateTime getNow(){
        return this.now;
    }
    public void setNow(LocalDateTime dateTime){
        this.now = dateTime;
    }
    public LocalDateTime getObjectCreationTime(){
        return this.objectCreationTime;
    }


}