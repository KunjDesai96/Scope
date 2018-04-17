package com.example.kunj.scope;

class TripExpense {

    Long _id;
    private String P_EXPENSE_NAME;
    private String P_EXPENSE_CATEGORY;
    private String P_EXPENSE_AMOUNT;
    private String P_EXPENSE_DATE;


    public TripExpense(String p_EXPENSE_NAME, String p_EXPENSE_CATEGORY, String p_EXPENSE_AMOUNT, String p_EXPENSE_DATE) {
        P_EXPENSE_NAME = p_EXPENSE_NAME;
        P_EXPENSE_CATEGORY = p_EXPENSE_CATEGORY;
        P_EXPENSE_AMOUNT = p_EXPENSE_AMOUNT;
        P_EXPENSE_DATE = p_EXPENSE_DATE;
    }
    public TripExpense() {
    }


    public String getP_EXPENSE_NAME() {
        return P_EXPENSE_NAME;
    }

    public String getP_EXPENSE_CATEGORY() {
        return P_EXPENSE_CATEGORY;
    }

    public String getP_EXPENSE_AMOUNT() {
        return P_EXPENSE_AMOUNT;
    }

    public String getP_EXPENSE_DATE() {
        return P_EXPENSE_DATE;
    }

    public Long get_id() {
        return _id;
    }

    @Override
    public String toString() {
        return "PersonalExpense{" +
                "_id=" + _id +
                ", P_EXPENSE_NAME='" + P_EXPENSE_NAME + '\'' +
                ", P_EXPENSE_CATEGORY='" + P_EXPENSE_CATEGORY + '\'' +
                ", P_EXPENSE_AMOUNT='" + P_EXPENSE_AMOUNT + '\'' +
                ", P_EXPENSE_DATE='" + P_EXPENSE_DATE + '\'' +
                '}';
    }
}
