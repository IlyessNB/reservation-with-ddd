package model.resource;

import model.reservation.DateWithTimeRange;

public class ResourceIsClosedException extends Exception {
    public ResourceIsClosedException(ResourceId resourceId, DateWithTimeRange dateWithTimeRange) {
        super("Ressource avec l'id " + resourceId + " est fermée le " + dateWithTimeRange.getDate() + " de " + dateWithTimeRange.getStartTime() + " à " + dateWithTimeRange.getEndTime() + ".");
    }
}
