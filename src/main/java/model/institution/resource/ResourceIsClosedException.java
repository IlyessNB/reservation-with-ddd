package model.institution.resource;

import java.time.LocalDate;
import java.time.LocalTime;

public class ResourceIsClosedException extends Exception {
    public ResourceIsClosedException(String resourceId, LocalDate date, LocalTime startTime, LocalTime endTime) {
        super("Ressource avec l'id " + resourceId + " est fermée le " + date + " de " + startTime + " à " + endTime + ".");
    }
}
