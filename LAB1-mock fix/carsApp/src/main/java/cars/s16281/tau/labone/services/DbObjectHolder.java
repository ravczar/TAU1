package cars.s16281.tau.labone.services;

import java.time.LocalDateTime;
import java.util.Optional;

public class DbObjectHolder {
    private CarImpl car;
    private DateTimeProvider timeProvider;

    private Optional<LocalDateTime> creationDate = Optional.empty();
    private Optional<LocalDateTime> modificationDate = Optional.empty();
    private Optional<LocalDateTime> accessDate = Optional.empty();

    // ctor
    public DbObjectHolder(DateTimeProvider provider, CarImpl car){
        updateCreationDate();
        this.timeProvider = provider;
        this.car = car;
    }

    public CarImpl getCar(){
        updateAccessDate();
        return this.car;
    }

    private void updateAccessDate(){
        if(DbObjectProperties.isTrackAccessDate()) {
            this.accessDate = Optional.of(timeProvider.get());
        }
    }

    private void updateCreationDate() {
        if(DbObjectProperties.isTrackCreationDate()) {
            this.creationDate = Optional.of(timeProvider.get());
        }
    }


    

}
