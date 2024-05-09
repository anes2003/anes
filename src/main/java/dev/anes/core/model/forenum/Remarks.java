package dev.anes.core.model.forenum;

public enum Remarks {
    NOLOAN("No Loan involvement"),
    PAYMENTCOMPLETE("Payment Complete"),
    OVERDUE("Overdue"),
    TENDAYS("10 Days Until Due"),
    ACTIVE("Active");

    private String value;

    public String getValue() {
        return value;
    }

    private Remarks(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    public static Remarks valueOf(int intValue) {
        switch (intValue) {
            case 0:
                return NOLOAN;
            case 1:
                return PAYMENTCOMPLETE;
            case 2:
                return OVERDUE;
            case 3:
                return TENDAYS;
            case 4:
                return ACTIVE;
            default:
                throw new IllegalArgumentException("Invalid integer value for Remarks enum: " + intValue);
        }
    }

    public int toInt() {
        switch (this) {
            case NOLOAN:
                return 0;
            case PAYMENTCOMPLETE:
                return 1;
            case OVERDUE:
                return 2;
            case TENDAYS:
                return 3;
            case ACTIVE:
                return 4;
            default:
                throw new IllegalStateException("Unexpected value: " + this);
        }
    }

}
