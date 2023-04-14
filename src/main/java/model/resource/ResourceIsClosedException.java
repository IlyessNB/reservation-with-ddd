package model.resource;

import model.reservation.TimeRange;

public class ResourceIsClosedException extends Exception {
    public ResourceIsClosedException(ResourceId resourceId, TimeRange timeRange) {
        super("Ressource avec l'id " + resourceId + " est fermée le " + timeRange.getDate() + " de " + timeRange.getStartTime() + " à " + timeRange.getEndTime() + ".");
    }
}
