package org.assignments.notification.enums;

public enum NotificationType {

    ORDER_CREATED("Order Created"),
    ORDER_PROCESSED("Order Processed"),
    ORDER_FAILED("Order Failed"),
    ORDER_COMPLETED("Order Completed");

    private String type;

    // Enum constructor must be private
    private NotificationType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return this.type;
    }

}
